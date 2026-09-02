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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.data.repository.DemoDataProvider
import com.example.ui.components.*
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NationalBuyerProductScreen(
    products: List<Product>,
    selectedProduct: Product?,
    onSelectProduct: (Product) -> Unit,
    onPlaceOrder: (Order) -> Unit,
    onAddToCart: ((Product) -> Unit)? = null,
    onSwitchToInternational: () -> Unit,
    onBackToStorefront: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeProduct by remember(selectedProduct, products) {
        mutableStateOf(selectedProduct ?: products.firstOrNull() ?: DemoDataProvider.sampleProducts.first())
    }

    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    var selectedColorVariant by remember { mutableStateOf("Space Grey") }
    var quantity by remember { mutableIntStateOf(1) }
    var pincodeInput by remember { mutableStateOf("110001") }
    var pincodeChecked by remember { mutableStateOf(true) }
    var activeTab by remember { mutableIntStateOf(0) } // 0: Overview, 1: Specs, 2: Delivery & GST, 3: Reviews

    // Checkout BottomSheet & Success Dialog
    var showCheckoutModal by remember { mutableStateOf(false) }
    var showCartAddedNotification by remember { mutableStateOf(false) }
    var completedOrder by remember { mutableStateOf<Order?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Checkout Form state
    var buyerName by remember { mutableStateOf("Rohit Sharma") }
    var buyerPhone by remember { mutableStateOf("+91 98765 43210") }
    var buyerAddress by remember { mutableStateOf("Flat 402, Royal Palms, MG Road") }
    var buyerCity by remember { mutableStateOf("New Delhi") }
    var buyerPincode by remember { mutableStateOf("110001") }
    var selectedPaymentMethod by remember { mutableStateOf("UPI (Google Pay / PhonePe)") }

    val nationalPriceINR = activeProduct.getMarketSellingPrice("IN")
    val mrpINR = kotlin.math.round(nationalPriceINR * 1.65 / 10.0) * 10.0 - 1.0
    val savingsINR = mrpINR - nationalPriceINR
    val savingsPct = ((savingsINR / mrpINR) * 100).toInt()

    val categories = listOf("All", "Smart Fitness", "Tech Gadgets", "Home & Living", "Health & Wellness")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("national_buyer_product_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // --- Top National Header Banner ---
        item {
            Surface(
                color = Color(0xFF0F1523),
                border = BorderStroke(1.dp, Color(0xFF1E293B)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
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
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF1E293B)
                            ) {
                                Text(
                                    text = "🇮🇳 INDIA DOMESTIC",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    ),
                                    color = SnapGold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = "National Buyer Store",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        // Switch to International Button
                        OutlinedButton(
                            onClick = onSwitchToInternational,
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.6f))
                        ) {
                            Icon(Icons.Default.Public, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Global Store 🌐", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                        }
                    }

                    // Value propositions bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.Bolt, contentDescription = null, tint = SnapGold, modifier = Modifier.size(13.dp))
                            Text("24-48h Express Courier", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(13.dp))
                            Text("100% Genuine • BIS Certified", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = SnapGoldLight, modifier = Modifier.size(13.dp))
                            Text("GST Invoice Included", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        // --- Product Quick Selector Carousel ---
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Select National Product Catalog Item:",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(products) { prod ->
                        val isSelected = prod.id == activeProduct.id
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    activeProduct = prod
                                    onSelectProduct(prod)
                                },
                            color = if (isSelected) SnapGoldContainer.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(
                                if (isSelected) 1.5.dp else 1.dp,
                                if (isSelected) SnapGold else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) SnapGold else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingBag,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.Black else Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = prod.name,
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (isSelected) SnapGold else MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "₹${prod.getMarketSellingPrice("IN").toInt()}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                        color = SnapEmerald
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // --- Product Hero Display & Visual Preview ---
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Badge Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SnapEmeraldContainer.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.6f))
                        ) {
                            Text(
                                text = "⚡ IN STOCK - DISPATCHES IN 12 HOURS",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, fontSize = 9.sp),
                                color = SnapEmerald,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SnapGoldContainer.copy(alpha = 0.4f),
                            border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.5f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = SnapGold, modifier = Modifier.size(12.dp))
                                Text(
                                    text = "4.9 (1,482 verified Indian buyers)",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                    color = SnapGold
                                )
                            }
                        }
                    }

                    // Product Title & Niche
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = activeProduct.name,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${activeProduct.category} • SKU: ${activeProduct.sku.ifBlank { "SNAP-IN-${activeProduct.id.takeLast(4)}" }}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Crystal Clear Product Photo Banner
                    ProductImageCard(
                        productId = activeProduct.id,
                        productName = activeProduct.name,
                        category = activeProduct.category,
                        height = 240.dp,
                        showBadge = true,
                        badgeText = "🇮🇳 100% GENUINE • EXPRESS DISPATCH"
                    )

                    // National Price Block
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF131A29),
                        border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.35f))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = "₹${nationalPriceINR.toInt()}",
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                                    color = SnapGold
                                )
                                Text(
                                    text = "M.R.P. ₹${mrpINR.toInt()}",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        textDecoration = TextDecoration.LineThrough
                                    ),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SnapEmerald
                                ) {
                                    Text(
                                        text = "$savingsPct% OFF",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Black,
                                            fontSize = 11.sp
                                        ),
                                        color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.LocalOffer, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(14.dp))
                                Text(
                                    text = "Inclusive of all GST (18%) + Free Fast Courier across India",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 11.sp),
                                    color = SnapEmerald
                                )
                            }

                            // Bank & UPI Cashback badge
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF1B2438),
                                border = BorderStroke(1.dp, Color(0xFF2A3650))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = SnapGold, modifier = Modifier.size(16.dp))
                                    Text(
                                        text = "Flat ₹150 Instant Cashback on UPI / RuPay payments at checkout",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }

                    // Variant & Color Selector
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Color / Edition: $selectedColorVariant",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        val variants = listOf("Space Grey", "Matte Obsidian", "Titanium Gold", "Alpine White")
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(variants) { variant ->
                                val isChosen = selectedColorVariant == variant
                                FilterChip(
                                    selected = isChosen,
                                    onClick = { selectedColorVariant = variant },
                                    label = { Text(variant) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SnapGoldContainer,
                                        selectedLabelColor = SnapGold
                                    )
                                )
                            }
                        }
                    }

                    // Quantity Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Quantity:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = { if (quantity > 1) quantity-- },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surface)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
                            }
                            Text(
                                text = "$quantity",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            IconButton(
                                onClick = { if (quantity < 10) quantity++ },
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surface)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    // Pincode & Delivery Checker
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = SnapGold, modifier = Modifier.size(16.dp))
                                    Text(
                                        text = "Check Delivery to Pincode",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = pincodeInput,
                                    onValueChange = { if (it.length <= 6) pincodeInput = it },
                                    placeholder = { Text("e.g. 110001") },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(8.dp),
                                    singleLine = true
                                )
                                Button(
                                    onClick = { pincodeChecked = true },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black)
                                ) {
                                    Text("Check", fontWeight = FontWeight.Bold)
                                }
                            }

                            if (pincodeChecked) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(14.dp))
                                    Text(
                                        text = "Delivering to $pincodeInput by Tomorrow 4:00 PM • Cash on Delivery Available",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SnapEmerald)
                                    )
                                }
                            }
                        }
                    }

                    // Action Buttons: Buy Now & Add to Cart
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                onAddToCart?.invoke(activeProduct)
                                showCartAddedNotification = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("national_add_to_cart_btn"),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.5.dp, SnapGold)
                        ) {
                            Icon(Icons.Default.AddShoppingCart, contentDescription = null, tint = SnapGold, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Add to Cart", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                        }

                        Button(
                            onClick = {
                                showCheckoutModal = true
                            },
                            modifier = Modifier
                                .weight(1.2f)
                                .height(48.dp)
                                .testTag("national_buy_now_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SnapGold,
                                contentColor = Color.Black
                            )
                        ) {
                            Icon(Icons.Default.FlashOn, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "Buy Now • ₹${(nationalPriceINR * quantity).toInt()}",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black)
                            )
                        }
                    }
                }
            }
        }

        // --- National Trust Highlights Strip ---
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.AssignmentReturn, contentDescription = null, tint = SnapGold, modifier = Modifier.size(22.dp))
                        Text("7-Day Easy Return", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        Text("No questions asked", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(22.dp))
                        Text("1-Year Warranty", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        Text("National pan-India", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.Payments, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(22.dp))
                        Text("COD Available", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        Text("Pay upon delivery", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // --- Navigation Tabs: Details, Specs, GST, Reviews ---
        item {
            PrimaryTabRow(
                selectedTabIndex = activeTab,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = SnapGold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Tab(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    text = { Text("Overview & Features", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)) }
                )
                Tab(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    text = { Text("Tech Specs", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)) }
                )
                Tab(
                    selected = activeTab == 2,
                    onClick = { activeTab = 2 },
                    text = { Text("GST & Delivery", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)) }
                )
                Tab(
                    selected = activeTab == 3,
                    onClick = { activeTab = 3 },
                    text = { Text("Buyer Reviews (4.9★)", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)) }
                )
            }
        }

        // --- Tab Contents ---
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    when (activeTab) {
                        0 -> {
                            Text(
                                text = "Product Description & Key Highlights",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = SnapGold
                            )
                            Text(
                                text = activeProduct.description.ifBlank {
                                    "Engineered for high performance with premium aerospace-grade materials. Includes intelligent sensor integration, rapid charging technology, and whisper-quiet operation. Specifically calibrated for national domestic voltage standards."
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            if (activeProduct.benefits.isNotEmpty()) {
                                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                Text(
                                    text = "Why Indian Shoppers Love This:",
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                                )
                                activeProduct.benefits.forEach { benefit ->
                                    Row(
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(16.dp))
                                        Text(text = benefit, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }
                        }

                        1 -> {
                            Text(
                                text = "Technical Specifications",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = SnapGold
                            )
                            val specs = if (activeProduct.specifications.isNotEmpty()) {
                                activeProduct.specifications
                            } else {
                                mapOf(
                                    "Model / SKU" to activeProduct.sku.ifBlank { "SNAP-IN-2026" },
                                    "Power & Voltage" to "220V - 240V 50Hz (India Standard 3-Pin)",
                                    "Battery / Run Time" to "Up to 14 Hours Continuous",
                                    "Material" to "Aerospace Aluminum & High-Impact Polycarbonate",
                                    "Certifications" to "BIS (Bureau of Indian Standards), CE, RoHS",
                                    "Warranty" to "1-Year Pan-India Replacement"
                                )
                            }

                            specs.forEach { (key, value) ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = key, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                }
                                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                            }
                        }

                        2 -> {
                            Text(
                                text = "National GST Breakdown & Dispatch Hubs",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = SnapGold
                            )

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF141C2B),
                                border = BorderStroke(1.dp, Color(0xFF22304A))
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Item Base Amount:", style = MaterialTheme.typography.bodySmall)
                                        Text("₹${(nationalPriceINR * 0.82).toInt()}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("CGST (9%) + SGST (9%) / IGST (18%):", style = MaterialTheme.typography.bodySmall)
                                        Text("₹${(nationalPriceINR * 0.18).toInt()}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = SnapGold))
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("National Express Delivery (BlueDart/Delhivery):", style = MaterialTheme.typography.bodySmall)
                                        Text("FREE", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = SnapEmerald))
                                    }
                                    Divider(color = Color(0xFF2A3A5A))
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Total Payable:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                        Text("₹${nationalPriceINR.toInt()}", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black, color = SnapGold))
                                    }
                                }
                            }

                            Text(
                                text = "Dispatch Hubs: Bhiwandi (Mumbai), Gurgaon (Delhi-NCR), Hoskote (Bengaluru).",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        3 -> {
                            Text(
                                text = "Verified Indian Customer Reviews",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = SnapGold
                            )

                            val reviews = listOf(
                                Triple("Rahul Sharma (Mumbai)", "⭐⭐⭐⭐⭐", "Arrived within 24 hours via BlueDart! Quality is phenomenal, completely matches the video. UPI payment was instant."),
                                Triple("Pooja Verma (Bengaluru)", "⭐⭐⭐⭐⭐", "100% genuine product. Tested with my iPhone and smart watch, works like magic. Very impressed with the packaging."),
                                Triple("Amit Patel (Ahmedabad)", "⭐⭐⭐⭐⭐", "Best purchase this month. GST invoice with business PAN was downloaded immediately. 5 stars for Within A Snap!")
                            )

                            reviews.forEach { (reviewer, rating, comment) ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                Text(text = reviewer, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                                Icon(Icons.Default.Verified, contentDescription = "Verified Buyer", tint = SnapGold, modifier = Modifier.size(13.dp))
                                            }
                                            Text(text = rating, style = MaterialTheme.typography.labelSmall)
                                        }
                                        Text(text = comment, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // --- Interactive National Checkout BottomSheet ---
    if (showCheckoutModal) {
        ModalBottomSheet(
            onDismissRequest = { showCheckoutModal = false },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "🇮🇳 Fast National Checkout",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                            color = SnapGold
                        )
                        Text(
                            text = "Item: ${activeProduct.name} (Qty: $quantity)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "₹${(nationalPriceINR * quantity).toInt()}",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                        color = SnapEmerald
                    )
                }

                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                Text(
                    text = "Delivery Address & Contact Details",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )

                OutlinedTextField(
                    value = buyerName,
                    onValueChange = { buyerName = it },
                    label = { Text("Full Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = SnapGold) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = buyerPhone,
                        onValueChange = { buyerPhone = it },
                        label = { Text("Phone (For SMS tracking)") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = SnapGold) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    OutlinedTextField(
                        value = buyerPincode,
                        onValueChange = { buyerPincode = it },
                        label = { Text("Pincode") },
                        modifier = Modifier.weight(0.7f),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                OutlinedTextField(
                    value = buyerAddress,
                    onValueChange = { buyerAddress = it },
                    label = { Text("Full Street Address & Landmark") },
                    leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = SnapGold) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Text(
                    text = "Payment Method:",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )

                val paymentOptions = listOf(
                    "UPI (Google Pay / PhonePe / Paytm / QR)",
                    "RuPay / Credit / Debit Card",
                    "NetBanking (SBI, HDFC, ICICI, Axis)",
                    "Cash on Delivery (Pay upon arrival)"
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    paymentOptions.forEach { method ->
                        val isSelected = selectedPaymentMethod == method
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { selectedPaymentMethod = method },
                            color = if (isSelected) SnapGoldContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(
                                if (isSelected) 1.5.dp else 1.dp,
                                if (isSelected) SnapGold else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedPaymentMethod = method },
                                    colors = RadioButtonDefaults.colors(selectedColor = SnapGold)
                                )
                                Text(
                                    text = method,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) SnapGold else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        val newOrderId = "ORD-IN-${(10000..99999).random()}"
                        val nowFormatted = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
                        val totalUSD = (nationalPriceINR * quantity) / 83.5
                        val order = Order(
                            id = newOrderId,
                            customerName = buyerName,
                            customerEmail = "${buyerName.lowercase().replace(" ", "")}@gmail.com",
                            customerCity = "$buyerCity, IN ($buyerPincode)",
                            marketCode = "IN",
                            productName = activeProduct.name,
                            quantity = quantity,
                            revenueUSD = totalUSD,
                            costUSD = totalUSD * 0.58,
                            profitUSD = totalUSD * 0.42,
                            paymentStatus = if (selectedPaymentMethod.contains("Cash on Delivery")) PaymentStatus.PENDING else PaymentStatus.PAID,
                            fulfillmentStatus = FulfillmentStatus.PROCESSING,
                            shippingStatus = "Order Confirmed — Dispatched from Bhiwandi Hub",
                            trackingNumber = "BD-IN-${(10000000..99999999).random()}",
                            date = nowFormatted,
                            riskScore = 5,
                            paymentMethod = selectedPaymentMethod
                        )
                        completedOrder = order
                        onPlaceOrder(order)
                        showCheckoutModal = false
                        showSuccessDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("national_confirm_order_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black)
                ) {
                    Text(
                        text = "Complete Order • ₹${(nationalPriceINR * quantity).toInt()}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
                    )
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }

    // --- Order Confirmation & GST Invoice Dialog ---
    if (showSuccessDialog && completedOrder != null) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            icon = {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(SnapEmerald),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
                }
            },
            title = {
                Text(
                    text = "National Order Placed Successfully!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = SnapGold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Thank you, ${completedOrder?.customerName}! Your order has been registered in the Within A Snap store system and sent to the Bhiwandi fulfillment hub.",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Order ID: ${completedOrder?.id}", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = SnapGold))
                            Text("Tracking: ${completedOrder?.trackingNumber} (BlueDart)", style = MaterialTheme.typography.labelSmall)
                            Text("Delivery To: ${completedOrder?.customerCity}", style = MaterialTheme.typography.labelSmall)
                            Text("Payment Mode: ${completedOrder?.paymentMethod}", style = MaterialTheme.typography.labelSmall)
                            Text("GST Invoice #INV-2026-IN generated automatically.", style = MaterialTheme.typography.labelSmall.copy(color = SnapEmerald))
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black)
                ) {
                    Text("Done & Continue Shopping", fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    if (showCartAddedNotification) {
        AlertDialog(
            onDismissRequest = { showCartAddedNotification = false },
            icon = { Icon(Icons.Default.ShoppingCartCheckout, contentDescription = null, tint = SnapGold, modifier = Modifier.size(32.dp)) },
            title = { Text("Added to Cart!", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
            text = {
                Text(
                    text = "${activeProduct.name} has been added to your cart. You can continue shopping or proceed to checkout.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showCartAddedNotification = false
                        showCheckoutModal = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black)
                ) {
                    Text("Proceed to Checkout", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCartAddedNotification = false }) {
                    Text("Keep Shopping", color = SnapGold)
                }
            }
        )
    }
}
