package model

import utility.generateBarabasiAlbertNetwork
//import utility.generateBarabasiAlbertNetworkForVacation

class School {

    private val m = 3

    fun generateLastBarabasiAlbertNetworks() {
        groupsByAge.forEach { groupByAge ->
            if (groupByAge.size > 0) {
                if (groupByAge[groupByAge.size - 1].agents.size < m) {
                    generateBarabasiAlbertNetwork(
                            groupByAge[groupByAge.size - 1],
                            groupByAge[groupByAge.size - 1].agents.size)
                } else {
                    generateBarabasiAlbertNetwork(
                            groupByAge[groupByAge.size - 1], m)
                }
            }
        }
    }

    private fun findNumberOfPeople(classNum: Int): Int {
        return when (classNum) {
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

//    private val vacationGroupSize = 100
//    val groupsByAgeForVacationContacts = arrayListOf(
//            arrayListOf<Group>(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf())

    fun addAgent(agent: Agent) {
        val classNum = when (agent.age) {
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
        if (groupsByAge[classNum].size == 0) {
            groupsByAge[classNum].add(Group())

//            groupsByAgeForVacationContacts[classNum].add(Group())
            adultNeeded[classNum] = true
        }
        if (groupsByAge[classNum][groupsByAge[classNum].size - 1].agents.size == currentGroupSize[classNum]) {
            generateBarabasiAlbertNetwork(
                    groupsByAge[classNum][groupsByAge[classNum].size - 1], m)
            groupsByAge[classNum].add(Group())
            currentGroupSize[classNum] = findNumberOfPeople(classNum)
            adultNeeded[classNum] = true
        }

//        if (groupsByAgeForVacationContacts[classNum][groupsByAgeForVacationContacts[classNum].size - 1].agents.size == vacationGroupSize) {
//            generateBarabasiAlbertNetworkForVacation(
//                    groupsByAgeForVacationContacts[classNum][groupsByAgeForVacationContacts[classNum].size - 1], 2)
//            groupsByAgeForVacationContacts[classNum].add(Group())
//        }

        groupsByAge[classNum][groupsByAge[classNum].size - 1].addAgent(agent)

//        groupsByAgeForVacationContacts[classNum][groupsByAgeForVacationContacts[classNum].size - 1].addAgent(agent)
    }
}