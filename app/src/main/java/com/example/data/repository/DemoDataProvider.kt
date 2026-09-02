package com.example.data.repository

import com.example.data.model.*

object DemoDataProvider {

    val supportedMarkets = listOf(
        MarketInfo(
            code = "IN",
            name = "India",
            flag = "🇮🇳",
            currencyCode = "INR",
            currencySymbol = "₹",
            exchangeRateToUSD = 83.5,
            standardTaxPct = 18.0,
            avgDutyPct = 10.0,
            deliveryDaysAvg = 4,
            marketOpportunityScore = 94,
            isEnabled = true
        ),
        MarketInfo(
            code = "AE",
            name = "United Arab Emirates",
            flag = "🇦🇪",
            currencyCode = "AED",
            currencySymbol = "AED",
            exchangeRateToUSD = 3.67,
            standardTaxPct = 5.0,
            avgDutyPct = 5.0,
            deliveryDaysAvg = 3,
            marketOpportunityScore = 98,
            isEnabled = true
        ),
        MarketInfo(
            code = "SA",
            name = "Saudi Arabia",
            flag = "🇸🇦",
            currencyCode = "SAR",
            currencySymbol = "SAR",
            exchangeRateToUSD = 3.75,
            standardTaxPct = 15.0,
            avgDutyPct = 7.0,
            deliveryDaysAvg = 4,
            marketOpportunityScore = 95,
            isEnabled = true
        ),
        MarketInfo(
            code = "US",
            name = "United States",
            flag = "🇺🇸",
            currencyCode = "USD",
            currencySymbol = "$",
            exchangeRateToUSD = 1.0,
            standardTaxPct = 7.5,
            avgDutyPct = 3.0,
            deliveryDaysAvg = 5,
            marketOpportunityScore = 89,
            isEnabled = true
        ),
        MarketInfo(
            code = "GB",
            name = "United Kingdom",
            flag = "🇬🇧",
            currencyCode = "GBP",
            currencySymbol = "£",
            exchangeRateToUSD = 0.79,
            standardTaxPct = 20.0,
            avgDutyPct = 4.0,
            deliveryDaysAvg = 5,
            marketOpportunityScore = 86,
            isEnabled = true
        ),
        MarketInfo(
            code = "EU",
            name = "European Union (DE/FR/IT)",
            flag = "🇪🇺",
            currencyCode = "EUR",
            currencySymbol = "€",
            exchangeRateToUSD = 0.92,
            standardTaxPct = 19.0,
            avgDutyPct = 5.0,
            deliveryDaysAvg = 6,
            marketOpportunityScore = 87,
            isEnabled = true
        )
    )

    val initialSuppliers = listOf(
        // --- INDIA SUPPLIERS ---
        Supplier(
            id = "sup_in_01",
            name = "Roposo Clout / GlowRoad Direct",
            rating = 4.88,
            productCostINR = 320.0,
            shippingCostINR = 65.0,
            deliveryDays = 2,
            moq = 1,
            returnPolicy = "7-Day Easy Doorstep Return & Instant UPI Refund",
            reliabilityScore = 97,
            country = "India (Delhi NCR & Mumbai Hubs)",
            isVerified = true,
            notes = "Zero-inventory dropship giant covering 24,000+ Indian pincodes. Full Cash on Delivery support with automated NDR (Non-Delivery Report) AI voice calls to prevent RTO.",
            logoEmoji = "🇮🇳",
            primaryMarket = "IN",
            supportedMarkets = listOf("IN"),
            specialty = "High-Margin Lifestyle, Fitness & Trending Home Gadgets",
            warehouses = "Delhi NCR, Mumbai & Bengaluru Fulfillment Centers",
            shippingCarriers = listOf("Blue Dart", "Delhivery Surface & Express", "Xpressbees"),
            avgProfitMarginPct = 58.0,
            codSupported = true,
            customBranding = true,
            integrationType = "1-Click Shopify & Direct Store API",
            orderSyncSpeed = "Real-Time Automated Push (< 5s)",
            totalOrdersFulfilled = "1,850,000+",
            contactSupportSla = "< 30 Mins WhatsApp Business Dedicated Agent",
            isIntegrated = true
        ),
        Supplier(
            id = "sup_in_02",
            name = "DropKwik / Shiprocket Fulfillment",
            rating = 4.79,
            productCostINR = 380.0,
            shippingCostINR = 75.0,
            deliveryDays = 3,
            moq = 1,
            returnPolicy = "Doorstep QA Inspection & 5-Day Buyer Exchange",
            reliabilityScore = 95,
            country = "India (Bengaluru & Gurugram)",
            isVerified = true,
            notes = "Smart AI courier allocation engine selecting lowest RTO carrier per destination pincode. Automated SMS tracking with custom store branding.",
            logoEmoji = "🚀",
            primaryMarket = "IN",
            supportedMarkets = listOf("IN"),
            specialty = "Ergonomics, Smart Electronics & Beauty Accessories",
            warehouses = "Bengaluru, Gurugram, Kolkata & Hyderabad Hubs",
            shippingCarriers = listOf("Shiprocket Prime", "DTDC Air", "Ecom Express", "Delhivery"),
            avgProfitMarginPct = 52.0,
            codSupported = true,
            customBranding = true,
            integrationType = "REST API / Webhook Auto-Fulfill",
            orderSyncSpeed = "Instant Cloud Sync",
            totalOrdersFulfilled = "920,000+",
            contactSupportSla = "< 1 Hour Priority Escalation Desk",
            isIntegrated = true
        ),
        Supplier(
            id = "sup_in_03",
            name = "Baapstore B2B Direct",
            rating = 4.72,
            productCostINR = 340.0,
            shippingCostINR = 70.0,
            deliveryDays = 3,
            moq = 1,
            returnPolicy = "10-Day Manufacturer Defect Replacement",
            reliabilityScore = 93,
            country = "India (Chennai & Surat)",
            isVerified = true,
            notes = "Direct manufacturer factory rates with full GST input tax credit (ITC) invoices. Pre-printed personalized custom invoices with your brand logo.",
            logoEmoji = "🏷️",
            primaryMarket = "IN",
            supportedMarkets = listOf("IN"),
            specialty = "Fitness Gear, Ergonomic Cushions & Kitchen Innovations",
            warehouses = "Chennai & Surat Central Depots",
            shippingCarriers = listOf("Bluedart", "Speed Post", "Delhivery"),
            avgProfitMarginPct = 55.0,
            codSupported = true,
            customBranding = true,
            integrationType = "Automated CSV & API Sync",
            orderSyncSpeed = "Hourly Batch Dispatch",
            totalOrdersFulfilled = "480,000+",
            contactSupportSla = "< 2 Hours Dedicated Account Manager",
            isIntegrated = true
        ),

        // --- UAE & SAUDI ARABIA (GCC) SUPPLIERS ---
        Supplier(
            id = "sup_ae_01",
            name = "ApexDirect Gulf MegaHub",
            rating = 4.92,
            productCostINR = 420.0,
            shippingCostINR = 160.0,
            deliveryDays = 3,
            moq = 1,
            returnPolicy = "30-Day Instant Dubai/Riyadh Hub Local Return",
            reliabilityScore = 98,
            country = "UAE (Dubai CommerCity) & Saudi Arabia (Riyadh)",
            isVerified = true,
            notes = "Premier Middle-East dropshipping powerhouse. Pre-cleared GCC customs (SABER/SASO compliant in Saudi Arabia). Dual Arabic/English luxury packaging and Tabby BNPL return support.",
            logoEmoji = "🇦🇪",
            primaryMarket = "AE",
            supportedMarkets = listOf("AE", "SA"),
            specialty = "High-Ticket Wellness, Luxury Beauty & Smart Posture Tech",
            warehouses = "Dubai CommerCity Freezone & Riyadh Logistics Park",
            shippingCarriers = listOf("SMSA Express (KSA)", "Aramex Priority", "Emirates Post", "Fetchr"),
            avgProfitMarginPct = 62.0,
            codSupported = true,
            customBranding = true,
            integrationType = "Direct 1-Click Cloud API Sync",
            orderSyncSpeed = "Real-Time Automated Push (< 2s)",
            totalOrdersFulfilled = "1,450,000+",
            contactSupportSla = "< 15 Mins 24/7 Bilingual (English & Arabic) VIP Desk",
            isIntegrated = true
        ),
        Supplier(
            id = "sup_sa_01",
            name = "DropshipGCC / Tawreed Direct",
            rating = 4.86,
            productCostINR = 460.0,
            shippingCostINR = 150.0,
            deliveryDays = 4,
            moq = 1,
            returnPolicy = "14-Day Free Riyadh Courier Pickup",
            reliabilityScore = 96,
            country = "Saudi Arabia (Riyadh & Jeddah Hubs)",
            isVerified = true,
            notes = "Specialized GCC cash-flow partner with 48-hour COD remittance into UAE & Saudi commercial banks. High average order value (AOV) catalog optimized for Gulf consumers.",
            logoEmoji = "🇸🇦",
            primaryMarket = "SA",
            supportedMarkets = listOf("SA", "AE"),
            specialty = "Ergonomic Lumbar Pillows, Heated Massagers & Smart LED",
            warehouses = "Riyadh Sulay Hub & Jeddah Port Zone",
            shippingCarriers = listOf("SMSA Express", "Naqel Express", "Zajil Express"),
            avgProfitMarginPct = 59.0,
            codSupported = true,
            customBranding = true,
            integrationType = "Automated Webhook Dispatch",
            orderSyncSpeed = "Instant Real-Time",
            totalOrdersFulfilled = "680,000+",
            contactSupportSla = "< 45 Mins WhatsApp Support",
            isIntegrated = true
        ),

        // --- USA & NORTH AMERICA SUPPLIERS ---
        Supplier(
            id = "sup_us_01",
            name = "Spocket USA Premier / USADrop Direct",
            rating = 4.91,
            productCostINR = 580.0,
            shippingCostINR = 210.0,
            deliveryDays = 3,
            moq = 1,
            returnPolicy = "30-Day Hassle-Free USA Domestic Return",
            reliabilityScore = 98,
            country = "United States (Los Angeles & Dallas)",
            isVerified = true,
            notes = "100% US-manufactured and stocked inventory. 2-4 day domestic delivery via USPS Priority Mail & FedEx 2-Day. Custom branded invoice and personalized thank-you stickers in every parcel.",
            logoEmoji = "🇺🇸",
            primaryMarket = "US",
            supportedMarkets = listOf("US"),
            specialty = "Health & Wellness Tech, Smart Ergonomics & Home Gym",
            warehouses = "Los Angeles (CA), Dallas (TX) & Atlanta (GA)",
            shippingCarriers = listOf("USPS Priority Commercial", "FedEx 2-Day Express", "UPS Ground"),
            avgProfitMarginPct = 65.0,
            codSupported = false,
            customBranding = true,
            integrationType = "Native One-Click Store Connection",
            orderSyncSpeed = "Instant Automatic Auto-Fulfillment",
            totalOrdersFulfilled = "3,200,000+",
            contactSupportSla = "< 20 Mins Live Chat 24/7",
            isIntegrated = true
        ),
        Supplier(
            id = "sup_us_02",
            name = "Modalyst Luxe USA",
            rating = 4.84,
            productCostINR = 640.0,
            shippingCostINR = 230.0,
            deliveryDays = 4,
            moq = 1,
            returnPolicy = "30-Day US Domestic Exchange Policy",
            reliabilityScore = 96,
            country = "United States (New York & Miami)",
            isVerified = true,
            notes = "Curated high-ticket boutique suppliers with strict quality control. Zero customs friction and automated end-to-end tracking updates for US buyers.",
            logoEmoji = "💎",
            primaryMarket = "US",
            supportedMarkets = listOf("US"),
            specialty = "Premium Smart Lifestyle, Cervical Wellness & Biohacking Gear",
            warehouses = "New York, Miami & Chicago Facilities",
            shippingCarriers = listOf("USPS First Class & Priority", "DHL eCommerce US"),
            avgProfitMarginPct = 60.0,
            codSupported = false,
            customBranding = true,
            integrationType = "Automated API Synchronization",
            orderSyncSpeed = "Real-Time",
            totalOrdersFulfilled = "1,100,000+",
            contactSupportSla = "< 1 Hour Priority Helpdesk",
            isIntegrated = true
        ),

        // --- EUROPE & UK SUPPLIERS ---
        Supplier(
            id = "sup_eu_01",
            name = "BigBuy EU MegaHub",
            rating = 4.89,
            productCostINR = 610.0,
            shippingCostINR = 240.0,
            deliveryDays = 4,
            moq = 1,
            returnPolicy = "60-Day European Consumer Protection Guarantee",
            reliabilityScore = 97,
            country = "European Union (Valencia, Spain & Frankfurt, Germany)",
            isVerified = true,
            notes = "Largest B2B dropshipping wholesaler in Europe with 24-48h dispatch. Full IOSS VAT pre-registration for frictionless zero-customs EU cross-border delivery.",
            logoEmoji = "🇪🇺",
            primaryMarket = "EU",
            supportedMarkets = listOf("EU", "GB"),
            specialty = "Medical-Grade Ergonomics, Fitness Devices & Smart Home",
            warehouses = "Valencia (Spain) & Frankfurt (Germany) Mega-Facilities",
            shippingCarriers = listOf("DHL EuroConnect", "DPD Classic", "GLS Europe", "Correos Express"),
            avgProfitMarginPct = 56.0,
            codSupported = true,
            customBranding = true,
            integrationType = "REST API / Multi-Channel Catalog Feed",
            orderSyncSpeed = "Instant Real-Time",
            totalOrdersFulfilled = "2,400,000+",
            contactSupportSla = "< 30 Mins Multilingual EU Support Desk",
            isIntegrated = true
        ),
        Supplier(
            id = "sup_gb_01",
            name = "Avasam UK Direct",
            rating = 4.82,
            productCostINR = 570.0,
            shippingCostINR = 200.0,
            deliveryDays = 3,
            moq = 1,
            returnPolicy = "30-Day UK Consumer Rights Compliant Return",
            reliabilityScore = 95,
            country = "United Kingdom (London & Manchester)",
            isVerified = true,
            notes = "UK-verified suppliers with stock held directly in England. 24h/48h Royal Mail tracked dispatch with zero import duties for British buyers.",
            logoEmoji = "🇬🇧",
            primaryMarket = "GB",
            supportedMarkets = listOf("GB", "EU"),
            specialty = "Physiotherapy Massagers, Smart Gadgets & Desk Ergonomics",
            warehouses = "London Heathrow Gateway & Manchester Hub",
            shippingCarriers = listOf("Royal Mail 24/48 Tracked", "Evri Next Day", "DPD UK"),
            avgProfitMarginPct = 57.0,
            codSupported = false,
            customBranding = true,
            integrationType = "1-Click Direct Cloud Integration",
            orderSyncSpeed = "Instant Push",
            totalOrdersFulfilled = "790,000+",
            contactSupportSla = "< 45 Mins Dedicated UK Support Team",
            isIntegrated = true
        ),

        // --- GLOBAL EXPRESS & FACTORY AIR CHARTER ---
        Supplier(
            id = "sup_gl_01",
            name = "CJ Dropshipping VIP Private Line",
            rating = 4.85,
            productCostINR = 350.0,
            shippingCostINR = 180.0,
            deliveryDays = 6,
            moq = 1,
            returnPolicy = "Free Replacement on Shipping Damage / Lost Parcel",
            reliabilityScore = 96,
            country = "Global (Shenzhen & Yiwu Air Charters)",
            isVerified = true,
            notes = "Factory direct sourcing with dedicated CJ Packet Fast Air Line (5-7 days to US, Europe & GCC). Free custom video inspection proof before parcel dispatch.",
            logoEmoji = "🌐",
            primaryMarket = "GLOBAL",
            supportedMarkets = listOf("IN", "AE", "SA", "US", "GB", "EU"),
            specialty = "Viral TikTok Winners, OEM Custom Electronics & Posture Tech",
            warehouses = "Shenzhen, Yiwu, Hong Kong & Global Air Charter Hubs",
            shippingCarriers = listOf("CJ Packet Fast Line", "DHL Express Global", "YunExpress Special Line"),
            avgProfitMarginPct = 64.0,
            codSupported = true,
            customBranding = true,
            integrationType = "Native Automated ERP / App Store Integration",
            orderSyncSpeed = "Instant 1-Click Fulfillment",
            totalOrdersFulfilled = "8,900,000+",
            contactSupportSla = "< 15 Mins 1-on-1 Dedicated Sourcing Agent",
            isIntegrated = true
        ),
        Supplier(
            id = "sup_gl_02",
            name = "HyperSKU / Wiio Cross-Border Elite",
            rating = 4.80,
            productCostINR = 370.0,
            shippingCostINR = 190.0,
            deliveryDays = 6,
            moq = 1,
            returnPolicy = "Automated Refund Guarantee for Damaged Deliveries",
            reliabilityScore = 94,
            country = "Global (Hong Kong & Shenzhen)",
            isVerified = true,
            notes = "Private agent experience with 100% automated quality inspection, custom laser branding, custom flyer inserts, and express air freight to 70+ countries.",
            logoEmoji = "⚡",
            primaryMarket = "GLOBAL",
            supportedMarkets = listOf("IN", "AE", "SA", "US", "GB", "EU"),
            specialty = "High-Margin Tech Gadgets, Heated Therapeutic Wear & Beauty Devices",
            warehouses = "Hong Kong Free Port & Shenzhen Airport Logistics",
            shippingCarriers = listOf("Yanwen Special Express", "4PX Worldwide", "DHL eCommerce"),
            avgProfitMarginPct = 61.0,
            codSupported = false,
            customBranding = true,
            integrationType = "API & Webhook Automated Sync",
            orderSyncSpeed = "Real-Time Auto-Fulfill",
            totalOrdersFulfilled = "2,100,000+",
            contactSupportSla = "< 30 Mins Dedicated Account Manager",
            isIntegrated = true
        )
    )

