package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.BrandLogoHeader
import com.example.ui.components.ProductImageCard
import com.example.ui.theme.*

@Composable
fun LandingScreen(
    onStartFree: () -> Unit,
    onExploreAi: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("landing_screen"),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Top Navigation Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BrandLogoHeader(compact = false)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onStartFree,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SnapGold,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("landing_nav_start_free")
                    ) {
                        Text(
                            text = "Launch OS",
                            fontWeight = FontWeight.Bold,
                            fontFamily = BodoniFontFamily
                        )
                    }
                }
            }
        }

        // Hero Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // AI Badge Pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SnapGoldContainer.copy(alpha = 0.4f),
                    border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.6f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = SnapGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "THE AI-FIRST DROPSHIPPING OS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            color = SnapGold
                        )
                    }
                }

                Text(
                    text = "FIND. LAUNCH. SELL.\nWITHIN A SNAP.",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 32.sp,
                        lineHeight = 38.sp,
                        letterSpacing = (-0.5).sp
                    ),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Your autonomous AI e-commerce team for discovering profitable products, evaluating verified suppliers, generating high-converting listings, and scaling globally across India, UAE, Saudi Arabia, USA & Europe.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onStartFree,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("landing_hero_start_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SnapGold,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.RocketLaunch,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Start Free",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    OutlinedButton(
                        onClick = onExploreAi,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("landing_hero_explore_btn"),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Hub,
                            contentDescription = null,
                            tint = SnapViolet,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "AI Team",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }

        // Live Simulated Ticker Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "10 Specialized",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapGold
                        )
                        Text(text = "AI Agents", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Divider(modifier = Modifier.height(30.dp).width(1.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "6+ Global",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapEmerald
                        )
                        Text(text = "Markets", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Divider(modifier = Modifier.height(30.dp).width(1.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "1-Click",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapCyan
                        )
                        Text(text = "Launch Flow", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        // Live Winning Products Spotlight with Clear Photography
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LIVE WINNING PRODUCTS",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = BodoniFontFamily,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "READY TO LAUNCH",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = SnapGold
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        ProductImageCard(
                            productId = "prod_01",
                            productName = "Smart Pulse Neck Massager",
                            category = "Health & Fitness",
                            height = 130.dp,
                            badgeText = "🔥 #1 BESTSELLER"
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        ProductImageCard(
                            productId = "prod_02",
                            productName = "AuraGlow 4-in-1 Facial Wand",
                            category = "Beauty & Skincare",
                            height = 130.dp,
                            badgeText = "✨ VIRAL BEAUTY"
                        )
                    }
                }
            }
        }

        // 1-Click Multi-Step Workflow Showcase Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF101522)
                ),
                border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
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
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(SnapGold),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Bolt, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                            }
                            Text(
                                text = "1-SNAP WORKFLOW DEMO",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                                color = SnapGold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SnapEmerald.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "LIVE WORKFLOW",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = SnapEmerald,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF0B0E14),
                        border = BorderStroke(1.dp, Color(0xFF1E2638))
                    ) {
                        Text(
                            text = "“Find me a profitable fitness product for UAE and prepare it for launch.”",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        WorkflowStepItem(num = "1", title = "AI Research Agent discovers Sonic Neck Massager", detail = "Opportunity Score: 95/100 • Margin: 48%")
                        WorkflowStepItem(num = "2", title = "AI Sourcing Agent picks ApexDirect Hub", detail = "5-Day GCC Express Delivery • 96% Reliability")
                        WorkflowStepItem(num = "3", title = "AI Pricing Engine configures UAE Price", detail = "AED 99 Retail (Local psychology & duties optimized)")
                        WorkflowStepItem(num = "4", title = "AI Content & Marketing generate full copy", detail = "SEO Listing, Meta/TikTok hooks, UGC Video Script")
                    }

                    Button(
                        onClick = onStartFree,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Open Full OS Dashboard", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Executive Leadership & Direct Support
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
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
                                        text = "Parvej Alam",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Icon(Icons.Default.Verified, contentDescription = "Verified", tint = SnapGold, modifier = Modifier.size(14.dp))
                                }
                                Text(
                                    text = "Platform Owner & Managing Director",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = SnapGoldLight
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SnapEmeraldContainer.copy(alpha = 0.4f),
                            border = BorderStroke(1.dp, SnapEmerald.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "DIRECT CONTACT",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                                color = SnapEmerald,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Email, contentDescription = null, tint = SnapGold, modifier = Modifier.size(14.dp))
                            Text(
                                text = "parvejalam1703@gmail.com",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = SnapEmerald, modifier = Modifier.size(14.dp))
                            Text(
                                text = "+919305868395",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                                color = SnapEmerald
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkflowStepItem(num: String, title: String, detail: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(SnapViolet.copy(alpha = 0.2f))
                .border(1.dp, SnapViolet, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = num, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = SnapViolet)
        }
        Column {
            Text(text = title, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold), color = Color.White)
            Text(text = detail, style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp), color = SnapGoldLight)
        }
    }
}
