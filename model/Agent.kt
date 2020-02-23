package model

import kotlin.math.*

class Agent(val isMale: Boolean, var age: Int) {

    var isMother = false
    var isOnMotherLeave = false

    var isStayingHomeWhenNoOtherActivityAvailable = false

    var month = (1..12).random()

    var connectedAgents = arrayListOf<Int>()
    var connectedWorkAgents = arrayListOf<Int>()
//    var friends = arrayListOf<Agent>()

    var healthStatus = when (age) {
        in 0..2 -> if ((0..99).random() == 0) 1 else 0
        in 3..6 -> if ((0..999).random() < 5) 1 else 0
        in 7..14 -> if ((0..999).random() < 3) 1 else 0
        else -> if ((0..999).random() < 2) 1 else 0
    }

//    var healthStatus = when (age) {
//        in 0..2 -> if ((0..99).random() == 0) 1 else 0
//        in 3..6 -> if ((0..999).random() < 2) 1 else 0
//        in 7..14 -> if ((0..999).random() < 1) 1 else 0
//        else -> if ((0..999).random() < 1) 1 else 0
//    }

    var infectionType = if (healthStatus == 1) {
        if ((0..99).random() < 50) {
            "RV"
//            "RSV"
        } else {
            "RSV"
        }
    } else {
        "none"
    }

//    var healthStatus = when (age) {
//        in 0..2 -> if ((0..999).random() < 12) 1 else 0
//        in 3..6 -> if ((0..999).random() < 6) 1 else 0
//        in 7..14 -> if ((0..999).random() < 3) 1 else 0
//        else -> if ((0..999).random() < 2) 1 else 0
//    }

//    var healthStatus = 0

    var daysImmune = if (healthStatus == 2) 1 else 0

    var fluAImmunity = false
    var fluBImmunity = false
    var RVImmunity = false
    var RSVImmunity = false

    var RVImmunityDays = 0
    var RSVImmunityDays = 0

//    fun getTypeOfInfection(globalMonth: Int): String {
//        when (age) {
//            0 -> when ((0..99).random()) {
//                in 0..28 -> "FluA" // 29%
//                in 29..46 -> "FluB" // 18%
//                in 29..46 -> "FluB" // 18%
//                else -> "BoV" // 18%
//            }
//        }
//    }

//    fun getTypeOfInfection(globalMonth: Int): String {
//        return "none"
//    }

    fun willBeInfected(): Int {
        val rand = java.util.Random()
        return if (healthStatus == 1) {
            when (age) {
                in 0..1 -> when(infectionType) {
                    "BoV" -> (50.0 + rand.nextGaussian() * 5.0).roundToInt()
                    "RV" -> (12.1 + rand.nextGaussian() * 1.5).roundToInt()
                    "fluA" -> (9.5 + rand.nextGaussian() * 1.5).roundToInt()
                    "fluB" -> (8.5 + rand.nextGaussian() * 1.0).roundToInt()
                    "MPV" -> (11.1 + rand.nextGaussian() * 1.5).roundToInt()
                    "RSV" -> (9.0 + rand.nextGaussian() * 1.5).roundToInt()
                    "PIV" -> (8.2 + rand.nextGaussian() * 1.5).roundToInt()
                    "AdV" -> (7.0 + rand.nextGaussian() * 0.5).roundToInt()
                    else -> 7
                }
                in 2..15 -> when(infectionType) {
                    "BoV" -> (40.0 + rand.nextGaussian() * 5.0).roundToInt()
                    "RV" -> (11.1 + rand.nextGaussian() * 1.5).roundToInt()
                    "fluA" -> (8.0 + rand.nextGaussian() * 1.5).roundToInt()
                    "fluB" -> (7.0 + rand.nextGaussian() * 1.0).roundToInt()
                    "MPV" -> (10.5 + rand.nextGaussian() * 1.5).roundToInt()
                    "RSV" -> (6.5 + rand.nextGaussian() * 1.5).roundToInt()
                    "PIV" -> (7.9 + rand.nextGaussian() * 1.5).roundToInt()
                    "AdV" -> (6.5 + rand.nextGaussian() * 0.5).roundToInt()
                    else -> 7
                }
                else -> when(infectionType) {
                    "RV" -> (10.1 + rand.nextGaussian() * 1.5).roundToInt()
                    "fluA" -> (4.8 + rand.nextGaussian() * 0.5).roundToInt()
                    "fluB" -> (4.0 + rand.nextGaussian() * 0.5).roundToInt()
                    "MPV" -> (10.0 + rand.nextGaussian() * 1.5).roundToInt()
                    "RSV" -> (8.4 + rand.nextGaussian() * 1.5).roundToInt()
                    "PIV" -> (7.7 + rand.nextGaussian() * 1.5).roundToInt()
                    "AdV" -> (6.0 + rand.nextGaussian() * 0.5).roundToInt()
                    else -> 7
                }
            }
        } else 0
    }

