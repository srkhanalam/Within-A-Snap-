package com.example.data.model

// --- Market Definition ---
data class MarketInfo(
    val code: String,          // IN, AE, SA, US, GB, EU
    val name: String,
    val flag: String,
    val currencyCode: String,
    val currencySymbol: String,
    val exchangeRateToUSD: Double, // 1 USD in this currency
    val standardTaxPct: Double,
    val avgDutyPct: Double,
    val deliveryDaysAvg: Int,
    val marketOpportunityScore: Int,
    val isEnabled: Boolean = true
)

// --- Supplier ---
data class Supplier(
    val id: String,
    val name: String,
    val rating: Double,
    val productCostINR: Double,
    val shippingCostINR: Double,
    val deliveryDays: Int,
    val moq: Int,
    val returnPolicy: String,
    val reliabilityScore: Int, // 0-100
    val country: String,
    val isVerified: Boolean = true,
    val notes: String = "",
    val logoEmoji: String = "📦",
    val primaryMarket: String = "GLOBAL", // IN, AE, SA, US, GB, EU, GLOBAL
    val supportedMarkets: List<String> = listOf("GLOBAL"),
    val specialty: String = "Trending Consumer Products",
    val warehouses: String = "Global Fulfillment Network",
    val shippingCarriers: List<String> = listOf("Express Air Charter"),
    val avgProfitMarginPct: Double = 48.0,
    val codSupported: Boolean = false,
    val customBranding: Boolean = true,
    val integrationType: String = "Direct 1-Click API Sync",
    val orderSyncSpeed: String = "Instant Automated ERP",
    val totalOrdersFulfilled: String = "50,000+",
    val contactSupportSla: String = "< 2 Hours 24/7",
    val isIntegrated: Boolean = true
)

// --- Product ---
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val niche: String,
    val imageUrl: String = "",
    val demandLevel: String,     // High, Very High, Medium, Trending
    val trendScore: Int,         // 0-100
    val competitionLevel: String,// Low, Medium, High
    val supplierCostINR: Double,
    val shippingCostINR: Double,
    val platformFeeINR: Double = 150.0,
    val adCostEstimateINR: Double = 350.0,
    val targetMarginPct: Double = 42.0,
    val opportunityScore: Int,   // 0-100
    val riskScore: Int,          // 0-100
    val deliveryDays: Int,
    val recommendedMarkets: List<String>, // IN, AE, SA, US, GB, EU
    val isLaunched: Boolean = false,
    val isWinnerProduct: Boolean = false,
    
    // AI Launch Kit Components
    val description: String = "",
    val benefits: List<String> = emptyList(),
    val bulletPoints: List<String> = emptyList(),
    val specifications: Map<String, String> = emptyMap(),
    val seoTitle: String = "",
    val seoDescription: String = "",
    val keywords: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val faqs: List<Pair<String, String>> = emptyList(),
    val sku: String = "",
    val adHeadline: String = "",
    val adPrimaryText: String = "",
    val adCta: String = "Shop Now — 50% Off Today",
    val socialCaptions: List<String> = emptyList(),
    val emailSubject: String = "",
    val emailBody: String = "",
    val videoScriptHook: String = "",
    val videoScriptUgc: String = "",
    val suppliers: List<Supplier> = emptyList()
) {
    val totalCostINR: Double get() = supplierCostINR + shippingCostINR + platformFeeINR + adCostEstimateINR
    val suggestedSellingPriceINR: Double get() = (totalCostINR / (1.0 - (targetMarginPct / 100.0))).coerceAtLeast(totalCostINR * 1.2)
    val estimatedProfitINR: Double get() = suggestedSellingPriceINR - totalCostINR
    val calculatedMarginPct: Double get() = if (suggestedSellingPriceINR > 0) (estimatedProfitINR / suggestedSellingPriceINR) * 100.0 else 0.0

    // Localized Selling Price calculation for different international markets
    fun getMarketSellingPrice(marketCode: String): Double {
        return when (marketCode) {
            "IN" -> kotlin.math.round(suggestedSellingPriceINR / 10.0) * 10.0 - 1.0 // e.g. ₹1,799
            "AE" -> kotlin.math.round((suggestedSellingPriceINR / 83.0 * 3.67) / 5.0) * 5.0 - 1.0 // e.g. AED 99
            "SA" -> kotlin.math.round((suggestedSellingPriceINR / 83.0 * 3.75) / 5.0) * 5.0 - 1.0 // e.g. SAR 109
            "US" -> kotlin.math.round((suggestedSellingPriceINR / 83.0) + 4.99) // e.g. $34.99
            "GB" -> kotlin.math.round((suggestedSellingPriceINR / 83.0 * 0.79) + 4.99) // e.g. £29.99
            "EU" -> kotlin.math.round((suggestedSellingPriceINR / 83.0 * 0.92) + 4.99) // e.g. €32.99
            else -> suggestedSellingPriceINR / 83.0
        }
    }

    fun getFormattedMarketPrice(marketCode: String): String {
        val price = getMarketSellingPrice(marketCode)
        return when (marketCode) {
            "IN" -> "₹${price.toInt()}"
            "AE" -> "AED ${price.toInt()}"
            "SA" -> "SAR ${price.toInt()}"
            "US" -> "$${String.format("%.2f", price)}"
            "GB" -> "£${String.format("%.2f", price)}"
            "EU" -> "€${String.format("%.2f", price)}"
            else -> "$${String.format("%.2f", price)}"
        }
    }
}

