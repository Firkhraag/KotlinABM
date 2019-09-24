package model

class Agent(val isMale: Boolean, var age: Int) {

    var healthStatus = when ((1..1000).random()) {
        in 1..5 -> 1
        in 6..50 -> 2
        else -> 0
    }
    var daysInfected = if (healthStatus == 1) (1..7).random() else 0
    var daysRecovered = if (healthStatus == 2) (1..61).random() else 0

    fun shouldStayAtHome(daysInfected: Int): Boolean {
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

    var isStayingHomeWhenInfected = shouldStayAtHome(daysInfected)

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

}