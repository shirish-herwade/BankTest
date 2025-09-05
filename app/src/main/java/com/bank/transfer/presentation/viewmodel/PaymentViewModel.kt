package com.bank.transfer.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // For coroutines
import com.bank.transfer.data.model.TransferDetails
import com.bank.transfer.data.model.TransferResult
import com.bank.transfer.data.model.TransferType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.runtime.State
import com.bank.transfer.data.model.PaymentUIState

class PaymentViewModel : ViewModel() {

    private var _uiState = mutableStateOf(PaymentUIState())
    val uiState: State<PaymentUIState> = _uiState

    fun setTransferType(newType: TransferType) {
        Log.d("PaymentViewModel", "Transfer type set to: $newType")
        _uiState.value = _uiState.value.copy(
            currentTransferType = newType,
            // Clear fields that might not be relevant for the new type or need re-entry
            recipientName = "",
            accountNumber = "",
            amount = "",
            iban = if (newType == TransferType.DOMESTIC) "" else _uiState.value.iban,
            swiftCode = if (newType == TransferType.DOMESTIC) "" else _uiState.value.swiftCode,
            // Clear all errors
            recipientNameError = null,
            accountNumberError = null,
            amountError = null,
            ibanError = null,
            swiftCodeError = null,
            paymentResult = null // Clear previous payment result
        )
    }

    fun onRecipientNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(recipientName = newName, recipientNameError = null)
    }

    fun onAccountNumberChanged(newAccountNumber: String) {
        _uiState.value = _uiState.value.copy(accountNumber = newAccountNumber, accountNumberError = null)
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


    fun sendPayment(onResultExternal: (TransferResult) -> Unit) { // Renamed param for clarity
        val details = TransferDetails(
            recipientName = _uiState.value.recipientName,
            accountNumber = _uiState.value.accountNumber,
//            amount = _uiState.value.amount, // This was the missing parameter
            iban = _uiState.value.iban,
            swiftCode = _uiState.value.swiftCode,
            fromAccount = TODO(),
            toAccount = TODO(),
            amount = 1.toDouble()
        )

        if (_uiState.value.recipientName.isBlank()) {
            _uiState.value = _uiState.value.copy(recipientNameError = "Recipient name is required")
            onResultExternal(TransferResult.Error(message = "Validation failed"))
            return
        }
        // ... more validations based on _uiState.value.currentTransferType ...

        _uiState.value = _uiState.value.copy(isLoading = true, paymentResult = null)
        Log.d("PaymentViewModel", "Attempting to send payment. CurrentState: $_uiState")

        viewModelScope.launch {
            delay(1500) // Simulate network delay
            val success = Random.nextBoolean()
            if (success) {
                _uiState.value = _uiState.value.copy(isLoading = false, paymentResult = "Payment Successful!")
                onResultExternal(TransferResult.Success(message = "Payment Successful!"))
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, paymentResult = "Payment Failed.")
                onResultExternal(TransferResult.Error(message = "Payment Failed."))
            }
        }
    }
}

