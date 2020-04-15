package application.model

class University(val pos: Pair<Double, Double>) {

    // Number of people in a group
    private fun findNumberOfPeople(groupNum: Int): Int {
        return when (groupNum) {
            0 -> when ((0..99).random()) {
                in (0..19) -> 14
                in (20..79) -> 15
                else -> 16
            }
            1 -> when ((0..99).random()) {
                in (0..19) -> 13
                in (20..79) -> 14
                else -> 15
            }
            2 -> when ((0..99).random()) {
                in (0..19) -> 13
                in (20..79) -> 14
                else -> 15
            }
            3 -> when ((0..99).random()) {
                in (0..19) -> 12
                in (20..79) -> 13
                else -> 14
            }
            4 -> when ((0..99).random()) {
                in (0..19) -> 11
                in (20..79) -> 12
                else -> 13
            }
            5 -> when ((0..99).random()) {
                in (0..19) -> 10
                in (20..79) -> 11
                else -> 12
            }
            else -> 15
        }
    }

    private var currentGroupSize = arrayListOf(
            findNumberOfPeople(0),
            findNumberOfPeople(1),
            findNumberOfPeople(2),
            findNumberOfPeople(3),
            findNumberOfPeople(4),
            findNumberOfPeople(5))

    val groupsByAge = arrayListOf(
            arrayListOf<Group>(),
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
            false)

    fun addAgent(agent: Agent) {
        // Group number by age
        val groupNum = when (agent.age) {
            18 -> 0
            19 -> if ((0..1).random() == 0) 0 else 1
            20 -> if ((0..1).random() == 0) 1 else 2
            21 -> if ((0..1).random() == 0) 2 else 3
            22 -> if ((0..1).random() == 0) 3 else 4
            23 -> if ((0..1).random() == 0) 4 else 5
            24 -> 5
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