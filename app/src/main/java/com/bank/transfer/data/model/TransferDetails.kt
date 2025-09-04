package com.bank.transfer.data.model

data class TransferDetails(
    val amount: Double,
    val fromAccount: String,
    val toAccount: String
) {

}
