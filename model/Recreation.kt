package model

import kotlin.math.max

class Recreation(val pos: Pair<Double, Double>) {

    fun getContactDuration(): Double {
        val rand = java.util.Random()
        return max(0.0,1.0 + rand.nextGaussian() * 1.0)
    }

    val agents = arrayListOf<Agent>()

    // Barabási-Albert Graph with m = 1
    fun addAgent(agent: Agent) {
        agents.add(agent)
    }

}