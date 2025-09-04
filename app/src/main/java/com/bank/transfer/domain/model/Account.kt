package com.bank.transfer.domain.model

data class Account(
    private val id: String,
    private val holderName: String,
    private val number: String,
    private val balance: Double
)
