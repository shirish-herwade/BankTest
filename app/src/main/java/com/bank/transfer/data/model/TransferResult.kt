package com.bank.transfer.data.model

sealed class TransferResult {
    data class Success(
        val statusCode: Int,
        val message: String
    ) : TransferResult()

    data class Error(
        val statusCode: Int,
        val message: String
    ) : TransferResult()

    companion object {
        internal const val PAYMENT_SUCCESS = "Payment successful!"
        internal const val PAYMENT_FAILED = "Payment failed!"
        internal const val DEFAULT_ERROR_MESSAGE = "Something went wrong"
        internal const val STATUS_CODE_SUCCESS = 200
        internal const val STATUS_CODE_ERROR = 400
        internal const val CALL_TIMEOUT = 3000L
    }
}
