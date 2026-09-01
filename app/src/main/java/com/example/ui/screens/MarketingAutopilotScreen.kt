package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.data.model.*
import com.example.ui.components.SectionHeader
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketingAutopilotScreen(
    campaigns: List<Campaign>,
    autopilotConfig: AutopilotConfig,
    auditLogs: List<AuditLog>,
    onToggleCampaign: (String) -> Unit,
    onUpdateBudget: (String, Double) -> Unit,
    onUpdateAutopilot: (AutopilotConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Ad Campaigns", "Autopilot Guardrails", "Audit Logs")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("marketing_autopilot_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Title & Mode Selector
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "AI Marketing & Autopilot System",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Autopilot Mode Picker (Assist, Semi-Auto, Full Autopilot)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Automation Operating Mode:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapGold
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val modes = listOf(
                                AutomationMode.ASSIST to "Assist (Manual)",
                                AutomationMode.SEMI_AUTO to "Semi-Auto",
                                AutomationMode.AUTOPILOT to "Full Autopilot"
                            )

                            modes.forEach { (mode, label) ->
                                val isSelected = autopilotConfig.mode == mode
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onUpdateAutopilot(autopilotConfig.copy(mode = mode)) },
                                    color = if (isSelected) SnapGold else MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Tabs
        item {
            ScrollableTabRow(
                selectedTabIndex = activeTab,
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                contentColor = SnapGold,
                indicator = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = activeTab == index
                    Tab(
                        selected = isSelected,
                        onClick = { activeTab = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (isSelected) SnapGold else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }
        }

        // Tab 0: Campaigns
        if (activeTab == 0) {
            items(campaigns) { camp ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
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
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                Surface(shape = RoundedCornerShape(6.dp), color = SnapVioletContainer.copy(alpha = 0.4f)) {
                                    Text(
                                        text = camp.channel.name,
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = SnapViolet,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = camp.name,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            StatusBadge(status = camp.status.name)
                        }

                        // Metrics Matrix
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("ROAS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${String.format("%.2f", camp.roas)}x", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                            }
                            Column {
                                Text("Revenue", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("$${camp.revenueUSD.toInt()}", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                            }
                            Column {
                                Text("Spend", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("$${camp.spendUSD.toInt()}", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                            }
                            Column {
                                Text("Daily Budget", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("$${camp.dailyBudgetUSD.toInt()}/d", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                            }
                        }

                        if (camp.aiOptimizationNotes.isNotEmpty()) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SnapGold, modifier = Modifier.size(16.dp))
                                    Text(
                                        text = camp.aiOptimizationNotes,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { onUpdateBudget(camp.id, camp.dailyBudgetUSD + 25.0) },
                                colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Scale Budget (+ $25)", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                            }

                            OutlinedButton(
                                onClick = { onToggleCampaign(camp.id) },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(if (camp.status == CampaignStatus.ACTIVE) "Pause Ad" else "Resume Ad")
                            }
                        }
                    }
                }
            }
        }

        // Tab 1: Guardrails
        if (activeTab == 1) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Safety Limits & Merchant Guardrails",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapGold
                        )
                        Text(
                            text = "The AI virtual team operates strictly within your pre-approved financial boundaries.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text("Max Daily Ad Spend: $${autopilotConfig.maxDailyAdSpendUSD.toInt()}", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        Slider(
                            value = autopilotConfig.maxDailyAdSpendUSD.toFloat(),
                            onValueChange = { onUpdateAutopilot(autopilotConfig.copy(maxDailyAdSpendUSD = it.toDouble())) },
                            valueRange = 100f..2000f,
                            steps = 18,
                            colors = SliderDefaults.colors(thumbColor = SnapGold, activeTrackColor = SnapGold)
                        )

                        Text("Minimum Net Profit Margin: ${autopilotConfig.minProfitMarginPct.toInt()}%", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        Slider(
                            value = autopilotConfig.minProfitMarginPct.toFloat(),
                            onValueChange = { onUpdateAutopilot(autopilotConfig.copy(minProfitMarginPct = it.toDouble())) },
                            valueRange = 20f..60f,
                            steps = 8,
                            colors = SliderDefaults.colors(thumbColor = SnapEmerald, activeTrackColor = SnapEmerald)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Require approval for budget scale > $50", style = MaterialTheme.typography.bodyMedium)
                            Switch(
                                checked = autopilotConfig.requireHumanApprovalForAdSpend,
                                onCheckedChange = { onUpdateAutopilot(autopilotConfig.copy(requireHumanApprovalForAdSpend = it)) }
                            )
                        }
                    }
                }
            }
        }

        // Tab 2: Audit Logs
        if (activeTab == 2) {
            items(auditLogs) { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(log.agentName, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                            Text(log.timestamp, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Text(log.action, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                        Text("Reason: ${log.reason}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Result: ${log.result}", style = MaterialTheme.typography.bodySmall, color = SnapEmerald)
                    }
                }
            }
        }
    }
}
