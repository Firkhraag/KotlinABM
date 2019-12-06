package model

import utility.generateBarabasiAlbertNetwork
import kotlin.math.max

class Workplace {

    private val m = 7

    fun getContactDuration(): Double {
        val rand = java.util.Random()
        return max(0.0,3.07 + rand.nextGaussian() * 2.07)
    }

    fun generateLastBarabasiAlbertNetwork() {
        if (workingGroups[workingGroups.size - 1].agents.size >= m) {
            generateBarabasiAlbertNetwork(
                    workingGroups[workingGroups.size - 1], m)
        } else {
            generateBarabasiAlbertNetwork(
                    workingGroups[workingGroups.size - 1], workingGroups[workingGroups.size - 1].agents.size)
        }
    }

    private val zipfDistribution = org.apache.commons.math3.distribution.ZipfDistribution(3000, 1.0)
    private var currentGroupSize = zipfDistribution.sample() + (m - 1)

    val workingGroups = arrayListOf<Group>()

    fun addAgent(agent: Agent) {
        if (workingGroups.size == 0) {
            workingGroups.add(Group())
        }
        if (workingGroups[workingGroups.size - 1].agents.size == currentGroupSize) {
            generateBarabasiAlbertNetwork(
                    workingGroups[workingGroups.size - 1], m)
            workingGroups.add(Group())
            currentGroupSize = zipfDistribution.sample() + (m - 1)
        }
        workingGroups[workingGroups.size - 1].addAgent(agent)
    }
}