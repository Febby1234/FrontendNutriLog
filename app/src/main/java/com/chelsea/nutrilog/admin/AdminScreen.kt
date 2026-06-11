package com.chelsea.nutrilog.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chelsea.nutrilog.components.LoadingIndicator
import com.chelsea.nutrilog.foodLog.models.FoodProposal

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AdminScreen(
    viewModel: AdminViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            viewModel.clearMessages()
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            TopAppBar(
                title = { Text("Admin - Food Proposals") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White
                )
            )
            
            // Content
            if (uiState.pendingProposals.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No pending proposals",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(uiState.pendingProposals) { proposal ->
                        ProposalCard(
                            proposal = proposal,
                            onApprove = { viewModel.approveProposal(proposal.proposalId) },
                            onReject = { viewModel.rejectProposal(proposal.proposalId) },
                            isLoading = uiState.isLoading
                        )
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
fun ProposalCard(
    proposal: FoodProposal,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    isLoading: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                proposal.proposedFoodName,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            Text(
                "${proposal.proposedCalories.toInt()} kcal per serving",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                "Status: ${proposal.status}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onApprove,
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32)
                    )
                ) {
                    Text("Approve", color = Color.White)
                }
                
                OutlinedButton(
                    onClick = onReject,
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Reject", color = Color.Red)
                }
            }
        }
    }
}
