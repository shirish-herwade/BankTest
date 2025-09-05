package com.bank.transfer.util


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