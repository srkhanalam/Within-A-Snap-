package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import com.example.data.model.BroadcastCampaignItem
import com.example.data.model.BroadcastChannel
import com.example.data.model.CustomerSegmentMetric
import com.example.ui.theme.*

@Composable
fun BroadcastCampaignScreen(
    broadcastCampaigns: List<BroadcastCampaignItem>,
    customerSegments: List<CustomerSegmentMetric>,
    onDispatchCampaign: (String, BroadcastChannel, String, String, Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showComposerDialog by remember { mutableStateOf(false) }
    var campaignTitle by remember { mutableStateOf("Flash Festive 25% Off VIP Drop") }
    var selectedChannel by remember { mutableStateOf(BroadcastChannel.WHATSAPP) }
    var selectedSegment by remember { mutableStateOf(customerSegments.firstOrNull()?.name ?: "All Customers") }
    var promoCode by remember { mutableStateOf("SNAPVIP25") }
    var discountPct by remember { mutableIntStateOf(25) }
    var templateText by remember { mutableStateOf("Hey {FirstName}! 👋 25% Off VIP Drop is LIVE for the next 48h! Use code {PromoCode}: {StoreLink}") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("broadcast_campaign_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131826)),
                border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(SnapCyan.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Campaign, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(20.dp))
                            }
                            Column {
                                Text(
                                    "SMS, Email & WhatsApp VIP Broadcast Center",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    "VIP Segmentation • Abandoned Cart Push • Instant Flash Sales",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Button(
                            onClick = { showComposerDialog = true },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SnapCyan),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Black)
                            Spacer(Modifier.width(4.dp))
                            Text("New Broadcast", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = Color.Black)
                        }
                    }
                }
            }
        }

        // Customer Segments Horizon
        item {
            Text("Audience Segmentation & VIP Cohorts", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                customerSegments.forEach { seg ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        border = BorderStroke(1.dp, Color(0xFF1E2838))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(seg.iconEmoji, style = MaterialTheme.typography.titleLarge)
                                Column {
                                    Text(seg.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                                    Text("Recommended: ${seg.recommendedPromo}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text("${seg.count} Buyers", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = SnapCyan)
                                Text("Avg LTV $${String.format("%.1f", seg.avgLtvUSD)}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = SnapEmerald)
                            }
                        }
                    }
                }
            }
        }

        // Broadcast History Header
        item {
            Text("Broadcast Campaign Execution History", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
        }

        // Broadcast Campaigns List
        items(broadcastCampaigns) { campaign ->
            val channelColor = when (campaign.channel) {
                BroadcastChannel.WHATSAPP -> SnapEmerald
                BroadcastChannel.SMS -> SnapGold
                BroadcastChannel.EMAIL -> SnapCyan
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, channelColor.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = channelColor.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, channelColor.copy(alpha = 0.4f))
                        ) {
                            Text(
                                "${campaign.channel.name} BROADCAST",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                color = channelColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            campaign.sentTimestamp,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        campaign.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    // Template Box
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF0F1420),
                        border = BorderStroke(1.dp, Color(0xFF1E2838))
                    ) {
                        Text(
                            "“${campaign.messageTemplate}”",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    // Metrics Strip
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF151D2C),
                        border = BorderStroke(1.dp, Color(0xFF222C40))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Audience:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${campaign.recipientCount} Recipients", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                            }
                            Column {
                                Text("Open Rate:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${campaign.openRatePct}%", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Recovered GMV:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("+$${String.format("%.0f", campaign.recoveredRevenueUSD)}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Black), color = SnapGold)
                            }
                        }
                    }
                }
            }
        }
    }

    // Composer Dialog
    if (showComposerDialog) {
        AlertDialog(
            onDismissRequest = { showComposerDialog = false },
            title = { Text("Compose Targeted VIP Broadcast", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = campaignTitle,
                        onValueChange = { campaignTitle = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Campaign Headline") },
                        singleLine = true
                    )

                    Text("Delivery Channel:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        BroadcastChannel.values().forEach { ch ->
                            FilterChip(
                                selected = selectedChannel == ch,
                                onClick = { selectedChannel = ch },
                                label = { Text(ch.name) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = promoCode,
                        onValueChange = { promoCode = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Promo Code") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = templateText,
                        onValueChange = { templateText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Message Copy Template") },
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDispatchCampaign(campaignTitle, selectedChannel, selectedSegment, promoCode, discountPct, templateText)
                        showComposerDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapEmerald)
                ) {
                    Text("Instant Broadcast Push", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showComposerDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
