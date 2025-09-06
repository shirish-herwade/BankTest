package com.bank.transfer.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bank.transfer.domain.BankLog
import com.bank.transfer.domain.repository.TransferRepository
import com.bank.transfer.presentation.viewmodel.PaymentViewModel

class PaymentViewModelFactory(
    private val transferRepository: TransferRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        BankLog.d("PaymentViewModelFactory", "transferRepository: $transferRepository")

        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PaymentViewModel(transferRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}