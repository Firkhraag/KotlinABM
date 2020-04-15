package application.model

import application.utility.generateBarabasiAlbertNetworkForWork

class Workplace {

    private val m = 8

    fun generateLastBarabasiAlbertNetwork() {
        if (companies[companies.size - 1].agents.size >= m) {
            generateBarabasiAlbertNetworkForWork(
                    companies[companies.size - 1], m)
        } else {
            generateBarabasiAlbertNetworkForWork(
                    companies[companies.size - 1], companies[companies.size - 1].agents.size)
        }
    }

    private val zipfDistribution = org.apache.commons.math3.distribution.ZipfDistribution(3000, 1.0)
    private var currentGroupSize = zipfDistribution.sample() + (m - 1)

    val companies = arrayListOf<Company>()

    fun addAgent(agent: Agent, homes: ArrayList<Home>) {
        if (companies.size == 0) {
            companies.add(Company(homes[(homes.indices).random()]))
        }
        if (companies[companies.size - 1].agents.size == currentGroupSize) {
            generateBarabasiAlbertNetworkForWork(
                    companies[companies.size - 1], m)
            companies.add(Company(homes[(homes.indices).random()]))
            currentGroupSize = zipfDistribution.sample() + (m - 1)
        }
        companies[companies.size - 1].addAgent(agent)
    }
}