package com.bank.transfer.presentation.navigation

import com.bank.transfer.data.model.TransferType

sealed class TransferTypeNavigationEvent {
    data class NavigateToPayment(
        val type: TransferType
    ) : TransferTypeNavigationEvent()
}