    val sampleProducts = listOf(
        Product(
            id = "prod_01",
            name = "Smart Sonic Pulse Neck & Spine Massager",
            category = "Fitness & Health Tech",
            niche = "Ergonomics & Wellness",
            demandLevel = "Trending High",
            trendScore = 96,
            competitionLevel = "Medium",
            supplierCostINR = 490.0,
            shippingCostINR = 180.0,
            platformFeeINR = 120.0,
            adCostEstimateINR = 310.0,
            targetMarginPct = 48.0,
            opportunityScore = 95,
            riskScore = 14,
            deliveryDays = 5,
            recommendedMarkets = listOf("AE", "SA", "IN", "US"),
            isLaunched = true,
            isWinnerProduct = true,
            description = "Medical-grade TENS and heat pulse cervical massager engineered for desk workers, athletes, and gamers to eliminate neck stiffness in 15 minutes.",
            benefits = listOf(
                "Relieves chronic cervical tension using dual-frequency acoustic pulse therapy",
                "Instant 42°C constant temperature infrared thermotherapy",
                "Ultra-lightweight 130g wireless ergonomic collar design with 8-day battery life"
            ),
            bulletPoints = listOf(
                "6 pulse massage modes & 15 customizable intensity levels",
                "Skin-friendly 304 stainless steel dynamic floating electrode pads",
                "Type-C fast recharge with ultra-silent 25dB motor",
                "Full CE, FCC, RoHS certified medical ergonomics"
            ),
            specifications = mapOf(
                "Battery Capacity" to "1200mAh Lithium-ion",
                "Heat Setting" to "42°C ± 2°C Infrared",
                "Weight" to "135g",
                "Materials" to "Elastic Silicone + 304 Stainless Steel"
            ),
            seoTitle = "Smart Sonic Neck & Cervical Massager | Relief in 15 Mins",
            seoDescription = "Eliminate neck pain and fatigue instantly with the Smart Sonic Pulse Massager. Wireless, heated, and ergonomic. Free express worldwide delivery.",
            keywords = listOf("neck massager", "tens pulse device", "desk worker relief", "cervical spine care", "heated neck collar"),
            tags = listOf("Bestseller", "Fitness Tech", "High Margin", "Fast Shipping"),
            faqs = listOf(
                "Is it safe to use daily?" to "Yes, recommended for 15-20 minutes daily for maximum tension release.",
                "How fast does it heat up?" to "Reaches optimal 42°C therapeutic warmth within 3 seconds of activation.",
                "What is the warranty?" to "Includes 12 months comprehensive replacement warranty."
            ),
            sku = "SNAP-NECK-PULSE-01",
            adHeadline = "Eliminate 8 Hours Of Desk Stiffness In 15 Minutes",
            adPrimaryText = "Over 40,000 desk workers across UAE and Saudi Arabia transformed their daily comfort. Try the Smart Sonic Neck Massager risk-free today with 30-day money-back guarantee.",
            adCta = "Claim 50% Off & Free Express Shipping",
            socialCaptions = listOf(
                "Say goodbye to text neck! 💆‍♂️ The wireless heated massager you can wear anywhere.",
                "Work all day without the tension. This viral sonic pulse gadget is a game changer! 🔥"
            ),
            emailSubject = "Your neck will thank you for this (50% Launch Special inside)",
            emailBody = "Hey there,\n\nIf you spend more than 4 hours a day looking at a screen, your cervical spine is taking up to 27kg of extra strain.\n\nMeet the Smart Sonic Neck Massager — instant relief anytime, anywhere.",
            videoScriptHook = "Stop scrolling if your neck feels like concrete after work!",
            videoScriptUgc = "Show talent sitting slumped over laptop -> zooms in on tight neck -> snaps on the sleek gold/white collar -> sigh of relief with thermal animation -> unboxing & 5-star rating overlay.",
            suppliers = initialSuppliers.take(2)
        ),
        Product(
            id = "prod_02",
            name = "AuraGlow 4-in-1 Microcurrent Facial Sculptor",
            category = "Beauty & Skincare Tech",
            niche = "Anti-Aging & Self Care",
            demandLevel = "Very High",
            trendScore = 93,
            competitionLevel = "Medium",
            supplierCostINR = 410.0,
            shippingCostINR = 160.0,
            platformFeeINR = 100.0,
            adCostEstimateINR = 290.0,
            targetMarginPct = 52.0,
            opportunityScore = 92,
            riskScore = 18,
            deliveryDays = 6,
            recommendedMarkets = listOf("AE", "SA", "US", "GB", "IN"),
            isLaunched = true,
            isWinnerProduct = true,
            description = "At-home spa contouring device combining Red Light Therapy (630nm), EMS microcurrent, therapeutic warmth, and facial lymphatic vibration.",
            benefits = listOf(
                "Visibly sculpts jawline and lifts facial contours in 2 weeks",
                "Reduces puffiness and smooths fine lines with medical-grade red photons",
                "Boosts skincare serum absorption by up to 300%"
            ),
            bulletPoints = listOf(
                "360° rotating massage head contours perfectly around chin and cheekbones",
                "Dual light spectrum: 630nm Collagen Red & 465nm Clarifying Blue",
                "USB rechargeable with portable luxury vanity pouch"
            ),
            specifications = mapOf(
                "LED Wavelengths" to "630nm Red / 465nm Blue",
                "Microcurrent" to "330uA Safe Frequency",
                "Battery Life" to "60 mins continuous"
            ),
            seoTitle = "AuraGlow 4-in-1 Facial Sculpting Wand | Red Light & EMS",
            seoDescription = "Lift, smooth, and rejuvenate your skin at home with AuraGlow. The viral 4-in-1 skincare wand with Red Light Therapy and EMS.",
            keywords = listOf("facial sculpting wand", "red light therapy", "microcurrent face lift", "anti aging wand", "skin glow gadget"),
            tags = listOf("Viral Beauty", "TikTok Winner", "High Margin"),
            faqs = listOf(
                "When will I see results?" to "Most users notice lifted, radiant skin after just 3 consecutive days of 5-minute sessions.",
                "Can I use it with my own serum?" to "Yes! It works best when applied over your favorite hyaluronic acid or hydrating serum."
            ),
            sku = "SNAP-BEAUTY-GLOW-02",
            adHeadline = "The 5-Minute At-Home Facial That Broke The Internet",
            adPrimaryText = "Skip the $250 salon appointments. Get radiant, sculpted skin from the comfort of your vanity with the AuraGlow Microcurrent Wand.",
            adCta = "Get Yours Today — Limited Stock",
            socialCaptions = listOf(
                "My morning glow routine takes literally 5 minutes now! ✨ Watch the instant cheekbone lift.",
                "Red light therapy + microcurrent at home. Why didn't I buy this sooner? 😍"
            ),
            emailSubject = "Achieve the glass-skin glow in 5 minutes a day 🌟",
            emailBody = "Dull skin and morning puffiness? Discover how 4 technologies in 1 single wand can give you salon-grade lifting at home.",
            videoScriptHook = "The secret behind effortless snatched cheekbones without contour makeup.",
            videoScriptUgc = "Creator applies serum -> turns on glowing red light wand -> glides along jawline -> split screen shows immediate lifted difference -> happy smile.",
            suppliers = listOf(initialSuppliers[0], initialSuppliers[1])
        ),
        Product(
            id = "prod_03",
            name = "MagnaGrip 3-in-1 Foldable MagSafe Travel Charger",
            category = "Consumer Electronics",
            niche = "Mobile Accessories & Travel",
            demandLevel = "High",
            trendScore = 91,
            competitionLevel = "High",
            supplierCostINR = 550.0,
            shippingCostINR = 140.0,
            platformFeeINR = 110.0,
            adCostEstimateINR = 320.0,
            targetMarginPct = 44.0,
            opportunityScore = 88,
            riskScore = 15,
            deliveryDays = 5,
            recommendedMarkets = listOf("US", "GB", "EU", "AE", "SA"),
            isLaunched = false,
            isWinnerProduct = false,
            description = "Ultra-compact origami magnetic wireless charging station for iPhone, Apple Watch, and AirPods. Folds into a pocket-sized cube for seamless travel.",
            benefits = listOf(
                "Charges Phone (15W), Watch (3W), and Earbuds (5W) simultaneously from a single cable",
                "Precision N52 neodymium magnets for snap-on alignment in portrait or landscape",
                "Foldable origami design weighs just 110g — the ultimate nightstand & travel companion"
            ),
            bulletPoints = listOf(
                "Fast 15W Qi-certified intelligent temperature management",
                "Aircraft-grade aluminum hinge rated for 10,000+ folds",
                "Includes 20W PD adapter and braided USB-C cable in box"
            ),
            specifications = mapOf(
                "Output Power" to "15W + 5W + 3W Fast Charge",
                "Folded Dimensions" to "68mm x 68mm x 23mm",
                "Safety" to "FOD Foreign Object & Overvoltage Protection"
            ),
            seoTitle = "3-in-1 Foldable MagSafe Travel Wireless Charger Station",
            seoDescription = "Declutter your nightstand and travel pack with the 3-in-1 Foldable Magnetic Charger. 15W fast charge for phone, watch, and pods.",
            keywords = listOf("magsafe travel charger", "3 in 1 wireless station", "foldable apple charger", "desk organizer gadget"),
            tags = listOf("Tech Trend", "Travel Must-Have", "Electronics"),
            faqs = listOf(
                "Does it support standby mode?" to "Yes, snaps into landscape kickstand mode perfectly to display clock and widgets.",
                "Is the wall brick included?" to "Yes, premium 20W fast-charging adapter is included."
            ),
            sku = "SNAP-TECH-MAG-03",
            adHeadline = "Never Pack 3 Different Charging Cables Again",
            adPrimaryText = "Charge your phone, watch, and pods in one sleek foldable magnetic dock. Travel light and keep your nightstand clutter-free.",
            adCta = "Shop The 3-in-1 Travel Charger",
            socialCaptions = listOf(
                "The only charger I pack for flights now. Folds down to the size of an AirPods case! ✈️🔋",
                "No more tangled wires on the hotel nightstand. Pure genius design. 🤯"
            ),
            emailSubject = "The smartest travel accessory of 2026 is here ⚡",
            emailBody = "Meet your new minimalist travel essential. One cable, three devices, zero clutter.",
            videoScriptHook = "Show a tangled ball of 5 messy cables, then toss it in the bin!",
            videoScriptUgc = "Unfolds the sleek metallic cube -> snaps iPhone on top -> watch on side -> AirPods on bottom -> all three glow green charging simultaneously.",
            suppliers = listOf(initialSuppliers[1], initialSuppliers[0])
        ),
        Product(
            id = "prod_04",
            name = "ErgoFlex Zero-Gravity Lumbar Posture Seat Cushion",
            category = "Home & Office Ergonomics",
            niche = "Work From Home & Driving",
            demandLevel = "Trending",
            trendScore = 89,
            competitionLevel = "Low",
            supplierCostINR = 480.0,
            shippingCostINR = 240.0,
            platformFeeINR = 130.0,
            adCostEstimateINR = 280.0,
            targetMarginPct = 46.0,
            opportunityScore = 91,
            riskScore = 12,
            deliveryDays = 4,
            recommendedMarkets = listOf("IN", "AE", "SA", "US"),
            isLaunched = false,
            isWinnerProduct = true,
            description = "Orthopedic contour memory foam cushion with cooling bamboo charcoal honeycomb gel to eliminate tailbone pressure and sciatica pain.",
            benefits = listOf(
                "U-shaped coccyx cut-out suspends tailbone to relieve lower back pain",
                "High-density dynamic memory foam never flattens even with 8+ hours daily use",
                "Breathable 3D mesh cover with anti-slip rubber bottom"
            ),
            bulletPoints = listOf(
                "Doctor-recommended ergonomic pelvic contouring",
                "Built-in side carry handle for seamless car-to-office transport",
                "Machine washable zipper cover"
            ),
            specifications = mapOf(
                "Core Material" to "100% High Density Memory Foam + Cool Gel",
                "Dimensions" to "45cm x 36cm x 7cm",
                "Weight Capacity" to "Supports up to 140kg"
            ),
            seoTitle = "Ergonomic Orthopedic Memory Foam Seat Cushion | Back Relief",
            seoDescription = "Transform any office chair or car seat into an orthopedic ergonomic recliner. Zero pressure on tailbone and spine.",
            keywords = listOf("ergonomic seat cushion", "sciatica relief", "office chair cushion", "coccyx memory foam", "tailbone pain"),
            tags = listOf("High Repeat", "Office Wellness", "Bestseller"),
            faqs = listOf(
                "Does it work in car seats?" to "Yes, fits perfectly in all vehicle driver and passenger seats.",
                "Does it lose its shape?" to "Made of high-density memory foam with a 3-year anti-flattening guarantee."
            ),
            sku = "SNAP-ERGO-CUSHION-04",
            adHeadline = "Turn Any Hard Chair Into A Cloud For Your Spine",
            adPrimaryText = "If sitting for long hours gives you lower back aches, this orthopedic coccyx cushion distributes your body weight evenly.",
            adCta = "Claim Yours With Free Delivery",
            socialCaptions = listOf(
                "Sitting 8 hours without back pain is finally possible! 🪑☁️",
                "Your office chair needs this upgrade right now."
            ),
            emailSubject = "Fix your sitting posture and end back stiffness today",
            emailBody = "Don't let bad chairs ruin your spine health. Experience zero-gravity comfort today.",
            videoScriptHook = "If you sit at a desk or drive over 2 hours a day, watch this!",
            videoScriptUgc = "Shows egg test on the cushion -> person sits on it -> egg stays unbroken -> explains weight distribution -> shows happy comfortable posture.",
            suppliers = listOf(initialSuppliers[2], initialSuppliers[0])
        ),
        Product(
            id = "prod_05",
            name = "PetGroom Pro Ultrasonic Deshedding Vacuum Brush",
            category = "Pet Supplies & Care",
            niche = "Pet Grooming & Tech",
            demandLevel = "High",
            trendScore = 88,
            competitionLevel = "Low",
            supplierCostINR = 620.0,
            shippingCostINR = 210.0,
            platformFeeINR = 140.0,
            adCostEstimateINR = 340.0,
            targetMarginPct = 45.0,
            opportunityScore = 90,
            riskScore = 16,
            deliveryDays = 5,
            recommendedMarkets = listOf("US", "GB", "EU", "AE"),
            isLaunched = false,
            isWinnerProduct = false,
            description = "Whisper-quiet pet grooming vacuum brush that removes and suctions 99% of loose fur directly into an easy-empty canister before it hits the floor.",
            benefits = listOf(
                "Eliminates pet hair on furniture, carpets, and clothes with 1-click suction",
                "Ultra-quiet 52dB operation won't scare sensitive dogs and cats",
                "Self-cleaning stainless steel pin brush ejects fur smoothly"
            ),
            bulletPoints = listOf(
                "Gentle rounded massage tips stimulate skin blood circulation",
                "Universal hose adapter fits standard household vacuums and portable units",
                "Includes de-matting and flea comb attachments"
            ),
            specifications = mapOf(
                "Noise Level" to "< 52dB Whispering Quiet",
                "Brush Material" to "Polished 304 Stainless Round Bristles",
                "Compatibility" to "Universal Vacuum Adapter Included"
            ),
            seoTitle = "PetGroom Pro Vacuum Grooming & Deshedding Brush Tool",
            seoDescription = "Brush and vacuum loose pet fur at the same time without flying hair or mess. Safe, gentle, and quiet for all dog and cat breeds.",
            keywords = listOf("pet vacuum brush", "dog deshedding tool", "cat hair vacuum", "pet grooming kit", "no mess fur brush"),
            tags = listOf("Pet Lovers", "High Engagement", "Viral Video Product"),
            faqs = listOf(
                "Will it scare my puppy or cat?" to "It runs under 52dB, far quieter than standard clippers.",
                "Is it easy to clean?" to "Simply press the quick-release button and fur ejects in one neat pack."
            ),
            sku = "SNAP-PET-GROOM-05",
            adHeadline = "Stop Pet Hair Flying Everywhere When You Brush",
            adPrimaryText = "Groom your dog or cat and vacuum 99% of loose fur simultaneously. Say goodbye to hair all over your couch and rugs.",
            adCta = "Get 40% Off PetGroom Pro",
            socialCaptions = listOf(
                "Where has this been all my life?! No more fur snowstorms in the living room! 🐶✨",
                "Golden retriever moms & dads... this is the holy grail grooming tool! 🐾"
            ),
            emailSubject = "The mess-free way to groom your furry best friend 🐕",
            emailBody = "Love your pet but tired of fur covering everything? Meet the vacuum brush that captures shedding instantly.",
            videoScriptHook = "Every dog parent needs to see this life-saving hack right now!",
            videoScriptUgc = "Fluffy husky being brushed -> vacuum suction whisks away shedding clumps in real time -> shows clean couch and happy wagging tail.",
            suppliers = listOf(initialSuppliers[0], initialSuppliers[1])
        )
    )