// --- Order ---
enum class PaymentStatus { PENDING, PAID, REFUNDED, FAILED }
enum class FulfillmentStatus { PENDING, PROCESSING, FULFILLED, SHIPPED, DELIVERED, CANCELLED }

data class Order(
    val id: String,
    val customerName: String,
    val customerEmail: String,
    val customerCity: String,
    val marketCode: String,
    val productName: String,
    val quantity: Int,
    val revenueUSD: Double,
    val costUSD: Double,
    val profitUSD: Double,
    val paymentStatus: PaymentStatus,
    val fulfillmentStatus: FulfillmentStatus,
    val shippingStatus: String,
    val trackingNumber: String,
    val date: String,
    val riskScore: Int, // 0-100
    val riskFactors: List<String> = emptyList(),
    val isSupplierPaid: Boolean = true,
    val supplierName: String = "ApexGlobal Direct",
    val paymentMethod: String = "Digital Gateway"
)

// --- Customer ---
enum class CustomerSegment { VIP, REPEAT_BUYER, AT_RISK, HIGH_VALUE, NEW }

data class Customer(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val country: String,
    val ordersCount: Int,
    val totalSpentUSD: Double,
    val ltvUSD: Double,
    val purchaseFrequencyDays: Int,
    val segment: CustomerSegment,
    val lastOrderDate: String,
    val suggestedUpsell: String
)

// --- Marketing Campaign ---
enum class MarketingChannel { META, GOOGLE, TIKTOK, EMAIL, SOCIAL }
enum class CampaignStatus { ACTIVE, PAUSED, OPTIMIZING, DRAFT }

data class Campaign(
    val id: String,
    val name: String,
    val channel: MarketingChannel,
    val status: CampaignStatus,
    val objective: String,
    val targetAudience: String,
    val dailyBudgetUSD: Double,
    val spendUSD: Double,
    val impressions: Int,
    val clicks: Int,
    val orders: Int,
    val revenueUSD: Double,
    val profitUSD: Double,
    val headline: String,
    val primaryText: String,
    val ctaText: String = "Shop Now",
    val creativeConcept: String = "",
    val aiOptimizationNotes: String = ""
) {
    val ctrPct: Double get() = if (impressions > 0) (clicks.toDouble() / impressions) * 100.0 else 0.0
    val cpcUSD: Double get() = if (clicks > 0) spendUSD / clicks else 0.0
    val roas: Double get() = if (spendUSD > 0) revenueUSD / spendUSD else 0.0
    val conversionRatePct: Double get() = if (clicks > 0) (orders.toDouble() / clicks) * 100.0 else 0.0
}

// --- AI Agent System ---
enum class AgentType {
    RESEARCH, SOURCING, CONTENT, PRICING, MARKETING, OPERATIONS, SUPPORT, FINANCE, RISK, ORCHESTRATOR
}

enum class RecommendationStatus { PENDING, APPROVED, REJECTED, EXECUTED }

data class AIRecommendation(
    val id: String,
    val agentType: AgentType,
    val title: String,
    val recommendation: String,
    val reason: String,
    val confidenceScore: Int, // 0-100
    val expectedImpact: String,
    val suggestedAction: String,
    val status: RecommendationStatus = RecommendationStatus.PENDING,
    val timestamp: String = "Just now",
    val targetEntityId: String? = null
)

// --- Autopilot System ---
enum class AutomationMode { ASSIST, SEMI_AUTO, AUTOPILOT }

