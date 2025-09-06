package com.bank.transfer.data.repo

import com.bank.transfer.data.model.TransferDetails
import com.bank.transfer.data.model.TransferResult
import com.bank.transfer.data.model.TransferResult.Companion.STATUS_CODE_ERROR
import com.bank.transfer.data.model.TransferResult.Companion.STATUS_CODE_SUCCESS
import com.bank.transfer.domain.BankLog
import com.bank.transfer.domain.repository.TransferRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

const val TAG = "TransferRepositoryImpl"
const val PAYMENT_SUCCESS = "Payment successful!"
const val PAYMENT_FAILED = "Payment failed!"

object TransferRepositoryImpl : TransferRepository {
    override suspend fun domesticTransfer(
        transferDetails: TransferDetails.DomesticTransferDetails
    ): TransferResult {
        try {
            delay(3000)
            val success = Random.nextBoolean()

            if (success) {
                BankLog.v(TAG, PAYMENT_SUCCESS)
                return TransferResult.Success(STATUS_CODE_SUCCESS, message = PAYMENT_SUCCESS)
            } else {
                BankLog.v(TAG, PAYMENT_FAILED)
                return TransferResult.Error(STATUS_CODE_ERROR, message = PAYMENT_FAILED)
            }
        } catch (e: Exception) {
            return TransferResult.Error(
                STATUS_CODE_ERROR,
                e.message ?: TransferResult.DEFAULT_ERROR_MESSAGE
            )
        }
    }

    override suspend fun internationalTransfer(
        transferDetails: TransferDetails.InternationalTransferDetails
    ): TransferResult {
        try {
            delay(3000)
            val success = Random.nextBoolean()

            if (success) {
                BankLog.v(TAG, PAYMENT_SUCCESS)
                return TransferResult.Success(STATUS_CODE_SUCCESS, message = PAYMENT_SUCCESS)
            } else {
                BankLog.v(TAG, PAYMENT_FAILED)
                return TransferResult.Error(STATUS_CODE_ERROR, message = PAYMENT_FAILED)
            }
        } catch (e: Exception) {
            return TransferResult.Error(
                STATUS_CODE_ERROR,
                e.message ?: TransferResult.DEFAULT_ERROR_MESSAGE
            )
        }
    }
}