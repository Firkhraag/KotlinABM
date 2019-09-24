package model

class School(val id: Int, val pos: Pair<Double, Double>) {

    val groupsByAge = mapOf(7 to arrayListOf<Agent>(),
            8 to arrayListOf<Agent>(),
            9 to arrayListOf<Agent>(),
            10 to arrayListOf<Agent>(),
            11 to arrayListOf<Agent>(),
            12 to arrayListOf<Agent>(),
            13 to arrayListOf<Agent>(),
            14 to arrayListOf<Agent>(),
            15 to arrayListOf<Agent>(),
            16 to arrayListOf<Agent>(),
            17 to arrayListOf<Agent>(),
            18 to arrayListOf<Agent>())

    fun addAgent(agent: Agent) {
        groupsByAge[agent.age]?.add(agent)
    }
}