    val sampleOrders = listOf(
        Order(
            id = "ORD-84920",
            customerName = "Mohammed Al-Mansoor",
            customerEmail = "m.mansoor@gmail.com",
            customerCity = "Dubai, UAE",
            marketCode = "AE",
            productName = "Smart Sonic Pulse Neck Massager",
            quantity = 2,
            revenueUSD = 198.0,
            costUSD = 76.0,
            profitUSD = 122.0,
            paymentStatus = PaymentStatus.PAID,
            fulfillmentStatus = FulfillmentStatus.SHIPPED,
            shippingStatus = "In Transit — Arriving Tomorrow",
            trackingNumber = "AE-EX-99201948",
            date = "Today, 10:45 AM",
            riskScore = 8,
            riskFactors = listOf("Verified Apple Pay payment", "Standard residential delivery address"),
            isSupplierPaid = true,
            supplierName = "ApexDirect Global"
        ),
        Order(
            id = "ORD-84919",
            customerName = "Priya Sharma",
            customerEmail = "priya.s@techconsult.in",
            customerCity = "Bengaluru, India",
            marketCode = "IN",
            productName = "AuraGlow 4-in-1 Facial Sculptor",
            quantity = 1,
            revenueUSD = 42.0,
            costUSD = 16.5,
            profitUSD = 25.5,
            paymentStatus = PaymentStatus.PAID,
            fulfillmentStatus = FulfillmentStatus.FULFILLED,
            shippingStatus = "Dispatched via BlueDart Express",
            trackingNumber = "IN-BD-4481023",
            date = "Today, 09:12 AM",
            riskScore = 12,
            riskFactors = listOf("UPI instant payment verified"),
            isSupplierPaid = true,
            supplierName = "IndoExpress Hub"
        ),
        Order(
            id = "ORD-84918",
            customerName = "Tariq Al-Otaibi",
            customerEmail = "tariq.otaibi@riyadh.sa",
            customerCity = "Riyadh, Saudi Arabia",
            marketCode = "SA",
            productName = "Smart Sonic Pulse Neck Massager",
            quantity = 1,
            revenueUSD = 89.0,
            costUSD = 36.0,
            profitUSD = 53.0,
            paymentStatus = PaymentStatus.PAID,
            fulfillmentStatus = FulfillmentStatus.PROCESSING,
            shippingStatus = "Supplier preparing package",
            trackingNumber = "SA-SMSA-778210",
            date = "Today, 08:30 AM",
            riskScore = 15,
            riskFactors = listOf("Mada card payment success"),
            isSupplierPaid = true,
            supplierName = "ApexDirect Global"
        ),
        Order(
            id = "ORD-84917",
            customerName = "Jessica Campbell",
            customerEmail = "jess.campbell@austin.edu",
            customerCity = "Austin, TX, USA",
            marketCode = "US",
            productName = "MagnaGrip 3-in-1 Foldable Travel Charger",
            quantity = 1,
            revenueUSD = 49.99,
            costUSD = 18.2,
            profitUSD = 31.79,
            paymentStatus = PaymentStatus.PAID,
            fulfillmentStatus = FulfillmentStatus.DELIVERED,
            shippingStatus = "Delivered to Front Porch",
            trackingNumber = "USPS-94001118992",
            date = "Yesterday, 04:15 PM",
            riskScore = 5,
            riskFactors = listOf("Stripe 3D-Secure verified"),
            isSupplierPaid = true,
            supplierName = "Shenzhen SpeedCraft"
        ),
        Order(
            id = "ORD-84916",
            customerName = "Anonymous Guest (Flagged)",
            customerEmail = "temp.user9948@relay.net",
            customerCity = "Unknown Proxy IP",
            marketCode = "US",
            productName = "Smart Sonic Pulse Neck Massager",
            quantity = 5,
            revenueUSD = 495.0,
            costUSD = 190.0,
            profitUSD = 305.0,
            paymentStatus = PaymentStatus.PENDING,
            fulfillmentStatus = FulfillmentStatus.PENDING,
            shippingStatus = "On Hold — Security Review Required",
            trackingNumber = "N/A",
            date = "Yesterday, 11:20 PM",
            riskScore = 88,
            riskFactors = listOf(
                "High Risk: 3 failed card attempts prior to success",
                "High Risk: VPN / Datacenter proxy IP detected",
                "High Risk: Billing zip code does not match IP state"
            ),
            isSupplierPaid = false,
            supplierName = "ApexDirect Global"
        )
    )

