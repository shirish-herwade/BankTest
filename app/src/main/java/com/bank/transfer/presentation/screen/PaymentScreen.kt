package com.bank.transfer.presentation.screen

// ... other imports ...
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.presentation.viewmodel.PaymentViewModel
import com.bank.transfer.ui.theme.PaymentBankTheme

@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    transferType: TransferType,
    onSendPayment: () -> Unit,
    onBack: () -> Unit
) {
    val paymentViewModel = PaymentViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        transferType.name
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF9CB9E8)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
//                    .padding(innerPadding)
//                    .padding(16.dp), // Padding for the outer box content
                contentAlignment = Alignment.TopCenter // Align content to the top center
            ) {
                Surface( // Use Surface for elevation, shape, and color
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp)), // Optional: Add rounded corners
                    color = Color.White.copy(alpha = 0.8f), // Light white color, adjust alpha for transparency
                    shadowElevation = 4.dp // Optional: Add a subtle shadow
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .verticalScroll(rememberScrollState())
                            .padding(16.dp), // Padding inside the Surface for the TextFields
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        OutlinedTextField(
                            value = paymentViewModel.uiState.value.recipientName,
                            onValueChange = paymentViewModel::onRecipientNameChanged,
                            label = { Text("Recipient Name") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = paymentViewModel.uiState.value.recipientNameError != null
                        )
                        val recipientNameError = paymentViewModel.uiState.value.recipientNameError
                        if (recipientNameError != null) {
                            Text(
                                text = recipientNameError,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        OutlinedTextField(
                            value = paymentViewModel.uiState.value.accountNumber,
                            onValueChange = paymentViewModel::onAccountNumberChanged,
                            label = { Text("Account Number") },
                            modifier = Modifier.fillMaxWidth(),
//                    .focusRequester(focusRequester),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = paymentViewModel.uiState.value.accountNumberError != null
                        )
                        val accountNumberError = paymentViewModel.uiState.value.accountNumberError
                        if (accountNumberError != null) {
                            Text(
                                text = accountNumberError,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        OutlinedTextField(
                            value = paymentViewModel.uiState.value.amount,
                            onValueChange = paymentViewModel::onAmountChanged,
                            label = { Text("Amount") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            isError = paymentViewModel.uiState.value.amountError != null
                        )
                        val amountError = paymentViewModel.uiState.value.amountError
                        if (amountError != null) {
                            Text(
                                text = amountError,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        if (transferType == TransferType.INTERNATIONAL) {
                            OutlinedTextField(
                                value = paymentViewModel.uiState.value.iban,
                                onValueChange = paymentViewModel::onIbanChanged,
                                label = { Text("IBAN") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = paymentViewModel.uiState.value.ibanError != null
                            )
                            val ibanError = paymentViewModel.uiState.value.ibanError
                            if (ibanError != null) {
                                Text(
                                    text = ibanError,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            OutlinedTextField(
                                value = paymentViewModel.uiState.value.swiftCode,
                                onValueChange = paymentViewModel::onSwiftCodeChanged,
                                label = { Text("SWIFT Code") },
                                modifier = Modifier.fillMaxWidth(),
                                isError = paymentViewModel.uiState.value.swiftCodeError != null
                            )
                            val swiftCodeError = paymentViewModel.uiState.value.swiftCodeError
                            if (swiftCodeError != null) {
                                Text(
                                    text = swiftCodeError,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            if (paymentViewModel.uiState.value.isLoading) {
                Log.v("PaymentScreen", "in if Loading...")
                CircularProgressIndicator(
                    strokeWidth = 8.dp,
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Log.v("PaymentScreen", "in else...")

                Button(
                    onClick = { paymentViewModel.sendPayment() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Send Payment")
                }
            }

            paymentViewModel.uiState.value.paymentResult?.let {
                Spacer(Modifier.height(16.dp))
                Text(
                    it, color = if (it.contains("Success"))
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Domestic Payment Preview")
@Preview
@Composable
fun PaymentScreenPreviewDomestic() {
    PaymentBankTheme {
        PaymentScreen(
            transferType = TransferType.DOMESTIC,
            onSendPayment = {},
            onBack = {}
        )
    }
}