data class AutopilotConfig(
    val mode: AutomationMode = AutomationMode.SEMI_AUTO,
    val maxDailyAdSpendUSD: Double = 500.0,
    val maxPriceChangePct: Double = 15.0,
    val minProfitMarginPct: Double = 30.0,
    val maxRefundAmountUSD: Double = 100.0,
    val approvedCountries: List<String> = listOf("IN", "AE", "SA", "US", "GB", "EU"),
    val approvedSuppliers: List<String> = listOf("Apex Direct", "Shenzhen SpeedCraft", "AuraDrop Global"),
    val requireHumanApprovalForPricing: Boolean = false,
    val requireHumanApprovalForAdSpend: Boolean = true,
    val requireHumanApprovalForSuppliers: Boolean = true,
    val isProductMonitoringActive: Boolean = true,
    val isPriceMonitoringActive: Boolean = true,
    val isOrderMonitoringActive: Boolean = true,
    val isCampaignMonitoringActive: Boolean = true,
    val isCustomerSupportActive: Boolean = true,
    val isOpportunityDetectionActive: Boolean = true
)

data class AuditLog(
    val id: String,
    val timestamp: String,
    val agentName: String,
    val action: String,
    val reason: String,
    val result: String,
    val requiresHumanApproval: Boolean = false,
    val isApproved: Boolean = true
)

// --- Store Builder ---
data class StoreProfile(
    val id: String = "store_01",
    val name: String = "Within A Snap Official",
    val tagline: String = "Find. Launch. Sell. Within A Snap.",
    val ownerName: String = "Parvej Alam",
    val ownerEmail: String = "parvejalam1703@gmail.com",
    val ownerPhone: String = "+919305868395",
    val niche: String = "High-Growth Lifestyle & Fitness",
    val targetMarket: String = "UAE, Saudi Arabia, India & Global",
    val primaryColorHex: String = "#E5A93C",
    val fontChoice: String = "Modern Geometric Sans",
    val customDomain: String = "within-a-snap.store",
    val isPublished: Boolean = true,
    val collections: List<String> = listOf("Trending Winners", "New Releases", "Best Sellers", "Staff Picks"),
    val pages: List<String> = listOf("Home", "Products", "About Us", "Track Order", "FAQ", "Contact"),
    val policies: Map<String, String> = mapOf(
        "Shipping" to "Fast 4-7 business days express courier with tracking.",
        "Returns" to "30-day money-back guarantee with zero hassle.",
        "Privacy" to "Bank-level encryption with strict GDPR & PDPL compliance."
    )
)

// --- Competitor Intelligence ---
data class CompetitorItem(
    val id: String,
    val name: String,
    val publicPriceUSD: Double,
    val category: String,
    val marketPositioning: String,
    val marketingThemes: List<String>,
    val storeStrengths: String,
    val seoOpportunities: String
)

// --- A/B Testing ---
data class ABTestItem(
    val id: String,
    val testName: String,
    val elementTested: String, // Title, Pricing, Ad Creative, Hero CTA
    val variantA: String,
    val variantB: String,
    val trafficSplitPct: Int = 50,
    val variantACtr: Double,
    val variantBCtr: Double,
    val variantAConversion: Double,
    val variantBConversion: Double,
    val variantARevenueUSD: Double,
    val variantBRevenueUSD: Double,
    val winningVariant: String,
    val confidencePct: Int,
    val aiRecommendation: String
)

// --- Customer Support Ticket ---
data class SupportTicket(
    val id: String,
    val customerName: String,
    val customerEmail: String,
    val orderId: String,
    val query: String,
    val aiDraftResponse: String,
    val isEscalated: Boolean = false,
    val isResolved: Boolean = false,
    val timestamp: String = "10m ago"
)

// --- Onboarding User Plan ---
data class OnboardingPlan(
    val merchantName: String = "Parvej Alam",
    val businessName: String = "Within A Snap Global",
    val niche: String = "Smart Fitness & Wellness",
    val targetMarkets: List<String> = listOf("AE", "SA", "IN", "US"),
    val monthlyBudgetUSD: Double = 2500.0,
    val desiredMarginPct: Double = 40.0,
    val preferredCurrency: String = "USD",
    val sellingChannels: List<String> = listOf("Direct Storefront", "Meta Ads", "TikTok Shop"),
    val supplierPreference: String = "Fast Express (< 7 days)",
    val generatedActionSteps: List<String> = listOf(
        "Launch #1 Hero Product: Smart Ergonomic Posture Corrector (Targeting UAE & Saudi Arabia)",
        "Set initial pricing at AED 119 / SAR 129 to lock in 46% net profit margin",
        "Deploy automated Meta & TikTok video ad campaigns with 3 hook variations",
        "Activate semi-autopilot fulfillment through Shenzhen SpeedCraft & Apex Direct",
        "Connect AI Customer Support Desk for automated tracking lookups"
    )
)

