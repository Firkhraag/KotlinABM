package model

import kotlin.math.*

class Agent(val isMale: Boolean, var age: Int) {

    var isMother = false
    var isOnMotherLeave = false

    var month = (1..12).random()

    var connectedWorkAgents = arrayListOf<Int>()

    var healthStatus = when (age) {
        in 0..2 -> if ((0..99).random() < 2) 1 else 0
        in 3..6 -> if ((0..999).random() < 8) 1 else 0
        in 7..14 -> if ((0..999).random() < 5) 1 else 0
        else -> if ((0..999).random() < 2) 1 else 0
    }

    var infectionType = if (healthStatus == 1) {
        when ((0..99).random()) {
            in 0..59 -> "RV" // 60%
            in 60..89 -> "AdV" // 30%
            else -> "PIV"// 10%
        }
    } else "none"

    var daysImmune = if (healthStatus == 2) 1 else 0

    var fluAImmunity = false
    var fluBImmunity = false
    var RVImmunity = false
    var RSVImmunity = false
    var AdVImmunity = false
    var PIVImmunity = false
    var CoVImmunity = false

    var RVImmunityDays = 0
    var RSVImmunityDays = 0
    var PIVImmunityDays = 0
    var AdVImmunityDays = 0
    var CoVImmunityDays = 0

//    var fluAImmunity = (0..9).random() == 0
//    var fluBImmunity = (0..9).random() == 0
//    var RVImmunity = false
//    var RSVImmunity = false
//    var AdVImmunity = false
//    var PIVImmunity = false
//    var CoVImmunity = false
//
//    var RVImmunityDays = 0
//    var RSVImmunityDays = 0
//    var PIVImmunityDays = 0
//    var AdVImmunityDays = 0
//    var CoVImmunityDays = 0

    private fun willBeInfected(): Int {
        return if (age < 16) {
            when(infectionType) {
                "fluA" -> 9
                "fluB" -> 8
                "RV" -> 11
                "RSV" -> 9
                "AdV" -> 9
                "PIV" -> 8
                "CoV" -> 8
                else -> 0
            }
        } else {
            when(infectionType) {
                "fluA" -> 5
                "fluB" -> 4
                "RV" -> 10
                "RSV" -> 7
                "AdV" -> 8
                "PIV" -> 7
                "CoV" -> 7
                else -> 0
            }
        }
    }

    private fun willHaveIncubationPeriod(): Int {
        return when (infectionType) {
            "fluA" -> 1
            "fluB" -> 1
            "RV" -> 2
            "RSV" -> 4
            "AdV" -> 6
            "PIV" -> 3
            "CoV" -> 3
            else -> 0
        }
    }

    // 25% chance of being asymptomatic
    private fun willBeAsymptomatic(): Boolean {
        return (0..99).random() >= 75
    }

    // Isolation and registration
    fun shouldStayAtHome(): Boolean {
        if (isAsymptomatic) {
            return false
        }
        return when (daysInfected) {
            1 -> when (age) {
                in 0..7 -> ((0..999).random() < 304)
                in 8..17 -> ((0..999).random() < 203)
                else -> ((0..9).random() == 0)
            }
            2 -> when (age) {
                in 0..7 -> ((0..999).random() < 575)
                in 8..17 -> ((0..999).random() < 498)
                else -> ((0..999).random() < 333)
            }
            3 -> when (age) {
                in 0..7 -> ((0..999).random() < 324)
                in 8..17 -> ((0..999).random() < 375)
                else -> ((0..999).random() < 167)
            }
            else -> false
        }
    }

    // Mean viral load
    private fun willBeMeanViralLoad(): Double {
        return when(infectionType) {
            "fluA" -> {
                when(age) {
                    in 0..2 -> 4.6
                    in 3..15 -> 3.45
                    else -> 2.3
                }
            }
            "fluB" -> {
                when(age) {
                    in 0..2 -> 4.7
                    in 3..15 -> 3.53
                    else -> 2.35
                }
            }
            "RV" -> {
                when(age) {
                    in 0..2 -> 3.5
                    in 3..15 -> 2.63
                    else -> 1.75
                }
            }
            "RSV" -> {
                when(age) {
                    in 0..2 -> 6.0
                    in 3..15 -> 4.5
                    else -> 2.25
                }
            }
            "AdV" -> {
                when(age) {
                    in 0..2 -> 4.1
                    in 3..15 -> 3.08
                    else -> 2.05
                }
            }
            "PIV" -> {
                when(age) {
                    in 0..2 -> 4.7
                    in 3..15 -> 3.53
                    else -> 2.35
                }
            }
            "CoV" -> {
                when(age) {
                    in 0..2 -> 4.93
                    in 3..15 -> 3.7
                    else -> 2.47
                }
            }
            else -> 0.0
        }
    }

