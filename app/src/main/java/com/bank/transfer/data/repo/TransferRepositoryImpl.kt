package com.bank.transfer.data.repo

import com.bank.transfer.data.model.TransferDetails
import com.bank.transfer.data.model.TransferResult
import com.bank.transfer.domain.repository.TransferRepository
import kotlinx.coroutines.delay

class TransferRepositoryImpl(private val api: TransferApiService) : TransferRepository {
    override fun domesticTransfer(transferDetails: TransferDetails): TransferResult {
        return try {
            val response = TransferResult.getDummySuccessResult()
            if (response is TransferResult.Success) {
                response
            } else { //if (response is TransferResult.Error) {
                TransferResult.Error(
                    statusCode = 400,
                    message = response.message ?: TransferResult.DEFAULT_ERROR_MESSAGE
                )
            }
        } catch (e: Exception) {
            TransferResult.Error(
                statusCode = 400,
                message = e.message ?: TransferResult.DEFAULT_ERROR_MESSAGE
            )
        }
    }

    override fun internationalTransfer(transferDetails: TransferDetails): TransferResult {
        TODO("Not yet implemented")
    }
}