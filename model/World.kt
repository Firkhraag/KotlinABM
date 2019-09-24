package model

import kotlin.math.*
import utility.readTableFloat
import utility.readTableInt

class World {

    var hour = 0
    var day = 1
    var month = 1
    var year = 1

    var dayOfTheWeek = 1
    var weekOfTheMonth = 1
    var dayOfTheYear = 1
    var weekend = false

    var infected = 0
    var recovered = 0
    var susceptible = 0

    val kindergartens = arrayListOf<Kindergarten>()
    val schools = arrayListOf<School>()
    val colleges = arrayListOf<College>()
    val universities = arrayListOf<University>()
    val homes = arrayListOf<Home>()
    val households = arrayListOf<Household>()

    private fun createKindergartens() {
        val kindergartenCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableFloat("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\Tables\\kindergartens.xlsx",
                1763, 1, kindergartenCoordinatesMatrix)
        kindergartenCoordinatesMatrix.forEachIndexed{ index, element ->
            kindergartens.add(Kindergarten(index, Pair(element[0], element[1])))
        }
    }

    private fun createSchools() {
        val schoolCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableFloat("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\Tables\\schools.xlsx",
                997, 1, schoolCoordinatesMatrix)
        schoolCoordinatesMatrix.forEachIndexed{ index, element ->
            schools.add(School(index, Pair(element[0], element[1])))
        }
    }

    private fun createColleges() {
        val collegeCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableFloat("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\Tables\\colleges.xlsx",
                276, 1, collegeCoordinatesMatrix)
        collegeCoordinatesMatrix.forEach{
            colleges.add(College(Pair(it[0], it[1])))
        }
    }

    private fun createUniversities() {
        val universityCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableFloat("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\Tables\\universities.xlsx",
                41, 1, universityCoordinatesMatrix)
        universityCoordinatesMatrix.forEach{
            universities.add(University(Pair(it[0], it[1])))
        }
    }

    private fun createHomes() {
        val homeCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableFloat("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\Tables\\homes.xlsx",
                22266, 2, homeCoordinatesMatrix)
        homeCoordinatesMatrix.forEach{
            homes.add(Home(Pair(it[0], it[1]), it[2].toInt()))
        }
    }

