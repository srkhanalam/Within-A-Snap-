package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SnapStatCard
import com.example.ui.theme.*

@Composable
fun AnalyticsScreen(
    totalRevenue: Double,
    totalProfit: Double,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("analytics_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "AI Profitability & Business Analytics",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "End-to-end unit economics, blended ad efficiency, and regional contribution margins.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // AI Business Analyst Diagnostic Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.5f))
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
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(SnapGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Insights, contentDescription = null, tint = SnapGold, modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = "AI BUSINESS ANALYST DIAGNOSIS",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, letterSpacing = 1.sp),
                                color = SnapGold
                            )
                        }

                        Surface(shape = RoundedCornerShape(6.dp), color = SnapEmerald.copy(alpha = 0.2f)) {
                            Text(
                                text = "46.2% Net Margin",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapEmerald,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "Store is scaling with high capital efficiency. GCC expansion into UAE & Saudi Arabia generated 72% of total net profits this month with a strong 5.24x blended ROAS. US market acquisition cost is normalizing.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Profit & Loss Breakdown Card
        item {
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
                        text = "Monthly Financial Breakdown (P&L)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    val pnlItems = listOf(
                        Triple("Gross Store Revenue", "+$8,420.00", SnapGold),
                        Triple("Supplier Cost of Goods (COGS)", "-$2,140.00", MaterialTheme.colorScheme.onSurfaceVariant),
                        Triple("International Express Shipping", "-$720.00", MaterialTheme.colorScheme.onSurfaceVariant),
                        Triple("Paid Advertising Spend (Meta/TikTok/Google)", "-$1,360.00", MaterialTheme.colorScheme.onSurfaceVariant),
                        Triple("Payment Gateway & FX Fees (2.9%)", "-$244.00", MaterialTheme.colorScheme.onSurfaceVariant),
                        Triple("Net Take-Home Cashflow", "+$3,956.00", SnapEmerald)
                    )

                    pnlItems.forEach { (label, amount, color) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            Text(amount, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = color)
                        }
                        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), thickness = 0.5.dp)
                    }
                }
            }
        }

        // Regional Revenue Contribution
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Revenue by International Market",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    val marketShares = listOf(
                        Triple("🇦🇪 UAE (Dubai & Abu Dhabi)", 0.44f, "$3,704 (44%)"),
                        Triple("🇸🇦 Saudi Arabia (Riyadh & Jeddah)", 0.28f, "$2,357 (28%)"),
                        Triple("🇮🇳 India (Tier 1 & 2 Metros)", 0.16f, "$1,347 (16%)"),
                        Triple("🇺🇸 United States & Global", 0.12f, "$1,012 (12%)")
                    )

                    marketShares.forEach { (country, ratio, label) ->
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(country, style = MaterialTheme.typography.bodySmall)
                                Text(label, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                            }
                            LinearProgressIndicator(
                                progress = { ratio },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = SnapGold,
                                trackColor = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                }
            }
        }
    }
}
