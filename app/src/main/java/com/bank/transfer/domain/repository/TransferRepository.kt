package com.bank.transfer.domain.repository

import com.bank.transfer.data.model.TransferDetails
import com.bank.transfer.data.model.TransferResult

interface TransferRepository {
    fun domesticTransfer(transferDetails: TransferDetails): TransferResult
    fun internationalTransfer(transferDetails: TransferDetails): TransferResult
}