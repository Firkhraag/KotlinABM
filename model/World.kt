package model

import kotlin.math.*
import utility.readTableFloat
import utility.readTableInt
import utility.writeTableResult
import org.apache.commons.math3.distribution.GammaDistribution
import kotlinx.coroutines.*

class World {

    private val temp = arrayListOf(19.4, 19.4, 30.2, 44.6, 55.4, 62.6, 66.2, 62.6, 51.8, 42.8, 30.2, 23.0)
    private val maxTemp = 66.2
//    private val minTemp = 19.4
//    private val maxMinTemp = 46.8
//
//    private fun scaler(value: Double, minVal: Double, maxminVal: Double): Double {
//        return (value - minVal) / maxminVal * 10
//    }

    private var day = 1
    private var month = 8
    private var year = 1

    private var globalDay = 0
    private var dayOfTheWeek = 1

    // Susceptible, Infected, New Cases
    private var worldStats = arrayListOf(0, 0, 0, 0)
//    var weekCases = 0
//
//    var infected = 0
//    var recovered = 0
//    var susceptible = 0

    private val kindergartens = arrayListOf<Kindergarten>()
    private val schools = arrayListOf<School>()
    private val colleges = arrayListOf<College>()
    private val universities = arrayListOf<University>()
    private val homes = arrayListOf<Home>()
    private val households = arrayListOf<Household>()

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
            if ((parentAge != null) && (parentAge < 32)) {
                randomNum = 1
            } else if ((parentAge != null) && (parentAge > 60)) {
                randomNum = 100
            }
            when(randomNum) {
                in 1..ageDistributionInDistrictsMatrix[16][biasedIndex] -> {
                    randomNum = (1..100).random()
                    return when(parentAge) {
                        18 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                Agent(true, 0)
                            } else {
                                Agent(false, 0)
                            }
                        }
                        19 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                Agent(true, (0..1).random())
                            } else {
                                Agent(false, (0..1).random())
                            }
                        }
                        20 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                Agent(true, (0..2).random())
                            } else {
                                Agent(false, (0..2).random())
                            }
                        }
                        21 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                Agent(true, (0..3).random())
                            } else {
                                Agent(false, (0..3).random())
                            }
                        }
                        22 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                Agent(true, (0..4).random())
                            } else {
                                Agent(false, (0..4).random())
                            }
                        }
                        23 -> {
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
                                    Agent(true, 5)
                                } else {
                                    Agent(false, 5)
                                }
                            }
                        }
                        24 -> {
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
                                    Agent(true, (5..6).random())
                                } else {
                                    Agent(false, (5..6).random())
                                }
                            }
                        }
                        25 -> {
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
                                    Agent(true, (5..7).random())
                                } else {
                                    Agent(false, (5..7).random())
                                }
                            }
                        }
                        26 -> {
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
                                    Agent(true, (5..8).random())
                                } else {
                                    Agent(false, (5..8).random())
                                }
                            }
                        }
                        27 -> {
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
                        28 -> {
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
                                    Agent(true, 10)
                                } else {
                                    Agent(false, 10)
                                }
                            }
                        }
                        29 -> {
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
                                    Agent(true, (10..11).random())
                                } else {
                                    Agent(false, (10..11).random())
                                }
                            }
                        }
                        30 -> {
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
                                    Agent(true, (10..12).random())
                                } else {
                                    Agent(false, (10..12).random())
                                }
                            }
                        }
                        31 -> {
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
                                    Agent(true, (10..13).random())
                                } else {
                                    Agent(false, (10..13).random())
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

    private fun addSpouse(agentMale: Agent): Agent {
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
        var spouseAge = agentMale.age + difference
        if (spouseAge < 18) {
            spouseAge = 18
        } else if (spouseAge > 80) {
            spouseAge = 80
        }
        return Agent(false, spouseAge)
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

//    @Synchronized private fun addAgentsToGroups(agent: Agent, household: Household,
//                                 closestKindergarten: Kindergarten,
//                                 closestSchool: School) {
//        when (agent.activityStatus) {
//            1 -> closestKindergarten.addAgent(agent)
//            2 -> if (agent.age < 14) closestSchool.addAgent(agent) else schools[(0 until schools.size).random()].addAgent(agent)
//            3 -> colleges[(0 until colleges.size).random()].addAgent(agent)
//            4 -> universities[(0 until universities.size).random()].addAgent(agent)
//            5 -> homes[(0 until homes.size).random()].addWorkingAgent(agent)
//        }
//
//        when (agent.healthStatus) {
//            0 -> worldStats[0] += 1
//            1 -> worldStats[1] += 1
//            2 -> worldStats[2] += 1
//        }
//        household.addAgent(agent)
//    }

    @Synchronized private fun addAgentsToGroups(agents: ArrayList<Agent>, household: Household,
                                               closestKindergarten: Kindergarten,
                                               closestSchool: School) {
        agents.forEach { agent ->
            when (agent.activityStatus) {
                1 -> closestKindergarten.addAgent(agent)
                2 -> if (agent.age < 14) closestSchool.addAgent(agent) else schools[(0 until schools.size).random()].addAgent(agent)
                3 -> colleges[(0 until colleges.size).random()].addAgent(agent)
                4 -> universities[(0 until universities.size).random()].addAgent(agent)
                5 -> homes[(0 until homes.size).random()].addWorkingAgent(agent)
            }

            when (agent.healthStatus) {
                0 -> worldStats[0] += 1
                1 -> worldStats[1] += 1
                2 -> worldStats[2] += 1
            }
            household.addAgent(agent)
        }
        households.add(household)
    }

    suspend fun processAll(districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                           ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>) = withContext(Dispatchers.IO) {

        districtsInfoMatrix.forEachIndexed { index, it ->
            launch {
                val currOkato = it[0]
                println("Current index $index")
                val homesFiltered = homes.filter { home -> home.okato == currOkato }
                val indexFor1People = index * 5 + 1
                val indexFor2People = index * 5 + 2
                val indexFor3People = index * 5 + 3
                val indexFor4People = index * 5 + 4
                val indexFor5People = index * 5 + 5

                for (i in 0..it[58]) {
//                for (i in 1..100) {
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "1P")

                    val agent = createAgent(index, indexFor1People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    addAgentsToGroups(arrayListOf(agent), household,closestKindergarten,closestSchool)
                }
                for (i in 0..it[59]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP2P0C")

                    val agentMale = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[60] - it[61])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP3P0C")

                    val agentMale = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[61]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP3P1C")

                    val agentMale = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[62] - it[63] - it[64])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP4P0C")

                    val agentMale = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[63]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP4P1C")

                    val agentMale = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[64]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP4P2C")

                    val agentMale = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[65] - it[66] - it[67] - it[68])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP5P0C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[66]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP5P1C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[67]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP5P2C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[68]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP5P3C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[69] - it[70] - it[71] - it[72])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP6P0C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[70]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP6P1C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[71]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP6P2C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[72]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP6P3C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[73]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP4P0С")

                    val agentMale = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale2 = addSpouse(agentMale2)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[74] - it[75])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP5P0С")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale2 = addSpouse(agentMale2)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[75]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP5P1С")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale2 = addSpouse(agentMale2)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[76] - it[77] - it[78])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP6P0С")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale2 = addSpouse(agentMale2)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[77]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP6P1С")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale2 = addSpouse(agentMale2)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[78]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP6P2С")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale2 = addSpouse(agentMale2)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale2.age)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[79] - it[80])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC2P0C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, true)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[80]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC2P1C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, false)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[81] - it[82] - it[83])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, true)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent3.age < 18) {
                        agent3.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[82]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, true)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[83]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC3P2C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[84] - it[85] - it[86] - it[87])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC4P0C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, true)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent3.age < 18) {
                        agent3.age = agent.age - 18
                    }
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent4.age < 18) {
                        agent4.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[85]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, true)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent3.age < 18) {
                        agent3.age = agent.age - 18
                    }
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[86]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC4P2C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, true)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[87]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC4P3C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[88] - it[89])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SFWC2P0C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, true)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[89]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SFWC2P1C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[90] - it[91] - it[92])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SFWC3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, true)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent3.age < 18) {
                        agent3.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[91]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SFWC3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, true)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[92]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SFWC3P2C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[93] - it[94])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWP3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[94]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWP3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[95] - it[96] - it[97])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWP4P0C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[96]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWP4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[97]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWP4P2C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[98] - it[99])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[99]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[55] - it[101] - it[102])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP4P0C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[101]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[102]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP4P2C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[103] - it[104] - it[105])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP5P0C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[104]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP5P1C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[105]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP5P2C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[106] - it[107])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O2P0C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[107]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O2P1C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[108] - it[109] - it[110])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[109]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[110]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O3P2C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[111] - it[112] - it[113])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O4P0C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[112]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[113]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O4P2C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..(it[114] - it[115] - it[116])) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O5P0C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[115]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O5P1C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
                for (i in 0..it[116]) {
                    val agents = arrayListOf<Agent>()
                    val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                    val closestKindergartenIndex = findClosestKindergarten(pos)
                    val closestSchoolIndex = findClosestSchool(pos)
                    val closestKindergarten = kindergartens[closestKindergartenIndex]
                    val closestSchool = schools[closestSchoolIndex]
                    val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O5P2C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household, closestKindergarten, closestSchool)
                }
            }
        }
    }

    private fun addHouseholdsToPool(districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                                    ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>) {
        runBlocking {
            processAll(districtsInfoMatrix, ageDistributionInDistrictsMatrix)
        }
        println("World creation has ended")
    }

    private fun contactsInHouseholdForAgent(household: Household, agent: Agent, externalFactor: Double) {
//        val risk = 1 / (1 + exp(-30.0 / household.agents.size))
        val risk = exp(-1 / (1 + exp(-household.agents.size / 30.0)) + 0.5)
        val shapeParamChild = 2.0
        var scaleParamChild = 3.0
        val infectivity = if (agent.age > 17) {
            GammaDistribution(shapeParamChild, scaleParamChild).density(agent.daysInfected.toDouble()) * 0.5
        } else {
            GammaDistribution(shapeParamChild, scaleParamChild).density(agent.daysInfected.toDouble())
        }
        household.agents.forEach { agent2 ->
            if (agent2.healthStatus == 0) {
//                val internalFactor = 1.2 / (1 + exp(-90.0 / agent.age))
                val internalFactor = exp(-1 / (1 + exp(-agent.age /90.0)) + 0.5)
                val randNum = (0..9999).random() * 0.0001
                if (randNum < infectivity * externalFactor * internalFactor * risk) {
                    agent2.healthStatus = 3
                }
            }
        }
    }

    private fun contactsInGroupForAgent(group: Group, agent: Agent, externalFactor: Double) {
//        val risk = 1 / (1 + exp(-30.0 / group.agents.size))
        val risk = exp(-1 / (1 + exp(-group.agents.size / 30.0)) + 0.5)
        val shapeParamChild = 2.0
        var scaleParamChild = 3.0
        val infectivity = if (agent.age > 17) {
            GammaDistribution(shapeParamChild, scaleParamChild).density(agent.daysInfected.toDouble()) * 0.5
        } else {
            GammaDistribution(shapeParamChild, scaleParamChild).density(agent.daysInfected.toDouble())
        }
        group.agents.forEach { agent2 ->
            if (agent2.healthStatus == 0) {
//                val internalFactor = 0.8 / (1 + exp(-90.0 / agent.age))
                val internalFactor = exp(-1 / (1 + exp(-agent.age /90.0)) + 0.5)
                val randNum = (0..9999).random() * 0.0001
                if (randNum < infectivity * externalFactor * internalFactor * risk) {
                    agent2.healthStatus = 3
                }
            }
        }
    }

    fun runSimulation() {

        val coeffR = 6.5
        val deviationR = -0.13
        val coeffT = 1.3
        val deviationT = 0.54
        val coeffD = 6.0
//        val coeffDKinder = 6
//        val coeffDSchool = 6
//        val coeffDCollege = 6
//        val coeffDUniv = 6
//        val coeffDHome = 6
//        val coeffDWork = 6
//        val deviationD = 0.1
        val deviationDKinder = 0.1
        val deviationDSchool = 0.1
        val deviationDCollege = 0.1
        val deviationDUniv = 0.1
        val deviationDHome = 0.1
        val deviationDWork = 0.1
        val coeffA = 1.09
        val deviationA = -0.01

        worldStats[3] = worldStats[1] / 7
        while(true) {

            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\output\\results.xlsx",
                    globalDay, worldStats)



//            var test = 0
//            println("World before: ${worldStats[1]}")
//            households.forEach { household ->
//                household.agents.forEach { agent ->
//                    if (agent.healthStatus == 1) {
//                        test += 1
//                    }
//                }
//            }
//            println("Actually: $test")

//            val externalFactor = 1 / (1 + exp(-maxTemp / temp[month - 1])) * exp(-1 / (1 +exp(-worldStats[3]/23676.0)) + 0.5)
            val externalFactor = exp(-1 / (1 + exp(-temp[month - 1] / maxTemp)) + 0.5) * exp(-1 / (1 +exp(-worldStats[3]/23676.0)) + 0.5)
            worldStats[3] = 0

            households.parallelStream().forEach { household ->
//            households.forEach { household ->
                household.agents.forEach { agent ->
                    if (agent.healthStatus == 1) {
                        contactsInHouseholdForAgent(household, agent, externalFactor)
                    }
                }
            }
            if (((month != 1) || ((month == 1) && (day !in arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)))) &&
                    ((month != 5) || ((month == 5) && (day !in arrayListOf(1, 9)))) &&
                    ((month != 2) || ((month == 2) && (day != 23))) &&
                    ((month != 3) || ((month == 3) && (day != 8))) &&
                    ((month != 11) || ((month == 11) && (day != 4)))) {
                if ((dayOfTheWeek != 7) && (dayOfTheWeek != 6)) {
                    if (month !in arrayListOf(6, 7, 8)) {
                        kindergartens.parallelStream().forEach { kindergarten ->
//                        kindergartens.forEach { kindergarten ->
                            kindergarten.groupsByAge.forEach { (_, groupByAge) ->
                                groupByAge.forEach { group ->
                                    group.agents.forEach { agent ->
                                        if ((agent.healthStatus == 1) &&
                                                (!agent.isStayingHomeWhenInfected)) {
                                            contactsInGroupForAgent(group, agent, externalFactor)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    homes.parallelStream().forEach { home ->
//                    homes.forEach { home ->
                        home.workingGroups.forEach { group ->
                            group.agents.forEach { agent ->
                                if ((agent.healthStatus == 1) &&
                                        (!agent.isStayingHomeWhenInfected)) {
                                    contactsInGroupForAgent(group, agent, externalFactor)
                                }
                            }
                        }
                    }
                }
                if ((dayOfTheWeek != 7) && (month !in arrayListOf(6, 7, 8))) {
                    if (((month != 3) || ((month == 3) && (day !in arrayListOf(21, 22, 23, 24, 25, 26, 27, 28, 29)))) &&
                            ((month != 10) || ((month == 10) && (day !in arrayListOf(28, 29, 30, 31)))) &&
                            ((month != 11) || ((month == 11) && (day !in arrayListOf(1, 2, 3))))) {
                        schools.parallelStream().forEach { school ->
//                        schools.forEach { school ->
                            school.groupsByAge.forEach { (_, groupByAge) ->
                                groupByAge.forEach { group ->
                                    group.agents.forEach { agent ->
                                        if ((agent.healthStatus == 1) &&
                                                (!agent.isStayingHomeWhenInfected)) {
                                            contactsInGroupForAgent(group, agent, externalFactor)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    universities.parallelStream().forEach { university ->
//                    universities.forEach { university ->
                        university.groupsByAge.forEach { (_, groupByAge) ->
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if ((agent.healthStatus == 1) &&
                                            (!agent.isStayingHomeWhenInfected)) {
                                        contactsInGroupForAgent(group, agent, externalFactor)
                                    }
                                }
                            }
                        }
                    }
                    colleges.parallelStream().forEach { college ->
//                    colleges.forEach { college ->
                        college.groupsByAge.forEach { (_, groupByAge) ->
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if ((agent.healthStatus == 1) &&
                                            (!agent.isStayingHomeWhenInfected)) {
                                        contactsInGroupForAgent(group, agent, externalFactor)
                                    }
                                }
                            }
                        }
                    }
                }
                else if (dayOfTheWeek == 7) {
                    households.parallelStream().forEach { household ->
//                    households.forEach { household ->
                        household.agents.forEach { agent ->
                            if (agent.healthStatus == 1) {
                                contactsInHouseholdForAgent(household, agent, externalFactor)
                            }
                        }
                    }
                }
            } else {
                households.parallelStream().forEach { household ->
//                households.forEach { household ->
                    household.agents.forEach { agent ->
                        if (agent.healthStatus == 1) {
                            contactsInHouseholdForAgent(household, agent, externalFactor)
                        }
                    }
                }
            }
            households.forEach { household ->
                household.agents.forEach { agent ->
//                    if (agent.healthStatus == 1) {
//                        println("Age: ${agent.age}, Days: ${agent.daysInfected}, Should: ${agent.shouldBeInfected}")
//
//                    }
                    when (agent.healthStatus) {
                        3 -> {
                            agent.healthStatus = 1
                            worldStats[0] -= 1
                            worldStats[1] += 1
                            worldStats[3] += 1
                            agent.daysInfected = 1
//                            agent.shouldBeInfected = agent.willBeInfected()
                            agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
                        }
                        1 -> {
                            //println("Id: ${agent.id}; Infected: ${agent.daysInfected}; ShouldBeInfected: ${agent.shouldBeInfected}")
                            if (agent.daysInfected == agent.shouldBeInfected) {
                                agent.healthStatus = 2
                                worldStats[1] -= 1
                                worldStats[2] += 1
//                                agent.daysInfected = 0
                                agent.isStayingHomeWhenInfected = false
                            } else {
                                agent.daysInfected += 1
                                if (!agent.isStayingHomeWhenInfected) {
                                    agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
                                }
                            }
                        }
                        0 -> {
                            if ((0..9999).random() == 0) {
                                agent.healthStatus = 1
                                worldStats[0] -= 1
                                worldStats[1] += 1
                                worldStats[3] += 1
                                agent.daysInfected = 1
//                                agent.shouldBeInfected = agent.willBeInfected()
                                agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
                            }
                        }
                    }
                }
            }
            //readLine()
            dayOfTheWeek += 1
            if (dayOfTheWeek == 8) {
                dayOfTheWeek = 1
            }
            day += 1
            globalDay += 1

            if ((month in arrayListOf(1, 3, 5, 7, 8, 10) && (day == 32)) ||
                    (month in arrayListOf(4, 6, 9, 11) && (day == 31)) ||
                    (month == 2) and (day == 29)) {
                if (month == 7) {
                    break
                }
                day = 1
                month += 1
                println("Month $month")
            } else if ((month == 12) && (day == 32)) {
                day = 1
                month = 1
                year += 1
                println("Month 1")
            }
        }
    }

//    fun runSimulation() {
//
//        worldStats[3] = worldStats[1] / 7
//        while(true) {
//
//            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\output\\results.xlsx",
//                    globalDay, worldStats)
//
//            val externalFactor = 1 / (1 + exp(-maxTemp / temp[month - 1])) * exp(-1 / (1 +exp(-worldStats[3]/23676.0)) + 0.5)
//            worldStats[3] = 0
//
//            households.parallelStream().forEach { household ->
//                household.agents.forEach { agent ->
//                    if (agent.healthStatus == 1) {
//                        contactsInHouseholdForAgent(household, agent, externalFactor)
//                    }
//                }
//            }
//            if (((month != 1) || ((month == 1) && (day !in arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)))) &&
//                    ((month != 5) || ((month == 5) && (day !in arrayListOf(1, 9)))) &&
//                    ((month != 2) || ((month == 2) && (day != 23))) &&
//                    ((month != 3) || ((month == 3) && (day != 8))) &&
//                    ((month != 11) || ((month == 11) && (day != 4)))) {
//                if ((dayOfTheWeek != 7) && (dayOfTheWeek != 6)) {
//                    if (month !in arrayListOf(6, 7, 8)) {
//
//                        kindergartens.parallelStream().forEach { kindergarten ->
//                            kindergarten.groupsByAge.forEach { (_, groupByAge) ->
//                                groupByAge.forEach { group ->
//                                    group.agents.forEach { agent ->
//                                        if ((agent.healthStatus == 1) &&
//                                                (!agent.isStayingHomeWhenInfected)) {
//                                            contactsInGroupForAgent(group, agent, externalFactor)
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    homes.parallelStream().forEach { home ->
//                        home.workingGroups.forEach { group ->
//                            group.agents.forEach { agent ->
//                                if ((agent.healthStatus == 1) &&
//                                        (!agent.isStayingHomeWhenInfected)) {
//                                    contactsInGroupForAgent(group, agent, externalFactor)
//                                }
//                            }
//                        }
//                    }
//                }
//                if ((dayOfTheWeek != 7) && (month !in arrayListOf(6, 7, 8))) {
//                    if (((month != 3) || ((month == 3) && (day !in arrayListOf(21, 22, 23, 24, 25, 26, 27, 28, 29)))) &&
//                            ((month != 10) || ((month == 10) && (day !in arrayListOf(28, 29, 30, 31)))) &&
//                            ((month != 11) || ((month == 11) && (day !in arrayListOf(1, 2, 3))))) {
//                        schools.parallelStream().forEach { school ->
//                            school.groupsByAge.forEach { (_, groupByAge) ->
//                                groupByAge.forEach { group ->
//                                    group.agents.forEach { agent ->
//                                        if ((agent.healthStatus == 1) &&
//                                                (!agent.isStayingHomeWhenInfected)) {
//                                            contactsInGroupForAgent(group, agent, externalFactor)
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    universities.parallelStream().forEach { university ->
//                        university.groupsByAge.forEach { (_, groupByAge) ->
//                            groupByAge.forEach { group ->
//                                group.agents.forEach { agent ->
//                                    if ((agent.healthStatus == 1) &&
//                                            (!agent.isStayingHomeWhenInfected)) {
//                                        contactsInGroupForAgent(group, agent, externalFactor)
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    colleges.parallelStream().forEach { college ->
//                        college.groupsByAge.forEach { (_, groupByAge) ->
//                            groupByAge.forEach { group ->
//                                group.agents.forEach { agent ->
//                                    if ((agent.healthStatus == 1) &&
//                                            (!agent.isStayingHomeWhenInfected)) {
//                                        contactsInGroupForAgent(group, agent, externalFactor)
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                else if (dayOfTheWeek == 7) {
//                    households.parallelStream().forEach { household ->
//                        household.agents.forEach { agent ->
//                            if (agent.healthStatus == 1) {
//                                contactsInHouseholdForAgent(household, agent, externalFactor)
//                            }
//                        }
//                    }
//                }
//            } else {
//                households.parallelStream().forEach { household ->
//                    household.agents.forEach { agent ->
//                        if (agent.healthStatus == 1) {
//                            contactsInHouseholdForAgent(household, agent, externalFactor)
//                        }
//                    }
//                }
//            }
//            households.forEach { household ->
//                household.agents.forEach { agent ->
//                    when (agent.healthStatus) {
//                        3 -> {
//                            agent.healthStatus = 1
//                            worldStats[0] -= 1
//                            worldStats[1] += 1
//                            worldStats[3] += 1
//                            agent.daysInfected = 1
//                            agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
//                        }
//                        1 -> {
//                            if (agent.daysInfected == agent.shouldBeInfected) {
//                                agent.healthStatus = 2
//                                worldStats[1] -= 1
//                                worldStats[2] += 1
//                                agent.isStayingHomeWhenInfected = false
//                            } else {
//                                agent.daysInfected += 1
//                                if (!agent.isStayingHomeWhenInfected) {
//                                    agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
//                                }
//                            }
//                        }
//                        0 -> {
//                            if ((0..9999).random() == 0) {
//                                agent.healthStatus = 1
//                                worldStats[0] -= 1
//                                worldStats[1] += 1
//                                worldStats[3] += 1
//                                agent.daysInfected = 1
//                                agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
//                            }
//                        }
//                    }
//                }
//            }
//            dayOfTheWeek += 1
//            if (dayOfTheWeek == 8) {
//                dayOfTheWeek = 1
//            }
//            day += 1
//            globalDay += 1
//
//            if ((month in arrayListOf(1, 3, 5, 7, 8, 10) && (day == 32)) ||
//                    (month in arrayListOf(4, 6, 9, 11) && (day == 31)) ||
//                    (month == 2) and (day == 29)) {
//                if (month == 7) {
//                    break
//                }
//                day = 1
//                month += 1
//                println("Month $month")
//            } else if ((month == 12) && (day == 32)) {
//                day = 1
//                month = 1
//                year += 1
//                println("Month 1")
//            }
//        }
//    }

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