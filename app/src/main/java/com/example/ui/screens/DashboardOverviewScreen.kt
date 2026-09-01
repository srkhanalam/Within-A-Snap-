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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen

@Composable
fun DashboardOverviewScreen(
    products: List<Product>,
    orders: List<Order>,
    campaigns: List<Campaign>,
    recommendations: List<AIRecommendation>,
    autopilotConfig: AutopilotConfig,
    totalRevenue: Double,
    totalProfit: Double,
    onNavigate: (AppScreen) -> Unit,
    onSelectProduct: (Product) -> Unit,
    onApproveRecommendation: (String) -> Unit,
    onRejectRecommendation: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("dashboard_overview_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // AI Daily Business Brief Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ai_daily_brief_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SnapGoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = SnapGold,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "AI DAILY BUSINESS BRIEF",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    ),
                                    color = SnapGold
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "Good Morning, Parvej Alam 👋",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Icon(
                                        Icons.Default.Verified,
                                        contentDescription = "Verified Owner",
                                        tint = SnapGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        // Autopilot Mode Badge
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SnapViolet.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, SnapViolet.copy(alpha = 0.4f)),
                            modifier = Modifier.clickable { onNavigate(AppScreen.AUTOPILOT) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Bolt, contentDescription = null, tint = SnapViolet, modifier = Modifier.size(12.dp))
                                Text(
                                    text = "Mode: ${autopilotConfig.mode}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = SnapViolet
                                )
                            }
                        }
                    }

                    Text(
                        text = "Store health is strong (+14% revenue). UAE Meta campaign ROAS surged to 5.7x. Sourcing agent identified 2-day faster shipping on Sonic Neck Massager. 1 high-risk order flagged for review.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onNavigate(AppScreen.COMMAND_CENTER) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Open AI Command Center", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }

        // Key Business Metrics Grid (2x2)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SnapStatCard(
                        title = "Gross Revenue",
                        value = "$${String.format("%,.2f", totalRevenue)}",
                        trend = "+14.2%",
                        isPositive = true,
                        icon = Icons.Default.AttachMoney,
                        accentColor = SnapGold,
                        modifier = Modifier.weight(1f)
                    )
                    SnapStatCard(
                        title = "Net Profit",
                        value = "$${String.format("%,.2f", totalProfit)}",
                        trend = "+18.6%",
                        isPositive = true,
                        icon = Icons.Default.TrendingUp,
                        accentColor = SnapEmerald,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SnapStatCard(
                        title = "Blended ROAS",
                        value = "5.24x",
                        trend = "+0.8x",
                        isPositive = true,
                        icon = Icons.Default.Campaign,
                        accentColor = SnapViolet,
                        modifier = Modifier.weight(1f)
                    )
                    SnapStatCard(
                        title = "Active Orders",
                        value = "${orders.size}",
                        trend = "+8 today",
                        isPositive = true,
                        icon = Icons.Default.ShoppingBag,
                        accentColor = SnapCyan,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // High Priority AI Recommendations
        item {
            SectionHeader(
                title = "⚡ Priority AI Action Items",
                subtitle = "Virtual team recommendations waiting for your approval",
                actionText = "Command Center",
                onActionClick = { onNavigate(AppScreen.COMMAND_CENTER) }
            )
        }

        val pendingRecs = recommendations.filter { it.status == RecommendationStatus.PENDING }.take(2)
        if (pendingRecs.isNotEmpty()) {
            items(pendingRecs) { rec ->
                AiRecommendationCard(
                    recommendation = rec,
                    onApprove = { onApproveRecommendation(rec.id) },
                    onReject = { onRejectRecommendation(rec.id) }
                )
            }
        }

        // Top Winning Products Section
        item {
            SectionHeader(
                title = "🔥 High-Opportunity Winning Products",
                subtitle = "AI-discovered winners ready to launch with 1 click",
                actionText = "View All",
                onActionClick = { onNavigate(AppScreen.PRODUCT_FINDER) }
            )
        }

        items(products.take(3)) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectProduct(product) }
                    .testTag("product_card_${product.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SnapGoldContainer.copy(alpha = 0.4f)
                        ) {
                            Text(
                                text = product.niche,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapGold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        OpportunityScoreBadge(score = product.opportunityScore)
                    }

                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "UAE / GCC Retail: ${product.getFormattedMarketPrice("AE")}",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = SnapEmerald
                            )
                            Text(
                                text = "Margin: ${product.targetMarginPct}% • ${product.deliveryDays}d Delivery",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Button(
                            onClick = { onSelectProduct(product) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (product.isLaunched) SnapSurfaceElevatedDark else SnapGold,
                                contentColor = if (product.isLaunched) SnapGold else Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (product.isLaunched) "Launch Kit" else "1-Click Launch",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }

        // Recent Orders Ticker
        item {
            SectionHeader(
                title = "📦 Real-Time Order Stream",
                subtitle = "Fulfillment & automated supplier routing status",
                actionText = "Manage Orders",
                onActionClick = { onNavigate(AppScreen.ORDERS) }
            )
        }

        items(orders.take(3)) { order ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(AppScreen.ORDERS) },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = order.id,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            CountryFlagBadge(marketCode = order.marketCode)
                        }
                        Text(
                            text = "${order.customerName} • ${order.productName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "+$${String.format("%.2f", order.profitUSD)} profit",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapEmerald
                        )
                        StatusBadge(status = order.fulfillmentStatus.name)
                    }
                }
            }
        }
    }
}
