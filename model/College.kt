package model

class College(val pos: Pair<Double, Double>) {

    val groupsByAge = mapOf(
            17 to arrayListOf<Agent>(),
            18 to arrayListOf<Agent>())

    fun addAgent(agent: Agent) {
        groupsByAge[agent.age]?.add(agent)
    }
}