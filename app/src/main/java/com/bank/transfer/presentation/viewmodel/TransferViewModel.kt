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
}

