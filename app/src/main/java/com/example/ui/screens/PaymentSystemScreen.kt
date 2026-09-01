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
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PaymentSystemScreen(
    gateways: List<PaymentGateway>,
    transactions: List<PaymentTransaction>,
    balances: List<MerchantBalance>,
    payouts: List<MerchantPayout>,
    smartRules: List<PaymentSmartRule>,
    onToggleGateway: (String, Boolean) -> Unit,
    onToggleGatewayLiveMode: (String, Boolean) -> Unit,
    onUpdateGatewayCredentials: (String, String, String) -> Unit,
    onRefundTransaction: (String, String) -> Unit,
    onRequestPayout: (Double, String, String, String) -> Unit,
    onToggleSmartRule: (String, Boolean) -> Unit,
    onSimulatePayment: (String, Double, String, String, String, PaymentTransactionStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Gateways & APIs", "Transactions", "Payouts & Balances", "Smart Routing", "Test Sandbox")

    var showPayoutDialog by remember { mutableStateOf(false) }
    var showCredentialDialog by remember { mutableStateOf<PaymentGateway?>(null) }
    var showTxDetailDialog by remember { mutableStateOf<PaymentTransaction?>(null) }
    var showRefundDialog by remember { mutableStateOf<PaymentTransaction?>(null) }
    var showSandboxDialog by remember { mutableStateOf(false) }

    val totalAvailableUSD = balances.sumOf { bal ->
        val rateToUsd = when (bal.currency) {
            "INR" -> 1.0 / 83.5
            "AED" -> 1.0 / 3.67
            "SAR" -> 1.0 / 3.75
            "EUR" -> 1.08
            "GBP" -> 1.28
            else -> 1.0
        }
        bal.availableAmount * rateToUsd
    }

    val totalPendingUSD = balances.sumOf { bal ->
        val rateToUsd = when (bal.currency) {
            "INR" -> 1.0 / 83.5
            "AED" -> 1.0 / 3.67
            "SAR" -> 1.0 / 3.75
            "EUR" -> 1.08
            "GBP" -> 1.28
            else -> 1.0
        }
        bal.pendingSettlement * rateToUsd
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("payment_system_screen"),
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
                                        Icons.Default.AccountBalanceWallet,
                                        contentDescription = null,
                                        tint = SnapGold,
                                        modifier = Modifier.padding(6.dp).size(20.dp)
                                    )
                                }
                                Text(
                                    text = "Payment Infrastructure",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Text(
                                text = "Multi-Currency Gateway Hub, UPI, Stripe, BNPL & Instant Settlements",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Button(
                            onClick = { showPayoutDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SnapGold,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.Payments, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Withdraw", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }

                    // Balance Card Overview
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF131C31)),
                        border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.35f)),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
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
                                Column {
                                    Text("TOTAL ACCUMULATED MERCHANT BALANCE", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 0.5.sp), color = SnapGoldLight)
                                    Text(
                                        text = "$${String.format("%,.2f", totalAvailableUSD)} USD",
                                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                                        color = MaterialTheme.colorScheme.onSurface
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
                                        Icon(Icons.Default.Lock, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(12.dp))
                                        Text("PCI-DSS Level 1 & DDP", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp), color = SnapEmerald)
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    Column {
                                        Text("Pending Settlement", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text("$${String.format("%,.2f", totalPendingUSD)}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = SnapAmber)
                                    }
                                    Column {
                                        Text("Gateway Success Rate", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text("98.4%", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                                    }
                                    Column {
                                        Text("Dispute Rate", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text("0.02% (Safe)", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                                    }
                                }
                            }
                        }
                    }

                    // Multi-Currency Scrollable Badges
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(balances) { bal ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF172033),
                                border = BorderStroke(1.dp, Color(0xFF283652))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(bal.flag, fontSize = 14.sp)
                                    Column {
                                        Text("${bal.symbol} ${String.format("%,.2f", bal.availableAmount)}", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                                        Text("${bal.currency} Balance", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // --- Navigation Tabs ---
        item {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = SnapGold,
                edgePadding = 16.dp,
                divider = { Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)) }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                                ),
                                color = if (selectedTab == index) SnapGold else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                }
            }
        }

        // --- Tab 0: Gateways & APIs ---
        if (selectedTab == 0) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Connected Payment Gateways",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Configure API keys, webhooks, live/sandbox modes & settlement preferences",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            items(gateways) { gw ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(
                        1.dp,
                        if (gw.isEnabled) SnapGold.copy(alpha = 0.4f) else Color(0xFF283652)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Gateway Title & Controls
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
                                    Icon(
                                        when (gw.provider) {
                                            "Stripe" -> Icons.Default.CreditCard
                                            "Razorpay" -> Icons.Default.QrCodeScanner
                                            "Cashfree" -> Icons.Default.AccountBalance
                                            "Tabby" -> Icons.Default.CalendarToday
                                            "PayPal" -> Icons.Default.Payment
                                            else -> Icons.Default.Shield
                                        },
                                        contentDescription = null,
                                        tint = if (gw.isEnabled) SnapGold else Color.Gray,
                                        modifier = Modifier.padding(8.dp).size(20.dp)
                                    )
                                }

                                Column {
                                    Text(
                                        text = gw.name,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = if (gw.isLiveMode) SnapEmeraldContainer.copy(alpha = 0.5f) else SnapAmberContainer.copy(alpha = 0.5f)
                                        ) {
                                            Text(
                                                text = if (gw.isLiveMode) "LIVE PRODUCTION" else "SANDBOX TEST",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 9.sp
                                                ),
                                                color = if (gw.isLiveMode) SnapEmerald else SnapAmber,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }

                                        Text(
                                            text = "• ${gw.category}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            // Enable switch
                            Switch(
                                checked = gw.isEnabled,
                                onCheckedChange = { onToggleGateway(gw.id, it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = SnapGold,
                                    checkedTrackColor = SnapGoldContainer
                                )
                            )
                        }

                        Text(
                            text = gw.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Feature Pills
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            gw.features.forEach { feat ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFF141E33),
                                    border = BorderStroke(1.dp, Color(0xFF233252))
                                ) {
                                    Text(
                                        text = "✓ $feat",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = SnapGoldLight,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                        // Stats & Actions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Fee: ${gw.feePct}%${if (gw.flatFeeUSD > 0) " + $${gw.flatFeeUSD}" else ""} • ${gw.settlementPeriod}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Success Rate: ${gw.successRatePct}% • Currencies: ${gw.supportedCurrencies.joinToString(", ")}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = SnapEmerald
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(
                                    onClick = { onToggleGatewayLiveMode(gw.id, !gw.isLiveMode) },
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    border = BorderStroke(1.dp, if (gw.isLiveMode) SnapAmber else SnapEmerald)
                                ) {
                                    Text(
                                        text = if (gw.isLiveMode) "Use Test" else "Use Live",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (gw.isLiveMode) SnapAmber else SnapEmerald
                                    )
                                }

                                Button(
                                    onClick = { showCredentialDialog = gw },
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = SnapGold,
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Icon(Icons.Default.Key, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text("API Keys", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    }
                }
            }
        }

        // --- Tab 1: Transactions Ledger & Fraud Risk ---
        if (selectedTab == 1) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Real-Time Payment Ledger (${transactions.size})",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Card captures, UPI intents, BNPL splits, and fraud risk scores",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            items(transactions) { tx ->
                val statusColor = when (tx.status) {
                    PaymentTransactionStatus.SUCCESS -> SnapEmerald
                    PaymentTransactionStatus.PENDING -> SnapAmber
                    PaymentTransactionStatus.FAILED -> SnapRose
                    PaymentTransactionStatus.REFUNDED -> SnapViolet
                    PaymentTransactionStatus.DISPUTED -> SnapRose
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clickable { showTxDetailDialog = tx },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, statusColor.copy(alpha = 0.25f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = statusColor.copy(alpha = 0.15f),
                                border = BorderStroke(1.dp, statusColor.copy(alpha = 0.4f))
                            ) {
                                Icon(
                                    when (tx.status) {
                                        PaymentTransactionStatus.SUCCESS -> Icons.Default.CheckCircle
                                        PaymentTransactionStatus.PENDING -> Icons.Default.HourglassTop
                                        PaymentTransactionStatus.FAILED -> Icons.Default.Cancel
                                        PaymentTransactionStatus.REFUNDED -> Icons.Default.RotateLeft
                                        PaymentTransactionStatus.DISPUTED -> Icons.Default.Warning
                                    },
                                    contentDescription = null,
                                    tint = statusColor,
                                    modifier = Modifier.padding(6.dp).size(18.dp)
                                )
                            }

                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(
                                    text = "${tx.customerName} (${tx.countryCode})",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${tx.method} • ${tx.gatewayName}",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${tx.id} • ${tx.createdAt}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            }
                        }

                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "${tx.currency} ${if (tx.currency == "INR") tx.amount.toInt() else String.format("%.2f", tx.amount)}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = statusColor.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = tx.status.name,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        fontSize = 9.sp
                                    ),
                                    color = statusColor,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // --- Tab 2: Payouts & Multi-Currency Accounts ---
        if (selectedTab == 2) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Merchant Multi-Currency Payouts",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Automated settlements to India, UAE, Saudi Arabia, and USA bank accounts",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Balances Breakdown Cards
            items(balances) { bal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, Color(0xFF283652))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(bal.flag, fontSize = 24.sp)
                            Column {
                                Text(
                                    text = "${bal.currency} Balance",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Available: ${bal.symbol} ${String.format("%,.2f", bal.availableAmount)}",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = SnapEmerald
                                )
                                Text(
                                    text = "Pending Settlement: ${bal.symbol} ${String.format("%,.2f", bal.pendingSettlement)}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Button(
                            onClick = { showPayoutDialog = true },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SnapGold,
                                contentColor = Color.Black
                            )
                        ) {
                            Text("Payout", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Recent Payout History",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(payouts) { po ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, Color(0xFF283652))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${po.currency} ${String.format("%,.2f", po.amount)} ($${String.format("%,.2f", po.amountUSD)} USD)",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = SnapGold
                            )

                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = if (po.status == "COMPLETED") SnapEmeraldContainer.copy(alpha = 0.5f) else SnapAmberContainer.copy(alpha = 0.5f)
                            ) {
                                Text(
                                    text = po.status,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    ),
                                    color = if (po.status == "COMPLETED") SnapEmerald else SnapAmber,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "Destination: ${po.destinationAccount} • ${po.bankName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "Ref: ${po.referenceNumber} • Initiated: ${po.initiatedAt}${if (po.completedAt != null) " • Completed: ${po.completedAt}" else ""}",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // --- Tab 3: Smart Routing & Anti-RTO Rules ---
        if (selectedTab == 3) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Autonomous Payment Routing Engine",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Rules to reduce payment gateway fees, eliminate COD cancellations, and boost conversion",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            items(smartRules) { rule ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(
                        1.dp,
                        if (rule.isEnabled) SnapGold.copy(alpha = 0.4f) else Color(0xFF283652)
                    )
                ) {
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
                                text = rule.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Switch(
                                checked = rule.isEnabled,
                                onCheckedChange = { onToggleSmartRule(rule.id, it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = SnapGold,
                                    checkedTrackColor = SnapGoldContainer
                                )
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF101726),
                            border = BorderStroke(1.dp, Color(0xFF1E2B45)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("IF:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapAmber)
                                    Text(rule.condition, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("THEN:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                                    Text(rule.action, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Estimated Merchant Benefit:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = rule.savingsEstimate,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = SnapEmerald
                            )
                        }
                    }
                }
            }
        }

        // --- Tab 4: Developer Sandbox & Test Simulator ---
        if (selectedTab == 4) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Payment Gateway Sandbox Simulator",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Simulate live customer checkouts, 3D Secure challenges, UPI intent success/failures, and webhook payloads without charging real money.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Button(
                        onClick = { showSandboxDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SnapCyan,
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Open Interactive Virtual Terminal", fontWeight = FontWeight.Bold)
                    }

                    // Test Cards / UPI Reference Card
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF131B2C)),
                        border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Developer Test Credentials & Sandbox Numbers",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapCyan
                            )
                            Text("• Stripe 3DS Success: 4000 0027 6000 3184 (Any expiry, CVC 123)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            Text("• Stripe 3DS Decline: 4000 0002 0115 3184", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            Text("• India UPI Test VPA: success@razorpay / testuser@okaxis", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            Text("• Tabby BNPL UAE: Mobile +971 50 000 0001 (OTP 1234)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            Text("• COD Anti-RTO Shield: Auto-generates OTP via SMS simulator", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }

    // --- Dialogs ---

    // 1. Payout Request Dialog
    if (showPayoutDialog) {
        var payoutAmount by remember { mutableStateOf("1000") }
        var payoutCurrency by remember { mutableStateOf("USD") }
        var selectedBank by remember { mutableStateOf("Mercury Business (USA) - •••• 1102") }

        AlertDialog(
            onDismissRequest = { showPayoutDialog = false },
            title = {
                Text(
                    text = "Request Merchant Payout",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SnapGold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Transfer accumulated store earnings directly to your verified commercial bank account.")

                    OutlinedTextField(
                        value = payoutAmount,
                        onValueChange = { payoutAmount = it },
                        label = { Text("Payout Amount") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("USD", "INR", "AED", "SAR", "EUR").forEach { curr ->
                            FilterChip(
                                selected = payoutCurrency == curr,
                                onClick = {
                                    payoutCurrency = curr
                                    selectedBank = when (curr) {
                                        "INR" -> "HDFC Bank (India) - •••• 9382"
                                        "AED" -> "Emirates NBD (Dubai) - •••• 4019"
                                        "SAR" -> "Al Rajhi Bank (Riyadh) - •••• 7721"
                                        else -> "Mercury Business (USA) - •••• 1102"
                                    }
                                },
                                label = { Text(curr) }
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF131B2C),
                        border = BorderStroke(1.dp, Color(0xFF233252)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Destination Account:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(selectedBank, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
                            Text("Settlement Speed: T+1 Next Business Day (Zero Wire Fee)", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = SnapEmerald)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amount = payoutAmount.toDoubleOrNull() ?: 500.0
                        val bankName = selectedBank.substringBefore(" -")
                        val dest = selectedBank.substringAfter("- ")
                        onRequestPayout(amount, payoutCurrency, dest, bankName)
                        showPayoutDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black)
                ) {
                    Text("Confirm & Initiate Payout", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showPayoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // 2. Gateway API Key Edit Dialog
    if (showCredentialDialog != null) {
        val gw = showCredentialDialog!!
        var apiKey by remember { mutableStateOf(gw.apiKey) }
        var merchantId by remember { mutableStateOf(gw.merchantId) }

        AlertDialog(
            onDismissRequest = { showCredentialDialog = null },
            title = {
                Text(
                    text = "${gw.name} API Configuration",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SnapGold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Manage production & sandbox credentials, Webhook secret URLs, and encryption keys.")

                    OutlinedTextField(
                        value = apiKey,
                        onValueChange = { apiKey = it },
                        label = { Text("Publishable / Secret API Key") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = merchantId,
                        onValueChange = { merchantId = it },
                        label = { Text("Merchant ID / Account ID") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF131B2C),
                        border = BorderStroke(1.dp, Color(0xFF233252)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Webhook Endpoint:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(gw.webhookUrl, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                            Text("Status: Webhook Verified & SSL Handshake Active", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = SnapEmerald)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateGatewayCredentials(gw.id, apiKey, merchantId)
                        showCredentialDialog = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black)
                ) {
                    Text("Save Keys & Re-Sync", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCredentialDialog = null }) {
                    Text("Close")
                }
            }
        )
    }

    // 3. Transaction Detail Inspector Dialog
    if (showTxDetailDialog != null) {
        val tx = showTxDetailDialog!!
        AlertDialog(
            onDismissRequest = { showTxDetailDialog = null },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = SnapGold)
                    Text("Transaction #${tx.id}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Customer: ${tx.customerName} (${tx.customerEmail})", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    Text("Order: ${tx.orderId} • Date: ${tx.createdAt}")
                    Text("Gateway: ${tx.gatewayName} (${tx.gatewayId})")
                    Text("Payment Method: ${tx.method}")
                    Text("Amount: ${tx.currency} ${tx.amount} ($${String.format("%.2f", tx.amountUSD)} USD)")
                    Text("Status: ${tx.status.name}")

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (tx.riskScore < 30) SnapEmeraldContainer.copy(alpha = 0.5f) else SnapRoseContainer.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = if (tx.riskScore < 30) SnapEmerald else SnapRose)
                            Column {
                                Text("Radar AI Risk Score: ${tx.riskScore}/100 (${tx.riskLevel})", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = if (tx.riskScore < 30) SnapEmerald else SnapRose)
                                Text("3D Secure 2.0 Authenticated • Card Issuer Liability Shift: YES", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp))
                            }
                        }
                    }

                    if (tx.failureReason != null) {
                        Text("Failure Reason: ${tx.failureReason}", color = SnapRose, style = MaterialTheme.typography.bodySmall)
                    }
                }
            },
            confirmButton = {
                if (tx.status == PaymentTransactionStatus.SUCCESS) {
                    Button(
                        onClick = {
                            showRefundDialog = tx
                            showTxDetailDialog = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SnapRose, contentColor = Color.White)
                    ) {
                        Text("Issue Refund")
                    }
                } else {
                    Button(onClick = { showTxDetailDialog = null }) {
                        Text("Close")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showTxDetailDialog = null }) {
                    Text("Dismiss")
                }
            }
        )
    }

    // 4. Refund Confirmation Dialog
    if (showRefundDialog != null) {
        val tx = showRefundDialog!!
        var refundReason by remember { mutableStateOf("Customer returned package in good condition") }

        AlertDialog(
            onDismissRequest = { showRefundDialog = null },
            title = { Text("Confirm Refund for #${tx.id}", color = SnapRose) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("This will immediately credit ${tx.currency} ${tx.amount} back to the customer's original payment method via ${tx.gatewayName}.")
                    OutlinedTextField(
                        value = refundReason,
                        onValueChange = { refundReason = it },
                        label = { Text("Reason for Refund") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onRefundTransaction(tx.id, refundReason)
                        showRefundDialog = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapRose, contentColor = Color.White)
                ) {
                    Text("Execute Full Refund")
                }
            },
            dismissButton = {
                TextButton(onClick = { showRefundDialog = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // 5. Interactive Sandbox Test Simulator Dialog
    if (showSandboxDialog) {
        var simGateway by remember { mutableStateOf("gw_stripe") }
        var simAmount by remember { mutableStateOf("49.99") }
        var simCurrency by remember { mutableStateOf("USD") }
        var simCustomer by remember { mutableStateOf("Arthur Pendelton") }
        var simMethod by remember { mutableStateOf("Visa 3DS •••• 4242") }
        var simOutcome by remember { mutableStateOf(PaymentTransactionStatus.SUCCESS) }

        AlertDialog(
            onDismissRequest = { showSandboxDialog = false },
            title = {
                Text(
                    text = "Virtual Terminal Sandbox",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SnapCyan
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Trigger real-time simulated card authorizations, UPI intents, or failures.")

                    OutlinedTextField(
                        value = simCustomer,
                        onValueChange = { simCustomer = it },
                        label = { Text("Simulated Customer Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = simAmount,
                            onValueChange = { simAmount = it },
                            label = { Text("Amount") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = simCurrency,
                            onValueChange = { simCurrency = it },
                            label = { Text("Currency") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("gw_stripe" to "Stripe", "gw_razorpay" to "Razorpay", "gw_tabby" to "Tabby BNPL").forEach { (id, name) ->
                            FilterChip(
                                selected = simGateway == id,
                                onClick = {
                                    simGateway = id
                                    simCurrency = when (id) {
                                        "gw_razorpay" -> "INR"
                                        "gw_tabby" -> "AED"
                                        else -> "USD"
                                    }
                                    simMethod = when (id) {
                                        "gw_razorpay" -> "UPI Intent (GPay)"
                                        "gw_tabby" -> "Tabby 4-Split"
                                        else -> "Visa 3DS •••• 4242"
                                    }
                                },
                                label = { Text(name) }
                            )
                        }
                    }

                    Text("Outcome to simulate:", style = MaterialTheme.typography.labelSmall)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        FilterChip(
                            selected = simOutcome == PaymentTransactionStatus.SUCCESS,
                            onClick = { simOutcome = PaymentTransactionStatus.SUCCESS },
                            label = { Text("Success (200 OK)") }
                        )
                        FilterChip(
                            selected = simOutcome == PaymentTransactionStatus.FAILED,
                            onClick = { simOutcome = PaymentTransactionStatus.FAILED },
                            label = { Text("3DS Fail (402)") }
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amt = simAmount.toDoubleOrNull() ?: 49.99
                        onSimulatePayment(simGateway, amt, simCurrency, simCustomer, simMethod, simOutcome)
                        showSandboxDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapCyan, contentColor = Color.Black)
                ) {
                    Text("Fire Test Transaction", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSandboxDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
