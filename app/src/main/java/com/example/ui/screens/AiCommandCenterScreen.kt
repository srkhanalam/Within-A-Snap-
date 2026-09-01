package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.ChatMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiCommandCenterScreen(
    chatHistory: List<ChatMessage>,
    commandInput: String,
    isAiThinking: Boolean,
    onInputChange: (String) -> Unit,
    onExecuteCommand: (String?) -> Unit,
    onApproveRecommendation: (String) -> Unit,
    onRejectRecommendation: (String) -> Unit,
    onSelectProductForLaunch: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val quickPrompts = listOf(
        "🚀 Find me a profitable fitness product for UAE and prepare it for launch.",
        "🔥 Which product should I launch this week?",
        "💰 Find products with at least 40% profit margin.",
        "🚚 Compare suppliers and find fastest shipping.",
        "📊 Why did my profit change this week?",
        "🌍 Which country should I expand into next?",
        "⚡ Set best selling price across India, UAE & USA."
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("ai_command_center_screen")
    ) {
        // Top Command Center Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(SnapGoldContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Hub, contentDescription = null, tint = SnapGold, modifier = Modifier.size(20.dp))
                    }
                    Column {
                        Text(
                            text = "AI Command Center",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "10 Autonomous Agents Online",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                            color = SnapEmerald
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SnapVioletContainer.copy(alpha = 0.4f),
                    border = BorderStroke(1.dp, SnapViolet.copy(alpha = 0.6f))
                ) {
                    Text(
                        text = "Orchestrator v2.6",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = SnapViolet,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Quick Prompt Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickPrompts) { prompt ->
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onExecuteCommand(prompt) },
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = prompt,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Chat & Structured Output Feed
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(chatHistory) { msg ->
                if (msg.isUser) {
                    // User Message Bubble
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp, 16.dp, 4.dp, 16.dp),
                            color = SnapGold,
                            modifier = Modifier.widthIn(max = 300.dp)
                        ) {
                            Text(
                                text = msg.text,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                color = Color.Black,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                } else {
                    // AI Response Card
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SnapGold, modifier = Modifier.size(16.dp))
                            Text(
                                text = msg.agentResult?.agentName ?: "Within A Snap AI Team",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapGold
                            )
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = msg.text,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                // Structured Data Matrix if available
                                if (msg.agentResult?.structuredData?.isNotEmpty() == true) {
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = MaterialTheme.colorScheme.surface,
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(12.dp),
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            msg.agentResult.structuredData.forEach { (key, value) ->
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(text = key, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                                    Text(text = value, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                                                }
                                            }
                                        }
                                    }
                                }

                                // Interactive Action Recommendation Card
                                if (msg.agentResult?.recommendation != null) {
                                    val rec = msg.agentResult.recommendation
                                    AiRecommendationCard(
                                        recommendation = rec,
                                        onApprove = { onApproveRecommendation(rec.id) },
                                        onReject = { onRejectRecommendation(rec.id) }
                                    )
                                }

                                // Multi-step workflow 1-Click Launch button
                                if (msg.agentResult?.multiStepWorkflowProduct != null) {
                                    val product = msg.agentResult.multiStepWorkflowProduct
                                    Button(
                                        onClick = { onSelectProductForLaunch(product) },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(Icons.Default.RocketLaunch, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Open Full 1-Click Launch Studio", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (isAiThinking) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = SnapGold,
                            strokeWidth = 2.dp
                        )
                        Text(
                            text = "AI Agents researching marketplace data & calculating margins...",
                            style = MaterialTheme.typography.bodySmall,
                            color = SnapGold
                        )
                    }
                }
            }
        }

        // Bottom Command Input Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = commandInput,
                    onValueChange = onInputChange,
                    placeholder = { Text("Ask your AI virtual team (e.g. 'Find trending UAE product')") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("ai_command_input"),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SnapGold,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    maxLines = 2
                )

                IconButton(
                    onClick = { onExecuteCommand(null) },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (commandInput.isNotBlank()) SnapGold else MaterialTheme.colorScheme.surface)
                        .testTag("ai_command_send_btn"),
                    enabled = commandInput.isNotBlank() && !isAiThinking
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = if (commandInput.isNotBlank()) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
