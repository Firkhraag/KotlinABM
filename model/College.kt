package model

import utility.generateBarabasiAlbertNetwork
import kotlin.math.max

// Колледжи и техникумы
class College {

//    private val m = 6
    private val m = 10

    fun getContactDuration(): Double {
        val rand = java.util.Random()
        return max(0.0,2.133 + rand.nextGaussian() * 1.62)
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
                in (0..19) -> 24
                in (20..79) -> 25
                else -> 26
            }
            1 -> when ((0..99).random()) {
                in (0..19) -> 19
                in (20..79) -> 20
                else -> 21
            }
            else -> 16
        }
    }

    private var currentGroupSize = arrayListOf(
            findNumberOfPeople(0),
            findNumberOfPeople(1))

    val groupsByAge = arrayListOf(
            arrayListOf<Group>(),
            arrayListOf())

    fun addAgent(agent: Agent) {
        val classNum = when (agent.age) {
            16 -> 0
            17 -> if ((0..1).random() == 0) 0 else 1
            18 -> 1
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