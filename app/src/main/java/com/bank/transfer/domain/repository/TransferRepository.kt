package com.bank.transfer.domain.repository

import com.bank.transfer.data.model.TransferDetails
import com.bank.transfer.data.model.TransferResult
import com.bank.transfer.data.repo.TransferRepositoryImpl

interface TransferRepository {
    suspend fun domesticTransfer(
        transferDetails: TransferDetails.DomesticTransferDetails
    ): TransferResult

    suspend fun internationalTransfer(
        transferDetails: TransferDetails.InternationalTransferDetails
    ): TransferResult

    companion object {
        fun create(): TransferRepository {
            return TransferRepositoryImpl
        }
    }
}