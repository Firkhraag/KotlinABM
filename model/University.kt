package model

import utility.generateBarabasiAlbertNetwork

class University(val pos: Pair<Double, Double>) {
//class University {

    val groupsByAge = mapOf(19 to arrayListOf<Group>(Group()),
            20 to arrayListOf<Group>(Group()),
            21 to arrayListOf<Group>(Group()),
            22 to arrayListOf<Group>(Group()))


    fun addAgent(agent: Agent) {
        if (groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)?.agents?.size == 18) {
//            generateBarabasiAlbertNetwork(
//                    groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)!!, 2)
            groupsByAge[agent.age]?.add(Group())
        }
        (groupsByAge[agent.age] ?: error(""))[groupsByAge[agent.age]?.size!! - 1].addAgent(agent)
    }
}