    private fun createAgent(okatoIndex: Int,
                            biasedIndex: Int,
                            districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                            ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>,
                            isChild: Boolean,
                            isMale: Boolean? = null,
                            parentAge: Int? = null,
                            isParentOfAdult: Boolean? = null): Agent {

        var randomNum = (1..100).random()

        if (isChild) {
            when(randomNum) {
                in 1..ageDistributionInDistrictsMatrix[16][biasedIndex] -> {
                    randomNum = (1..100).random()
                    return when(parentAge) {
                        in 18..23 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                Agent(true, (0..4).random())
                            } else {
                                Agent(false, (0..4).random())
                            }
                        }
                        in 24..27 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random())
                                } else {
                                    Agent(false, (0..4).random())
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..9).random())
                                } else {
                                    Agent(false, (5..9).random())
                                }
                            }
                        }
                        else -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][154])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random())
                                } else {
                                    Agent(false, (0..4).random())
                                }
                            } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][155])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..9).random())
                                } else {
                                    Agent(false, (5..9).random())
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][121])) {
                                    Agent(true, (10..14).random())
                                } else {
                                    Agent(false, (10..14).random())
                                }
                            }
                        }
                    }
                }
                else -> {
                    randomNum = (1..100).random()
                    return if (randomNum in (1..districtsInfoMatrix[okatoIndex][123])) {
                        Agent(true, (15..17).random())
                    } else {
                        Agent(false, (15..17).random())
                    }
                }
            }
        } else {
            if (isParentOfAdult != null) {
                randomNum = if (isParentOfAdult) {
                    (ageDistributionInDistrictsMatrix[12][biasedIndex] + 1..100).random()
                } else {
                    (1..ageDistributionInDistrictsMatrix[13][biasedIndex]).random()
                }
            }
            when (randomNum) {
                in 1..ageDistributionInDistrictsMatrix[10][biasedIndex] -> {
                    randomNum = (1..100).random()
                    if (isMale != null) {
                        return Agent(isMale, (18..24).random())
                    }
                    return if (randomNum in (1..districtsInfoMatrix[okatoIndex][125])) {
                        Agent(true, (18..24).random())
                    } else {
                        Agent(false, (18..24).random())
                    }
                }
                in ageDistributionInDistrictsMatrix[10][biasedIndex] + 1..ageDistributionInDistrictsMatrix[11][biasedIndex] -> {
                    randomNum = (1..100).random()
                    if (randomNum in (1..districtsInfoMatrix[okatoIndex][156])) {
                        if (isMale != null) {
                            return Agent(isMale, (25..29).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][127])) {
                            Agent(true, (25..29).random())
                        } else {
                            Agent(false, (25..29).random())
                        }
                    } else {
                        if (isMale != null) {
                            return Agent(isMale, (30..34).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][129])) {
                            Agent(true, (30..34).random())
                        } else {
                            Agent(false, (30..34).random())
                        }
                    }
                }
                in ageDistributionInDistrictsMatrix[11][biasedIndex] + 1..ageDistributionInDistrictsMatrix[12][biasedIndex] -> {
                    randomNum = (1..100).random()
                    if (randomNum in (1..districtsInfoMatrix[okatoIndex][157])) {
                        if (isMale != null) {
                            return Agent(isMale, (35..39).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][131])) {
                            Agent(true, (35..39).random())
                        } else {
                            Agent(false, (35..39).random())
                        }
                    } else {
                        if (isMale != null) {
                            return Agent(isMale, (40..44).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][133])) {
                            Agent(true, (40..44).random())
                        } else {
                            Agent(false, (40..44).random())
                        }
                    }
                }
                in ageDistributionInDistrictsMatrix[12][biasedIndex] + 1..ageDistributionInDistrictsMatrix[13][biasedIndex] -> {
                    randomNum = (1..100).random()
                    if (randomNum in (1..districtsInfoMatrix[okatoIndex][158])) {
                        if (isMale != null) {
                            return Agent(isMale, (45..49).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][135])) {
                            Agent(true, (45..49).random())
                        } else {
                            Agent(false, (45..49).random())
                        }
                    } else {
                        if (isMale != null) {
                            return Agent(isMale, (50..54).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][137])) {
                            Agent(true, (50..54).random())
                        } else {
                            Agent(false, (50..54).random())
                        }
                    }
                }
                in ageDistributionInDistrictsMatrix[13][biasedIndex] + 1..ageDistributionInDistrictsMatrix[14][biasedIndex] -> {
                    randomNum = (1..100).random()
                    if (randomNum in (1..districtsInfoMatrix[okatoIndex][159])) {
                        if (isMale != null) {
                            return Agent(isMale, (55..59).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][139])) {
                            Agent(true, (55..59).random())
                        } else {
                            Agent(false, (55..59).random())
                        }
                    } else {
                        if (isMale != null) {
                            return Agent(isMale, (60..64).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][141])) {
                            Agent(true, (60..64).random())
                        } else {
                            Agent(false, (60..64).random())
                        }
                    }
                }
                else -> {
                    randomNum = (1..100).random()
                    if (randomNum in (1..districtsInfoMatrix[okatoIndex][160])) {
                        if (isMale != null) {
                            return Agent(isMale, (65..69).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][143])) {
                            Agent(true, (65..69).random())
                        } else {
                            Agent(false, (65..69).random())
                        }
                    } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][161])) {
                        if (isMale != null) {
                            return Agent(isMale, (70..74).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][145])) {
                            Agent(true, (70..74).random())
                        } else {
                            Agent(false, (70..74).random())
                        }
                    } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][162])) {
                        if (isMale != null) {
                            return Agent(isMale, (75..79).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][147])) {
                            Agent(true, (75..79).random())
                        } else {
                            Agent(false, (75..79).random())
                        }
                    } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][163])) {
                        if (isMale != null) {
                            return Agent(isMale, (80..84).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][149])) {
                            Agent(true, (80..84).random())
                        } else {
                            Agent(false, (80..84).random())
                        }
                    } else {
                        if (isMale != null) {
                            return Agent(isMale, (85..89).random())
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][151])) {
                            Agent(true, (85..89).random())
                        } else {
                            Agent(false, (85..89).random())
                        }
                    }
                }
            }
        }
    }

    private fun addPair(household: Household,
                        okatoIndex: Int,
                        biasedIndex: Int,
                        districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                        ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>,
                        closestKindergarten: Kindergarten,
                        closestSchool: School, isParentOfAdult: Boolean?): Int {
        val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                ageDistributionInDistrictsMatrix, false, true, null, isParentOfAdult)
        addAgentToGroups(agent, household, closestKindergarten, closestSchool)

        val difference = when ((1..100).random()) {
            in 1..3 -> (-20..-15).random()
            in 4..8 -> (-14..-10).random()
            in 9..20 -> (-9..-6).random()
            in 21..33 -> (-5..-4).random()
            in 34..53 -> (-3..-2).random()
            in 54..86 -> (-1..1).random()
            in 87..93 -> (2..3).random()
            in 94..96 -> (4..5).random()
            in 97..98 -> (6..9).random()
            else -> (10..14).random()
        }
        var spouseAge = agent.age + difference
        if (spouseAge < 18) {
            spouseAge = 18
        } else if (spouseAge > 80) {
            spouseAge = 80
        }
        val agent2 = Agent(false, spouseAge)
        addAgentToGroups(agent2, household, closestKindergarten, closestSchool)

