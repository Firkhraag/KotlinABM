package model

class Group {

    val agents = arrayListOf<Agent>()
    var hasAdult = false

    fun addAgent(agent: Agent) {
        agents.add(agent)
    }
}