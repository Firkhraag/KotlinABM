package model

class University(val pos: Pair<Double, Double>) {

    val groupsByAge = mapOf(19 to arrayListOf<Agent>(),
            20 to arrayListOf<Agent>(),
            21 to arrayListOf<Agent>(),
            22 to arrayListOf<Agent>())

    fun addAgent(agent: Agent) {
        groupsByAge[agent.age]?.add(agent)
    }
}