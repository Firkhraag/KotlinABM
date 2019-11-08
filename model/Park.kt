package model

class Park(val pos: Pair<Double, Double>) {

    val agents = arrayListOf<Agent>()

    // Barabási-Albert Graph with m = 1
    fun addAgent(agent: Agent) {
        agents.add(agent)
    }

}