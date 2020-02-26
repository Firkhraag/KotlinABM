package model

class Household(val type: String) {

    val agents = arrayListOf<Agent>()

    fun addAgent(agent: Agent) {
        agents.add(agent)
    }

}