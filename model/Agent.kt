package model

import kotlin.math.*

class Agent(val isMale: Boolean, var age: Int) {

    val connectedAgents = arrayListOf<Int>()

    var healthStatus = if ((0..9999).random() < 12) 1 else {
        if (age < 18) {
            if ((0..9).random() == 0) 2 else 0
        } else 0
    }

    fun willBeInfected(): Int {
        return if (healthStatus == 1) {
            if (age >= 18) {
                when ((0..99).random()) {
                    in (0..19) -> 4
                    in (20..69) -> 5
                    in (70..89) -> 6
                    in (90..95) -> 7
                    else -> 8
                }
                (5..8).random()
            }
            else {
                when ((0..99).random()) {
                    in (0..22) -> 5
                    in (23..30) -> 6
                    in (31..40) -> 7
                    in (41..45) -> 8
                    in (46..52) -> 9
                    in (53..64) -> 10
                    in (65..78) -> 11
                    in (79..80) -> 12
                    in (81..82) -> 13
                    in (83..85) -> 14
                    else -> 15
                }
            }
        }
        else 0
    }

    fun shouldStayAtHome(): Boolean {
        return when (daysInfected) {
            0 -> when (age) {
                in 0..7 -> ((0..999).random() < 304)
                in 8..17 -> ((0..999).random() < 203)
                else -> ((0..9).random() == 0)
            }
            1 -> when (age) {
                in 0..7 -> ((0..999).random() < 575)
                in 8..17 -> ((0..999).random() < 498)
                else -> ((0..999).random() < 333)
            }
            2 -> when (age) {
                in 0..7 -> ((0..999).random() < 324)
                in 8..17 -> ((0..999).random() < 375)
                else -> ((0..999).random() < 167)
            }
            3 -> when (age) {
                in 0..7 -> ((0..999).random() < 124)
                in 8..17 -> ((0..999).random() < 175)
                else -> ((0..999).random() < 55)
            }
            else -> false
        }
    }

    fun getViralShedding(): Double {
        val rand = java.util.Random()
        if (age < 18) {
            return when (daysInfected) {
                0 -> 0.0
                1 -> max(0.0,8.19 + rand.nextGaussian() * 1.41)
                2 -> max(0.0,8.19 + rand.nextGaussian() * 1.41)
                3 -> max(0.0,7.3 + rand.nextGaussian() * 1.48)
                4 -> max(0.0,7.15 + rand.nextGaussian() * 1.35)
                5 -> max(0.0,7.0 + rand.nextGaussian() * 1.23)
                6 -> max(0.0,5.45 + rand.nextGaussian() * 2.03)
                7 -> max(0.0,3.9 + rand.nextGaussian() * 2.83)
                8 -> max(0.0,3.01 + rand.nextGaussian() * 2.9)
                9 -> max(0.0, 2.12 + rand.nextGaussian() * 3.05)
                10 -> max(0.0, 1.605 + rand.nextGaussian() * 2.685)
                11 -> max(0.0, 1.09 + rand.nextGaussian() * 2.32)
                12 -> max(0.0, 1.045 + rand.nextGaussian() * 1.99)
                13 -> max(0.0, 1.0 + rand.nextGaussian() * 1.66)
                14 -> max(0.0, 0.935 + rand.nextGaussian() * 2.065)
                else -> max(0.0, 0.87 + rand.nextGaussian() * 1.5)
            }
        } else {
            return when (daysInfected) {
                0 -> 0.0
                1 -> max(0.0,1.9 + rand.nextGaussian() * 0.4)
                2 -> max(0.0,3.0 + rand.nextGaussian() * 0.3)
                3 -> max(0.0,2.6 + rand.nextGaussian() * 0.8)
                4 -> max(0.0,2.2 + rand.nextGaussian() * 0.1)
                5 -> max(0.0,1.5 + rand.nextGaussian() * 0.2)
                6 -> max(0.0,1.1 + rand.nextGaussian() * 0.15)
                7 -> max(0.0,0.75 + rand.nextGaussian() * 0.4)
                else -> max(0.0,0.25 + rand.nextGaussian() * 0.1)
            }
        }
    }

    var infectivity = 0.0
    var viralLoad = 0.0
    fun findInfectivity(a: Double) {
        viralLoad = getViralShedding()
        infectivity = a * viralLoad
    }

    var susceptibility = when (age) {
        in 0..17 -> {
            1.18153846154
        }
        in 18..64 -> {
            1.11384615385
        }
        else -> {
            1.0
        }
    }

    var shouldBeInfected = willBeInfected()
    var daysImmune = if (healthStatus == 2) 1 else 0
    var daysInfected = if (healthStatus == 1) (0..shouldBeInfected).random() else 0
    var isStayingHomeWhenInfected = shouldStayAtHome()
//    var viralLoad = getViralShedding()

    // Data from census
    val activityStatus: Int = when (age) {
        // Ясли
        1 -> if ((0..1).random() == 0) 1 else 0
        // Детсад
        in 2..6 -> 1
        // Детсад / школа
        7 -> if ((0..1).random() == 0) 1 else 2
        // Школа
        in 8..15 -> 2
        // 20% в колледж
        16 -> when ((0..9).random()) {
            in 0..7 -> 2
            else -> 3
        }
        17 -> when ((0..9).random()) {
            in 0..7 -> 2
            else -> 3
        }
        // 10% в колледж, 40% в школе, 50% - в универе
        18 -> when ((0..9).random()) {
            in 0..3 -> 2
            in 4..8 -> 4
            else -> 3
        }
        // 10% безработен, 70% - универ, 20% - работа
        in 19..22 -> when ((0..9).random()) {
            in 0..6 -> 4
            7 -> 0
            else -> 5
        }
        // Магистратура / работа
        in 23..24 -> if ((0..99).random() < 82) 5 else {
            if ((0..9).random() < 8) 4 else 0
        }
        // Работа
        in 25..64 -> {
            if (isMale) when (age) {
                in 23..29 -> if ((0..99).random() < 82) 5 else 0
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