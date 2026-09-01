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
import com.example.data.model.PriceSurgeAction
import com.example.data.model.SupplierPriceGuardEvent
import com.example.data.model.WarehouseInventoryGuard
import com.example.ui.theme.*

@Composable
fun InventoryGuardScreen(
    priceSurgeEvents: List<SupplierPriceGuardEvent>,
    inventoryGuards: List<WarehouseInventoryGuard>,
    onToggleAutoReroute: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("inventory_guard_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131826)),
                border = BorderStroke(1.dp, SnapAmber.copy(alpha = 0.5f))
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
                                    .background(SnapAmber.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Inventory2, contentDescription = null, tint = SnapAmber, modifier = Modifier.size(20.dp))
                            }
                            Column {
                                Text(
                                    "Supplier Price & Inventory Guard",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    "Price Surge Margin Lock • Multi-Warehouse Auto-Reroute",
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
                                "Auto-Protected",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapEmerald,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Tab Selector
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color(0xFF0F1420),
                        contentColor = SnapAmber,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                color = SnapAmber
                            )
                        },
                        divider = {}
                    ) {
                        listOf("Price Surge Margin Locks", "Warehouse Stock Monitors").forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = {
                                    Text(
                                        title,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (selectedTab == index) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        if (selectedTab == 0) {
            // Price Surge Alerts
            items(priceSurgeEvents) { event ->
                val actionColor = when (event.action) {
                    PriceSurgeAction.AUTO_REPRICED_MARGIN_LOCKED -> SnapEmerald
                    PriceSurgeAction.REROUTED_BACKUP_SUPPLIER -> SnapCyan
                    PriceSurgeAction.DELISTED_OUT_OF_STOCK -> SnapRose
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, actionColor.copy(alpha = 0.4f))
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
                                color = actionColor.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, actionColor.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    when (event.action) {
                                        PriceSurgeAction.AUTO_REPRICED_MARGIN_LOCKED -> "🛡️ MARGIN LOCKED & AUTO-REPRICED"
                                        PriceSurgeAction.REROUTED_BACKUP_SUPPLIER -> "🔄 REROUTED TO BACKUP WAREHOUSE"
                                        PriceSurgeAction.DELISTED_OUT_OF_STOCK -> "🛑 AUTO-DELISTED (STOCK ZERO)"
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                    color = actionColor,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }

                            Text(
                                event.timestamp,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            event.productName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            "Supplier: ${event.supplierName}",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Surge Comparison Box
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF0F1420),
                            border = BorderStroke(1.dp, Color(0xFF1E2838))
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Supplier Cost Jump:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(
                                        "₹${event.oldSupplierCostINR.toInt()} ➔ ₹${event.newSupplierCostINR.toInt()} (+${String.format("%.1f", event.surgePct)}%)",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                        color = SnapRose
                                    )
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Retail Price Adjusted:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(
                                        "₹${event.oldRetailPriceINR.toInt()} ➔ ₹${event.autoAdjustedRetailPriceINR.toInt()}",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                        color = SnapGold
                                    )
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Net Protected Margin:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("${event.protectedMarginPct}% Profit Locked", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Black), color = SnapEmerald)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Warehouse Stock Monitors
            items(inventoryGuards) { guard ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, Color(0xFF1E2838))
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
                            Text(
                                guard.productName,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (guard.status == "IN_STOCK") SnapEmerald.copy(alpha = 0.2f) else SnapAmber.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, if (guard.status == "IN_STOCK") SnapEmerald.copy(alpha = 0.4f) else SnapAmber.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    guard.status,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (guard.status == "IN_STOCK") SnapEmerald else SnapAmber,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        // Warehouses Multi-Stock Strip
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF0F1420),
                            border = BorderStroke(1.dp, Color(0xFF1E2838))
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Primary: ${guard.primaryWarehouse}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("${guard.primaryStockUnits} Units", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Secondary: ${guard.secondaryWarehouse}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("${guard.secondaryStockUnits} Units", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Auto-Reroute to Backup Warehouse if Stock < 10:",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Switch(
                                checked = guard.isAutoRerouteActive,
                                onCheckedChange = { onToggleAutoReroute(guard.productId, it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.Black, checkedTrackColor = SnapEmerald)
                            )
                        }
                    }
                }
            }
        }
    }
}
