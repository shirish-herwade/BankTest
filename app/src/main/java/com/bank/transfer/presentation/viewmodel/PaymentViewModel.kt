package com.bank.transfer.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bank.transfer.data.model.PaymentUIState
import com.bank.transfer.data.model.TransferDetails
import com.bank.transfer.data.model.TransferResult
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.domain.BankLog
import com.bank.transfer.domain.repository.TransferRepository
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val transferRepository: TransferRepository
) : ViewModel() {
    private val TAG = "PaymentViewModel"
    private var _uiState = mutableStateOf(PaymentUIState())
    val uiState: State<PaymentUIState> = _uiState

    internal fun initializeTransferType(transferType: TransferType) {
        _uiState.value = _uiState.value.copy(
            currentTransferType = transferType
        )
        BankLog.d(TAG, "Initialized with transfer type: $transferType")
    }

    internal fun setTransferType(newType: TransferType) {
        BankLog.d(TAG, "Transfer type set to: $newType")
        _uiState.value = _uiState.value.copy(
            currentTransferType = newType,
            recipientName = "",
            accountNumber = "",
            amount = "",
            iban = if (newType == TransferType.DOMESTIC) ""
            else _uiState.value.iban,
            swiftCode = if (newType == TransferType.DOMESTIC) ""
            else _uiState.value.swiftCode,
            recipientNameError = null,
            accountNumberError = null,
            amountError = null,
            ibanError = null,
            swiftCodeError = null,
            paymentResult = null
        )
    }

    internal fun onRecipientNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(
            recipientName = newName, recipientNameError = null
        )
    }

    internal fun onAccountNumberChanged(newAccountNumber: String) {
        _uiState.value = _uiState.value.copy(
            accountNumber = newAccountNumber, accountNumberError = null
        )
    }

    internal fun onAmountChanged(newAmount: String) {
        _uiState.value = _uiState.value.copy(
            amount = newAmount, amountError = null
        )
    }

    internal fun onIbanChanged(newIban: String) {
        BankLog.d(TAG, "onIbanChanged called with: $newIban")
        if (_uiState.value.currentTransferType == TransferType.INTERNATIONAL) {
            BankLog.d(TAG, " in if INTERNATIONAL type")
            _uiState.value = _uiState.value.copy(
                iban = newIban, ibanError = null
            )
        } else {
            BankLog.e(TAG, "onIbanChanged in else INTERNATIONAL type")
        }
    }

    internal fun onSwiftCodeChanged(newSwift: String) {
        if (_uiState.value.currentTransferType == TransferType.INTERNATIONAL) {
            _uiState.value = _uiState.value.copy(
                swiftCode = newSwift, swiftCodeError = null
            )
        } else {
            BankLog.e("in onSwiftCodeChanged", " in else INTERNATIONAL type")
        }
    }

    private fun isDataValid(): Boolean {
        val name = _uiState.value.recipientName
        if (name.isBlank()) {
            _uiState.value = _uiState.value.copy(
                recipientNameError = "Recipient name is required"
            )
            return false
        } else if (name.length < 3) {
            _uiState.value = _uiState.value.copy(
                recipientNameError = "Name is too short"
            )
            return false
        }

        val accountNumber = _uiState.value.accountNumber
        if (accountNumber.isBlank()) {
            _uiState.value = _uiState.value.copy(accountNumberError = "Account number is required")
            return false
        } else if (accountNumber.length < 6 || accountNumber.length > 12) {
            _uiState.value = _uiState.value.copy(accountNumberError = "Account number invalid")
            return false
        }

        val amount = _uiState.value.amount
        if (amount.isBlank()) {
            _uiState.value = _uiState.value.copy(amountError = "Amount is required")
            return false
        } else if (amount.toDoubleOrNull() == null) {
            _uiState.value = _uiState.value.copy(amountError = "Invalid amount")
            return false
        }

        if (_uiState.value.currentTransferType == TransferType.INTERNATIONAL && (_uiState.value.iban.isBlank() || _uiState.value.swiftCode.isBlank())) {
            if (_uiState.value.iban.isBlank()) {
                _uiState.value = _uiState.value.copy(ibanError = "IBAN is required")
            }
            if (_uiState.value.swiftCode.isBlank()) {
                _uiState.value = _uiState.value.copy(swiftCodeError = "SWIFT code is required")
            }
            return false
        }
        return true
    }

    private fun clearFields() {
        _uiState.value = _uiState.value.copy(
            recipientName = "",
            accountNumber = "",
            amount = "",
            iban = "",
            swiftCode = "",
            recipientNameError = null,
            accountNumberError = null,
            amountError = null
        )
    }

    internal fun sendPayment() {
        if (!isDataValid()) {
            return
        }

        _uiState.value = _uiState.value.copy(
            isLoading = true, paymentResult = null
        )
        BankLog.d(
            TAG,
            "Attempting to send payment. Details: $_uiState.value, CurrentState: $_uiState.value"
        )

        viewModelScope.launch {
            try {
                val paymentResult: TransferResult

                if (_uiState.value.currentTransferType == TransferType.DOMESTIC) {
                    paymentResult = transferRepository.domesticTransfer(
                        TransferDetails.DomesticTransferDetails(
                            _uiState.value.recipientName,
                            _uiState.value.accountNumber,
                            _uiState.value.amount.toDouble()
                        )
                    )
                } else {
                    paymentResult = transferRepository.internationalTransfer(
                        TransferDetails.InternationalTransferDetails(
                            _uiState.value.recipientName,
                            _uiState.value.accountNumber,
                            _uiState.value.amount.toDouble(),
                            _uiState.value.iban,
                            _uiState.value.swiftCode
                        )
                    )
                }

                if (paymentResult is TransferResult.Success) {
                    BankLog.v(TAG, "Payment successful!")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, paymentResult = "Payment Successful!"
                    )
                    clearFields()
                } else { //  (paymentResult is TransferResult.Error)
                    BankLog.e(TAG, "Payment failed!")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false, paymentResult = "Payment Failed."
                    )
                }
            } catch (e: Exception) {
                BankLog.e(TAG, "Payment failed with exception: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false, paymentResult = "Payment Failed. ${e.message}"
                )
            }
        }
    }
}