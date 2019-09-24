package model

class Kindergarten(val id: Int, val pos: Pair<Double, Double>) {

    val groupsByAge = mapOf(0 to arrayListOf<Agent>(),
            1 to arrayListOf<Agent>(),
            2 to arrayListOf<Agent>(),
            3 to arrayListOf<Agent>(),
            4 to arrayListOf<Agent>(),
            5 to arrayListOf<Agent>(),
            6 to arrayListOf<Agent>())

    fun addAgent(agent: Agent) {
        groupsByAge[agent.age]?.add(agent)
    }
}