package model

import utility.generateBarabasiAlbertNetworkForWork

class Workplace {

    private val m = 8

    fun generateLastBarabasiAlbertNetwork() {
        if (workingGroups[workingGroups.size - 1].agents.size >= m) {
            generateBarabasiAlbertNetworkForWork(
                    workingGroups[workingGroups.size - 1], m)
        } else {
            generateBarabasiAlbertNetworkForWork(
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
            generateBarabasiAlbertNetworkForWork(
                    workingGroups[workingGroups.size - 1], m)
            workingGroups.add(Group())
            currentGroupSize = zipfDistribution.sample() + (m - 1)
        }
        workingGroups[workingGroups.size - 1].addAgent(agent)
    }
}