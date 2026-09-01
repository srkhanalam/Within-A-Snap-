package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FraudCheckReport
import com.example.data.model.RiskLevel
import com.example.ui.theme.*

@Composable
fun RiskAndFraudScreen(
    fraudReports: List<FraudCheckReport>,
    onApproveOrder: (String) -> Unit,
    onBlockOrder: (String) -> Unit,
    onSanitizeAddress: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("ALL") }
    var addressDialogReport by remember { mutableStateOf<FraudCheckReport?>(null) }
    var editedAddressText by remember { mutableStateOf("") }

    val filteredReports = remember(fraudReports, selectedFilter) {
        when (selectedFilter) {
            "CRITICAL" -> fraudReports.filter { it.riskLevel == RiskLevel.CRITICAL }
            "MEDIUM" -> fraudReports.filter { it.riskLevel == RiskLevel.MEDIUM }
            "LOW" -> fraudReports.filter { it.riskLevel == RiskLevel.LOW }
            else -> fraudReports
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("risk_and_fraud_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131826)),
                border = BorderStroke(1.dp, SnapRose.copy(alpha = 0.5f))
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
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(SnapRose.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Security, contentDescription = null, tint = SnapRose, modifier = Modifier.size(20.dp))
                            }
                            Column {
                                Text(
                                    "Anti-Fraud & Real-Time Risk Shield",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    "Disposable Email Blacklist • COD Auto-Sanitizer • Velocity Guard",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SnapEmerald.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.4f))
                        ) {
                            Text(
                                "99.8% Protected",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapEmerald,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Telemetry Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF0F1420),
                            border = BorderStroke(1.dp, Color(0xFF222C40))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Blocked Fraud", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("$12,480", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = SnapRose)
                            }
                        }
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF0F1420),
                            border = BorderStroke(1.dp, Color(0xFF222C40))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("RTO Prevention", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("-28.4%", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = SnapEmerald)
                            }
                        }
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF0F1420),
                            border = BorderStroke(1.dp, Color(0xFF222C40))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Sanitized Orders", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("384 Orders", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = SnapCyan)
                            }
                        }
                    }
                }
            }
        }

        // Filter Pills
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("ALL" to "All Scans", "CRITICAL" to "🚨 Critical Risk", "MEDIUM" to "⚠️ OTP Verify", "LOW" to "✅ Verified Clean").forEach { (key, label) ->
                    FilterChip(
                        selected = selectedFilter == key,
                        onClick = { selectedFilter = key },
                        label = { Text(label, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SnapRose.copy(alpha = 0.2f),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Risk Reports List
        items(filteredReports) { report ->
            val riskAccent = when (report.riskLevel) {
                RiskLevel.CRITICAL -> SnapRose
                RiskLevel.MEDIUM -> SnapAmber
                RiskLevel.LOW -> SnapEmerald
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, riskAccent.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Title & Badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    "Order #${report.orderId}",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    "• ${report.customerName}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                "${report.email} | ${report.phone}",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = riskAccent.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, riskAccent.copy(alpha = 0.5f))
                        ) {
                            Text(
                                "Risk Score: ${report.riskScore}/100",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                color = riskAccent,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Diagnostics Grid
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF0F1420),
                        border = BorderStroke(1.dp, Color(0xFF1E2838))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("IP / Proxy:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(report.ipAddress, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold), color = Color.White)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Disposable Email Check:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(
                                    if (report.isDisposableEmail) "❌ FAKE / DISPOSABLE DOMAIN" else "✅ Verified Clean Mailbox",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (report.isDisposableEmail) SnapRose else SnapEmerald
                                )
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Order Velocity (24h):", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(
                                    "${report.velocityOrdersLast24h} orders/IP ${if (report.velocityOrdersLast24h > 3) "(🚨 VELOCITY SPIKE)" else "(Normal)"}",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = if (report.velocityOrdersLast24h > 3) SnapRose else SnapEmerald
                                )
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Estimated RTO Probability:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${report.rtoProbabilityPct}%", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = riskAccent)
                            }
                        }
                    }

                    // Address Sanitization Box
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF151C2C),
                        border = BorderStroke(1.dp, if (report.addressSanitized) SnapCyan.copy(alpha = 0.4f) else SnapAmber.copy(alpha = 0.4f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(14.dp))
                                    Text(
                                        if (report.addressSanitized) "AI Sanitized Delivery Address (Standardized):" else "Raw Unsanitized Address:",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (report.addressSanitized) SnapCyan else SnapAmber
                                    )
                                }

                                TextButton(
                                    onClick = {
                                        addressDialogReport = report
                                        editedAddressText = report.sanitizedAddress
                                    },
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.height(24.dp)
                                ) {
                                    Text("Edit", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                                }
                            }

                            Text(
                                text = report.sanitizedAddress,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }

                    // Reasons Tag Cloud
                    if (report.reasons.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            report.reasons.take(3).forEach { reason ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFF1E2638)
                                ) {
                                    Text(
                                        reason,
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onBlockOrder(report.orderId) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("block_order_btn_${report.orderId}"),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, SnapRose.copy(alpha = 0.6f)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = SnapRose)
                        ) {
                            Icon(Icons.Default.Block, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Block & Blacklist", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                        }

                        Button(
                            onClick = { onApproveOrder(report.orderId) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("approve_order_btn_${report.orderId}"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SnapEmerald)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Black)
                            Spacer(Modifier.width(6.dp))
                            Text("Approve Dispatch", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.Black)
                        }
                    }
                }
            }
        }
    }

    // Address Edit Dialog
    if (addressDialogReport != null) {
        AlertDialog(
            onDismissRequest = { addressDialogReport = null },
            title = { Text("Sanitize & Correct Delivery Address", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Order #${addressDialogReport?.orderId} for ${addressDialogReport?.customerName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = editedAddressText,
                        onValueChange = { editedAddressText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Standardized Courier Address") },
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        addressDialogReport?.let { report ->
                            onSanitizeAddress(report.orderId, editedAddressText)
                        }
                        addressDialogReport = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SnapCyan)
                ) {
                    Text("Save & Sanitize", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { addressDialogReport = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
