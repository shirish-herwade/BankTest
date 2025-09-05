package com.bank.transfer.data.model

data class PaymentUIState(
    val recipientName: String = "",
    val accountNumber: String = "",
    val amount: String = "",
    val iban: String = "",
    val swiftCode: String = "",
    val currentTransferType: TransferType = TransferType.DOMESTIC,
    val recipientNameError: String? = null,
    val accountNumberError: String? = null,
    val amountError: String? = null,
    val ibanError: String? = null,
    val swiftCodeError: String? = null,
    val isLoading: Boolean = false,
    val paymentResult: String? = null
)