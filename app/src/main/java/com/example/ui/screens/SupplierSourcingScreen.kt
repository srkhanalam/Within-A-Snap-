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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Supplier
import com.example.ui.components.SectionHeader
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SupplierSourcingScreen(
    suppliers: List<Supplier>,
    onToggleSupplierIntegration: (String, Boolean) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    var selectedMarketFilter by remember { mutableStateOf("ALL") }
    var searchQuery by remember { mutableStateOf("") }
    var selectedSupplierForModal by remember { mutableStateOf<Supplier?>(null) }
    var primarySupplierId by remember { mutableStateOf(suppliers.firstOrNull()?.id ?: "") }
    var showProfitCalculatorModal by remember { mutableStateOf<Supplier?>(null) }
    var sampleOrderSuccessMsg by remember { mutableStateOf<String?>(null) }

    val marketFilters = listOf(
        Pair("ALL", "🌐 All Hubs (${suppliers.size})"),
        Pair("IN", "🇮🇳 India (${suppliers.count { it.primaryMarket == "IN" || it.supportedMarkets.contains("IN") }})"),
        Pair("AE_SA", "🇦🇪🇸🇦 UAE & Saudi (${suppliers.count { it.primaryMarket == "AE" || it.primaryMarket == "SA" || it.supportedMarkets.contains("AE") || it.supportedMarkets.contains("SA") }})"),
        Pair("US", "🇺🇸 USA (${suppliers.count { it.primaryMarket == "US" || it.supportedMarkets.contains("US") }})"),
        Pair("EU_GB", "🇪🇺🇬🇧 Europe & UK (${suppliers.count { it.primaryMarket == "EU" || it.primaryMarket == "GB" || it.supportedMarkets.contains("EU") || it.supportedMarkets.contains("GB") }})"),
        Pair("GLOBAL", "⚡ Global Air (${suppliers.count { it.primaryMarket == "GLOBAL" }})")
    )

    val filteredSuppliers = remember(suppliers, selectedMarketFilter, searchQuery) {
        suppliers.filter { sup ->
            val matchesMarket = when (selectedMarketFilter) {
                "ALL" -> true
                "IN" -> sup.primaryMarket == "IN" || sup.supportedMarkets.contains("IN")
                "AE_SA" -> sup.primaryMarket == "AE" || sup.primaryMarket == "SA" || sup.supportedMarkets.contains("AE") || sup.supportedMarkets.contains("SA")
                "US" -> sup.primaryMarket == "US" || sup.supportedMarkets.contains("US")
                "EU_GB" -> sup.primaryMarket == "EU" || sup.primaryMarket == "GB" || sup.supportedMarkets.contains("EU") || sup.supportedMarkets.contains("GB")
                "GLOBAL" -> sup.primaryMarket == "GLOBAL" || sup.supportedMarkets.contains("GLOBAL")
                else -> true
            }
            val matchesSearch = searchQuery.isBlank() ||
                    sup.name.contains(searchQuery, ignoreCase = true) ||
                    sup.country.contains(searchQuery, ignoreCase = true) ||
                    sup.specialty.contains(searchQuery, ignoreCase = true) ||
                    sup.notes.contains(searchQuery, ignoreCase = true)
            matchesMarket && matchesSearch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("supplier_sourcing_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // --- Header Banner ---
        item {
            Surface(
                color = Color(0xFF0D1322),
                border = BorderStroke(1.dp, Color(0xFF1E293B)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = SnapGoldContainer.copy(alpha = 0.6f)
                                ) {
                                    Icon(
                                        Icons.Default.LocalShipping,
                                        contentDescription = null,
                                        tint = SnapGold,
                                        modifier = Modifier.padding(6.dp).size(20.dp)
                                    )
                                }
                                Text(
                                    text = "Dropshipping Suppliers & Hubs",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Text(
                                text = "Verified High-Margin Fulfillment for India, UAE, Saudi Arabia, USA & Europe",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SnapEmeraldContainer.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.5f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Verified, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(14.dp))
                                Text("100% Vetted", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                            }
                        }
                    }

                    // Metric Highlights
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131C31)),
                            border = BorderStroke(1.dp, Color(0xFF233252)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f).padding(end = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("AVG DISPATCH", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("2-4 Days", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131C31)),
                            border = BorderStroke(1.dp, Color(0xFF233252)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("NET PROFIT MARGIN", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("52% - 65%", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131C31)),
                            border = BorderStroke(1.dp, Color(0xFF233252)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f).padding(start = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("COD & NDR SHIELD", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Active & Ready", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                            }
                        }
                    }

                    // Search input
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search by name, country, warehouse or specialty...", fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SnapGold) },
                        trailingIcon = {
                            if (searchQuery.isNotBlank()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SnapGold,
                            unfocusedBorderColor = Color(0xFF283652),
                            focusedContainerColor = Color(0xFF101726),
                            unfocusedContainerColor = Color(0xFF101726)
                        )
                    )

                    // Market Filter Pills
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(marketFilters) { (key, label) ->
                            val isSelected = selectedMarketFilter == key
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedMarketFilter = key },
                                label = {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SnapGold,
                                    selectedLabelColor = Color.Black,
                                    containerColor = Color(0xFF172033),
                                    labelColor = MaterialTheme.colorScheme.onSurface
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isSelected,
                                    borderColor = Color(0xFF283652),
                                    selectedBorderColor = SnapGold
                                )
                            )
                        }
                    }
                }
            }
        }

        // Notification if sample order dispatched
        sampleOrderSuccessMsg?.let { msg ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = SnapEmeraldContainer),
                    border = BorderStroke(1.dp, SnapEmerald),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SnapEmerald)
                            Text(msg, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                        }
                        IconButton(onClick = { sampleOrderSuccessMsg = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        // Section Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Verified Supplier Directory (${filteredSuppliers.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Sorted by AI Reliability Score",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Supplier Cards
        items(filteredSuppliers) { sup ->
            val isPrimary = sup.id == primarySupplierId

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { selectedSupplierForModal = sup },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(
                    1.dp,
                    if (isPrimary) SnapGold.copy(alpha = 0.8f) else Color(0xFF283652)
                )
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Supplier Title & Status
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF0F172A),
                                border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = sup.logoEmoji,
                                    fontSize = 22.sp,
                                    modifier = Modifier.padding(6.dp)
                                )
                            }

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = sup.name,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    if (sup.isVerified) {
                                        Icon(
                                            Icons.Default.Verified,
                                            contentDescription = "Verified",
                                            tint = SnapEmerald,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = sup.country,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Rating & Score
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF141E33),
                            border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = SnapGold, modifier = Modifier.size(14.dp))
                                Text(
                                    text = "${sup.rating}",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = SnapGold
                                )
                                Text(
                                    text = "(${sup.reliabilityScore}% QA)",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = SnapEmerald
                                )
                            }
                        }
                    }

                    // Specialty & Overview
                    Text(
                        text = sup.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Feature Badges
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SnapEmeraldContainer.copy(alpha = 0.4f),
                            border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "📈 ${sup.avgProfitMarginPct.toInt()}% Avg Profit Margin",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                color = SnapEmerald,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SnapGoldContainer.copy(alpha = 0.4f),
                            border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.4f))
                        ) {
                            Text(
                                text = "⏱️ ${sup.deliveryDays}-Day Express SLA",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                color = SnapGoldLight,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        if (sup.codSupported) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = SnapCyanContainer.copy(alpha = 0.4f),
                                border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "💵 COD & NDR Shield Supported",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                    color = SnapCyan,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        if (sup.customBranding) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF1C1938),
                                border = BorderStroke(1.dp, SnapViolet.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "✨ Custom Box & Thank-You Card",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = SnapViolet,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    // Key Stats Grid
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF101726),
                        border = BorderStroke(1.dp, Color(0xFF1E2B45)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Unit Cost", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("₹${sup.productCostINR.toInt()} / $${String.format("%.1f", sup.productCostINR / 83.5)}", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                            }
                            Column {
                                Text("Shipping", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("₹${sup.shippingCostINR.toInt()}", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                            }
                            Column {
                                Text("MOQ", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${sup.moq} pc", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                            }
                            Column {
                                Text("Total Fulfilled", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(sup.totalOrdersFulfilled, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                            }
                        }
                    }

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { showProfitCalculatorModal = sup },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            border = BorderStroke(1.dp, SnapCyan)
                        ) {
                            Icon(Icons.Default.Calculate, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Margin Calc", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { selectedSupplierForModal = sup },
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                border = BorderStroke(1.dp, Color(0xFF3B4D73))
                            ) {
                                Text("Details & SLA", style = MaterialTheme.typography.labelSmall)
                            }

                            Button(
                                onClick = {
                                    primarySupplierId = sup.id
                                    onToggleSupplierIntegration(sup.id, true)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isPrimary) SnapEmerald else SnapGold,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                if (isPrimary) {
                                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text("Active Supplier", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                } else {
                                    Text("Set As Primary", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // --- Supplier Details & SLA Modal ---
    selectedSupplierForModal?.let { sup ->
        AlertDialog(
            onDismissRequest = { selectedSupplierForModal = null },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(sup.logoEmoji, fontSize = 24.sp)
                    Column {
                        Text(
                            text = sup.name,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = SnapGold
                        )
                        Text(
                            text = "${sup.country} • ${sup.specialty}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            text = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(max = 420.dp)
                ) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131C31)),
                            border = BorderStroke(1.dp, Color(0xFF233252)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("WAREHOUSE & DISPATCH HUBS", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SnapGoldLight))
                                Text(sup.warehouses, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131C31)),
                            border = BorderStroke(1.dp, Color(0xFF233252)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("SHIPPING CARRIERS & API SYNC", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SnapGoldLight))
                                Text(sup.shippingCarriers.joinToString(" • "), style = MaterialTheme.typography.bodySmall, color = SnapEmerald)
                                Text("Sync Speed: ${sup.orderSyncSpeed}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Support SLA: ${sup.contactSupportSla}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131C31)),
                            border = BorderStroke(1.dp, Color(0xFF233252)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("RETURN & DISPUTE POLICY", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SnapGoldLight))
                                Text(sup.returnPolicy, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }

                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF131C31)),
                            border = BorderStroke(1.dp, Color(0xFF233252)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("INTEGRATION PROTOCOL", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SnapGoldLight))
                                Text("Protocol: ${sup.integrationType}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                Text("Status: ${if (sup.isIntegrated) "CONNECTED & ACTIVE" else "READY TO CONNECT"}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = if (sup.isIntegrated) SnapEmerald else SnapAmber)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        sampleOrderSuccessMsg = "Sample Test Order dispatched via ${sup.name}! Tracking generated."
                        selectedSupplierForModal = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapCyan, contentColor = Color.Black)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Simulate Sample Dispatch", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedSupplierForModal = null }) {
                    Text("Close")
                }
            }
        )
    }

    // --- Interactive Profit Margin Calculator Modal ---
    showProfitCalculatorModal?.let { sup ->
        var testSellingPriceUSD by remember { mutableStateOf("49.99") }
        val unitCostUSD = sup.productCostINR / 83.5
        val shippingCostUSD = sup.shippingCostINR / 83.5
        val platformFeeUSD = 1.50
        val adCostEstUSD = 12.00
        val sellingPriceNum = testSellingPriceUSD.toDoubleOrNull() ?: 49.99
        val totalCostUSD = unitCostUSD + shippingCostUSD + platformFeeUSD + adCostEstUSD
        val netProfitUSD = (sellingPriceNum - totalCostUSD).coerceAtLeast(0.0)
        val marginPct = if (sellingPriceNum > 0) (netProfitUSD / sellingPriceNum) * 100.0 else 0.0

        AlertDialog(
            onDismissRequest = { showProfitCalculatorModal = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Calculate, contentDescription = null, tint = SnapGold)
                    Text("Profit Margin Calculator", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Live unit economics using ${sup.name} wholesale rates:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedTextField(
                        value = testSellingPriceUSD,
                        onValueChange = { testSellingPriceUSD = it },
                        label = { Text("Retail Selling Price ($ USD)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF101726)),
                        border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Supplier Product Cost:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("-$${String.format("%.2f", unitCostUSD)}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapRose)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Fast Shipping Cost:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("-$${String.format("%.2f", shippingCostUSD)}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapRose)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Payment Gateway Fee (3%):", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("-$${String.format("%.2f", platformFeeUSD)}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapRose)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Target Ad Cost per Order:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("-$${String.format("%.2f", adCostEstUSD)}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapRose)
                            }

                            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 4.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text("ESTIMATED NET PROFIT / UNIT:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black), color = SnapGoldLight)
                                Text(
                                    "+$${String.format("%.2f", netProfitUSD)} USD",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                    color = SnapEmerald
                                )
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Net Margin Percentage:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(
                                    "${String.format("%.1f", marginPct)}%",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                                    color = SnapEmerald
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showProfitCalculatorModal = null },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black)
                ) {
                    Text("Apply & Close", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}
