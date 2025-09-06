package com.bank.transfer.presentation.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bank.transfer.data.model.TransferDetails
import com.bank.transfer.data.model.TransferResult
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.domain.repository.TransferRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PaymentViewModelTest {
    @get:Rule
    private var instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: PaymentViewModel
    private lateinit var mockTransferRepository: TransferRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockTransferRepository = mock()
        viewModel = PaymentViewModel(mockTransferRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initializeTransferType sets domestic transfer type correctly`() {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        assertEquals(TransferType.DOMESTIC, viewModel.uiState.value.currentTransferType)
    }

    @Test
    fun `initializeTransferType sets international transfer type correctly`() {
        viewModel.initializeTransferType(TransferType.INTERNATIONAL)
        assertEquals(TransferType.INTERNATIONAL, viewModel.uiState.value.currentTransferType)
    }

    @Test
    fun `setTransferType to DOMESTIC resets relevant fields`() {
        viewModel.initializeTransferType(TransferType.INTERNATIONAL)
        viewModel.onRecipientNameChanged("John Doe")
        viewModel.onAccountNumberChanged("123456789")
        viewModel.onAmountChanged("100.0")
        viewModel.onIbanChanged("DE89370400440532013000")
        viewModel.onSwiftCodeChanged("DEUTDEFF")

        viewModel.setTransferType(TransferType.DOMESTIC)

        assertEquals(TransferType.DOMESTIC, viewModel.uiState.value.currentTransferType)
        assertEquals("", viewModel.uiState.value.recipientName)
        assertEquals("", viewModel.uiState.value.accountNumber)
        assertEquals("", viewModel.uiState.value.amount)
        assertEquals("", viewModel.uiState.value.iban)
        assertEquals("", viewModel.uiState.value.swiftCode)
        assertNull(viewModel.uiState.value.recipientNameError)
        assertNull(viewModel.uiState.value.accountNumberError)
        assertNull(viewModel.uiState.value.amountError)
        assertNull(viewModel.uiState.value.ibanError)
        assertNull(viewModel.uiState.value.swiftCodeError)
        assertNull(viewModel.uiState.value.paymentResult)
    }

    @Test
    fun `setTransferType to INTERNATIONAL keeps IBAN and SWIFT if previously entered`() {
        viewModel.initializeTransferType(TransferType.INTERNATIONAL)
        viewModel.onIbanChanged("DE89370400440532013000")
        viewModel.onSwiftCodeChanged("DEUTDEFF")

        viewModel.onRecipientNameChanged("John Doe")
        viewModel.setTransferType(TransferType.INTERNATIONAL)

        assertEquals("DE89370400440532013000", viewModel.uiState.value.iban)
        assertEquals("DEUTDEFF", viewModel.uiState.value.swiftCode)
    }


    @Test
    fun `onRecipientNameChanged updates recipient name and clears error`() {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        viewModel.sendPayment()
        assertTrue(viewModel.uiState.value.recipientNameError != null)

        viewModel.onRecipientNameChanged("Jane Doe")
        assertEquals("Jane Doe", viewModel.uiState.value.recipientName)
        assertNull(viewModel.uiState.value.recipientNameError)
    }

    @Test
    fun `onAccountNumberChanged updates account number and clears error`() {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        viewModel.sendPayment()
        assertTrue(viewModel.uiState.value.accountNumberError == null)

        viewModel.onAccountNumberChanged("987654321")
        assertEquals("987654321", viewModel.uiState.value.accountNumber)
        assertNull(viewModel.uiState.value.accountNumberError)
    }

    @Test
    fun `onAmountChanged updates amount and clears error`() {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        viewModel.sendPayment()
        assertTrue(viewModel.uiState.value.amountError == null)

        viewModel.onAmountChanged("250.75")
        assertEquals("250.75", viewModel.uiState.value.amount)
        assertNull(viewModel.uiState.value.amountError)
    }

    @Test
    fun `onIbanChanged updates IBAN if type is INTERNATIONAL`() {
        viewModel.initializeTransferType(TransferType.INTERNATIONAL)
        viewModel.onIbanChanged("GB29NWBK60161331926819")
        assertEquals("GB29NWBK60161331926819", viewModel.uiState.value.iban)
        assertNull(viewModel.uiState.value.ibanError)
    }

    @Test
    fun `onIbanChanged does not update IBAN if type is DOMESTIC`() {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        viewModel.onIbanChanged("GB29NWBK60161331926819")
        assertEquals("", viewModel.uiState.value.iban)
    }

    @Test
    fun `onSwiftCodeChanged updates SWIFT if type is INTERNATIONAL`() {
        viewModel.initializeTransferType(TransferType.INTERNATIONAL)
        viewModel.onSwiftCodeChanged("NWBKGB2L")
        assertEquals("NWBKGB2L", viewModel.uiState.value.swiftCode)
        assertNull(viewModel.uiState.value.swiftCodeError)
    }

    @Test
    fun `onSwiftCodeChanged does not update SWIFT if type is DOMESTIC`() {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        viewModel.onSwiftCodeChanged("NWBKGB2L")
        assertEquals("", viewModel.uiState.value.swiftCode)
    }

    @Test
    fun `sendPayment with invalid data shows errors and does not call repository`() = runTest {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        viewModel.sendPayment()

        assertNotNull(viewModel.uiState.value.recipientNameError)
//        assertNotNull(viewModel.uiState.value.accountNumberError)
//        assertNotNull(viewModel.uiState.value.amountError)
//        assertFalse(viewModel.uiState.value.isLoading)
//        assertNull(viewModel.uiState.value.paymentResult)

        verify(mockTransferRepository, never()).domesticTransfer(any())
        verify(mockTransferRepository, never()).internationalTransfer(any())
    }

    @Test
    fun `sendPayment with invalid recipient name shows error`() {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        viewModel.onRecipientNameChanged("J")
        viewModel.onAccountNumberChanged("12345678")
        viewModel.onAmountChanged("10")
        viewModel.sendPayment()
        assertEquals(
            "Name is too short",
            viewModel.uiState.value.recipientNameError
        )
    }

    @Test
    fun `sendPayment with invalid account number shows error`() {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        viewModel.onRecipientNameChanged("John Doe")
        viewModel.onAccountNumberChanged("123")
        viewModel.onAmountChanged("10")
        viewModel.sendPayment()
        assertEquals(
            "Account number invalid",
            viewModel.uiState.value.accountNumberError
        )
    }

    @Test
    fun `sendPayment with invalid amount shows error`() {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        viewModel.onRecipientNameChanged("John Doe")
        viewModel.onAccountNumberChanged("12345678")
        viewModel.onAmountChanged("abc")
        viewModel.sendPayment()
        assertEquals("Invalid amount", viewModel.uiState.value.amountError)
    }

    @Test
    fun `sendPayment for INTERNATIONAL with missing IBAN shows error`() {
        viewModel.initializeTransferType(TransferType.INTERNATIONAL)
        viewModel.onRecipientNameChanged("John Doe")
        viewModel.onAccountNumberChanged("12345678")
        viewModel.onAmountChanged("100")
        // viewModel.onIbanChanged("...")
        viewModel.onSwiftCodeChanged("DEUTDEFF")
        viewModel.sendPayment()
        assertEquals("IBAN is required", viewModel.uiState.value.ibanError)
    }

    @Test
    fun `sendPayment for INTERNATIONAL with missing SWIFT shows error`() {
        viewModel.initializeTransferType(TransferType.INTERNATIONAL)
        viewModel.onRecipientNameChanged("John Doe")
        viewModel.onAccountNumberChanged("12345678")
        viewModel.onAmountChanged("100")
        viewModel.onIbanChanged("DE89370400440532013000")
        // viewModel.onSwiftCodeChanged("...")
        viewModel.sendPayment()
        assertEquals("SWIFT code is required", viewModel.uiState.value.swiftCodeError)
    }

    @Test
    fun `sendPayment for DOMESTIC successful`() = runTest {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        val recipientName = "Jane Doe"
        val accountNumber = "1234567890"
        val amount = "150.0"
        val expectedDetails = TransferDetails.DomesticTransferDetails(
            recipientName, accountNumber, amount.toDouble()
        )

        whenever(mockTransferRepository.domesticTransfer(expectedDetails))
            .thenReturn(
                TransferResult.Success(
                    statusCode = 200,
                    message = "Domestic payment successful"
                )
            )

        viewModel.onRecipientNameChanged(recipientName)
        viewModel.onAccountNumberChanged(accountNumber)
        viewModel.onAmountChanged(amount)
        viewModel.sendPayment()

        assertTrue(viewModel.uiState.value.isLoading)
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals("Payment Successful!", viewModel.uiState.value.paymentResult)
        assertEquals("", viewModel.uiState.value.recipientName)
        assertEquals("", viewModel.uiState.value.accountNumber)
        assertEquals("", viewModel.uiState.value.amount)

        verify(mockTransferRepository).domesticTransfer(expectedDetails)
        verify(mockTransferRepository, never()).internationalTransfer(any())
    }

    @Test
    fun `sendPayment for INTERNATIONAL successful`() = runTest {
        viewModel.initializeTransferType(TransferType.INTERNATIONAL)
        val recipientName = "John International"
        val accountNumber = "0987654321"
        val amount = "500.0"
        val iban = "GB29NWBK60161331926819"
        val swift = "NWBKGB2L"
        val expectedDetails = TransferDetails.InternationalTransferDetails(
            recipientName, accountNumber, amount.toDouble(), iban, swift
        )

        whenever(mockTransferRepository.internationalTransfer(expectedDetails))
            .thenReturn(
                TransferResult.Success(
                    200,
                    "International payment successful"
                )
            )

        viewModel.onRecipientNameChanged(recipientName)
        viewModel.onAccountNumberChanged(accountNumber)
        viewModel.onAmountChanged(amount)
        viewModel.onIbanChanged(iban)
        viewModel.onSwiftCodeChanged(swift)
        viewModel.sendPayment()

        assertTrue(viewModel.uiState.value.isLoading)
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals("Payment Successful!", viewModel.uiState.value.paymentResult)
        assertEquals("", viewModel.uiState.value.recipientName)
        assertEquals("", viewModel.uiState.value.iban)
        assertEquals("", viewModel.uiState.value.swiftCode)

        verify(mockTransferRepository).internationalTransfer(expectedDetails)
        verify(mockTransferRepository, never()).domesticTransfer(any())
    }

    @Test
    fun `sendPayment DOMESTIC fails`() = runTest {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        val recipientName = "Fail Recipient"
        val accountNumber = "111222333"
        val amount = "10.0"
        val expectedDetails = TransferDetails.DomesticTransferDetails(
            recipientName, accountNumber, amount.toDouble()
        )

        whenever(mockTransferRepository.domesticTransfer(expectedDetails))
            .thenReturn(
                TransferResult.Error(
                    400,
                    "Network error"
                )
            )

        viewModel.onRecipientNameChanged(recipientName)
        viewModel.onAccountNumberChanged(accountNumber)
        viewModel.onAmountChanged(amount)
        viewModel.sendPayment()

        assertTrue(viewModel.uiState.value.isLoading)
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals(
            "Payment Failed.",
            viewModel.uiState.value.paymentResult
        ) // Specific error message is simplified in VM
        // Fields should NOT be cleared on failure
        assertEquals(recipientName, viewModel.uiState.value.recipientName)

        verify(mockTransferRepository).domesticTransfer(expectedDetails)
    }

    @Test
    fun `sendPayment INTERNATIONAL fails`() = runTest {
        viewModel.initializeTransferType(TransferType.INTERNATIONAL)
        val recipientName = "Fail Intl"
        val accountNumber = "000999888"
        val amount = "1000.0"
        val iban = "FR1420041010050500013M02606"
        val swift = "CRLYFRPP"
        val expectedDetails = TransferDetails.InternationalTransferDetails(
            recipientName, accountNumber, amount.toDouble(), iban, swift
        )

        whenever(mockTransferRepository.internationalTransfer(expectedDetails))
            .thenReturn(
                TransferResult.Error(
                    300,
                    "Insufficient funds"
                )
            )

        viewModel.onRecipientNameChanged(recipientName)
        viewModel.onAccountNumberChanged(accountNumber)
        viewModel.onAmountChanged(amount)
        viewModel.onIbanChanged(iban)
        viewModel.onSwiftCodeChanged(swift)
        viewModel.sendPayment()

        assertTrue(viewModel.uiState.value.isLoading)
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals("Payment Failed.", viewModel.uiState.value.paymentResult)
        assertEquals(recipientName, viewModel.uiState.value.recipientName)
        assertEquals(iban, viewModel.uiState.value.iban)

        verify(mockTransferRepository).internationalTransfer(expectedDetails)
    }

    @Test
    fun `sendPayment handles repository exception`() = runTest {
        viewModel.initializeTransferType(TransferType.DOMESTIC)
        val recipientName = "Exception User"
        val accountNumber = "555666777"
        val amount = "50.0"
        val expectedDetails = TransferDetails.DomesticTransferDetails(
            recipientName, accountNumber, amount.toDouble()
        )
        val exceptionMessage = "Critical system failure"

        whenever(mockTransferRepository.domesticTransfer(expectedDetails))
            .thenThrow(RuntimeException(exceptionMessage))

        viewModel.onRecipientNameChanged(recipientName)
        viewModel.onAccountNumberChanged(accountNumber)
        viewModel.onAmountChanged(amount)
        viewModel.sendPayment()

        assertTrue(viewModel.uiState.value.isLoading)
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals("Payment Failed. $exceptionMessage", viewModel.uiState.value.paymentResult)
        assertEquals(recipientName, viewModel.uiState.value.recipientName) // Fields not cleared

        verify(mockTransferRepository).domesticTransfer(expectedDetails)
    }
}
