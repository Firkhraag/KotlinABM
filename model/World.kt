package model

import utility.readTableInt
import utility.writeTableResult
import kotlinx.coroutines.*
import kotlin.math.max

class World {

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

    private val temp = arrayListOf(-5.8, -5.9, -5.9, -5.9,
        -6.0, -6.0, -6.1, -6.1, -6.2, -6.2, -6.2, -6.3,
        -6.3, -6.4, -6.5, -6.5, -6.6, -6.6, -6.7, -6.7,
        -6.8, -6.8, -6.9, -6.9, -7.0, -7.0, -7.0, -7.1, -7.1,
        -7.1, -7.1, -7.2, -7.2, -7.2, -7.2, -7.2, -7.2, -7.1,
        -7.1, -7.1, -7.0, -7.0, -6.9, -6.8, -6.8, -6.7, -6.6,
        -6.5, -6.4, -6.3, -6.1, -6.0, -5.9, -5.7, -5.6, -5.4,
        -5.2, -5.0, -4.8, -4.7, -4.7, -4.5, -4.2, -4.0, -3.8,
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

//    private val maxTemp = 19.4
    private val minTemp = -7.2

    private val maxMinTemp = 26.6

    private var day = 31
    private var month = 7
    private var year = 1

    private var globalDay = 0
    private var tempDay = 212
    private var dayOfTheWeek = 1

    // Susceptible, Infected, New Cases
    private var worldStats = arrayListOf(0, 0, 0, 0, 0)

    private val kindergarten = Kindergarten()
    private val school = School()
    private val university = University()
    private val workplace = Workplace()
    private val households = arrayListOf<Household>()

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

    @Synchronized private fun addAgentsToGroups(agents: ArrayList<Agent>, household: Household, index: Int) {
        agents.forEach { agent ->
            when (agent.activityStatus) {
                1 -> kindergarten.addAgent(agent)
                2 -> school.addAgent(agent)
                4 -> university.addAgent(agent)
                5 -> {
                    workplace.addAgent(agent)
//                    var groupFound = false
//                    for ((groupIndex, ageGroup) in kindergarten.groupsByAge.withIndex()) {
//                        if (kindergarten.adultNeeded[groupIndex]) {
//                            for (group in ageGroup) {
//                                if (!group.hasAdult) {
//                                    group.hasAdult = true
//                                    group.addAgent(agent)
//                                    groupFound = true
//                                    kindergarten.adultNeeded[groupIndex] = false
//                                    break
//                                }
//                            }
//                        }
//                    }
//                    if (!groupFound) {
//                        for ((groupIndex, ageGroup) in school.groupsByAge.withIndex()) {
//                            if (school.adultNeeded[groupIndex]) {
//                                for (group in ageGroup) {
//                                    if (!group.hasAdult) {
//                                        group.hasAdult = true
//                                        group.addAgent(agent)
//                                        groupFound = true
//                                        school.adultNeeded[groupIndex] = false
//                                        break
//                                    }
//                                }
//                            }
//                        }
//                        if (!groupFound) {
//                            for ((groupIndex, ageGroup) in university.groupsByAge.withIndex()) {
//                                if (university.adultNeeded[groupIndex]) {
//                                    for (group in ageGroup) {
//                                        if (!group.hasAdult) {
//                                            group.hasAdult = true
//                                            group.addAgent(agent)
//                                            groupFound = true
//                                            university.adultNeeded[groupIndex] = false
//                                            break
//                                        }
//                                    }
//                                }
//                            }
//                            if (!groupFound) {
//                                workplace.addAgent(agent)
//                            }
//                        }
//                    }
                }
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
                println("Current index $index")
                val indexFor1People = index * 5 + 1
                val indexFor2People = index * 5 + 2
                val indexFor3People = index * 5 + 3
                val indexFor4People = index * 5 + 4
                val indexFor5People = index * 5 + 5

                for (i in 0..it[58]) {
//                for (i in 1..100) {
                    val household = Household("1P")

                    val agent = createAgent(index, indexFor1People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    addAgentsToGroups(arrayListOf(agent), household, index)
                }
                for (i in 0..it[59]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP2P0C")

                    val agentMale = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[60] - it[61])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP3P0C")

                    val agentMale = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[61]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP3P1C")

                    val agentMale = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)

                    if (agentMale.activityStatus != 0) {
                        agentFemale.isMother = true
                        if ((agent.age <= 2) && (agent.activityStatus == 0)) {
                            agentFemale.activityStatus = 0
                        }
                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[62] - it[63] - it[64])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP4P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[63]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP4P1C")

                    val agentMale = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, null)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[64]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP4P2C")

                    val agentMale = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false,
                            true, null, false)
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agentFemale.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[65] - it[66] - it[67] - it[68])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP5P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[66]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP5P1C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[67]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP5P2C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[68]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP5P3C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[69] - it[70] - it[71] - it[72])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP6P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[70]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP6P1C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[71]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP6P2C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[72]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP6P3C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[73]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP4P0С")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[74] - it[75])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP5P0С")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[75]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP5P1С")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[76] - it[77] - it[78])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP6P0С")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[77]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP6P1С")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[78]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP6P2С")

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
//                    if ((agentMale.activityStatus != 0) &&
//                            (agentMale2.activityStatus != 0) &&
//                            (agentFemale.activityStatus != 0)) {
//                        agentFemale2.isMother = true
//                        if ((agent2.age <= 2) &&
//                                (agent2.activityStatus == 0)) {
//                            agentFemale2.activityStatus = 0
//                        }
//                    }

                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[79] - it[80])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC2P0C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, true)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[80]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC2P1C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, false)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

                    agent.isMother = true
                    if ((agent2.age <= 2) && (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[81] - it[82] - it[83])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC3P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[82]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, true)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[83]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC3P2C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[84] - it[85] - it[86] - it[87])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC4P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[85]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC4P1C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[86]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC4P2C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[87]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC4P3C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, false, null, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[88] - it[89])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SFWC2P0C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, true)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[89]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SFWC2P1C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

                    agent.isMother = true
                    if ((agent2.age <= 2) &&
                            (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[90] - it[91] - it[92])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SFWC3P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[91]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SFWC3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, true)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[92]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SFWC3P2C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[93] - it[94])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[94]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[95] - it[96] - it[97])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP4P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[96]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[97]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP4P2C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[98] - it[99])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[99]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[55] - it[101] - it[102])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP4P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[101]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, true, null, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false, null, null, true)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true, null, agent.age)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[102]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP4P2C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[103] - it[104] - it[105])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP5P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[104]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP5P1C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[105]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP5P2C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[106] - it[107])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O2P0C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[107]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O2P1C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)

                    agent.isMother = true
                    if ((agent2.age <= 2) &&
                            (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[108] - it[109] - it[110])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[109]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[110]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O3P2C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[111] - it[112] - it[113])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O4P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[112]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[113]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O4P2C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, false)
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                            ageDistributionInDistrictsMatrix, true)

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..(it[114] - it[115] - it[116])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O5P0C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[115]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O5P1C")

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
                    addAgentsToGroups(agents, household, index)
                }
                for (i in 0..it[116]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O5P2C")

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
                    addAgentsToGroups(agents, household, index)
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
        println("Workplaces created")
//        school.generateLastBarabasiAlbertNetworks()
//        println("Schools created")
//        kindergarten.generateLastBarabasiAlbertNetworks()
//        println("Kindergartens created")
//        university.generateLastBarabasiAlbertNetworks()
//        println("Universities created")

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
                val durationCoeff = if (additionalContactInHoliday) {
                    getAdditionalHouseholdContactDuration() / 24.0
                } else {
                    getHouseholdContactDuration() / 24.0
                }

                val curTemp = (temp[tempDay] - minTemp) / maxMinTemp
                val tCoeff = tMap[agent.infectionType] ?: 0.0
                val externalInfluence = tCoeff * curTemp + 1.0

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
                var probab = agent.infectivity * susceptibility * externalInfluence * durationCoeff
//                println("Age: ${agent.age}, Age2: ${agent2.age}, DurCoef: $durationCoeff, Inf: ${agent.infectivity}, Ig: ${agent.getIgLevel()} Susc: $susceptibility, curT: $curTemp, ExInfl: $externalInfluence, Prob: $probab")
//                readLine()
//                probabStats[0] += probab
                if (randNum < probab) {
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
                else -> true
            }
            if ((agent2.healthStatus == 0) &&
                    (!agent2IsImmune) &&
                    (!agent2.isOnMotherLeave)) {

                val durationCoeff = when (agent.activityStatus) {
                    1 -> getKindergartenContactDuration() / 24.0
                    2 -> getSchoolContactDuration() / 24.0
                    4 -> getUniversityContactDuration() / 24.0
                    5 -> getWorkplaceContactDuration() / 24.0
                    else -> 1.0
                }
                val curTemp = (temp[tempDay] - minTemp) / maxMinTemp
                val tCoeff = tMap[agent.infectionType] ?: 0.0
                val externalInfluence = tCoeff * curTemp + 1.0

//                when (agent2.activityStatus) {
//                    1 -> contactStats[1] += 1
//                    2 -> contactStats[2] += 1
//                    3 -> contactStats[3] += 1
//                    4 -> contactStats[4] += 1
//                    5 -> contactStats[5] += 1
//                }

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
                var probab = agent.infectivity * susceptibility * externalInfluence * durationCoeff
//                when (agent2.activityStatus) {
//                    1 -> probabStats[1] += probab
//                    2 -> probabStats[2] += probab
//                    3 -> probabStats[3] += probab
//                    4 -> probabStats[4] += probab
//                    5 -> probabStats[5] += probab
//                }
                if (randNum < probab) {
                    agent2.healthStatus = 3
                    agent2.infectionType = agent.infectionType
                }
            }
        }
    }

    private fun contactsInGroupForAgentAtWork(group: Group, agent: Agent, tMap: Map<String, Double>) {
        group.agents.forEachIndexed { index, agent2 ->
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
            if ((agent2.healthStatus == 0) &&
                    (!agent2IsImmune) &&
                    (index in agent.connectedWorkAgents) &&
                    (!agent2.isOnMotherLeave)) {

                val durationCoeff = getWorkplaceContactDuration() / 24.0
                val curTemp = (temp[tempDay] - minTemp) / maxMinTemp
                val tCoeff = tMap[agent.infectionType] ?: 0.0
                val externalInfluence = tCoeff * curTemp + 1.0

//                contactStats[5] += 1

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
                var probab = agent.infectivity * susceptibility * externalInfluence * durationCoeff
//                probabStats[5] += probab
                if (randNum < probab) {
                    agent2.healthStatus = 3
                    agent2.infectionType = agent.infectionType
                }
            }
        }
    }

    private fun randomInfection(agent: Agent) {
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

    fun runSimulation(a: Double, bMap: Map<String, Double>, tMap: Map<String, Double>,
                      imMap: Map<String, Int>, durCoeff: Double): ArrayList<ArrayList<Int>> {

        val worldStats2 = arrayListOf(0, 0, 0, 0)
        val worldStats3 = arrayListOf(arrayListOf(0, 0, 0, 0))
        val worldStats4 = arrayListOf(0, 0, 0, 0, 0, 0, 0)

        households.parallelStream().forEach { household ->
            household.agents.forEach { agent ->
                agent.findSusceptibility(bMap)
            }
        }

        while(true) {

            worldStats[3] = 0
            worldStats[4] = 0

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

//            contactStats = arrayListOf(0, 0, 0, 0, 0, 0)
//            probabStats = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

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

            // Каникулы 2007/2008
            //
            // Осенние - с 05.11.2007 г. по 11.11.2007 г. - 7 дней;
            //
            // Зимние - с 26.12.2007 г. по 09.01.2008 г. - 15 дней;
            //
            // Весенние - с 22.03.2008 г. по 31.03.2008 г. - 10 дней.
            var schoolHoliday = false
            if (month in arrayListOf(6, 7, 8)) {
                schoolHoliday = true
            }
            if ((month == 11) && (day in arrayListOf(5, 6, 7, 8, 9, 10, 11))) {
                schoolHoliday = true
            }
            if ((month == 12) && (day in arrayListOf(26, 27, 28, 29, 30, 31))) {
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
                        agent.findInfectivity(a)
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
                        kindergarten.groupsByAge.parallelStream().forEach { groupByAge ->
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
                    workplace.workingGroups.parallelStream().forEach { group ->
                        group.agents.forEach { agent ->
                            if ((agent.healthStatus == 1) &&
                                    (!agent.isStayingHomeWhenInfected) && (!agent.isOnMotherLeave)) {
                                contactsInGroupForAgentAtWork(group, agent, tMap)
                            }
                        }
                    }
                }
                if (!schoolHoliday) {
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
                if (!universityHoliday) {
                    university.groupsByAge.parallelStream().forEach { groupByAge ->
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

                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if ((agent.activityStatus == 0) ||
                                (agent.isStayingHomeWhenInfected) ||
                                ((agent.activityStatus == 1) && kinderHoliday) ||
                                ((agent.activityStatus == 2) && schoolHoliday) ||
                                ((agent.age == 7) && workingHoliday) ||
                                ((agent.activityStatus == 4) && universityHoliday) ||
                                ((agent.activityStatus == 5) && workingHoliday)) {

                            if (agent.healthStatus == 1) {

                                household.agents.forEach { agent2 ->
                                    if ((agent2.activityStatus == 0) ||
                                            (agent2.isStayingHomeWhenInfected) ||
                                            ((agent2.activityStatus == 1) && kinderHoliday) ||
                                            ((agent2.activityStatus == 2) && schoolHoliday) ||
                                            ((agent2.age == 7) && workingHoliday) ||
                                            ((agent2.activityStatus == 4) && universityHoliday) ||
                                            ((agent2.activityStatus == 5) && workingHoliday)) {

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

                                            val durationCoeff = getAdditionalHouseholdContactDuration() / 24.0
                                            val curTemp = (temp[tempDay] - minTemp) / maxMinTemp
                                            val tCoeff = tMap[agent.infectionType] ?: 0.0
                                            val externalInfluence = tCoeff * curTemp + 1.0

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
                                            val probab = agent.infectivity *
                                                    susceptibility *
                                                    externalInfluence *
                                                    durationCoeff
                                            if (randNum < probab) {
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
                            worldStats[0] -= 1
                            worldStats[1] += 1
                            worldStats[3] += 1

                            when (agent.infectionType) {
                                "fluA" -> worldStats4[0] += 1
                                "fluB" -> worldStats4[1] += 1
                                "RV" -> worldStats4[2] += 1
                                "RSV" -> worldStats4[3] += 1
                                "AdV" -> worldStats4[4] += 1
                                "PIV" -> worldStats4[5] += 1
                                "CoV" -> worldStats4[6] += 1
                            }

//                            when (agent.age) {
//                                in (0..2) -> {
//                                    worldStats3[worldStats3.size - 1][0] += 1
//                                    worldStats2[0] += 1
//                                }
//                                in (3..6) -> {
//                                    worldStats3[worldStats3.size - 1][1] += 1
//                                    worldStats2[1] += 1
//                                }
//                                in (7..14) -> {
//                                    worldStats3[worldStats3.size - 1][2] += 1
//                                    worldStats2[2] += 1
//                                }
//                                else -> {
//                                    worldStats3[worldStats3.size - 1][3] += 1
//                                    worldStats2[3] += 1
//                                }
//                            }

                            agent.updateHealthParameters()
                            if (agent.isStayingHomeWhenInfected) {
                                worldStats[4] += 1
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
                                worldStats[1] -= 1
                                worldStats[2] += 1
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
                                        worldStats[4] += 1
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
                                worldStats[0] += 1
                                worldStats[2] -= 1
                            } else {
                                agent.daysImmune += 1
                            }
                        }
                        0 -> {
                            if (agent.age < 18) {
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
                }
            }

//            if (contactStats[0] == 0) {
//                probabStats[0] = 0.0
//            } else {
//                probabStats[0] /= contactStats[0].toDouble()
//            }
//            if (contactStats[1] == 0) {
//                probabStats[1] = 0.0
//            } else {
//                probabStats[1] /= contactStats[1].toDouble()
//            }
//            if (contactStats[2] == 0) {
//                probabStats[2] = 0.0
//            } else {
//                probabStats[2] /= contactStats[2].toDouble()
//            }
//            if (contactStats[3] == 0) {
//                probabStats[3] = 0.0
//            } else {
//                probabStats[3] /= contactStats[3].toDouble()
//            }
//            if (contactStats[4] == 0) {
//                probabStats[4] = 0.0
//            } else {
//                probabStats[4] /= contactStats[4].toDouble()
//            }
//            if (contactStats[5] == 0) {
//                probabStats[5] = 0.0
//            } else {
//                probabStats[5] /= contactStats[5].toDouble()
//            }

            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\output\\results.xlsx",
                    globalDay, worldStats)

            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\output\\resultsByAge.xlsx",
                    globalDay, worldStats2)

            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\output\\resultsByEtiology.xlsx",
                    globalDay, worldStats4)


//            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\output\\contacts.xlsx",
//                    globalDay, contactStats)
//
//            writeTableResult2("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\output\\probab.xlsx",
//                    globalDay, probabStats)


            dayOfTheWeek += 1
            if (dayOfTheWeek == 8) {
                dayOfTheWeek = 1
                worldStats3.add(arrayListOf(0, 0, 0, 0))
            }
            day += 1
            if ((month == 7) && (day == 31)) {
                break
            }

            globalDay += 1
            tempDay += 1
            if (tempDay == 366) {
                tempDay = 0
            }
            if (tempDay == 59) {
                tempDay = 60
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
                year += 1
                println("Month 1")
            }
//            readLine()
        }
        return worldStats3
    }

//    fun resetState() {
//        day = 31
//        month = 7
//        year = 1
//
//        globalDay = 0
//        tempDay = 212
//        dayOfTheWeek = 1
//
//        // Susceptible, Infected, New Cases
//        worldStats = arrayListOf(0, 0, 0, 0, 0)
//
//        households.forEach { household ->
//            household.agents.forEach { agent ->
//                agent.healthStatus = when (agent.age) {
//                    in 0..2 -> if ((0..99).random() == 0) 1 else 0
//                    in 3..6 -> if ((0..999).random() < 5) 1 else 0
//                    in 7..14 -> if ((0..999).random() < 3) 1 else 0
//                    else -> if ((0..999).random() < 2) 1 else 0
//                }
//                agent.infectionType = if (agent.healthStatus == 1) {
//                    if ((0..99).random() < 50) {
//                        "RV"
//                    } else {
//                        "RSV"
//                    }
//                } else {
//                    "none"
//                }
//                agent.daysImmune = if (agent.healthStatus == 2) 1 else 0
//
//                agent.fluAImmunity = false
//                agent.fluBImmunity = false
//                agent.RVImmunity = false
//                agent.RSVImmunity = false
//
//                agent.RVImmunityDays = 0
//                agent.RSVImmunityDays = 0
//
//                agent.updateHealthParameters()
//                agent.daysInfected = if (agent.healthStatus == 1) ((1 - agent.incubationPeriod)..agent.shouldBeInfected).random() else 0
//                agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
//            }
//        }
//    }

    init {

        val ageDistributionInDistrictsMatrix = arrayListOf<ArrayList<Int>>()
        readTableInt("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\Tables\\age-num-of-people-districts.xlsx",
                18, 535, ageDistributionInDistrictsMatrix)

        val districtsInfoMatrix = arrayListOf<ArrayList<Int>>()
        readTableInt("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\Tables\\districts.xlsx",
                107, 163, districtsInfoMatrix)
        addHouseholdsToPool(districtsInfoMatrix, ageDistributionInDistrictsMatrix)
    }
}