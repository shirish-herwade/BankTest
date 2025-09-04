package com.bank.transfer.presentation.state

import com.bank.transfer.data.model.TransferType
import com.bank.transfer.domain.model.Account
import java.util.Date

data class TransferUIState(
    val recipientName: String = "",
    val accountNumber: String = "",
    val amount: String = "",
    val iban: String = "", // Only relevant for international
    val swiftCode: String = "", // Only relevant for international
    val currentTransferType: TransferType = TransferType.DOMESTIC,
    val recipientNameError: String? = null,
    val accountNumberError: String? = null,
    val amountError: String? = null,
    val ibanError: String? = null,
    val swiftCodeError: String? = null,
    val isLoading: Boolean = false,
    val paymentResult: String? = null
)