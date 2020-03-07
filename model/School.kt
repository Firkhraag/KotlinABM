package model

class School {

    // Number of people in a group
    private fun findNumberOfPeople(groupNum: Int): Int {
        return when (groupNum) {
            0 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            1 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            2 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            3 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            4 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            5 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            6 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            7 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            8 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            9 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            10 -> when ((0..99).random()) {
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            else -> 25
        }
    }

    private var currentGroupSize = arrayListOf(
            findNumberOfPeople(0),
            findNumberOfPeople(1),
            findNumberOfPeople(2),
            findNumberOfPeople(3),
            findNumberOfPeople(4),
            findNumberOfPeople(5),
            findNumberOfPeople(6),
            findNumberOfPeople(7),
            findNumberOfPeople(8),
            findNumberOfPeople(9),
            findNumberOfPeople(10))

    val groupsByAge = arrayListOf(
            arrayListOf<Group>(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf())

    val adultNeeded = arrayListOf(
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false)

    fun addAgent(agent: Agent) {
        // Group number by age
        val groupNum = when (agent.age) {
            7 -> 0
            8 -> if ((0..1).random() == 0) 0 else 1
            9 -> if ((0..1).random() == 0) 1 else 2
            10 -> if ((0..1).random() == 0) 2 else 3
            11 -> if ((0..1).random() == 0) 3 else 4
            12 -> if ((0..1).random() == 0) 4 else 5
            13 -> if ((0..1).random() == 0) 5 else 6
            14 -> if ((0..1).random() == 0) 6 else 7
            15 -> if ((0..1).random() == 0) 7 else 8
            16 -> if ((0..1).random() == 0) 8 else 9
            17 -> if ((0..1).random() == 0) 9 else 10
            18 -> 10
            else -> 99
        }
        if (groupsByAge[groupNum].size == 0) {
            groupsByAge[groupNum].add(Group())

            adultNeeded[groupNum] = true
        }
        if (groupsByAge[groupNum][groupsByAge[groupNum].size - 1].agents.size == currentGroupSize[groupNum]) {
            groupsByAge[groupNum].add(Group())
            currentGroupSize[groupNum] = findNumberOfPeople(groupNum)
            adultNeeded[groupNum] = true
        }
        groupsByAge[groupNum][groupsByAge[groupNum].size - 1].addAgent(agent)
    }
}