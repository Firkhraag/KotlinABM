package model

class Group {

//    var numOfAgents = 0
//    var numOfInfected = 0
    val agents = arrayListOf<Agent>()

//    fun getNumOfInfected(): Int {
//        var numOfInfected = 0
//        for (agent in agents) {
//            if ((agent.healthStatus == 1) && (!agent.isStayingHomeWhenInfected)) {
//                numOfInfected += 1
//            }
//        }
//        return numOfInfected
//    }
//
//    fun getNumOfAgen(): Int {
//        var num = 0
//        for (agent in agents) {
//            if (!agent.isStayingHomeWhenInfected) {
//                num += 1
//            }
//        }
//        return num
//    }

    fun addAgent(agent: Agent) {
        agents.add(agent)
//        numOfAgents += 1
    }
}