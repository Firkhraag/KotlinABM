package application.model

// Домохозяйство (тип)
class Household(val type: String) {

    // Массив агентов
    val agents = arrayListOf<Agent>()

    // Добавить агента
    fun addAgent(agent: Agent) {
        agents.add(agent)
    }
}