//        println("**************PAIR***************")
//        println("-----------------------------")
//        println("Age: ${agent.age}")
//        println("Sex: ${agent.isMale}")
//        println("Activity: ${agent.activityStatus}")
//
//        println("-----------------------------")
//        println("Age: ${agent2.age}")
//        println("Sex: ${agent2.isMale}")
//        println("Activity: ${agent2.activityStatus}")

        return spouseAge
    }

    private fun degRad(deg: Double): Double {
        return deg * (PI/180)
    }

    private fun findDistance(point1: Pair<Double, Double>, point2: Pair<Double, Double>): Double {
        val dLat = degRad(point2.second - point1.second)
        val dLon = degRad(point2.first - point1.first)
        val a = sin(dLat/2) * sin(dLat/2) + cos(degRad(point1.second)) * cos(degRad(point2.second)) * sin(dLon/2) * sin(dLon/2)
        return 12742 * atan2(sqrt(a), sqrt(1-a))
    }

    private fun findClosestKindergarten(homePos: Pair<Double, Double>): Int {
        var minIndex = 0
        var minDist = 10000.0
        var dist: Double
        kindergartens.forEach {
            dist = findDistance(homePos, it.pos)
            if (dist < minDist) {
                minDist = dist
                minIndex = it.id
            }
        }
        return minIndex
    }

    private fun findClosestSchool(homePos: Pair<Double, Double>): Int {
        var minIndex: Int = 0
        var minDist = 10000.0
        var dist: Double
        schools.forEach {
            dist = findDistance(homePos, it.pos)
            if (dist < minDist) {
                minDist = dist
                minIndex = it.id
            }
        }
        return minIndex
    }

    private fun addAgentToGroups(agent: Agent, household: Household,
                                 closestKindergarten: Kindergarten,
                                 closestSchool: School) {
        when (agent.activityStatus) {
            1 -> closestKindergarten.addAgent(agent)
            2 -> if (agent.age < 14) closestSchool.addAgent(agent) else schools[(0 until schools.size).random()].addAgent(agent)
            3 -> colleges[(0 until colleges.size).random()].addAgent(agent)
            4 -> universities[(0 until universities.size).random()].addAgent(agent)
            5 -> homes[(0 until homes.size).random()].addWorkingAgent(agent)
        }
        when (agent.healthStatus) {
            0 -> susceptible += 1
            1 -> infected += 1
            2 -> recovered += 1
        }
        household.addAgent(agent)
    }

    private fun createHousehold(type: String,
                                pos: Pair<Double, Double>,
                                okatoIndex: Int,
                                biasedIndex: Int,
                                districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                                ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>) {

        val closestKindergarten = kindergartens[findClosestKindergarten(pos)]
        val closestSchool = schools[findClosestSchool(pos)]
        val household = Household(pos, findClosestSchool(pos), findClosestKindergarten(pos), type)
        when (type) {
            "1P" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
            }
            "PWOP2P0C" -> {
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
            }
            "PWOP3P0C" -> {
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
            }
            "PWOP3P1C" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
            }
            "PWOP4P0C" -> {
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "PWOP4P1C" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "PWOP4P2C" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "PWOP5P0C" -> {
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "PWOP5P1C" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "PWOP5P2C" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "PWOP5P3C" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "PWOP6P0C" -> {
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "PWOP6P1C" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "PWOP6P2C" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "PWOP6P3C" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "2PWOP4P0С" -> {
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
            }
            "2PWOP5P0С" -> {
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
            }
            "2PWOP5P1С" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
            }
            "2PWOP6P0С" -> {
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "2PWOP6P1С" -> {
                val parentAge = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "2PWOP6P2С" -> {
                val parentAge1 = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val parentAge2 = addPair(household,
                        okatoIndex,
                        biasedIndex,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge1)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge2)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "SMWC2P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "SMWC2P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "SMWC3P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent3.age < 18) {
                    agent3.age = agent.age - 18
                }
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "SMWC3P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "SMWC3P2C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "SMWC4P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent3.age < 18) {
                    agent3.age = agent.age - 18
                }
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent4.age < 18) {
                    agent4.age = agent.age - 18
                }
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "SMWC4P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent3.age < 18) {
                    agent3.age = agent.age - 18
                }
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "SMWC4P2C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "SMWC4P3C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "SFWC2P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "SFWC2P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "SFWC3P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent3.age < 18) {
                    agent3.age = agent.age - 18
                }
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "SFWC3P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "SFWC3P2C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "SPWCWP3P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "SPWCWP3P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                if (agent2.age - agent.age < 18) {
                    agent2.age = agent.age + 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)

