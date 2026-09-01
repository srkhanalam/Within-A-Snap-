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
fun InternationalBuyerProductScreen(
    products: List<Product>,
    selectedProduct: Product?,
    onSelectProduct: (Product) -> Unit,
    onPlaceOrder: (Order) -> Unit,
    onSwitchToNational: () -> Unit,
    onBackToStorefront: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeProduct by remember(selectedProduct, products) {
        mutableStateOf(selectedProduct ?: products.firstOrNull() ?: DemoDataProvider.sampleProducts.first())
    }

    var selectedMarketCode by remember { mutableStateOf("AE") }
    var selectedLanguage by remember { mutableStateOf("English") }
    var selectedPlugStandard by remember { mutableStateOf("UK/UAE 230V (Type G)") }
    var quantity by remember { mutableIntStateOf(1) }
    var activeTab by remember { mutableIntStateOf(0) } // 0: Overview, 1: Global Specs, 2: Duty & Customs DDP, 3: Worldwide Reviews

    // Checkout BottomSheet & Confirmation
    var showCheckoutModal by remember { mutableStateOf(false) }
    var completedOrder by remember { mutableStateOf<Order?>(null) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // International Buyer Checkout form
    var buyerName by remember { mutableStateOf("Tariq Al-Mansoor") }
    var buyerEmail by remember { mutableStateOf("tariq.mansoor@dubaiholdings.ae") }
    var buyerPhone by remember { mutableStateOf("+971 50 123 4567") }
    var buyerCountry by remember { mutableStateOf("United Arab Emirates") }
    var buyerCity by remember { mutableStateOf("Dubai") }
    var buyerAddress by remember { mutableStateOf("Villa 14, Palm Jumeirah Crescent") }
    var customsIdNumber by remember { mutableStateOf("784-1992-1234567-1") }
    var selectedPaymentGateway by remember { mutableStateOf("Apple Pay / Google Pay") }

    val marketConfigs = listOf(
        Triple("AE", "🇦🇪 UAE (AED)", 3.67),
        Triple("SA", "🇸🇦 Saudi Arabia (SAR)", 3.75),
        Triple("US", "🇺🇸 USA (USD $)", 1.0),
        Triple("GB", "🇬🇧 UK (GBP £)", 0.79),
        Triple("EU", "🇪🇺 Europe (EUR €)", 0.92)
    )

    val currentMarketTriple = marketConfigs.firstOrNull { it.first == selectedMarketCode } ?: marketConfigs.first()
    val exchangeRate = currentMarketTriple.third

    val baseUSD = activeProduct.getMarketSellingPrice("US")
    val itemPriceLocal = when (selectedMarketCode) {
        "AE" -> activeProduct.getMarketSellingPrice("AE")
        "SA" -> activeProduct.getMarketSellingPrice("SA")
        "US" -> baseUSD
        "GB" -> activeProduct.getMarketSellingPrice("GB")
        "EU" -> activeProduct.getMarketSellingPrice("EU")
        else -> baseUSD * exchangeRate
    }

    val currencyPrefix = when (selectedMarketCode) {
        "AE" -> "AED "
        "SA" -> "SAR "
        "US" -> "$"
        "GB" -> "£"
        "EU" -> "€"
        else -> "$"
    }

    val shippingFeeLocal = 0.0 // Free Priority Air Cargo
    val dutyAndTaxRate = when (selectedMarketCode) {
        "AE" -> 0.05
        "SA" -> 0.15
        "US" -> 0.00
        "GB" -> 0.20
        "EU" -> 0.19
        else -> 0.08
    }
    val dutyAmountLocal = itemPriceLocal * dutyAndTaxRate
    val totalLandedCost = (itemPriceLocal * quantity) + (dutyAmountLocal * quantity)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("international_buyer_product_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // --- Top Global Buyer Header ---
        item {
            Surface(
                color = Color(0xFF0D1424),
                border = BorderStroke(1.dp, Color(0xFF1D283E)),
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
                                    text = "🌐 GLOBAL CROSS-BORDER",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 1.sp
                                    ),
                                    color = SnapCyan,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = "International Buyer Store",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        // Switch to National Button
                        OutlinedButton(
                            onClick = onSwitchToNational,
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.6f))
                        ) {
                            Icon(Icons.Default.Flag, contentDescription = null, tint = SnapGold, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("India Domestic 🇮🇳", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                        }
                    }

                    // Global Country & Currency Switcher Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Destination & Currency:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(marketConfigs) { (code, label, _) ->
                                val isSelected = selectedMarketCode == code
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedMarketCode = code },
                                    label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SnapCyanContainer,
                                        selectedLabelColor = SnapCyan
                                    )
                                )
                            }
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
                    text = "Select International Product Catalog Item:",
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
                            color = if (isSelected) SnapCyanContainer.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(
                                if (isSelected) 1.5.dp else 1.dp,
                                if (isSelected) SnapCyan else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
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
                                        .background(if (isSelected) SnapCyan else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Public,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.Black else Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = prod.name,
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (isSelected) SnapCyan else MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "$currencyPrefix${prod.getMarketSellingPrice(selectedMarketCode).toInt()}",
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
                            color = SnapCyanContainer.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.6f))
                        ) {
                            Text(
                                text = "✈️ DHL EXPRESS AIR FREIGHT • 3-5 DAYS",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, fontSize = 9.sp),
                                color = SnapCyan,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SnapEmeraldContainer.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.6f))
                        ) {
                            Text(
                                text = "🛡️ DDP - DUTIES PRE-PAID",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 9.sp),
                                color = SnapEmerald,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

                    // Product Title & Global SKU
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = activeProduct.name,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${activeProduct.category} • Global Edition • SKU: GBL-${activeProduct.id.takeLast(4)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // International Multi-Currency Price Block
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF10192A),
                        border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.35f))
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
                                    text = "$currencyPrefix${itemPriceLocal.toInt()}",
                                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                                    color = SnapCyan
                                )
                                Text(
                                    text = "MSRP $currencyPrefix${(itemPriceLocal * 1.5).toInt()}",
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
                                        text = "35% OFF",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Black,
                                            fontSize = 11.sp
                                        ),
                                        color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            // Landed Cost Transparency Breakdown
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF162238),
                                border = BorderStroke(1.dp, Color(0xFF263958))
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Item Base:", style = MaterialTheme.typography.labelSmall)
                                        Text("$currencyPrefix${itemPriceLocal.toInt()}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("DHL Express Air Cargo:", style = MaterialTheme.typography.labelSmall)
                                        Text("FREE (Global Promo)", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SnapEmerald))
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Pre-cleared Customs & VAT (${(dutyAndTaxRate * 100).toInt()}% DDP):", style = MaterialTheme.typography.labelSmall)
                                        Text("$currencyPrefix${dutyAmountLocal.toInt()}", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SnapCyan))
                                    }
                                    Divider(color = Color(0xFF2E466E))
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Guaranteed Landed Price:", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                                        Text("$currencyPrefix${(itemPriceLocal + dutyAmountLocal).toInt()}", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black, color = SnapCyan))
                                    }
                                }
                            }

                            Text(
                                text = "✨ Zero Surprise Guarantee: No additional fees or customs duties required upon doorstep delivery.",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = SnapEmerald)
                            )
                        }
                    }

                    // International Specs & Plug Selector
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Regional Electrical Standard & Plug:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        val plugs = listOf("UK/UAE 230V (Type G)", "US/CA 110V (Type A/B)", "EU 220V (Type C/F)", "Universal Auto-Switch")
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(plugs) { plug ->
                                val isChosen = selectedPlugStandard == plug
                                FilterChip(
                                    selected = isChosen,
                                    onClick = { selectedPlugStandard = plug },
                                    label = { Text(plug) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SnapCyanContainer,
                                        selectedLabelColor = SnapCyan
                                    )
                                )
                            }
                        }
                    }

                    // Language Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Manual & Documentation Language:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        val languages = listOf("English", "العربية (Arabic)", "Français", "Español")
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(languages) { lang ->
                                val isSel = selectedLanguage == lang
                                FilterChip(
                                    selected = isSel,
                                    onClick = { selectedLanguage = lang },
                                    label = { Text(lang, style = MaterialTheme.typography.labelSmall) },
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

                    // Action Buttons: Buy Now & Add to Cart
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                showCheckoutModal = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("international_add_to_cart_btn"),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.5.dp, SnapCyan)
                        ) {
                            Icon(Icons.Default.AddShoppingCart, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Add to Cart", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                        }

                        Button(
                            onClick = {
                                showCheckoutModal = true
                            },
                            modifier = Modifier
                                .weight(1.2f)
                                .height(48.dp)
                                .testTag("international_buy_now_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SnapCyan,
                                contentColor = Color.Black
                            )
                        ) {
                            Icon(Icons.Default.Public, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "Buy Now • $currencyPrefix${totalLandedCost.toInt()}",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Black)
                            )
                        }
                    }
                }
            }
        }

        // --- International Trust Strip ---
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
                        Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(22.dp))
                        Text("DHL Express Cargo", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        Text("3-5 business days", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = SnapGold, modifier = Modifier.size(22.dp))
                        Text("CE / FCC Certified", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        Text("Global compliance", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(22.dp))
                        Text("Global Warranty", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        Text("1-Year Worldwide", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // --- Navigation Tabs ---
        item {
            PrimaryTabRow(
                selectedTabIndex = activeTab,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = SnapCyan,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Tab(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    text = { Text("Product Overview", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)) }
                )
                Tab(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    text = { Text("Global Specs", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)) }
                )
                Tab(
                    selected = activeTab == 2,
                    onClick = { activeTab = 2 },
                    text = { Text("Customs & DDP", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)) }
                )
                Tab(
                    selected = activeTab == 3,
                    onClick = { activeTab = 3 },
                    text = { Text("Global Reviews", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)) }
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
                                text = "International Edition Overview",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = SnapCyan
                            )
                            Text(
                                text = if (selectedLanguage.contains("Arabic")) {
                                    "منتج عالمي متميز مصمم بأعلى معايير الجودة والأداء. يشمل شاحناً ذكياً متوافقاً مع معايير دول مجلس التعاون الخليجي، وتوصيلاً سريعاً عبر الشحن الجوي السريع."
                                } else {
                                    activeProduct.description.ifBlank {
                                        "World-class engineering designed for international buyers with universal voltage auto-adaptation, premium materials, and global aviation-safe battery architecture."
                                    }
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            if (activeProduct.benefits.isNotEmpty()) {
                                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                Text(
                                    text = "Key International Features:",
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                                )
                                activeProduct.benefits.forEach { benefit ->
                                    Row(
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(16.dp))
                                        Text(text = benefit, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                    }
                                }
                            }
                        }

                        1 -> {
                            Text(
                                text = "Universal Technical Specifications",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = SnapCyan
                            )

                            val globalSpecs = mapOf(
                                "Plug & Voltage Standard" to selectedPlugStandard,
                                "Global Certifications" to "CE, FCC, RoHS, ISO 9001, FDA Class 1",
                                "International Air Transport" to "IATA UN38.3 Lithium Battery Certified",
                                "Harmonized Tariff (HS Code)" to "8543.70.9960 (Consumer Electronics)",
                                "Dimensions & Weight" to "21 x 14 x 6 cm / 0.42 kg (0.92 lbs)",
                                "Worldwide Service Hubs" to "Dubai (UAE), Frankfurt (EU), London (UK), Dallas (USA)"
                            )

                            globalSpecs.forEach { (key, value) ->
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
                                text = "Cross-Border Customs & DDP Logistics",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = SnapCyan
                            )

                            Text(
                                text = "Within A Snap utilizes DDP (Delivered Duty Paid) protocols. All import customs clearance, local value-added taxes (VAT), and courier terminal fees are settled in advance by our logistics network.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
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
                                        Text("Courier Partner:", style = MaterialTheme.typography.bodySmall)
                                        Text("DHL Express Air Worldwide", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = SnapCyan))
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Transit Time to ${currentMarketTriple.second}:", style = MaterialTheme.typography.bodySmall)
                                        Text("3 to 5 Business Days", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = SnapEmerald))
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Tracking Protocol:", style = MaterialTheme.typography.bodySmall)
                                        Text("Live GPS & Customs Clearance Pass", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                                    }
                                }
                            }
                        }

                        3 -> {
                            Text(
                                text = "Verified Global Customer Reviews",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = SnapCyan
                            )

                            val globalReviews = listOf(
                                Triple("Tariq Al-Mansoor 🇦🇪 (Dubai)", "⭐⭐⭐⭐⭐", "Arrived in Dubai via DHL in just 3 days! The GCC plug adapter was included in the box. Excellent craftsmanship."),
                                Triple("Fahad Al-Otaibi 🇸🇦 (Riyadh)", "⭐⭐⭐⭐⭐", "ممتاز جداً وسريع التوصيل إلى الرياض. الجودة مذهلة والتغليف فاخر."),
                                Triple("Sarah Miller 🇺🇸 (California)", "⭐⭐⭐⭐⭐", "Zero hidden customs fees when it arrived at my doorstep in Los Angeles. Works seamlessly. Very pleased!"),
                                Triple("Oliver Wright 🇬🇧 (London)", "⭐⭐⭐⭐⭐", "Fast shipping to the UK. VAT was already sorted. Premium build quality, definitely recommended.")
                            )

                            globalReviews.forEach { (reviewer, rating, comment) ->
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
                                            Text(text = reviewer, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
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

    // --- Interactive International Checkout BottomSheet ---
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
                            text = "🌐 International Global Checkout",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                            color = SnapCyan
                        )
                        Text(
                            text = "Item: ${activeProduct.name} (Qty: $quantity)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "$currencyPrefix${totalLandedCost.toInt()}",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                        color = SnapEmerald
                    )
                }

                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                Text(
                    text = "International Recipient & Customs Details",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )

                OutlinedTextField(
                    value = buyerName,
                    onValueChange = { buyerName = it },
                    label = { Text("Full Name (as per Passport / ID)") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = SnapCyan) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = buyerEmail,
                        onValueChange = { buyerEmail = it },
                        label = { Text("Email (for AWB tracking)") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = SnapCyan) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    OutlinedTextField(
                        value = buyerPhone,
                        onValueChange = { buyerPhone = it },
                        label = { Text("Phone") },
                        modifier = Modifier.weight(0.8f),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                OutlinedTextField(
                    value = buyerAddress,
                    onValueChange = { buyerAddress = it },
                    label = { Text("Destination Address, City & Country") },
                    leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = SnapCyan) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                    value = customsIdNumber,
                    onValueChange = { customsIdNumber = it },
                    label = { Text("National ID / Customs Clearance Pass #") },
                    leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = SnapGold) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Text(
                    text = "Global Payment Gateway:",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )

                val gateways = listOf(
                    "Apple Pay / Google Pay Instant",
                    "Credit / Debit Card (Visa, Mastercard, Amex 3D Secure)",
                    "PayPal Express Checkout",
                    "Tabby / Klarna (Pay in 4 interest-free installments)"
                )

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    gateways.forEach { gw ->
                        val isSelected = selectedPaymentGateway == gw
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { selectedPaymentGateway = gw },
                            color = if (isSelected) SnapCyanContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(
                                if (isSelected) 1.5.dp else 1.dp,
                                if (isSelected) SnapCyan else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedPaymentGateway = gw },
                                    colors = RadioButtonDefaults.colors(selectedColor = SnapCyan)
                                )
                                Text(
                                    text = gw,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) SnapCyan else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        val newOrderId = "ORD-INT-${(10000..99999).random()}"
                        val nowFormatted = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
                        val totalUSD = baseUSD * quantity
                        val order = Order(
                            id = newOrderId,
                            customerName = buyerName,
                            customerEmail = buyerEmail,
                            customerCity = "$buyerCity, $selectedMarketCode",
                            marketCode = selectedMarketCode,
                            productName = activeProduct.name,
                            quantity = quantity,
                            revenueUSD = totalUSD,
                            costUSD = totalUSD * 0.54,
                            profitUSD = totalUSD * 0.46,
                            paymentStatus = PaymentStatus.PAID,
                            fulfillmentStatus = FulfillmentStatus.PROCESSING,
                            shippingStatus = "DHL Express Air Freight Pre-Cleared (AWB-${(10000000..99999999).random()})",
                            trackingNumber = "DHL-EXP-${(10000000..99999999).random()}",
                            date = nowFormatted,
                            riskScore = 8,
                            paymentMethod = selectedPaymentGateway
                        )
                        completedOrder = order
                        onPlaceOrder(order)
                        showCheckoutModal = false
                        showSuccessDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("international_confirm_order_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SnapCyan, contentColor = Color.Black)
                ) {
                    Text(
                        text = "Pay $currencyPrefix${totalLandedCost.toInt()} & Place Order",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
                    )
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }

    // --- Order Confirmation & Air Waybill Dialog ---
    if (showSuccessDialog && completedOrder != null) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            icon = {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(SnapCyan),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
                }
            },
            title = {
                Text(
                    text = "International Order Placed!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = SnapCyan
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Thank you, ${completedOrder?.customerName}! Your global cross-border order has been confirmed and submitted for international DHL air dispatch.",
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
                            Text("Order ID: ${completedOrder?.id}", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = SnapCyan))
                            Text("Air Waybill (AWB): ${completedOrder?.trackingNumber}", style = MaterialTheme.typography.labelSmall)
                            Text("Destination: ${completedOrder?.customerCity}", style = MaterialTheme.typography.labelSmall)
                            Text("Payment Gateway: ${completedOrder?.paymentMethod}", style = MaterialTheme.typography.labelSmall)
                            Text("DDP Clearance: Complete (Zero Doorstep Duty)", style = MaterialTheme.typography.labelSmall.copy(color = SnapEmerald))
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapCyan, contentColor = Color.Black)
                ) {
                    Text("Done & Continue Shopping", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}
