package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationMarketplaceScreen(
    products: List<Product>,
    currentLocation: UserLocation,
    availableLocations: List<UserLocation>,
    onSelectLocation: (UserLocation) -> Unit,
    onSetPincode: (String, String) -> Unit,
    onDetectGps: () -> Unit,
    onOpenProductDetail: (Product) -> Unit,
    onAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    var showLocationDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf("All", "Smart Fitness", "Tech Gadgets", "Home & Living", "Health & Wellness")
    val filters = listOf("All", "⚡ Express (<24h)", "📦 Local Warehouse", "💵 Cash on Delivery", "🔥 Top in ${currentLocation.cityName}")

    val filteredProducts = products.filter { prod ->
        val matchesCategory = selectedCategory == "All" || prod.category.contains(selectedCategory, ignoreCase = true) || prod.niche.contains(selectedCategory, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() || prod.name.contains(searchQuery, ignoreCase = true) || prod.category.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "⚡ Express (<24h)" -> prod.deliveryDays <= 4
            "📦 Local Warehouse" -> true
            "💵 Cash on Delivery" -> currentLocation.codAvailable
            "🔥 Top in ${currentLocation.cityName}" -> prod.opportunityScore >= 85
            else -> true
        }
        matchesCategory && matchesSearch && matchesFilter
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("location_marketplace_screen"),
        contentPadding = PaddingValues(bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Hyper-Local Top Delivery Banner ---
        item {
            Surface(
                color = Color(0xFF0D1322),
                border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.35f)),
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = currentLocation.countryFlag, fontSize = 24.sp)
                            Column {
                                Text(
                                    text = "LOCATION-BASED MARKETPLACE",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    ),
                                    color = SnapCyan
                                )
                                Text(
                                    text = "Hyper-Local Fast Sourcing & Direct Delivery",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Change Location Button
                        OutlinedButton(
                            onClick = { showLocationDialog = true },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, SnapCyan),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = SnapCyanContainer.copy(alpha = 0.3f),
                                contentColor = SnapCyan
                            ),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.testTag("change_location_btn")
                        ) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(text = "Change", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        }
                    }

                    // Active Delivery Location Details Card
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF131A2D)),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showLocationDialog = true }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(SnapCyanContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (currentLocation.isGpsDetected) Icons.Default.GpsFixed else Icons.Default.Place,
                                        contentDescription = null,
                                        tint = SnapCyan,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = "Delivering to: ${currentLocation.cityName}",
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = SnapCyanContainer
                                        ) {
                                            Text(
                                                text = currentLocation.postalCode,
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                                                color = SnapCyan,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    Text(
                                        text = "Hub: ${currentLocation.nearbyHubName} • ${currentLocation.distanceKm} km away",
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.5.sp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = currentLocation.deliveryEtaDescription,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.5.sp),
                                    color = SnapEmerald
                                )
                                Text(
                                    text = "Carrier: ${currentLocation.localDeliveryCarrier}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Logistics Trust Badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF101726),
                            border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.3f)),
                            modifier = Modifier.weight(1f).padding(end = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Bolt, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(13.dp))
                                Text("Express Dispatch", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, fontWeight = FontWeight.Bold), color = SnapEmerald)
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF101726),
                            border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.3f)),
                            modifier = Modifier.weight(1f).padding(horizontal = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Payments, contentDescription = null, tint = SnapGold, modifier = Modifier.size(13.dp))
                                Text(
                                    if (currentLocation.codAvailable) "COD Available" else "Prepaid Secure",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, fontWeight = FontWeight.Bold),
                                    color = SnapGold
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF101726),
                            border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.3f)),
                            modifier = Modifier.weight(1f).padding(start = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(13.dp))
                                Text("Local Stock ${currentLocation.localStockAvailabilityPct}%", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, fontWeight = FontWeight.Bold), color = SnapCyan)
                            }
                        }
                    }
                }
            }
        }

        // --- Search Bar ---
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            "Search products in ${currentLocation.cityName} hub...",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp)
                        )
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = SnapCyan)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("location_search_field"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedBorderColor = SnapCyan,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )
            }
        }

        // --- Hyper-Local Filters Scrollable Row ---
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    val isSelected = selectedFilter == filter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        label = {
                            Text(
                                text = filter,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SnapCyanContainer,
                            selectedLabelColor = SnapCyan,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            selectedBorderColor = SnapCyan
                        )
                    )
                }
            }
        }

        // --- Category Tabs ---
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = selectedCategory == category
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedCategory = category }
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            ),
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // --- Localized Catalog Section Header ---
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "AVAILABLE NEAR ${currentLocation.cityName.uppercase()} (${filteredProducts.size})",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Tax & GST Included",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = SnapEmerald
                )
            }
        }

        // --- Product Cards Grid / List ---
        items(filteredProducts) { product ->
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                LocationProductCard(
                    product = product,
                    currentLocation = currentLocation,
                    onOpenDetail = { onOpenProductDetail(product) },
                    onAddToCart = { onAddToCart(product) }
                )
            }
        }
    }

    // --- Location Picker & Pincode Checker Modal ---
    if (showLocationDialog) {
        LocationSelectorDialog(
            currentLocation = currentLocation,
            availableLocations = availableLocations,
            onDismiss = { showLocationDialog = false },
            onSelectLocation = { loc ->
                onSelectLocation(loc)
                showLocationDialog = false
            },
            onSetPincode = { pin, city ->
                onSetPincode(pin, city)
                showLocationDialog = false
            },
            onDetectGps = {
                onDetectGps()
                showLocationDialog = false
            }
        )
    }
}

