package model

import utility.generateBarabasiAlbertNetwork
import kotlin.math.max

class Kindergarten {

//    private val m = 5
    private val m = 9

    fun getContactDuration(): Double {
        val rand = java.util.Random()
        return max(0.0,9.0 + rand.nextGaussian() * 1.0)
    }

    fun generateLastBarabasiAlbertNetworks() {
        groupsByAge.forEach { groupByAge ->
            if (groupByAge.size > 0) {
                if (groupByAge[groupByAge.size - 1].agents.size >= m) {
                    generateBarabasiAlbertNetwork(
                            groupByAge[groupByAge.size - 1], m)
                } else {
                    generateBarabasiAlbertNetwork(
                            groupByAge[groupByAge.size - 1], groupByAge[groupByAge.size - 1].agents.size)
                }
            }
        }
    }

    private fun findNumberOfPeople(classNum: Int): Int {
        return when (classNum) {
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
                in (0..19) -> 19
                in (20..79) -> 20
                else -> 21
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


    fun addAgent(agent: Agent) {
//        val classNum = when (agent.age) {
//            1 -> 0
//            2 -> if ((0..1).random() == 0) 0 else 1
//            3 -> if ((0..1).random() == 0) 1 else 2
//            4 -> if ((0..1).random() == 0) 2 else 3
//            5 -> if ((0..1).random() == 0) 3 else 4
//            6 -> if ((0..1).random() == 0) 4 else 5
//            7 -> 5
//            else -> 99
//        }
        val classNum = when (agent.age) {
            1 -> 0
            2 -> 1
            3 -> if ((0..1).random() == 0) 1 else 2
            4 -> if ((0..1).random() == 0) 2 else 3
            5 -> if ((0..1).random() == 0) 3 else 4
            6 -> if ((0..1).random() == 0) 4 else 5
            7 -> 5
            else -> 99
        }
        if (groupsByAge[classNum].size == 0) {
            groupsByAge[classNum].add(Group())
        }
        if (groupsByAge[classNum][groupsByAge[classNum].size - 1].agents.size == currentGroupSize[classNum]) {
            generateBarabasiAlbertNetwork(
                    groupsByAge[classNum][groupsByAge[classNum].size - 1], m)
            groupsByAge[classNum].add(Group())
            currentGroupSize[classNum] = findNumberOfPeople(classNum)
        }
        groupsByAge[classNum][groupsByAge[classNum].size - 1].addAgent(agent)
    }
}