package com.bank.transfer.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bank.transfer.data.model.TransferDetails
import com.bank.transfer.data.model.TransferResult
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.presentation.state.TransferUIState

class TransferViewModel : ViewModel() {

    var transferUIState by mutableStateOf(TransferUIState())
        private set

//    fun onTransferTypeChanged(newType: TransferType) {
//        Log.d("TransferViewModel", "Transfer type changed to: $newType")
//        transferUIState = transferUIState.copy(
//            currentTransferType = newType,
//            // Clear fields that might not be relevant for the new type
//            iban = if (newType == TransferType.DOMESTIC) "" else transferUIState.iban,
//            swiftCode = if (newType == TransferType.DOMESTIC) "" else transferUIState.swiftCode,
//            ibanError = null,
//            swiftCodeError = null,
//            // Clear other form fields if needed upon type change
//            recipientName = "",
//            accountNumber = "",
//            amount = "",
//            recipientNameError = null,
//            accountNumberError = null,
//            amountError = null,
//            paymentResult = null
//        )
//    }

    fun onRecipientNameChanged(newName: String) {
        transferUIState = transferUIState.copy(
            recipientName = newName,
            recipientNameError = null
        )
    }

    fun onAccountNumberChanged(newAccountNumber: String) {
        transferUIState = transferUIState.copy(
            accountNumber = newAccountNumber,
            accountNumberError = null
        )
    }
    fun onAmountChanged(newAmount: String) {
        transferUIState = transferUIState.copy(
            amount = newAmount,
            amountError = null
        )
    }
    fun onIbanChanged(newIban: String) {
        if (transferUIState.currentTransferType == TransferType.INTERNATIONAL) {
            transferUIState = transferUIState.copy(
                iban = newIban,
                ibanError = null
            )
        }
    }

    fun onSwiftCodeChanged(newSwift: String) {
        if (transferUIState.currentTransferType == TransferType.INTERNATIONAL) {
            transferUIState = transferUIState.copy(
                swiftCode = newSwift,
                swiftCodeError = null
            )
        }
    }

    //TODO clean code in this class

//    fun sendPayment(details: TransferDetails, onResult: (TransferResult) -> Unit) {
//        // 1. Validate all necessary fields from transferUIState based on currentTransferType
//        // For example:
//        if (transferUIState.recipientName.isBlank()) {
//            transferUIState = transferUIState.copy(recipientNameError = "Recipient name is required")
//            onResult(TransferResult.Error(message = "Validation failed")) // Or a more specific error
//            return
//        }
//        // ... more validations ...
//
//        transferUIState = transferUIState.copy(isLoading = true, paymentResult = null)
//        Log.d("TransferViewModel", "Attempting to send payment. Details: $details, CurrentState: $transferUIState")
//
//        // Simulate network call or actual payment processing
//        // viewModelScope.launch {
//        //     delay(2000) // Simulate delay
//        //     val success = Random.nextBoolean() // Simulate success/failure
//        //     if (success) {
//        //         transferUIState = transferUIState.copy(isLoading = false, paymentResult = "Payment Successful!")
//        //         onResult(TransferResult.Success(message = "Payment Successful!"))
//        //     } else {
//        //         transferUIState = transferUIState.copy(isLoading = false, paymentResult = "Payment Failed.")
//        //         onResult(TransferResult.Error(message = "Payment Failed."))
//        //     }
//        // }
//
//        // For now, let's just simulate a success immediately for the callback
//        // In a real app, this would be asynchronous.
//        transferUIState = transferUIState.copy(isLoading = false, paymentResult = "Payment processed (simulated).")
//        onResult(TransferResult.Success(message = "Payment processed (simulated)."))
//    }
}