    val sampleCustomers = listOf(
        Customer(
            id = "cust_01",
            name = "Mohammed Al-Mansoor",
            email = "m.mansoor@gmail.com",
            phone = "+971 50 123 4567",
            country = "United Arab Emirates",
            ordersCount = 6,
            totalSpentUSD = 740.0,
            ltvUSD = 1250.0,
            purchaseFrequencyDays = 21,
            segment = CustomerSegment.VIP,
            lastOrderDate = "Today",
            suggestedUpsell = "AuraGlow Facial Sculptor Gold Edition"
        ),
        Customer(
            id = "cust_02",
            name = "Priya Sharma",
            email = "priya.s@techconsult.in",
            phone = "+91 98765 43210",
            country = "India",
            ordersCount = 3,
            totalSpentUSD = 185.0,
            ltvUSD = 320.0,
            purchaseFrequencyDays = 34,
            segment = CustomerSegment.REPEAT_BUYER,
            lastOrderDate = "Today",
            suggestedUpsell = "ErgoFlex Lumbar Cushion for Work From Home"
        ),
        Customer(
            id = "cust_03",
            name = "Tariq Al-Otaibi",
            email = "tariq.otaibi@riyadh.sa",
            phone = "+966 55 987 6543",
            country = "Saudi Arabia",
            ordersCount = 4,
            totalSpentUSD = 490.0,
            ltvUSD = 850.0,
            purchaseFrequencyDays = 28,
            segment = CustomerSegment.HIGH_VALUE,
            lastOrderDate = "Today",
            suggestedUpsell = "Smart Sonic Neck Replacement Gel Pads 4-Pack"
        ),
        Customer(
            id = "cust_04",
            name = "Jessica Campbell",
            email = "jess.campbell@austin.edu",
            phone = "+1 512 555 0192",
            country = "United States",
            ordersCount = 2,
            totalSpentUSD = 98.0,
            ltvUSD = 160.0,
            purchaseFrequencyDays = 62,
            segment = CustomerSegment.AT_RISK,
            lastOrderDate = "Yesterday",
            suggestedUpsell = "Win-back 20% discount coupon for MagnaGrip Travel Case"
        )
    )

    val sampleCampaigns = listOf(
        Campaign(
            id = "camp_01",
            name = "UAE & KSA — Sonic Neck Massager Video Hooks",
            channel = MarketingChannel.META,
            status = CampaignStatus.ACTIVE,
            objective = "Purchases / ROAS Max",
            targetAudience = "UAE & Saudi Arabia (Age 24-52, Office Workers, Tech Enthusiasts)",
            dailyBudgetUSD = 120.0,
            spendUSD = 840.0,
            impressions = 64200,
            clicks = 2480,
            orders = 74,
            revenueUSD = 4820.0,
            profitUSD = 2460.0,
            headline = "Desk Neck Stiffness? Relieve It In 15 Mins",
            primaryText = "Over 40,000 satisfied professionals in Dubai and Riyadh. 50% Off Flash Launch today.",
            ctaText = "Shop Now — Express Delivery",
            creativeConcept = "UGC Split-screen Desk Worker Strain vs Thermal Relief Animation",
            aiOptimizationNotes = "ROAS is 5.74x. Scale daily budget by +15% safely during 6PM-11PM peak buying hours."
        ),
        Campaign(
            id = "camp_02",
            name = "TikTok Viral — AuraGlow Glass Skin Routine",
            channel = MarketingChannel.TIKTOK,
            status = CampaignStatus.OPTIMIZING,
            objective = "Conversions",
            targetAudience = "USA, UK & UAE (Age 18-38, Skincare & Beauty Trends)",
            dailyBudgetUSD = 90.0,
            spendUSD = 450.0,
            impressions = 98400,
            clicks = 3850,
            orders = 42,
            revenueUSD = 1890.0,
            profitUSD = 910.0,
            headline = "The 5-Min Snatched Jawline Hack",
            primaryText = "No salon appointments needed. Red light therapy + microcurrent contouring at home.",
            ctaText = "Order Now",
            creativeConcept = "Quick-paced before/after jawline glide with ASMR audio",
            aiOptimizationNotes = "Creative #3 has 4.8% CTR (Top 1%). Autopilot shifted 25% ad spend to Creative #3."
        ),
        Campaign(
            id = "camp_03",
            name = "Google Search High-Intent — MagSafe Charger",
            channel = MarketingChannel.GOOGLE,
            status = CampaignStatus.ACTIVE,
            objective = "Target CPA Purchases",
            targetAudience = "Global High-Intent Search: 'travel wireless charger', 'foldable 3 in 1 charger'",
            dailyBudgetUSD = 60.0,
            spendUSD = 360.0,
            impressions = 14200,
            clicks = 1120,
            orders = 28,
            revenueUSD = 1399.0,
            profitUSD = 680.0,
            headline = "Best 3-in-1 Foldable Travel Charger 2026",
            primaryText = "Charge iPhone, Apple Watch & AirPods with 1 compact dock. Free shipping today.",
            ctaText = "Buy Now",
            creativeConcept = "High-converting responsive search ad with sitelinks",
            aiOptimizationNotes = "Keyword 'portable magsafe stand' converting at 8.4%. Added exact match bid boost."
        )
    )

    val sampleCompetitors = listOf(
        CompetitorItem(
            id = "comp_01",
            name = "ReliefPro Ergonomics",
            publicPriceUSD = 69.99,
            category = "Neck Massagers",
            marketPositioning = "Premium Medical Grade",
            marketingThemes = listOf("Doctor Recommended", "Over 100,000 Sold", "60-Day Guarantee"),
            storeStrengths = "Clean video testimonials, rapid 2-day checkout with ShopPay",
            seoOpportunities = "Lacking localized Arabic keywords for UAE/Saudi market. Opportunity to dominate GCC search."
        ),
        CompetitorItem(
            id = "comp_02",
            name = "LumiGlow Beauty Co",
            publicPriceUSD = 59.00,
            category = "Skincare Devices",
            marketPositioning = "Trendy Viral TikTok Aesthetic",
            marketingThemes = listOf("Celebrity Favorite", "Glass Skin in 7 Days", "Clean Beauty"),
            storeStrengths = "Huge influencer UGC library and interactive shade finder",
            seoOpportunities = "High bundle churn. We can win by offering free botanical serum with each wand."
        )
    )

    val sampleABTests = listOf(
        ABTestItem(
            id = "ab_01",
            testName = "Hero Headline on Product Page",
            elementTested = "Headline Copy",
            variantA = "Eliminate 8 Hours Of Desk Stiffness In 15 Minutes",
            variantB = "Medical-Grade Sonic Neck Massager for Instant Relief",
            variantACtr = 4.8,
            variantBCtr = 3.2,
            variantAConversion = 3.9,
            variantBConversion = 2.4,
            variantARevenueUSD = 4280.0,
            variantBRevenueUSD = 2640.0,
            winningVariant = "Variant A (+62% Revenue)",
            confidencePct = 98,
            aiRecommendation = "Variant A emotional hook clearly outperforms clinical jargon. Deploy Variant A sitewide."
        ),
        ABTestItem(
            id = "ab_02",
            testName = "Price Point Testing in UAE Market",
            elementTested = "Pricing Tier",
            variantA = "AED 99 (Introductory Impulse Price)",
            variantB = "AED 129 + Free Travel Pouch Bonus",
            variantACtr = 4.2,
            variantBCtr = 4.1,
            variantAConversion = 4.6,
            variantBConversion = 4.1,
            variantARevenueUSD = 3800.0,
            variantBRevenueUSD = 4750.0,
            winningVariant = "Variant B (+25% Total Profit)",
            confidencePct = 95,
            aiRecommendation = "Variant B higher margin easily offsets the minor conversion dip, yielding higher net cashflow."
        )
    )

    val sampleSupportTickets = listOf(
        SupportTicket(
            id = "TCK-1049",
            customerName = "Zaid Al-Harbi",
            customerEmail = "zaid.harbi@jeddah.sa",
            orderId = "ORD-84918",
            query = "Where is my order? I ordered the neck massager 2 days ago to Jeddah.",
            aiDraftResponse = "Hello Zaid! 👋 Your order #ORD-84918 was fulfilled by our express courier (SMSA Express #SA-SMSA-778210). It is currently out for regional transit and is scheduled for doorstep delivery to Jeddah tomorrow afternoon between 1 PM and 5 PM. Track live here: https://track.within-a-snap.com/SA-SMSA-778210",
            isEscalated = false,
            isResolved = false,
            timestamp = "5m ago"
        ),
        SupportTicket(
            id = "TCK-1048",
            customerName = "Sarah Jenkins",
            customerEmail = "sarah.j@gmail.com",
            orderId = "ORD-84917",
            query = "How do I switch the light mode on the AuraGlow wand to Blue photon mode?",
            aiDraftResponse = "Hi Sarah! To switch modes on your AuraGlow Sculpting Wand, simply double-tap the center touch sensor while the wand is on. First tap activates Red Collagen mode (630nm), double-tap switches to Clarifying Blue mode (465nm). Let us know if you need anything else! ✨",
            isEscalated = false,
            isResolved = true,
            timestamp = "25m ago"
        )
    )

    val initialAiRecommendations = listOf(
        AIRecommendation(
            id = "rec_01",
            agentType = AgentType.MARKETING,
            title = "Scale UAE Meta Video Ad Campaign",
            recommendation = "Increase daily budget by +$30/day on Campaign #1 ('Sonic Neck Massager')",
            reason = "ROAS is performing at an exceptional 5.74x with high conversion consistency across Dubai and Abu Dhabi.",
            confidenceScore = 96,
            expectedImpact = "+$1,450 net monthly profit increase with sub-15% acquisition cost",
            suggestedAction = "Approve Budget Expansion to $150/day",
            status = RecommendationStatus.PENDING,
            targetEntityId = "camp_01"
        ),
        AIRecommendation(
            id = "rec_02",
            agentType = AgentType.RESEARCH,
            title = "Launch High-Opportunity Winner: Zero-Gravity Lumbar Seat Cushion",
            recommendation = "Deploy 1-Click Launch for 'ErgoFlex Zero-Gravity Lumbar Cushion' targeting India and UAE",
            reason = "Demand surged +44% this week while local supplier IndoExpress has 48h fulfillment at 46% margin.",
            confidenceScore = 92,
            expectedImpact = "Estimated $3,200 first-month gross revenue at low ad competition",
            suggestedAction = "Launch Product in 1-Click",
            status = RecommendationStatus.PENDING,
            targetEntityId = "prod_04"
        ),
        AIRecommendation(
            id = "rec_03",
            agentType = AgentType.SOURCING,
            title = "Switch Neck Massager Supplier to ApexDirect Hub",
            recommendation = "Reallocate 70% of new orders to ApexDirect Global for GCC destinations",
            reason = "Delivery speed is 3 days faster (5 days vs 8 days) with 96% reliability and identical net landing cost.",
            confidenceScore = 94,
            expectedImpact = "Reduces customer transit inquiries by 40% and boosts 5-star review rate",
            suggestedAction = "Set ApexDirect as Default Supplier",
            status = RecommendationStatus.PENDING,
            targetEntityId = "sup_01"
        ),
        AIRecommendation(
            id = "rec_04",
            agentType = AgentType.RISK,
            title = "Review Flagged High-Risk Order #ORD-84916",
            recommendation = "Hold shipment and require SMS phone verification for Order #ORD-84916 ($495.00)",
            reason = "AI Risk Engine detected 88/100 risk score due to VPN proxy mismatch and multiple card declines.",
            confidenceScore = 98,
            expectedImpact = "Prevents $495.00 potential chargeback loss and inventory dispute",
            suggestedAction = "Request Customer ID / Verification",
            status = RecommendationStatus.PENDING,
            targetEntityId = "ORD-84916"
        )
    )

    val sampleAuditLogs = listOf(
        AuditLog(
            id = "log_01",
            timestamp = "10:45 AM",
            agentName = "Operations Agent",
            action = "Auto-routed Order #ORD-84920 to ApexDirect UAE Warehouse",
            reason = "Customer located in Dubai, ApexDirect provides 24h express dispatch",
            result = "Tracking #AE-EX-99201948 generated successfully",
            requiresHumanApproval = false,
            isApproved = true
        ),
        AuditLog(
            id = "log_02",
            timestamp = "09:30 AM",
            agentName = "Marketing Agent",
            action = "Reallocated $25 ad spend from TikTok Ad #4 to Ad #2",
            reason = "Ad #4 CTR dropped 22% below baseline, Ad #2 converting at 4.8%",
            result = "Campaign ROAS improved from 4.2x to 5.7x",
            requiresHumanApproval = false,
            isApproved = true
        ),
        AuditLog(
            id = "log_03",
            timestamp = "08:15 AM",
            agentName = "Pricing Agent",
            action = "Localized UAE Price to AED 99.00 for Sonic Neck Massager",
            reason = "Psychological pricing threshold optimization against local competitor averages",
            result = "Conversion rate increased by +18% in GCC region",
            requiresHumanApproval = true,
            isApproved = true
        ),
        AuditLog(
            id = "log_04",
            timestamp = "07:00 AM",
            agentName = "Support Agent",
            action = "Auto-resolved shipping lookup inquiry for customer Sarah J.",
            reason = "Courier API confirmed delivery timeline match with standard policy",
            result = "Ticket resolved with 5-star customer satisfaction rating",
            requiresHumanApproval = false,
            isApproved = true
        )
    )