    fun willHaveIncubationPeriod(): Int {
        if (healthStatus == 0) {
            return 0
        }
        return when (infectionType) {
            "AdV" -> if ((0..1).random() == 0) 5 else 6
//            "CoV" -> if ((0..1).random() == 0) 2 else 3
            "fluA" -> 1
            "fluB" -> 0
            "PIV" -> 2
            "RV" -> if ((0..1).random() == 0) 1 else 2
            "RSV" -> if ((0..1).random() == 0) 4 else 3
//            "EV" -> 5
            "BoV" -> 4
            "MPV" -> if ((0..1).random() == 0) 4 else 5
            else -> 0
        }
    }

    // 25% chance of being asymptomatic
    private fun willBeAsymptomatic(): Boolean {
        return if (healthStatus == 1) {
            (0..99).random() >= 75
        } else false
    }

    fun shouldStayAtHome(): Boolean {
        if ((isAsymptomatic) || (healthStatus == 0)) {
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

    private fun willBeMeanViralLoad(): Double {
        val rand = java.util.Random()
        if (healthStatus == 0) {
            return 0.0
        }
        return when(infectionType) {
            "AdV" -> {
                when(age) {
                    in 0..2 -> 4.1 + rand.nextGaussian() * 0.5
                    in 3..15 -> 3.075 + rand.nextGaussian() * 0.5
                    else -> 2.05 + rand.nextGaussian() * 0.5
                }
            }
//            "CoV" -> {
//                when(age) {
//                    in 0..2 -> 4.93 + rand.nextGaussian() * 0.5
//                    in 3..15 -> 3.6975 + rand.nextGaussian() * 0.5
//                    else -> 2.465 + rand.nextGaussian() * 0.5
//                }
//            }
            "BoV" -> {
                when(age) {
                    in 0..2 -> 4.1 + rand.nextGaussian() * 0.5
                    in 3..15 -> 3.075 + rand.nextGaussian() * 0.5
                    else -> 2.05 + rand.nextGaussian() * 0.5
                }
            }
            "MPV" -> {
                when(age) {
                    in 0..2 -> 5.0 + rand.nextGaussian() * 0.5
                    in 3..15 -> 3.6975 + rand.nextGaussian() * 0.5
                    else -> 2.465 + rand.nextGaussian() * 0.5
                }
            }
            "fluA" -> {
                when(age) {
                    in 0..2 -> 4.6 + rand.nextGaussian() * 0.5
                    in 3..15 -> 3.45 + rand.nextGaussian() * 0.5
                    else -> 2.3 + rand.nextGaussian() * 0.5
                }
            }
            "fluB" -> {
                when(age) {
                    in 0..2 -> 4.7 + rand.nextGaussian() * 0.5
                    in 3..15 -> 3.525 + rand.nextGaussian() * 0.5
                    else -> 2.35 + rand.nextGaussian() * 0.5
                }
            }
            "PIV" -> {
                when(age) {
                    in 0..2 -> 4.7 + rand.nextGaussian() * 0.5
                    in 3..15 -> 3.525 + rand.nextGaussian() * 0.5
                    else -> 2.35 + rand.nextGaussian() * 0.5
                }
            }
//            "EV" -> {
//                when(age) {
//                    in 0..2 -> 3.9 + rand.nextGaussian() * 0.5
//                    in 3..15 -> 2.925 + rand.nextGaussian() * 0.5
//                    else -> 1.95 + rand.nextGaussian() * 0.5
//                }
//            }
            "RV" -> {
                when(age) {
                    in 0..2 -> 3.5 + rand.nextGaussian() * 0.5
                    in 3..15 -> 2.625 + rand.nextGaussian() * 0.5
                    else -> 1.75 + rand.nextGaussian() * 0.5
                }
            }
            "RSV" -> {
                when(age) {
                    in 0..2 -> 6.0 + rand.nextGaussian() * 0.5
                    in 3..15 -> 4.5 + rand.nextGaussian() * 0.5
                    else -> 2.25 + rand.nextGaussian() * 0.5
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
                val k = -(meanViralLoad / 2) / ((1 - incubationPeriod) - (1 - incubationPeriod) / 2)
                val b = (meanViralLoad / 2) * (1 - incubationPeriod) / ((1 - incubationPeriod) - (1 - incubationPeriod) / 2)
                k * daysInfected + b
            }
        } else {
            val k = -(meanViralLoad) / (shouldBeInfected - (1 + shouldBeInfected) / 2)
            val b = (meanViralLoad) * shouldBeInfected / (shouldBeInfected - (1 + shouldBeInfected) / 2)
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

    private var isAsymptomatic = willBeAsymptomatic()
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

//    var infectionType = "none"
//    private var isAsymptomatic = false
//    var meanViralLoad = 0.0
//    var incubationPeriod = 0
//    var daysInfected = 0
//    var isStayingHomeWhenInfected = false
//    var shouldBeInfected = 0
//
//    fun initializeHealthParameters(globalMonth: Int) {
//        infectionType = getTypeOfInfection(globalMonth)
//        isAsymptomatic = willBeAsymptomatic()
//        isStayingHomeWhenInfected = shouldStayAtHome()
//        incubationPeriod = willHaveIncubationPeriod()
//        shouldBeInfected = willBeInfected()
//        daysInfected = if (healthStatus == 1) ((1 - incubationPeriod)..shouldBeInfected).random() else 0
//        meanViralLoad = willBeMeanViralLoad()
//    }

//    fun updateHealthParameters(globalMonth: Int) {
//        infectionType = getTypeOfInfection(globalMonth)
//        isAsymptomatic = willBeAsymptomatic()
//        isStayingHomeWhenInfected = shouldStayAtHome()
//        incubationPeriod = willHaveIncubationPeriod()
//        shouldBeInfected = willBeInfected()
//        daysInfected = 1 - incubationPeriod
//        meanViralLoad = willBeMeanViralLoad()
//    }

    //1.1347
    //1.09
    //0.9


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
            in 30..59 -> min(3005.46, max(850.85,1579.628 + rand.nextGaussian() * 447.59))
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
            else -> min(428.56, max(51.5,199.485 + rand.nextGaussian() * 100.01))
        }
    }

//    private fun getIgMLevel(): Double {
//        val rand = java.util.Random()
//        return when (age) {
//            0 -> {
//                when (month) {
//                    1 -> min(29.6, max(17.3,18.5 + rand.nextGaussian() * 3.5))
//                    in 2..6 -> min(145.0, max(18.4,57.3 + rand.nextGaussian() * 37.4))
//                    in 7..9 -> min(146.0, max(26.4,68.7 + rand.nextGaussian() * 38.9))
//                    else -> min(180.0, max(23.5,86.1 + rand.nextGaussian() * 40.3))
//                }
//            }
//            1 -> min(201.0, max(25.6,98.3 + rand.nextGaussian() * 40.3))
//            2 -> min(199.0, max(36.0,92.5 + rand.nextGaussian() * 33.9))
//            3 -> min(188.0, max(26.1,86.1 + rand.nextGaussian() * 35.3))
//            in 4..5 -> min(207.0, max(33.3,105.8 + rand.nextGaussian() * 40.8))
//            in 6..7 -> min(220.0, max(30.5,97.6 + rand.nextGaussian() * 42.9))
//            in 8..9 -> min(257.0, max(33.7,93.9 + rand.nextGaussian() * 49.3))
//            in 10..11 -> min(187.0, max(30.0,102.4 + rand.nextGaussian() * 38.8))
//            in 12..13 -> min(206.0, max(44.0,120.9 + rand.nextGaussian() * 43.8))
//            in 14..15 -> min(205.0, max(33.0,99.7 + rand.nextGaussian() * 49.7))
//            else -> min(198.5, max(75.0,130.9 + rand.nextGaussian() * 44.5))
//        }
//    }

    fun getIgLevel(): Double {
//        val igGMax = 1820.0
        val igGMax = 3005.46
        val igGMin = 217.0
        val igAMax = 476.18
        val igAMin = 6.67
//        val igMMax = 257.0
        return (getIgGLevel() + getIgALevel() - igGMin - igAMin) / (igGMax + igAMax - igGMin - igAMin)
    }

    var infectivity = 0.0
    fun findInfectivity(a: Double) {
        infectivity = a * findViralLoad() / 12.0
    }

    var fluASusceptibility = 0.0
    var fluBSusceptibility = 0.0
    var RVSusceptibility = 0.0
    var RSVSusceptibility = 0.0
    fun findSusceptibility(bMap: Map<String, Double>) {
//        fluASusceptibility = (bMap["fluA"] ?: 0.0) * getIgLevel() + 1.0
//        fluBSusceptibility = (bMap["fluB"] ?: 0.0) * getIgLevel() + 1.0
//        RVSusceptibility = (bMap["RV"] ?: 0.0) * getIgLevel() + 1.0
//        RSVSusceptibility = (bMap["RSV"] ?: 0.0) * getIgLevel() + 1.0
        fluASusceptibility = 2 / (1 + exp((bMap["fluA"] ?: 0.0) * getIgLevel()))
        fluBSusceptibility = 2 / (1 + exp((bMap["fluB"] ?: 0.0) * getIgLevel()))
        RVSusceptibility = 2 / (1 + exp((bMap["RV"] ?: 0.0) * getIgLevel()))
        RSVSusceptibility = 2 / (1 + exp((bMap["RSV"] ?: 0.0) * getIgLevel()))
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
        18 -> if ((0..1).random() == 0) 2 else 4
        // 50% - универ, 50% - работа
        in 19..22 -> if ((0..1).random() == 0) 4 else 5
        // Магистратура / работа
        in 23..24 -> if ((0..99).random() < 82) 5 else {
            if ((0..9).random() < 6) 4 else 0
        }
        // Работа
        in 25..64 -> {
            if (isMale) when (age) {
                in 25..29 -> if ((0..99).random() < 82) 5 else 0
                in 30..39 -> if ((0..99).random() < 95) 5 else 0
                in 40..49 -> if ((0..99).random() < 94) 5 else 0
                in 50..59 -> if ((0..99).random() < 88) 5 else 0
                else -> if ((0..99).random() < 51) 5 else 0
            } else when (age) {
                in 23..29 -> if ((0..99).random() < 74) 5 else 0
                in 30..39 -> if ((0..99).random() < 85) 5 else 0
                in 40..49 -> if ((0..99).random() < 89) 5 else 0
                in 50..59 -> if ((0..99).random() < 70) 5 else 0
                else -> if ((0..99).random() < 29) 5 else 0
            }
        }
        else -> 0
    }

}