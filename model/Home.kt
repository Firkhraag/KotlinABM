package model

class Home(val pos: Pair<Double, Double>, val okato: Int) {
    val workingAgents = arrayListOf<Agent>()

    fun addWorkingAgent(agent: Agent) {
        workingAgents.add(agent)
    }
}