    // ==========================================
    // --- PAYMENT SYSTEM DEMO DATA ---
    // ==========================================

    val initialPaymentGateways = listOf(
        PaymentGateway(
            id = "gw_stripe",
            name = "Stripe Payments Global",
            provider = "Stripe",
            category = "Global Cards & Wallets",
            supportedCurrencies = listOf("USD", "AED", "SAR", "EUR", "GBP", "INR"),
            supportedCountries = listOf("US", "AE", "SA", "GB", "EU", "IN"),
            isEnabled = true,
            isLiveMode = true,
            apiKey = "pk_live_51Mxx92SnapGlobalKey987293",
            merchantId = "acct_1Mxx92SnapLive",
            webhookUrl = "https://api.within-a-snap.com/v1/webhooks/stripe",
            feePct = 2.9,
            flatFeeUSD = 0.30,
            successRatePct = 98.6,
            settlementPeriod = "T+2 Rolling Daily",
            description = "Primary international credit/debit processor with native 3DS 2.0 and Apple Pay/Google Pay.",
            features = listOf("3D Secure 2.0", "Radar AI Fraud Defense", "Apple Pay & Google Pay", "Auto Multi-Currency Settlement")
        ),
        PaymentGateway(
            id = "gw_razorpay",
            name = "Razorpay India (UPI & Cards)",
            provider = "Razorpay",
            category = "India UPI & NetBanking",
            supportedCurrencies = listOf("INR"),
            supportedCountries = listOf("IN"),
            isEnabled = true,
            isLiveMode = true,
            apiKey = "rzp_live_SnapMerchant8829104",
            merchantId = "mid_rzp_990142",
            webhookUrl = "https://api.within-a-snap.com/v1/webhooks/razorpay",
            feePct = 2.0,
            flatFeeUSD = 0.0,
            successRatePct = 99.1,
            settlementPeriod = "T+1 Daily Payout",
            description = "India's highest converting domestic payment suite for UPI Intent, QR, and Rupay/Mastercard.",
            features = listOf("Zero Fee UPI Intent", "PhonePe / GPay Native DeepLink", "Instant Refund Webhook", "18% GST Compliant Invoicing")
        ),
        PaymentGateway(
            id = "gw_cashfree",
            name = "Cashfree Payments (Auto Payouts)",
            provider = "Cashfree",
            category = "India UPI & NetBanking",
            supportedCurrencies = listOf("INR"),
            supportedCountries = listOf("IN"),
            isEnabled = true,
            isLiveMode = false,
            apiKey = "cf_test_app_id_8819203",
            merchantId = "cf_snap_in",
            webhookUrl = "https://api.within-a-snap.com/v1/webhooks/cashfree",
            feePct = 1.9,
            flatFeeUSD = 0.0,
            successRatePct = 97.8,
            settlementPeriod = "Instant 24x7 Payout",
            description = "Backup Indian gateway specialized in instant vendor payouts and bank account verification.",
            features = listOf("Instant Bank Verification", "Auto UPI Routing", "Split Payments for Suppliers")
        ),
        PaymentGateway(
            id = "gw_tabby",
            name = "Tabby / Tamara BNPL (GCC)",
            provider = "Tabby",
            category = "BNPL Installments",
            supportedCurrencies = listOf("AED", "SAR"),
            supportedCountries = listOf("AE", "SA"),
            isEnabled = true,
            isLiveMode = true,
            apiKey = "pk_live_tabby_ae_ksa_81920",
            merchantId = "mer_tabby_snap_ae",
            webhookUrl = "https://api.within-a-snap.com/v1/webhooks/tabby",
            feePct = 4.5,
            flatFeeUSD = 0.25,
            successRatePct = 96.4,
            settlementPeriod = "Weekly Batch (Tuesday)",
            description = "Buy Now Pay Later in 4 interest-free installments for United Arab Emirates and Saudi Arabia.",
            features = listOf("4 Interest-Free Splits", "+35% Average Order Value (AOV)", "Instant Credit Approval", "Zero Customer Risk")
        ),
        PaymentGateway(
            id = "gw_paypal",
            name = "PayPal Express Checkout",
            provider = "PayPal",
            category = "Global Cards & Wallets",
            supportedCurrencies = listOf("USD", "EUR", "GBP"),
            supportedCountries = listOf("US", "GB", "EU"),
            isEnabled = true,
            isLiveMode = true,
            apiKey = "client_id_snap_live_paypal_9901",
            merchantId = "paypal_snap_store",
            webhookUrl = "https://api.within-a-snap.com/v1/webhooks/paypal",
            feePct = 3.49,
            flatFeeUSD = 0.49,
            successRatePct = 97.2,
            settlementPeriod = "Instant to PayPal Wallet",
            description = "Trusted one-click checkout for North American and European buyers.",
            features = listOf("Buyer Protection Guarantee", "One-Touch Checkout", "Venmo Integration (US)")
        ),
        PaymentGateway(
            id = "gw_cod_shield",
            name = "AI COD Shield & OTP Verification",
            provider = "Within A Snap AI",
            category = "COD Engine",
            supportedCurrencies = listOf("INR", "AED", "SAR"),
            supportedCountries = listOf("IN", "AE", "SA"),
            isEnabled = true,
            isLiveMode = true,
            apiKey = "snap_ai_shield_builtin",
            merchantId = "cod_risk_engine_01",
            webhookUrl = "https://api.within-a-snap.com/v1/webhooks/cod",
            feePct = 0.0,
            flatFeeUSD = 0.15,
            successRatePct = 94.8,
            settlementPeriod = "Upon Delivery by Courier (T+3)",
            description = "Automated WhatsApp & SMS OTP confirmation engine to eliminate fake orders and reduce RTO (Return to Origin).",
            features = listOf("Automated OTP Confirmation", "RTO Risk Scoring", "Prepaid Conversion Incentive (5% Off)", "Address Autocomplete Verification")
        )
    )

    val samplePaymentTransactions = listOf(
        PaymentTransaction(
            id = "tx_994812",
            orderId = "ORD-84920",
            customerName = "Zaid Al-Mansoor",
            customerEmail = "zaid.mansoor@gmail.com",
            amount = 99.0,
            currency = "AED",
            amountUSD = 27.0,
            gatewayId = "gw_stripe",
            gatewayName = "Stripe Global",
            method = "Apple Pay (Visa •••• 4012)",
            status = PaymentTransactionStatus.SUCCESS,
            riskScore = 12,
            riskLevel = "LOW",
            countryCode = "AE",
            createdAt = "Today, 10:45 AM"
        ),
        PaymentTransaction(
            id = "tx_994811",
            orderId = "ORD-84919",
            customerName = "Rahul Sharma",
            customerEmail = "rahul.sharma@outlook.com",
            amount = 1799.0,
            currency = "INR",
            amountUSD = 21.6,
            gatewayId = "gw_razorpay",
            gatewayName = "Razorpay India",
            method = "UPI (rahul@okaxis / GPay)",
            status = PaymentTransactionStatus.SUCCESS,
            riskScore = 5,
            riskLevel = "LOW",
            countryCode = "IN",
            createdAt = "Today, 09:20 AM"
        ),
        PaymentTransaction(
            id = "tx_994810",
            orderId = "ORD-84918",
            customerName = "Fatima Al-Harbi",
            customerEmail = "f.harbi@yahoo.com",
            amount = 189.0,
            currency = "SAR",
            amountUSD = 50.4,
            gatewayId = "gw_tabby",
            gatewayName = "Tabby BNPL",
            method = "Tabby 4-Split (1st installment SAR 47.25)",
            status = PaymentTransactionStatus.SUCCESS,
            riskScore = 18,
            riskLevel = "LOW",
            countryCode = "SA",
            createdAt = "Today, 08:14 AM"
        ),
        PaymentTransaction(
            id = "tx_994809",
            orderId = "ORD-84917",
            customerName = "Michael Vance",
            customerEmail = "mvance89@gmail.com",
            amount = 34.99,
            currency = "USD",
            amountUSD = 34.99,
            gatewayId = "gw_stripe",
            gatewayName = "Stripe Global",
            method = "Mastercard 3DS •••• 8821",
            status = PaymentTransactionStatus.SUCCESS,
            riskScore = 22,
            riskLevel = "LOW",
            countryCode = "US",
            createdAt = "Yesterday, 11:30 PM"
        ),
        PaymentTransaction(
            id = "tx_994808",
            orderId = "ORD-84916",
            customerName = "Kavita Nair",
            customerEmail = "kavita.nair@rediffmail.com",
            amount = 1499.0,
            currency = "INR",
            amountUSD = 18.0,
            gatewayId = "gw_cod_shield",
            gatewayName = "AI COD Shield",
            method = "Cash on Delivery (OTP Verified: 7892)",
            status = PaymentTransactionStatus.PENDING,
            riskScore = 48,
            riskLevel = "ELEVATED",
            countryCode = "IN",
            createdAt = "Yesterday, 07:12 PM"
        ),
        PaymentTransaction(
            id = "tx_994807",
            orderId = "ORD-84912",
            customerName = "David Brown",
            customerEmail = "dbrown@techmail.com",
            amount = 49.99,
            currency = "USD",
            amountUSD = 49.99,
            gatewayId = "gw_paypal",
            gatewayName = "PayPal Express",
            method = "PayPal Wallet Balance",
            status = PaymentTransactionStatus.SUCCESS,
            riskScore = 8,
            riskLevel = "LOW",
            countryCode = "US",
            createdAt = "Yesterday, 04:50 PM"
        ),
        PaymentTransaction(
            id = "tx_994806",
            orderId = "ORD-84905",
            customerName = "Alexandre Dupont",
            customerEmail = "a.dupont@orange.fr",
            amount = 32.99,
            currency = "EUR",
            amountUSD = 35.8,
            gatewayId = "gw_stripe",
            gatewayName = "Stripe Global",
            method = "Visa •••• 1109 (3DS Failed: Auth Cancelled)",
            status = PaymentTransactionStatus.FAILED,
            riskScore = 75,
            riskLevel = "HIGH",
            countryCode = "EU",
            createdAt = "2 days ago",
            failureReason = "Customer closed 3D Secure authentication window"
        )
    )

    val sampleMerchantBalances = listOf(
        MerchantBalance(
            currency = "USD",
            symbol = "$",
            availableAmount = 4820.50,
            pendingSettlement = 1290.00,
            reservedRiskBuffer = 250.00,
            flag = "🇺🇸"
        ),
        MerchantBalance(
            currency = "AED",
            symbol = "AED",
            availableAmount = 14350.00,
            pendingSettlement = 3280.00,
            reservedRiskBuffer = 800.00,
            flag = "🇦🇪"
        ),
        MerchantBalance(
            currency = "SAR",
            symbol = "SAR",
            availableAmount = 11200.00,
            pendingSettlement = 2450.00,
            reservedRiskBuffer = 600.00,
            flag = "🇸🇦"
        ),
        MerchantBalance(
            currency = "INR",
            symbol = "₹",
            availableAmount = 184500.00,
            pendingSettlement = 42300.00,
            reservedRiskBuffer = 10000.00,
            flag = "🇮🇳"
        ),
        MerchantBalance(
            currency = "EUR",
            symbol = "€",
            availableAmount = 2140.00,
            pendingSettlement = 580.00,
            reservedRiskBuffer = 150.00,
            flag = "🇪🇺"
        )
    )

    val sampleMerchantPayouts = listOf(
        MerchantPayout(
            id = "po_8819",
            destinationAccount = "HDFC Bank •••• 9382",
            bankName = "HDFC Bank (India)",
            currency = "INR",
            amount = 120000.0,
            amountUSD = 1440.0,
            status = "COMPLETED",
            initiatedAt = "Aug 29, 2026",
            completedAt = "Aug 30, 2026 (T+1)",
            referenceNumber = "UTR-HDFC-9918273645"
        ),
        MerchantPayout(
            id = "po_8818",
            destinationAccount = "Emirates NBD •••• 4019",
            bankName = "Emirates NBD (Dubai)",
            currency = "AED",
            amount = 9500.0,
            amountUSD = 2588.0,
            status = "COMPLETED",
            initiatedAt = "Aug 27, 2026",
            completedAt = "Aug 28, 2026",
            referenceNumber = "SWIFT-ENBD-44810293"
        ),
        MerchantPayout(
            id = "po_8817",
            destinationAccount = "Mercury Bank •••• 1102",
            bankName = "Mercury Business (USA)",
            currency = "USD",
            amount = 3200.0,
            amountUSD = 3200.0,
            status = "COMPLETED",
            initiatedAt = "Aug 25, 2026",
            completedAt = "Aug 26, 2026",
            referenceNumber = "ACH-FED-77192834"
        ),
        MerchantPayout(
            id = "po_8816",
            destinationAccount = "Al Rajhi Bank •••• 7721",
            bankName = "Al Rajhi Bank (Riyadh)",
            currency = "SAR",
            amount = 7800.0,
            amountUSD = 2080.0,
            status = "PROCESSING",
            initiatedAt = "Today, 06:00 AM",
            referenceNumber = "SARIE-RJHI-551920"
        )
    )

