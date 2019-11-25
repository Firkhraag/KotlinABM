package model

import utility.generateBarabasiAlbertNetwork

class College(val pos: Pair<Double, Double>) {
//class College {

    val groupsByAge = mapOf(17 to arrayListOf<Group>(),
            18 to arrayListOf())

    fun addAgent(agent: Agent) {
        if (groupsByAge[agent.age]?.size == 0) {
            val group = Group(groupRepo.size)
            groupsByAge[agent.age]?.add(group)
            groupRepo.add(group)
        }
        if (groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)?.agents?.size == 15) {
//            generateBarabasiAlbertNetwork(
//                    groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)!!, 2)
            val group = Group(groupRepo.size)
            groupsByAge[agent.age]?.add(group)
            groupRepo.add(group)
        }
        agent.groupId = (groupsByAge[agent.age] ?: error(""))[groupsByAge[agent.age]?.size!! - 1].id
        (groupsByAge[agent.age] ?: error(""))[groupsByAge[agent.age]?.size!! - 1].addAgent(agent)
    }
}