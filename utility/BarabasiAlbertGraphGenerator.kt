package utility

import model.Group

fun generateBarabasiAlbertNetwork(group: Group, m: Int) {
//
//    var degreeSum: Int
//    if (m == 1) {
//        for (i in 0 until 2) {
//            val agent = group.agents[i]
//            for (j in 0 until m) {
//                if (i == j) continue
//                agent.addConnectedAgent(j)
//            }
//        }
//        degreeSum = 2
//    } else {
//        for (i in 0 until m) {
//            val agent = group.agents[i]
//            for (j in 0 until m) {
//                if (i == j) continue
//                agent.addConnectedAgent(j)
//            }
//        }
//        degreeSum = m * (m - 1)
//    }
//
//    for (i in m until group.agents.size) {
//        if ((m == 1) && (i == 1)) continue
//        val agent = group.agents[i]
//        var addedCon = 0
//        for (j in 0 until i) {
//            val agent2 = group.agents[j]
//            val p = agent2.connectedAgents.size / degreeSum
////            val p = if ((i == 1) && (m == 1)) 1 else {
////                agent2.connectedAgents.size / degreeSum
////            }
//
//            val randomNum = (0..9999).random() * 0.0001
//            if (randomNum < p) {
//                agent.addConnectedAgent(j)
//                agent2.addConnectedAgent(i)
//                addedCon += 1
//                if (addedCon == m) {
//                    break
//                }
//            }
//        }
//        while (addedCon < m) {
//            val randomNum = (0 until i).random()
//            if (randomNum !in agent.connectedAgents) {
//                agent.addConnectedAgent(randomNum)
//                group.agents[randomNum].addConnectedAgent(i)
//                addedCon += 1
//            }
//        }
//        degreeSum += m
//    }
//
//    group.isBarabasiAlbertNetwork = true
}