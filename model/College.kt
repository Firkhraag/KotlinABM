package model

import utility.generateBarabasiAlbertNetwork

class College(val pos: Pair<Double, Double>) {
//class College {

    val groupsByAge = mapOf(17 to arrayListOf<Group>(Group()),
            18 to arrayListOf<Group>(Group()))

    fun addAgent(agent: Agent) {
        if (groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)?.agents?.size == 15) {
//            generateBarabasiAlbertNetwork(
//                    groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)!!, 2)
            groupsByAge[agent.age]?.add(Group())
        }
        (groupsByAge[agent.age] ?: error(""))[groupsByAge[agent.age]?.size!! - 1].addAgent(agent)
    }
}