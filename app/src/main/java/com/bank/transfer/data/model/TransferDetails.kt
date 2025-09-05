package com.bank.transfer.data.model

data class TransferDetails(
    val amount: Double,
    val fromAccount: String,
    val toAccount: String,
    val swiftCode: String,
    val recipientName: String,
    val accountNumber: String,
    val iban: String
) {

}
