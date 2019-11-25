package model

import utility.generateBarabasiAlbertNetwork

class School(val id: Int, val pos: Pair<Double, Double>) {

    val groupsByAge = mapOf(7 to arrayListOf<Group>(),
            8 to arrayListOf(),
            9 to arrayListOf(),
            10 to arrayListOf(),
            11 to arrayListOf(),
            12 to arrayListOf(),
            13 to arrayListOf(),
            14 to arrayListOf(),
            15 to arrayListOf(),
            16 to arrayListOf(),
            17 to arrayListOf(),
            18 to arrayListOf())

    fun addAgent(agent: Agent) {
        if (groupsByAge[agent.age]?.size == 0) {
            val group = Group(groupRepo.size)
            groupsByAge[agent.age]?.add(group)
            groupRepo.add(group)
        }
        if (groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)?.agents?.size == 25) {
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