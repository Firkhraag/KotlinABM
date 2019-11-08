package model

import utility.generateBarabasiAlbertNetwork

class School(val id: Int, val pos: Pair<Double, Double>) {

    val groupsByAge = mapOf(7 to arrayListOf<Group>(Group()),
            8 to arrayListOf<Group>(Group()),
            9 to arrayListOf<Group>(Group()),
            10 to arrayListOf<Group>(Group()),
            11 to arrayListOf<Group>(Group()),
            12 to arrayListOf<Group>(Group()),
            13 to arrayListOf<Group>(Group()),
            14 to arrayListOf<Group>(Group()),
            15 to arrayListOf<Group>(Group()),
            16 to arrayListOf<Group>(Group()),
            17 to arrayListOf<Group>(Group()),
            18 to arrayListOf<Group>(Group()))

    fun addAgent(agent: Agent) {
        if (groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)?.agents?.size == 25) {
//            generateBarabasiAlbertNetwork(
//                    groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)!!, 15)
            groupsByAge[agent.age]?.add(Group())
        }
        (groupsByAge[agent.age] ?: error(""))[groupsByAge[agent.age]?.size!! - 1].addAgent(agent)
    }
}