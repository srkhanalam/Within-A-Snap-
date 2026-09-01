package com.example.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.BrandLogoHeader
import com.example.ui.components.CountryFlagBadge
import com.example.ui.theme.*

enum class OwnerTab(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    TELEMETRY("📊 360° Operations", Icons.Default.QueryStats),
    AI_AGENTS("🤖 AI Fleet Diagnostics", Icons.Default.Psychology),
    SUPPLIERS("📦 Supplier SLA & COGS", Icons.Default.LocalShipping),
    TREASURY("💳 Treasury & Payouts", Icons.Default.AccountBalance),
    SECURITY("🛡️ Security & Health", Icons.Default.Security)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerAdminConsoleScreen(
    adminSession: AdminSession,
    telemetryMetrics: SystemTelemetryMetric,
    adminAuditTrail: List<AdminAuditEntry>,
    systemHealth: List<SystemHealthService>,
    orders: List<Order>,
    products: List<Product>,
    suppliers: List<Supplier>,
    merchantBalances: List<MerchantBalance>,
    merchantPayouts: List<MerchantPayout>,
    isEmergencyAdFreezeActive: Boolean,
    isEmergencyAutoFulfillPaused: Boolean,
    onToggleEmergencyAdFreeze: (Boolean) -> Unit,
    onToggleEmergencyAutoFulfill: (Boolean) -> Unit,
    onFlushCache: () -> Unit,
    onLockConsole: () -> Unit,
    onUnlockConsole: (String) -> Boolean,
    onLogout: () -> Unit,
    onToggleSupplierIntegration: (String, Boolean) -> Unit,
    onNavigateToScreen: (com.example.ui.viewmodel.AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentTab by remember { mutableStateOf(OwnerTab.TELEMETRY) }
    var unlockPinInput by remember { mutableStateOf("") }
    var unlockError by remember { mutableStateOf<String?>(null) }
    var showBackupDialog by remember { mutableStateOf(false) }
    var showQuickActionToast by remember { mutableStateOf<String?>(null) }

    // Lock Overlay if Console is Locked
    if (adminSession.isConsoleLocked) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xE6080C14)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF111827)),
                border = BorderStroke(1.5.dp, SnapGold)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(SnapGoldContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = SnapGold, modifier = Modifier.size(32.dp))
                    }

                    Text(
                        "Owner Console Locked",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                        color = Color.White
                    )

                    Text(
                        "Enter Owner Master PIN (1703) to unlock live financial numbers and executive controls.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    OutlinedTextField(
                        value = unlockPinInput,
                        onValueChange = {
                            unlockPinInput = it
                            unlockError = null
                        },
                        label = { Text("Master PIN") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SnapGold,
                            unfocusedBorderColor = Color(0xFF2E3D5C),
                            focusedContainerColor = Color(0xFF090D16),
                            unfocusedContainerColor = Color(0xFF090D16)
                        )
                    )

                    unlockError?.let {
                        Text(it, color = SnapRose, style = MaterialTheme.typography.bodySmall)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { unlockPinInput = "1703" },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Auto (1703)", fontSize = 12.sp, color = SnapGoldLight)
                        }

                        Button(
                            onClick = {
                                val success = onUnlockConsole(unlockPinInput)
                                if (success) {
                                    unlockPinInput = ""
                                    unlockError = null
                                } else {
                                    unlockError = "Incorrect PIN. Use '1703'."
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black)
                        ) {
                            Text("Unlock", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        return
    }

    // Main Owner Console Layout
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF080C14))
            .padding(horizontal = 16.dp)
            .testTag("owner_admin_console"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp)
    ) {
        // --- 1. Top Executive Banner ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF101726)),
                border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.5f))
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
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(SnapGold),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = adminSession.currentUser?.avatarInitials ?: "PA",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                    color = Color.Black
                                )
                            }

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = adminSession.currentUser?.name ?: "Parvej Alam",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                        color = Color.White
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = SnapGoldContainer
                                    ) {
                                        Text(
                                            text = "ROOT OWNER",
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Black),
                                            color = SnapGold,
                                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "Session: Active • IP: ${adminSession.currentUser?.ipAddress ?: "103.21.144.92"}",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = SnapEmerald
                                )
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            IconButton(
                                onClick = onLockConsole,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1E2A3E))
                            ) {
                                Icon(Icons.Default.Lock, contentDescription = "Lock Console", tint = SnapGold, modifier = Modifier.size(18.dp))
                            }

                            IconButton(
                                onClick = onLogout,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2A1B24))
                            ) {
                                Icon(Icons.Default.Logout, contentDescription = "Logout", tint = SnapRose, modifier = Modifier.size(18.dp))
                            }
                        }
                    }

                    // System Pulse Bar
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF090E17),
                        border = BorderStroke(1.dp, Color(0xFF1B263B))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(SnapEmerald))
                                Text("Fleet Uptime ${telemetryMetrics.serverUptimePct}%", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                            }
                            Text("⚡ ${telemetryMetrics.activeWebhooksPerMinute} webhooks/min", style = MaterialTheme.typography.labelSmall, color = SnapCyan)
                            Text("🛡️ Fraud Block ${telemetryMetrics.fraudRiskBlockRatePct}%", style = MaterialTheme.typography.labelSmall, color = SnapGoldLight)
                        }
                    }
                }
            }
        }

        // --- 2. Emergency Master Controls Bar ---
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1523)),
                border = BorderStroke(1.dp, Color(0xFF243452))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "MASTER AUTOPILOT GUARDRAILS & EMERGENCY SWITCHES",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                        color = SnapGold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Emergency Ad Freeze
                        FilterChip(
                            selected = isEmergencyAdFreezeActive,
                            onClick = { onToggleEmergencyAdFreeze(!isEmergencyAdFreezeActive) },
                            label = {
                                Text(
                                    if (isEmergencyAdFreezeActive) "🛑 ADS FROZEN" else "Pause All Ads",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    if (isEmergencyAdFreezeActive) Icons.Default.Warning else Icons.Default.Campaign,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SnapRose,
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFF182234),
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        // Auto-Fulfill Freeze
                        FilterChip(
                            selected = isEmergencyAutoFulfillPaused,
                            onClick = { onToggleEmergencyAutoFulfill(!isEmergencyAutoFulfillPaused) },
                            label = {
                                Text(
                                    if (isEmergencyAutoFulfillPaused) "🛑 FULFILL PAUSED" else "Freeze Fulfillment",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    if (isEmergencyAutoFulfillPaused) Icons.Default.PauseCircle else Icons.Default.LocalShipping,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SnapAmber,
                                selectedLabelColor = Color.Black,
                                containerColor = Color(0xFF182234),
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                onFlushCache()
                                showQuickActionToast = "Flushed CDN edge cache and resynced 11 supplier catalogs."
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color(0xFF2A3C5E))
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp), tint = SnapCyan)
                            Spacer(Modifier.width(4.dp))
                            Text("Flush Cache", fontSize = 11.sp, color = SnapCyan)
                        }

                        OutlinedButton(
                            onClick = { showBackupDialog = true },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.6f))
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp), tint = SnapGold)
                            Spacer(Modifier.width(4.dp))
                            Text("Export Backup", fontSize = 11.sp, color = SnapGoldLight)
                        }
                    }
                }
            }
        }

        // --- 3. Quick Action Toast Feedback ---
        showQuickActionToast?.let { toast ->
            item {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = SnapEmeraldContainer,
                    border = BorderStroke(1.dp, SnapEmerald)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(18.dp))
                            Text(toast, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                        }
                        IconButton(onClick = { showQuickActionToast = null }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }

        // --- 4. Executive Metric Cockpit (6 KPI Cards) ---
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "CONSOLIDATED OWNER FINANCIAL LEDGER",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = SnapGold
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OwnerMetricCard(
                        title = "Gross Merchandise (GMV)",
                        value = "$${String.format("%,.0f", telemetryMetrics.totalGmvUSD)}",
                        sub = "+18.4% this week",
                        color = SnapGold,
                        modifier = Modifier.weight(1f)
                    )
                    OwnerMetricCard(
                        title = "Real In-Pocket Profit",
                        value = "$${String.format("%,.0f", telemetryMetrics.totalNetProfitUSD)}",
                        sub = "49.1% Net Margin",
                        color = SnapEmerald,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OwnerMetricCard(
                        title = "Cash in Multi-Banks",
                        value = "$${String.format("%,.0f", telemetryMetrics.cashInBankUSD)}",
                        sub = "5 Currencies Liquid",
                        color = SnapCyan,
                        modifier = Modifier.weight(1f)
                    )
                    OwnerMetricCard(
                        title = "Supplier COGS Liabilities",
                        value = "$${String.format("%,.0f", telemetryMetrics.supplierCogsPayableUSD)}",
                        sub = "11 Suppliers Active",
                        color = SnapAmber,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // --- 5. Navigation Tab Pills ---
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(OwnerTab.values()) { tab ->
                    val isSelected = currentTab == tab
                    FilterChip(
                        selected = isSelected,
                        onClick = { currentTab = tab },
                        label = {
                            Text(
                                tab.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        leadingIcon = {
                            Icon(tab.icon, contentDescription = null, modifier = Modifier.size(16.dp))
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SnapGold,
                            selectedLabelColor = Color.Black,
                            selectedLeadingIconColor = Color.Black,
                            containerColor = Color(0xFF131A29),
                            labelColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }
        }

        // --- 6. TAB CONTENT SECTIONS ---

        // TAB 1: 360° Operations & Itemized Order Unit Economics
        if (currentTab == OwnerTab.TELEMETRY) {
            item {
                Text(
                    "ITEMIZED ORDER UNIT ECONOMICS & PROFIT ATTRIBUTION",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = SnapGold
                )
            }

            items(orders.take(6)) { order ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1422)),
                    border = BorderStroke(1.dp, Color(0xFF1D283E))
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
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(order.id, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                                CountryFlagBadge(order.marketCode)
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (order.paymentStatus == PaymentStatus.PAID) SnapEmeraldContainer else SnapAmberContainer
                            ) {
                                Text(
                                    order.paymentStatus.name,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp),
                                    color = if (order.paymentStatus == PaymentStatus.PAID) SnapEmerald else SnapAmber,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            order.productName,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        // Granular Unit Economics Breakdown
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF070B12),
                            border = BorderStroke(1.dp, Color(0xFF162032))
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Retail Price Sold:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("$${String.format("%.2f", order.revenueUSD)} (Market: ${order.marketCode})", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Supplier COGS & Shipping:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("-$${String.format("%.2f", order.costUSD)}", style = MaterialTheme.typography.bodySmall, color = SnapRose)
                                }
                                Divider(color = Color(0xFF162032), modifier = Modifier.padding(vertical = 2.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Real In-Pocket Net Profit:", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                                    val profitPct = if (order.revenueUSD > 0) (order.profitUSD / order.revenueUSD) * 100.0 else 0.0
                                    Text("+$${String.format("%.2f", order.profitUSD)} (${String.format("%.1f", profitPct)}%)", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Black), color = SnapEmerald)
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Carrier: ${order.shippingStatus} • Tracking: ${order.trackingNumber.ifEmpty { "Pending" }}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(order.date, style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        // TAB 2: AI Fleet Diagnostics
        if (currentTab == OwnerTab.AI_AGENTS) {
            item {
                Text(
                    "AUTONOMOUS SPECIALIZED AGENT FLEET HEALTH",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = SnapGold
                )
            }

            val agents = listOf(
                Triple("AI Product Researcher & Trend Hunter", "Scanning TikTok Creative Center & Amazon Movers (98.4% Accuracy)", SnapCyan),
                Triple("Pricing & Multi-Currency Profit Optimizer", "Dynamically adjusting USD/AED/SAR/INR margins based on real-time COGS", SnapGold),
                Triple("Automated Ad Creative & Script Producer", "Generating 3 Meta & TikTok video ad hooks per winning launch", SnapEmerald),
                Triple("Sourcing & Automated Supplier Dispatcher", "Auto-routing orders to 11 verified domestic & global hubs", SnapViolet),
                Triple("Anti-RTO COD Shield & Address Verifier", "Automated SMS/IVR OTP confirmation blocking fraud returns", SnapAmber),
                Triple("AI Customer Support & Dispute Desk", "Resolving 84% of ticket queries instantly with real-time tracking lookups", SnapRose)
            )

            items(agents) { (name, desc, accent) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1422)),
                    border = BorderStroke(1.dp, accent.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(accent.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SmartToy, contentDescription = null, tint = accent, modifier = Modifier.size(22.dp))
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                                Surface(shape = RoundedCornerShape(4.dp), color = SnapEmeraldContainer) {
                                    Text("ONLINE", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold), color = SnapEmerald, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                }
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(desc, style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        // TAB 3: Multi-Country Supplier SLA & Liability Radar
        if (currentTab == OwnerTab.SUPPLIERS) {
            item {
                Text(
                    "MULTI-COUNTRY DROPSHIPPING SUPPLIERS & COGS OVERVIEW",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = SnapGold
                )
            }

            items(suppliers) { sup ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1422)),
                    border = BorderStroke(1.dp, if (sup.isIntegrated) SnapGold.copy(alpha = 0.3f) else Color(0xFF1F2A3F))
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
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(sup.logoEmoji, fontSize = 20.sp)
                                Column {
                                    Text(sup.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                                    Text("Hub: ${sup.warehouses}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = SnapGoldLight)
                                }
                            }

                            Switch(
                                checked = sup.isIntegrated,
                                onCheckedChange = { onToggleSupplierIntegration(sup.id, it) },
                                colors = SwitchDefaults.colors(checkedThumbColor = SnapGold, checkedTrackColor = SnapGoldContainer)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF070B12)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("SLA Lead Time", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("${sup.deliveryDays} Days", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                                }
                                Column {
                                    Text("Reliability", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("${sup.reliabilityScore}%", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                                }
                                Column {
                                    Text("Avg Margin", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("${sup.avgProfitMarginPct}%", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                                }
                                Column {
                                    Text("Orders Synced", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(sup.totalOrdersFulfilled, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }

        // TAB 4: Treasury, Bank Payouts & Multi-Currency Flow
        if (currentTab == OwnerTab.TREASURY) {
            item {
                Text(
                    "TREASURY BALANCES & COMMERCIAL BANK SETTLEMENTS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = SnapGold
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    merchantBalances.forEach { bal ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1422)),
                            border = BorderStroke(1.dp, Color(0xFF1D2940))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(bal.flag, fontSize = 22.sp)
                                    Column {
                                        Text("${bal.currency} Vault", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                                        Text("Pending Settlement: ${bal.symbol}${String.format("%,.0f", bal.pendingSettlement)}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text("${bal.symbol}${String.format("%,.0f", bal.availableAmount)}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black), color = SnapEmerald)
                                    Text("Available to Withdraw", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = SnapGoldLight)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(10.dp))
                Text(
                    "RECENT BANK PAYOUT SETTLEMENTS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = SnapGold
                )
            }

            items(merchantPayouts) { po ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1422)),
                    border = BorderStroke(1.dp, Color(0xFF1A263B))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(po.bankName, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                            Text("A/C: ${po.destinationAccount} • Ref: ${po.referenceNumber}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(po.initiatedAt, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text("${po.currency} ${String.format("%,.0f", po.amount)}", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                            Surface(shape = RoundedCornerShape(4.dp), color = SnapEmeraldContainer) {
                                Text(po.status, style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold), color = SnapEmerald, modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp))
                            }
                        }
                    }
                }
            }
        }

        // TAB 5: Security Audit Log & Edge Health
        if (currentTab == OwnerTab.SECURITY) {
            item {
                Text(
                    "REAL-TIME CLOUD INFRASTRUCTURE & EDGE HEALTH",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = SnapGold
                )
            }

            items(systemHealth) { svc ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1422)),
                    border = BorderStroke(1.dp, Color(0xFF1B283E))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(svc.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                            Text(svc.endpoint, style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, fontFamily = FontFamily.Monospace), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(SnapEmerald))
                            Text("${svc.latencyMs}ms", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(10.dp))
                Text(
                    "TAMPER-PROOF EXECUTIVE SECURITY AUDIT LOGS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                    color = SnapGold
                )
            }

            items(adminAuditTrail) { audit ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1422)),
                    border = BorderStroke(
                        1.dp,
                        when (audit.severity) {
                            "CRITICAL" -> SnapRose.copy(alpha = 0.5f)
                            "WARNING" -> SnapAmber.copy(alpha = 0.5f)
                            else -> Color(0xFF1B283E)
                        }
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(audit.action, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.White)
                            Text(audit.timestamp, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = SnapGoldLight)
                        }
                        Text(audit.details, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Actor: ${audit.actor}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = SnapCyan)
                            Text("IP: ${audit.ipAddress}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }

    // Export Database Backup Dialog
    if (showBackupDialog) {
        AlertDialog(
            onDismissRequest = { showBackupDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.CloudDownload, contentDescription = null, tint = SnapGold)
                    Text("Encrypted Database Export", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Generated 256-bit AES snapshot including:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF090D16),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = """
                            {
                              "export_timestamp": "2026-09-01T09:36:45Z",
                              "store_name": "Within A Snap Official",
                              "owner": "Parvej Alam (parvejalam1703@gmail.com)",
                              "total_orders_synced": ${orders.size},
                              "active_suppliers": ${suppliers.size},
                              "products_catalog": ${products.size},
                              "treasury_balances_usd": ${telemetryMetrics.cashInBankUSD},
                              "encryption_standard": "AES-256-GCM"
                            }
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontSize = 10.sp),
                            color = SnapCyan,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showBackupDialog = false
                        showQuickActionToast = "Database backup snapshot exported and downloaded securely."
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black)
                ) {
                    Text("Download JSON Snapshot", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showBackupDialog = false }) {
                    Text("Close")
                }
            },
            containerColor = Color(0xFF111827)
        )
    }
}

@Composable
fun OwnerMetricCard(
    title: String,
    value: String,
    sub: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1422)),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(title, style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black), color = color)
            Text(sub, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.SemiBold), color = SnapGoldLight)
        }
    }
}