// ==========================================
// --- PAYMENT SYSTEM & SETTLEMENT MODELS ---
// ==========================================

enum class PaymentTransactionStatus {
    SUCCESS, PENDING, FAILED, REFUNDED, DISPUTED
}

data class PaymentGateway(
    val id: String,
    val name: String,
    val provider: String,
    val category: String, // "Global Cards & Wallets", "India UPI & NetBanking", "BNPL Installments", "COD Engine"
    val supportedCurrencies: List<String>,
    val supportedCountries: List<String>,
    val isEnabled: Boolean,
    val isLiveMode: Boolean,
    val apiKey: String,
    val merchantId: String,
    val webhookUrl: String,
    val feePct: Double,
    val flatFeeUSD: Double,
    val successRatePct: Double,
    val settlementPeriod: String,
    val description: String,
    val features: List<String>
)

data class PaymentTransaction(
    val id: String,
    val orderId: String,
    val customerName: String,
    val customerEmail: String,
    val amount: Double,
    val currency: String,
    val amountUSD: Double,
    val gatewayId: String,
    val gatewayName: String,
    val method: String,
    val status: PaymentTransactionStatus,
    val riskScore: Int, // 0-100
    val riskLevel: String, // "LOW", "ELEVATED", "HIGH"
    val countryCode: String,
    val createdAt: String,
    val receiptUrl: String = "https://receipts.within-a-snap.com/tx_",
    val failureReason: String? = null
)

data class MerchantBalance(
    val currency: String,
    val symbol: String,
    val availableAmount: Double,
    val pendingSettlement: Double,
    val reservedRiskBuffer: Double,
    val flag: String
)

data class MerchantPayout(
    val id: String,
    val destinationAccount: String,
    val bankName: String,
    val currency: String,
    val amount: Double,
    val amountUSD: Double,
    val status: String, // "COMPLETED", "PROCESSING", "SCHEDULED"
    val initiatedAt: String,
    val completedAt: String? = null,
    val referenceNumber: String
)

data class PaymentSmartRule(
    val id: String,
    val title: String,
    val condition: String,
    val action: String,
    val isEnabled: Boolean,
    val savingsEstimate: String
)

// ==========================================
// --- OWNER & ADMIN SECURITY MODELS ---
// ==========================================

enum class AdminRole(val displayName: String, val level: Int, val badgeColorHex: String) {
    SUPER_ADMIN_OWNER("Platform Owner / Super Admin", 1, "#E5A93C"),
    OPERATIONS_DIRECTOR("Operations & Logistics Director", 2, "#00E5FF"),
    FINANCIAL_CONTROLLER("Treasury & Finance Lead", 2, "#10B981"),
    AI_SYSTEMS_ENGINEER("AI Fleet & Automations Engineer", 3, "#A855F7"),
    SUPPORT_SUPERVISOR("Customer Support Lead", 4, "#F43F5E")
}

data class AdminUser(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: AdminRole,
    val avatarInitials: String = "PA",
    val secretPin: String = "1703",
    val masterPasswordHash: String = "snap_owner_root_2026",
    val lastLoginTimestamp: String = "Just now",
    val ipAddress: String = "103.21.144.92 (New Delhi, IN)",
    val sessionToken: String = "OWNER-ROOT-TOKEN-9921",
    val isTwoFactorEnabled: Boolean = true,
    val permissions: List<String> = listOf(
        "ALL_STORE_METRICS",
        "BANK_PAYOUT_RELEASE",
        "SUPPLIER_CONTRACT_OVERRIDE",
        "AUTOPILOT_KILL_SWITCH",
        "API_GATEWAY_CREDENTIALS",
        "FRAUD_SHIELD_CONTROLS",
        "CUSTOMER_DATA_EXPORT",
        "DATABASE_BACKUP_RESTORE"
    )
)

data class AdminSession(
    val isAuthenticated: Boolean = false,
    val currentUser: AdminUser? = null,
    val isConsoleLocked: Boolean = false,
    val sessionExpiresInMinutes: Int = 120,
    val unreadSecurityAlertsCount: Int = 0
)

