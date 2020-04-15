package application.model

class Kindergarten(val pos: Pair<Double, Double>) {

    // Number of people in a group
    private fun findNumberOfPeople(groupNum: Int): Int {
        return when (groupNum) {
            0 -> when ((0..99).random()) {
                in (0..19) -> 9
                in (20..79) -> 10
                else -> 11
            }
            1 -> when ((0..99).random()) {
                in (0..19) -> 14
                in (20..79) -> 15
                else -> 16
            }
            2 -> when ((0..99).random()) {
                in (0..19) -> 14
                in (20..79) -> 15
                else -> 16
            }
            3 -> when ((0..99).random()) {
                in (0..19) -> 19
                in (20..79) -> 20
                else -> 21
            }
            4 -> when ((0..99).random()) {
                in (0..19) -> 19
                in (20..79) -> 20
                else -> 21
            }
            5 -> when ((0..99).random()) {
                in (0..19) -> 19
                in (20..79) -> 20
                else -> 21
            }
            else -> 20
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
            0 -> 0
            1 -> 1
            2 -> if ((0..1).random() == 0) 1 else 2
            3 -> if ((0..1).random() == 0) 2 else 3
            4 -> if ((0..1).random() == 0) 3 else 4
            5 -> if ((0..1).random() == 0) 4 else 5
            6 -> 5
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