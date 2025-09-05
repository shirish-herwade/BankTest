package com.bank.transfer.domain.model

data class Account(
    private val id: String,
    private val holderName: String,
    private val accNumber: String,
    private val balance: Double,
    private val ifscCode: String,
)
