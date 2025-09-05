package com.bank.transfer.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bank.transfer.data.model.PaymentUIState
import com.bank.transfer.data.model.TransferType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class PaymentViewModel : ViewModel() {

    private var _uiState = mutableStateOf(PaymentUIState())
    val uiState: State<PaymentUIState> = _uiState

    fun setTransferType(newType: TransferType) {
        Log.d("PaymentViewModel", "Transfer type set to: $newType")
        _uiState.value = _uiState.value.copy(
            currentTransferType = newType,
            recipientName = "",
            accountNumber = "",
            amount = "",
            iban = if (newType == TransferType.DOMESTIC) "" else _uiState.value.iban,
            swiftCode = if (newType == TransferType.DOMESTIC) "" else _uiState.value.swiftCode,
            recipientNameError = null,
            accountNumberError = null,
            amountError = null,
            ibanError = null,
            swiftCodeError = null,
            paymentResult = null
        )
    }

    fun onRecipientNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(recipientName = newName, recipientNameError = null)
    }

    fun onAccountNumberChanged(newAccountNumber: String) {
        _uiState.value =
            _uiState.value.copy(accountNumber = newAccountNumber, accountNumberError = null)
    }

    fun onAmountChanged(newAmount: String) {
        _uiState.value = _uiState.value.copy(amount = newAmount, amountError = null)
    }

    fun onIbanChanged(newIban: String) {
        if (_uiState.value.currentTransferType == TransferType.INTERNATIONAL) {
            _uiState.value = _uiState.value.copy(iban = newIban, ibanError = null)
        }
    }

    fun onSwiftCodeChanged(newSwift: String) {
        if (_uiState.value.currentTransferType == TransferType.INTERNATIONAL) {
            _uiState.value = _uiState.value.copy(swiftCode = newSwift, swiftCodeError = null)
        }
    }


    fun sendPayment() {
        if (_uiState.value.recipientName.isBlank()) {
            _uiState.value =
                _uiState.value.copy(recipientNameError = "Recipient name is required")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, paymentResult = null)
        Log.d(
            "PaymentViewModel",
            "Attempting to send payment. Details: $_uiState.value, CurrentState: $_uiState.value"
        )

        viewModelScope.launch {
            try {
                Log.v("PaymentViewModel", "Sending payment...")
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    paymentResult = null
                )
                delay(5000)
                val success = Random.nextBoolean()
                if (success) {
                    Log.v("PaymentViewModel", "Payment successful!")
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            paymentResult = "Payment Successful!"
                        )
//                onResult(TransferResult.Success(message = "Payment Successful!"))
                } else {
                    Log.v("PaymentViewModel", "Payment failed!")
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            paymentResult = "Payment Failed."
                        )
//                onResult(TransferResult.Error(message = "Payment Failed."))
                }
            } catch (e: Exception) {
                Log.v("PaymentViewModel", "Payment failed with exception: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    paymentResult = "Payment Failed. ${e.message}"
                )
            }
        }
    }
}

