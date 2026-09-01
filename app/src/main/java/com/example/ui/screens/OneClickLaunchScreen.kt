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
import com.example.ui.components.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneClickLaunchScreen(
    product: Product?,
    onLaunchConfirm: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (product == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Please select a product to launch.")
        }
        return
    }

    var activeTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Listing & Copy", "Multi-Market Pricing", "SEO & Metadata", "Ad Creatives & UGC", "Email & Social")

    var editableTitle by remember { mutableStateOf(product.name) }
    var editableDesc by remember { mutableStateOf(product.description) }
    var editableAdHeadline by remember { mutableStateOf(product.adHeadline) }
    var isPublished by remember { mutableStateOf(product.isLaunched) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("one_click_launch_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }

                Text(
                    text = "AI Launch Studio",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isPublished) SnapEmeraldContainer.copy(alpha = 0.4f) else SnapGoldContainer.copy(alpha = 0.4f),
                    border = BorderStroke(1.dp, if (isPublished) SnapEmerald else SnapGold)
                ) {
                    Text(
                        text = if (isPublished) "LIVE IN STORE" else "READY TO LAUNCH",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (isPublished) SnapEmerald else SnapGold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Product Banner Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.4f))
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
                            color = SnapVioletContainer.copy(alpha = 0.4f)
                        ) {
                            Text(
                                text = product.niche,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapViolet,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        OpportunityScoreBadge(score = product.opportunityScore)
                    }

                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Estimated Net Margin: ${product.targetMarginPct}%",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapEmerald
                        )
                        Text(
                            text = "Fulfillment: ${product.deliveryDays} Days",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Launch Tabs
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

        // Tab 0: Listing & Copy
        if (activeTab == 0) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "AI High-Converting Product Copy",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapGold
                        )

                        OutlinedTextField(
                            value = editableTitle,
                            onValueChange = { editableTitle = it },
                            label = { Text("Product Title") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = editableDesc,
                            onValueChange = { editableDesc = it },
                            label = { Text("Product Description") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Text("Key Benefits:", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        product.benefits.forEach { benefit ->
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(16.dp))
                                Text(benefit, style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Text("Bullet Points:", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        product.bulletPoints.forEach { pt ->
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
                                Text("•", fontWeight = FontWeight.Bold, color = SnapGold)
                                Text(pt, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }

        // Tab 1: Multi-Market Dynamic Pricing
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
                            text = "Multi-Currency Dynamic Pricing Engine",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapGold
                        )
                        Text(
                            text = "Automated tax/duties, landed freight, and ad CPA threshold calculations for each target region.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        val markets = listOf(
                            Triple("AE", "🇦🇪 UAE (AED)", "AED 99.00"),
                            Triple("SA", "🇸🇦 Saudi Arabia (SAR)", "SAR 109.00"),
                            Triple("IN", "🇮🇳 India (INR)", "₹1,799.00"),
                            Triple("US", "🇺🇸 United States (USD)", "$34.99"),
                            Triple("GB", "🇬🇧 United Kingdom (GBP)", "£29.99"),
                            Triple("EU", "🇪🇺 Europe (EUR)", "€32.99")
                        )

                        markets.forEach { (mCode, name, price) ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(name, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                        Text("Target Margin: ${product.targetMarginPct}%", style = MaterialTheme.typography.bodySmall, color = SnapEmerald)
                                    }
                                    Text(
                                        text = price,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = SnapGold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Tab 2: SEO & Metadata
        if (activeTab == 2) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "AI SEO & Google Ranking Metadata",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapGold
                        )

                        OutlinedTextField(
                            value = product.seoTitle,
                            onValueChange = {},
                            label = { Text("SEO Page Title") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = product.seoDescription,
                            onValueChange = {},
                            label = { Text("Meta Description") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Text("Target Keywords:", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(product.keywords) { kw ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = SnapVioletContainer.copy(alpha = 0.3f),
                                    border = BorderStroke(1.dp, SnapViolet.copy(alpha = 0.4f))
                                ) {
                                    Text(kw, style = MaterialTheme.typography.labelSmall, color = SnapViolet, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Tab 3: Ad Creatives & UGC
        if (activeTab == 3) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Meta & TikTok Ad Copy & UGC Video Script",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapGold
                        )

                        OutlinedTextField(
                            value = editableAdHeadline,
                            onValueChange = { editableAdHeadline = it },
                            label = { Text("Ad Headline (Hook)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = product.adPrimaryText,
                            onValueChange = {},
                            label = { Text("Ad Primary Text") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2,
                            shape = RoundedCornerShape(10.dp)
                        )

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text("🎬 TikTok / Reels UGC Script Concept:", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = SnapViolet)
                                Text("Hook: ${product.videoScriptHook}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                                Text("Storyboard: ${product.videoScriptUgc}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }

        // Tab 4: Email & Social
        if (activeTab == 4) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Email Flow & Social Captions",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapGold
                        )

                        OutlinedTextField(
                            value = product.emailSubject,
                            onValueChange = {},
                            label = { Text("Launch Email Subject") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = product.emailBody,
                            onValueChange = {},
                            label = { Text("Launch Email Body") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }
        }

        // 1-Click Launch Action Button
        item {
            Button(
                onClick = {
                    isPublished = true
                    onLaunchConfirm(product.id)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("one_click_launch_publish_btn"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPublished) SnapEmerald else SnapGold,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = if (isPublished) Icons.Default.CheckCircle else Icons.Default.RocketLaunch,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isPublished) "Published to Storefront (Live)" else "1-Click Launch This Product Now",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
