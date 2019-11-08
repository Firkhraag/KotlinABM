//import org.apache.commons.math3.distribution.GammaDistribution
import model.World

fun main(args: Array<String>) {
    val world = World()
    world.runSimulation()

//    val infectivity = GammaDistribution(2.0, 2.0).density(5.0)
//    println(infectivity)

//    val group = Group()
//    for (i in 1..25) {
//        group.addAgent(Agent(false, 24))
//    }
//    generateBarabasiAlbertNetwork(group, 2)
//    for (agent in group.agents) {
//        println(agent.connectedAgents.size)
//    }
}