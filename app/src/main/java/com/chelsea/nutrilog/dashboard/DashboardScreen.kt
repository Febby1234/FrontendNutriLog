package com.chelsea.nutrilog.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chelsea.nutrilog.components.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedDays by remember { mutableStateOf(7) }

    LaunchedEffect(Unit) {
        viewModel.refreshDashboard()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            TopAppBar(
                title = { Text("Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White
                )
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                uiState.summary?.let { summary ->
                    // 1. TODAY'S CALORIE CARD WITH PROGRESS
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF1F8E9)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "Today's Calorie Intake",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                                Text(
                                    "${summary.todayCalories.toInt()} kcal",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32),
                                    fontSize = 28.sp
                                )
                                Text(
                                    "Target: ${summary.targetCalories.toInt()} kcal",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF558B2F),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Text(
                                    "Remaining: ${(summary.targetCalories - summary.todayCalories).toInt()} kcal",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF9CCC65),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            
                            // Circular Progress Indicator
                            Box(
                                modifier = Modifier.size(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    progress = { 
                                        (summary.todayCalories / summary.targetCalories)
                                            .toFloat()
                                            .coerceIn(0f, 1f) 
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    color = Color(0xFF4CAF50),
                                    trackColor = Color.LightGray,
                                    strokeWidth = 4.dp
                                )
                                
                                Text(
                                    "${((summary.todayCalories / summary.targetCalories).toFloat().coerceIn(0f, 1f) * 100).toInt()}%",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32)
                                )
                            }
                        }
                    }
                    
                    // 2. MACRONUTRIENT BREAKDOWN CARD
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Macronutrient Breakdown",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Protein
                                MacroNutrientItem(
                                    label = "Protein",
                                    value = "${summary.macronutrients.protein.toInt()}g",
                                    color = Color(0xFFFF7043),
                                    modifier = Modifier.weight(1f)
                                )
                                
                                // Fat
                                MacroNutrientItem(
                                    label = "Fat",
                                    value = "${summary.macronutrients.fat.toInt()}g",
                                    color = Color(0xFFFFCA28),
                                    modifier = Modifier.weight(1f)
                                )
                                
                                // Carbs
                                MacroNutrientItem(
                                    label = "Carbs",
                                    value = "${summary.macronutrients.carbs.toInt()}g",
                                    color = Color(0xFF66BB6A),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                    
                    // 3. BMR AND HEALTH PROFILE CARD
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // BMR Card
                        Card(
                            modifier = Modifier
                                .weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE8F5E9)
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "BMR",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                                Text(
                                    "${summary.bmr.toInt()}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4CAF50)
                                )
                                Text(
                                    "kcal/day",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }
                        }
                        
                        // Remaining Card
                        Card(
                            modifier = Modifier
                                .weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFF3E0)
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Remaining",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                                Text(
                                    "${(summary.targetCalories - summary.todayCalories).toInt()}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFF9800)
                                )
                                Text(
                                    "kcal",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                    
                    // 4. HEALTH PROFILE CARD
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Your Profile",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            
                            val profile = summary.healthProfile
                            ProfileRow("Age", "${profile.age} years", Color(0xFF4CAF50))
                            ProfileRow("Gender", profile.gender, Color(0xFF2196F3))
                            ProfileRow("Weight", "${profile.weightKg} kg", Color(0xFFFF9800))
                            ProfileRow("Height", "${profile.heightCm} cm", Color(0xFF9C27B0))
                        }
                    }
                    
                    // 5. HISTORY FILTER
                    Text(
                        "Calorie History & Trends",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp, top = 8.dp)
                    )
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(7, 14, 30).forEach { days ->
                            FilterChip(
                                selected = selectedDays == days,
                                onClick = {
                                    selectedDays = days
                                    viewModel.loadNutritionDataForDays(days)
                                },
                                label = { Text("$days days") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    
                    // 6. NUTRITION DATA LIST
                    if (uiState.nutritionData.isNotEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Recent Days",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                                
                                uiState.nutritionData.take(7).forEach { data ->
                                    NutritionDataRow(data)
                                    Divider(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        color = Color.LightGray
                                    )
                                }
                            }
                        }
                    }
                    
                    // 7. TODAY'S FOOD ITEMS
                    if (uiState.summary?.todayItems?.isNotEmpty() == true) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Today's Food",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                                
                                uiState.summary?.todayItems?.forEach { item ->
                                    FoodItemRow(item)
                                    Divider(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        color = Color.LightGray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Loading Overlay
        if (uiState.isLoading) {
            LoadingIndicator()
        }
    }
}

@Composable
fun ProfileRow(label: String, value: String, color: Color = Color.Gray) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
        Row(
            modifier = Modifier
                .background(color = color.copy(alpha = 0.1f), shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(value, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun MacroNutrientItem(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = color.copy(alpha = 0.1f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = color, shape = androidx.compose.foundation.shape.CircleShape)
        )
        
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        Text(
            value,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun NutritionDataRow(data: com.chelsea.nutrilog.dashboard.models.NutritionDataPoint) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                data.date,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "P: ${data.protein.toInt()}g | F: ${data.fat.toInt()}g | C: ${data.carbs.toInt()}g",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Text(
            "${data.calories.toInt()} kcal",
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF4CAF50),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FoodItemRow(item: com.chelsea.nutrilog.dashboard.models.FoodItemDTO) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                item.name,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            // Mengambil data porsi (serving) asli dari database
            Text(
                "${item.servingQuantity} serving(s)",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        // Mengambil data total kalori konsumsi asli dari database
        Text(
            "${item.consumedCalories.toInt()} kcal",
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF4CAF50),
            fontWeight = FontWeight.Bold
        )
    }
}
