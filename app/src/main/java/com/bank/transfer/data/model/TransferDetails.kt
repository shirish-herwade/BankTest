package com.bank.transfer.data.model

sealed class TransferDetails {
    data class DomesticTransferDetails(
        val recipientName: String,
        val accountNumber: String,
        val amount: Double,
    )

    data class InternationalTransferDetails(
        val recipientName: String,
        val accountNumber: String,
        val amount: Double,
        val iban: String,
        val swiftCode: String,
    )
}