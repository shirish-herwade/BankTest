package com.bank.transfer.presentation.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bank.transfer.domain.repository.TransferRepository
import com.bank.transfer.presentation.factory.PaymentViewModelFactory
import com.bank.transfer.presentation.navigation.AppNavHost
import com.bank.transfer.presentation.viewmodel.PaymentViewModel
import com.bank.transfer.ui.theme.PaymentBankTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TransferActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<TransferActivity>()

    @Test
    fun appNavHost_isDisplayed() {
//        composeTestRule.setContent {
//            PaymentBankTheme {
//                val navController = rememberNavController()
//                val repository: TransferRepository = TransferRepository.create()
//                val paymentViewModelFactory = PaymentViewModelFactory(repository)
//                val paymentViewModel: PaymentViewModel =
//                    androidx.lifecycle.viewmodel.compose.viewModel(
//                        factory = paymentViewModelFactory
//                    )
//
//                AppNavHost(
//                    navController = navController,
//                    paymentViewModel = paymentViewModel
//                )
//            }
//        }

    }

    @Test
    fun Transfer_Type_screen_is_shown_with_two_headers_and_two_buttons_with_text() {
        composeTestRule.onNodeWithText("Transfer Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Select Transfer Type").assertIsDisplayed()
        composeTestRule.onNodeWithText("Domestic Transfer").assertIsDisplayed()
        composeTestRule.onNodeWithText("International Transfer").assertIsDisplayed()
    }

    @Test
    fun navigateToTransferScreen_Domestic_onButtonClick() {
        composeTestRule.onNodeWithText("Domestic Transfer").performClick()
        composeTestRule.onNodeWithText("Enter Amount")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Enter iBan").assertIsNotDisplayed()
    }

    @Test
    fun navigateToPaymentScreen_Int_onButtonClick() {
        composeTestRule.onNodeWithText("International Transfer").performClick()
        composeTestRule.onNodeWithText("Amount")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("IBan").assertIsDisplayed()
    }
}
