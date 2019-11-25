package model

import utility.generateBarabasiAlbertNetwork

class Kindergarten(val id: Int, val pos: Pair<Double, Double>) {

    val groupsByAge = mapOf(1 to arrayListOf<Group>(),
            2 to arrayListOf(),
            3 to arrayListOf(),
            4 to arrayListOf(),
            5 to arrayListOf(),
            6 to arrayListOf())


    fun addAgent(agent: Agent) {
        if (groupsByAge[agent.age]?.size == 0) {
            val group = Group(groupRepo.size)
            groupsByAge[agent.age]?.add(group)
            groupRepo.add(group)
        }
        if (groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)?.agents?.size == 30) {
//            generateBarabasiAlbertNetwork(
//                    groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)!!, 15)
            val group = Group(groupRepo.size)
            groupsByAge[agent.age]?.add(group)
            groupRepo.add(group)
        }
        agent.groupId = (groupsByAge[agent.age] ?: error(""))[groupsByAge[agent.age]?.size!! - 1].id
        (groupsByAge[agent.age] ?: error(""))[groupsByAge[agent.age]?.size!! - 1].addAgent(agent)
    }
}