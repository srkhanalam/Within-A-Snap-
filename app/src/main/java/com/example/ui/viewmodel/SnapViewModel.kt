package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.AgentExecutionResult
import com.example.data.ai.SpecializedAgents
import com.example.data.model.*
import com.example.data.repository.StoreRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppScreen {
    LANDING,
    ONBOARDING,
    OVERVIEW,
    COMMAND_CENTER,
    PRODUCT_FINDER,
    LAUNCH_STUDIO,
    SUPPLIERS,
    STORE_BUILDER,
    MARKETING,
    ORDERS,
    SUPPORT,
    ANALYTICS,
    PAYMENTS,
    INTERNATIONAL,
    COMPETITORS_AB,
    AUTOPILOT,
    NATIONAL_BUYER,
    INTERNATIONAL_BUYER,
    BUYER_STOREFRONT,
    ADMIN_LOGIN,
    OWNER_CONSOLE,
    // 7 New Advanced Operations & Growth Suites
    RISK_FRAUD,
    INVOICES_LABELS,
    UPSELL_BUNDLES,
    ROAS_ATTRIBUTION,
    MULTILINGUAL_SUPPORT,
    BROADCAST_CENTER,
    INVENTORY_GUARD
}

data class ChatMessage(
    val id: String,
    val isUser: Boolean,
    val text: String,
    val timestamp: String = "Just now",
    val agentResult: AgentExecutionResult? = null
)