@Composable
fun LocationProductCard(
    product: Product,
    currentLocation: UserLocation,
    onOpenDetail: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onOpenDetail() }
            .testTag("location_product_${product.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    ) {
        Column {
            // Product Image with Local Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                ProductImageCard(
                    productId = product.id,
                    productName = product.name,
                    category = product.category,
                    height = 180.dp,
                    showBadge = false
                )

                // Top Left: Distance & Dispatch Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xDD0D1322),
                    border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.6f)),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.NearMe, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(11.dp))
                        Text(
                            text = "${currentLocation.distanceKm} km from ${currentLocation.cityName} Hub",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, fontWeight = FontWeight.Bold),
                            color = SnapCyan
                        )
                    }
                }

                // Top Right: Local Stock Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xDD0D1322),
                    border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.6f)),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(SnapEmerald)
                        )
                        Text(
                            text = "In Stock (${currentLocation.localStockAvailabilityPct}%)",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, fontWeight = FontWeight.Bold),
                            color = SnapEmerald
                        )
                    }
                }
            }

            // Card Body Details
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp,
                            fontSize = 10.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = "Rating", tint = SnapGold, modifier = Modifier.size(13.dp))
                        Text(
                            text = "4.9 (1.2k+ reviews)",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.5.sp),
                            color = SnapGold
                        )
                    }
                }

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product.description.ifEmpty { "High-demand top winning product with verified local supplier warranty and fast domestic dispatch." },
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Localized Price & Delivery ETA Pill
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.LocalShipping, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(13.dp))
                        Text(
                            text = "ETA: ${currentLocation.deliveryEtaDescription}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                            color = SnapEmerald
                        )
                    }

                    if (currentLocation.codAvailable) {
                        Text(
                            text = "💵 Pay on Delivery",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp, fontWeight = FontWeight.SemiBold),
                            color = SnapGold
                        )
                    }
                }

                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 2.dp))

                // Price and Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        val formattedPrice = product.getFormattedMarketPrice(currentLocation.countryCode)
                        Text(
                            text = formattedPrice,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = BodoniFontFamily,
                                fontWeight = FontWeight.Black
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Incl. ${currentLocation.taxRatePct}% Tax / GST",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = onAddToCart,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.AddShoppingCart, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Cart", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        }

                        Button(
                            onClick = onOpenDetail,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SnapCyan,
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text("Buy Now", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LocationSelectorDialog(
    currentLocation: UserLocation,
    availableLocations: List<UserLocation>,
    onDismiss: () -> Unit,
    onSelectLocation: (UserLocation) -> Unit,
    onSetPincode: (String, String) -> Unit,
    onDetectGps: () -> Unit
) {
    var inputPincode by remember { mutableStateOf(currentLocation.postalCode) }
    var inputCity by remember { mutableStateOf(currentLocation.cityName) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF101728)),
            border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(22.dp))
                        Text(
                            text = "Select Delivery Location",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                // GPS Auto-Detection Button
                Button(
                    onClick = onDetectGps,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SnapCyan,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.GpsFixed, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "⚡ Auto-Detect My Current GPS Location",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black)
                    )
                }

                Divider(color = Color(0xFF1E283E))

                // Enter PIN code or Zip code
                Text(
                    text = "ENTER PINCODE / POSTAL CODE",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = SnapGold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputPincode,
                        onValueChange = { inputPincode = it },
                        placeholder = { Text("e.g. 400001, 10001", fontSize = 12.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SnapCyan,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            focusedContainerColor = Color(0xFF161F33),
                            unfocusedContainerColor = Color(0xFF161F33)
                        )
                    )

                    Button(
                        onClick = {
                            if (inputPincode.isNotBlank()) {
                                onSetPincode(inputPincode, inputCity)
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        Text("Verify", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black))
                    }
                }

                // Quick Popular Cities Chips
                Text(
                    text = "POPULAR COMMERCE HUBS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(availableLocations) { loc ->
                        val isSelected = currentLocation.id == loc.id
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) SnapCyanContainer else Color(0xFF161F33),
                            border = BorderStroke(1.dp, if (isSelected) SnapCyan else Color(0xFF232E47)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onSelectLocation(loc) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(loc.countryFlag, fontSize = 13.sp)
                                Text(
                                    text = loc.cityName,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    ),
                                    color = if (isSelected) SnapCyan else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
