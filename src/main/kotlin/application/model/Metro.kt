package application.model

class Metro(val pos: Pair<Double, Double>) {

    // On average 53 people in a wagon
//    val groupsByTimeMorning = arrayListOf<Group>()
//    val groupsByTimeEvening = arrayListOf<Group>()
    val morningFirstWagon = Group()
    val eveningFirstWagon = Group()

//    init {
//        for (j in (0..29)) {
//            groupsByTimeMorning.add(Group())
//            groupsByTimeEvening.add(Group())
//        }
//    }

//    fun addAgentEveryTimeStep(agent: Agent, timeIndex: Int, isMorning: Boolean) {
//        if (isMorning) {
//            groupsByTimeMorning[timeIndex].addAgent(agent)
//        } else {
//            groupsByTimeEvening[timeIndex].addAgent(agent)
//        }
//    }

    fun addAgent(agent: Agent, isMorning: Boolean) {
        if (isMorning) {
            morningFirstWagon.addAgent(agent)
        } else {
            eveningFirstWagon.addAgent(agent)
        }
    }

}
