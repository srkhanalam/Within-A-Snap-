package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.AutomationMode
import com.example.ui.components.BrandLogoHeader
import com.example.ui.components.CountryFlagBadge
import com.example.ui.components.ThemeSelectorDialog
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SnapViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: SnapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themePreset by viewModel.selectedThemePreset.collectAsStateWithLifecycle()
            WithinASnapTheme(preset = themePreset) {
                MainAppScaffold(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffold(
    viewModel: SnapViewModel,
    modifier: Modifier = Modifier
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val products by viewModel.products.collectAsStateWithLifecycle()
    val orders by viewModel.orders.collectAsStateWithLifecycle()
    val suppliers by viewModel.suppliers.collectAsStateWithLifecycle()
    val campaigns by viewModel.campaigns.collectAsStateWithLifecycle()
    val markets by viewModel.markets.collectAsStateWithLifecycle()
    val recommendations by viewModel.aiRecommendations.collectAsStateWithLifecycle()
    val auditLogs by viewModel.auditLogs.collectAsStateWithLifecycle()
    val autopilotConfig by viewModel.autopilotConfig.collectAsStateWithLifecycle()
    val storeProfile by viewModel.storeProfile.collectAsStateWithLifecycle()
    val competitors by viewModel.competitors.collectAsStateWithLifecycle()
    val abTests by viewModel.abTests.collectAsStateWithLifecycle()
    val supportTickets by viewModel.supportTickets.collectAsStateWithLifecycle()
    val onboardingPlan by viewModel.onboardingPlan.collectAsStateWithLifecycle()
    val selectedMarketCode by viewModel.selectedMarketCode.collectAsStateWithLifecycle()
    val selectedProductForLaunch by viewModel.selectedProductForLaunch.collectAsStateWithLifecycle()
    val selectedBuyerProduct by viewModel.selectedBuyerProduct.collectAsStateWithLifecycle()

    val chatHistory by viewModel.aiChatHistory.collectAsStateWithLifecycle()
    val commandInput by viewModel.aiCommandInput.collectAsStateWithLifecycle()
    val isAiThinking by viewModel.isAiThinking.collectAsStateWithLifecycle()

    val totalRevenue by viewModel.totalRevenueUSD.collectAsStateWithLifecycle()
    val totalProfit by viewModel.totalProfitUSD.collectAsStateWithLifecycle()

    val paymentGateways by viewModel.paymentGateways.collectAsStateWithLifecycle()
    val paymentTransactions by viewModel.paymentTransactions.collectAsStateWithLifecycle()
    val merchantBalances by viewModel.merchantBalances.collectAsStateWithLifecycle()
    val merchantPayouts by viewModel.merchantPayouts.collectAsStateWithLifecycle()
    val paymentSmartRules by viewModel.paymentSmartRules.collectAsStateWithLifecycle()

    val adminSession by viewModel.adminSession.collectAsStateWithLifecycle()
    val adminUsers by viewModel.adminUsers.collectAsStateWithLifecycle()
    val adminAuditTrail by viewModel.adminAuditTrail.collectAsStateWithLifecycle()
    val systemHealth by viewModel.systemHealth.collectAsStateWithLifecycle()
    val telemetryMetrics by viewModel.telemetryMetrics.collectAsStateWithLifecycle()
    val isEmergencyAdFreezeActive by viewModel.isEmergencyAdFreezeActive.collectAsStateWithLifecycle()
    val isEmergencyAutoFulfillPaused by viewModel.isEmergencyAutoFulfillPaused.collectAsStateWithLifecycle()

    // 7 Advanced Operations & Growth StateFlows
    val fraudReports by viewModel.fraudReports.collectAsStateWithLifecycle()
    val invoices by viewModel.invoices.collectAsStateWithLifecycle()
    val shippingLabels by viewModel.shippingLabels.collectAsStateWithLifecycle()
    val packingSlips by viewModel.packingSlips.collectAsStateWithLifecycle()
    val upsellOffers by viewModel.upsellOffers.collectAsStateWithLifecycle()
    val smartBundles by viewModel.smartBundles.collectAsStateWithLifecycle()
    val adSpendAttributions by viewModel.adSpendAttributions.collectAsStateWithLifecycle()
    val multilingualTickets by viewModel.multilingualTickets.collectAsStateWithLifecycle()
    val broadcastCampaigns by viewModel.broadcastCampaigns.collectAsStateWithLifecycle()
    val customerSegments by viewModel.customerSegments.collectAsStateWithLifecycle()
    val priceSurgeEvents by viewModel.priceSurgeEvents.collectAsStateWithLifecycle()
    val inventoryGuards by viewModel.inventoryGuards.collectAsStateWithLifecycle()
    val userLocations by viewModel.userLocations.collectAsStateWithLifecycle()
    val currentUserLocation by viewModel.currentUserLocation.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showMarketSelectorDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    val selectedThemePreset by viewModel.selectedThemePreset.collectAsStateWithLifecycle()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(310.dp),
                drawerContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BrandLogoHeader(compact = false, showTagline = true, taglineText = "FIND • LAUNCH • SELL")

                    // Controls & Preferences Panel: Owner, Market, AI Command & Theme
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp)),
                        color = Color(0xFF131926),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // 1. Owner Profile & Executive Contact
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(SnapGold),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "PA",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                                        color = Color.Black
                                    )
                                }

                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = storeProfile.ownerName,
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Icon(
                                            Icons.Default.Verified,
                                            contentDescription = "Verified Owner",
                                            tint = SnapGold,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                    Text(
                                        text = "Platform Owner & Director",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = SnapGoldLight
                                    )
                                }
                            }

                            Divider(color = Color(0xFF222B3D), modifier = Modifier.padding(vertical = 2.dp))

                            // 1.1 Owner Console Access Button
                            Button(
                                onClick = {
                                    if (adminSession.isAuthenticated) {
                                        viewModel.navigateTo(AppScreen.OWNER_CONSOLE)
                                    } else {
                                        viewModel.navigateTo(AppScreen.ADMIN_LOGIN)
                                    }
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (adminSession.isAuthenticated) SnapGold else Color(0xFF1E2B42),
                                    contentColor = if (adminSession.isAuthenticated) Color.Black else SnapGold
                                )
                            ) {
                                Icon(
                                    if (adminSession.isAuthenticated) Icons.Default.AdminPanelSettings else Icons.Default.Lock,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = if (adminSession.isAuthenticated) "👑 Owner Deep Console" else "🔐 Admin / Owner Login",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }

                            // 2. Operating Market Switcher Button
                            val currentMarket = markets.firstOrNull { it.code == selectedMarketCode } ?: markets.first()
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        showMarketSelectorDialog = true
                                        scope.launch { drawerState.close() }
                                    },
                                color = Color(0xFF182030),
                                border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.4f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(text = currentMarket.flag, fontSize = 18.sp)
                                        Column {
                                            Text(
                                                text = "Operating Market",
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                text = "${currentMarket.name} (${currentMarket.currencySymbol} ${currentMarket.currencyCode})",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = SnapCyan
                                            )
                                        }
                                    }
                                    Icon(
                                        Icons.Default.Tune,
                                        contentDescription = "Switch Market",
                                        tint = SnapCyan,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            // 3. AI Command Center Quick Trigger
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        viewModel.navigateTo(AppScreen.COMMAND_CENTER)
                                        scope.launch { drawerState.close() }
                                    },
                                color = Color(0xFF182030),
                                border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.4f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.AutoAwesome,
                                            contentDescription = "AI Command",
                                            tint = SnapGold,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Column {
                                            Text(
                                                text = "AI Command Center",
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                color = SnapGold
                                            )
                                            Text(
                                                text = "Autonomous Sourcing & Dropship OS",
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                    Icon(
                                        Icons.Default.ArrowForward,
                                        contentDescription = null,
                                        tint = SnapGold,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }

                            // 4. App Theme & Palette Selector Quick Button
                            OutlinedButton(
                                onClick = {
                                    showThemeDialog = true
                                    scope.launch { drawerState.close() }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Icon(
                                    Icons.Default.Palette,
                                    contentDescription = "Theme",
                                    modifier = Modifier.size(15.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = "🎨 Theme: ${selectedThemePreset.displayName}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )

                    Text(
                        text = "BUYER PRODUCT PAGES",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                        color = SnapCyan
                    )

                    val buyerDrawerItems = listOf(
                        Triple(AppScreen.LOCATION_MARKETPLACE, "📍 Location-Based Marketplace", Icons.Default.LocationOn),
                        Triple(AppScreen.BUYER_STOREFRONT, "🛍️ Live Customer Storefront", Icons.Default.Storefront),
                        Triple(AppScreen.NATIONAL_BUYER, "🇮🇳 National Buyer Page (₹)", Icons.Default.ShoppingBag),
                        Triple(AppScreen.INTERNATIONAL_BUYER, "🌐 Global Buyer Page ($/AED/SAR)", Icons.Default.Public)
                    )

                    buyerDrawerItems.forEach { (screen, title, icon) ->
                        val isSelected = currentScreen == screen
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    tint = if (isSelected) SnapCyan else SnapGold
                                )
                            },
                            label = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    ),
                                    color = if (isSelected) SnapCyan else MaterialTheme.colorScheme.onSurface
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                viewModel.navigateTo(screen)
                                scope.launch { drawerState.close() }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = SnapCyanContainer.copy(alpha = 0.5f),
                                unselectedContainerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )

                    Text(
                        text = "VIRTUAL TEAM MODULES",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                        color = SnapGold
                    )

                    val drawerItems = listOf(
                        Triple(AppScreen.OVERVIEW, "🏠 Home", Icons.Default.Home),
                        Triple(AppScreen.OWNER_CONSOLE, "👑 Owner Deep Console", Icons.Default.AdminPanelSettings),
                        Triple(AppScreen.COMMAND_CENTER, "AI Command Center", Icons.Default.Hub),
                        Triple(AppScreen.PRODUCT_FINDER, "AI Product Finder", Icons.Default.Search),
                        Triple(AppScreen.LAUNCH_STUDIO, "1-Click Launch Studio", Icons.Default.RocketLaunch),
                        Triple(AppScreen.SUPPLIERS, "Suppliers & Sourcing", Icons.Default.LocalShipping),
                        Triple(AppScreen.STORE_BUILDER, "AI Storefront Builder", Icons.Default.Storefront),
                        Triple(AppScreen.MARKETING, "Marketing & Autopilot", Icons.Default.Campaign),
                        Triple(AppScreen.ORDERS, "Orders & Auto-Fulfillment", Icons.Default.ShoppingBag),
                        Triple(AppScreen.PAYMENTS, "Payment Gateways & Payouts", Icons.Default.AccountBalanceWallet),
                        Triple(AppScreen.SUPPORT, "AI Customer Support Desk", Icons.Default.SupportAgent),
                        Triple(AppScreen.ANALYTICS, "Profitability Analytics", Icons.Default.Insights),
                        Triple(AppScreen.INTERNATIONAL, "International Markets", Icons.Default.Public),
                        Triple(AppScreen.COMPETITORS_AB, "Competitor Intel & A/B", Icons.Default.CompareArrows),
                        Triple(AppScreen.ADMIN_LOGIN, "🔐 Admin Security Portal", Icons.Default.Lock),
                        Triple(AppScreen.ONBOARDING, "Store Setup Wizard", Icons.Default.Tune),
                        Triple(AppScreen.LANDING, "Public Landing Page", Icons.Default.Store)
                    )

                    drawerItems.forEach { (screen, title, icon) ->
                        val isSelected = currentScreen == screen
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    tint = if (isSelected) SnapGold else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            label = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) SnapGold else MaterialTheme.colorScheme.onSurface
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                viewModel.navigateTo(screen)
                                scope.launch { drawerState.close() }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = SnapGoldContainer.copy(alpha = 0.5f),
                                unselectedContainerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )

                    Text(
                        text = "🚀 7 ADVANCED OPERATIONS & GROWTH",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                        color = SnapEmerald
                    )

                    val advancedOperationsItems = listOf(
                        Triple(AppScreen.RISK_FRAUD, "🛡️ Anti-Fraud & Risk Engine", Icons.Default.Security),
                        Triple(AppScreen.INVOICES_LABELS, "📄 Invoices & 4x6 Labels", Icons.Default.ReceiptLong),
                        Triple(AppScreen.UPSELL_BUNDLES, "⚡ Upsells & Smart Bundles", Icons.Default.TrendingUp),
                        Triple(AppScreen.ROAS_ATTRIBUTION, "📊 ROAS & Net Profit Sync", Icons.Default.MonetizationOn),
                        Triple(AppScreen.MULTILINGUAL_SUPPORT, "🌐 Multilingual AI Desk", Icons.Default.Translate),
                        Triple(AppScreen.BROADCAST_CENTER, "📢 VIP Broadcast Center", Icons.Default.Campaign),
                        Triple(AppScreen.INVENTORY_GUARD, "📦 Inventory & Price Guard", Icons.Default.Inventory2)
                    )

                    advancedOperationsItems.forEach { (screen, title, icon) ->
                        val isSelected = currentScreen == screen
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    tint = if (isSelected) SnapEmerald else SnapCyan
                                )
                            },
                            label = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    ),
                                    color = if (isSelected) SnapEmerald else MaterialTheme.colorScheme.onSurface
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                viewModel.navigateTo(screen)
                                scope.launch { drawerState.close() }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = SnapEmerald.copy(alpha = 0.15f),
                                unselectedContainerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (currentScreen != AppScreen.LANDING) {
                    TopAppBar(
                        title = {
                            BrandLogoHeader(
                                compact = true,
                                showTagline = true,
                                taglineText = "FIND • LAUNCH • SELL",
                                onClick = { viewModel.navigateTo(AppScreen.OVERVIEW) }
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } },
                                modifier = Modifier.testTag("main_drawer_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Open Navigation Menu",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        },
                        actions = {
                            // Optional clean quick notification or empty for maximum header spaciousness
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            },
            bottomBar = {
                if (currentScreen != AppScreen.LANDING && currentScreen != AppScreen.ONBOARDING) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = 6.dp
                    ) {
                        val bottomNavItems = listOf(
                            Triple(AppScreen.OVERVIEW, "Home", Icons.Default.Home),
                            Triple(AppScreen.LOCATION_MARKETPLACE, "Market", Icons.Default.LocationOn),
                            Triple(AppScreen.COMMAND_CENTER, "AI Command", Icons.Default.Hub),
                            Triple(AppScreen.PRODUCT_FINDER, "Winners", Icons.Default.TravelExplore),
                            Triple(AppScreen.ORDERS, "Orders", Icons.Default.ShoppingBag)
                        )

                        bottomNavItems.forEach { (screen, label, icon) ->
                            val isSelected = currentScreen == screen
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { viewModel.navigateTo(screen) },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label,
                                        tint = if (isSelected) SnapGold else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                label = {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        ),
                                        color = if (isSelected) SnapGold else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = SnapGoldContainer.copy(alpha = 0.6f)
                                ),
                                modifier = Modifier.testTag("nav_item_${label.lowercase()}")
                            )
                        }
                    }
                }
            },
            modifier = modifier.fillMaxSize()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentScreen) {
                    AppScreen.LANDING -> {
                        LandingScreen(
                            onStartFree = { viewModel.navigateTo(AppScreen.OVERVIEW) },
                            onExploreAi = { viewModel.navigateTo(AppScreen.COMMAND_CENTER) }
                        )
                    }

                    AppScreen.ONBOARDING -> {
                        OnboardingScreen(
                            currentPlan = onboardingPlan,
                            onComplete = { updatedPlan ->
                                viewModel.completeOnboarding(updatedPlan)
                            }
                        )
                    }

                    AppScreen.OVERVIEW -> {
                        DashboardOverviewScreen(
                            products = products,
                            orders = orders,
                            campaigns = campaigns,
                            recommendations = recommendations,
                            autopilotConfig = autopilotConfig,
                            totalRevenue = totalRevenue,
                            totalProfit = totalProfit,
                            onNavigate = { viewModel.navigateTo(it) },
                            onSelectProduct = { viewModel.setSelectedProductForLaunch(it) },
                            onApproveRecommendation = { viewModel.approveRecommendation(it) },
                            onRejectRecommendation = { viewModel.rejectRecommendation(it) }
                        )
                    }

                    AppScreen.COMMAND_CENTER -> {
                        AiCommandCenterScreen(
                            chatHistory = chatHistory,
                            commandInput = commandInput,
                            isAiThinking = isAiThinking,
                            onInputChange = { viewModel.setAiCommandInput(it) },
                            onExecuteCommand = { viewModel.executeAiCommand(it) },
                            onApproveRecommendation = { viewModel.approveRecommendation(it) },
                            onRejectRecommendation = { viewModel.rejectRecommendation(it) },
                            onSelectProductForLaunch = { viewModel.setSelectedProductForLaunch(it) }
                        )
                    }

                    AppScreen.PRODUCT_FINDER -> {
                        ProductFinderScreen(
                            products = products,
                            onSelectProduct = { viewModel.setSelectedProductForLaunch(it) },
                            onLaunchProduct = { viewModel.launchProduct(it) }
                        )
                    }

                    AppScreen.LAUNCH_STUDIO -> {
                        val productToLaunch = selectedProductForLaunch ?: products.first()
                        OneClickLaunchScreen(
                            product = productToLaunch,
                            onLaunchConfirm = { viewModel.launchProduct(it) },
                            onBack = { viewModel.navigateTo(AppScreen.PRODUCT_FINDER) }
                        )
                    }

                    AppScreen.SUPPLIERS -> {
                        SupplierSourcingScreen(
                            suppliers = suppliers,
                            onToggleSupplierIntegration = { id, state -> viewModel.toggleSupplierIntegration(id, state) }
                        )
                    }

                    AppScreen.STORE_BUILDER -> {
                        StoreBuilderScreen(
                            storeProfile = storeProfile,
                            products = products,
                            onUpdateStoreProfile = { viewModel.updateStoreProfile(it) }
                        )
                    }

                    AppScreen.MARKETING, AppScreen.AUTOPILOT -> {
                        MarketingAutopilotScreen(
                            campaigns = campaigns,
                            autopilotConfig = autopilotConfig,
                            auditLogs = auditLogs,
                            onToggleCampaign = { viewModel.toggleCampaignStatus(it) },
                            onUpdateBudget = { id, b -> viewModel.updateCampaignBudget(id, b) },
                            onUpdateAutopilot = { viewModel.updateAutopilotConfig(it) }
                        )
                    }

                    AppScreen.ORDERS -> {
                        OrderManagementScreen(
                            orders = orders,
                            onFulfillOrder = { viewModel.fulfillOrderWithSupplier(it) },
                            onApproveRiskOrder = { viewModel.approveRiskOrder(it) }
                        )
                    }

                    AppScreen.SUPPORT -> {
                        CustomerServiceScreen(
                            tickets = supportTickets,
                            onResolveTicket = { viewModel.resolveTicket(it) },
                            onEscalateTicket = { viewModel.escalateTicket(it) }
                        )
                    }

                    AppScreen.ANALYTICS -> {
                        AnalyticsScreen(
                            totalRevenue = totalRevenue,
                            totalProfit = totalProfit
                        )
                    }

                    AppScreen.PAYMENTS -> {
                        PaymentSystemScreen(
                            gateways = paymentGateways,
                            transactions = paymentTransactions,
                            balances = merchantBalances,
                            payouts = merchantPayouts,
                            smartRules = paymentSmartRules,
                            onToggleGateway = { id, enabled -> viewModel.togglePaymentGateway(id, enabled) },
                            onToggleGatewayLiveMode = { id, isLive -> viewModel.toggleGatewayLiveMode(id, isLive) },
                            onUpdateGatewayCredentials = { id, key, mId -> viewModel.updateGatewayCredentials(id, key, mId) },
                            onRefundTransaction = { id, reason -> viewModel.refundPaymentTransaction(id, reason) },
                            onRequestPayout = { amt, curr, dest, bank -> viewModel.requestPayout(amt, curr, dest, bank) },
                            onToggleSmartRule = { id, enabled -> viewModel.togglePaymentSmartRule(id, enabled) },
                            onSimulatePayment = { id, amt, curr, cust, meth, out ->
                                viewModel.simulateTestPayment(id, amt, curr, cust, meth, out)
                            }
                        )
                    }

                    AppScreen.INTERNATIONAL -> {
                        InternationalMarketsScreen(
                            markets = markets,
                            selectedMarketCode = selectedMarketCode,
                            onSelectMarket = { viewModel.setSelectedMarket(it) }
                        )
                    }

                    AppScreen.COMPETITORS_AB -> {
                        CompetitorAndAbTestingScreen(
                            competitors = competitors,
                            abTests = abTests
                        )
                    }

                    AppScreen.NATIONAL_BUYER -> {
                        NationalBuyerProductScreen(
                            products = products,
                            selectedProduct = selectedBuyerProduct,
                            onSelectProduct = { viewModel.setSelectedBuyerProduct(it) },
                            onPlaceOrder = { viewModel.placeBuyerOrder(it) },
                            onAddToCart = { viewModel.addToBuyerCart(it) },
                            onSwitchToInternational = { viewModel.navigateTo(AppScreen.INTERNATIONAL_BUYER) },
                            onBackToStorefront = { viewModel.navigateTo(AppScreen.BUYER_STOREFRONT) }
                        )
                    }

                    AppScreen.INTERNATIONAL_BUYER -> {
                        InternationalBuyerProductScreen(
                            products = products,
                            selectedProduct = selectedBuyerProduct,
                            onSelectProduct = { viewModel.setSelectedBuyerProduct(it) },
                            onPlaceOrder = { viewModel.placeBuyerOrder(it) },
                            onAddToCart = { viewModel.addToBuyerCart(it) },
                            onSwitchToNational = { viewModel.navigateTo(AppScreen.NATIONAL_BUYER) },
                            onBackToStorefront = { viewModel.navigateTo(AppScreen.BUYER_STOREFRONT) }
                        )
                    }

                    AppScreen.BUYER_STOREFRONT -> {
                        BuyerStorefrontScreen(
                            products = products,
                            storeProfile = storeProfile,
                            onOpenNationalProduct = {
                                viewModel.setSelectedBuyerProduct(it)
                                viewModel.navigateTo(AppScreen.NATIONAL_BUYER)
                            },
                            onOpenInternationalProduct = {
                                viewModel.setSelectedBuyerProduct(it)
                                viewModel.navigateTo(AppScreen.INTERNATIONAL_BUYER)
                            },
                            onDirectAddToCart = { viewModel.addToBuyerCart(it) }
                        )
                    }

                    AppScreen.LOCATION_MARKETPLACE -> {
                        LocationMarketplaceScreen(
                            products = products,
                            currentLocation = currentUserLocation,
                            availableLocations = userLocations,
                            onSelectLocation = { viewModel.selectUserLocation(it) },
                            onSetPincode = { pin, city -> viewModel.setLocationByPincode(pin, city) },
                            onDetectGps = { viewModel.simulateGpsLocationDetection() },
                            onOpenProductDetail = {
                                viewModel.setSelectedBuyerProduct(it)
                                if (currentUserLocation.countryCode == "IN") {
                                    viewModel.navigateTo(AppScreen.NATIONAL_BUYER)
                                } else {
                                    viewModel.navigateTo(AppScreen.INTERNATIONAL_BUYER)
                                }
                            },
                            onAddToCart = { viewModel.addToBuyerCart(it) }
                        )
                    }

                    AppScreen.ADMIN_LOGIN -> {
                        AdminLoginScreen(
                            adminUsers = adminUsers,
                            onLogin = { email, pin, role -> viewModel.loginAdmin(email, pin, role) },
                            onNavigateBack = { viewModel.navigateTo(AppScreen.OVERVIEW) }
                        )
                    }

                    AppScreen.OWNER_CONSOLE -> {
                        OwnerAdminConsoleScreen(
                            adminSession = adminSession,
                            telemetryMetrics = telemetryMetrics,
                            adminAuditTrail = adminAuditTrail,
                            systemHealth = systemHealth,
                            orders = orders,
                            products = products,
                            suppliers = suppliers,
                            merchantBalances = merchantBalances,
                            merchantPayouts = merchantPayouts,
                            isEmergencyAdFreezeActive = isEmergencyAdFreezeActive,
                            isEmergencyAutoFulfillPaused = isEmergencyAutoFulfillPaused,
                            onToggleEmergencyAdFreeze = { viewModel.toggleEmergencyAdFreeze(it) },
                            onToggleEmergencyAutoFulfill = { viewModel.toggleEmergencyAutoFulfill(it) },
                            onFlushCache = { viewModel.flushSystemCache() },
                            onLockConsole = { viewModel.lockAdminConsole() },
                            onUnlockConsole = { viewModel.unlockAdminConsole(it) },
                            onLogout = { viewModel.logoutAdmin() },
                            onToggleSupplierIntegration = { id, active -> viewModel.toggleSupplierIntegration(id, active) },
                            onNavigateToScreen = { viewModel.navigateTo(it) }
                        )
                    }

                    // 7 Advanced Operations & Growth Suites
                    AppScreen.RISK_FRAUD -> {
                        RiskAndFraudScreen(
                            fraudReports = fraudReports,
                            onApproveOrder = { viewModel.approveFraudReport(it) },
                            onBlockOrder = { viewModel.blockFraudReport(it) },
                            onSanitizeAddress = { id, addr -> viewModel.sanitizeOrderAddress(id, addr) }
                        )
                    }

                    AppScreen.INVOICES_LABELS -> {
                        InvoiceAndLabelScreen(
                            invoices = invoices,
                            shippingLabels = shippingLabels,
                            packingSlips = packingSlips
                        )
                    }

                    AppScreen.UPSELL_BUNDLES -> {
                        UpsellAndBundleScreen(
                            upsellOffers = upsellOffers,
                            smartBundles = smartBundles,
                            onToggleUpsell = { id, enabled -> viewModel.toggleUpsellOffer(id, enabled) }
                        )
                    }

                    AppScreen.ROAS_ATTRIBUTION -> {
                        RoasAttributionScreen(
                            adAttributions = adSpendAttributions
                        )
                    }

                    AppScreen.MULTILINGUAL_SUPPORT -> {
                        MultilingualSupportScreen(
                            tickets = multilingualTickets,
                            onResolveTicket = { viewModel.resolveMultilingualTicket(it) },
                            onApplyMacro = { id, macro -> viewModel.applySmartMacro(id, macro) }
                        )
                    }

                    AppScreen.BROADCAST_CENTER -> {
                        BroadcastCampaignScreen(
                            broadcastCampaigns = broadcastCampaigns,
                            customerSegments = customerSegments,
                            onDispatchCampaign = { title, ch, seg, code, pct, tpl ->
                                viewModel.dispatchBroadcastCampaign(title, ch, seg, code, pct, tpl)
                            }
                        )
                    }

                    AppScreen.INVENTORY_GUARD -> {
                        InventoryGuardScreen(
                            priceSurgeEvents = priceSurgeEvents,
                            inventoryGuards = inventoryGuards,
                            onToggleAutoReroute = { id, active -> viewModel.toggleInventoryAutoReroute(id, active) }
                        )
                    }
                }
            }
        }
    }

    // Market Switcher Dialog
    if (showMarketSelectorDialog) {
        AlertDialog(
            onDismissRequest = { showMarketSelectorDialog = false },
            title = {
                Text(
                    text = "Select Primary Operating Market",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    markets.forEach { m ->
                        val isSelected = m.code == selectedMarketCode
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    viewModel.setSelectedMarket(m.code)
                                    showMarketSelectorDialog = false
                                },
                            color = if (isSelected) SnapGoldContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, if (isSelected) SnapGold else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(text = m.flag, fontSize = 20.sp)
                                    Column {
                                        Text(text = m.name, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                        Text(text = "Currency: ${m.currencyCode} (${m.currencySymbol})", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }

                                if (isSelected) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SnapGold)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMarketSelectorDialog = false }) {
                    Text("Done", fontWeight = FontWeight.Bold, color = SnapGold)
                }
            }
        )
    }

    if (showThemeDialog) {
        ThemeSelectorDialog(
            currentPreset = selectedThemePreset,
            onPresetSelected = { preset ->
                viewModel.setThemePreset(preset)
            },
            onDismiss = { showThemeDialog = false }
        )
    }
}