    val sampleSmartRules = listOf(
        PaymentSmartRule(
            id = "rule_01",
            title = "Zero-Fee UPI Smart Routing",
            condition = "Customer Location is India & Cart Value <= ₹10,000",
            action = "Route directly to Razorpay UPI Intent with 0% gateway MDR",
            isEnabled = true,
            savingsEstimate = "+₹3,400 / mo"
        ),
        PaymentSmartRule(
            id = "rule_02",
            title = "5% Instant Discount on Prepaid (Anti-RTO)",
            condition = "Order is COD and Customer selects Prepaid switch",
            action = "Apply instant 5% price slash, saving 100% on courier RTO risk",
            isEnabled = true,
            savingsEstimate = "+$420 / mo"
        ),
        PaymentSmartRule(
            id = "rule_03",
            title = "GCC Tabby BNPL Auto-Trigger",
            condition = "Cart Total > AED 100 or SAR 100 in UAE / Saudi Arabia",
            action = "Highlight 'Pay in 4 installments' badge to boost conversions by 32%",
            isEnabled = true,
            savingsEstimate = "+$1,850 / mo revenue"
        ),
        PaymentSmartRule(
            id = "rule_04",
            title = "3D Secure 2.0 Dynamic Challenge",
            condition = "Transaction Risk Score > 45 or International Card",
            action = "Enforce OTP challenge to transfer 100% chargeback liability to card issuer",
            isEnabled = true,
            savingsEstimate = "Zero chargeback losses"
        )
    )

    // ==========================================
    // --- ADMIN & OWNER SYSTEM DATA ---
    // ==========================================

    val initialAdminUsers = listOf(
        AdminUser(
            id = "adm_owner_01",
            name = "Parvej Alam",
            email = "parvejalam1703@gmail.com",
            phone = "+919305868395",
            role = AdminRole.SUPER_ADMIN_OWNER,
            avatarInitials = "PA",
            secretPin = "1703",
            masterPasswordHash = "snap_owner_root_2026",
            lastLoginTimestamp = "Active Now",
            ipAddress = "103.21.144.92 (New Delhi, IN)",
            sessionToken = "SNAP-OWNER-ROOT-TOKEN-1703",
            isTwoFactorEnabled = true,
            permissions = listOf(
                "ALL_STORE_METRICS",
                "BANK_PAYOUT_RELEASE",
                "SUPPLIER_CONTRACT_OVERRIDE",
                "AUTOPILOT_KILL_SWITCH",
                "API_GATEWAY_CREDENTIALS",
                "FRAUD_SHIELD_CONTROLS",
                "CUSTOMER_DATA_EXPORT",
                "DATABASE_BACKUP_RESTORE"
            )
        ),
        AdminUser(
            id = "adm_ops_02",
            name = "Aarav Sharma",
            email = "aarav.ops@withinasnap.com",
            phone = "+919811234567",
            role = AdminRole.OPERATIONS_DIRECTOR,
            avatarInitials = "AS",
            secretPin = "2244",
            masterPasswordHash = "ops_sharma_pass",
            lastLoginTimestamp = "14m ago",
            ipAddress = "49.207.210.18 (Bengaluru, IN)",
            sessionToken = "SNAP-OPS-TOKEN-8812",
            isTwoFactorEnabled = true,
            permissions = listOf("ALL_STORE_METRICS", "SUPPLIER_CONTRACT_OVERRIDE", "FRAUD_SHIELD_CONTROLS")
        ),
        AdminUser(
            id = "adm_fin_03",
            name = "Fatima Al-Mansoor",
            email = "fatima.treasury@withinasnap.ae",
            phone = "+971501234567",
            role = AdminRole.FINANCIAL_CONTROLLER,
            avatarInitials = "FA",
            secretPin = "9900",
            masterPasswordHash = "treasury_gcc_safe",
            lastLoginTimestamp = "1h ago",
            ipAddress = "86.96.192.4 (Dubai, UAE)",
            sessionToken = "SNAP-FIN-TOKEN-7721",
            isTwoFactorEnabled = true,
            permissions = listOf("ALL_STORE_METRICS", "BANK_PAYOUT_RELEASE", "API_GATEWAY_CREDENTIALS")
        )
    )

    val initialAdminAuditTrail = listOf(
        AdminAuditEntry(
            id = "audit_01",
            timestamp = "Just now",
            actor = "Parvej Alam (Owner)",
            action = "Unlocked Owner Deep Telemetry Console",
            category = "AUTH",
            ipAddress = "103.21.144.92 (New Delhi, IN)",
            severity = "INFO",
            details = "Biometric / Master PIN 1703 authenticated successfully with 2FA token."
        ),
        AdminAuditEntry(
            id = "audit_02",
            timestamp = "12m ago",
            actor = "AI Autopilot Engine",
            action = "Auto-Synced 42 Dropship Orders to Roposo & ApexDirect",
            category = "SUPPLIER",
            ipAddress = "Internal System Daemon (AWS eu-central-1)",
            severity = "INFO",
            details = "Total COGS liability of ₹18,480 / $221.31 charged via Auto-Debit API."
        ),
        AdminAuditEntry(
            id = "audit_03",
            timestamp = "45m ago",
            actor = "Razorpay & Cashfree Webhook",
            action = "Anti-RTO COD OTP Challenge Passed (Pincode 110001)",
            category = "SECURITY",
            ipAddress = "52.66.12.98 (Mumbai, IN)",
            severity = "INFO",
            details = "Order #SNAP-8821 verified via SMS OTP before dispatch."
        ),
        AdminAuditEntry(
            id = "audit_04",
            timestamp = "2h ago",
            actor = "Parvej Alam (Owner)",
            action = "Approved Payout Withdrawal to HDFC Current Account",
            category = "FINANCE",
            ipAddress = "103.21.144.92 (New Delhi, IN)",
            severity = "WARNING",
            details = "Released ₹3,50,000 via IMPS Instant Settlement Ref: HDFC-IMPS-88910."
        ),
        AdminAuditEntry(
            id = "audit_05",
            timestamp = "4h ago",
            actor = "AI Fraud Radar",
            action = "Blocked High-Risk Anonymous Proxy Checkout",
            category = "SECURITY",
            ipAddress = "185.220.101.5 (Tor Exit Node / Amsterdam)",
            severity = "CRITICAL",
            details = "Card authorization attempt blocked. Fraud risk score 94/100."
        )
    )

    val initialSystemHealth = listOf(
        SystemHealthService(
            name = "Shopify & Custom Storefront Cloud Edge",
            category = "STOREFRONT",
            status = "HEALTHY",
            latencyMs = 24,
            endpoint = "https://within-a-snap.store/api/v2"
        ),
        SystemHealthService(
            name = "Roposo / GlowRoad Fulfillment API",
            category = "SUPPLIER",
            status = "HEALTHY",
            latencyMs = 86,
            endpoint = "https://api.roposoclout.in/v1/orders/push"
        ),
        SystemHealthService(
            name = "ApexDirect Gulf Warehouse Sync (Dubai/Riyadh)",
            category = "SUPPLIER",
            status = "HEALTHY",
            latencyMs = 112,
            endpoint = "https://gcc-erp.apexdirect.ae/webhook"
        ),
        SystemHealthService(
            name = "Razorpay & Cashfree India Payment Pipeline",
            category = "PAYMENTS",
            status = "HEALTHY",
            latencyMs = 38,
            endpoint = "https://api.razorpay.com/v1/payments"
        ),
        SystemHealthService(
            name = "Stripe Global 3DS2 Acquiring Engine",
            category = "PAYMENTS",
            status = "HEALTHY",
            latencyMs = 62,
            endpoint = "https://api.stripe.com/v1/charges"
        ),
        SystemHealthService(
            name = "Gemini AI Agent Autonomous Fleet Daemon",
            category = "AI_ENGINE",
            status = "HEALTHY",
            latencyMs = 180,
            endpoint = "https://generativelanguage.googleapis.com/v1beta"
        )
    )

    // --- 1. ANTI-FRAUD & REAL-TIME RISK REPORTS ---
    val initialFraudReports = listOf(
        FraudCheckReport(
            orderId = "SNAP-9921",
            customerName = "Rahul Sharma",
            email = "rahul.sharma92@gmail.com",
            phone = "+91 98101 23456",
            ipAddress = "103.24.88.12 (Delhi, IN)",
            riskScore = 8,
            riskLevel = RiskLevel.LOW,
            isDisposableEmail = false,
            isPhoneValid = true,
            velocityOrdersLast24h = 1,
            addressSanitized = true,
            originalAddress = "flat 402 near shiv mandir noida sec 62",
            sanitizedAddress = "Flat 402, Near Shiv Mandir, Sector 62, Noida, Uttar Pradesh, 201309",
            rtoProbabilityPct = 4.2,
            recommendedAction = "AUTO_APPROVE",
            reasons = listOf("Verified UPI Payment ID", "Clean Pincode History", "Valid Phone Format")
        ),
        FraudCheckReport(
            orderId = "SNAP-9924",
            customerName = "Tariq Al-Mansoor",
            email = "tariq.mansoor@outlook.com",
            phone = "+971 50 123 4567",
            ipAddress = "194.170.21.5 (Dubai, AE)",
            riskScore = 12,
            riskLevel = RiskLevel.LOW,
            isDisposableEmail = false,
            isPhoneValid = true,
            velocityOrdersLast24h = 1,
            addressSanitized = true,
            originalAddress = "villa 12 al barsha 2 behind mall",
            sanitizedAddress = "Villa 12, Street 14B, Al Barsha 2, Dubai, United Arab Emirates",
            rtoProbabilityPct = 3.8,
            recommendedAction = "AUTO_APPROVE",
            reasons = listOf("Tabby BNPL Pre-Scored", "Valid UAE National Address Scan")
        ),
        FraudCheckReport(
            orderId = "SNAP-9930",
            customerName = "Vikram Patil",
            email = "temp_user992@guerrillamail.com",
            phone = "+91 99999 00000",
            ipAddress = "185.220.101.5 (Tor Exit / Anonymous Proxy)",
            riskScore = 92,
            riskLevel = RiskLevel.CRITICAL,
            isDisposableEmail = true,
            isPhoneValid = false,
            velocityOrdersLast24h = 6,
            addressSanitized = false,
            originalAddress = "somewhere near station pune 411001",
            sanitizedAddress = "Incomplete Address (Missing House/Street No.)",
            rtoProbabilityPct = 94.6,
            recommendedAction = "AUTO_BLOCK",
            reasons = listOf("Disposable Email Domain Detected", "Repeated Order Velocity Spike (6/hr)", "High-Risk Tor Exit Node", "Fake Phone Sequence")
        ),
        FraudCheckReport(
            orderId = "SNAP-9932",
            customerName = "Amit Kumar Verma",
            email = "amit.verma881@yahoo.com",
            phone = "+91 88001 99281",
            ipAddress = "49.36.12.18 (Patna, IN)",
            riskScore = 54,
            riskLevel = RiskLevel.MEDIUM,
            isDisposableEmail = false,
            isPhoneValid = true,
            velocityOrdersLast24h = 2,
            addressSanitized = true,
            originalAddress = "h no 12 gali 3 kankarbagh patna",
            sanitizedAddress = "House No. 12, Gali No. 3, Kankarbagh, Patna, Bihar, 800020",
            rtoProbabilityPct = 38.0,
            recommendedAction = "REQUIRE_SMS_OTP",
            reasons = listOf("COD High-Risk Pincode Zone", "Address Auto-Sanitized with Pincode Correction")
        )
    )