data class SystemTelemetryMetric(
    val serverUptimePct: Double = 99.98,
    val activeWebhooksPerMinute: Int = 342,
    val totalGmvUSD: Double = 48750.0,
    val totalNetProfitUSD: Double = 23940.0,
    val supplierCogsPayableUSD: Double = 14200.0,
    val cashInBankUSD: Double = 34550.0,
    val pendingCourierDispatches: Int = 18,
    val fraudRiskBlockRatePct: Double = 4.2,
    val aiAgentsActiveCount: Int = 6,
    val totalOrdersAllTime: Int = 1248
)

data class AdminAuditEntry(
    val id: String,
    val timestamp: String,
    val actor: String,
    val action: String,
    val category: String, // "AUTH", "FINANCE", "SUPPLIER", "AUTOPILOT", "SECURITY"
    val ipAddress: String,
    val severity: String = "INFO", // "INFO", "WARNING", "CRITICAL"
    val details: String
)

data class SystemHealthService(
    val name: String,
    val category: String,
    val status: String, // "HEALTHY", "DEGRADED", "OFFLINE"
    val latencyMs: Int,
    val endpoint: String,
    val lastPing: String = "2s ago"
)

// ===================================================
// --- 7 MISSION CRITICAL GROWTH & OPERATIONS SUITE ---
// ===================================================

// 1. Anti-Fraud & Real-Time Risk Engine
enum class RiskLevel { LOW, MEDIUM, CRITICAL }

data class FraudCheckReport(
    val orderId: String,
    val customerName: String,
    val email: String,
    val phone: String,
    val ipAddress: String,
    val riskScore: Int, // 0-100
    val riskLevel: RiskLevel,
    val isDisposableEmail: Boolean,
    val isPhoneValid: Boolean,
    val velocityOrdersLast24h: Int,
    val addressSanitized: Boolean,
    val originalAddress: String,
    val sanitizedAddress: String,
    val rtoProbabilityPct: Double,
    val recommendedAction: String, // "AUTO_APPROVE", "REQUIRE_SMS_OTP", "MANUAL_HOLD", "AUTO_BLOCK"
    val reasons: List<String>
)

// 2. Instant PDF Invoice, Thermal Shipping Labels & Packing Slips
data class InvoiceLineItem(
    val title: String,
    val sku: String,
    val hsnCode: String,
    val quantity: Int,
    val unitPrice: Double,
    val taxPct: Double,
    val total: Double
)

data class StoreInvoice(
    val invoiceNumber: String,
    val orderId: String,
    val invoiceDate: String,
    val customerName: String,
    val customerEmail: String,
    val billingAddress: String,
    val shippingAddress: String,
    val marketCode: String,
    val currency: String,
    val items: List<InvoiceLineItem>,
    val subtotal: Double,
    val taxAmount: Double, // CGST/SGST/IGST or VAT
    val shippingFee: Double,
    val grandTotal: Double,
    val taxIdNumber: String = "GSTIN: 07AAAAA0000A1Z5 / VAT: 300000000000003",
    val paymentMethod: String = "UPI / Pre-paid Express",
    val status: String = "PAID"
)

data class ThermalShippingLabel(
    val orderId: String,
    val trackingNumber: String,
    val carrierName: String, // "Delhivery Air", "BlueDart Prime", "SMSA Express", "USPS Priority", "Royal Mail Tracked 24"
    val routingCode: String,
    val hubCode: String,
    val weightKg: Double,
    val packageType: String = "Standard Flyer",
    val senderName: String = "Within A Snap Logistics Hub",
    val senderAddress: String = "Logistics Center, Sector 62, Noida / Dubai CommerCity Hub",
    val recipientName: String,
    val recipientPhone: String,
    val recipientAddress: String,
    val recipientCity: String,
    val recipientPincode: String,
    val paymentMode: String, // "PREPAID" or "COD: ₹1,799"
    val barcodeString: String = "SNAP-DEL-992014-X"
)

data class WarehousePackingSlip(
    val slipNumber: String,
    val orderId: String,
    val date: String,
    val warehouseLocation: String,
    val pickerName: String,
    val items: List<Pair<String, Int>>, // Item name to qty
    val qualityCheckStatus: String = "PASSED",
    val packingBoxSize: String = "Box M-02 (Biodegradable)"
)

// 3. Post-Purchase 1-Click Upsell & Dynamic Bundles
enum class UpsellType { POST_PURCHASE_1CLICK, BUNDLE_DISCOUNT, EXTENDED_WARRANTY, PRIORITY_DISPATCH }

