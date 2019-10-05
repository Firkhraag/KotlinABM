package model

import kotlin.math.*
import utility.readTableFloat
import utility.readTableInt
import utility.writeTableResult

class World {

    private var hour = 0
    private var day = 1
    private var month = 1
    private var year = 1

    private var globalDay = 0
    private var dayOfTheWeek = 1

    // Susceptible, Infected, Recovered, New Cases
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
            0 -> worldStats[0] += 1
            1 -> worldStats[1] += 1
            2 -> worldStats[2] += 1
        }
        household.addAgent(agent)
    }

    private fun addHouseholdsToPool(districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                                    ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>) {
        var okatoIndex = 0
//        districtsInfoMatrix.parallelStream().forEach {
        districtsInfoMatrix.forEach {
            val currOkato = it[0]
            println(currOkato)
            val homesFiltered = homes.filter { home -> home.okato == currOkato }
            val indexFor1People = okatoIndex * 5 + 1
            val indexFor2People = okatoIndex * 5 + 2
            val indexFor3People = okatoIndex * 5 + 3
            val indexFor4People = okatoIndex * 5 + 4
            val indexFor5People = okatoIndex * 5 + 5

//            for (i in 0..it[58]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "1P")

                val agent = createAgent(okatoIndex, indexFor1People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[59]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP2P0C")

                addPair(household, okatoIndex,
                        indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten, closestSchool, null)
                households.add(household)
            }
//            for (i in 0..(it[60] - it[61])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP3P0C")

                addPair(household,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[61]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP3P1C")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor3People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[62] - it[63] - it[64])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP4P0C")

                addPair(household,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[63]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP4P1C")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[64]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP4P2C")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[65] - it[66] - it[67] - it[68])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP5P0C")

                addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[66]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP5P1C")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[67]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP5P2C")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[68]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP5P3C")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[69] - it[70] - it[71] - it[72])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP6P0C")

                addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[70]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP6P1C")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[71]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP6P2C")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[72]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "PWOP6P3C")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[73]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP4P0С")

                addPair(household,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                addPair(household,
                        okatoIndex,
                        indexFor4People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                households.add(household)
            }
//            for (i in 0..(it[74] - it[75])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP5P0С")

                addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[75]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP5P1С")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[76] - it[77] - it[78])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP6P0С")

                addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[77]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP6P1С")

                val parentAge = addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        null)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[78]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "2PWOP6P2С")

                val parentAge1 = addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val parentAge2 = addPair(household,
                        okatoIndex,
                        indexFor5People,
                        districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        closestKindergarten,
                        closestSchool,
                        false)
                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge1)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, parentAge2)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[79] - it[80])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC2P0C")

                val agent = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[80]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC2P1C")

                val agent = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[81] - it[82] - it[83])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC3P0C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent3.age < 18) {
                    agent3.age = agent.age - 18
                }
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[82]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC3P1C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[83]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC3P2C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[84] - it[85] - it[86] - it[87])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC4P0C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent3.age < 18) {
                    agent3.age = agent.age - 18
                }
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent4.age < 18) {
                    agent4.age = agent.age - 18
                }
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[85]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC4P1C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent3.age < 18) {
                    agent3.age = agent.age - 18
                }
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[86]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC4P2C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[87]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SMWC4P3C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, false, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[88] - it[89])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SFWC2P0C")

                val agent = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[89]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SFWC2P1C")

                val agent = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[90] - it[91] - it[92])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SFWC3P0C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent3.age < 18) {
                    agent3.age = agent.age - 18
                }
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[91]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SFWC3P1C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, true)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                if (agent.age - agent2.age < 18) {
                    agent2.age = agent.age - 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[92]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SFWC3P2C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[93] - it[94])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWP3P0C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[94]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWP3P1C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                if (agent2.age - agent.age < 18) {
                    agent2.age = agent.age + 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[95] - it[96] - it[97])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWP4P0C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[96]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWP4P1C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[97]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWP4P2C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                if (agent2.age - agent.age < 18) {
                    agent2.age = agent.age + 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[98] - it[99])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP3P0C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[99]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP3P1C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                if (agent2.age - agent.age < 18) {
                    agent2.age = agent.age + 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[19] - it[101] - it[102])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP4P0C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[101]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP4P1C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[102]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP4P2C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                if (agent2.age - agent.age < 18) {
                    agent2.age = agent.age + 18
                }
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[103] - it[104] - it[105])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP5P0C")

                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[104]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP5P1C")

                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[105]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "SPWCWPWOP5P2C")

                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, true, null, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false, null, null, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agent.age)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[106] - it[107])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O2P0C")

                val agent = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[107]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O2P1C")

                val agent = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[108] - it[109] - it[110])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O3P0C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[109]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O3P1C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[110]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O3P2C")

                val agent = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[111] - it[112] - it[113])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O4P0C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[112]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O4P1C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[113]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O4P2C")

                val agent = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..(it[114] - it[115] - it[116])) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O5P0C")

                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[115]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O5P1C")

                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
                households.add(household)
            }
