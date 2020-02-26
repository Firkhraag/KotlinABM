package model

//import utility.generateBarabasiAlbertNetwork
//import utility.generateBarabasiAlbertNetworkForVacation

class Population {

//    val groupsByAge = arrayListOf<ArrayList<Group>>()

    val groupsByAge = arrayListOf(
            arrayListOf<Group>(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf(),
            arrayListOf())

//    fun initializeGroups() {
//        for (i in (0..89)) {
//            groupsByAge.add(arrayListOf())
//        }
//    }

    fun addAgent(agent: Agent) {
        val groupNum = when (agent.age) {
            in 0..4 -> 0
            in 5..9 -> 1
            in 10..14 -> 2
            in 15..19 -> 3
            in 20..24 -> 4
            in 25..29 -> 5
            in 30..34 -> 6
            in 35..39 -> 7
            in 40..44 -> 8
            in 45..49 -> 9
            in 50..54 -> 10
            in 55..59 -> 11
            in 60..64 -> 12
            in 65..69 -> 13
            in 70..74 -> 14
            in 75..79 -> 15
            in 80..84 -> 16
            else -> 17
        }
        if (groupsByAge[groupNum].size == 0) {
            groupsByAge[groupNum].add(Group())
        }
        if (groupsByAge[groupNum][groupsByAge[groupNum].size - 1].agents.size == 4) {
            groupsByAge[groupNum].add(Group())
        }
        groupsByAge[groupNum][groupsByAge[groupNum].size - 1].addAgent(agent)
    }

}