data class UpsellOffer(
    val id: String,
    val type: UpsellType,
    val title: String,
    val subtitle: String,
    val originalPriceUSD: Double,
    val discountedPriceUSD: Double,
    val discountPct: Int,
    val badgeText: String,
    val timerSeconds: Int = 180,
    val conversionRatePct: Double = 18.4,
    val totalRevenueGeneratedUSD: Double = 3420.0,
    val isEnabled: Boolean = true
)

data class SmartBundle(
    val id: String,
    val title: String,
    val targetNiche: String,
    val mainProductName: String,
    val includedItems: List<String>,
    val originalPriceUSD: Double,
    val bundlePriceUSD: Double,
    val savingsUSD: Double,
    val tag: String = "🔥 MOST POPULAR (Save 35%)",
    val isAutoSuggested: Boolean = true
)

// 4. True Net Profit & Ad Spend Sync (ROAS Attribution)
data class AdSpendAttribution(
    val id: String,
    val campaignName: String,
    val platform: String, // "Meta Ads", "TikTok Ads", "Google Shopping"
    val adSpendUSD: Double,
    val grossRevenueUSD: Double,
    val cogsUSD: Double,
    val gatewayFeesUSD: Double,
    val shippingFeesUSD: Double,
    val trueNetProfitUSD: Double,
    val actualRoas: Double,
    val breakevenRoas: Double,
    val roasMultiplierScore: Double,
    val status: String // "HIGHLY_PROFITABLE", "OPTIMAL", "BREAKEVEN_RISK", "UNPROFITABLE"
)

// 5. Multi-Language Customer Support Extension
data class MultilingualTicket(
    val id: String,
    val customerName: String,
    val customerEmail: String,
    val orderId: String,
    val originalLanguage: String, // "Arabic (العربية)", "Hindi (हिंदी)", "Spanish (Español)", "German (Deutsch)", "French (Français)"
    val languageCode: String,
    val originalQuery: String,
    val translatedToEnglish: String,
    val aiSuggestedReplyEnglish: String,
    val translatedReplyNative: String,
    val smartMacroApplied: String, // "TRACKING_INSTANT_LOOKUP", "RETURN_SHIPPING_POLICY", "PINCODE_ADDRESS_FIX", "COD_DISCOUNT_CONVERT"
    val isResolved: Boolean = false,
    val sentiment: String = "Neutral"
)

// 6. SMS / Email / WhatsApp VIP Broadcast Center
enum class BroadcastChannel { WHATSAPP, SMS, EMAIL }

data class BroadcastCampaignItem(
    val id: String,
    val title: String,
    val channel: BroadcastChannel,
    val audienceSegment: String, // "VIP High-Spenders (LTV > $200)", "Repeat Buyers (2+ Orders)", "At-Risk (Inactive > 45d)", "COD Abandoned Checkouts", "All Customers"
    val recipientCount: Int,
    val messageTemplate: String,
    val discountPromoCode: String,
    val discountPct: Int,
    val sentTimestamp: String,
    val deliveredCount: Int,
    val openRatePct: Double,
    val clickRatePct: Double,
    val recoveredRevenueUSD: Double,
    val status: String // "SENT", "SCHEDULED", "DRAFT"
)

data class CustomerSegmentMetric(
    val name: String,
    val count: Int,
    val avgLtvUSD: Double,
    val recommendedPromo: String,
    val iconEmoji: String
)

// 7. Automated Supplier Inventory & Price Change Guard
enum class PriceSurgeAction { AUTO_REPRICED_MARGIN_LOCKED, DELISTED_OUT_OF_STOCK, REROUTED_BACKUP_SUPPLIER }

data class SupplierPriceGuardEvent(
    val id: String,
    val productName: String,
    val supplierName: String,
    val oldSupplierCostINR: Double,
    val newSupplierCostINR: Double,
    val surgePct: Double,
    val oldRetailPriceINR: Double,
    val autoAdjustedRetailPriceINR: Double,
    val protectedMarginPct: Double,
    val action: PriceSurgeAction,
    val timestamp: String = "15m ago"
)

data class WarehouseInventoryGuard(
    val productId: String,
    val productName: String,
    val primaryWarehouse: String,
    val primaryStockUnits: Int,
    val secondaryWarehouse: String,
    val secondaryStockUnits: Int,
    val isAutoRerouteActive: Boolean = true,
    val status: String // "IN_STOCK", "LOW_STOCK_WARNING", "OUT_OF_STOCK_REROUTED"
)