//            for (i in 0..it[116]) {
            for (i in 0..19) {
                val pos = homesFiltered[(0 until (homesFiltered.size)).random()].pos
                val closestKindergartenIndex = findClosestKindergarten(pos)
                val closestSchoolIndex = findClosestSchool(pos)
                val closestKindergarten = kindergartens[closestKindergartenIndex]
                val closestSchool = schools[closestSchoolIndex]
                val household = Household(pos, closestSchoolIndex, closestKindergartenIndex, "O5P2C")

                val agent = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent, household, closestKindergarten, closestSchool)
                val agent2 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent2, household, closestKindergarten, closestSchool)
                val agent3 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                addAgentToGroups(agent3, household, closestKindergarten, closestSchool)
                val agent4 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent4, household, closestKindergarten, closestSchool)
                val agent5 = createAgent(okatoIndex, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true)
                addAgentToGroups(agent5, household, closestKindergarten, closestSchool)
                households.add(household)
            }
            okatoIndex += 1
        }
    }

    private fun makeContactWithInfected(exposedAgentAge: Int, infectiousAgentAge: Int, placeType: Int): Boolean {
        var randNum = (0..99).random()
        when (placeType) {
            0 -> {
                if (exposedAgentAge <= 18) {
                    if (infectiousAgentAge <= 18) {
                        if (randNum < 80) {
                            return true
                        }
                    } else {
                        if (randNum < 25) {
                            return true
                        }
                    }
                    return false
                } else {
                    if (infectiousAgentAge <= 18) {
                        if (randNum < 35) {
                            return true
                        }
                    } else {
                        if (randNum < 40) {
                            return true
                        }
                    }
                    return false
                }
            }
            1 -> {
                if (randNum < 28) {
                    return true
                }
                return false
            }
            2 -> {
                when (infectiousAgentAge) {
                    in 7..12 -> {
                        if (randNum < 5) {
                            return true
                        }
                    }
                    in 13..16 -> {
                        if (randNum < 4) {
                            return true
                        }
                    }
                    else -> {
                        if (randNum < 3) {
                            return true
                        }
                    }
                }
                return false
            }
            3, 4 -> {
                if (randNum < 2) {
                    return true
                }
                return false
            }
            5 -> {
                if (randNum < 5) {
                    return true
                }
                return false
            }
            6 -> {
                randNum = (0..10000).random()
                if (randNum < 1) {
                    return true
                }
                return false
            }
        }
        return ((0..99).random() < 5)
    }

    fun runSimulation() {
        while(true) {
            println("Day $day")
//            households.parallelStream().forEach { household ->
            households.forEach { household ->
                household.agents.forEach { agent ->
                    if (agent.healthStatus == 0) {
                        household.agents.forEach { agent2 ->
                            if (agent2.healthStatus == 1) {
                                if (makeContactWithInfected(agent.age, agent2.age, 0)) {
                                    agent.healthStatus = 3
                                }
                            }
                        }
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
//                    kindergartens.parallelStream().forEach { kindergarten ->
                        kindergartens.forEach { kindergarten ->
                            kindergarten.groupsByAge.forEach { (_, groupByAge) ->
                                groupByAge.forEach { group ->
                                    group.agents.forEach { agent ->
                                        if (agent.healthStatus == 0) {
                                            group.agents.forEach { agent2 ->
                                                if ((agent2.healthStatus == 1) && (!agent2.isStayingHomeWhenInfected)) {
                                                    if (makeContactWithInfected(agent.age, agent2.age, 1)) {
                                                        agent.healthStatus = 3
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                homes.parallelStream().forEach { home ->
                    homes.forEach { home ->
                        home.workingGroups.forEach { group ->
                            group.agents.forEach { agent ->
                                if (agent.healthStatus == 0) {
                                    group.agents.forEach { agent2 ->
                                        if ((agent2.healthStatus == 1) && (!agent2.isStayingHomeWhenInfected)) {
                                            if (makeContactWithInfected(agent.age, agent2.age, 5)) {
                                                agent.healthStatus = 3
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if ((dayOfTheWeek != 7) && (month !in arrayListOf(6, 7, 8))) {
                    if (((month != 3) || ((month == 3) && (day !in arrayListOf(21, 22, 23, 24, 25, 26, 27, 28, 29)))) &&
                            ((month != 10) || ((month == 10) && (day !in arrayListOf(28, 29, 30, 31)))) &&
                            ((month != 11) || ((month == 11) && (day !in arrayListOf(1, 2, 3)))))
//                schools.parallelStream().forEach { school ->
                    schools.forEach { school ->
                        school.groupsByAge.forEach { (_, groupByAge) ->
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if (agent.healthStatus == 0) {
                                        group.agents.forEach { agent2 ->
                                            if ((agent2.healthStatus == 1) && (!agent2.isStayingHomeWhenInfected)) {
                                                if (makeContactWithInfected(agent.age, agent2.age, 2)) {
                                                    agent.healthStatus = 3
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                universities.parallelStream().forEach { university ->
                    universities.forEach { university ->
                        university.groupsByAge.forEach { (_, groupByAge) ->
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if (agent.healthStatus == 0) {
                                        group.agents.forEach { agent2 ->
                                            if ((agent2.healthStatus == 1) && (!agent2.isStayingHomeWhenInfected)) {
                                                if (makeContactWithInfected(agent.age, agent2.age, 4)) {
                                                    agent.healthStatus = 3
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                colleges.parallelStream().forEach { college ->
                    colleges.forEach { college ->
                        college.groupsByAge.forEach { (_, groupByAge) ->
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if (agent.healthStatus == 0) {
                                        group.agents.forEach { agent2 ->
                                            if ((agent2.healthStatus == 1) && (!agent2.isStayingHomeWhenInfected)) {
                                                if (makeContactWithInfected(agent.age, agent2.age, 3)) {
                                                    agent.healthStatus = 3
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
//            households.parallelStream().forEach { household ->
            households.forEach { household ->
                household.agents.forEach { agent ->
                    when (agent.healthStatus) {
                        3 -> {
                            agent.healthStatus = 1

                            worldStats[0] -= 1
                            worldStats[1] += 1
                            worldStats[3] += 1
                            agent.daysInfected = 1
                            agent.isStayingHomeWhenInfected = agent.shouldStayAtHome(agent.daysInfected)
                        }
                        1 -> {
                            agent.daysInfected += 1
                            if (agent.daysInfected == 8) {
                                agent.healthStatus = 2
                                worldStats[1] -= 1
                                worldStats[2] += 1
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
                                worldStats[2] -= 1
                                worldStats[0] += 1
                            }
                        }
                        0 -> {
                            if (makeContactWithInfected(agent.age, -1, 6)) {
                                agent.healthStatus = 1
                                worldStats[0] -= 1
                                worldStats[1] += 1
                                worldStats[3] += 1
                                agent.daysInfected = 1
                                agent.isStayingHomeWhenInfected = agent.shouldStayAtHome(agent.daysInfected)
                            }
                        }
                    }
                }
            }
//            println("---------------DAY$day------------------")
//            println("Susceptible: $susceptible")
//            println("Infected: $infected")
//            println("Recovered: $recovered")
            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\output\\results.xlsx", globalDay, worldStats)
            dayOfTheWeek += 1
            worldStats[3] = 0
            if (dayOfTheWeek == 8) {
                dayOfTheWeek = 1
//                println("Week cases: $weekCases")
//                weekCases = 0
            }
            day += 1
            globalDay += 1

            if ((month in arrayListOf(1, 3, 5, 7, 8, 10) && (day == 32)) ||
                    (month in arrayListOf(4, 6, 9, 11) && (day == 31)) ||
                    (month == 2) and (day == 29)) {
                day = 1
                month += 1
                println("Month $month")
            } else if ((month == 12) && (day == 32)) {
                day = 1
                month = 1
                year += 1
            }
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