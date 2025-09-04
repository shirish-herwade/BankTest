package com.bank.transfer.presentation.viewmodel

import androidx.compose.animation.core.copy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.presentation.state.TransferUIState

class TransferViewModel : ViewModel() {

    var transferUIState by mutableStateOf(TransferUIState())
        private set

    fun onRecipientNameChanged(newName: String) {
        transferUIState = transferUIState.copy(
            recipientName = newName,
            recipientNameError = null
        )
    }

    fun onAmountChanged(newAmount: String) {
        transferUIState = transferUIState.copy(
            amount = newAmount,
            amountError = null
        )
    }

    fun onTransferTypeChanged(newType: TransferType) {
        transferUIState = transferUIState.copy(
            currentTransferType = newType,
            // You might want to clear IBAN/SWIFT if switching from INT to DOMESTIC
            iban = if (newType == TransferType.DOMESTIC) "" else transferUIState.iban,
            swiftCode = if (newType == TransferType.DOMESTIC) "" else transferUIState.swiftCode,
            ibanError = null,
            swiftCodeError = null
        )
    }

    fun onSendPaymentClicked() {
        // 1. Validate inputs based on transferUIState
        // e.g., if (transferUIState.recipientName.isBlank()) {
        //          transferUIState = transferUIState.copy(recipientNameError = "Required")
        //          return
        //        }
        // 2. Set isLoading = true
        // transferUIState = transferUIState.copy(isLoading = true)
        // 3. Perform payment logic (e.g., call a use case)
        // 4. Update state with result (success/error message, isLoading = false)
    }

     fun onDomesticClicked() {
         onTransferTypeChanged(TransferType.DOMESTIC)
     }

     fun onInterNationalClicked() {
         onTransferTypeChanged(TransferType.INTERNATIONAL)
     }
}

