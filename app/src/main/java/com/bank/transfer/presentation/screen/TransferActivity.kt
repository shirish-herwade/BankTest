package com.bank.transfer.presentation.screen

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bank.transfer.domain.repository.TransferRepository
import com.bank.transfer.presentation.factory.PaymentViewModelFactory
import com.bank.transfer.presentation.navigation.AppNavHost
import com.bank.transfer.presentation.viewmodel.PaymentViewModel
import com.bank.transfer.ui.theme.PaymentBankTheme

class TransferActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        avoidScreenShot()

        setContent {
            PaymentBankTheme {
                val navController = rememberNavController()
                val paymentViewModel = getPaymentViewModel()

                AppNavHost(
                    navController = navController,
                    paymentViewModel = paymentViewModel
                )
            }
        }
    }

    @Composable
    private fun getPaymentViewModel(): PaymentViewModel {
        val repository: TransferRepository = TransferRepository.create()
        val paymentViewModelFactory = PaymentViewModelFactory(repository)
        return viewModel(factory = paymentViewModelFactory)
    }

    private fun avoidScreenShot() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
}

