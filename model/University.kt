package model

import utility.generateBarabasiAlbertNetwork

class University(val pos: Pair<Double, Double>) {
//class University {

    val groupsByAge = mapOf(19 to arrayListOf<Group>(),
            20 to arrayListOf(),
            21 to arrayListOf(),
            22 to arrayListOf())


    fun addAgent(agent: Agent) {
        if (groupsByAge[agent.age]?.size == 0) {
            val group = Group(groupRepo.size)
            groupsByAge[agent.age]?.add(group)
            groupRepo.add(group)
        }
        if (groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)?.agents?.size == 18) {
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