package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerStorefrontScreen(
    products: List<Product>,
    storeProfile: StoreProfile,
    onOpenNationalProduct: (Product) -> Unit,
    onOpenInternationalProduct: (Product) -> Unit,
    onDirectAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    var isInternationalMode by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf("All", "Smart Fitness", "Tech Gadgets", "Home & Living", "Health & Wellness")

    val filteredProducts = products.filter { prod ->
        val matchesCategory = selectedCategory == "All" || prod.category.contains(selectedCategory, ignoreCase = true) || prod.niche.contains(selectedCategory, ignoreCase = true)
        val matchesSearch = searchQuery.isBlank() || prod.name.contains(searchQuery, ignoreCase = true) || prod.category.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("buyer_storefront_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // --- Storefront Top Banner & Mode Toggle ---
        item {
            Surface(
                color = Color(0xFF0F1524),
                border = BorderStroke(1.dp, Color(0xFF1F2B42)),
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
                        BrandLogoHeader(compact = true)

                        // Mode Toggle Pill
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(1.dp, if (isInternationalMode) SnapCyan else SnapGold)
                        ) {
                            Row(
                                modifier = Modifier.padding(2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable { isInternationalMode = false },
                                    color = if (!isInternationalMode) SnapGold else Color.Transparent
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("🇮🇳", fontSize = 12.sp)
                                        Text(
                                            text = "National",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = if (!isInternationalMode) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .clickable { isInternationalMode = true },
                                    color = if (isInternationalMode) SnapCyan else Color.Transparent
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text("🌐", fontSize = 12.sp)
                                        Text(
                                            text = "Global",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = if (isInternationalMode) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Store Identity
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = storeProfile.name,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = storeProfile.tagline,
                                style = MaterialTheme.typography.bodySmall,
                                color = SnapGoldLight
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isInternationalMode) SnapCyanContainer.copy(alpha = 0.5f) else SnapGoldContainer.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = if (isInternationalMode) "AIR CARGO DDP" else "EXPRESS 24H",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    fontSize = 10.sp,
                                    letterSpacing = 1.sp
                                ),
                                color = if (isInternationalMode) SnapCyan else SnapGold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Search input
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search catalog by keywords or category...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SnapGold) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // --- Category Filters ---
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = selectedCategory == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = if (isInternationalMode) SnapCyanContainer else SnapGoldContainer,
                            selectedLabelColor = if (isInternationalMode) SnapCyan else SnapGold
                        )
                    )
                }
            }
        }

        // --- Buyer Portal Switcher Banners ---
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // National Card
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable {
                            val target = filteredProducts.firstOrNull() ?: products.first()
                            onOpenNationalProduct(target)
                        },
                    color = Color(0xFF131A26),
                    border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("🇮🇳", fontSize = 16.sp)
                            Text("National Page", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                        }
                        Text("Domestic ₹ pricing, UPI, GST invoices, 24-48h dispatch", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                // International Card
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable {
                            val target = filteredProducts.firstOrNull() ?: products.first()
                            onOpenInternationalProduct(target)
                        },
                    color = Color(0xFF0F1B2B),
                    border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("🌐", fontSize = 16.sp)
                            Text("Global Page", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                        }
                        Text("Multi-currency USD/AED/SAR/EUR, DDP duties, DHL air cargo", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // --- Section Header ---
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isInternationalMode) "Global Winning Products" else "National Best Sellers (India)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "${filteredProducts.size} Items Available",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // --- Product Items List ---
        items(filteredProducts) { prod ->
            val displayPrice = if (isInternationalMode) {
                "$${prod.getMarketSellingPrice("US").toInt()}"
            } else {
                "₹${prod.getMarketSellingPrice("IN").toInt()}"
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable {
                        if (isInternationalMode) onOpenInternationalProduct(prod) else onOpenNationalProduct(prod)
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(
                    1.dp,
                    if (isInternationalMode) SnapCyan.copy(alpha = 0.3f) else SnapGold.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isInternationalMode) SnapCyanContainer.copy(alpha = 0.5f) else SnapGoldContainer.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = if (isInternationalMode) "AIR EXPRESS" else "NATIONAL STOCK",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp),
                                color = if (isInternationalMode) SnapCyan else SnapGold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = SnapGold, modifier = Modifier.size(13.dp))
                            Text(text = "4.9 (1.2k+)", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        }
                    }

                    Text(
                        text = prod.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = prod.description.ifBlank { "High-velocity trending winner engineered for durability, smart capabilities, and ergonomic everyday performance." },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = displayPrice,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                color = if (isInternationalMode) SnapCyan else SnapGold
                            )
                            Text(
                                text = if (isInternationalMode) "Free Worldwide Air Cargo" else "Inclusive of 18% GST",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, color = SnapEmerald)
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = {
                                    if (isInternationalMode) onOpenInternationalProduct(prod) else onOpenNationalProduct(prod)
                                },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                border = BorderStroke(1.dp, if (isInternationalMode) SnapCyan else SnapGold)
                            ) {
                                Text(
                                    text = "View Page",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isInternationalMode) SnapCyan else SnapGold
                                )
                            }

                            Button(
                                onClick = {
                                    if (isInternationalMode) onOpenInternationalProduct(prod) else onOpenNationalProduct(prod)
                                },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isInternationalMode) SnapCyan else SnapGold,
                                    contentColor = Color.Black
                                )
                            ) {
                                Icon(Icons.Default.FlashOn, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = "Buy Now",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