    private fun findViralLoad(): Double {
        return min(12.0, if (daysInfected < 1) {
            if (incubationPeriod == 1) {
                meanViralLoad / 2
            } else {
                val k = meanViralLoad / (incubationPeriod - 1)
                val b = k * (incubationPeriod - 1)
                k * daysInfected + b
            }
        } else {
            val k = 2 * meanViralLoad / (1 - shouldBeInfected)
            val b = -k * shouldBeInfected
            if (isAsymptomatic) {
                1/2 * (k * daysInfected + b)
            } else {
                k * daysInfected + b
            }
        })
    }

//    fun willHavePneumonia(): Boolean {
//        return when (infectionType) {
//            "AdV" -> (0..99).random() < 16
//            "CoV" -> (0..99).random() < 4
//            "fluA" -> (0..99).random() < 11
//            "fluB" -> (0..99).random() < 7
//            "PIV" -> (0..99).random() < 22
//            "RV" -> (0..99).random() < 4
//            "RSV" -> (0..99).random() < 44
//            else -> false
//        }
//    }

//    val pneumoniaMultiplier = 1.3

    var isAsymptomatic = willBeAsymptomatic()
    var incubationPeriod = willHaveIncubationPeriod()
    var shouldBeInfected = willBeInfected()
    var daysInfected = if (healthStatus == 1) ((1 - incubationPeriod)..shouldBeInfected).random() else 0
    var isStayingHomeWhenInfected = shouldStayAtHome()
    var meanViralLoad = willBeMeanViralLoad()

    fun updateHealthParameters() {
        isAsymptomatic = willBeAsymptomatic()
        incubationPeriod = willHaveIncubationPeriod()
        shouldBeInfected = willBeInfected()
        daysInfected = 1 - incubationPeriod
        isStayingHomeWhenInfected = shouldStayAtHome()
        meanViralLoad = willBeMeanViralLoad()
    }

    // Ig Multipliers
    // Multiplier for 19-29 compared to previous age group: 1.1347
    // Multiplier for 30-59 compared to previous age group: 1.09
    // Multiplier for 60+ compared to previous age group: 0.9
    private fun getIgGLevel(): Double {
        val rand = java.util.Random()
        return when (age) {
            0 -> {
                when (month) {
                    1 -> min(1480.0, max(399.0,913.85 + rand.nextGaussian() * 262.19))
                    in 2..4 -> min(981.0, max(217.0,429.5 + rand.nextGaussian() * 145.59))
                    in 5..7 -> min(1110.0, max(270.0,482.43 + rand.nextGaussian() * 236.8))
                    else -> min(977.0, max(242.0,536.79 + rand.nextGaussian() * 186.62))
                }
            }
            1 -> min(1260.0, max(389.0,726.79 + rand.nextGaussian() * 238.61))
            2 -> min(1970.0, max(486.0,786.41 + rand.nextGaussian() * 249.14))
            in 3..5 -> min(1120.0, max(457.0,823.19 + rand.nextGaussian() * 164.19))
            in 6..8 -> min(1580.0, max(483.0,982.86 + rand.nextGaussian() * 255.53))
            in 9..11 -> min(2290.0, max(642.0,1016.12 + rand.nextGaussian() * 322.27))
            in 12..16 -> min(1610.0, max(636.0,1123.56 + rand.nextGaussian() * 203.83))
            in 17..18 -> min(2430.0, max(688.0,1277.20 + rand.nextGaussian() * 361.89))
            in 19..29 -> min(2757.3, max(780.6,1449.2 + rand.nextGaussian() * 410.63))
            in 30..59 -> min(3005.46, max(850.85,1579.63 + rand.nextGaussian() * 447.59))
            else -> min(2704.91, max(765.77,1421.67 + rand.nextGaussian() * 369.57))
        }
    }

    private fun getIgALevel(): Double {
        val rand = java.util.Random()
        return when (age) {
            0 -> {
                when (month) {
                    1 -> min(8.75, max(6.67,6.77 + rand.nextGaussian() * 0.45))
                    in 2..4 -> min(24.6, max(6.67,9.58 + rand.nextGaussian() * 5.16))
                    in 5..7 -> min(53.0, max(6.67,17.23 + rand.nextGaussian() * 9.77))
                    else -> min(114.0, max(6.68,23.63 + rand.nextGaussian() * 12.37))
                }
            }
            1 -> min(103.0, max(13.1,34.09 + rand.nextGaussian() * 17.1))
            2 -> min(135.0, max(6.67,48.87 + rand.nextGaussian() * 24.52))
            in 3..5 -> min(192.0, max(35.7,62.75 + rand.nextGaussian() * 34.05))
            in 6..8 -> min(276.0, max(44.8,97.38 + rand.nextGaussian() * 49.66))
            in 9..11 -> min(262.0, max(32.6,102.27 + rand.nextGaussian() * 47.05))
            in 12..16 -> min(305.0, max(36.4,112.16 + rand.nextGaussian() * 47.51))
            in 17..18 -> min(385.0, max(46.3,179.21 + rand.nextGaussian() * 89.92))
            in 19..29 -> min(436.86, max(52.54,203.35 + rand.nextGaussian() * 102.0))
            in 30..59 -> min(476.18, max(57.26,221.65 + rand.nextGaussian() * 111.18))
            else -> min(428.56, max(51.5,199.49 + rand.nextGaussian() * 100.01))
        }
    }

