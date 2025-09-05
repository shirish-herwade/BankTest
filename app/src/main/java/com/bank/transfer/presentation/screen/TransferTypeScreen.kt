package com.bank.transfer.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.presentation.navigation.TransferTypeNavigationEvent
import com.bank.transfer.presentation.viewmodel.TransferTypeViewModel
import com.bank.transfer.ui.theme.PaymentBankTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TransferTypeScreen(
    transferTypeViewModel: TransferTypeViewModel = viewModel(),
    onNavigateToPaymentScreen: (TransferType) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        transferTypeViewModel.navigationEvent.collectLatest { event ->
            when (event) {
                is TransferTypeNavigationEvent.NavigateToPayment -> {
                    onNavigateToPaymentScreen(event.type)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select Transfer Type",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = { transferTypeViewModel.onDomesticTypeSelected() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Domestic Transfer")
        }

        Button(
            onClick = { transferTypeViewModel.onInternationalTypeSelected() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("International Transfer")
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun TransferTypeScreenPreview() {
    PaymentBankTheme {
        TransferTypeScreen(
            transferTypeViewModel = TransferTypeViewModel(),
            onNavigateToPaymentScreen = { type -> println("Preview navigate to payment with type: $type") }
        )
    }
}

