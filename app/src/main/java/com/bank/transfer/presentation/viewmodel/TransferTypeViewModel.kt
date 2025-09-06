package com.bank.transfer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.domain.BankLog
import com.bank.transfer.presentation.navigation.TransferTypeNavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class TransferTypeViewModel : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<TransferTypeNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()
    val TAG = "TransferTypeViewModel"
    internal fun onDomesticTypeSelected() {
        BankLog.v(TAG,"in onDomesticTypeSelected. before launch")
        viewModelScope.launch {
            BankLog.v(TAG,"in onDomesticTypeSelected. in launch")
            _navigationEvent.emit(
                TransferTypeNavigationEvent
                    .NavigateToPayment(TransferType.DOMESTIC)
            )
            BankLog.v(TAG,"in onDomesticTypeSelected. after emit")
        }
    }

    internal fun onInternationalTypeSelected() {
        viewModelScope.launch {
            _navigationEvent.emit(
                TransferTypeNavigationEvent
                    .NavigateToPayment(TransferType.INTERNATIONAL)
            )
        }
    }
}