class SnapViewModel(
    private val repository: StoreRepository = StoreRepository()
) : ViewModel() {

    private val _currentScreen = MutableStateFlow(AppScreen.OVERVIEW)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _selectedProductForLaunch = MutableStateFlow<Product?>(null)
    val selectedProductForLaunch: StateFlow<Product?> = _selectedProductForLaunch.asStateFlow()

    private val _selectedBuyerProduct = MutableStateFlow<Product?>(null)
    val selectedBuyerProduct: StateFlow<Product?> = _selectedBuyerProduct.asStateFlow()

    private val _buyerCart = MutableStateFlow<List<Pair<Product, Int>>>(emptyList())
    val buyerCart: StateFlow<List<Pair<Product, Int>>> = _buyerCart.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isGlobalSearchActive = MutableStateFlow(false)
    val isGlobalSearchActive: StateFlow<Boolean> = _isGlobalSearchActive.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    private val _aiChatHistory = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = "msg_init",
                isUser = false,
                text = "Welcome to Within A Snap! I am your AI Virtual E-Commerce Team. You can ask me to research products, evaluate suppliers, optimize pricing, run ad campaigns, or launch winning products across India, UAE, Saudi Arabia, USA, and Europe."
            )
        )
    )
    val aiChatHistory: StateFlow<List<ChatMessage>> = _aiChatHistory.asStateFlow()

    private val _aiCommandInput = MutableStateFlow("")
    val aiCommandInput: StateFlow<String> = _aiCommandInput.asStateFlow()

    // Data from repository
    val products = repository.products
    val suppliers = repository.suppliers
    val orders = repository.orders
    val customers = repository.customers
    val campaigns = repository.campaigns
    val markets = repository.markets
    val aiRecommendations = repository.aiRecommendations
    val auditLogs = repository.auditLogs
    val autopilotConfig = repository.autopilotConfig
    val storeProfile = repository.storeProfile
    val competitors = repository.competitors
    val abTests = repository.abTests
    val supportTickets = repository.supportTickets
    val onboardingPlan = repository.onboardingPlan
    val hasCompletedOnboarding = repository.hasCompletedOnboarding
    val selectedMarketCode = repository.selectedMarketCode

    // Payment System StateFlows
    val paymentGateways = repository.paymentGateways
    val paymentTransactions = repository.paymentTransactions
    val merchantBalances = repository.merchantBalances
    val merchantPayouts = repository.merchantPayouts
    val paymentSmartRules = repository.paymentSmartRules

    // Admin & Owner Security System
    val adminUsers = repository.adminUsers
    val adminSession = repository.adminSession
    val adminAuditTrail = repository.adminAuditTrail
    val systemHealth = repository.systemHealth
    val telemetryMetrics = repository.telemetryMetrics
    val isEmergencyAdFreezeActive = repository.isEmergencyAdFreezeActive
    val isEmergencyAutoFulfillPaused = repository.isEmergencyAutoFulfillPaused

    // 7 New Advanced Operations & Growth StateFlows
    val fraudReports = repository.fraudReports
    val invoices = repository.invoices
    val shippingLabels = repository.shippingLabels
    val packingSlips = repository.packingSlips
    val upsellOffers = repository.upsellOffers
    val smartBundles = repository.smartBundles
    val adSpendAttributions = repository.adSpendAttributions
    val multilingualTickets = repository.multilingualTickets
    val broadcastCampaigns = repository.broadcastCampaigns
    val customerSegments = repository.customerSegments
    val priceSurgeEvents = repository.priceSurgeEvents
    val inventoryGuards = repository.inventoryGuards

    // Dynamic metrics
    val totalRevenueUSD = orders.map { list -> list.filter { it.paymentStatus == PaymentStatus.PAID }.sumOf { it.revenueUSD } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 8420.0)

    val totalProfitUSD = orders.map { list -> list.filter { it.paymentStatus == PaymentStatus.PAID }.sumOf { it.profitUSD } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 3890.0)

    val pendingOrdersCount = orders.map { list -> list.count { it.fulfillmentStatus == FulfillmentStatus.PENDING || it.fulfillmentStatus == FulfillmentStatus.PROCESSING } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 3)

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun setSelectedProductForLaunch(product: Product) {
        _selectedProductForLaunch.value = product
        _currentScreen.value = AppScreen.LAUNCH_STUDIO
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setGlobalSearchActive(active: Boolean) {
        _isGlobalSearchActive.value = active
    }

    fun setAiCommandInput(input: String) {
        _aiCommandInput.value = input
    }

    fun setSelectedMarket(code: String) {
        repository.setSelectedMarket(code)
    }

    fun executeAiCommand(customPrompt: String? = null) {
        val prompt = (customPrompt ?: _aiCommandInput.value).trim()
        if (prompt.isEmpty()) return

        val userMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            isUser = true,
            text = prompt
        )
        _aiChatHistory.update { it + userMsg }
        _aiCommandInput.value = ""
        _isAiThinking.value = true

        viewModelScope.launch {
            try {
                val result = SpecializedAgents.processNaturalLanguageCommand(
                    query = prompt,
                    currentProducts = products.value,
                    currentSuppliers = suppliers.value,
                    currentCampaigns = campaigns.value,
                    currentOrders = orders.value,
                    autopilotConfig = autopilotConfig.value
                )

                // Add to recommendation list in repository
                repository.addRecommendation(result.recommendation)

                val aiResponseMsg = ChatMessage(
                    id = "msg_resp_${System.currentTimeMillis()}",
                    isUser = false,
                    text = result.summary,
                    agentResult = result
                )
                _aiChatHistory.update { it + aiResponseMsg }

                if (result.multiStepWorkflowProduct != null) {
                    _selectedProductForLaunch.value = result.multiStepWorkflowProduct
                }
            } finally {
                _isAiThinking.value = false
            }
        }
    }

    fun approveRecommendation(recId: String) {
        repository.updateRecommendationStatus(recId, RecommendationStatus.APPROVED)
    }

    fun rejectRecommendation(recId: String) {
        repository.updateRecommendationStatus(recId, RecommendationStatus.REJECTED)
    }

    fun launchProduct(productId: String) {
        repository.launchProduct(productId)
    }

    fun updateProduct(product: Product) {
        repository.updateProduct(product)
    }

    fun updateOrderStatus(orderId: String, status: FulfillmentStatus) {
        repository.updateOrderStatus(orderId, status)
    }

    fun fulfillOrderWithSupplier(orderId: String) {
        repository.fulfillOrderWithSupplier(orderId)
    }

    fun approveRiskOrder(orderId: String) {
        repository.approveRiskOrder(orderId)
    }

    fun toggleCampaignStatus(campaignId: String) {
        repository.toggleCampaignStatus(campaignId)
    }

    fun updateCampaignBudget(campaignId: String, newBudget: Double) {
        repository.updateCampaignBudget(campaignId, newBudget)
    }

    fun createCampaign(campaign: Campaign) {
        repository.createCampaign(campaign)
    }

    fun updateAutopilotConfig(config: AutopilotConfig) {
        repository.updateAutopilotConfig(config)
    }

    fun updateStoreProfile(profile: StoreProfile) {
        repository.updateStoreProfile(profile)
    }

    fun resolveTicket(ticketId: String) {
        repository.resolveTicket(ticketId)
    }

    fun escalateTicket(ticketId: String) {
        repository.escalateTicket(ticketId)
    }

    fun setSelectedBuyerProduct(product: Product?) {
        _selectedBuyerProduct.value = product
    }

    fun addToBuyerCart(product: Product, quantity: Int = 1) {
        _buyerCart.update { list ->
            val existing = list.find { it.first.id == product.id }
            if (existing != null) {
                list.map { if (it.first.id == product.id) Pair(it.first, it.second + quantity) else it }
            } else {
                list + Pair(product, quantity)
            }
        }
    }

    fun removeFromBuyerCart(productId: String) {
        _buyerCart.update { list -> list.filterNot { it.first.id == productId } }
    }

    fun clearBuyerCart() {
        _buyerCart.value = emptyList()
    }

    fun placeBuyerOrder(order: Order) {
        repository.createOrder(order)
    }

    // Payment System Actions
    fun togglePaymentGateway(gatewayId: String, isEnabled: Boolean) {
        repository.togglePaymentGateway(gatewayId, isEnabled)
    }

    fun toggleGatewayLiveMode(gatewayId: String, isLive: Boolean) {
        repository.toggleGatewayLiveMode(gatewayId, isLive)
    }

    fun updateGatewayCredentials(gatewayId: String, apiKey: String, merchantId: String) {
        repository.updateGatewayCredentials(gatewayId, apiKey, merchantId)
    }

    fun refundPaymentTransaction(txId: String, reason: String = "Customer request") {
        repository.refundTransaction(txId, reason)
    }

    fun requestPayout(amount: Double, currency: String, destination: String, bankName: String) {
        repository.requestMerchantPayout(amount, currency, destination, bankName)
    }

    fun togglePaymentSmartRule(ruleId: String, isEnabled: Boolean) {
        repository.toggleSmartRule(ruleId, isEnabled)
    }

    fun simulateTestPayment(
        gatewayId: String,
        amount: Double,
        currency: String,
        customerName: String,
        method: String,
        outcome: PaymentTransactionStatus
    ) {
        repository.simulateTestPayment(gatewayId, amount, currency, customerName, method, outcome)
    }

    fun toggleSupplierIntegration(supplierId: String, isIntegrated: Boolean) {
        repository.toggleSupplierIntegration(supplierId, isIntegrated)
    }

    // Admin & Owner Management Functions
    fun loginAdmin(identifier: String, secretPin: String, role: AdminRole? = null): Boolean {
        val success = repository.loginAdmin(identifier, secretPin, role)
        if (success) {
            _currentScreen.value = AppScreen.OWNER_CONSOLE
        }
        return success
    }

    fun logoutAdmin() {
        repository.logoutAdmin()
        _currentScreen.value = AppScreen.ADMIN_LOGIN
    }

    fun lockAdminConsole() {
        repository.lockAdminConsole()
    }

    fun unlockAdminConsole(pin: String): Boolean {
        return repository.unlockAdminConsole(pin)
    }

    fun toggleEmergencyAdFreeze(freeze: Boolean) {
        repository.toggleEmergencyAdFreeze(freeze)
    }

    fun toggleEmergencyAutoFulfill(pause: Boolean) {
        repository.toggleEmergencyAutoFulfill(pause)
    }

    fun flushSystemCache() {
        repository.flushSystemCache()
    }

    // 7 New Advanced Operations & Growth Actions
    fun approveFraudReport(orderId: String) {
        repository.approveFraudReport(orderId)
    }

    fun blockFraudReport(orderId: String) {
        repository.blockFraudReport(orderId)
    }

    fun sanitizeOrderAddress(orderId: String, newAddress: String) {
        repository.sanitizeOrderAddress(orderId, newAddress)
    }

    fun toggleUpsellOffer(offerId: String, enabled: Boolean) {
        repository.toggleUpsellOffer(offerId, enabled)
    }

    fun resolveMultilingualTicket(ticketId: String) {
        repository.resolveMultilingualTicket(ticketId)
    }

    fun applySmartMacro(ticketId: String, macroName: String) {
        repository.applySmartMacro(ticketId, macroName)
    }

    fun dispatchBroadcastCampaign(title: String, channel: BroadcastChannel, segment: String, promoCode: String, discountPct: Int, template: String) {
        repository.dispatchBroadcastCampaign(title, channel, segment, promoCode, discountPct, template)
    }

    fun toggleInventoryAutoReroute(productId: String, active: Boolean) {
        repository.toggleInventoryAutoReroute(productId, active)
    }

    fun completeOnboarding(plan: OnboardingPlan) {
        repository.updateOnboardingPlan(plan)
        repository.setOnboardingCompleted(true)
        _currentScreen.value = AppScreen.OVERVIEW
    }
}
