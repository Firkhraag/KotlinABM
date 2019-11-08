package model

import utility.generateBarabasiAlbertNetwork

class Home(val pos: Pair<Double, Double>, val okato: Int) {

    val workingGroups = arrayListOf<Group>(Group())
    var currentGroupSize = (5..15).random()

    fun addWorkingAgent(agent: Agent) {
        if (workingGroups[workingGroups.size - 1].agents.size == currentGroupSize) {
//        if (workingGroups[workingGroups.size - 1].numOfAgents == 10) {
//            generateBarabasiAlbertNetwork(
//                    workingGroups[workingGroups.size - 1], 4)
            workingGroups.add(Group())
            currentGroupSize = (5..15).random()
        }
        workingGroups[workingGroups.size - 1].addAgent(agent)
    }
}