    // --- 2. INVOICES, SHIPPING LABELS & PACKING SLIPS ---
    val initialInvoices = listOf(
        StoreInvoice(
            invoiceNumber = "INV-2026-00412",
            orderId = "SNAP-9921",
            invoiceDate = "01 Sep 2026",
            customerName = "Rahul Sharma",
            customerEmail = "rahul.sharma92@gmail.com",
            billingAddress = "Flat 402, Sector 62, Noida, UP 201309",
            shippingAddress = "Flat 402, Sector 62, Noida, UP 201309",
            marketCode = "IN",
            currency = "INR",
            items = listOf(
                InvoiceLineItem("Smart Ergonomic Posture Sensor", "SKU-POST-01", "HSN 9021", 1, 1524.58, 18.0, 1799.0)
            ),
            subtotal = 1524.58,
            taxAmount = 274.42,
            shippingFee = 0.0,
            grandTotal = 1799.0,
            taxIdNumber = "GSTIN: 07AAAAA0000A1Z5 (Snap Commerce Global LLP)",
            paymentMethod = "Razorpay UPI Instant",
            status = "PAID"
        ),
        StoreInvoice(
            invoiceNumber = "INV-2026-00413",
            orderId = "SNAP-9924",
            invoiceDate = "01 Sep 2026",
            customerName = "Tariq Al-Mansoor",
            customerEmail = "tariq.mansoor@outlook.com",
            billingAddress = "Villa 12, Al Barsha 2, Dubai, UAE",
            shippingAddress = "Villa 12, Al Barsha 2, Dubai, UAE",
            marketCode = "AE",
            currency = "AED",
            items = listOf(
                InvoiceLineItem("Sonic Sculpt Facial Lifting Wand", "SKU-SONIC-02", "HSN 8509", 1, 141.90, 5.0, 149.0)
            ),
            subtotal = 141.90,
            taxAmount = 7.10,
            shippingFee = 0.0,
            grandTotal = 149.0,
            taxIdNumber = "TRN / VAT: 300992188400003 (Snap Gulf FZ-LLC)",
            paymentMethod = "Tabby Split-in-4 BNPL",
            status = "PAID"
        ),
        StoreInvoice(
            invoiceNumber = "INV-2026-00414",
            orderId = "SNAP-9926",
            invoiceDate = "31 Aug 2026",
            customerName = "Jessica Miller",
            customerEmail = "jessica.m@icloud.com",
            billingAddress = "742 Evergreen Terrace, Austin, TX 78701, USA",
            shippingAddress = "742 Evergreen Terrace, Austin, TX 78701, USA",
            marketCode = "US",
            currency = "USD",
            items = listOf(
                InvoiceLineItem("Ultra-Grip Compression Knee Sleeve (Pair)", "SKU-KNEE-03", "HSN 9506", 1, 32.55, 7.5, 34.99)
            ),
            subtotal = 32.55,
            taxAmount = 2.44,
            shippingFee = 0.0,
            grandTotal = 34.99,
            taxIdNumber = "EIN: 98-7654321 (Snap North America Inc.)",
            paymentMethod = "Stripe Apple Pay",
            status = "PAID"
        )
    )

    val initialShippingLabels = listOf(
        ThermalShippingLabel(
            orderId = "SNAP-9921",
            trackingNumber = "DEL-992014-XIN",
            carrierName = "Delhivery Air Surface Prime",
            routingCode = "DEL/NOI-62",
            hubCode = "HUB-NCR-01",
            weightKg = 0.35,
            recipientName = "Rahul Sharma",
            recipientPhone = "+91 98101 23456",
            recipientAddress = "Flat 402, Near Shiv Mandir, Sector 62",
            recipientCity = "Noida, Uttar Pradesh",
            recipientPincode = "201309",
            paymentMode = "PREPAID (Razorpay UPI)",
            barcodeString = "SNAP-DEL-992014-XIN"
        ),
        ThermalShippingLabel(
            orderId = "SNAP-9924",
            trackingNumber = "SMSA-881920-AE",
            carrierName = "SMSA Express GCC Direct",
            routingCode = "DXB-BARSHA",
            hubCode = "HUB-DXB-COM",
            weightKg = 0.42,
            recipientName = "Tariq Al-Mansoor",
            recipientPhone = "+971 50 123 4567",
            recipientAddress = "Villa 12, Street 14B, Al Barsha 2",
            recipientCity = "Dubai",
            recipientPincode = "PO Box 9201",
            paymentMode = "PREPAID (Tabby)",
            barcodeString = "SNAP-SMSA-881920-AE"
        ),
        ThermalShippingLabel(
            orderId = "SNAP-9926",
            trackingNumber = "USPS-940011189956",
            carrierName = "USPS Priority Commercial Plus",
            routingCode = "TX-AUS-787",
            hubCode = "USADROP-LAX-03",
            weightKg = 0.28,
            recipientName = "Jessica Miller",
            recipientPhone = "+1 (512) 555-0199",
            recipientAddress = "742 Evergreen Terrace",
            recipientCity = "Austin, Texas",
            recipientPincode = "78701",
            paymentMode = "PREPAID (Stripe Card)",
            barcodeString = "SNAP-USPS-940011189956"
        )
    )

    val initialPackingSlips = listOf(
        WarehousePackingSlip(
            slipNumber = "PS-9921",
            orderId = "SNAP-9921",
            date = "01 Sep 2026 - 11:30 AM",
            warehouseLocation = "Baapstore / Roposo Hub NCR (Bin A-14)",
            pickerName = "Sunil Rawat (Picker #4)",
            items = listOf("Smart Ergonomic Posture Sensor" to 1, "User Manual & USB-C Cable" to 1, "Branded Thank You Card" to 1)
        ),
        WarehousePackingSlip(
            slipNumber = "PS-9924",
            orderId = "SNAP-9924",
            date = "01 Sep 2026 - 02:15 PM",
            warehouseLocation = "ApexDirect Dubai CommerCity (Bin D-08)",
            pickerName = "Zaid Al-Harbi (Picker #2)",
            items = listOf("Sonic Sculpt Facial Wand (Gold Edition)" to 1, "Protective Velvet Pouch" to 1, "Warranty Certificate" to 1)
        )
    )

    // --- 3. 1-CLICK POST-PURCHASE UPSELLS & SMART BUNDLES ---
    val initialUpsellOffers = listOf(
        UpsellOffer(
            id = "upsell_01",
            type = UpsellType.POST_PURCHASE_1CLICK,
            title = "Add Replacement Sensor Pods (Set of 2)",
            subtitle = "Keep your posture sensor running for 24+ months with spare calibrated pods.",
            originalPriceUSD = 19.99,
            discountedPriceUSD = 9.99,
            discountPct = 50,
            badgeText = "⚡ EXCLUSIVE 1-CLICK OFFER (50% OFF)",
            timerSeconds = 180,
            conversionRatePct = 22.4,
            totalRevenueGeneratedUSD = 4280.0
        ),
        UpsellOffer(
            id = "upsell_02",
            type = UpsellType.EXTENDED_WARRANTY,
            title = "2-Year VIP Replacement Warranty Protection",
            subtitle = "Zero-questions-asked express replacement if damaged, dropped, or defective.",
            originalPriceUSD = 14.99,
            discountedPriceUSD = 6.99,
            discountPct = 53,
            badgeText = "🛡️ 94% OF CUSTOMERS ADD THIS",
            timerSeconds = 180,
            conversionRatePct = 31.8,
            totalRevenueGeneratedUSD = 6120.0
        ),
        UpsellOffer(
            id = "upsell_03",
            type = UpsellType.PRIORITY_DISPATCH,
            title = "Skip The Line: VIP Same-Day Warehouse Dispatch",
            subtitle = "Your package gets packed first with dedicated courier air-express courier priority.",
            originalPriceUSD = 9.99,
            discountedPriceUSD = 3.99,
            discountPct = 60,
            badgeText = "🚀 FAST TRACK DISPATCH",
            timerSeconds = 180,
            conversionRatePct = 44.1,
            totalRevenueGeneratedUSD = 3890.0
        )
    )

    val initialSmartBundles = listOf(
        SmartBundle(
            id = "bundle_01",
            title = "The Ultimate Wellness & Posture Trio",
            targetNiche = "Fitness & Posture Correction",
            mainProductName = "Smart Ergonomic Posture Corrector",
            includedItems = listOf("Posture Sensor Device", "Extra Breathable Strap (Black)", "Orthopedic Lumbar Cushion"),
            originalPriceUSD = 89.97,
            bundlePriceUSD = 54.99,
            savingsUSD = 34.98,
            tag = "🔥 BEST VALUE (Save $35.00)"
        ),
        SmartBundle(
            id = "bundle_02",
            title = "Couples High-Performance Duo Pack",
            targetNiche = "Active Lifestyle & Fitness",
            mainProductName = "Ultra-Grip Compression Knee Sleeves",
            includedItems = listOf("Knee Sleeves Pair (His)", "Knee Sleeves Pair (Hers)", "Breathable Sports Carry Bag"),
            originalPriceUSD = 74.98,
            bundlePriceUSD = 44.99,
            savingsUSD = 29.99,
            tag = "⚡ BUY 1 GET 2ND 50% OFF"
        ),
        SmartBundle(
            id = "bundle_03",
            title = "Spa Luxe Anti-Aging Master Routine",
            targetNiche = "Beauty & Skincare Tech",
            mainProductName = "Sonic Sculpt Facial Lifting Wand",
            includedItems = listOf("Sonic Sculpt Microcurrent Wand", "Organic Hyaluronic Conduction Gel", "Silicone Cleanse Head"),
            originalPriceUSD = 119.99,
            bundlePriceUSD = 79.99,
            savingsUSD = 40.00,
            tag = "✨ SALON GRADE RESULTS"
        )
    )

    // --- 4. TRUE NET PROFIT & AD SPEND ATTRIBUTION (ROAS ENGINE) ---
    val initialAdSpendAttributions = listOf(
        AdSpendAttribution(
            id = "roas_01",
            campaignName = "Posture Corrector - Meta Lookalike 2% (GCC & USA)",
            platform = "Meta Ads",
            adSpendUSD = 1250.0,
            grossRevenueUSD = 4860.0,
            cogsUSD = 1210.0,
            gatewayFeesUSD = 145.80,
            shippingFeesUSD = 486.00,
            trueNetProfitUSD = 1768.20,
            actualRoas = 3.88,
            breakevenRoas = 1.94,
            roasMultiplierScore = 2.0,
            status = "HIGHLY_PROFITABLE"
        ),
        AdSpendAttribution(
            id = "roas_02",
            campaignName = "Sonic Facial Wand - TikTok Spark Ads Viral UGC",
            platform = "TikTok Ads",
            adSpendUSD = 980.0,
            grossRevenueUSD = 3940.0,
            cogsUSD = 920.0,
            gatewayFeesUSD = 118.20,
            shippingFeesUSD = 394.00,
            trueNetProfitUSD = 1527.80,
            actualRoas = 4.02,
            breakevenRoas = 1.82,
            roasMultiplierScore = 2.2,
            status = "HIGHLY_PROFITABLE"
        ),
        AdSpendAttribution(
            id = "roas_03",
            campaignName = "Knee Sleeves - Google Shopping High-Intent Search",
            platform = "Google Shopping",
            adSpendUSD = 620.0,
            grossRevenueUSD = 1680.0,
            cogsUSD = 510.0,
            gatewayFeesUSD = 50.40,
            shippingFeesUSD = 168.00,
            trueNetProfitUSD = 331.60,
            actualRoas = 2.71,
            breakevenRoas = 2.12,
            roasMultiplierScore = 1.28,
            status = "OPTIMAL"
        ),
        AdSpendAttribution(
            id = "roas_04",
            campaignName = "Magnetic Wireless Charger - Broad Meta Retargeting",
            platform = "Meta Ads",
            adSpendUSD = 450.0,
            grossRevenueUSD = 720.0,
            cogsUSD = 290.0,
            gatewayFeesUSD = 21.60,
            shippingFeesUSD = 72.00,
            trueNetProfitUSD = -113.60,
            actualRoas = 1.60,
            breakevenRoas = 2.25,
            roasMultiplierScore = 0.71,
            status = "UNPROFITABLE"
        )
    )

    // --- 5. MULTILINGUAL TICKETS WITH SMART MACROS ---
    val initialMultilingualTickets = listOf(
        MultilingualTicket(
            id = "multi_01",
            customerName = "Mohammed Al-Otaibi",
            customerEmail = "m.otaibi@riyadh.sa",
            orderId = "SNAP-9925",
            originalLanguage = "Arabic (العربية)",
            languageCode = "AR",
            originalQuery = "مرحباً، طلبت مصحح القوام قبل يومين وأريد معرفة رقم التتبع للشحنة ومتى تصل إلى الرياض؟",
            translatedToEnglish = "Hello, I ordered the posture corrector two days ago and I want to know the shipment tracking number and when it will arrive in Riyadh?",
            aiSuggestedReplyEnglish = "Hello Mohammed! Your order is dispatched via SMSA Express under tracking #SMSA-99214-SA. Estimated delivery in Riyadh is 03 September 2026.",
            translatedReplyNative = "مرحباً محمد! تم شحن طلبك عبر سمسا إكسبريس برقم التتبع SMSA-99214-SA. موعد التسليم المتوقع في الرياض هو 03 سبتمبر 2026.",
            smartMacroApplied = "TRACKING_INSTANT_LOOKUP",
            isResolved = false,
            sentiment = "Positive"
        ),
        MultilingualTicket(
            id = "multi_02",
            customerName = "Pooja Deshmukh",
            customerEmail = "pooja.d@gmail.com",
            orderId = "SNAP-9928",
            originalLanguage = "Hindi (हिंदी)",
            languageCode = "HI",
            originalQuery = "नमस्ते, मुझे डिलीवरी का पिनकोड बदलना है। क्या यह 400001 की जगह 400050 पर डिलीवर हो सकता है?",
            translatedToEnglish = "Hello, I want to change the delivery pincode. Can it be delivered to 400050 instead of 400001?",
            aiSuggestedReplyEnglish = "Namaste Pooja! We have updated your delivery address and routing to Pincode 400050 (Bandra West, Mumbai) with Delhivery Air. You will receive an SMS update shortly.",
            translatedReplyNative = "नमस्ते पूजा! हमने आपका डिलीवरी पता और पिनकोड 400050 (बांद्रा वेस्ट, मुंबई) दिल्लीवरी एयर के साथ अपडेट कर दिया है। आपको जल्द ही एसएमएस मिलेगा।",
            smartMacroApplied = "PINCODE_ADDRESS_FIX",
            isResolved = false,
            sentiment = "Neutral"
        ),
        MultilingualTicket(
            id = "multi_03",
            customerName = "Carlos Fernandez",
            customerEmail = "carlos.f@madrid.es",
            orderId = "SNAP-9931",
            originalLanguage = "Spanish (Español)",
            languageCode = "ES",
            originalQuery = "¿Tienen garantía si el dispositivo no funciona correctamente? ¿Cómo es el proceso de devolución?",
            translatedToEnglish = "Do you have a warranty if the device does not work properly? How is the return process?",
            aiSuggestedReplyEnglish = "Hello Carlos! All items include our 30-Day Zero-Hassle Money-Back Guarantee and 1-Year Manufacturer Warranty. If defective, we issue a free replacement label instantly.",
            translatedReplyNative = "¡Hola Carlos! Todos los artículos incluyen nuestra garantía de devolución de dinero de 30 días sin complicaciones y 1 año de garantía del fabricante. Si tiene defectos, emitimos un reemplazo gratuito al instante.",
            smartMacroApplied = "RETURN_SHIPPING_POLICY",
            isResolved = false,
            sentiment = "Inquiry"
        )
    )

