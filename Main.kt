//import org.apache.commons.math3.distribution.GammaDistribution
import model.Agent
import model.School
import model.World
import model.groupRepo
import org.apache.commons.math3.distribution.GammaDistribution

fun main(args: Array<String>) {
    val world = World()

    while (true) {

        println("Write scale")
        val scale = readLine() ?: "1.0"

        println("Write coeffR")
        val coeffR = readLine() ?: "1.0"

        println("Write deviationR")
        val deviationR = readLine() ?: "1.0"

        println("Write coeffT")
        val coeffT = readLine() ?: "1.0"

        println("Write deviationT")
        val deviationT = readLine() ?: "1.0"

        println("Write coeffD")
        val coeffD = readLine() ?: "1.0"

        println("Write deviationD")
        val deviationD = readLine() ?: "1.0"

        println("Write coeffA")
        val coeffA = readLine() ?: "1.0"

        println("Write deviationA")
        val deviationA = readLine() ?: "1.0"

        println("Write tempSuscCoeff")
        val tempSuscCoeff = readLine() ?: "1.0"

        world.runSimulation(scale.toDouble(), coeffR.toDouble(), deviationR.toDouble(), coeffT.toDouble(),
                deviationT.toDouble(), coeffD.toDouble(), deviationD.toDouble(), coeffA.toDouble(),
                deviationA.toDouble(), tempSuscCoeff.toDouble())

        world.resetState()
    }

//    for (group in groupRepo) {
//        if (group.numOfInfected > 0) {
//            println(group.numOfInfected)
//            println(group.numOfAgents)
//        }
//    }

//    val school = School(1, Pair(1.0, 1.0))
//    val agent = Agent(true, 16)
//    school.addAgent(agent)
//    println(school.groupsByAge[16]?.get(0)?.numOfInfected)
////    println(agent.groupId)
//    println(groupRepo[agent.groupId].numOfInfected)
//    groupRepo[agent.groupId].numOfInfected += 1
//    println(school.groupsByAge[16]?.get(0)?.numOfInfected)

}