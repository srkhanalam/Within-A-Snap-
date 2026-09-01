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
import com.example.data.model.Product
import com.example.ui.components.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFinderScreen(
    products: List<Product>,
    onSelectProduct: (Product) -> Unit,
    onLaunchProduct: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedNicheFilter by remember { mutableStateOf("All") }
    var minMarginFilter by remember { mutableDoubleStateOf(30.0) }
    var selectedRegionFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredProducts = products.filter { product ->
        val matchesNiche = selectedNicheFilter == "All" || product.niche.contains(selectedNicheFilter, ignoreCase = true) || product.category.contains(selectedNicheFilter, ignoreCase = true)
        val matchesMargin = product.targetMarginPct >= minMarginFilter
        val matchesRegion = selectedRegionFilter == "All" || product.recommendedMarkets.contains(selectedRegionFilter)
        val matchesSearch = searchQuery.isBlank() || product.name.contains(searchQuery, ignoreCase = true) || product.category.contains(searchQuery, ignoreCase = true)
        matchesNiche && matchesMargin && matchesRegion && matchesSearch
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("product_finder_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Search & Title
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "AI Product Finder & Winner Hunter",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Real-time algorithmic scoring across margins, viral velocity, and supplier fulfillment reliability.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search products, keywords, categories...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SnapGold,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }
        }

        // Filter Chips Row
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Target Market:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                val regions = listOf("All" to "🌍 All", "AE" to "🇦🇪 UAE", "SA" to "🇸🇦 Saudi Arabia", "IN" to "🇮🇳 India", "US" to "🇺🇸 USA", "EU" to "🇪🇺 Europe")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(regions) { (code, label) ->
                        val isSelected = selectedRegionFilter == code
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedRegionFilter = code },
                            label = { Text(label) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SnapGoldContainer,
                                selectedLabelColor = SnapGold
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) SnapGold else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        )
                    }
                }

                Text(text = "Niche & Category:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                val niches = listOf("All", "Ergonomics", "Beauty", "Electronics", "Pet")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(niches) { niche ->
                        val isSelected = selectedNicheFilter == niche
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedNicheFilter = niche },
                            label = { Text(niche) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SnapVioletContainer,
                                selectedLabelColor = SnapViolet
                            )
                        )
                    }
                }
            }
        }

        // Results Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Discovered Products (${filteredProducts.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Sorted by Opportunity Score",
                    style = MaterialTheme.typography.bodySmall,
                    color = SnapGold
                )
            }
        }

        // Product Cards List
        items(filteredProducts) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectProduct(product) }
                    .testTag("finder_product_card_${product.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(
                    1.dp,
                    if (product.isWinnerProduct) SnapGold.copy(alpha = 0.6f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Header badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                            if (product.isWinnerProduct) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = SnapGold,
                                    modifier = Modifier.padding(end = 4.dp)
                                ) {
                                    Text(
                                        text = "★ WINNER",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                        color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text(
                                text = product.category,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        OpportunityScoreBadge(score = product.opportunityScore)
                    }

                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )

                    // Market & Price Matrix
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Suggested Retail", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(product.getFormattedMarketPrice("AE"), style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                            }
                            Divider(modifier = Modifier.height(24.dp).width(1.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Net Margin", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${product.targetMarginPct}%", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                            }
                            Divider(modifier = Modifier.height(24.dp).width(1.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Shipping", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${product.deliveryDays} Days", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }

                    // Recommended Market Flags
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Recommended:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            product.recommendedMarkets.forEach { mCode ->
                                CountryFlagBadge(marketCode = mCode)
                            }
                        }

                        Button(
                            onClick = { onSelectProduct(product) },
                            colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.RocketLaunch, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Launch Studio", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }
    }
}
