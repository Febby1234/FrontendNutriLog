package com.chelsea.nutrilog.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chelsea.nutrilog.components.ErrorMessage
import com.chelsea.nutrilog.components.LoadingIndicator
import com.chelsea.nutrilog.components.NutriLogButton
import com.chelsea.nutrilog.components.NutriLogTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn && !uiState.isLoading) {
            onRegisterSuccess()
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            TopAppBar(
                title = { Text("Register") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Name Field
                NutriLogTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Full Name",
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Email Field
                NutriLogTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Password Field
                NutriLogTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    isPassword = true,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Age Field
                NutriLogTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = "Age",
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Weight Field
                NutriLogTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = "Weight (kg)",
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Height Field
                NutriLogTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = "Height (cm)",
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // Gender Selection
                Text("Gender", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Male", "Female", "Other").forEach { genderOption ->
                        FilterChip(
                            selected = gender == genderOption,
                            onClick = { gender = genderOption },
                            label = { Text(genderOption) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Error Message
                ErrorMessage(uiState.error)
                
                // Register Button
                NutriLogButton(
                    text = "Register",
                    onClick = {
                        if (validateInputs(name, email, password, age, weight, height)) {
                            viewModel.register(
                                email = email,
                                password = password,
                                name = name,
                                age = age.toInt(),
                                weight = weight.toFloat(),
                                height = height.toFloat(),
                                gender = gender
                            )
                        }
                    },
                    isLoading = uiState.isLoading,
                    enabled = validateInputs(name, email, password, age, weight, height),
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }
        
        // Loading Overlay
        if (uiState.isLoading) {
            LoadingIndicator()
        }
    }
}

private fun validateInputs(
    name: String,
    email: String,
    password: String,
    age: String,
    weight: String,
    height: String
): Boolean {
    return name.isNotBlank() && 
           email.isNotBlank() && 
           password.isNotBlank() && 
           age.isNotBlank() && 
           weight.isNotBlank() && 
           height.isNotBlank()
}
