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
import com.example.data.model.*
import com.example.ui.components.CountryFlagBadge
import com.example.ui.components.StatusBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderManagementScreen(
    orders: List<Order>,
    onFulfillOrder: (String) -> Unit,
    onApproveRiskOrder: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredOrders = orders.filter { order ->
        val matchesFilter = when (selectedFilter) {
            "All" -> true
            "Pending" -> order.fulfillmentStatus == FulfillmentStatus.PENDING || order.fulfillmentStatus == FulfillmentStatus.PROCESSING
            "Fulfilled" -> order.fulfillmentStatus == FulfillmentStatus.FULFILLED || order.fulfillmentStatus == FulfillmentStatus.SHIPPED || order.fulfillmentStatus == FulfillmentStatus.DELIVERED
            "High Risk" -> order.riskScore > 50
            else -> true
        }
        val matchesSearch = searchQuery.isBlank() || order.id.contains(searchQuery, ignoreCase = true) || order.customerName.contains(searchQuery, ignoreCase = true) || order.productName.contains(searchQuery, ignoreCase = true)
        matchesFilter && matchesSearch
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("order_management_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Title & Search
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Orders & Automated Fulfillment",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by Order #, Customer Name, Product...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                val filters = listOf("All", "Pending", "Fulfilled", "High Risk")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filters) { f ->
                        val isSelected = selectedFilter == f
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedFilter = f },
                            label = {
                                Text(if (f == "High Risk") "⚠️ High Risk (${orders.count { it.riskScore > 50 }})" else f)
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = if (f == "High Risk") SnapRoseContainer else SnapGoldContainer,
                                selectedLabelColor = if (f == "High Risk") SnapRose else SnapGold
                            )
                        )
                    }
                }
            }
        }

        // Orders List
        items(filteredOrders) { order ->
            val isHighRisk = order.riskScore > 50

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(
                    1.dp,
                    if (isHighRisk) SnapRose.copy(alpha = 0.8f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
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
                            Text(
                                text = order.id,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            CountryFlagBadge(marketCode = order.marketCode)
                        }

                        StatusBadge(status = order.fulfillmentStatus.name)
                    }

                    Text(
                        text = "${order.customerName} • ${order.customerCity}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "Item: ${order.productName} (x${order.quantity})",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Financials Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Revenue: $${order.revenueUSD}", style = MaterialTheme.typography.bodySmall)
                        Text("Cost: $${order.costUSD}", style = MaterialTheme.typography.bodySmall)
                        Text("Net Profit: +$${order.profitUSD}", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                    }

                    // High Risk Warning Box
                    if (isHighRisk) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SnapRoseContainer.copy(alpha = 0.3f),
                            border = BorderStroke(1.dp, SnapRose.copy(alpha = 0.6f))
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Warning, contentDescription = null, tint = SnapRose, modifier = Modifier.size(16.dp))
                                    Text("AI Risk Alert (${order.riskScore}/100)", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = SnapRose)
                                }
                                order.riskFactors.forEach { factor ->
                                    Text("• $factor", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Button(
                                    onClick = { onApproveRiskOrder(order.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = SnapEmerald, contentColor = Color.Black),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text("Merchant Override: Clear Risk & Approve", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    } else {
                        // Regular Shipping Status
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Tracking: ${order.trackingNumber}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                    Text(order.shippingStatus, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                if (order.fulfillmentStatus == FulfillmentStatus.PENDING || order.fulfillmentStatus == FulfillmentStatus.PROCESSING) {
                                    Button(
                                        onClick = { onFulfillOrder(order.id) },
                                        colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text("1-Click Fulfill", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
