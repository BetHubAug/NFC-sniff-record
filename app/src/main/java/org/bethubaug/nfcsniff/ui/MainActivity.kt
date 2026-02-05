package org.bethubaug.nfcsniff.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import dagger.hilt.android.AndroidEntryPoint
import org.bethubaug.nfcsniff.viewmodel.NfcViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vm: NfcViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Minimal Compose scaffold (replace with actual Compose UI)
            androidx.compose.material.Surface {
                androidx.compose.material.Column {
                    androidx.compose.material.Text("NFC Sniff Record — Modernized")
                    val state = vm.uiState.collectAsState()
                    val isScanning = state.value.isScanning
                    androidx.compose.material.Button(onClick = { vm.setScanningEnabled(!isScanning) }) {
                        androidx.compose.material.Text(if (isScanning) "Stop" else "Start")
                    }
                    androidx.compose.material.Button(onClick = { vm.clearHistory() }) {
                        androidx.compose.material.Text("Clear History")
                    }
                    val last = state.value.lastTag
                    androidx.compose.material.Text("Last tag: ${'$'}{last?.idHex ?: "—"}")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.onResume(this as Activity)
    }

    override fun onPause() {
        vm.onPause(this as Activity)
        super.onPause()
    }
}