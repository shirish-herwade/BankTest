package com.bank.transfer.presentation.viewmodel

import android.util.Log
import androidx.compose.animation.core.copy
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bank.transfer.data.model.PaymentUIState
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.domain.BankLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class PaymentViewModel : ViewModel() {

    private var _uiState = mutableStateOf(PaymentUIState())
    val uiState: State<PaymentUIState> = _uiState

    fun initializeTransferType(transferType: TransferType) {
        _uiState.value = _uiState.value.copy(
            currentTransferType = transferType
        )
        Log.d("PaymentViewModel", "Initialized with transfer type: $transferType")
    }

    fun setTransferType(newType: TransferType) {
        BankLog.d("PaymentViewModel", "Transfer type set to: $newType")
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

    fun onRecipientNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(
            recipientName = newName,
            recipientNameError = null
        )
    }

    fun onAccountNumberChanged(newAccountNumber: String) {
        _uiState.value = _uiState.value.copy(
            accountNumber = newAccountNumber,
            accountNumberError = null
        )
    }

    fun onAmountChanged(newAmount: String) {
        _uiState.value = _uiState.value.copy(
            amount = newAmount,
            amountError = null
        )
    }

    fun onIbanChanged(newIban: String) {
        BankLog.d("PaymentViewModel", "onIbanChanged called with: $newIban")
        if (_uiState.value.currentTransferType == TransferType.INTERNATIONAL) {
            BankLog.d("PaymentViewModel", " in if INTERNATIONAL type") // Log state update
            _uiState.value = _uiState.value.copy(
                iban = newIban,
                ibanError = null
            )
        } else {
            Log.e("in onIbanChanged", " in else INTERNATIONAL type") // Log state update
        }
    }

    fun onSwiftCodeChanged(newSwift: String) {
        if (_uiState.value.currentTransferType == TransferType.INTERNATIONAL) {
            _uiState.value = _uiState.value.copy(
                swiftCode = newSwift,
                swiftCodeError = null
            )
        } else {
            Log.e("in onSwiftCodeChanged", " in else INTERNATIONAL type") // Log state update
        }
    }

    fun isDataValid(): Boolean {
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
            _uiState.value =
                _uiState.value.copy(accountNumberError = "Account number is required")
            return false
        } else if (accountNumber.length < 6 || accountNumber.length > 12) {
            _uiState.value =
                _uiState.value.copy(accountNumberError = "Account number invalid")
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

        if (_uiState.value.currentTransferType == TransferType.INTERNATIONAL &&
            (_uiState.value.iban.isBlank() || _uiState.value.swiftCode.isBlank())
        ) {
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

    fun clearFields() {
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

    fun sendPayment() {
        if (!isDataValid()) {
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, paymentResult = null)
        BankLog.d(
            "PaymentViewModel",
            "Attempting to send payment. Details: $_uiState.value, CurrentState: $_uiState.value"
        )

        viewModelScope.launch {
            try {
                BankLog.v("PaymentViewModel", "Sending payment...")
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    paymentResult = null
                )
                delay(3000)
                val success = Random.nextBoolean()
                if (success) {
                    BankLog.v("PaymentViewModel", "Payment successful!")
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            paymentResult = "Payment Successful!"
                        )
                    clearFields()
//                onResult(TransferResult.Success(message = "Payment Successful!"))
                } else {
                    BankLog.v("PaymentViewModel", "Payment failed!")
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            paymentResult = "Payment Failed."
                        )
//                onResult(TransferResult.Error(message = "Payment Failed."))
                }
            } catch (e: Exception) {
                BankLog.v("PaymentViewModel", "Payment failed with exception: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    paymentResult = "Payment Failed. ${e.message}"
                )
            }
        }
    }
}