//                println("-------------PARENT----------------")
//                println("Age: ${agent.age}")
//                println("Sex: ${agent.isMale}")
//                println("Activity: ${agent.activityStatus}")
//                println("-------------PARENT OF PARENT----------------")
//                println("Age: ${agent2.age}")
//                println("Sex: ${agent2.isMale}")
//                println("Activity: ${agent2.activityStatus}")
//                println("-------------CHILDREN----------------")
//                println("Age: ${agent3.age}")
//                println("Sex: ${agent3.isMale}")
//                println("Activity: ${agent3.activityStatus}")
            }
            "SPWCWP4P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "SPWCWP4P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "SPWCWP4P2C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                if (agent2.age - agent.age < 18) {
                    agent2.age = agent.age + 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "SPWCWPWOP3P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "SPWCWPWOP3P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                if (agent2.age - agent.age < 18) {
                    agent2.age = agent.age + 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "SPWCWPWOP4P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "SPWCWPWOP4P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "SPWCWPWOP4P2C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                if (agent2.age - agent.age < 18) {
                    agent2.age = agent.age + 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "SPWCWPWOP5P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
            }
            "SPWCWPWOP5P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
            }
            "SPWCWPWOP5P2C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
            }
            "O2P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "O2P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
            }
            "O3P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "O3P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "O3P2C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
            }
            "O4P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "O4P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "O4P2C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
            }
            "O5P0C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
            }
            "O5P1C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
            }
            "O5P2C" -> {
                val agent = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, biasedIndex, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
            }
        }
        households.add(household)
    }

    private fun addHouseholdsToPool(districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                                    ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>) {
        var okatoIndex = 0
        districtsInfoMatrix.forEach {
            val currOkato = it[0]
            println(currOkato)
            val homesFiltered = homes.filter { home -> home.okato == currOkato }
            val indexFor1People = okatoIndex * 5 + 1
            val indexFor2People = okatoIndex * 5 + 2
            val indexFor3People = okatoIndex * 5 + 3
            val indexFor4People = okatoIndex * 5 + 4
            val indexFor5People = okatoIndex * 5 + 5
////            for (i in 0..it[58]) {
            for (i in 0..100) {
                createHousehold("1P",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor1People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[59]) {
            for (i in 0..100) {
                createHousehold("PWOP2P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor2People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[60] - it[61])) {
            for (i in 0..100) {
                createHousehold("PWOP3P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[61]) {
            for (i in 0..100) {
                createHousehold("PWOP3P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[62] - it[63] - it[64])) {
            for (i in 0..100) {
                createHousehold("PWOP4P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[63]) {
            for (i in 0..100) {
                createHousehold("PWOP4P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[64]) {
            for (i in 0..100) {
                createHousehold("PWOP4P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[65] - it[66] - it[67] - it[68])) {
            for (i in 0..100) {
                createHousehold("PWOP5P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[66]) {
            for (i in 0..100) {
                createHousehold("PWOP5P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[67]) {
            for (i in 0..100) {
                createHousehold("PWOP5P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[68]) {
            for (i in 0..100) {
                createHousehold("PWOP5P3C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[69] - it[70] - it[71] - it[72])) {
            for (i in 0..100) {
                createHousehold("PWOP6P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[70]) {
            for (i in 0..100) {
                createHousehold("PWOP6P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[71]) {
            for (i in 0..100) {
                createHousehold("PWOP6P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[72]) {
            for (i in 0..100) {
                createHousehold("PWOP6P3C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[73]) {
            for (i in 0..100) {
                createHousehold("2PWOP4P0С",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[74] - it[75])) {
            for (i in 0..100) {
                createHousehold("2PWOP5P0С",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[75]) {
            for (i in 0..100) {
                createHousehold("2PWOP5P1С",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[76] - it[77] - it[78])) {
            for (i in 0..100) {
                createHousehold("2PWOP6P0С",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[77]) {
            for (i in 0..100) {
                createHousehold("2PWOP6P1С",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[78]) {
            for (i in 0..100) {
                createHousehold("2PWOP6P2С",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[79] - it[80])) {
            for (i in 0..100) {
                createHousehold("SMWC2P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor2People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[80]) {
            for (i in 0..100) {
                createHousehold("SMWC2P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor2People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[81] - it[82] - it[83])) {
            for (i in 0..100) {
                createHousehold("SMWC3P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[82]) {
            for (i in 0..100) {
                createHousehold("SMWC3P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[83]) {
            for (i in 0..100) {
                createHousehold("SMWC3P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[84] - it[85] - it[86] - it[87])) {
            for (i in 0..100) {
                createHousehold("SMWC4P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[85]) {
            for (i in 0..100) {
                createHousehold("SMWC4P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[86]) {
            for (i in 0..100) {
                createHousehold("SMWC4P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[87]) {
            for (i in 0..100) {
                createHousehold("SMWC4P3C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[88] - it[89])) {
            for (i in 0..100) {
                createHousehold("SFWC2P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor2People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[89]) {
            for (i in 0..100) {
                createHousehold("SFWC2P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor2People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[90] - it[91] - it[92])) {
            for (i in 0..100) {
                createHousehold("SFWC3P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[91]) {
            for (i in 0..100) {
                createHousehold("SFWC3P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[92]) {
            for (i in 0..100) {
                createHousehold("SFWC3P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[93] - it[94])) {
            for (i in 0..100) {
                createHousehold("SPWCWP3P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[94]) {
            for (i in 0..100) {
                createHousehold("SPWCWP3P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[95] - it[96] - it[97])) {
            for (i in 0..100) {
                createHousehold("SPWCWP4P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[96]) {
            for (i in 0..100) {
                createHousehold("SPWCWP4P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[97]) {
            for (i in 0..100) {
                createHousehold("SPWCWP4P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[98] - it[99])) {
            for (i in 0..100) {
                createHousehold("SPWCWPWOP3P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[99]) {
            for (i in 0..100) {
                createHousehold("SPWCWPWOP3P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[100] - it[101] - it[102])) {
            for (i in 0..100) {
                createHousehold("SPWCWPWOP4P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[101]) {
            for (i in 0..100) {
                createHousehold("SPWCWPWOP4P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[102]) {
            for (i in 0..100) {
                createHousehold("SPWCWPWOP4P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[103] - it[104] - it[105])) {
            for (i in 0..100) {
                createHousehold("SPWCWPWOP5P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[104]) {
            for (i in 0..100) {
                createHousehold("SPWCWPWOP5P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[105]) {
            for (i in 0..100) {
                createHousehold("SPWCWPWOP5P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[106] - it[107])) {
            for (i in 0..100) {
                createHousehold("O2P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor2People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[107]) {
            for (i in 0..100) {
                createHousehold("O2P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor2People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[108] - it[109] - it[110])) {
            for (i in 0..100) {
                createHousehold("O3P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[109]) {
            for (i in 0..100) {
                createHousehold("O3P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[110]) {
            for (i in 0..100) {
                createHousehold("O3P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[111] - it[112] - it[113])) {
            for (i in 0..100) {
                createHousehold("O4P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[112]) {
            for (i in 0..100) {
                createHousehold("O4P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[113]) {
            for (i in 0..100) {
                createHousehold("O4P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..(it[114] - it[115] - it[116])) {
            for (i in 0..100) {
                createHousehold("O5P0C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[115]) {
            for (i in 0..100) {
                createHousehold("O5P1C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
//            for (i in 0..it[116]) {
            for (i in 0..100) {
                createHousehold("O5P2C",
                        homesFiltered[(0 until (homesFiltered.size)).random()].pos,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix)
            }
            okatoIndex += 1
        }
    }

    private fun makeContactWithInfected(): Boolean {
        return ((0..99).random() < 5)
    }

    fun runSimulation() {
        while(true) {
            println("Day $day")
            households.forEach { household ->
                household.agents.forEach { agent ->
                    if (agent.healthStatus == 0) {
                        household.agents.forEach { agent2 ->
                            if (agent2.healthStatus == 1) {
                                makeContactWithInfected()
                            }
                        }
                    }
                }
            }
            if ((dayOfTheWeek != 7) && (dayOfTheWeek != 6)) {
                kindergartens.forEach { kindergarten ->
                    kindergarten.groupsByAge.forEach { (key, agents) ->
                        agents.forEach { agent ->
                            if (agent.healthStatus == 0) {
                                agents.forEach { agent2 ->
                                    if ((agent2.healthStatus == 1) && (!agent2.isStayingHomeWhenInfected)) {
                                        makeContactWithInfected()
                                    }
                                }
                            }
                        }
                    }
                }
                homes.forEach { home ->
                    home.workingAgents.forEach { agent ->
                        if (agent.healthStatus == 0) {
                            home.workingAgents.forEach { agent2 ->
                                if ((agent2.healthStatus == 1) && (!agent2.isStayingHomeWhenInfected)) {
                                    makeContactWithInfected()
                                }
                            }
                        }
                    }
                }
            }
            if (dayOfTheWeek != 7) {
                schools.forEach { school ->
                    school.groupsByAge.forEach { (key, agents) ->
                        agents.forEach { agent ->
                            if (agent.healthStatus == 0) {
                                agents.forEach { agent2 ->
                                    if ((agent2.healthStatus == 1) && (!agent2.isStayingHomeWhenInfected)) {
                                        makeContactWithInfected()
                                    }
                                }
                            }
                        }
                    }
                }
                universities.forEach { university ->
                    university.groupsByAge.forEach { (key, agents) ->
                        agents.forEach { agent ->
                            if (agent.healthStatus == 0) {
                                agents.forEach { agent2 ->
                                    if ((agent2.healthStatus == 1) && (!agent2.isStayingHomeWhenInfected)) {
                                        makeContactWithInfected()
                                    }
                                }
                            }
                        }
                    }
                }
                colleges.forEach { college ->
                    college.groupsByAge.forEach { (key, agents) ->
                        agents.forEach { agent ->
                            if (agent.healthStatus == 0) {
                                agents.forEach { agent2 ->
                                    if ((agent2.healthStatus == 1) && (!agent2.isStayingHomeWhenInfected)) {
                                        makeContactWithInfected()
                                    }
                                }
                            }
                        }
                    }
                }
            }
            households.forEach { household ->
                household.agents.forEach { agent ->
                    when (agent.healthStatus) {
                        3 -> {
                            agent.healthStatus = 1
                            susceptible -= 1
                            infected += 1
                            agent.daysInfected = 1
                            agent.isStayingHomeWhenInfected = agent.shouldStayAtHome(agent.daysInfected)
                        }
                        1 -> {
                            agent.daysInfected += 1
                            if (agent.daysInfected == 8) {
                                agent.healthStatus = 2
                                infected -= 1
                                recovered += 1
                                agent.daysInfected = 0
                                agent.daysRecovered = 1
                                agent.isStayingHomeWhenInfected = false
                            } else {
                                if (!agent.isStayingHomeWhenInfected) {
                                    agent.isStayingHomeWhenInfected = agent.shouldStayAtHome(agent.daysInfected)
                                }
                            }
                        }
                        2 -> {
                            agent.daysRecovered += 1
                            if (agent.daysRecovered == 62) {
                                agent.healthStatus = 0
                                agent.daysRecovered = 0
                                recovered -= 1
                                susceptible += 1
                            }
                        }

                    }
                }
            }
            dayOfTheWeek += 1
            if (dayOfTheWeek == 8) {
                dayOfTheWeek = 1
            }
            day += 1
        }
    }

    init {
        createKindergartens()
        createSchools()
        createColleges()
        createUniversities()
        createHomes()

        val ageDistributionInDistrictsMatrix = arrayListOf<ArrayList<Int>>()
        readTableInt("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\Tables\\age-num-of-people-districts.xlsx",
                18, 535, ageDistributionInDistrictsMatrix)

        val districtsInfoMatrix = arrayListOf<ArrayList<Int>>()
        readTableInt("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\Tables\\districts.xlsx",
                107, 163, districtsInfoMatrix)
        addHouseholdsToPool(districtsInfoMatrix, ageDistributionInDistrictsMatrix)
    }
}