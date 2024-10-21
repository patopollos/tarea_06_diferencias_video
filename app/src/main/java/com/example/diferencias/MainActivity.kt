package com.example.diferencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // Usamos una Surface para definir el fondo de la aplicación
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Llamamos a la pantalla que contiene los composables
                    CounterScreen()
                }

        }
    }
}

class CounterViewModel : ViewModel() {
    // StateFlow para el contador
    private val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> = _counter.asStateFlow()

    // Función para incrementar el contador StateFlow
    fun incrementCounter() {
        _counter.value += 2
    }
}

@Composable
fun CounterScreen(viewModel: CounterViewModel = viewModel()) {
    // Convertimos el StateFlow a un estado composable usando collectAsState
    val counterState by viewModel.counter.collectAsState()

    // Variable gestionada por mutableStateOf
    var localCounter by remember { mutableStateOf(0) }

    Column {
        // Primer composable que muestra el contador del StateFlow
        CounterDisplay1(counter = counterState)

        // Segundo composable que también muestra el mismo contador del StateFlow
        CounterDisplay2(counter = counterState)

        // Botón para incrementar el contador del StateFlow
        Button(onClick = { viewModel.incrementCounter() }) {
            Text(text = "Incrementar Contador StateFlow")
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Tercer composable que muestra el contador gestionado por mutableStateOf
        LocalCounterDisplay(localCounter = localCounter)

        // Botón para incrementar el contador gestionado por mutableStateOf
        Button(onClick = { localCounter++ }) {
            Text(text = "Incrementar Contador Local")
        }
    }
}

@Composable
fun CounterDisplay1(counter: Int) {
    Text(text = "Contador StateFlow 1: $counter", style = MaterialTheme.typography.bodyLarge)
}

@Composable
fun CounterDisplay2(counter: Int) {
    Text(text = "Contador StateFlow 2: $counter", style = MaterialTheme.typography.bodyLarge)
}

@Composable
fun LocalCounterDisplay(localCounter: Int) {
    Text(text = "Contador local con mutableStateOf: $localCounter", style = MaterialTheme.typography.bodyLarge)
}