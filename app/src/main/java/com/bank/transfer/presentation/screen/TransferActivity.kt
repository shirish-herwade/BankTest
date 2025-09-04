package com.bank.transfer.presentation.screen

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue // Ensure this import
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue // Ensure this import
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bank.transfer.data.model.TransferDetails
import com.bank.transfer.data.model.TransferResult
import com.bank.transfer.data.model.TransferType
import com.bank.transfer.ui.theme.PaymentBankTheme

class TransferActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        avoidScreenShot()
        
        setContent {
            PaymentBankTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> 
                    var currentTransferType by remember { mutableStateOf(TransferType.DOMESTIC) }

                    Column(
                        modifier = Modifier 
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier 
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(onClick = { currentTransferType = TransferType.DOMESTIC }) {
                                Text("Domestic")
                            }
                            Spacer(Modifier.width(8.dp)) 
                            Button(onClick = { currentTransferType = TransferType.INTERNATIONAL }) {
                                Text("International")
                            }
                        }

                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )

                        PaymentScreen(
                            transferType = currentTransferType,
                            onSendPayment = { details: TransferDetails, function: (TransferResult) -> Unit ->
                                Log.d("PaymentApp", "Payment Details: ")
                            } ,
                            onBack = { /* TODO: Implement onBack */ } // Added a lambda for onBack
                        )
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        PaymentBankTheme {
            PaymentScreen(
                transferType = TODO(),
                onSendPayment = TODO(),
                onBack = TODO()
            ) 
        }
    }

    fun addAppLauncherShortcut(context: Context) {
        val shortcutManager = context.getSystemService(ShortcutManager::class.java)

        if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported) {
            val shortcut = ShortcutInfo.Builder(context, "app_copy_launcher")
                .setShortLabel("My App Copy")
                .setLongLabel("Open My App (Copy)")
//                .setIcon(resources.) // use your launcher icon
                .setIntent(
                    Intent(context, TransferActivity::class.java).apply {
                        action = Intent.ACTION_VIEW
                    }
                )
                .build()

            // Ask the launcher to pin the shortcut
            shortcutManager.requestPinShortcut(shortcut, null)
        }
    }

    fun avoidScreenShot(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
}
