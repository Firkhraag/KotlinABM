package utility

import model.Group

fun generateBarabasiAlbertNetworkForWork(group: Group, m: Int) {

    var degreeSum: Int
    if (m == 1) {
        if (group.agents.size < 2) {
            return
        }
        for (i in 0 until 2) {
            val agent = group.agents[i]
            for (j in 0 until m) {
                if (i == j) continue
                agent.connectedWorkAgents.add(j)
            }
        }
        degreeSum = 2
    } else {
        for (i in 0 until m) {
            val agent = group.agents[i]
            for (j in 0 until m) {
                if (i == j) continue
                agent.connectedWorkAgents.add(j)
            }
        }
        degreeSum = m * (m - 1)
    }

    for (i in m until group.agents.size) {
        if ((m == 1) && (i == 1)) continue
        val agent = group.agents[i]
        var addedCon = 0
        for (j in 0 until i) {
            val agent2 = group.agents[j]
            val p = agent2.connectedWorkAgents.size / degreeSum

            val randomNum = (0..9999).random() * 0.0001
            if (randomNum < p) {
                agent.connectedWorkAgents.add(j)
                agent2.connectedWorkAgents.add(i)
                addedCon += 1
                if (addedCon == m) {
                    break
                }
            }
        }
        while (addedCon < m) {
            val randomNum = (0 until i).random()
            if (randomNum !in agent.connectedWorkAgents) {
                agent.connectedWorkAgents.add(randomNum)
                group.agents[randomNum].connectedWorkAgents.add(i)
                addedCon += 1
            }
        }
        degreeSum += m
    }
}
