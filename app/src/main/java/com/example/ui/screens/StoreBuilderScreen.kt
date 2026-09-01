package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.data.model.StoreProfile
import com.example.ui.components.BrandLogoHeader
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreBuilderScreen(
    storeProfile: StoreProfile,
    products: List<Product>,
    onUpdateStoreProfile: (StoreProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    var storeName by remember { mutableStateOf(storeProfile.name) }
    var tagline by remember { mutableStateOf(storeProfile.tagline) }
    var domain by remember { mutableStateOf(storeProfile.customDomain) }
    var ownerName by remember { mutableStateOf(storeProfile.ownerName) }
    var ownerEmail by remember { mutableStateOf(storeProfile.ownerEmail) }
    var ownerPhone by remember { mutableStateOf(storeProfile.ownerPhone) }
    var selectedThemeColor by remember { mutableStateOf(storeProfile.primaryColorHex) }
    var isPublished by remember { mutableStateOf(storeProfile.isPublished) }

    val themeColors = listOf("#E5A93C", "#8B5CF6", "#10B981", "#06B6D4", "#F43F5E")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("store_builder_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "AI Storefront Builder",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "High-converting headless e-commerce store with instant edge deployment.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isPublished) SnapEmeraldContainer.copy(alpha = 0.4f) else SnapAmber.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, if (isPublished) SnapEmerald else SnapAmber)
                ) {
                    Text(
                        text = if (isPublished) "STOREFRONT LIVE" else "DRAFT",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (isPublished) SnapEmerald else SnapAmber,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Live Mobile Storefront Preview Frame
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F121A)),
                border = BorderStroke(2.dp, SnapGold.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Mobile Top Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🌐 https://$domain",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = SnapGold, modifier = Modifier.size(16.dp))
                        }
                    }

                    // Store Hero Banner
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF181F2E),
                        border = BorderStroke(1.dp, Color(0xFF2C3852))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = storeName,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = Color.White
                            )
                            Text(
                                text = tagline,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                color = SnapGoldLight
                            )
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("Shop Launch Special", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                            }
                        }
                    }

                    // Store Products Mini Grid
                    Text("Featured Winners", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.White)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        products.take(2).forEach { p ->
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF141924),
                                border = BorderStroke(1.dp, Color(0xFF242C3E))
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(p.name, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = Color.White, maxLines = 1)
                                    Text(p.getFormattedMarketPrice("AE"), style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                                }
                            }
                        }
                    }

                    // Trust Badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text("🔒 256-Bit SSL", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = Color.Gray)
                        Text("🚀 3-Day Express", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = Color.Gray)
                        Text("💰 30-Day Refund", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = Color.Gray)
                    }
                }
            }
        }

        // Store Customization Controls
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
                        text = "Storefront Settings & Brand Identity",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    OutlinedTextField(
                        value = storeName,
                        onValueChange = { storeName = it },
                        label = { Text("Store Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = tagline,
                        onValueChange = { tagline = it },
                        label = { Text("Store Tagline") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = domain,
                        onValueChange = { domain = it },
                        label = { Text("Custom Domain") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 4.dp))

                    Text(
                        text = "Store Owner & Executive Contact",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = SnapGold
                    )

                    OutlinedTextField(
                        value = ownerName,
                        onValueChange = { ownerName = it },
                        label = { Text("Owner Name") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = SnapGold) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = ownerEmail,
                        onValueChange = { ownerEmail = it },
                        label = { Text("Owner Email ID") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = SnapGold) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = ownerPhone,
                        onValueChange = { ownerPhone = it },
                        label = { Text("Contact Number") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = SnapGold) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 4.dp))

                    Text("Brand Accent Theme:", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        themeColors.forEach { hex ->
                            val color = Color(android.graphics.Color.parseColor(hex))
                            val isSelected = selectedThemeColor == hex
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .clickable { selectedThemeColor = hex }
                                    .then(
                                        if (isSelected) Modifier.border(2.dp, Color.White, CircleShape)
                                        else Modifier
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }

                    Button(
                        onClick = {
                            val updated = storeProfile.copy(
                                name = storeName,
                                tagline = tagline,
                                customDomain = domain,
                                ownerName = ownerName,
                                ownerEmail = ownerEmail,
                                ownerPhone = ownerPhone,
                                primaryColorHex = selectedThemeColor,
                                isPublished = true
                            )
                            onUpdateStoreProfile(updated)
                            isPublished = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.CloudDone, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save & Deploy to Live Edge", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
