package application.model

import application.utility.readTableInt
import application.utility.writeTableResult
import kotlinx.coroutines.*
import application.utility.readTableDouble
import application.utility.readTableInt2
import javafx.application.Platform
import javafx.beans.property.ReadOnlyDoubleWrapper
import javafx.beans.property.SimpleStringProperty
import javafx.scene.chart.XYChart
import java.lang.Thread.sleep
import kotlin.math.exp
import kotlin.math.max

class World(private val progress: ReadOnlyDoubleWrapper) {
    // Contact durations
    private fun getHouseholdContactDuration(): Double {
        val rand = java.util.Random()
        return max(0.0,12.0 + rand.nextGaussian() * 2.0)
    }

    private fun getAdditionalHouseholdContactDuration(): Double {
        val rand = java.util.Random()
        return max(0.0,6.0 + rand.nextGaussian() * 2.0)
    }

    private fun getKindergartenContactDuration(): Double {
        val rand = java.util.Random()
        return max(0.0,5.88 + rand.nextGaussian() * 2.52)
    }

    private fun getSchoolContactDuration(): Double {
        val rand = java.util.Random()
        return max(0.0,4.783 + rand.nextGaussian() * 2.67)
    }

    private fun getUniversityContactDuration(): Double {
        val rand = java.util.Random()
        return max(0.0,2.133 + rand.nextGaussian() * 1.62)
    }

    private fun getWorkplaceContactDuration(): Double {
        val rand = java.util.Random()
        return max(0.0,3.07 + rand.nextGaussian() * 2.07)
    }

    private fun getMetroContactDuration(): Double {
        return 0.05
    }

    private var durationBias = 8.0

    // Temperatures
    private val temp = arrayListOf(-5.8, -5.9, -5.9, -5.9,
            -6.0, -6.0, -6.1, -6.1, -6.2, -6.2, -6.2, -6.3,
            -6.3, -6.4, -6.5, -6.5, -6.6, -6.6, -6.7, -6.7,
            -6.8, -6.8, -6.9, -6.9, -7.0, -7.0, -7.0, -7.1, -7.1,
            -7.1, -7.1, -7.2, -7.2, -7.2, -7.2, -7.2, -7.2, -7.1,
            -7.1, -7.1, -7.0, -7.0, -6.9, -6.8, -6.8, -6.7, -6.6,
            -6.5, -6.4, -6.3, -6.1, -6.0, -5.9, -5.7, -5.6, -5.4,
            -5.2, -5.0, -4.8, -4.7, -4.5, -4.2, -4.0, -3.8,
            -3.6, -3.4, -3.1, -2.9, -2.7, -2.4, -2.2, -1.9, -1.7,
            -1.4, -1.2, -0.9, -0.6, -0.4, -0.1, 0.2, 0.4,
            0.7, 1.0, 1.2, 1.5, 1.8, 2.0, 2.3, 2.5, 2.8,
            3.1, 3.3, 3.6, 3.9, 4.1, 4.4, 4.6, 4.9, 5.1,
            5.4, 5.6, 5.9, 6.1, 6.4, 6.6, 6.9, 7.1, 7.4,
            7.6, 7.8, 8.1, 8.3, 8.5, 8.8, 9.0, 9.2, 9.4,
            9.7, 9.9, 10.1, 10.3, 10.5, 10.7, 11.0, 11.2,
            11.4, 11.6, 11.8, 12.0, 12.1, 12.3, 12.5, 12.7,
            12.9, 13.1, 13.2, 13.4, 13.6, 13.7, 13.9, 14.0,
            14.2, 14.3, 14.5, 14.6, 14.8, 14.9, 15.0, 15.2,
            15.3, 15.4, 15.5, 15.6, 15.8, 15.9, 16.0, 16.1,
            16.2, 16.3, 16.4, 16.5, 16.6, 16.7, 16.8, 16.9,
            17.0, 17.1, 17.2, 17.2, 17.3, 17.4, 17.5, 17.6,
            17.7, 17.8, 17.9, 17.9, 18.0, 18.1, 18.2, 18.3,
            18.4, 18.4, 18.5, 18.6, 18.7, 18.7, 18.8, 18.9,
            18.9, 19.0, 19.1, 19.1, 19.2, 19.2, 19.3, 19.3,
            19.3, 19.4, 19.4, 19.4, 19.4, 19.4, 19.4, 19.4,
            19.4, 19.4, 19.3, 19.3, 19.3, 19.2, 19.1, 19.1,
            19.0, 18.9, 18.8, 18.7, 18.6, 18.5, 18.4, 18.3,
            18.2, 18.0, 17.9, 17.7, 17.6, 17.4, 17.2, 17.1,
            16.9, 16.7, 16.5, 16.3, 16.1, 15.9, 15.7, 15.5,
            15.3, 15.1, 14.9, 14.7, 14.5, 14.3, 14.1, 13.9,
            13.7, 13.5, 13.3, 13.1, 12.8, 12.6, 12.4, 12.2,
            12.1, 11.9, 11.7, 11.5, 11.3, 11.1, 10.9, 10.7,
            10.6, 10.4, 10.2, 10.0, 9.9, 9.7, 9.5, 9.4,
            9.2, 9.0, 8.9, 8.7, 8.5, 8.3, 8.2, 8.0,
            7.8, 7.7, 7.5, 7.3, 7.1, 6.9, 6.8, 6.6,
            6.4, 6.2, 6.0, 5.8, 5.6, 5.4, 5.2, 4.9,
            4.7, 4.5, 4.3, 4.0, 3.8, 3.6, 3.3, 3.1,
            2.9, 2.6, 2.4, 2.1, 1.9, 1.6, 1.4, 1.1,
            0.9, 0.7, 0.4, 0.2, -0.1, -0.3, -0.5, -0.8,
            -1.0, -1.2, -1.5, -1.7, -1.9, -2.1, -2.3, -2.5,
            -2.7, -2.9, -3.0, -3.2, -3.4, -3.5, -3.7, -3.8,
            -4.0, -4.1, -4.2, -4.3, -4.4, -4.5, -4.6, -4.7,
            -4.8, -4.9, -5.0, -5.0, -5.1, -5.2, -5.2, -5.3,
            -5.3, -5.4, -5.4, -5.4, -5.5, -5.5, -5.5, -5.6,
            -5.6, -5.6, -5.7, -5.7, -5.7, -5.7, -5.8, -5.8
    )

    private val minTemp = -7.2
    private val maxMinTemp = 26.6

    // Date
    private var day = 1
    private var month = 8

    private var globalDay = 0
    private var tempDay = 212
    private var dayOfTheWeek = 1

    // New cases, New cases registered
    private var worldStats = arrayListOf(0, 0)
    private var currentlyInfected = 0

    // Collectives
    private val kindergartens = arrayListOf<Kindergarten>()
    private val schools = arrayListOf<School>()
    private val universities = arrayListOf<University>()
    private val workplace = Workplace()
    private val homes = arrayListOf<Home>()
    private val metro = arrayListOf<Metro>()
    private val households = arrayListOf<Household>()
    private val publicSpaces = arrayListOf<PublicSpace>()

    private val shortestMetroPaths = arrayListOf<ArrayList<Int>>()

    private fun createKindergartens() {
        val kindergartenCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableDouble("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\tables\\kindergarten_coordinates.xlsx",
                1763, 1, kindergartenCoordinatesMatrix)
        kindergartenCoordinatesMatrix.forEach {
            kindergartens.add(Kindergarten(Pair(it[0], it[1])))
        }
    }

    private fun createSchools() {
        val schoolCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableDouble("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\tables\\school_coordinates.xlsx",
                997, 1, schoolCoordinatesMatrix)
        schoolCoordinatesMatrix.forEach {
            schools.add(School(Pair(it[0], it[1])))
        }
    }

    private fun createUniversities() {
        val universityCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableDouble("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\tables\\university_coordinates.xlsx",
                41, 1, universityCoordinatesMatrix)
        universityCoordinatesMatrix.forEach {
            universities.add(University(Pair(it[0], it[1])))
        }
    }

    private fun createHomes() {
        val homeCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableDouble("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\tables\\homes.xlsx",
                22266, 5, homeCoordinatesMatrix)
        homeCoordinatesMatrix.forEach{
            homes.add(Home(Pair(it[0], it[1]), it[2].toInt(), it[3].toInt(), it[4].toInt(), it[5].toInt()))
        }
    }

    private fun createMetro() {
        val metroCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableDouble("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\tables\\metro_coordinates.xlsx",
                215, 1, metroCoordinatesMatrix)
        metroCoordinatesMatrix.forEach{
            metro.add(Metro(Pair(it[0], it[1])))
        }
    }

