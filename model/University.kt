package model

import utility.generateBarabasiAlbertNetwork
//import utility.generateBarabasiAlbertNetworkForVacation

class University {

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

//    private val vacationGroupSize = 100
//    val groupsByAgeForVacationContacts = arrayListOf(
//            arrayListOf<Group>(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf(),
//            arrayListOf())

    fun addAgent(agent: Agent) {
        val classNum = when (agent.age) {
            18 -> 0
            19 -> if ((0..1).random() == 0) 0 else 1
            20 -> if ((0..1).random() == 0) 1 else 2
            21 -> if ((0..1).random() == 0) 2 else 3
            22 -> if ((0..1).random() == 0) 3 else 4
            23 -> if ((0..1).random() == 0) 4 else 5
            24 -> 5
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