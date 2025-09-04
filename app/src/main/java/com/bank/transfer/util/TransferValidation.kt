package com.bank.transfer.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

//var amount by remember { mutableStateOf("") }
//var amountError by remember { mutableStateOf<String?>(null) }

fun validateAmount(value: String): String? {
    if (value.isBlank()) return "Amount cannot be empty."
    val decimalValue = value.toDoubleOrNull()
    if (decimalValue == null) return "Invalid amount format."
    if (decimalValue <= 0) return "Amount must be positive."
    // Add more checks: max limit, decimal places, etc.
    return null
}
//
//OutlinedTextField(
//value = amount,
//onValueChange = {
//    amount = it
//    amountError = validateAmount(it)
//},
//label = { Text("Amount") },
//isError = amountError != null,
//// ...
//)
//if (amountError != null) {
//    Text(amountError!!, color = MaterialTheme.colorScheme.error)
//}