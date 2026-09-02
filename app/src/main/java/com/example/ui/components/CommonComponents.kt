package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import androidx.compose.ui.layout.ContentScale
import com.example.data.model.*
import com.example.ui.theme.*

@Composable
fun BrandLogoHeader(
    modifier: Modifier = Modifier,
    compact: Boolean = false,
    showTagline: Boolean = true,
    taglineText: String = "FIND • LAUNCH • SELL",
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(if (compact) 34.dp else 42.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF1E2433), Color(0xFF0F121A))
                    )
                )
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.6f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_brand_logo),
                contentDescription = "Within A Snap Logo",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "WITHIN",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = BodoniFontFamily,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.6.sp,
                        fontSize = if (compact) 15.sp else 18.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    softWrap = false
                )
                Text(
                    text = "A SNAP",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = BodoniFontFamily,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.6.sp,
                        fontSize = if (compact) 15.sp else 18.sp
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    softWrap = false
                )
            }
            if (showTagline) {
                Text(
                    text = taglineText,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = BodoniFontFamily,
                        fontSize = if (compact) 9.sp else 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    ),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                    maxLines = 1,
                    softWrap = false
                )
            }
        }
    }
}

fun getProductDrawableRes(productId: String, category: String = ""): Int {
    return when {
        productId.contains("01") || productId.contains("neck", ignoreCase = true) || productId.contains("spine", ignoreCase = true) -> R.drawable.img_neck_massager
        productId.contains("02") || productId.contains("glow", ignoreCase = true) || productId.contains("aura", ignoreCase = true) || productId.contains("facial", ignoreCase = true) -> R.drawable.img_facial_wand
        productId.contains("03") || productId.contains("mag", ignoreCase = true) || productId.contains("charger", ignoreCase = true) -> R.drawable.img_magsafe_charger
        productId.contains("04") || productId.contains("cushion", ignoreCase = true) || productId.contains("lumbar", ignoreCase = true) || productId.contains("ergo", ignoreCase = true) -> R.drawable.img_lumbar_cushion
        productId.contains("05") || productId.contains("groom", ignoreCase = true) || productId.contains("pet", ignoreCase = true) || productId.contains("vacuum", ignoreCase = true) -> R.drawable.img_pet_groomer
        category.contains("Fitness", ignoreCase = true) || category.contains("Health", ignoreCase = true) -> R.drawable.img_neck_massager
        category.contains("Beauty", ignoreCase = true) || category.contains("Skin", ignoreCase = true) -> R.drawable.img_facial_wand
        category.contains("Electronic", ignoreCase = true) || category.contains("Tech", ignoreCase = true) -> R.drawable.img_magsafe_charger
        category.contains("Home", ignoreCase = true) || category.contains("Office", ignoreCase = true) -> R.drawable.img_lumbar_cushion
        category.contains("Pet", ignoreCase = true) -> R.drawable.img_pet_groomer
        else -> R.drawable.img_neck_massager
    }
}

@Composable
fun ProductImageCard(
    productId: String,
    productName: String,
    category: String = "",
    modifier: Modifier = Modifier,
    height: androidx.compose.ui.unit.Dp = 180.dp,
    contentScale: ContentScale = ContentScale.Crop,
    showBadge: Boolean = true,
    badgeText: String? = null
) {
    val drawableRes = getProductDrawableRes(productId, category)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF0F1420))
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = productName,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale
        )

        // Subtle gradient overlay at bottom for contrast
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.55f)),
                        startY = 100f
                    )
                )
        )

        if (showBadge) {
            val label = badgeText ?: when {
                productId.contains("01") -> "🔥 #1 BESTSELLER"
                productId.contains("02") -> "✨ VIRAL BEAUTY"
                productId.contains("03") -> "⚡ 15W FAST CHARGE"
                productId.contains("04") -> "🪑 ORTHOPEDIC"
                productId.contains("05") -> "🐾 PET CARE PRO"
                else -> "⭐ HIGH DEMAND"
            }
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color.Black.copy(alpha = 0.75f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)),
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 9.sp,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}


