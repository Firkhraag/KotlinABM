package model

class Kindergarten(val id: Int, val pos: Pair<Double, Double>) {

    val groupsByAge = mapOf(1 to arrayListOf<Group>(Group()),
            2 to arrayListOf<Group>(Group()),
            3 to arrayListOf<Group>(Group()),
            4 to arrayListOf<Group>(Group()),
            5 to arrayListOf<Group>(Group()),
            6 to arrayListOf<Group>(Group()))


    fun addAgent(agent: Agent) {
        if (groupsByAge[agent.age]?.get(groupsByAge[agent.age]?.size!! - 1)?.numOfAgents == 30) {
            groupsByAge[agent.age]?.add(Group())
        }
        (groupsByAge[agent.age] ?: error(""))[groupsByAge[agent.age]?.size!! - 1].addAgent(agent)
    }
}