    // --- 6. VIP BROADCAST CAMPAIGNS & SEGMENTS ---
    val initialCustomerSegments = listOf(
        CustomerSegmentMetric("VIP High-Spenders (LTV > $150)", 248, 264.50, "Exclusive 30% Off Private Drop", "👑"),
        CustomerSegmentMetric("Repeat Loyal Buyers (2+ Orders)", 412, 148.20, "Early Access VIP Product Launches", "⭐"),
        CustomerSegmentMetric("At-Risk (Inactive > 45 Days)", 380, 58.40, "Comeback $15 Gift Voucher", "⏰"),
        CustomerSegmentMetric("COD Abandoned Checkouts (24h)", 186, 38.00, "Extra 10% Instant UPI Prepaid Switch", "🛒"),
        CustomerSegmentMetric("All Registered Buyers", 1248, 114.80, "Global Weekend Flash Sale", "🌍")
    )

    val initialBroadcastCampaigns = listOf(
        BroadcastCampaignItem(
            id = "bc_01",
            title = "Weekend Flash Sale: 25% Off All Wellness Winners",
            channel = BroadcastChannel.WHATSAPP,
            audienceSegment = "Repeat Loyal Buyers (2+ Orders)",
            recipientCount = 412,
            messageTemplate = "Hey {FirstName}! 👋 Flash Weekend Drop is LIVE! Take 25% off our top posture & wellness devices with code SNAP25. Valid for 48h only: {StoreLink}",
            discountPromoCode = "SNAP25",
            discountPct = 25,
            sentTimestamp = "Yesterday, 06:00 PM",
            deliveredCount = 406,
            openRatePct = 89.2,
            clickRatePct = 34.8,
            recoveredRevenueUSD = 3480.0,
            status = "SENT"
        ),
        BroadcastCampaignItem(
            id = "bc_02",
            title = "COD Cart Abandonment Recovery - Extra 10% UPI Discount",
            channel = BroadcastChannel.SMS,
            audienceSegment = "COD Abandoned Checkouts (24h)",
            recipientCount = 186,
            messageTemplate = "Hi {FirstName}, complete your order within 2 hours and get EXTRA 10% OFF + Zero COD fee with Instant UPI: {CheckoutLink}",
            discountPromoCode = "PREPAID10",
            discountPct = 10,
            sentTimestamp = "Today, 10:30 AM",
            deliveredCount = 182,
            openRatePct = 94.1,
            clickRatePct = 48.6,
            recoveredRevenueUSD = 1840.0,
            status = "SENT"
        ),
        BroadcastCampaignItem(
            id = "bc_03",
            title = "VIP Early Bird Access: Sonic Sculpt 2.0 Gold Launch",
            channel = BroadcastChannel.EMAIL,
            audienceSegment = "VIP High-Spenders (LTV > $150)",
            recipientCount = 248,
            messageTemplate = "Dear {FirstName}, as a founding VIP collector, enjoy 72-hour private pre-order access to Sonic Sculpt 2.0 before global release.",
            discountPromoCode = "VIPEXCLUSIVE",
            discountPct = 30,
            sentTimestamp = "Tomorrow, 09:00 AM",
            deliveredCount = 0,
            openRatePct = 0.0,
            clickRatePct = 0.0,
            recoveredRevenueUSD = 0.0,
            status = "SCHEDULED"
        )
    )

    // --- 7. SUPPLIER PRICE SURGE & INVENTORY GUARDS ---
    val initialPriceSurgeEvents = listOf(
        SupplierPriceGuardEvent(
            id = "surge_01",
            productName = "Smart Ergonomic Posture Sensor",
            supplierName = "Roposo Clout Direct",
            oldSupplierCostINR = 320.0,
            newSupplierCostINR = 380.0,
            surgePct = 18.75,
            oldRetailPriceINR = 1699.0,
            autoAdjustedRetailPriceINR = 1799.0,
            protectedMarginPct = 44.5,
            action = PriceSurgeAction.AUTO_REPRICED_MARGIN_LOCKED,
            timestamp = "18m ago"
        ),
        SupplierPriceGuardEvent(
            id = "surge_02",
            productName = "Sonic Sculpt Facial Wand",
            supplierName = "Shenzhen SpeedCraft",
            oldSupplierCostINR = 620.0,
            newSupplierCostINR = 690.0,
            surgePct = 11.29,
            oldRetailPriceINR = 2899.0,
            autoAdjustedRetailPriceINR = 2999.0,
            protectedMarginPct = 48.0,
            action = PriceSurgeAction.AUTO_REPRICED_MARGIN_LOCKED,
            timestamp = "1h ago"
        ),
        SupplierPriceGuardEvent(
            id = "surge_03",
            productName = "Magnetic Car Phone Mount Pro",
            supplierName = "AuraDrop Global",
            oldSupplierCostINR = 210.0,
            newSupplierCostINR = 340.0,
            surgePct = 61.90,
            oldRetailPriceINR = 999.0,
            autoAdjustedRetailPriceINR = 1099.0,
            protectedMarginPct = 42.0,
            action = PriceSurgeAction.REROUTED_BACKUP_SUPPLIER,
            timestamp = "3h ago"
        )
    )

    val initialInventoryGuards = listOf(
        WarehouseInventoryGuard(
            productId = "prod_01",
            productName = "Smart Ergonomic Posture Corrector",
            primaryWarehouse = "Roposo Hub Delhi NCR",
            primaryStockUnits = 840,
            secondaryWarehouse = "Baapstore Bengaluru Hub",
            secondaryStockUnits = 620,
            isAutoRerouteActive = true,
            status = "IN_STOCK"
        ),
        WarehouseInventoryGuard(
            productId = "prod_02",
            productName = "Sonic Sculpt Facial Lifting Wand",
            primaryWarehouse = "ApexDirect Dubai CommerCity",
            primaryStockUnits = 420,
            secondaryWarehouse = "Shenzhen SpeedCraft Air Hub",
            secondaryStockUnits = 1800,
            isAutoRerouteActive = true,
            status = "IN_STOCK"
        ),
        WarehouseInventoryGuard(
            productId = "prod_03",
            productName = "Ultra-Grip Compression Knee Sleeves",
            primaryWarehouse = "USADrop Los Angeles MegaHub",
            primaryStockUnits = 38,
            secondaryWarehouse = "Spocket Dallas Central",
            secondaryStockUnits = 310,
            isAutoRerouteActive = true,
            status = "LOW_STOCK_WARNING"
        )
    )

    val sampleLocations = listOf(
        UserLocation(
            id = "loc_in_mum",
            cityName = "Mumbai",
            stateRegion = "Maharashtra",
            countryCode = "IN",
            countryFlag = "🇮🇳",
            postalCode = "400001",
            currencyCode = "INR",
            currencySymbol = "₹",
            nearbyHubName = "Bhiwandi West Logistics Hub",
            distanceKm = 14,
            estimatedDeliveryHours = 24,
            expressDeliveryAvailable = true,
            codAvailable = true,
            taxRatePct = 18.0,
            localStockAvailabilityPct = 98,
            localDeliveryCarrier = "Bluedart / Delhivery Express"
        ),
        UserLocation(
            id = "loc_in_del",
            cityName = "Delhi NCR",
            stateRegion = "Delhi / Gurugram",
            countryCode = "IN",
            countryFlag = "🇮🇳",
            postalCode = "110001",
            currencyCode = "INR",
            currencySymbol = "₹",
            nearbyHubName = "Gurugram North Fulfillment Center",
            distanceKm = 18,
            estimatedDeliveryHours = 24,
            expressDeliveryAvailable = true,
            codAvailable = true,
            taxRatePct = 18.0,
            localStockAvailabilityPct = 95,
            localDeliveryCarrier = "Shadowfax / Ekart Logistics"
        ),
        UserLocation(
            id = "loc_in_blr",
            cityName = "Bengaluru",
            stateRegion = "Karnataka",
            countryCode = "IN",
            countryFlag = "🇮🇳",
            postalCode = "560001",
            currencyCode = "INR",
            currencySymbol = "₹",
            nearbyHubName = "Electronic City South Depot",
            distanceKm = 11,
            estimatedDeliveryHours = 18,
            expressDeliveryAvailable = true,
            codAvailable = true,
            taxRatePct = 18.0,
            localStockAvailabilityPct = 96,
            localDeliveryCarrier = "XpressBees Hyperlocal"
        ),
        UserLocation(
            id = "loc_in_hyd",
            cityName = "Hyderabad",
            stateRegion = "Telangana",
            countryCode = "IN",
            countryFlag = "🇮🇳",
            postalCode = "500001",
            currencyCode = "INR",
            currencySymbol = "₹",
            nearbyHubName = "Shamshabad Air Freight Terminal",
            distanceKm = 22,
            estimatedDeliveryHours = 24,
            expressDeliveryAvailable = true,
            codAvailable = true,
            taxRatePct = 18.0,
            localStockAvailabilityPct = 94,
            localDeliveryCarrier = "Delhivery Surface & Air"
        ),
        UserLocation(
            id = "loc_ae_dxb",
            cityName = "Dubai",
            stateRegion = "Dubai Emirate",
            countryCode = "AE",
            countryFlag = "🇦🇪",
            postalCode = "00000",
            currencyCode = "AED",
            currencySymbol = "AED",
            nearbyHubName = "Jebel Ali Freezone Express Hub",
            distanceKm = 8,
            estimatedDeliveryHours = 12,
            expressDeliveryAvailable = true,
            codAvailable = true,
            taxRatePct = 5.0,
            localStockAvailabilityPct = 99,
            localDeliveryCarrier = "Aramex / Careem Express"
        ),
        UserLocation(
            id = "loc_sa_ruh",
            cityName = "Riyadh",
            stateRegion = "Riyadh Province",
            countryCode = "SA",
            countryFlag = "🇸🇦",
            postalCode = "11564",
            currencyCode = "SAR",
            currencySymbol = "SAR",
            nearbyHubName = "King Khalid Cargo City Depot",
            distanceKm = 15,
            estimatedDeliveryHours = 24,
            expressDeliveryAvailable = true,
            codAvailable = true,
            taxRatePct = 15.0,
            localStockAvailabilityPct = 96,
            localDeliveryCarrier = "SMSA Express / NAQEL"
        ),
        UserLocation(
            id = "loc_us_nyc",
            cityName = "New York",
            stateRegion = "New York (NY)",
            countryCode = "US",
            countryFlag = "🇺🇸",
            postalCode = "10001",
            currencyCode = "USD",
            currencySymbol = "$",
            nearbyHubName = "Newark Liberty Logistics Hub",
            distanceKm = 19,
            estimatedDeliveryHours = 24,
            expressDeliveryAvailable = true,
            codAvailable = false,
            taxRatePct = 8.875,
            localStockAvailabilityPct = 97,
            localDeliveryCarrier = "USPS Priority / FedEx Home"
        ),
        UserLocation(
            id = "loc_gb_lon",
            cityName = "London",
            stateRegion = "Greater London",
            countryCode = "GB",
            countryFlag = "🇬🇧",
            postalCode = "SW1A 1AA",
            currencyCode = "GBP",
            currencySymbol = "£",
            nearbyHubName = "Heathrow Express Sorting Center",
            distanceKm = 16,
            estimatedDeliveryHours = 24,
            expressDeliveryAvailable = true,
            codAvailable = false,
            taxRatePct = 20.0,
            localStockAvailabilityPct = 95,
            localDeliveryCarrier = "Royal Mail 24 Tracked"
        )
    )
}


