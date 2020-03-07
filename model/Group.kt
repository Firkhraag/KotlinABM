package model

class Group {

    val agents = arrayListOf<Agent>()

    fun addAgent(agent: Agent) {
        agents.add(agent)
    }
}