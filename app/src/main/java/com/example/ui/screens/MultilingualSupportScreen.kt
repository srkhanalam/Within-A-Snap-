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
import com.example.data.model.MultilingualTicket
import com.example.ui.theme.*

@Composable
fun MultilingualSupportScreen(
    tickets: List<MultilingualTicket>,
    onResolveTicket: (String) -> Unit,
    onApplyMacro: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("multilingual_support_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131826)),
                border = BorderStroke(1.dp, SnapViolet.copy(alpha = 0.5f))
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
                                    .background(SnapViolet.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Translate, contentDescription = null, tint = SnapViolet, modifier = Modifier.size(20.dp))
                            }
                            Column {
                                Text(
                                    "Multilingual AI Support & Smart Macros",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    "Arabic • Hindi • Spanish • German • Auto 2-Way Translation",
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
                                "Live AI Translation",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapEmerald,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Text(
                        "Incoming queries in non-English languages are instantly translated to English for your team, with smart macros that compose perfect native responses back to the customer.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Tickets Stream
        items(tickets) { ticket ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, if (ticket.isResolved) SnapEmerald.copy(alpha = 0.3f) else SnapViolet.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "${ticket.customerName} (${ticket.orderId})",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                "${ticket.customerEmail} • ${ticket.originalLanguage}",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (ticket.isResolved) SnapEmerald.copy(alpha = 0.2f) else SnapAmber.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, if (ticket.isResolved) SnapEmerald.copy(alpha = 0.4f) else SnapAmber.copy(alpha = 0.4f))
                        ) {
                            Text(
                                if (ticket.isResolved) "Resolved" else "Needs Reply",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (ticket.isResolved) SnapEmerald else SnapAmber,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    // Original Customer Query in Native Language
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF0F1420),
                        border = BorderStroke(1.dp, Color(0xFF1E2838))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.Language, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(14.dp))
                                Text("Customer Message (${ticket.originalLanguage}):", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                            }
                            Text("“${ticket.originalQuery}”", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                        }
                    }

                    // Auto-Translated to English Box
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF151D2C),
                        border = BorderStroke(1.dp, SnapViolet.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SnapViolet, modifier = Modifier.size(14.dp))
                                Text("AI English Translation for Merchant:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapViolet)
                            }
                            Text(ticket.translatedToEnglish, style = MaterialTheme.typography.bodySmall, color = Color.White)
                        }
                    }

                    // Native Translated Draft Reply
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF101C24),
                        border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.Send, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(14.dp))
                                Text("AI Native Language Reply (${ticket.originalLanguage}):", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapEmerald)
                            }
                            Text(ticket.translatedReplyNative, style = MaterialTheme.typography.bodySmall, color = Color.White)
                        }
                    }

                    // Smart Macro Quick Actions
                    if (!ticket.isResolved) {
                        Text("1-Tap Smart Macro Resolution:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            OutlinedButton(
                                onClick = { onApplyMacro(ticket.id, "TRACKING_LOOKUP") },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                            ) {
                                Text("📦 Send Tracking", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapCyan)
                            }

                            OutlinedButton(
                                onClick = { onApplyMacro(ticket.id, "ADDRESS_CHANGE") },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                            ) {
                                Text("📍 Fix Address", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapGold)
                            }

                            Button(
                                onClick = { onResolveTicket(ticket.id) },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = SnapEmerald),
                                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 6.dp)
                            ) {
                                Text("✅ Send & Close", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}
