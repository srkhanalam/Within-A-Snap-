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
import com.example.data.model.AdSpendAttribution
import com.example.ui.theme.*

@Composable
fun RoasAttributionScreen(
    adAttributions: List<AdSpendAttribution>,
    modifier: Modifier = Modifier
) {
    // Dynamic Breakeven Calculator State
    var sellingPriceInput by remember { mutableStateOf("34.99") }
    var cogsInput by remember { mutableStateOf("9.50") }
    var shippingInput by remember { mutableStateOf("3.50") }
    var gatewayFeeInput by remember { mutableStateOf("1.25") }

    val calculatedBreakevenRoas = remember(sellingPriceInput, cogsInput, shippingInput, gatewayFeeInput) {
        val sp = sellingPriceInput.toDoubleOrNull() ?: 34.99
        val cogs = cogsInput.toDoubleOrNull() ?: 9.50
        val ship = shippingInput.toDoubleOrNull() ?: 3.50
        val fee = gatewayFeeInput.toDoubleOrNull() ?: 1.25
        val netMargin = (sp - cogs - ship - fee).coerceAtLeast(0.01)
        if (netMargin > 0) sp / netMargin else 2.5
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("roas_attribution_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131826)),
                border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.5f))
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
                                    .background(SnapEmerald.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(20.dp))
                            }
                            Column {
                                Text(
                                    "True Net Profit & ROAS Attribution",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    "Meta, TikTok & Google Ad Sync • Gateway & COGS Deducted",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SnapEmerald.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.4f))
                        ) {
                            Text(
                                "3.42x Blended ROAS",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapEmerald,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Aggregated Profit Stats
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF0F1420),
                            border = BorderStroke(1.dp, Color(0xFF1E2838))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Total Ad Spend", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("$3,300", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = SnapRose)
                            }
                        }
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF0F1420),
                            border = BorderStroke(1.dp, Color(0xFF1E2838))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Gross Ad Revenue", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("$11,200", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = SnapGold)
                            }
                        }
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF0F1420),
                            border = BorderStroke(1.dp, Color(0xFF1E2838))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("True Net Profit", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("+$3,514", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = SnapEmerald)
                            }
                        }
                    }
                }
            }
        }

        // Live Breakeven ROAS Calculator Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF10192A)),
                border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.4f))
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
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Calculate, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(18.dp))
                            Text("Interactive Breakeven ROAS Calculator", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SnapCyan.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.4f))
                        ) {
                            Text(
                                "Min ROAS: ${String.format("%.2f", calculatedBreakevenRoas)}x",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                color = SnapCyan,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    // Inputs Row
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = sellingPriceInput,
                            onValueChange = { sellingPriceInput = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Price ($)") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = cogsInput,
                            onValueChange = { cogsInput = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("COGS ($)") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = shippingInput,
                            onValueChange = { shippingInput = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Ship ($)") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = gatewayFeeInput,
                            onValueChange = { gatewayFeeInput = it },
                            modifier = Modifier.weight(1f),
                            label = { Text("Fee ($)") },
                            singleLine = true
                        )
                    }

                    Text(
                        "Any ad campaign running above ${String.format("%.2f", calculatedBreakevenRoas)}x is generating pure profit in your bank account.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Section Title
        item {
            Text(
                "Active Ad-Set ROAS Attribution Waterfall",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        // Campaign Attributions List
        items(adAttributions) { attribution ->
            val isProfitable = attribution.trueNetProfitUSD > 0
            val statusColor = if (isProfitable) SnapEmerald else SnapRose

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, statusColor.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                attribution.campaignName,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                "Platform: ${attribution.platform}",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = statusColor.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, statusColor.copy(alpha = 0.4f))
                        ) {
                            Text(
                                "${attribution.actualRoas}x Actual ROAS",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                color = statusColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Waterfall Ledger
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF0F1420),
                        border = BorderStroke(1.dp, Color(0xFF1E2838))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Gross Ad Revenue:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("+$${String.format("%.2f", attribution.grossRevenueUSD)}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("(-) Ad Spend:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("-$${String.format("%.2f", attribution.adSpendUSD)}", style = MaterialTheme.typography.bodySmall, color = SnapRose)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("(-) Supplier COGS:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("-$${String.format("%.2f", attribution.cogsUSD)}", style = MaterialTheme.typography.bodySmall, color = SnapRose)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("(-) Payment Fees + Shipping:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("-$${String.format("%.2f", attribution.gatewayFeesUSD + attribution.shippingFeesUSD)}", style = MaterialTheme.typography.bodySmall, color = SnapRose)
                            }

                            Divider(color = Color(0xFF222C40), thickness = 1.dp)

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("True Net Take-Home Profit:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = statusColor)
                                Text(
                                    "${if (isProfitable) "+$" else "-$"}${String.format("%.2f", kotlin.math.abs(attribution.trueNetProfitUSD))}",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                                    color = statusColor
                                )
                            }
                        }
                    }

                    // Breakeven target comparison
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Breakeven Floor: ${attribution.breakevenRoas}x",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            if (isProfitable) "✅ Running Above Breakeven (Scale Budget)" else "🚨 Running Below Breakeven (Kill or Optimize)",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, fontWeight = FontWeight.Bold),
                            color = statusColor
                        )
                    }
                }
            }
        }
    }
}
