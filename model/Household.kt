package model

class Household(val pos: Pair<Double, Double>,
                val closestSchool: Int, val closestKindergarten: Int,
                val type: String) {
//class Household {

    val agents = arrayListOf<Agent>()
//    var numOfAgents = 0
//    var numOfInfected = 0

    fun addAgent(agent: Agent) {
//        println("-------------")
//        println("Pos: $pos")
//        println("Type: $type")
//        println("Age: ${agent.age}")
//        println("Activity: ${agent.activityStatus}")
//        if (agent.healthStatus == 1) {
//            numOfInfected += 1
//        }
        agents.add(agent)
//        numOfAgents += 1
//        if (!agent.isStayingHomeWhenInfected) {
//            numOfAgents += 1
//        }
//        if ((agent.healthStatus == 1) && (!agent.isStayingHomeWhenInfected)) {
//            numOfInfected += 1
//        }
    }

}