package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.data.model.OnboardingPlan
import com.example.ui.components.BrandLogoHeader
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    currentPlan: OnboardingPlan,
    onComplete: (OnboardingPlan) -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableIntStateOf(1) }

    var merchantName by remember { mutableStateOf(currentPlan.merchantName) }
    var businessName by remember { mutableStateOf(currentPlan.businessName) }
    var selectedNiche by remember { mutableStateOf(currentPlan.niche) }
    var selectedMarkets by remember { mutableStateOf(currentPlan.targetMarkets.toSet()) }
    var monthlyBudget by remember { mutableDoubleStateOf(currentPlan.monthlyBudgetUSD) }
    var desiredMargin by remember { mutableDoubleStateOf(currentPlan.desiredMarginPct) }
    var preferredCurrency by remember { mutableStateOf(currentPlan.preferredCurrency) }
    var supplierPreference by remember { mutableStateOf(currentPlan.supplierPreference) }

    val totalSteps = 3

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
            .testTag("onboarding_screen"),
        contentPadding = PaddingValues(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BrandLogoHeader(compact = true)
        }

        // Progress Bar
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "AI STORE SETUP WIZARD",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                        color = SnapGold
                    )
                    Text(
                        text = "Step $step of $totalSteps",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                LinearProgressIndicator(
                    progress = { step.toFloat() / totalSteps.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = SnapGold,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        // Step 1: Business Identity & Niche
        if (step == 1) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "1. Business Identity & Niche",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Tell your AI e-commerce team what you want to sell and who you are.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        OutlinedTextField(
                            value = merchantName,
                            onValueChange = { merchantName = it },
                            label = { Text("Your Name") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = businessName,
                            onValueChange = { businessName = it },
                            label = { Text("Business / Store Name") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Text(
                            text = "Select Primary Niche:",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )

                        val niches = listOf(
                            "Smart Fitness & Ergonomics",
                            "Beauty, Skincare & Anti-Aging",
                            "Consumer Electronics & MagSafe",
                            "Home Office & Ergonomic Furniture",
                            "Viral Pet Care & Grooming Tech"
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            niches.forEach { niche ->
                                val isSelected = selectedNiche == niche
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { selectedNiche = niche },
                                    color = if (isSelected) SnapGoldContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, if (isSelected) SnapGold else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = niche,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                            ),
                                            color = if (isSelected) SnapGold else MaterialTheme.colorScheme.onSurface
                                        )
                                        if (isSelected) {
                                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SnapGold, modifier = Modifier.size(18.dp))
                                        }
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { step = 2 },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Next: Target Markets & Capital", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Step 2: Target Markets, Budgets & Margins
        if (step == 2) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "2. Target Markets & Margin Goals",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "Select International Target Countries:",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )

                        val marketOptions = listOf(
                            "AE" to "🇦🇪 United Arab Emirates (High AOV, 3-day express)",
                            "SA" to "🇸🇦 Saudi Arabia (Fastest growing GCC market)",
                            "IN" to "🇮🇳 India (Huge volume, UPI payments)",
                            "US" to "🇺🇸 United States (Massive market scale)",
                            "GB" to "🇬🇧 United Kingdom & Europe"
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            marketOptions.forEach { (code, label) ->
                                val isChecked = selectedMarkets.contains(code)
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable {
                                            selectedMarkets = if (isChecked) selectedMarkets - code else selectedMarkets + code
                                        },
                                    color = if (isChecked) SnapEmeraldContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, if (isChecked) SnapEmerald else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Normal
                                            )
                                        )
                                        Checkbox(
                                            checked = isChecked,
                                            onCheckedChange = { checked ->
                                                selectedMarkets = if (checked) selectedMarkets + code else selectedMarkets - code
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Text(
                            text = "Desired Net Profit Margin: ${desiredMargin.toInt()}%",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Slider(
                            value = desiredMargin.toFloat(),
                            onValueChange = { desiredMargin = it.toDouble() },
                            valueRange = 25f..65f,
                            steps = 7,
                            colors = SliderDefaults.colors(thumbColor = SnapGold, activeTrackColor = SnapGold)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { step = 1 },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Back")
                            }
                            Button(
                                onClick = { step = 3 },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Generate Plan", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Step 3: AI Generated Business Plan
        if (step == 3) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = SnapGold)
                            Text(
                                text = "Your AI Initial Business Plan",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = SnapGold
                            )
                        }

                        Text(
                            text = "Prepared exclusively for $businessName targeting ${selectedMarkets.joinToString(", ")} with a minimum ${desiredMargin.toInt()}% margin target.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(
                                    text = "AI Recommended Roadmap:",
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                    color = SnapEmerald
                                )

                                currentPlan.generatedActionSteps.forEachIndexed { idx, stepText ->
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(text = "${idx + 1}.", fontWeight = FontWeight.Bold, color = SnapGold)
                                        Text(text = stepText, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = {
                                val updatedPlan = currentPlan.copy(
                                    merchantName = merchantName,
                                    businessName = businessName,
                                    niche = selectedNiche,
                                    targetMarkets = selectedMarkets.toList(),
                                    monthlyBudgetUSD = monthlyBudget,
                                    desiredMarginPct = desiredMargin,
                                    preferredCurrency = preferredCurrency,
                                    supplierPreference = supplierPreference
                                )
                                onComplete(updatedPlan)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = SnapGold, contentColor = Color.Black),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.RocketLaunch, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Activate Plan & Open OS Dashboard", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