    private fun getIgMLevel(): Double {
        val rand = java.util.Random()
        return when (age) {
            0 -> {
                when (month) {
                    1 -> min(50.9, max(5.1,16.89 + rand.nextGaussian() * 8.87))
                    in 2..4 -> min(68.5, max(15.2,34.21 + rand.nextGaussian() * 13.55))
                    in 5..7 -> min(130.0, max(26.9,69.05 + rand.nextGaussian() * 29.73))
                    else -> min(162.0, max(24.2,73.42 + rand.nextGaussian() * 35.76))
                }
            }
            1 -> min(195.0, max(38.6,115.25 + rand.nextGaussian() * 41.63))
            2 -> min(236.0, max(42.7,104.66 + rand.nextGaussian() * 40.55))
            in 3..5 -> min(198.0, max(58.7,115.60 + rand.nextGaussian() * 39.24))
            in 6..8 -> min(242.0, max(50.3,108.05 + rand.nextGaussian() * 41.27))
            in 9..11 -> min(213.0, max(37.4,104.95 + rand.nextGaussian() * 43.68))
            in 12..16 -> min(197.0, max(42.4,119.16 + rand.nextGaussian() * 39.31))
            in 17..18 -> min(323.0, max(60.7,130.60 + rand.nextGaussian() * 64.32))
            in 19..29 -> min(366.51, max(68.88,148.19 + rand.nextGaussian() * 72.98))
            in 30..59 -> min(399.5, max(75.08,161.53 + rand.nextGaussian() * 79.55))
            else -> min(359.55, max(67.57,145.38 + rand.nextGaussian() * 71.6))
        }
    }

    private fun getIgLevel(): Double {
        val igGMax = 3005.46
        val igGMin = 217.0
        val igAMax = 476.18
        val igAMin = 6.67
        val igMMax = 399.5
        val igMMin = 5.1
        return (getIgGLevel() + getIgALevel() + getIgMLevel() - igGMin - igAMin - igMMin) / (igGMax + igAMax + igMMax - igGMin - igAMin - igMMin)
    }

    var infectivity = 0.0
    fun findInfectivity() {
        infectivity = findViralLoad() / 12.0
    }

    var fluASusceptibility = 0.0
    var fluBSusceptibility = 0.0
    var RVSusceptibility = 0.0
    var RSVSusceptibility = 0.0
    var AdVSusceptibility = 0.0
    var PIVSusceptibility = 0.0
    var CoVSusceptibility = 0.0
    fun findSusceptibility(bMap: Map<String, Double>) {
        fluASusceptibility = 2 / (1 + exp((bMap["fluA"] ?: error("Required")) * getIgLevel()))
        fluBSusceptibility = 2 / (1 + exp((bMap["fluB"] ?: error("Required")) * getIgLevel()))
        RVSusceptibility = 2 / (1 + exp((bMap["RV"] ?: error("Required")) * getIgLevel()))
        RSVSusceptibility = 2 / (1 + exp((bMap["RSV"] ?: error("Required")) * getIgLevel()))
        AdVSusceptibility = 2 / (1 + exp((bMap["AdV"] ?: error("Required")) * getIgLevel()))
        PIVSusceptibility = 2 / (1 + exp((bMap["PIV"] ?: error("Required")) * getIgLevel()))
        CoVSusceptibility = 2 / (1 + exp((bMap["CoV"] ?: error("Required")) * getIgLevel()))
    }

    // Data from census
    var activityStatus: Int = when (age) {
        // Ясли
        in 0..2 -> if ((0..99).random() < 23) 1 else 0
        // Детсад
        in 3..5 -> if ((0..99).random() < 83) 1 else 0
        6 -> 1
        // Школа
        in 7..15 -> 2
        in 16..17 -> 2
        // 10% в колледж, 40% в школе, 50% - в универе
        // Школа / универ
        18 -> if ((0..1).random() == 0) 2 else 3
        // 50% - универ, 50% - работа
        in 19..22 -> if ((0..1).random() == 0) 3 else 4
        // Магистратура / работа
        in 23..24 -> if ((0..99).random() < 82) 4 else {
            if ((0..9).random() < 6) 3 else 0
        }
        // Работа
        in 25..64 -> {
            if (isMale) when (age) {
                in 25..29 -> if ((0..99).random() < 82) 4 else 0
                in 30..39 -> if ((0..99).random() < 95) 4 else 0
                in 40..49 -> if ((0..99).random() < 94) 4 else 0
                in 50..59 -> if ((0..99).random() < 88) 4 else 0
                else -> if ((0..99).random() < 51) 4 else 0
            } else when (age) {
                in 23..29 -> if ((0..99).random() < 74) 4 else 0
                in 30..39 -> if ((0..99).random() < 85) 4 else 0
                in 40..49 -> if ((0..99).random() < 89) 4 else 0
                in 50..59 -> if ((0..99).random() < 70) 4 else 0
                else -> if ((0..99).random() < 29) 5 else 0
            }
        }
        else -> 0
    }

}