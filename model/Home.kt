package model

class Home(val pos: Pair<Double, Double>, val okato: Int) {

    val workingGroups = arrayListOf<Group>(Group())
    var currentGroupSize = (5..30).random()

    fun addWorkingAgent(agent: Agent) {
        if (workingGroups[workingGroups.size - 1].numOfAgents == currentGroupSize) {
            workingGroups.add(Group())
            currentGroupSize = (5..30).random()
        }
        workingGroups[workingGroups.size - 1].addAgent(agent)
    }
}