package com.bank.transfer.presentation.screen // Your main package

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bank.transfer.navigation.AppNavHost
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
                val paymentViewModel: PaymentViewModel = viewModel()

                AppNavHost(
                    navController = navController,
                    paymentViewModel = paymentViewModel
                )
            }
        }
    }

    private fun avoidScreenShot() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
}