@Composable
fun SnapStatCard(
    title: String,
    value: String,
    trend: String,
    isPositive: Boolean = true,
    icon: ImageVector,
    accentColor: Color = SnapGold,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("stat_card_${title.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = if (isPositive) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    contentDescription = null,
                    tint = if (isPositive) SnapEmerald else SnapRose,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = trend,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = if (isPositive) SnapEmerald else SnapRose
                )
                Text(
                    text = "vs last week",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun OpportunityScoreBadge(
    score: Int,
    modifier: Modifier = Modifier,
    label: String = "AI Opportunity Score"
) {
    val (color, containerColor) = when {
        score >= 90 -> SnapEmerald to SnapEmeraldContainer.copy(alpha = 0.3f)
        score >= 75 -> SnapGold to SnapGoldContainer.copy(alpha = 0.3f)
        else -> SnapRose to SnapRoseContainer.copy(alpha = 0.3f)
    }

    Surface(
        modifier = modifier.clip(RoundedCornerShape(10.dp)),
        color = containerColor,
        border = BorderStroke(1.dp, color.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = "$label: ",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$score/100",
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = color
            )
        }
    }
}

@Composable
fun AgentAvatarBadge(
    agentType: AgentType,
    modifier: Modifier = Modifier
) {
    val (name, color, icon) = when (agentType) {
        AgentType.RESEARCH -> Triple("Research Agent", SnapCyan, Icons.Default.TravelExplore)
        AgentType.SOURCING -> Triple("Sourcing Agent", SnapGold, Icons.Default.LocalShipping)
        AgentType.CONTENT -> Triple("Content Agent", SnapViolet, Icons.Default.AutoStories)
        AgentType.PRICING -> Triple("Pricing Agent", SnapEmerald, Icons.Default.PriceCheck)
        AgentType.MARKETING -> Triple("Marketing Agent", Color(0xFFEC4899), Icons.Default.Campaign)
        AgentType.OPERATIONS -> Triple("Operations Agent", Color(0xFF3B82F6), Icons.Default.Inventory)
        AgentType.SUPPORT -> Triple("Support Agent", Color(0xFF14B8A6), Icons.Default.SupportAgent)
        AgentType.FINANCE -> Triple("Finance Agent", Color(0xFF10B981), Icons.Default.AccountBalance)
        AgentType.RISK -> Triple("Risk Agent", SnapRose, Icons.Default.Shield)
        AgentType.ORCHESTRATOR -> Triple("Master Orchestrator", SnapGold, Icons.Default.Hub)
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = name,
            tint = color,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            ),
            color = color
        )
    }
}

@Composable
fun AiRecommendationCard(
    recommendation: AIRecommendation,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    onEdit: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("ai_recommendation_card_${recommendation.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = BorderStroke(
            1.dp,
            if (recommendation.status == RecommendationStatus.APPROVED) SnapEmerald.copy(alpha = 0.8f)
            else SnapGold.copy(alpha = 0.4f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AgentAvatarBadge(agentType = recommendation.agentType)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = SnapGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${recommendation.confidenceScore}% Confidence",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = SnapGold
                    )
                }
            }

            Text(
                text = recommendation.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = recommendation.recommendation,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
            )

            // Reason & Impact Box
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "💡 Reason:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = SnapGold
                        )
                        Text(
                            text = recommendation.reason,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "📈 Impact:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = SnapEmerald
                        )
                        Text(
                            text = recommendation.expectedImpact,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Action Controls
            if (recommendation.status == RecommendationStatus.PENDING) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onApprove,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("approve_btn_${recommendation.id}"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SnapEmerald,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Approve & Execute",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    if (onEdit != null) {
                        OutlinedButton(
                            onClick = onEdit,
                            modifier = Modifier.testTag("edit_btn_${recommendation.id}"),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, SnapGold),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = SnapGold),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(16.dp))
                        }
                    }

                    OutlinedButton(
                        onClick = onReject,
                        modifier = Modifier.testTag("reject_btn_${recommendation.id}"),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, SnapRose.copy(alpha = 0.5f)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = SnapRose),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Reject", modifier = Modifier.size(16.dp))
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (recommendation.status == RecommendationStatus.APPROVED) SnapEmerald.copy(alpha = 0.15f)
                            else SnapRose.copy(alpha = 0.15f)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (recommendation.status == RecommendationStatus.APPROVED) "✓ Action Approved & Active" else "✕ Action Dismissed",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (recommendation.status == RecommendationStatus.APPROVED) SnapEmerald else SnapRose
                    )
                    Text(
                        text = recommendation.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val (color, bgColor) = when (status.uppercase()) {
        "PAID", "DELIVERED", "ACTIVE", "FULFILLED" -> SnapEmerald to SnapEmeraldContainer.copy(alpha = 0.3f)
        "PROCESSING", "SHIPPED", "OPTIMIZING" -> SnapCyan to Color(0xFF083344).copy(alpha = 0.4f)
        "PENDING", "DRAFT" -> SnapAmber to Color(0xFF451A03).copy(alpha = 0.4f)
        "REFUNDED", "CANCELLED", "FAILED" -> SnapRose to SnapRoseContainer.copy(alpha = 0.3f)
        else -> MaterialTheme.colorScheme.onSurfaceVariant to MaterialTheme.colorScheme.surfaceVariant
    }

    Surface(
        modifier = modifier.clip(RoundedCornerShape(6.dp)),
        color = bgColor,
        border = BorderStroke(1.dp, color.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = status,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            ),
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun CountryFlagBadge(
    marketCode: String,
    modifier: Modifier = Modifier
) {
    val (flag, name) = when (marketCode) {
        "IN" -> "🇮🇳" to "India"
        "AE" -> "🇦🇪" to "UAE"
        "SA" -> "🇸🇦" to "Saudi Arabia"
        "US" -> "🇺🇸" to "USA"
        "GB" -> "🇬🇧" to "UK"
        "EU" -> "🇪🇺" to "Europe"
        else -> "🌐" to marketCode
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = flag, fontSize = 12.sp)
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (actionText != null && onActionClick != null) {
            TextButton(
                onClick = onActionClick,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = SnapGold
                )
            }
        }
    }
}
