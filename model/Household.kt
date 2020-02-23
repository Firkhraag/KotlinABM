package model

import kotlin.math.max

class Household(val type: String) {

//    fun getContactDuration(): Double {
//        val rand = java.util.Random()
//        return max(0.0,12.5 + rand.nextGaussian() * 5.133)
//    }

    val agents = arrayListOf<Agent>()
//    var numOfAgents = 0
//    var numOfInfected = 0

    fun addAgent(agent: Agent) {
        agents.add(agent)
//        if (agent.healthStatus == 1) {
//            numOfInfected += 1
//        }
    }

}