package com.bank.transfer.presentation.navigation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.presentation.viewmodel.PaymentViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


// You might need Robolectric if your ViewModels have Android dependencies
// or if you want to test navigation state more deeply without a full instrumented test.
// For simple NavController interactions, you might not need it.
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Config.OLDEST_SDK]) // Configure Robolectric as needed
class AppNavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var mockPaymentViewModel: PaymentViewModel // Use a mock or a fake

    @Before
    fun setup() {
        // Create a TestNavHostController
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        // Add necessary navigators for Compose
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        navController.navigatorProvider.addNavigator(DialogNavigator()) // If you use dialog destinations

        // Mock your ViewModel. Use a real one if it has no complex dependencies for these tests.
        // For verifying navigation calls triggered BY the ViewModel, a real one might be okay.
        // For verifying data SET ON the ViewModel before navigation, a mock is better.
        mockPaymentViewModel = mockk(relaxed = true) // relaxed = true to avoid stubbing all methods
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule.setContent {
            AppNavHost(
                navController = navController,
                paymentViewModel = mockPaymentViewModel,
                finish = context.finish()
            )
        }

        // Check that the current destination is the start destination
        assert(navController.currentBackStackEntry?.destination?.route == AppDestinations.TRANSFER_TYPE_ROUTE)
        // You can also assert that the TransferTypeScreen's content is visible
        composeTestRule.onNodeWithText("Select Transfer Type").assertIsDisplayed() // Example text
    }

    @Test
    fun appNavHost_navigateToPaymentScreen_fromTransferTypeScreen() {
        composeTestRule.setContent {
            AppNavHost(
                navController = navController,
                paymentViewModel = mockPaymentViewModel,
                finish = context.finish()
            )
        }

        // 1. Ensure we are on the TransferTypeScreen
        composeTestRule.onNodeWithText("Select Transfer Type").assertIsDisplayed() // Example text

        // 2. Simulate the action that triggers navigation (e.g., clicking a button in TransferTypeScreen)
        // This will call the onNavigateToPaymentScreen lambda in your AppNavHost
        val testTransferType = TransferType.DOMESTIC
        composeTestRule.onNodeWithText(testTransferType.toString())
            .performClick() // Assuming you have a button/item with this text

        // 3. Verify that the PaymentViewModel was updated
        verify { mockPaymentViewModel.setTransferType(testTransferType) }

        // 4. Verify that navigate was called on the NavController
        assert(navController.currentBackStackEntry?.destination?.route == AppDestinations.PAYMENT_ROUTE)

        // 5. Verify that the PaymentScreen is now displayed
        composeTestRule.onNodeWithText("Enter Payment Details")
            .assertIsDisplayed() // Example text from PaymentScreen
    }

    @Test
    fun appNavHost_popBackStack_fromPaymentScreen() {
        composeTestRule.setContent {
            AppNavHost(
                navController = navController,
                paymentViewModel = mockPaymentViewModel,
                finish = context.finish()
            )
        }

        // First, navigate to PaymentScreen
        // Manually set the route for this test setup, or perform the click like in the previous test
        navController.setCurrentDestination(AppDestinations.TRANSFER_TYPE_ROUTE) // Start here
        composeTestRule.runOnUiThread { // Ensure navigation happens on the main thread
            // Simulate the navigation that would happen
            mockPaymentViewModel.setTransferType(TransferType.DOMESTIC) // Call this as it's part of the original lambda
            navController.navigate(AppDestinations.PAYMENT_ROUTE)
        }
        composeTestRule.waitForIdle() // Wait for navigation to complete

        // Verify we are on the PaymentScreen
        assert(navController.currentBackStackEntry?.destination?.route == AppDestinations.PAYMENT_ROUTE)
        composeTestRule.onNodeWithText("Enter Payment Details").assertIsDisplayed() // Example text

        // Simulate the back action from PaymentScreen
        composeTestRule.onNodeWithText("Back")
            .performClick() // Assuming a "Back" button in PaymentScreen

        // Verify that popBackStack was called (implicitly by checking the current route)
        assert(navController.currentBackStackEntry?.destination?.route == AppDestinations.TRANSFER_TYPE_ROUTE)

        // Verify that TransferTypeScreen is displayed again
        composeTestRule.onNodeWithText("Select Transfer Type").assertIsDisplayed() // Example text
    }

    // You would also need a way to provide a mock or test instance of TransferTypeViewModel
    // if its interactions are critical to the navigation logic being tested.
    // Often, for NavHost tests, you focus more on the NavController's state.
}
