package com.bank.transfer.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bank.transfer.presentation.screen.PaymentScreen
import com.bank.transfer.presentation.screen.TransferTypeScreen
import com.bank.transfer.presentation.viewmodel.PaymentViewModel
import com.bank.transfer.presentation.viewmodel.TransferTypeViewModel

object AppDestinations {
    const val TRANSFER_TYPE_ROUTE = "transfer_type"
    const val PAYMENT_ROUTE = "payment"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    paymentViewModel: PaymentViewModel
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.TRANSFER_TYPE_ROUTE
    ) {
        composable(AppDestinations.TRANSFER_TYPE_ROUTE) {
            val transferTypeViewModel: TransferTypeViewModel = viewModel()
            TransferTypeScreen(
                transferTypeViewModel = transferTypeViewModel,
                onNavigateToPaymentScreen = { selectedType ->
                    paymentViewModel.setTransferType(selectedType)
                    navController.navigate(AppDestinations.PAYMENT_ROUTE)
                }
            )
        }

        composable(AppDestinations.PAYMENT_ROUTE) {
            PaymentScreen(
                transferType = paymentViewModel.uiState.value.currentTransferType,
                onSendPayment = {
                    paymentViewModel::sendPayment
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
