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
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.StoreInvoice
import com.example.data.model.ThermalShippingLabel
import com.example.data.model.WarehousePackingSlip
import com.example.ui.theme.*

@Composable
fun InvoiceAndLabelScreen(
    invoices: List<StoreInvoice>,
    shippingLabels: List<ThermalShippingLabel>,
    packingSlips: List<WarehousePackingSlip>,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Tax Invoices", "4x6 Thermal Labels", "Packing Slips")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("invoice_and_label_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131826)),
                border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.5f))
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
                                    .background(SnapCyan.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(20.dp))
                            }
                            Column {
                                Text(
                                    "Tax Invoicing & Thermal Shipping Labels",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    "GSTIN & GCC VAT Compliant • 4x6 Barcode Labels • Warehouse Slips",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SnapCyan.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.4f))
                        ) {
                            Text(
                                "Auto-Generated",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapCyan,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Tab Selector
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color(0xFF0F1420),
                        contentColor = SnapCyan,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                color = SnapCyan
                            )
                        },
                        divider = {}
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = {
                                    Text(
                                        title,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (selectedTab == index) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        // Content Tabs
        when (selectedTab) {
            0 -> {
                // Invoices List
                items(invoices) { invoice ->
                    InvoiceCard(invoice = invoice)
                }
            }
            1 -> {
                // 4x6 Thermal Labels List
                items(shippingLabels) { label ->
                    ThermalLabelCard(label = label)
                }
            }
            2 -> {
                // Packing Slips List
                items(packingSlips) { slip ->
                    PackingSlipCard(slip = slip)
                }
            }
        }
    }
}

@Composable
fun InvoiceCard(invoice: StoreInvoice) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(1.dp, Color(0xFF1E2838))
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
                Column {
                    Text(
                        invoice.invoiceNumber,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                        color = Color.White
                    )
                    Text(
                        "Order #${invoice.orderId} • ${invoice.invoiceDate}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = SnapEmerald.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.4f))
                ) {
                    Text(
                        invoice.status,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = SnapEmerald,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            // Customer Info Box
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF0F1420),
                border = BorderStroke(1.dp, Color(0xFF1E2838))
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Billed & Shipped To:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                    Text(invoice.customerName, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = Color.White)
                    Text(invoice.shippingAddress, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Tax Registration: ${invoice.taxIdNumber}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            // Line Items
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                invoice.items.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.title, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                            Text("${item.sku} • ${item.hsnCode} (Tax ${item.taxPct}%)", style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text(
                            "${invoice.currency} ${String.format("%.2f", item.total)}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                }
            }

            Divider(color = Color(0xFF1E2838), thickness = 1.dp)

            // Tax & Grand Total Breakdown
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Subtotal:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("${invoice.currency} ${String.format("%.2f", invoice.subtotal)}", style = MaterialTheme.typography.bodySmall, color = Color.White)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Tax Amount (GST / VAT):", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("+${invoice.currency} ${String.format("%.2f", invoice.taxAmount)}", style = MaterialTheme.typography.bodySmall, color = SnapCyan)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Grand Total Paid:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = SnapGold)
                Text("${invoice.currency} ${String.format("%.2f", invoice.grandTotal)}", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = SnapGold)
            }

            // Download Button
            Button(
                onClick = { /* Simulated PDF download */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A2338))
            ) {
                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp), tint = SnapCyan)
                Spacer(Modifier.width(6.dp))
                Text("Download Tax PDF Invoice", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
            }
        }
    }
}

@Composable
fun ThermalLabelCard(label: ThermalShippingLabel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)), // White thermal sticker look
        border = BorderStroke(2.dp, Color(0xFF2B3A55))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header Barcode
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    label.carrierName.uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                    color = Color.Black
                )
                Surface(
                    color = Color.Black,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        label.paymentMode,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Divider(color = Color.Black, thickness = 2.dp)

            // Routing & Hub codes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("ROUTING CODE", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold), color = Color.Gray)
                    Text(label.routingCode, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black), color = Color.Black)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("HUB / SORT", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold), color = Color.Gray)
                    Text(label.hubCode, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black), color = Color.Black)
                }
            }

            Divider(color = Color.Black, thickness = 1.dp)

            // Recipient Details
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text("DELIVER TO:", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold), color = Color.Gray)
                Text(label.recipientName, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black), color = Color.Black)
                Text(label.recipientAddress, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold), color = Color.Black)
                Text("${label.recipientCity} - PIN: ${label.recipientPincode}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Black), color = Color.Black)
                Text("Ph: ${label.recipientPhone}", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
            }

            Divider(color = Color.Black, thickness = 1.dp)

            // Barcode Graphic Simulation
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Vertical Barcode lines
                    repeat(32) { index ->
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(if (index % 3 == 0) 3.dp else if (index % 2 == 0) 1.dp else 2.dp)
                                .background(Color.Black)
                        )
                    }
                }
            }

            Text(
                "TRACKING: ${label.trackingNumber}",
                style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Print button
            Button(
                onClick = { /* Simulated Print */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                Spacer(Modifier.width(6.dp))
                Text("Print 4x6 Thermal Label", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
            }
        }
    }
}

@Composable
fun PackingSlipCard(slip: WarehousePackingSlip) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = BorderStroke(1.dp, Color(0xFF1E2838))
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
                Column {
                    Text(slip.slipNumber, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Color.White)
                    Text("Order #${slip.orderId} • ${slip.date}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = SnapEmerald.copy(alpha = 0.2f)
                ) {
                    Text(
                        "QC ${slip.qualityCheckStatus}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = SnapEmerald,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF0F1420),
                border = BorderStroke(1.dp, Color(0xFF1E2838))
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Fulfillment Location:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapViolet)
                    Text(slip.warehouseLocation, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Text("Assigned Picker: ${slip.pickerName} | Box: ${slip.packingBoxSize}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Text("Item Pick List:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
            slip.items.forEach { (item, qty) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircleOutline, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(16.dp))
                        Text(item, style = MaterialTheme.typography.bodySmall, color = Color.White)
                    }
                    Text("Qty: $qty", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                }
            }
        }
    }
}
