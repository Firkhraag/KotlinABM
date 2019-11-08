package model

import kotlin.math.*

class Agent(val isMale: Boolean, var age: Int) {

//    var id = 0

//    val connectedAgents = arrayListOf<Int>()
//
//    fun addConnectedAgent(agentNum: Int) {
//        connectedAgents.add(agentNum)
//    }

    var healthStatus = if ((0..10000).random() < 15) 1 else {
        if (age < 18) {
            if ((1..100).random() < 33) 2 else 0
        } else 0
    }
//var healthStatus = 1

    var daysInfected = if (healthStatus == 1) if (age > 17 ) (1..8).random() else (1..15).random() else 0
//    var daysInfected = if (healthStatus == 1) {
//        if (age > 17 ) (1..6).random() else (1..13).random()
//    } else 0

//    fun willBeInfected(): Int {
//        return if (healthStatus == 1) {
//            if (age > 17) (5..8).random()
//            else (7..15).random()
//        }
//        else 0
//    }

    val shouldBeInfected = if (age > 17 ) (max(5, daysInfected)..8).random() else (max(7, daysInfected)..15).random()
//    var shouldBeInfected = if (healthStatus == 1) {
//        if (age > 17 ) 7 else 14
//    } else 0

    fun shouldStayAtHome(): Boolean {
        return when (daysInfected) {
            0 -> false
            1 -> when (age) {
                in 0..7 -> ((0..9).random() < 3)
                in 8..18 -> ((0..9).random() < 2)
                else -> ((0..9).random() == 0)
            }
            2 -> when (age) {
                in 0..7 -> ((0..9).random() < 6)
                in 8..18 -> ((0..9).random() < 5)
                else -> ((0..9).random() < 4)
            }
            else -> when (age) {
                in 0..18 -> ((0..9).random() < 3)
                else -> ((0..9).random() < 2)
            }
        }
    }

    var isStayingHomeWhenInfected = shouldStayAtHome()

    // Activity 0 - Unemployed, 1 - Kindergarten, 2 - School, 3 - College, 4 - University, 5 - Work
    val activityStatus: Int = when (age) {
        1 -> if ((0..99).random() < 33) 1 else 0
        in 2..6 -> 1
        in 7..16 -> 2
        in 17..18 -> when ((0..9).random()) {
            0 -> 5
            in 1..6 -> 2
            else -> 3
        }
        in 19..22 -> if ((0..9).random() < 8) 4 else 5
        in 23..64 -> if (isMale) when (age) {
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
        else -> 0
    }

//    var isVaccinated = if (age < 18) (0..99).random() < 45 else false
//
////    var vaccineEffectiveness = if (isVaccinated) {
////        if (age < 5) 63 else 56
////    } else 0
//    var vaccineEffectiveness = if (isVaccinated) {
//        if (age < 5) 0.37 else 0.44
//    } else 1.0

}