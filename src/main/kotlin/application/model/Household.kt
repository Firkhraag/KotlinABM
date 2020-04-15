package application.model

class Household(val type: String,
                val home: Home) {

    val agents = arrayListOf<Agent>()

    fun addAgent(agent: Agent) {
        agents.add(agent)
    }

}