    private fun createPublicSpaces() {
        val publicspacesCoordinatesMatrix = arrayListOf<ArrayList<Double>>()
        readTableDouble("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\tables\\publicspaces.xlsx",
                747, 2, publicspacesCoordinatesMatrix)
        publicspacesCoordinatesMatrix.forEach{
            publicSpaces.add(PublicSpace(Pair(it[0], it[1]), it[2].toInt()))
        }
    }

    private fun getShortestMetroPaths() {
        readTableInt2("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\tables\\shortest_metro_paths.xlsx",
                46656, shortestMetroPaths)
    }

    // Create agent
    private fun createAgent(
            household: Household,
            okatoIndex: Int,
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
                                Agent(true, 0, household)
                            } else {
                                Agent(false, 0, household)
                            }
                        }
                        19 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                Agent(true, (0..1).random(), household)
                            } else {
                                Agent(false, (0..1).random(), household)
                            }
                        }
                        20 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                Agent(true, (0..2).random(), household)
                            } else {
                                Agent(false, (0..2).random(), household)
                            }
                        }
                        21 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                Agent(true, (0..3).random(), household)
                            } else {
                                Agent(false, (0..3).random(), household)
                            }
                        }
                        22 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                Agent(true, (0..4).random(), household)
                            } else {
                                Agent(false, (0..4).random(), household)
                            }
                        }
                        23 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random(), household)
                                } else {
                                    Agent(false, (0..4).random(), household)
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, 5, household)
                                } else {
                                    Agent(false, 5, household)
                                }
                            }
                        }
                        24 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random(), household)
                                } else {
                                    Agent(false, (0..4).random(), household)
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..6).random(), household)
                                } else {
                                    Agent(false, (5..6).random(), household)
                                }
                            }
                        }
                        25 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random(), household)
                                } else {
                                    Agent(false, (0..4).random(), household)
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..7).random(), household)
                                } else {
                                    Agent(false, (5..7).random(), household)
                                }
                            }
                        }
                        26 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random(), household)
                                } else {
                                    Agent(false, (0..4).random(), household)
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..8).random(), household)
                                } else {
                                    Agent(false, (5..8).random(), household)
                                }
                            }
                        }
                        27 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random(), household)
                                } else {
                                    Agent(false, (0..4).random(), household)
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..9).random(), household)
                                } else {
                                    Agent(false, (5..9).random(), household)
                                }
                            }
                        }
                        28 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][154])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random(), household)
                                } else {
                                    Agent(false, (0..4).random(), household)
                                }
                            } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][155])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..9).random(), household)
                                } else {
                                    Agent(false, (5..9).random(), household)
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][121])) {
                                    Agent(true, 10, household)
                                } else {
                                    Agent(false, 10, household)
                                }
                            }
                        }
                        29 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][154])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random(), household)
                                } else {
                                    Agent(false, (0..4).random(), household)
                                }
                            } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][155])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..9).random(), household)
                                } else {
                                    Agent(false, (5..9).random(), household)
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][121])) {
                                    Agent(true, (10..11).random(), household)
                                } else {
                                    Agent(false, (10..11).random(), household)
                                }
                            }
                        }
                        30 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][154])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random(), household)
                                } else {
                                    Agent(false, (0..4).random(), household)
                                }
                            } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][155])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..9).random(), household)
                                } else {
                                    Agent(false, (5..9).random(), household)
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][121])) {
                                    Agent(true, (10..12).random(), household)
                                } else {
                                    Agent(false, (10..12).random(), household)
                                }
                            }
                        }
                        31 -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][154])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random(), household)
                                } else {
                                    Agent(false, (0..4).random(), household)
                                }
                            } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][155])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..9).random(), household)
                                } else {
                                    Agent(false, (5..9).random(), household)
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][121])) {
                                    Agent(true, (10..13).random(), household)
                                } else {
                                    Agent(false, (10..13).random(), household)
                                }
                            }
                        }
                        else -> {
                            if (randomNum in (1..districtsInfoMatrix[okatoIndex][154])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][117])) {
                                    Agent(true, (0..4).random(), household)
                                } else {
                                    Agent(false, (0..4).random(), household)
                                }
                            } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][155])) {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][119])) {
                                    Agent(true, (5..9).random(), household)
                                } else {
                                    Agent(false, (5..9).random(), household)
                                }
                            } else {
                                randomNum = (1..100).random()
                                if (randomNum in (1..districtsInfoMatrix[okatoIndex][121])) {
                                    Agent(true, (10..14).random(), household)
                                } else {
                                    Agent(false, (10..14).random(), household)
                                }
                            }
                        }
                    }
                }
                else -> {
                    randomNum = (1..100).random()
                    return if (randomNum in (1..districtsInfoMatrix[okatoIndex][123])) {
                        Agent(true, (15..17).random(), household)
                    } else {
                        Agent(false, (15..17).random(), household)
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
                        return Agent(isMale, (18..24).random(), household)
                    }
                    return if (randomNum in (1..districtsInfoMatrix[okatoIndex][125])) {
                        Agent(true, (18..24).random(), household)
                    } else {
                        Agent(false, (18..24).random(), household)
                    }
                }
                in ageDistributionInDistrictsMatrix[10][biasedIndex] +
                        1..ageDistributionInDistrictsMatrix[11][biasedIndex] -> {
                    randomNum = (1..100).random()
                    if (randomNum in (1..districtsInfoMatrix[okatoIndex][156])) {
                        if (isMale != null) {
                            return Agent(isMale, (25..29).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][127])) {
                            Agent(true, (25..29).random(), household)
                        } else {
                            Agent(false, (25..29).random(), household)
                        }
                    } else {
                        if (isMale != null) {
                            return Agent(isMale, (30..34).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][129])) {
                            Agent(true, (30..34).random(), household)
                        } else {
                            Agent(false, (30..34).random(), household)
                        }
                    }
                }
                in ageDistributionInDistrictsMatrix[11][biasedIndex] +
                        1..ageDistributionInDistrictsMatrix[12][biasedIndex] -> {
                    randomNum = (1..100).random()
                    if (randomNum in (1..districtsInfoMatrix[okatoIndex][157])) {
                        if (isMale != null) {
                            return Agent(isMale, (35..39).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][131])) {
                            Agent(true, (35..39).random(), household)
                        } else {
                            Agent(false, (35..39).random(), household)
                        }
                    } else {
                        if (isMale != null) {
                            return Agent(isMale, (40..44).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][133])) {
                            Agent(true, (40..44).random(), household)
                        } else {
                            Agent(false, (40..44).random(), household)
                        }
                    }
                }
                in ageDistributionInDistrictsMatrix[12][biasedIndex] +
                        1..ageDistributionInDistrictsMatrix[13][biasedIndex] -> {
                    randomNum = (1..100).random()
                    if (randomNum in (1..districtsInfoMatrix[okatoIndex][158])) {
                        if (isMale != null) {
                            return Agent(isMale, (45..49).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][135])) {
                            Agent(true, (45..49).random(), household)
                        } else {
                            Agent(false, (45..49).random(), household)
                        }
                    } else {
                        if (isMale != null) {
                            return Agent(isMale, (50..54).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][137])) {
                            Agent(true, (50..54).random(), household)
                        } else {
                            Agent(false, (50..54).random(), household)
                        }
                    }
                }
                in ageDistributionInDistrictsMatrix[13][biasedIndex] + 1..ageDistributionInDistrictsMatrix[14][biasedIndex] -> {
                    randomNum = (1..100).random()
                    if (randomNum in (1..districtsInfoMatrix[okatoIndex][159])) {
                        if (isMale != null) {
                            return Agent(isMale, (55..59).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][139])) {
                            Agent(true, (55..59).random(), household)
                        } else {
                            Agent(false, (55..59).random(), household)
                        }
                    } else {
                        if (isMale != null) {
                            return Agent(isMale, (60..64).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][141])) {
                            Agent(true, (60..64).random(), household)
                        } else {
                            Agent(false, (60..64).random(), household)
                        }
                    }
                }
                else -> {
                    randomNum = (1..100).random()
                    if (randomNum in (1..districtsInfoMatrix[okatoIndex][160])) {
                        if (isMale != null) {
                            return Agent(isMale, (65..69).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][143])) {
                            Agent(true, (65..69).random(), household)
                        } else {
                            Agent(false, (65..69).random(), household)
                        }
                    } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][161])) {
                        if (isMale != null) {
                            return Agent(isMale, (70..74).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][145])) {
                            Agent(true, (70..74).random(), household)
                        } else {
                            Agent(false, (70..74).random(), household)
                        }
                    } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][162])) {
                        if (isMale != null) {
                            return Agent(isMale, (75..79).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][147])) {
                            Agent(true, (75..79).random(), household)
                        } else {
                            Agent(false, (75..79).random(), household)
                        }
                    } else if (randomNum in (1..districtsInfoMatrix[okatoIndex][163])) {
                        if (isMale != null) {
                            return Agent(isMale, (80..84).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][149])) {
                            Agent(true, (80..84).random(), household)
                        } else {
                            Agent(false, (80..84).random(), household)
                        }
                    } else {
                        if (isMale != null) {
                            return Agent(isMale, (85..89).random(), household)
                        }
                        randomNum = (1..100).random()
                        return if (randomNum in (1..districtsInfoMatrix[okatoIndex][151])) {
                            Agent(true, (85..89).random(), household)
                        } else {
                            Agent(false, (85..89).random(), household)
                        }
                    }
                }
            }
        }
    }

    private fun addSpouse(household: Household, agentMale: Agent): Agent {
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
        return Agent(false, spouseAge, household)
    }

    @Synchronized private fun addAgentsToGroups(agents: ArrayList<Agent>, household: Household) {
        agents.forEach { agent ->
            when (agent.activityStatus) {
                1 -> kindergartens[household.home.closestKindergartenIndex].addAgent(agent)
                2 -> schools[household.home.closestSchoolIndex].addAgent(agent)
                3 -> universities[(universities.indices).random()].addAgent(agent)
                4 -> workplace.addAgent(agent, homes)
            }
            if (agent.healthStatus == 1) {
                currentlyInfected += 1
            }
            household.addAgent(agent)
        }
        households.add(household)
    }

    private suspend fun processAll(districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                                   ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>) = withContext(Dispatchers.IO) {

        districtsInfoMatrix.forEachIndexed { index, it ->
            launch {
                progress.set(index / 108.0)
                println("Current index $index")

                val homesFiltered = homes.filter { home -> home.okato == it[0] }

                val indexFor1People = index * 5 + 1
                val indexFor2People = index * 5 + 2
                val indexFor3People = index * 5 + 3
                val indexFor4People = index * 5 + 4
                val indexFor5People = index * 5 + 5

                for (i in 0..it[58]) {
                    val household = Household("1P", homesFiltered[(homesFiltered.indices).random()])
                    val agent = createAgent(household, index, indexFor1People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    addAgentsToGroups(arrayListOf(agent), household)
                }
                for (i in 0..it[59]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP2P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[60] - it[61])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP3P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[61]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP3P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if (agentMale.activityStatus != 0) {
                        agentFemale.isMother = true
                        if ((agent.age <= 2) && (agent.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[62] - it[63] - it[64])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP4P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[63]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP4P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                            (agent.activityStatus != 0)) {
                        agentFemale.isMother = true
                        if ((agent2.age <= 2) &&
                                (agent2.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[64]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP4P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if (agentMale.activityStatus != 0) {
                        agentFemale.isMother = true
                        if ((agent2.age <= 2) &&
                                (agent2.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        } else if ((agent.age <= 2) &&
                                (agent.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[65] - it[66] - it[67] - it[68])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP5P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[66]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP5P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                            (agent.activityStatus != 0)) {
                        agentFemale.isMother = true
                        if ((agent2.activityStatus != 0) &&
                                (agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[67]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP5P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                            (agent.activityStatus != 0)) {
                        agentFemale.isMother = true
                        if ((agent2.age <= 2) &&
                                (agent2.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        } else if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[68]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP5P3C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if (agentMale.activityStatus != 0){
                        agentFemale.isMother = true
                        if ((agent.age <= 2) &&
                                (agent.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        } else if ((agent2.age <= 2) &&
                                (agent2.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        } else if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[69] - it[70] - it[71] - it[72])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP6P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[70]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP6P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true, isMale = null, parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                            (agent.activityStatus != 0) &&
                            (agent2.activityStatus != 0) &&
                            (agent3.activityStatus != 0)) {
                        agentFemale.isMother = true
                        if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[71]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP6P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent4 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                            (agent.activityStatus != 0) &&
                            (agent2.activityStatus != 0)) {
                        agentFemale.isMother = true
                        if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        } else if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[72]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP6P3C", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent4 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)

                    if ((agentMale.activityStatus != 0) &&
                            (agent.activityStatus != 0)) {
                        agentFemale.isMother = true
                        if ((agent2.age <= 2) &&
                                (agent2.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        } else if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        } else if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[73]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP4P0С", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agentMale2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale2 = addSpouse(household, agentMale2)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[74] - it[75])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP5P0С", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agentMale2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale2 = addSpouse(household, agentMale2)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[75]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP5P1С", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agentMale2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale2 = addSpouse(household, agentMale2)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                            (agentMale2.activityStatus != 0) &&
                            (agentFemale2.activityStatus != 0)) {
                        agentFemale.isMother = true
                        if ((agent.age <= 2) &&
                                (agent.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[76] - it[77] - it[78])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP6P0С", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agentMale2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale2 = addSpouse(household, agentMale2)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[77]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP6P1С", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agentMale2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = null
                    )
                    val agentFemale2 = addSpouse(household, agentMale2)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                            (agentMale2.activityStatus != 0) &&
                            (agentFemale2.activityStatus != 0) &&
                            (agent.activityStatus != 0)) {
                        agentFemale.isMother = true
                        if ((agent2.age <= 2) &&
                                (agent2.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[78]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP6P2С", homesFiltered[(homesFiltered.indices).random()])

                    val agentMale = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agentFemale = addSpouse(household, agentMale)
                    val agentMale2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agentFemale2 = addSpouse(household, agentMale2)
                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale2.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                            (agentMale2.activityStatus != 0) &&
                            (agentFemale2.activityStatus != 0)) {
                        agentFemale.isMother = true
                        if ((agent.age <= 2) &&
                                (agent.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        } else if ((agent2.age <= 2) &&
                                (agent2.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[79] - it[80])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC2P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent2 = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[80]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC2P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    agent.isMother = true
                    if ((agent2.age <= 2) && (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[81] - it[82] - it[83])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC3P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent3.age < 18) {
                        agent3.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[82]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC3P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.isMother = true
                        if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[83]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC3P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    agent.isMother = true
                    if ((agent2.age <= 2) &&
                            (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    } else if ((agent3.age <= 2) &&
                            (agent3.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[84] - it[85] - it[86] - it[87])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC4P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent3.age < 18) {
                        agent3.age = agent.age - 18
                    }
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent4.age < 18) {
                        agent4.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[85]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC4P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent3.age < 18) {
                        agent3.age = agent.age - 18
                    }
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if ((agent2.activityStatus != 0) &&
                            (agent3.activityStatus != 0)) {
                        agent.isMother = true
                        if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[86]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC4P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.isMother = true
                        if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agent.activityStatus = 0
                        } else if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[87]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC4P3C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    agent.isMother = true
                    if ((agent2.age <= 2) &&
                            (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    } else if ((agent3.age <= 2) &&
                            (agent3.activityStatus == 0)) {
                        agent.activityStatus = 0
                    } else if ((agent4.age <= 2) &&
                            (agent4.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[88] - it[89])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SFWC2P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent2 = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[89]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SFWC2P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    agent.isMother = true
                    if ((agent2.age <= 2) &&
                            (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[90] - it[91] - it[92])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SFWC3P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent3.age < 18) {
                        agent3.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[91]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SFWC3P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.isMother = true
                        if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[92]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SFWC3P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    agent.isMother = true
                    if ((agent2.age <= 2) &&
                            (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    } else if ((agent3.age <= 2) &&
                            (agent3.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[93] - it[94])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP3P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[94]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP3P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.isMother = true
                        if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[95] - it[96] - it[97])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP4P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[96]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP4P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if ((agent2.activityStatus != 0) &&
                            (agent3.activityStatus != 0)) {
                        agent.isMother = true
                        if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[97]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP4P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.isMother = true
                        if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agent.activityStatus = 0
                        } else if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[98] - it[99])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP3P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[99]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP3P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.isMother = true
                        if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[55] - it[101] - it[102])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP4P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[101]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP4P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if ((agent2.activityStatus != 0) &&
                            (agent3.activityStatus != 0)) {
                        agent.isMother = true
                        if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[102]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP4P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.isMother = true
                        if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agent.activityStatus = 0
                        } else if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[103] - it[104] - it[105])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP5P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent5 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[104]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP5P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = true
                    )
                    val agent5 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if ((agent2.activityStatus != 0) &&
                            (agent3.activityStatus != 0) &&
                            (agent4.activityStatus != 0)) {
                        agent.isMother = true
                        if ((agent5.age <= 2) &&
                                (agent5.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[105]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP5P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isParentOfAdult = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent5 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if ((agent2.activityStatus != 0) &&
                            (agent3.activityStatus != 0)) {
                        agent.isMother = true
                        if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agent.activityStatus = 0
                        } else if ((agent5.age <= 2) &&
                                (agent5.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[106] - it[107])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O2P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[107]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O2P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = true
                    )

                    agent.isMother = true
                    if ((agent2.age <= 2) &&
                            (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[108] - it[109] - it[110])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O3P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[109]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O3P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = true
                    )

                    if (agent2.activityStatus != 0) {
                        agent.isMother = true
                        if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[110]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O3P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = true
                    )
                    val agent3 = createAgent(household, index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = true
                    )

                    agent.isMother = true
                    if ((agent2.age <= 2) &&
                            (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    } else if ((agent3.age <= 2) &&
                            (agent3.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[111] - it[112] - it[113])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O4P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[112]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O4P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = true
                    )

                    if ((agent2.activityStatus != 0) &&
                            (agent3.activityStatus != 0)) {
                        agent.isMother = true
                        if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[113]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O4P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = true
                    )
                    val agent4 = createAgent(household, index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = true
                    )

                    if (agent2.activityStatus != 0) {
                        agent.isMother = true
                        if ((agent3.age <= 2) &&
                                (agent3.activityStatus == 0)) {
                            agent.activityStatus = 0
                        } else if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[114] - it[115] - it[116])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O5P0C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent5 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[115]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O5P1C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent5 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = true
                    )

                    if ((agent2.activityStatus != 0) &&
                            (agent3.activityStatus != 0) &&
                            (agent4.activityStatus != 0)) {
                        agent.isMother = true
                        if ((agent5.age <= 2) &&
                                (agent5.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[116]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O5P2C", homesFiltered[(homesFiltered.indices).random()])

                    val agent = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = true
                    )
                    val agent5 = createAgent(household, index, indexFor5People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, isChild = true
                    )

                    if ((agent2.activityStatus != 0) &&
                            (agent3.activityStatus != 0)) {
                        agent.isMother = true
                        if ((agent4.age <= 2) &&
                                (agent4.activityStatus == 0)) {
                            agent.activityStatus = 0
                        } else if ((agent5.age <= 2) &&
                                (agent5.activityStatus == 0)) {
                            agent.activityStatus = 0
                        }
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    agents.add(agent5)
                    addAgentsToGroups(agents, household)
                }
            }
        }
    }

    private fun addHouseholdsToPool(districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                                    ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>) {
        runBlocking {
            processAll(districtsInfoMatrix, ageDistributionInDistrictsMatrix)
        }
        println("Households created")
        workplace.generateLastBarabasiAlbertNetwork()
        println("World creation has ended")
    }

    private fun contactsInHouseholdForAgent(household: Household, additionalContactInHoliday: Boolean,
                                            agent: Agent, tMap: Map<String, Double>) {
        household.agents.forEach { agent2 ->
            val agent2IsImmune = when (agent.infectionType) {
                "fluA" -> agent2.fluAImmunity
                "fluB" -> agent2.fluBImmunity
                "RV" -> agent2.RVImmunity
                "RSV" -> agent2.RSVImmunity
                "AdV" -> agent2.AdVImmunity
                "PIV" -> agent2.PIVImmunity
                "CoV" -> agent2.CoVImmunity
                else -> true
            }
            if ((agent2.healthStatus == 0) && (!agent2IsImmune)) {
                val durationCoefficient = if (additionalContactInHoliday) {
                    1 / (1 + exp(
                            -getAdditionalHouseholdContactDuration() + durationBias))
                } else {
                    1 / (1 + exp(
                            -getHouseholdContactDuration() + durationBias))
                }

                val curTemp = (temp[tempDay] - minTemp) / maxMinTemp
                val externalInfluence = (tMap[agent.infectionType] ?: error("Required")) * curTemp + 1.0

                val randNum = (0..9999).random() * 0.0001
                val susceptibility = when (agent.infectionType) {
                    "fluA" -> agent.fluASusceptibility
                    "fluB" -> agent.fluBSusceptibility
                    "RV" -> agent.RVSusceptibility
                    "RSV" -> agent.RSVSusceptibility
                    "AdV" -> agent2.AdVSusceptibility
                    "PIV" -> agent2.PIVSusceptibility
                    "CoV" -> agent2.CoVSusceptibility
                    else -> 0.0
                }
                val probabilityOfInfection = agent.infectivity * susceptibility *
                        externalInfluence * durationCoefficient
                if (randNum < probabilityOfInfection) {
                    agent2.healthStatus = 3
                    agent2.infectionType = agent.infectionType
                }
            }
        }
    }

    private fun contactsInGroupForAgent(group: Group, agent: Agent, tMap: Map<String, Double>) {
        group.agents.forEach { agent2 ->
            val agent2IsImmune = when (agent.infectionType) {
                "fluA" -> agent2.fluAImmunity
                "fluB" -> agent2.fluBImmunity
                "RV" -> agent2.RVImmunity
                "RSV" -> agent2.RSVImmunity
                "AdV" -> agent2.AdVImmunity
                "PIV" -> agent2.PIVImmunity
                "CoV" -> agent2.CoVImmunity
                else -> error("Not found")
            }
            if ((agent2.healthStatus == 0) &&
                    (!agent2IsImmune) &&
                    (!agent2.isOnMotherLeave)) {

                val durationCoefficient = when (agent.activityStatus) {
                    1 -> 1 / (1 + exp(
                            -getKindergartenContactDuration() + durationBias))
                    2 -> 1 / (1 + exp(
                            -getSchoolContactDuration() + durationBias))
                    3 -> 1 / (1 + exp(
                            -getUniversityContactDuration() + durationBias))
                    4 -> 1 / (1 + exp(
                            -getWorkplaceContactDuration() + durationBias))
                    else -> 1.0
                }
                val curTemp = (temp[tempDay] - minTemp) / maxMinTemp
                val externalInfluence = (tMap[agent.infectionType] ?: error("Required")) * curTemp + 1.0

                val randNum = (0..9999).random() * 0.0001
                val susceptibility = when (agent.infectionType) {
                    "fluA" -> agent.fluASusceptibility
                    "fluB" -> agent.fluBSusceptibility
                    "RV" -> agent.RVSusceptibility
                    "RSV" -> agent.RSVSusceptibility
                    "AdV" -> agent2.AdVSusceptibility
                    "PIV" -> agent2.PIVSusceptibility
                    "CoV" -> agent2.CoVSusceptibility
                    else -> error("Not found")
                }
                val probabilityOfInfection = agent.infectivity * susceptibility *
                        externalInfluence * durationCoefficient
                if (randNum < probabilityOfInfection) {
                    agent2.healthStatus = 3
                    agent2.infectionType = agent.infectionType
                }
            }
        }
    }

    private fun contactsInGroupForAgentAtWork(group: Company, agent: Agent, tMap: Map<String, Double>) {
        group.agents.forEachIndexed { index, agent2 ->
            val agent2IsImmune = when (agent.infectionType) {
                "fluA" -> agent2.fluAImmunity
                "fluB" -> agent2.fluBImmunity
                "RV" -> agent2.RVImmunity
                "RSV" -> agent2.RSVImmunity
                "AdV" -> agent2.AdVImmunity
                "PIV" -> agent2.PIVImmunity
                "CoV" -> agent2.CoVImmunity
                else -> error("Not found")
            }
            if ((agent2.healthStatus == 0) &&
                    (!agent2IsImmune) &&
                    (index in agent.connectedWorkAgents) &&
                    (!agent2.isOnMotherLeave)) {

                val durationCoefficient = 1 / (1 + exp(
                        -getWorkplaceContactDuration() + durationBias))
                val curTemp = (temp[tempDay] - minTemp) / maxMinTemp
                val externalInfluence = (tMap[agent.infectionType] ?: error("Required")) * curTemp + 1.0

                val randNum = (0..9999).random() * 0.0001
                val susceptibility = when (agent.infectionType) {
                    "fluA" -> agent.fluASusceptibility
                    "fluB" -> agent.fluBSusceptibility
                    "RV" -> agent.RVSusceptibility
                    "RSV" -> agent.RSVSusceptibility
                    "AdV" -> agent2.AdVSusceptibility
                    "PIV" -> agent2.PIVSusceptibility
                    "CoV" -> agent2.CoVSusceptibility
                    else -> error("Not found")
                }
                val probabilityOfInfection = agent.infectivity * susceptibility *
                        externalInfluence * durationCoefficient
                if (randNum < probabilityOfInfection) {
                    agent2.healthStatus = 3
                    agent2.infectionType = agent.infectionType
                }
            }
        }
    }

    private fun randomInfection(agent: Agent) {
        if ((globalDay < 90) || (globalDay > 305)) {
//        if ((globalDay < 60) || (globalDay > 305)) {
            when ((0..99).random()) {
                in 0..59 -> {
                    if (!agent.RVImmunity) {
                        agent.healthStatus = 3
                        agent.infectionType = "RV"
                    }
                } // 60%
                in 60..84 -> {
                    if (!agent.AdVImmunity) {
                        agent.healthStatus = 3
                        agent.infectionType = "AdV"
                    }
                } // 25%
                else -> {
                    if (!agent.PIVImmunity) {
                        agent.healthStatus = 3
                        agent.infectionType = "PIV"
                    }
                } // 15%
            }
        } else {
            if (agent.age < 16) {
                when ((0..99).random()) {
                    in 0..4 -> {
                        if (!agent.fluAImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "fluA"
                        }
                    } // 5%
                    in 5..9 -> {
                        if (!agent.fluBImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "fluB"
                        }
                    } // 5%
                    in 10..39 -> {
                        if (!agent.RVImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "RV"
                        }
                    } // 30%
                    in 40..65 -> {
                        if (!agent.RSVImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "RSV"
                        }
                    } // 26%
                    in 66..82 -> {
                        if (!agent.AdVImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "AdV"
                        }
                    } // 17%
                    in 83..94 -> {
                        if (!agent.PIVImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "PIV"
                        }
                    } // 12%
                    else -> {
                        if (!agent.CoVImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "CoV"
                        }
                    } // 5%
                }
            } else {
                when ((0..99).random()) {
                    in 0..32 -> {
                        if (!agent.fluAImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "fluA"
                        }
                    } // 33%
                    in 33..50 -> {
                        if (!agent.fluBImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "fluB"
                        }
                    } // 18%
                    in 51..68 -> {
                        if (!agent.RVImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "RV"
                        }
                    } // 18%
                    in 69..76 -> {
                        if (!agent.RSVImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "RSV"
                        }
                    } // 8%
                    in 77..82 -> {
                        if (!agent.AdVImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "AdV"
                        }
                    } // 6%
                    in 83..91 -> {
                        if (!agent.PIVImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "PIV"
                        }
                    } // 9%
                    else -> {
                        if (!agent.CoVImmunity) {
                            agent.healthStatus = 3
                            agent.infectionType = "CoV"
                        }
                    } // 8%
                }
            }
        }
    }

    fun runSimulation(db: Double, bMap: Map<String, Double>, tMap: Map<String, Double>,
                      imMap: Map<String, Int>, numOfIter: Int, series1: XYChart.Series<String, Number>,
                      series2: XYChart.Series<String, Number>, dateLabelText: SimpleStringProperty
    ): ArrayList<ArrayList<Int>> {

        val schoolQuarantine = false

        durationBias = db

        val worldStats2 = arrayListOf(0, 0, 0, 0)
        val worldStats3 = arrayListOf(arrayListOf(0, 0, 0, 0))
        val worldStats4 = arrayListOf(0, 0, 0, 0, 0, 0, 0)

        households.parallelStream().forEach { household ->
            household.agents.forEach { agent ->
                agent.findSusceptibility(bMap)
            }
        }

        while(true) {

            worldStats[0] = 0
            worldStats[1] = 0

            worldStats2[0] = 0
            worldStats2[1] = 0
            worldStats2[2] = 0
            worldStats2[3] = 0

            worldStats4[0] = 0
            worldStats4[1] = 0
            worldStats4[2] = 0
            worldStats4[3] = 0
            worldStats4[4] = 0
            worldStats4[5] = 0
            worldStats4[6] = 0

            var holiday = false
            if (dayOfTheWeek == 7) {
                holiday = true
            }
            if ((month == 1) && (day in arrayListOf(1, 2, 3, 7))) {
                holiday = true
            }
            if ((month == 5) && (day in arrayListOf(1, 9))) {
                holiday = true
            }
            if ((month == 2) && (day == 23)) {
                holiday = true
            }
            if ((month == 3) && (day == 8)) {
                holiday = true
            }
            if ((month == 6) && (day == 12)) {
                holiday = true
            }

            var workingHoliday = false
            if (dayOfTheWeek == 6) {
                workingHoliday = true
            }

            var kinderHoliday = false
            if (month in arrayListOf(7, 8)) {
                kinderHoliday = true
            }

            // Каникулы
            // Осенние - 05.11.2007 - 11.11.2007 - 7 дней;
            // Зимние - 26.12.2007 - 09.01.2008 - 15 дней;
            // Весенние - 22.03.2008 - 31.03.2008 - 10 дней.
            // Зимние - 28.12.2007 - 09.03.2008
            var schoolHoliday = false
            if (month in arrayListOf(6, 7, 8)) {
                schoolHoliday = true
            }
            if ((month == 11) && (day in arrayListOf(5, 6, 7, 8, 9, 10, 11))) {
                schoolHoliday = true
            }
//            if ((month == 12) && (day in arrayListOf(26, 27, 28, 29, 30, 31))) {
            if ((month == 12) && (day in arrayListOf(28, 29, 30, 31))) {
                schoolHoliday = true
            }
            if ((month == 1) && (day in arrayListOf(6, 7, 8, 9))) {
                schoolHoliday = true
            }
            if ((month == 3) && (day in arrayListOf(22, 23, 24, 25, 26, 27, 28, 29, 30, 31))) {
                schoolHoliday = true
            }

            var universityHoliday = false
            if ((month == 12) && (day in arrayListOf(22, 23, 24, 25, 26, 27, 28, 29, 30, 31))) {
                universityHoliday = true
            }
            if (month == 1) {
                if (day !in arrayListOf(11, 15, 19, 23, 27)) {
                    universityHoliday = true
                }
            }
            if (month == 6) {
                if (day !in arrayListOf(11, 15, 19, 23, 27)) {
                    universityHoliday = true
                }
            }
            if (month in arrayListOf(7, 8)) {
                universityHoliday = true
            }
            if ((month == 2) && (day in arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))) {
                universityHoliday = true
            }

            households.parallelStream().forEach { household ->
                household.agents.forEach { agent ->
                    if (agent.healthStatus == 1) {
                        agent.findInfectivity()
                        contactsInHouseholdForAgent(household, false, agent, tMap)
                    }
                }
            }

            if ((universityHoliday) && (schoolHoliday) && (kinderHoliday) && (workingHoliday)) {
                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if (agent.healthStatus == 1) {
                            contactsInHouseholdForAgent(household, true, agent, tMap)
                        }
                    }
                }
            } else if (holiday) {
                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if (agent.healthStatus == 1) {
                            contactsInHouseholdForAgent(household, true, agent, tMap)
                        }
                    }
                }
            } else {
                if (!workingHoliday) {
                    if (!kinderHoliday) {
                        kindergartens.parallelStream().forEach { kindergarten ->
                            kindergarten.groupsByAge.forEach { groupByAge ->
                                groupByAge.forEach { group ->
                                    group.agents.forEach { agent ->
                                        if ((agent.healthStatus == 1) &&
                                                (!agent.isStayingHomeWhenInfected)) {
                                            contactsInGroupForAgent(group, agent, tMap)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    workplace.companies.parallelStream().forEach { company ->
                        company.agents.forEach { agent ->
                            if ((agent.healthStatus == 1) &&
                                    (!agent.isStayingHomeWhenInfected) && (!agent.isOnMotherLeave)) {
                                contactsInGroupForAgentAtWork(company, agent, tMap)
                            }
                        }
                    }
//                    workplace.companies.parallelStream().forEach { company ->
//                        company.agents.forEach { agent ->
//                            if (agent.usesMetro) {
//                                shortestMetroPaths[agent.household.home.closestMetroIndex * 216 +
//                                        company.home.closestMetroIndex][0]
//                            }
//                            if ((agent.healthStatus == 1) &&
//                                    (!agent.isStayingHomeWhenInfected) && (!agent.isOnMotherLeave)) {
//                                contactsInGroupForAgentAtWork(company, agent, tMap)
//                            }
//                        }
//                    }
                }
                if (!schoolHoliday) {
                    schools.parallelStream().forEach { school ->
                        school.groupsByAge.forEachIndexed { index, groupByAge ->
                            if ((index == 0) && (workingHoliday)) {

                            } else {
                                groupByAge.forEach { group ->
                                    group.agents.forEach { agent ->
                                        if ((agent.healthStatus == 1) &&
                                                (!agent.isStayingHomeWhenInfected)) {
                                            contactsInGroupForAgent(group, agent, tMap)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!universityHoliday) {
                    universities.parallelStream().forEach { university ->
                        university.groupsByAge.forEach { groupByAge ->
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if ((agent.healthStatus == 1) &&
                                            (!agent.isStayingHomeWhenInfected) && (!agent.isOnMotherLeave)) {
                                        contactsInGroupForAgent(group, agent, tMap)
                                    }
                                }
                            }
                        }
                    }
                }

                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if ((agent.activityStatus == 0) ||
                                (agent.isStayingHomeWhenInfected) ||
                                ((agent.activityStatus == 1) && kinderHoliday) ||
                                ((agent.activityStatus == 2) && schoolHoliday) ||
                                ((agent.age == 7) && workingHoliday) ||
                                ((agent.activityStatus == 3) && universityHoliday) ||
                                ((agent.activityStatus == 4) && workingHoliday)) {

                            if (agent.healthStatus == 1) {

                                household.agents.forEach { agent2 ->
                                    if ((agent2.activityStatus == 0) ||
                                            (agent2.isStayingHomeWhenInfected) ||
                                            ((agent2.activityStatus == 1) && kinderHoliday) ||
                                            ((agent2.activityStatus == 2) && schoolHoliday) ||
                                            ((agent2.age == 7) && workingHoliday) ||
                                            ((agent2.activityStatus == 3) && universityHoliday) ||
                                            ((agent2.activityStatus == 4) && workingHoliday)) {

                                        val agent2IsImmune = when (agent.infectionType) {
                                            "fluA" -> agent2.fluAImmunity
                                            "fluB" -> agent2.fluBImmunity
                                            "RV" -> agent2.RVImmunity
                                            "RSV" -> agent2.RSVImmunity
                                            "AdV" -> agent2.AdVImmunity
                                            "PIV" -> agent2.PIVImmunity
                                            "CoV" -> agent2.CoVImmunity
                                            else -> true
                                        }
                                        if ((!agent2IsImmune) &&
                                                (agent2.healthStatus == 0)) {

                                            val durationCoefficient = 1 / (1 + exp(
                                                    -getAdditionalHouseholdContactDuration() + durationBias))

//                                            val durationCoefficient = getAdditionalHouseholdContactDuration() / 24.0
                                            val curTemp = (temp[tempDay] - minTemp) / maxMinTemp
                                            val externalInfluence = (tMap[agent.infectionType] ?: error("Required")) *
                                                    curTemp + 1.0

                                            val randNum = (0..9999).random() * 0.0001
                                            val susceptibility = when (agent.infectionType) {
                                                "fluA" -> agent.fluASusceptibility
                                                "fluB" -> agent.fluBSusceptibility
                                                "RV" -> agent.RVSusceptibility
                                                "RSV" -> agent.RSVSusceptibility
                                                "AdV" -> agent2.AdVSusceptibility
                                                "PIV" -> agent2.PIVSusceptibility
                                                "CoV" -> agent2.CoVSusceptibility
                                                else -> 0.0
                                            }
                                            val probabilityOfInfection = agent.infectivity *
                                                    susceptibility *
                                                    externalInfluence *
                                                    durationCoefficient
                                            if (randNum < probabilityOfInfection) {
                                                agent2.healthStatus = 3
                                                agent2.infectionType = agent.infectionType
                                            }

                                        }
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
                            worldStats[0] += 1

                            when (agent.infectionType) {
                                "fluA" -> worldStats4[0] += 1
                                "fluB" -> worldStats4[1] += 1
                                "RV" -> worldStats4[2] += 1
                                "RSV" -> worldStats4[3] += 1
                                "AdV" -> worldStats4[4] += 1
                                "PIV" -> worldStats4[5] += 1
                                "CoV" -> worldStats4[6] += 1
                            }

                            agent.updateHealthParameters()
                            if (agent.isStayingHomeWhenInfected) {
                                worldStats[1] += 1
                                when (agent.age) {
                                    in (0..2) -> {
                                        worldStats3[worldStats3.size - 1][0] += 1
                                        worldStats2[0] += 1
                                    }
                                    in (3..6) -> {
                                        worldStats3[worldStats3.size - 1][1] += 1
                                        worldStats2[1] += 1
                                    }
                                    in (7..14) -> {
                                        worldStats3[worldStats3.size - 1][2] += 1
                                        worldStats2[2] += 1
                                    }
                                    else -> {
                                        worldStats3[worldStats3.size - 1][3] += 1
                                        worldStats2[3] += 1
                                    }
                                }
                                if (agent.age < 14) {
                                    for (otherAgent in household.agents) {
                                        if (otherAgent.isMother) {
                                            otherAgent.isOnMotherLeave = true
                                            break
                                        }
                                    }
                                }
                            }
                        }
                        1 -> {
                            if (agent.daysInfected == agent.shouldBeInfected) {
                                currentlyInfected--
                                when (agent.infectionType) {
                                    "fluA" -> agent.fluAImmunity = true
                                    "fluB" -> agent.fluBImmunity = true
                                    "RV" -> agent.RVImmunity = true
                                    "RSV" -> agent.RSVImmunity = true
                                    "AdV" -> agent.AdVImmunity = true
                                    "PIV" -> agent.PIVImmunity = true
                                    "CoV" -> agent.CoVImmunity = true
                                }
                                agent.healthStatus = 2
                                agent.daysImmune = 1
                                if ((agent.age < 14) && (agent.isStayingHomeWhenInfected)) {
                                    for (otherAgent in household.agents) {
                                        if (otherAgent.isMother) {
                                            otherAgent.isOnMotherLeave = false
                                            break
                                        }
                                    }
                                }
                            } else {
                                agent.daysInfected += 1
                                if (!agent.isStayingHomeWhenInfected) {
                                    agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
                                    if (agent.isStayingHomeWhenInfected) {
                                        worldStats[1] += 1
                                        when (agent.age) {
                                            in (0..2) -> {
                                                worldStats3[worldStats3.size - 1][0] += 1
                                                worldStats2[0] += 1
                                            }
                                            in (3..6) -> {
                                                worldStats3[worldStats3.size - 1][1] += 1
                                                worldStats2[1] += 1
                                            }
                                            in (7..14) -> {
                                                worldStats3[worldStats3.size - 1][2] += 1
                                                worldStats2[2] += 1
                                            }
                                            else -> {
                                                worldStats3[worldStats3.size - 1][3] += 1
                                                worldStats2[3] += 1
                                            }
                                        }
                                        if (agent.age < 14) {
                                            for (otherAgent in household.agents) {
                                                if (otherAgent.isMother) {
                                                    otherAgent.isOnMotherLeave = true
                                                    break
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        2 -> {
                            if (agent.daysImmune == 14) {
                                agent.healthStatus = 0
                            } else {
                                agent.daysImmune += 1
                            }
                        }
                        0 -> {
                            if (agent.age < 16) {
                                if ((0..9999).random() < 2) {
                                    randomInfection(agent)
                                }
                            } else {
                                if ((0..9999).random() == 0) {
                                    randomInfection(agent)
                                }
                            }
                        }
                    }
                    if (agent.RVImmunity) {
                        if (agent.RVImmunityDays == imMap["RV"]) {
                            agent.RVImmunityDays = 0
                            agent.RVImmunity = false
                        } else {
                            agent.RVImmunityDays += 1
                        }
                    }
                    if (agent.RSVImmunity) {
                        if (agent.RSVImmunityDays == imMap["RSV"]) {
                            agent.RSVImmunityDays = 0
                            agent.RSVImmunity = false
                        } else {
                            agent.RSVImmunityDays += 1
                        }
                    }
                    if (agent.AdVImmunity) {
                        if (agent.AdVImmunityDays == imMap["AdV"]) {
                            agent.AdVImmunityDays = 0
                            agent.AdVImmunity = false
                        } else {
                            agent.AdVImmunityDays += 1
                        }
                    }
                    if (agent.PIVImmunity) {
                        if (agent.PIVImmunityDays == imMap["PIV"]) {
                            agent.PIVImmunityDays = 0
                            agent.PIVImmunity = false
                        } else {
                            agent.PIVImmunityDays += 1
                        }
                    }
                    if (agent.CoVImmunity) {
                        if (agent.CoVImmunityDays == imMap["CoV"]) {
                            agent.CoVImmunityDays = 0
                            agent.CoVImmunity = false
                        } else {
                            agent.CoVImmunityDays += 1
                        }
                    }
                }
            }

            currentlyInfected += worldStats[0]
            val monthName = when (month) {
                1 -> "Января"
                2 -> "Февраля"
                3 -> "Марта"
                4 -> "Апреля"
                5 -> "Мая"
                6 -> "Июня"
                7 -> "Июля"
                8 -> "Августа"
                9 -> "Сентября"
                10 -> "Октября"
                11 -> "Ноября"
                else -> "Декабря"
            }
            Platform.runLater {
                series1.data.add(XYChart.Data<String, Number>(globalDay.toString(), currentlyInfected))
                series2.data.add(XYChart.Data<String, Number>(globalDay.toString(), worldStats[0]))
                dateLabelText.set("$day $monthName")
            }

            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\output\\results${numOfIter}.xlsx",
                    globalDay, worldStats)

            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\output\\resultsByAge${numOfIter}.xlsx",
                    globalDay, worldStats2)

            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\output\\resultsByEtiology${numOfIter}.xlsx",
                    globalDay, worldStats4)

            sleep(100)

            dayOfTheWeek += 1
            if (dayOfTheWeek == 8) {
                dayOfTheWeek = 1
                worldStats3.add(arrayListOf(0, 0, 0, 0))
            }
            day += 1
            if ((month == 7) && (day == 32)) {
                break
            }

            globalDay += 1
            tempDay += 1
//            if (tempDay == 366) {
//                tempDay = 0
//            }
//            if (tempDay == 59) {
//                tempDay = 60
//            }
            if (tempDay == 365) {
                tempDay = 0
            }

            if ((month in arrayListOf(1, 3, 5, 7, 8, 10) && (day == 32)) ||
                    (month in arrayListOf(4, 6, 9, 11) && (day == 31)) ||
                    (month == 2) and (day == 29)) {
                day = 1
                month += 1
                println("Month $month")
            } else if ((month == 12) && (day == 32)) {
                day = 1
                month = 1
                println("Month 1")
            }
        }
        return worldStats3
    }

    init {
        getShortestMetroPaths()
        createKindergartens()
        createSchools()
        createUniversities()
        createHomes()
        createMetro()
        createPublicSpaces()

//        val numberOfStationsWork = shortestMetroPaths[agent.household.home.closestMetroIndex * 216 +
//                            company.home.closestMetroIndex].size
        println()

        val ageDistributionInDistrictsMatrix = arrayListOf<ArrayList<Int>>()
        readTableInt("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\Tables\\age-num-of-people-districts.xlsx",
                18, 535, ageDistributionInDistrictsMatrix)

        val districtsInfoMatrix = arrayListOf<ArrayList<Int>>()
        readTableInt("D:\\Dev\\Projects\\KotlinProjects\\TornadoFX\\src\\Tables\\districts.xlsx",
                107, 163, districtsInfoMatrix)

        addHouseholdsToPool(districtsInfoMatrix, ageDistributionInDistrictsMatrix)

        progress.set(107.0 / 108.0)

        for (company in workplace.companies) {
            for (agent in company.agents) {
                if (agent.usesMetro) {
                    agent.numberOfStationsWork = shortestMetroPaths[agent.household.home.closestMetroIndex * 216 +
                            company.home.closestMetroIndex].size
                    if (company.home.closestMetroIndex == agent.household.home.closestMetroIndex) {
                        agent.usesMetro = false
                    } else {
                        metro[shortestMetroPaths[agent.household.home.closestMetroIndex * 216 +
                                company.home.closestMetroIndex][0]].addAgent(agent, isMorning = true)
                        metro[shortestMetroPaths[company.home.closestMetroIndex * 216 +
                                agent.household.home.closestMetroIndex][0]].addAgent(agent, isMorning = false)
//                        for ((index, metroIndex) in (shortestMetroPaths[household.home.closestMetroIndex * 216 +
//                                    workplace.companies[workplace.companies.size - 1].home.closestMetroIndex]).withIndex()) {
//                            metro[metroIndex].addAgent(agent, index, isMorning = true)
//                        }
//                        for ((index, metroIndex) in (shortestMetroPaths[
//                                workplace.companies[workplace.companies.size - 1].home.closestMetroIndex * 216 +
//                                household.home.closestMetroIndex]).withIndex()) {
//                            metro[metroIndex].addAgent(agent, index, isMorning = false)
//                        }
                    }
                }
//                if ((agent.usesMetro) && (company.home.closestMetroIndex == agent.household.home.closestMetroIndex)) {
//                    agent.usesMetro = false
//                }
            }
        }

        progress.set(1.0)
    }

    fun restartWorld() {
        month = 8
        day = 1
        tempDay = 211

        globalDay = 0
        dayOfTheWeek = 1

        households.parallelStream().forEach { household ->
            household.agents.forEach { agent ->

                agent.fluAImmunity = false
                agent.fluBImmunity = false
                agent.RVImmunity = false
                agent.RSVImmunity = false
                agent.AdVImmunity = false
                agent.PIVImmunity = false
                agent.CoVImmunity = false

                agent.RVImmunityDays = 0
                agent.RSVImmunityDays = 0
                agent.PIVImmunityDays = 0
                agent.AdVImmunityDays = 0
                agent.CoVImmunityDays = 0

                agent.isOnMotherLeave = false

                agent.healthStatus = when (agent.age) {
                    in 0..2 -> if ((0..99).random() < 2) 1 else 0
                    in 3..6 -> if ((0..999).random() < 8) 1 else 0
                    in 7..14 -> if ((0..999).random() < 5) 1 else 0
                    else -> if ((0..999).random() < 2) 1 else 0
                }

                agent.infectionType = if (agent.healthStatus == 1) {
                    when ((0..99).random()) {
                        in 0..59 -> "RV" // 60%
                        in 60..89 -> "AdV" // 30%
                        else -> "PIV"// 10%
                    }
                } else "none"

                agent.isAsymptomatic = agent.willBeAsymptomatic()
                agent.incubationPeriod = agent.willHaveIncubationPeriod()
                agent.shouldBeInfected = agent.willBeInfected()
                agent.daysInfected = if (agent.healthStatus == 1)
                    ((1 - agent.incubationPeriod)..agent.shouldBeInfected).random() else 0
                agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
                agent.meanViralLoad = agent.willBeMeanViralLoad()
            }
        }
    }

    fun runR0(db: Double, bMap: Map<String, Double>, tMap: Map<String, Double>, infection: String, m: Int, d: Int): Int {
        durationBias = db

        month = m
        day = d
        tempDay = when (month) {
            1 -> 9
            2 -> 40
            3 -> 68
            4 -> 98
            5 -> 129
            6 -> 159
            7 -> 189
            8 -> 220
            9 -> 251
            10 -> 281
            11 -> 313
            12 -> 343
            else -> error("Wrong month")
        }

        globalDay = 0
        dayOfTheWeek = 1

        var numOfInfected = 0
        households.parallelStream().forEach { household ->
            household.agents.forEach { agent ->

                agent.fluAImmunity = false
                agent.fluBImmunity = false
                agent.RVImmunity = false
                agent.RSVImmunity = false
                agent.AdVImmunity = false
                agent.PIVImmunity = false
                agent.CoVImmunity = false

                agent.RVImmunityDays = 0
                agent.RSVImmunityDays = 0
                agent.PIVImmunityDays = 0
                agent.AdVImmunityDays = 0
                agent.CoVImmunityDays = 0

                agent.isOnMotherLeave = false

                agent.healthStatus = 0
                agent.isAsymptomatic = false
                agent.incubationPeriod = 0
                agent.shouldBeInfected = 0
                agent.daysInfected = 0
                agent.isStayingHomeWhenInfected = false
                agent.meanViralLoad = 0.0
                agent.findSusceptibility(bMap)
            }
        }

        val foundHousehold = households[(0 until households.size).random()]
        val foundAgent = foundHousehold.agents[(0 until foundHousehold.agents.size).random()]
        foundAgent.infectionType = infection
        foundAgent.healthStatus = 1
        foundAgent.updateHealthParameters()

        while(true) {

            var holiday = false
            if (dayOfTheWeek == 7) {
                holiday = true
            }
            if ((month == 1) && (day in arrayListOf(1, 2, 3, 7))) {
                holiday = true
            }
            if ((month == 5) && (day in arrayListOf(1, 9))) {
                holiday = true
            }
            if ((month == 2) && (day == 23)) {
                holiday = true
            }
            if ((month == 3) && (day == 8)) {
                holiday = true
            }
            if ((month == 6) && (day == 12)) {
                holiday = true
            }

            var workingHoliday = false
            if (dayOfTheWeek == 6) {
                workingHoliday = true
            }

            var kinderHoliday = false
            if (month in arrayListOf(7, 8)) {
                kinderHoliday = true
            }

            // Каникулы
            // Осенние - 05.11.2007 - 11.11.2007 - 7 дней;
            // Зимние - 26.12.2007 - 09.01.2008 - 15 дней;
            // Весенние - 22.03.2008 - 31.03.2008 - 10 дней.
            // Зимние - 28.12.2007 - 09.03.2008
            var schoolHoliday = false
            if (month in arrayListOf(6, 7, 8)) {
                schoolHoliday = true
            }
            if ((month == 11) && (day in arrayListOf(5, 6, 7, 8, 9, 10, 11))) {
                schoolHoliday = true
            }
//            if ((month == 12) && (day in arrayListOf(26, 27, 28, 29, 30, 31))) {
            if ((month == 12) && (day in arrayListOf(28, 29, 30, 31))) {
                schoolHoliday = true
            }
            if ((month == 1) && (day in arrayListOf(6, 7, 8, 9))) {
                schoolHoliday = true
            }
            if ((month == 3) && (day in arrayListOf(22, 23, 24, 25, 26, 27, 28, 29, 30, 31))) {
                schoolHoliday = true
            }

            var universityHoliday = false
            if ((month == 12) && (day in arrayListOf(22, 23, 24, 25, 26, 27, 28, 29, 30, 31))) {
                universityHoliday = true
            }
            if (month == 1) {
                if (day !in arrayListOf(11, 15, 19, 23, 27)) {
                    universityHoliday = true
                }
            }
            if (month == 6) {
                if (day !in arrayListOf(11, 15, 19, 23, 27)) {
                    universityHoliday = true
                }
            }
            if (month in arrayListOf(7, 8)) {
                universityHoliday = true
            }
            if ((month == 2) && (day in arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))) {
                universityHoliday = true
            }

            households.parallelStream().forEach { household ->
                household.agents.forEach { agent ->
                    if (agent.healthStatus == 1) {
                        agent.findInfectivity()
                        contactsInHouseholdForAgent(household, false, agent, tMap)
                    }
                }
            }

            if ((universityHoliday) && (schoolHoliday) && (kinderHoliday) && (workingHoliday)) {
                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if (agent.healthStatus == 1) {
                            contactsInHouseholdForAgent(household, true, agent, tMap)
                        }
                    }
                }
            } else if (holiday) {
                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if (agent.healthStatus == 1) {
                            contactsInHouseholdForAgent(household, true, agent, tMap)
                        }
                    }
                }
            } else {
                if (!workingHoliday) {
                    if (!kinderHoliday) {
                        kindergartens.parallelStream().forEach { kindergarten ->
                            kindergarten.groupsByAge.forEach { groupByAge ->
                                groupByAge.forEach { group ->
                                    group.agents.forEach { agent ->
                                        if ((agent.healthStatus == 1) &&
                                                (!agent.isStayingHomeWhenInfected)) {
                                            contactsInGroupForAgent(group, agent, tMap)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    workplace.companies.parallelStream().forEach { company ->
                        company.agents.forEach { agent ->
                            if ((agent.healthStatus == 1) &&
                                    (!agent.isStayingHomeWhenInfected) && (!agent.isOnMotherLeave)) {
                                contactsInGroupForAgentAtWork(company, agent, tMap)
                            }
                        }
                    }
                }
                if (!schoolHoliday) {
                    schools.parallelStream().forEach { school ->
                        school.groupsByAge.forEachIndexed { index, groupByAge ->
                            if ((index == 0) && (workingHoliday)) {

                            } else {
                                groupByAge.forEach { group ->
                                    group.agents.forEach { agent ->
                                        if ((agent.healthStatus == 1) &&
                                                (!agent.isStayingHomeWhenInfected)) {
                                            contactsInGroupForAgent(group, agent, tMap)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!universityHoliday) {
                    universities.parallelStream().forEach { university ->
                        university.groupsByAge.forEach { groupByAge ->
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if ((agent.healthStatus == 1) &&
                                            (!agent.isStayingHomeWhenInfected) && (!agent.isOnMotherLeave)) {
                                        contactsInGroupForAgent(group, agent, tMap)
                                    }
                                }
                            }
                        }
                    }
                }

                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if ((agent.activityStatus == 0) ||
                                (agent.isStayingHomeWhenInfected) ||
                                ((agent.activityStatus == 1) && kinderHoliday) ||
                                ((agent.activityStatus == 2) && schoolHoliday) ||
                                ((agent.age == 7) && workingHoliday) ||
                                ((agent.activityStatus == 3) && universityHoliday) ||
                                ((agent.activityStatus == 4) && workingHoliday)) {

                            if (agent.healthStatus == 1) {

                                household.agents.forEach { agent2 ->
                                    if ((agent2.activityStatus == 0) ||
                                            (agent2.isStayingHomeWhenInfected) ||
                                            ((agent2.activityStatus == 1) && kinderHoliday) ||
                                            ((agent2.activityStatus == 2) && schoolHoliday) ||
                                            ((agent2.age == 7) && workingHoliday) ||
                                            ((agent2.activityStatus == 3) && universityHoliday) ||
                                            ((agent2.activityStatus == 4) && workingHoliday)) {

                                        val agent2IsImmune = when (agent.infectionType) {
                                            "fluA" -> agent2.fluAImmunity
                                            "fluB" -> agent2.fluBImmunity
                                            "RV" -> agent2.RVImmunity
                                            "RSV" -> agent2.RSVImmunity
                                            "AdV" -> agent2.AdVImmunity
                                            "PIV" -> agent2.PIVImmunity
                                            "CoV" -> agent2.CoVImmunity
                                            else -> true
                                        }
                                        if ((!agent2IsImmune) &&
                                                (agent2.healthStatus == 0)) {

                                            val durationCoefficient = 1 / (1 + exp(
                                                    -getAdditionalHouseholdContactDuration() + durationBias))

                                            val curTemp = (temp[tempDay] - minTemp) / maxMinTemp
                                            val externalInfluence = (tMap[agent.infectionType] ?: error("Required")) *
                                                    curTemp + 1.0

                                            val randNum = (0..9999).random() * 0.0001
                                            val susceptibility = when (agent.infectionType) {
                                                "fluA" -> agent.fluASusceptibility
                                                "fluB" -> agent.fluBSusceptibility
                                                "RV" -> agent.RVSusceptibility
                                                "RSV" -> agent.RSVSusceptibility
                                                "AdV" -> agent2.AdVSusceptibility
                                                "PIV" -> agent2.PIVSusceptibility
                                                "CoV" -> agent2.CoVSusceptibility
                                                else -> 0.0
                                            }
                                            val probabilityOfInfection = agent.infectivity *
                                                    susceptibility *
                                                    externalInfluence *
                                                    durationCoefficient
                                            if (randNum < probabilityOfInfection) {
                                                agent2.healthStatus = 3
                                                agent2.infectionType = agent.infectionType
                                            }

                                        }
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
                            numOfInfected += 1
                            agent.healthStatus = 2
                        }
                        1 -> {
                            if (agent.daysInfected == agent.shouldBeInfected) {
                                agent.healthStatus = 2
                            } else {
                                agent.daysInfected += 1
                                if (!agent.isStayingHomeWhenInfected) {
                                    agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
                                    if (agent.isStayingHomeWhenInfected) {
                                        if (agent.age < 14) {
                                            for (otherAgent in household.agents) {
                                                if (otherAgent.isMother) {
                                                    otherAgent.isOnMotherLeave = true
                                                    break
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


            dayOfTheWeek += 1
            if (dayOfTheWeek == 8) {
                dayOfTheWeek = 1
            }
            day += 1
            if (globalDay == 15) {
                break
            }

            globalDay += 1
            tempDay += 1
            if (tempDay == 365) {
                tempDay = 0
            }

            if ((month in arrayListOf(1, 3, 5, 7, 8, 10) && (day == 32)) ||
                    (month in arrayListOf(4, 6, 9, 11) && (day == 31)) ||
                    (month == 2) and (day == 29)) {
                day = 1
                month += 1
                println("Month $month")
            } else if ((month == 12) && (day == 32)) {
                day = 1
                month = 1
                println("Month 1")
            }
        }
        return numOfInfected
    }
}