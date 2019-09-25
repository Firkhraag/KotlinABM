package model

class Household(val pos: Pair<Double, Double>,
                val closestSchool: Int, val closestKindergarten: Int,
                val type: String) {

    val agents = arrayListOf<Agent>()

    fun addAgent(agent: Agent) {
        agents.add(agent)
    }

}