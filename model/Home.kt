package model

import utility.generateBarabasiAlbertNetwork

class Home(val pos: Pair<Double, Double>, val okato: Int) {

    val workingGroups = arrayListOf<Group>()
    var currentGroupSize = (5..20).random()

    fun addWorkingAgent(agent: Agent) {
        if (workingGroups.size == 0) {
            val group = Group(groupRepo.size)
            workingGroups.add(group)
            groupRepo.add(group)
        }
        if (workingGroups[workingGroups.size - 1].agents.size == currentGroupSize) {
//        if (workingGroups[workingGroups.size - 1].numOfAgents == 10) {
//            generateBarabasiAlbertNetwork(
//                    workingGroups[workingGroups.size - 1], 4)
            val group = Group(groupRepo.size)
            workingGroups.add(group)
            groupRepo.add(group)
            currentGroupSize = (5..15).random()
        }
        agent.groupId = workingGroups[workingGroups.size - 1].id
        workingGroups[workingGroups.size - 1].addAgent(agent)
    }
}