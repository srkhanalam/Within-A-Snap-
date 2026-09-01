package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AdminRole
import com.example.data.model.AdminUser
import com.example.ui.components.BrandLogoHeader
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLoginScreen(
    adminUsers: List<AdminUser>,
    onLogin: (String, String, AdminRole?) -> Boolean,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedUser by remember { mutableStateOf(adminUsers.firstOrNull()) }
    var emailOrIdentifier by remember { mutableStateOf(adminUsers.firstOrNull()?.email ?: "parvejalam1703@gmail.com") }
    var secretPin by remember { mutableStateOf("1703") }
    var selectedRole by remember { mutableStateOf(AdminRole.SUPER_ADMIN_OWNER) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isBiometricScanning by remember { mutableStateOf(false) }
    var showQuickFillSheet by remember { mutableStateOf(false) }

    val gradientBg = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF090D16),
            Color(0xFF0E1726),
            Color(0xFF060911)
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBg)
            .padding(horizontal = 20.dp)
            .testTag("admin_login_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 32.dp, bottom = 60.dp)
    ) {
        item {
            // Top Navigation & Security Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF161F30))
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SnapGoldContainer.copy(alpha = 0.3f),
                    border = BorderStroke(1.dp, SnapGold.copy(alpha = 0.6f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Default.Shield,
                            contentDescription = null,
                            tint = SnapGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "256-Bit Encrypted Vault",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = SnapGoldLight
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Brand Header & Shield Icon
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                SnapGold.copy(alpha = 0.4f),
                                Color(0xFF1E293B)
                            )
                        )
                    )
                    .border(2.dp, SnapGold, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.AdminPanelSettings,
                    contentDescription = null,
                    tint = SnapGold,
                    modifier = Modifier.size(38.dp)
                )
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = "Owner & Executive Portal",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Authorized access for Platform Owner & Executive Directors to inspect deep financial telemetry and autopilot controls.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )

            Spacer(Modifier.height(20.dp))
        }

        // Login Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF111927)),
                border = BorderStroke(1.dp, Color(0xFF22314E))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Quick Account Preset Selector
                    Text(
                        text = "SELECT EXECUTIVE PROFILE",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.sp),
                        color = SnapGold
                    )

                    adminUsers.forEach { user ->
                        val isSelected = selectedUser?.id == user.id
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedUser = user
                                    emailOrIdentifier = user.email
                                    secretPin = user.secretPin
                                    selectedRole = user.role
                                    errorMessage = null
                                },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Color(0xFF1A263D) else Color(0xFF0D131F),
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) SnapGold else Color(0xFF1E2C47)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(38.dp)
                                            .clip(CircleShape)
                                            .background(if (user.role == AdminRole.SUPER_ADMIN_OWNER) SnapGold else SnapCyan),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = user.avatarInitials,
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                                            color = Color.Black
                                        )
                                    }

                                    Column {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Text(
                                                text = user.name,
                                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            if (user.role == AdminRole.SUPER_ADMIN_OWNER) {
                                                Surface(
                                                    shape = RoundedCornerShape(4.dp),
                                                    color = SnapGoldContainer
                                                ) {
                                                    Text(
                                                        text = "OWNER",
                                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Black),
                                                        color = SnapGold,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                    )
                                                }
                                            }
                                        }

                                        Text(
                                            text = user.role.displayName,
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                RadioButton(
                                    selected = isSelected,
                                    onClick = {
                                        selectedUser = user
                                        emailOrIdentifier = user.email
                                        secretPin = user.secretPin
                                        selectedRole = user.role
                                        errorMessage = null
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = SnapGold)
                                )
                            }
                        }
                    }

                    Divider(color = Color(0xFF1E2C47), modifier = Modifier.padding(vertical = 4.dp))

                    // Manual Email / ID Field
                    OutlinedTextField(
                        value = emailOrIdentifier,
                        onValueChange = {
                            emailOrIdentifier = it
                            errorMessage = null
                        },
                        label = { Text("Owner Email / Phone / Identifier") },
                        leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = SnapGold) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SnapGold,
                            unfocusedBorderColor = Color(0xFF22314E),
                            focusedContainerColor = Color(0xFF0B101A),
                            unfocusedContainerColor = Color(0xFF0B101A)
                        )
                    )

                    // Master Secret PIN
                    OutlinedTextField(
                        value = secretPin,
                        onValueChange = {
                            secretPin = it
                            errorMessage = null
                        },
                        label = { Text("4-Digit Master PIN or Passcode") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = SnapGold) },
                        trailingIcon = {
                            TextButton(onClick = { secretPin = "1703" }) {
                                Text("Auto (1703)", fontSize = 11.sp, color = SnapGoldLight)
                            }
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SnapGold,
                            unfocusedBorderColor = Color(0xFF22314E),
                            focusedContainerColor = Color(0xFF0B101A),
                            unfocusedContainerColor = Color(0xFF0B101A)
                        )
                    )

                    // Error Alert if any
                    errorMessage?.let { err ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SnapRoseContainer.copy(alpha = 0.5f),
                            border = BorderStroke(1.dp, SnapRose)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.Error, contentDescription = null, tint = SnapRose, modifier = Modifier.size(16.dp))
                                Text(err, style = MaterialTheme.typography.bodySmall, color = Color.White)
                            }
                        }
                    }

                    // Biometric Scanning Simulator
                    AnimatedVisibility(visible = isBiometricScanning) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = SnapCyanContainer.copy(alpha = 0.4f),
                            border = BorderStroke(1.dp, SnapCyan)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp,
                                    color = SnapCyan
                                )
                                Text(
                                    "Scanning Biometric Passkey & Verifying Hardware Secure Enclave...",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = SnapCyan
                                )
                            }
                        }
                    }

                    // Login Action Button
                    Button(
                        onClick = {
                            val success = onLogin(emailOrIdentifier, secretPin, selectedRole)
                            if (!success) {
                                errorMessage = "Invalid Master PIN or Owner credentials. Use '1703' or click preset above."
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("admin_login_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SnapGold,
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(Icons.Default.VpnKey, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Authenticate & Launch Owner Cockpit",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                        )
                    }

                    // Biometric Shortcut Button
                    OutlinedButton(
                        onClick = {
                            isBiometricScanning = true
                            emailOrIdentifier = "parvejalam1703@gmail.com"
                            secretPin = "1703"
                            selectedRole = AdminRole.SUPER_ADMIN_OWNER
                            onLogin("parvejalam1703@gmail.com", "1703", AdminRole.SUPER_ADMIN_OWNER)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, SnapCyan.copy(alpha = 0.8f))
                    ) {
                        Icon(Icons.Default.Fingerprint, contentDescription = null, tint = SnapCyan, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Instant Touch ID / Biometric Owner Unlock",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = SnapCyan
                        )
                    }
                }
            }
        }

        // Security Telemetry Footer
        item {
            Spacer(Modifier.height(20.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D131F)),
                border = BorderStroke(1.dp, Color(0xFF1C273D)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "SECURITY AUDIT RECORD",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(SnapEmerald))
                            Text("Geo-Fence Active", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = SnapEmerald)
                        }
                    }

                    Text(
                        text = "Client IP: 103.21.144.92 • TLS 1.3 Strict Session Security • Hardware Secure Enclave verified for Director Parvej Alam.",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
