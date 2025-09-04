package com.bank.transfer.data.model

sealed class TransferResult {
    data class Success(
        val statusCode: Int = 222,
        val message: String
    ) : TransferResult()

    data class Error(
        val statusCode: Int = 420,
        val message: String
    ) : TransferResult()

    object Loading : TransferResult()

    fun getResponseMessage(): String {
        return when (this) {
            is Success -> message
            is Error -> message
            Loading -> TODO()
        }
    }

    companion object {
        internal const val DEFAULT_ERROR_MESSAGE = "Something went wrong"

        fun getDummySuccessResult(): Success {
            return TransferResult.Success(
                200,
                "Payment sent successfully"
            )
        }
    }
}
