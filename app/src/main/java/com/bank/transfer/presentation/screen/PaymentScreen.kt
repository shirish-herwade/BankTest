package com.bank.transfer.presentation.screen


import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bank.transfer.data.model.TransferDetails
import com.bank.transfer.data.model.TransferResult
import com.bank.transfer.data.model.TransferType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    transferType: TransferType,
    onSendPayment: (
        transferDetails: TransferDetails,
        onResult: (result: TransferResult) -> Unit,
    ) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pay an account", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // From account
            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("From", fontSize = 12.sp, color = Color.Gray)
                    Spacer(Modifier.height(4.dp))
                    Text("Select account", fontSize = 16.sp)
                }
            }

            // To payee
            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("To", fontSize = 12.sp, color = Color.Gray)
                    Spacer(Modifier.height(4.dp))
                    Text("Add/select payee", fontSize = 16.sp)
                }
            }

            // Amount field
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Amount") },
                placeholder = { Text("£ Enter amount") },
                shape = RoundedCornerShape(12.dp)
            )

            // Payment type
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("What sort of transfer is this?", fontSize = 14.sp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RadioButton(
                        selected = true,
                        onClick = { }
                    )
                    Column {
                        Text("One-off", color = MaterialTheme.colorScheme.primary)
                        Text("13 July 2020", fontSize = 13.sp, color = Color.Gray)
                    }
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = { }) {
                        Text("Change date")
                    }
                }
            }

            // Review button
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4BA3F4))
            ) {
                Text("Review transfer", fontSize = 16.sp, color = Color.White)
            }

            // Important info
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Important", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text(
                    "• Future-dated payments can't be subsequently changed in the app. You'll need to use Online Banking",
                    fontSize = 14.sp
                )
                Text(
                    "• If you're a Wealth customer, please call us to change a future-dated transfer",
                    fontSize = 14.sp
                )
                Text(
                    "• Cash transfers between accounts will take place immediately",
                    fontSize = 14.sp
                )
            }
        }
    }


}


@Preview(showBackground = true, name = "Domestic Transfer Preview")
@Composable
fun DomesticPaymentScreenPreview() {
    MaterialTheme { // Assuming you have a MaterialTheme defined
        PaymentScreen(
            transferType = TransferType.DOMESTIC,
            onSendPayment = TODO()
        ) {
            println("Domestic Payment Details: ")
        }
    }
}

@Preview(showBackground = true, name = "International Transfer Preview")
@Composable
fun InternationalPaymentScreenPreview() {
    MaterialTheme { // Assuming you have a MaterialTheme defined
        PaymentScreen(
            transferType = TransferType.INTERNATIONAL,
            onSendPayment = TODO()
        ) {
            println("International Payment Details: ")
        }
    }
}

