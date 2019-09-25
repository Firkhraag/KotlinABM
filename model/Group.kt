package model

class Group {
    var numOfAgents = 0
    val agents = arrayListOf<Agent>()

    fun addAgent(agent: Agent) {
        agents.add(agent)
        numOfAgents += 1
    }
}