package application.model

import javafx.beans.property.ReadOnlyDoubleWrapper
import javafx.beans.property.SimpleStringProperty
import javafx.scene.chart.XYChart
import application.utility.readTableInt
import application.utility.writeTableInt
import kotlinx.coroutines.*
import application.utility.readTableDouble
import javafx.application.Platform
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.*

class World(private val progress: ReadOnlyDoubleWrapper) {

    // Температура воздуха, начиная с 1 января
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
    // Минимальная температура воздуха
    private val minTemp = -7.2
    // Max - Min температура
    private val maxMinTemp = 26.6

    // Параметры, отвечающие за дату
    // День
    private var day = 1
    // Месяц
    private var month = 8
    // День недели
    private var dayOfTheWeek = 1
    // Шаг модели
    private var globalDay = 0
    // День в году
    private var dayOfTheYear = 211
    // Номер недели
    private var globalWeek = 0

    // Данные по заболеваемости
    private val realData = arrayListOf<ArrayList<Double>>()

    // Коллективы
    // Детские сады
    private val kindergarten = Kindergarten()
    // Школы
    private val school = School()
    // Университеты
    private val university = University()
    // Рабочие коллективы
    private val workplace = Workplace()
    // Домохозяйства
    private val households = arrayListOf<Household>()

    // Вероятности случайного инфицирования различными инфекциями в различное время
    private val etiologiesRatio = arrayListOf<ArrayList<Double>>()

    // Продолжительности контактов в домохозяйстве
    private fun getHouseholdContactDuration(): Double {
        // Normal distribution (mean = 12.4, SD = 5.13)
        val rand = java.util.Random()
        return min(24.0, max(0.0,12.4 + rand.nextGaussian() * 5.13))
    }

    private fun getHouseholdContactDurationWithKindergarten(): Double {
        // Normal distribution (mean = 5.0, SD = 2.05)
        val rand = java.util.Random()
        return min(20.0, max(0.0,5.0 + rand.nextGaussian() * 2.05))
    }

    private fun getHouseholdContactDurationWithWork(): Double {
        // Normal distribution (mean = 8.0, SD = 3.28)
        val rand = java.util.Random()
        return min(20.0, max(0.0,5.0 + rand.nextGaussian() * 2.05))
    }

    private fun getHouseholdContactDurationWithSchool(): Double {
        // Normal distribution (mean = 6.0, SD = 2.46)
        val rand = java.util.Random()
        return min(20.0, max(0.0,6.0 + rand.nextGaussian() * 2.46))
    }

    private fun getHouseholdContactDurationWithUniversity(): Double {
        // Normal distribution (mean = 9.0, SD = 3.69)
        val rand = java.util.Random()
        return min(20.0, max(0.0,7.0 + rand.nextGaussian() * 3.69))
    }

    // Продолжительности контактов на работе
    private fun getWorkplaceContactDuration(): Double {
        // Exponential distribution (mean = 3.0, SD = 3.0)
        val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(1.0, 3.0)
        return min(8.0, erlangDistribution.sample())
    }

    // Продолжительности контактов в школе
    private fun getSchoolContactDuration(): Double {
        // Normal distribution (mean = 4.783, SD = 2.67)
        val rand = java.util.Random()
        return max(0.0,4.783 + rand.nextGaussian() * 2.67)
    }

    // Продолжительности контактов в университете
    private fun getUniversityContactDuration(): Double {
        // Exponential distribution (mean = 2.1, SD = 3.0)
        val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(1.0, 2.1)
        return erlangDistribution.sample()
    }

    // Продолжительности контактов в детском саде
    private fun getKindergartenContactDuration(): Double {
        // Normal distribution (mean = 5.88, SD = 2.52)
        val rand = java.util.Random()
        return max(0.0,5.88 + rand.nextGaussian() * 2.52)
    }

    // Создание агента (индекс района,
    // индекс числа членов домохозяйства,
    // массив данных по районам,
    // массив данных по возрастам,
    // является ли агент ребенком,
    // является ли агент мужчиной,
    // возраст родителя, если агент ребенок,
    // является ли родителем взрослого)
    private fun createAgent(okatoIndex: Int,
                            biasedIndex: Int,
                            districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                            ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>,
                            isChild: Boolean,
                            isMale: Boolean? = null,
                            parentAge: Int? = null,
                            isOld: Boolean? = null): Agent {
        // Случайное число
//        var randomNum = (1..100).random()

        // Случайное число для возраста
        val ageRandomNum = (1..100).random()
        // Случайное число для пола
        val sexRandomNum = (1..100).random()
        if (isChild) {
            // Случайное число для возрастной группы
            val ageGroupRandomNum = (1..100).random()
            // Ребенок
            return when(parentAge) {
                // Родитель 18 лет, ребенок - 0 лет
                18 -> Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), 0)
                // Родитель 19 лет, ребенок - 0-1 лет
                19 -> Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..1).random())
                // Родитель 20 лет, ребенок - 0-2 лет
                20 -> Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..2).random())
                // Родитель 21 год, ребенок - 0-3 лет
                21 -> Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..3).random())
                // Родитель 22 года, ребенок - 0-4 лет
                22 -> Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                // Родитель 23 года, ребенок - 0-5 лет
                23 -> {
                    if (ageRandomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                        Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                    } else {
                        Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), 5)
                    }
                }
                // Родитель 24 года, ребенок - 0-6 лет
                24 -> {
                    if (ageRandomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                        Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                    } else {
                        Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..6).random())
                    }
                }
                // Родитель 25 лет, ребенок - 0-7 лет
                25 -> {
                    if (ageRandomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                        Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                    } else {
                        Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..7).random())
                    }
                }
                // Родитель 26 лет, ребенок - 0-8 лет
                26 -> {
                    if (ageRandomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                        Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                    } else {
                        Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..8).random())
                    }
                }
                // Родитель 27 лет, ребенок - 0-9 лет
                27 -> {
                    if (ageRandomNum in (1..districtsInfoMatrix[okatoIndex][153])) {
                        Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                    } else {
                        Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..9).random())
                    }
                }
                // Родитель 28 лет, ребенок - 0-10 лет
                28 -> {
                    when (ageRandomNum) {
                        in (1..districtsInfoMatrix[okatoIndex][154]) -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                        }
                        in (1..districtsInfoMatrix[okatoIndex][155]) -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..9).random())
                        }
                        else -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][121]), 10)
                        }
                    }
                }
                // Родитель 29 лет, ребенок - 0-11 лет
                29 -> {
                    when (ageRandomNum) {
                        in (1..districtsInfoMatrix[okatoIndex][154]) -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                        }
                        in (1..districtsInfoMatrix[okatoIndex][155]) -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..9).random())
                        }
                        else -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][121]), (10..11).random())
                        }
                    }
                }
                // Родитель 30 лет, ребенок - 0-12 лет
                30 -> {
                    when (ageRandomNum) {
                        in (1..districtsInfoMatrix[okatoIndex][154]) -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                        }
                        in (1..districtsInfoMatrix[okatoIndex][155]) -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..9).random())
                        }
                        else -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][121]), (10..12).random())
                        }
                    }
                }
                // Родитель 31 год, ребенок - 0-13 лет
                31 -> {
                    when (ageRandomNum) {
                        in (1..districtsInfoMatrix[okatoIndex][154]) -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                        }
                        in (1..districtsInfoMatrix[okatoIndex][155]) -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..9).random())
                        }
                        else -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][121]), (10..13).random())
                        }
                    }
                }
                // Родитель 32 года, ребенок - 0-14 лет
                32 -> {
                    when (ageRandomNum) {
                        in (1..districtsInfoMatrix[okatoIndex][154]) -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                        }
                        in (1..districtsInfoMatrix[okatoIndex][155]) -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..9).random())
                        }
                        else -> {
                            Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][121]), (10..14).random())
                        }
                    }
                }
                // Родитель 33 года, ребенок - 0-15 лет
                33 -> {
                    if (ageGroupRandomNum in 1..ageDistributionInDistrictsMatrix[16][biasedIndex]) {
                        // В группе 0-14 лет
                        when (ageRandomNum) {
                            in (1..districtsInfoMatrix[okatoIndex][154]) -> {
                                Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                            }
                            in (1..districtsInfoMatrix[okatoIndex][155]) -> {
                                Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..9).random())
                            }
                            else -> {
                                Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][121]), (10..14).random())
                            }
                        }
                    }
                    else {
                        // В группе 15-17 лет
                        return Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][123]), 15)
                    }
                }
                // Родитель 34 года, ребенок - 0-16 лет
                34 -> {
                    if (ageGroupRandomNum in 1..ageDistributionInDistrictsMatrix[16][biasedIndex]) {
                        // В группе 0-14 лет
                        when (ageRandomNum) {
                            in (1..districtsInfoMatrix[okatoIndex][154]) -> {
                                Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                            }
                            in (1..districtsInfoMatrix[okatoIndex][155]) -> {
                                Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..9).random())
                            }
                            else -> {
                                Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][121]), (10..14).random())
                            }
                        }
                    }
                    else {
                        // В группе 15-17 лет
                        return Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][123]), (15..16).random())
                    }
                }
                // Родитель 35+ лет, ребенок - 0-17 лет
                else -> {
                    if (ageGroupRandomNum in 1..ageDistributionInDistrictsMatrix[16][biasedIndex]) {
                        // В группе 0-14 лет
                        when (ageRandomNum) {
                            in (1..districtsInfoMatrix[okatoIndex][154]) -> {
                                Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][117]), (0..4).random())
                            }
                            in (1..districtsInfoMatrix[okatoIndex][155]) -> {
                                Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][119]), (5..9).random())
                            }
                            else -> {
                                Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][121]), (10..14).random())
                            }
                        }
                    }
                    else {
                        // В группе 15-17 лет
                        return Agent(sexRandomNum in (1..districtsInfoMatrix[okatoIndex][123]), (15..17).random())
                    }
                }
            }
        } else {
            // Взрослый
            // Случайное число для возрастной группы
            val ageGroupRandomNum = if (isOld != null) {
                if (isOld) {
                    // Взрослый в возрасте
                    // 45+
                    (ageDistributionInDistrictsMatrix[12][biasedIndex] + 1..100).random()
                } else {
                    // Более молодой взрослый
                    // 18-54
                    (1..ageDistributionInDistrictsMatrix[13][biasedIndex]).random()
                }
            } else {
                (1..100).random()
            }
            when (ageGroupRandomNum) {
                in 1..ageDistributionInDistrictsMatrix[10][biasedIndex] -> {
                    // 18-24 лет
                    return Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][125]), (18..24).random())
                }
                in ageDistributionInDistrictsMatrix[10][biasedIndex] + 1..ageDistributionInDistrictsMatrix[11][biasedIndex] -> {
                    // 25-34 лет
                    return if (ageRandomNum in (1..districtsInfoMatrix[okatoIndex][156])) {
                        Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][127]), (25..29).random())
                    } else {
                        Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][129]), (30..34).random())
                    }
                }
                in ageDistributionInDistrictsMatrix[11][biasedIndex] + 1..ageDistributionInDistrictsMatrix[12][biasedIndex] -> {
                    // 35-44 лет
                    return if (ageRandomNum in (1..districtsInfoMatrix[okatoIndex][157])) {
                        Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][131]), (35..39).random())
                    } else {
                        Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][133]), (40..44).random())
                    }
                }
                in ageDistributionInDistrictsMatrix[12][biasedIndex] + 1..ageDistributionInDistrictsMatrix[13][biasedIndex] -> {
                    // 45-54 лет
                    return if (ageRandomNum in (1..districtsInfoMatrix[okatoIndex][158])) {
                        Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][135]), (45..49).random())
                    } else {
                        Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][137]), (50..54).random())
                    }
                }
                in ageDistributionInDistrictsMatrix[13][biasedIndex] + 1..ageDistributionInDistrictsMatrix[14][biasedIndex] -> {
                    // 55-64 лет
                    return if (ageRandomNum in (1..districtsInfoMatrix[okatoIndex][159])) {
                        Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][139]), (55..59).random())
                    } else {
                        Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][141]), (60..64).random())
                    }
                }
                else -> {
                    // 65+ лет
                    return when (ageRandomNum) {
                        in (1..districtsInfoMatrix[okatoIndex][160]) -> {
                            Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][143]), (65..69).random())
                        }
                        in (1..districtsInfoMatrix[okatoIndex][161]) -> {
                            Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][145]), (70..74).random())
                        }
                        in (1..districtsInfoMatrix[okatoIndex][162]) -> {
                            Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][147]), (75..79).random())
                        }
                        in (1..districtsInfoMatrix[okatoIndex][163]) -> {
                            Agent(isMale ?: sexRandomNum in (1..districtsInfoMatrix[okatoIndex][149]), (80..84).random())
                        }
                        else -> {
                            Agent(isMale ?: sexRandomNum  in (1..districtsInfoMatrix[okatoIndex][151]), (85..89).random())
                        }
                    }
                }
            }
        }
    }

    // Добавление супруги
    private fun addSpouse(agentMale: Agent): Agent {
        // Разница в возрасте
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
        // Возраст супруги
        var spouseAge = agentMale.age + difference
        if (spouseAge < 18) {
            spouseAge = 18
        } else if (spouseAge > 80) {
            spouseAge = 80
        }
        return Agent(false, spouseAge)
    }

    // Добавление агентов в коллективы
    @Synchronized private fun addAgentsToGroups(agents: ArrayList<Agent>, household: Household) {
        agents.forEach { agent ->
            when (agent.activityStatus) {
                1 -> kindergarten.addAgent(agent)
                2 -> school.addAgent(agent)
                3 -> university.addAgent(agent)
                4 -> workplace.addAgent(agent)
            }
            // Добавление в домохозяйство
            household.addAgent(agent)
        }
        // Добавление нового домохозяйства в массив домохозяйств
        households.add(household)
    }

    // Задача создания домохозяйств с параллельным исполнением
    private suspend fun processAll(districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                                   ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>) = withContext(Dispatchers.IO) {
        // Счетчик прогресса создания популяции
        val counter = AtomicInteger()
        // Проходим по каждому району
        districtsInfoMatrix.forEachIndexed { index, it ->
            launch {
                println("Current index $index")

                val indexFor1People = index * 5 + 1
                val indexFor2People = index * 5 + 2
                val indexFor3People = index * 5 + 3
                val indexFor4People = index * 5 + 4
                val indexFor5People = index * 5 + 5

                // Создаем домохозяйства каждого типа
                for (i in 0..it[58]) {
                    val household = Household("1P")
                    val agent = createAgent(index, indexFor1People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, false)
                    addAgentsToGroups(arrayListOf(agent), household)
                }
                for (i in 0..it[59]) {
                    // Массив агентов в домохозяйстве
                    val agents = arrayListOf<Agent>()
                    // Домозяйство
                    val household = Household("PWOP2P0C")

                    // Взрослый мужчина
                    val agentMale = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    // Взрослая женщина
                    val agentFemale = addSpouse(agentMale)
                    // Добавляем их в массив агентов
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    // Добавляем агентов в коллективы и добавляем домохозяйство в массив домохозяйств
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[60] - it[61])) { // Общее число домохозяйств этого типа - число домохозяйств с одним ребенком
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP3P0C")

                    val agentMale = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agent)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[61]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("PWOP3P1C")

                    val agentMale = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agentFemale = addSpouse(agentMale)
                    // Создание ребенка
                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    // Если супруг не сидит дома
                    if (agentMale.activityStatus != 0) {
                        // У матери есть нужда в больничном по уходу за ребенком дома в случае его болезни
                        agentFemale.needMotherLeave = true
                        // Мать должна сидеть дома с ребенком раннего возраста
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
                    val household = Household("PWOP4P0C")

                    val agentMale = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
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
                    val household = Household("PWOP4P1C")

                    val agentMale = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                        (agent.activityStatus != 0)) {
                        agentFemale.needMotherLeave = true
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
                    val household = Household("PWOP4P2C")

                    val agentMale = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if (agentMale.activityStatus != 0) {
                        agentFemale.needMotherLeave = true
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
                    val household = Household("PWOP5P0C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
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
                    val household = Household("PWOP5P1C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                        (agent.activityStatus != 0)) {
                        agentFemale.needMotherLeave = true
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
                    val household = Household("PWOP5P2C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                        (agent.activityStatus != 0)) {
                        agentFemale.needMotherLeave = true
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
                    val household = Household("PWOP5P3C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if (agentMale.activityStatus != 0){
                        agentFemale.needMotherLeave = true
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
                    val household = Household("PWOP6P0C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
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
                    val household = Household("PWOP6P1C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true, isMale = null, parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                        (agent.activityStatus != 0) &&
                        (agent2.activityStatus != 0) &&
                        (agent3.activityStatus != 0)) {
                        agentFemale.needMotherLeave = true
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
                    val household = Household("PWOP6P2C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                        (agent.activityStatus != 0) &&
                        (agent2.activityStatus != 0)) {
                        agentFemale.needMotherLeave = true
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
                    val household = Household("PWOP6P3C")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, true, null, agentFemale.age)

                    if ((agentMale.activityStatus != 0) &&
                        (agent.activityStatus != 0)) {
                        agentFemale.needMotherLeave = true
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
                    val household = Household("2PWOP4P0С")

                    val agentMale = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale2 = addSpouse(agentMale2)
                    agents.add(agentMale)
                    agents.add(agentFemale)
                    agents.add(agentMale2)
                    agents.add(agentFemale2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[74] - it[75])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("2PWOP5P0С")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale2 = addSpouse(agentMale2)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
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
                    val household = Household("2PWOP5P1С")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale2 = addSpouse(agentMale2)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                        (agentMale2.activityStatus != 0) &&
                        (agentFemale2.activityStatus != 0)) {
                        agentFemale.needMotherLeave = true
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
                    val household = Household("2PWOP6P0С")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale2 = addSpouse(agentMale2)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
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
                    val household = Household("2PWOP6P1С")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = null
                    )
                    val agentFemale2 = addSpouse(agentMale2)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                        (agentMale2.activityStatus != 0) &&
                        (agentFemale2.activityStatus != 0) &&
                        (agent.activityStatus != 0)) {
                        agentFemale.needMotherLeave = true
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
                    val household = Household("2PWOP6P2С")

                    val agentMale = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agentFemale = addSpouse(agentMale)
                    val agentMale2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agentFemale2 = addSpouse(agentMale2)
                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale.age
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agentFemale2.age
                    )

                    if ((agentMale.activityStatus != 0) &&
                        (agentMale2.activityStatus != 0) &&
                        (agentFemale2.activityStatus != 0)) {
                        agentFemale.needMotherLeave = true
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
                    val household = Household("SMWC2P0C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isOld = true
                    )
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
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
                    val household = Household("SMWC2P1C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    agent.needMotherLeave = true
                    if ((agent2.age <= 2) && (agent2.activityStatus == 0)) {
                        agent.activityStatus = 0
                    }

                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..(it[81] - it[82] - it[83])) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SMWC3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isOld = true
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
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
                    val household = Household("SMWC3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isOld = true
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.needMotherLeave = true
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
                    val household = Household("SMWC3P2C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    agent.needMotherLeave = true
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
                    val household = Household("SMWC4P0C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isOld = true
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    if (agent.age - agent3.age < 18) {
                        agent3.age = agent.age - 18
                    }
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
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
                    val household = Household("SMWC4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isOld = true
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    if (agent.age - agent3.age < 18) {
                        agent3.age = agent.age - 18
                    }
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if ((agent2.activityStatus != 0) &&
                        (agent3.activityStatus != 0)) {
                        agent.needMotherLeave = true
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
                    val household = Household("SMWC4P2C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isOld = true
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.needMotherLeave = true
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
                    val household = Household("SMWC4P3C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = false,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    agent.needMotherLeave = true
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
                    val household = Household("SFWC2P0C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = true
                    )
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
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
                    val household = Household("SFWC2P1C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    agent.needMotherLeave = true
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
                    val household = Household("SFWC3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = true
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
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
                    val household = Household("SFWC3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = true
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    if (agent.age - agent2.age < 18) {
                        agent2.age = agent.age - 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.needMotherLeave = true
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
                    val household = Household("SFWC3P2C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    agent.needMotherLeave = true
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
                    val household = Household("SPWCWP3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[94]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.needMotherLeave = true
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
                    val household = Household("SPWCWP4P0C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[96]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWP4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if ((agent2.activityStatus != 0) &&
                        (agent3.activityStatus != 0)) {
                        agent.needMotherLeave = true
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
                    val household = Household("SPWCWP4P2C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.needMotherLeave = true
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
                    val household = Household("SPWCWPWOP3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[99]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.needMotherLeave = true
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
                    val household = Household("SPWCWPWOP4P0C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    agents.add(agent4)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[101]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("SPWCWPWOP4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if ((agent2.activityStatus != 0) &&
                        (agent3.activityStatus != 0)) {
                        agent.needMotherLeave = true
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
                    val household = Household("SPWCWPWOP4P2C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    if (agent2.age - agent.age < 18) {
                        agent2.age = agent.age + 18
                    }
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if (agent2.activityStatus != 0) {
                        agent.needMotherLeave = true
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
                    val household = Household("SPWCWPWOP5P0C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
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
                    val household = Household("SPWCWPWOP5P1C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = true
                    )
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if ((agent2.activityStatus != 0) &&
                        (agent3.activityStatus != 0) &&
                        (agent4.activityStatus != 0)) {
                        agent.needMotherLeave = true
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
                    val household = Household("SPWCWPWOP5P2C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = true,
                        parentAge = null,
                        isOld = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = false,
                        isMale = null,
                        parentAge = null,
                        isOld = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix,
                        isChild = true,
                        isMale = null,
                        parentAge = agent.age
                    )

                    if ((agent2.activityStatus != 0) &&
                        (agent3.activityStatus != 0)) {
                        agent.needMotherLeave = true
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
                    val household = Household("O2P0C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[107]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O2P1C")

                    val agent = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor2People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = true,
                        isMale = null, parentAge = (18..35).random()
                    )

                    agent.needMotherLeave = true
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
                    val household = Household("O3P0C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    agents.add(agent)
                    agents.add(agent2)
                    agents.add(agent3)
                    addAgentsToGroups(agents, household)
                }
                for (i in 0..it[109]) {
                    val agents = arrayListOf<Agent>()
                    val household = Household("O3P1C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = true,
                        isMale = null, parentAge = (18..35).random()
                    )

                    if (agent2.activityStatus != 0) {
                        agent.needMotherLeave = true
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
                    val household = Household("O3P2C")

                    val agent = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = true,
                        isMale = null, parentAge = (18..35).random()
                    )
                    val agent3 = createAgent(index, indexFor3People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = true,
                        isMale = null, parentAge = (18..35).random()
                    )

                    agent.needMotherLeave = true
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
                    val household = Household("O4P0C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
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
                    val household = Household("O4P1C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = true,
                        isMale = null, parentAge = (18..35).random()
                    )

                    if ((agent2.activityStatus != 0) &&
                        (agent3.activityStatus != 0)) {
                        agent.needMotherLeave = true
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
                    val household = Household("O4P2C")

                    val agent = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = true,
                        isMale = null, parentAge = (18..35).random()
                    )
                    val agent4 = createAgent(index, indexFor4People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = true,
                        isMale = null, parentAge = (18..35).random()
                    )

                    if (agent2.activityStatus != 0) {
                        agent.needMotherLeave = true
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
                    val household = Household("O5P0C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
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
                    val household = Household("O5P1C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = true,
                        isMale = null, parentAge = (18..35).random()
                    )

                    if ((agent2.activityStatus != 0) &&
                        (agent3.activityStatus != 0) &&
                        (agent4.activityStatus != 0)) {
                        agent.needMotherLeave = true
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
                    val household = Household("O5P2C")

                    val agent = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent2 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent3 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = false
                    )
                    val agent4 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = true,
                        isMale = null, parentAge = (18..35).random()
                    )
                    val agent5 = createAgent(index, indexFor5People, districtsInfoMatrix,
                        ageDistributionInDistrictsMatrix, isChild = true,
                        isMale = null, parentAge = (18..35).random()
                    )

                    if ((agent2.activityStatus != 0) &&
                        (agent3.activityStatus != 0)) {
                        agent.needMotherLeave = true
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
                progress.set(counter.incrementAndGet() / 106.0)
            }
        }
    }

    // Инициализация
    private fun addHouseholdsToPool(districtsInfoMatrix: ArrayList<ArrayList<Int>>,
                                    ageDistributionInDistrictsMatrix: ArrayList<ArrayList<Int>>) {
        runBlocking {
            processAll(districtsInfoMatrix, ageDistributionInDistrictsMatrix)
        }
        println("Households created")
        // Создание графов в оставшихся компаниях
        workplace.generateLastBarabasiAlbertNetworks()
        println("World creation has ended")
    }

    // Контакты внутри домохозяйств
    private fun makeContactsInHouseholdForAgent(isContactOnHoliday: Boolean, household: Household,
                                            agent: Agent, isKindergartenHoliday: Boolean = false,
                                            isSchoolHoliday: Boolean = false, isWorkHoliday: Boolean = false,
                                            isUniversityHoliday: Boolean = false) {
        // agent инфицирован, agent2 восприимчив
        household.agents.forEach { agent2 ->
            // Наличие типоспецифического иммунитета к инфекции больного
            val agent2IsImmune = when (agent.infectionType) {
                "fluA" -> agent2.hasImmunityFluA
                "fluB" -> agent2.hasImmunityFluB
                "RV" -> agent2.hasImmunityRV
                "RSV" -> agent2.hasImmunityRSV
                "AdV" -> agent2.hasImmunityAdV
                "PIV" -> agent2.hasImmunityPIV
                "CoV" -> agent2.hasImmunityCoV
                else -> error("Wrong type")
            }
            // Если восприимчив и отсутствует типоспецифический иммунитет
            if ((agent2.healthStatus == 0) && (!agent2IsImmune)) {
                // Случайное число
                val randomNumber = (0..9999).random() * 0.0001
                // Влияние продолжительности контакта на вероятность инфицирования
                val contactDurationInfluence = if (isContactOnHoliday) {
                    1 / (1 + exp(-getHouseholdContactDuration() + durationInfluenceParameter))
                } else {
                    if ((((agent.activityStatus == 1) && (!agent.isStayingHomeWhenInfected)) ||
                                (agent2.activityStatus == 1)) && (!isKindergartenHoliday)) {
                        // Один из них посещал детский сад
                        1 / (1 + exp(-getHouseholdContactDurationWithKindergarten() + durationInfluenceParameter))
                    } else if ((((agent.activityStatus == 4) && (!agent.isStayingHomeWhenInfected)) ||
                                (agent2.activityStatus == 4)) && (!isWorkHoliday)) {
                        // Один из них посещал работу
                        1 / (1 + exp(-getHouseholdContactDurationWithWork() + durationInfluenceParameter))
                    } else if ((((agent.activityStatus == 2) && (!agent.isStayingHomeWhenInfected)) ||
                                (agent2.activityStatus == 2)) && (!isSchoolHoliday)) {
                        // Один из них посещал школу
                        1 / (1 + exp(-getHouseholdContactDurationWithSchool() + durationInfluenceParameter))
                    } else if ((((agent.activityStatus == 3) && (!agent.isStayingHomeWhenInfected)) ||
                                (agent2.activityStatus == 3)) && (!isUniversityHoliday)) {
                        // Один из них посещал университет
                        1 / (1 + exp(-getHouseholdContactDurationWithUniversity() + durationInfluenceParameter))
                    } else {
                        // Оба сидят дома
                        1 / (1 + exp(-getHouseholdContactDuration() + durationInfluenceParameter))
                    }
                }
                // Нормализованная температура воздуха на текущем шаге
                val currentTemperature = (temp[dayOfTheYear] - minTemp) / maxMinTemp
                // Влияние температуры воздуха на вероятность инфицирования
                val temperatureInfluence = (temperatureParameterMap[agent.infectionType] ?: error("Required")) * currentTemperature + 1.0
                // Влияние восприимчивости на вероятность инфицирования
                val susceptibilityInfluence = when (agent.infectionType) {
                    "fluA" -> agent2.susceptibilityInfluenceFluA
                    "fluB" -> agent2.susceptibilityInfluenceFluB
                    "RV" -> agent2.susceptibilityInfluenceRV
                    "RSV" -> agent2.susceptibilityInfluenceRSV
                    "AdV" -> agent2.susceptibilityInfluenceAdV
                    "PIV" -> agent2.susceptibilityInfluencePIV
                    "CoV" -> agent2.susceptibilityInfluenceCoV
                    else -> error("Wrong type")
                }
                // Вероятность инфицирования
                val infectionProbability = agent.infectivityInfluence * susceptibilityInfluence *
                        temperatureInfluence * contactDurationInfluence
                if (randomNumber < infectionProbability) {
                    // Успешно инфицирован
                    agent2.healthStatus = 3
                    agent2.infectionType = agent.infectionType
                }
            }
        }
    }

    // Контакты в детских садах, школах, университетах
    private fun makeContactsInGroupForAgent(group: Group, agent: Agent) {
        // agent инфицирован, agent2 восприимчив
        group.agents.forEach { agent2 ->
            // Наличие типоспецифического иммунитета к инфекции больного
            val agent2IsImmune = when (agent.infectionType) {
                "fluA" -> agent2.hasImmunityFluA
                "fluB" -> agent2.hasImmunityFluB
                "RV" -> agent2.hasImmunityRV
                "RSV" -> agent2.hasImmunityRSV
                "AdV" -> agent2.hasImmunityAdV
                "PIV" -> agent2.hasImmunityPIV
                "CoV" -> agent2.hasImmunityCoV
                else -> error("Wrong type")
            }
            // Если восприимчив и отсутствует типоспецифический иммунитет и не на больничном по уходу за ребенком
            if ((agent2.healthStatus == 0) && (!agent2IsImmune) && (!agent2.isOnMotherLeave)) {
                // Случайное число
                val randomNumber = (0..9999).random() * 0.0001
                // Влияние продолжительности контакта на вероятность инфицирования
                val contactDurationInfluence = when (agent.activityStatus) {
                    // Детский сад
                    1 -> 1 / (1 + exp(
                        -getKindergartenContactDuration() + durationInfluenceParameter))
                    // Школа
                    2 -> 1 / (1 + exp(
                        -getSchoolContactDuration() + durationInfluenceParameter))
                    // Университет
                    3 -> 1 / (1 + exp(
                        -getUniversityContactDuration() + durationInfluenceParameter))
                    else -> error("Wrong")
                }
                // Нормализованная температура воздуха на текущем шаге
                val currentTemperature = (temp[dayOfTheYear] - minTemp) / maxMinTemp
                // Влияние температуры воздуха на вероятность инфицирования
                val temperatureInfluence = (temperatureParameterMap[agent.infectionType] ?: error("Required")) * currentTemperature + 1.0
                // Влияние восприимчивости на вероятность инфицирования
                val susceptibilityInfluence = when (agent.infectionType) {
                    "fluA" -> agent2.susceptibilityInfluenceFluA
                    "fluB" -> agent2.susceptibilityInfluenceFluB
                    "RV" -> agent2.susceptibilityInfluenceRV
                    "RSV" -> agent2.susceptibilityInfluenceRSV
                    "AdV" -> agent2.susceptibilityInfluenceAdV
                    "PIV" -> agent2.susceptibilityInfluencePIV
                    "CoV" -> agent2.susceptibilityInfluenceCoV
                    else -> error("Wrong type")
                }
                // Вероятность инфицирования
                val infectionProbability = agent.infectivityInfluence * susceptibilityInfluence *
                        temperatureInfluence * contactDurationInfluence
                if (randomNumber < infectionProbability) {
                    // Успешно инфицирован
                    agent2.healthStatus = 3
                    agent2.infectionType = agent.infectionType
                }
            }
        }
    }

    // Контакты внутри рабочих коллективов
    private fun makeContactsInGroupForAgentAtWork(group: Group, agent: Agent) {
        // agent инфицирован, agent2 восприимчив
        group.agents.forEachIndexed { index, agent2 ->
            // Наличие типоспецифического иммунитета к инфекции больного
            val agent2IsImmune = when (agent.infectionType) {
                "fluA" -> agent2.hasImmunityFluA
                "fluB" -> agent2.hasImmunityFluB
                "RV" -> agent2.hasImmunityRV
                "RSV" -> agent2.hasImmunityRSV
                "AdV" -> agent2.hasImmunityAdV
                "PIV" -> agent2.hasImmunityPIV
                "CoV" -> agent2.hasImmunityCoV
                else -> error("Wrong type")
            }
            // Если восприимчив и отсутствует типоспецифический иммунитет и не на больничном по уходу за ребенком и имеет связь
            if ((agent2.healthStatus == 0) && (!agent2IsImmune) &&
                (index in agent.connectedWorkAgents) && (!agent2.isOnMotherLeave)) {
                // Случайное число
                val randomNumber = (0..9999).random() * 0.0001
                // Влияние продолжительности контакта
                val contactDurationInfluence = 1 / (1 + exp(
                    -getWorkplaceContactDuration() + durationInfluenceParameter))
                // Нормализованная температура воздуха на текущем шаге
                val currentTemperature = (temp[dayOfTheYear] - minTemp) / maxMinTemp
                // Влияние температуры воздуха на вероятность инфицирования
                val temperatureInfluence = (temperatureParameterMap[agent.infectionType] ?: error("Required")) * currentTemperature + 1.0
                // Влияние восприимчивости на вероятность инфицирования
                val susceptibilityInfluence = when (agent.infectionType) {
                    "fluA" -> agent2.susceptibilityInfluenceFluA
                    "fluB" -> agent2.susceptibilityInfluenceFluB
                    "RV" -> agent2.susceptibilityInfluenceRV
                    "RSV" -> agent2.susceptibilityInfluenceRSV
                    "AdV" -> agent2.susceptibilityInfluenceAdV
                    "PIV" -> agent2.susceptibilityInfluencePIV
                    "CoV" -> agent2.susceptibilityInfluenceCoV
                    else -> error("Wrong type")
                }
                // Вероятность инфицирования
                val infectionProbability = agent.infectivityInfluence * susceptibilityInfluence *
                        temperatureInfluence * contactDurationInfluence
                if (randomNumber < infectionProbability) {
                    // Успешно инфицирован
                    agent2.healthStatus = 3
                    agent2.infectionType = agent.infectionType
                }
            }
        }
    }

    // Выбор инфекции при случайном инфицировании
    private fun infectRandomly(agent: Agent) {
        val randomNum = (0..999).random() * 0.001
        if (randomNum < etiologiesRatio[globalWeek][0]) {
            // 60% грипп A
            if ((0..9).random() < 6) {
                if (!agent.hasImmunityFluA) {
                    agent.healthStatus = 3
                    agent.infectionType = "fluA"
                }
            } else {
                // 40% грипп B
                if (!agent.hasImmunityFluB) {
                    agent.healthStatus = 3
                    agent.infectionType = "fluB"
                }
            }
        } else if ((randomNum >= etiologiesRatio[globalWeek][0]) && (randomNum < etiologiesRatio[globalWeek][1])) {
            if (!agent.hasImmunityRV) {
                agent.healthStatus = 3
                agent.infectionType = "RV"
            }
        } else if ((randomNum >= etiologiesRatio[globalWeek][1]) && (randomNum < etiologiesRatio[globalWeek][2])) {
            if (!agent.hasImmunityRSV) {
                agent.healthStatus = 3
                agent.infectionType = "RSV"
            }
        } else if ((randomNum >= etiologiesRatio[globalWeek][2]) && (randomNum < etiologiesRatio[globalWeek][3])) {
            if (!agent.hasImmunityAdV) {
                agent.healthStatus = 3
                agent.infectionType = "AdV"
            }
        } else if ((randomNum >= etiologiesRatio[globalWeek][3]) && (randomNum < etiologiesRatio[globalWeek][4])) {
            if (!agent.hasImmunityPIV) {
                agent.healthStatus = 3
                agent.infectionType = "PIV"
            }
        } else  {
            if (!agent.hasImmunityCoV) {
                agent.healthStatus = 3
                agent.infectionType = "CoV"
            }
        }
    }

    fun runSimulation(numOfIter: Int, series1: XYChart.Series<String, Number>, series2: XYChart.Series<String, Number>,
                      series3: XYChart.Series<String, Number>, series4: XYChart.Series<String, Number>,
                      series1Real: XYChart.Series<String, Number>, series2Real: XYChart.Series<String, Number>,
                      series3Real: XYChart.Series<String, Number>, series4Real: XYChart.Series<String, Number>,
                      dateLabelText: SimpleStringProperty
    ): ArrayList<ArrayList<Int>> {

        // Число новых случаев, число выявленных новых случаев
        val newCasesDayStats = arrayListOf(0, 0)
        // Заболеваемость в возрастных группах 0-2, 3-6, 7-14, 15+ по дням
        val ageGroupsDayStats = arrayListOf(0, 0, 0, 0)
        // Заболеваемость в возрастных группах 0-2, 3-6, 7-14, 15+ по неделям
        val ageGroupsWeekStats = arrayListOf(arrayListOf(0, 0, 0, 0))
        // Заболеваемость различными инфекциями по дням
        val etiologyDayStats = arrayListOf(0, 0, 0, 0, 0, 0, 0)

        while(true) {

            // Обнуляем полученную ежедневную заболеваемость
            newCasesDayStats[0] = 0
            newCasesDayStats[1] = 0

            ageGroupsDayStats[0] = 0
            ageGroupsDayStats[1] = 0
            ageGroupsDayStats[2] = 0
            ageGroupsDayStats[3] = 0

            etiologyDayStats[0] = 0
            etiologyDayStats[1] = 0
            etiologyDayStats[2] = 0
            etiologyDayStats[3] = 0
            etiologyDayStats[4] = 0
            etiologyDayStats[5] = 0
            etiologyDayStats[6] = 0

            // Выходные, праздники, каникулы
            var isHoliday = false
            if (dayOfTheWeek == 7) {
                isHoliday = true
            }
            if ((month == 1) && (day in arrayListOf(1, 2, 3, 7))) {
                isHoliday = true
            }
            if ((month == 5) && (day in arrayListOf(1, 9))) {
                isHoliday = true
            }
            if ((month == 2) && (day == 23)) {
                isHoliday = true
            }
            if ((month == 3) && (day == 8)) {
                isHoliday = true
            }
            if ((month == 6) && (day == 12)) {
                isHoliday = true
            }

            var isWorkHoliday = false
            if (dayOfTheWeek == 6) {
                isWorkHoliday = true
            }

            var isKindergartenHoliday = isWorkHoliday
            if (month in arrayListOf(7, 8)) {
                isKindergartenHoliday = true
            }

            // Каникулы
            // Осенние - 05.11.2007 - 11.11.2007 - 7 дней;
            // Зимние - 26.12.2007 - 09.01.2008 - 15 дней;
            // Весенние - 22.03.2008 - 31.03.2008 - 10 дней.
            // Зимние - 28.12.2007 - 09.03.2008
            var isSchoolHoliday = false
            if (month in arrayListOf(6, 7, 8)) {
                isSchoolHoliday = true
            }
            if ((month == 11) && (day in arrayListOf(5, 6, 7, 8, 9, 10, 11))) {
                isSchoolHoliday = true
            }
            if ((month == 12) && (day in arrayListOf(28, 29, 30, 31))) {
                isSchoolHoliday = true
            }
            if ((month == 1) && (day in arrayListOf(6, 7, 8, 9))) {
                isSchoolHoliday = true
            }
            if ((month == 3) && (day in arrayListOf(22, 23, 24, 25, 26, 27, 28, 29, 30, 31))) {
                isSchoolHoliday = true
            }

            var isUniversityHoliday = false
            if ((month == 12) && (day in arrayListOf(22, 23, 24, 25, 26, 27, 28, 29, 30, 31))) {
                isUniversityHoliday = true
            }
            if (month == 1) {
                if (day !in arrayListOf(11, 15, 19, 23, 27)) {
                    isUniversityHoliday = true
                }
            }
            if (month == 6) {
                if (day !in arrayListOf(11, 15, 19, 23, 27)) {
                    isUniversityHoliday = true
                }
            }
            if (month in arrayListOf(7, 8)) {
                isUniversityHoliday = true
            }
            if ((month == 2) && (day in arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))) {
                isUniversityHoliday = true
            }

            // Для каждого агента находим влияние силы инфекции на вероятность инфицирования на текущем шаге
            households.parallelStream().forEach { household ->
                household.agents.forEach { agent ->
                    agent.findInfectivityInfluence()
                }
            }
            // Выходной во всех коллективах
            // Полные контакты в домохозяйствах
            if (((isUniversityHoliday) && (isSchoolHoliday) && (isKindergartenHoliday) && (isWorkHoliday)) || (isHoliday)) {
                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if (agent.healthStatus == 1) {
                            makeContactsInHouseholdForAgent(true, household, agent)
                        }
                    }
                }
            } else {
                // Контакты в детских садах
                if (!isKindergartenHoliday) {
                    kindergarten.groupsByAge.parallelStream().forEach { groupByAge ->
                        groupByAge.forEach { group ->
                            group.agents.forEach { agent ->
                                if ((agent.healthStatus == 1) &&
                                        (!agent.isStayingHomeWhenInfected)) {
                                    makeContactsInGroupForAgent(group, agent)
                                }
                            }
                        }
                    }
                }
                // Контакты в рабочих коллективах
                if (!isWorkHoliday) {
                    workplace.workingGroups.parallelStream().forEach { group ->
                        group.agents.forEach { agent ->
                            if ((agent.healthStatus == 1) &&
                                    (!agent.isStayingHomeWhenInfected) && (!agent.isOnMotherLeave)) {
                                makeContactsInGroupForAgentAtWork(group, agent)
                            }
                        }
                    }
                }
                // Контакты в школах
                if (!isSchoolHoliday) {
                    school.groupsByAge.forEachIndexed { index, groupByAge ->
                        if ((index == 0) && (isWorkHoliday)) {
                            // 1-й класс отдыхает по субботам
                        } else {
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if ((agent.healthStatus == 1) &&
                                            (!agent.isStayingHomeWhenInfected)) {
                                        makeContactsInGroupForAgent(group, agent)
                                    }
                                }
                            }
                        }
                    }
                }
                // Контакты в университетах
                if (!isUniversityHoliday) {
                    university.groupsByAge.parallelStream().forEach { groupByAge ->
                        groupByAge.forEach { group ->
                            group.agents.forEach { agent ->
                                if ((agent.healthStatus == 1) &&
                                        (!agent.isStayingHomeWhenInfected) && (!agent.isOnMotherLeave)) {
                                    makeContactsInGroupForAgent(group, agent)
                                }
                            }
                        }
                    }
                }
                // Контакты в домохозяйствах
                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if (agent.healthStatus == 1) {
                            makeContactsInHouseholdForAgent(false, household, agent,
                                isKindergartenHoliday, isSchoolHoliday, isWorkHoliday, isUniversityHoliday)
                        }
                    }
                }
            }
            // Меняем свойства агентов
            households.forEach { household ->
                household.agents.forEach { agent ->
                    when (agent.healthStatus) {
                        3 -> {
                            // Переход в инфицированное состояние
                            agent.healthStatus = 1
                            newCasesDayStats[0] += 1
                            agent.updateHealthParameters()
                        }
                        1 -> {
                            if (agent.daysInfected == agent.infectionPeriod) {
                                // Переход в резистентное состояние и приобретение типоспецифического иммунитета
                                when (agent.infectionType) {
                                    "fluA" -> agent.hasImmunityFluA = true
                                    "fluB" -> agent.hasImmunityFluB = true
                                    "RV" -> agent.hasImmunityRV = true
                                    "RSV" -> agent.hasImmunityRSV = true
                                    "AdV" -> agent.hasImmunityAdV = true
                                    "PIV" -> agent.hasImmunityPIV = true
                                    "CoV" -> agent.hasImmunityCoV = true
                                }
                                agent.healthStatus = 2
                                agent.daysImmune = 1
                                // Конец больничного по уходу за ребенком
                                if ((agent.age < 14) && (agent.isStayingHomeWhenInfected)) {
                                    for (otherAgent in household.agents) {
                                        if (otherAgent.isOnMotherLeave) {
                                            otherAgent.isOnMotherLeave = false
                                            break
                                        }
                                    }
                                }
                            } else {
                                // Прибавляем 1 ко дню с момента инфицирования
                                agent.daysInfected += 1
                                if (!agent.isStayingHomeWhenInfected) {
                                    // Самоизоляция
                                    agent.isStayingHomeWhenInfected = agent.findIfShouldStayAtHome()
                                    if (agent.isStayingHomeWhenInfected) {
                                        // Выявление
                                        newCasesDayStats[1] += 1
                                        when (agent.age) {
                                            in (0..2) -> {
                                                ageGroupsWeekStats[ageGroupsWeekStats.size - 1][0] += 1
                                                ageGroupsDayStats[0] += 1
                                            }
                                            in (3..6) -> {
                                                ageGroupsWeekStats[ageGroupsWeekStats.size - 1][1] += 1
                                                ageGroupsDayStats[1] += 1
                                            }
                                            in (7..14) -> {
                                                ageGroupsWeekStats[ageGroupsWeekStats.size - 1][2] += 1
                                                ageGroupsDayStats[2] += 1
                                            }
                                            else -> {
                                                ageGroupsWeekStats[ageGroupsWeekStats.size - 1][3] += 1
                                                ageGroupsDayStats[3] += 1
                                            }
                                        }

                                        when (agent.infectionType) {
                                            "fluA" -> etiologyDayStats[0] += 1
                                            "fluB" -> etiologyDayStats[1] += 1
                                            "RV" -> etiologyDayStats[2] += 1
                                            "RSV" -> etiologyDayStats[3] += 1
                                            "AdV" -> etiologyDayStats[4] += 1
                                            "PIV" -> etiologyDayStats[5] += 1
                                            "CoV" -> etiologyDayStats[6] += 1
                                        }

                                        // Больничный по уходу за ребенком
                                        if (agent.age < 14) {
                                            for (otherAgent in household.agents) {
                                                if (otherAgent.needMotherLeave) {
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
                                // Переход из резистентного состояния в восприимчивое
                                agent.healthStatus = 0
                            } else {
                                agent.daysImmune += 1
                            }
                        }
                        // Случайное инфицирование
                        0 -> {
                            if (agent.age < 16) {
                                // Дети
                                if ((0..9999).random() < 2) {
                                    infectRandomly(agent)
                                }
                            } else {
                                // Взрослые
                                if ((0..9999).random() == 0) {
                                    infectRandomly(agent)
                                }
                            }
                        }
                    }

                    // Обновляем типоспецифический иммунитет
                    if (agent.hasImmunityRV) {
                        if (agent.immunityRVDays == immunityMap["RV"]) {
                            // Переход в состояние восприимчивости к данной инфекции
                            agent.immunityRVDays = 0
                            agent.hasImmunityRV = false
                        } else {
                            agent.immunityRVDays += 1
                        }
                    }
                    if (agent.hasImmunityRSV) {
                        if (agent.immunityRSVDays == immunityMap["RSV"]) {
                            agent.immunityRSVDays = 0
                            agent.hasImmunityRSV = false
                        } else {
                            agent.immunityRSVDays += 1
                        }
                    }
                    if (agent.hasImmunityAdV) {
                        if (agent.immunityAdVDays == immunityMap["AdV"]) {
                            agent.immunityAdVDays = 0
                            agent.hasImmunityAdV = false
                        } else {
                            agent.immunityAdVDays += 1
                        }
                    }
                    if (agent.hasImmunityPIV) {
                        if (agent.immunityPIVDays == immunityMap["PIV"]) {
                            agent.immunityPIVDays = 0
                            agent.hasImmunityPIV = false
                        } else {
                            agent.immunityPIVDays += 1
                        }
                    }
                    if (agent.hasImmunityCoV) {
                        if (agent.immunityCoVDays == immunityMap["CoV"]) {
                            agent.immunityCoVDays = 0
                            agent.hasImmunityCoV = false
                        } else {
                            agent.immunityCoVDays += 1
                        }
                    }
                }
            }

            // Запись в таблицы результатов на текущем шаге
            writeTableInt("src\\output\\results${numOfIter}.xlsx", globalDay, newCasesDayStats)

            writeTableInt("src\\output\\resultsByAge${numOfIter}.xlsx", globalDay, ageGroupsDayStats)

            writeTableInt("src\\output\\resultsByEtiology${numOfIter}.xlsx", globalDay, etiologyDayStats)

            // Меняем день
            day++
            // Условие остановки
            if ((month == 7) && (day == 32)) {
                break
            }

            // Меняем день недели
            dayOfTheWeek++
            if (dayOfTheWeek == 8) {

                // Название месяца для UI
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
                // Обновление UI
                Platform.runLater {
                    series1.data.add(XYChart.Data<String, Number>("$day $monthName", ageGroupsWeekStats[ageGroupsWeekStats.size - 1][0] / 9863.0))
                    series2.data.add(XYChart.Data<String, Number>("$day $monthName", ageGroupsWeekStats[ageGroupsWeekStats.size - 1][1] / 9863.0))
                    series3.data.add(XYChart.Data<String, Number>("$day $monthName", ageGroupsWeekStats[ageGroupsWeekStats.size - 1][2] / 9863.0))
                    series4.data.add(XYChart.Data<String, Number>("$day $monthName", ageGroupsWeekStats[ageGroupsWeekStats.size - 1][3] / 9863.0))

                    series1Real.data.add(XYChart.Data<String, Number>("$day $monthName", realData[globalWeek][0]))
                    series2Real.data.add(XYChart.Data<String, Number>("$day $monthName", realData[globalWeek][1]))
                    series3Real.data.add(XYChart.Data<String, Number>("$day $monthName", realData[globalWeek][2]))
                    series4Real.data.add(XYChart.Data<String, Number>("$day $monthName", realData[globalWeek][3]))

                    dateLabelText.set("$day $monthName")
                }

                // Время на обновление UI
                sleep(100)

                dayOfTheWeek = 1
                globalWeek++
                if (globalWeek == 52) {
                    globalWeek = 0
                }

                // Добавление новой недели
                ageGroupsWeekStats.add(arrayListOf(0, 0, 0, 0))
            }

            // Меняем шаг модели
            globalDay++
            dayOfTheYear++
            if (dayOfTheYear == 366) {
                dayOfTheYear = 0
            }
            if (dayOfTheYear == 59) {
                dayOfTheYear = 60
            }

            // Меняем месяц
            if ((month in arrayListOf(1, 3, 5, 7, 8, 10) && (day == 32)) ||
                    (month in arrayListOf(4, 6, 9, 11) && (day == 31)) ||
                    (month == 2) and (day == 29)) {
                day = 1
                month++
                println("Month $month")
            } else if ((month == 12) && (day == 32)) {
                day = 1
                month = 1
                println("Month 1")
            }
        }
        // Возвращаем полученную заболеваемость в 4-х возрастных группах по неделям
        return ageGroupsWeekStats
    }

    init {
        // Данные по удельному весу различных инфекций по неделям
        readTableDouble("src\\tables\\etiologies_cum.xlsx", 51, 5, etiologiesRatio)

        // Данные по распределению возрастов по районам
        val ageDistributionInDistrictsMatrix = arrayListOf<ArrayList<Int>>()
        readTableInt("src\\tables\\age-num-of-people-districts.xlsx", 18, 535, ageDistributionInDistrictsMatrix)

        // Данные по районам
        val districtsInfoMatrix = arrayListOf<ArrayList<Int>>()
        readTableInt("src\\tables\\districts.xlsx", 107, 163, districtsInfoMatrix)

        // Данные по заболеваемости
        readTableDouble("src\\tables\\stats.xlsx", 51, 3, realData)

        // Создание популяции
        addHouseholdsToPool(districtsInfoMatrix, ageDistributionInDistrictsMatrix)
    }

    // Подготовка к новому прогону
    fun restartWorld() {
        // Обновление даты
        month = 8
        day = 1
        dayOfTheYear = 211
        globalDay = 0
        dayOfTheWeek = 1

        // Обновление свойств агентов
        households.parallelStream().forEach { household ->
            household.agents.forEach { agent ->
                agent.hasImmunityFluA = false
                agent.hasImmunityFluB = false
                agent.hasImmunityRV = false
                agent.hasImmunityRSV = false
                agent.hasImmunityAdV = false
                agent.hasImmunityPIV = false
                agent.hasImmunityCoV = false

                agent.immunityRVDays = 0
                agent.immunityRSVDays = 0
                agent.immunityPIVDays = 0
                agent.immunityAdVDays = 0
                agent.immunityCoVDays = 0

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

                agent.isAsymptomatic = agent.findIfWillBeAsymptomatic()
                agent.incubationPeriod = agent.findIncubationPeriod()
                agent.infectionPeriod = agent.findInfectionPeriod()
                agent.daysInfected = if (agent.healthStatus == 1) ((1 - agent.incubationPeriod)..agent.infectionPeriod).random() else 0
                agent.isStayingHomeWhenInfected = agent.findIfShouldStayAtHome()
                agent.meanViralLoad = agent.findMeanViralLoad()
            }
        }
    }

    // Вычисление R0 (тип инфекции, месяц, день)
    fun runR0(infection: String, m: Int, d: Int): Int {
        // Устанавливаем дату
        month = m
        day = d
        // Если 5-й день месяца
        dayOfTheYear = when (month) {
            1 -> 4
            2 -> 35
            3 -> 63
            4 -> 93
            5 -> 123
            6 -> 154
            7 -> 184
            8 -> 215
            9 -> 246
            10 -> 276
            11 -> 308
            12 -> 338
            else -> error("Wrong month")
        }
        // Считаем, что понедельник
        dayOfTheWeek = 1
        // Число вторичных случаев
        var numOfInfected = 0
        // Делаем всех агентов восприимчивыми
        households.parallelStream().forEach { household ->
            household.agents.forEach { agent ->

                agent.hasImmunityFluA = false
                agent.hasImmunityFluB = false
                agent.hasImmunityRV = false
                agent.hasImmunityRSV = false
                agent.hasImmunityAdV = false
                agent.hasImmunityPIV = false
                agent.hasImmunityCoV = false

                agent.immunityRVDays = 0
                agent.immunityRSVDays = 0
                agent.immunityPIVDays = 0
                agent.immunityAdVDays = 0
                agent.immunityCoVDays = 0

                agent.isOnMotherLeave = false

                agent.healthStatus = 0
                agent.isAsymptomatic = false
                agent.incubationPeriod = 0
                agent.infectionPeriod = 0
                agent.daysInfected = 0
                agent.isStayingHomeWhenInfected = false
                agent.meanViralLoad = 0.0
            }
        }
        // Инфицируем случайного агента в случайном домохозяйстве
        val foundHousehold = households[(0 until households.size).random()]
        val foundAgent = foundHousehold.agents[(0 until foundHousehold.agents.size).random()]
        foundAgent.infectionType = infection
        foundAgent.healthStatus = 1
        foundAgent.updateHealthParameters()

        // Условие выхода из цикла
        var stopRun = false
        // Цикл
        while(true) {
            // Выходные, праздники, каникулы
            var isHoliday = false
            if (dayOfTheWeek == 7) {
                isHoliday = true
            }
            if ((month == 1) && (day in arrayListOf(1, 2, 3, 7))) {
                isHoliday = true
            }
            if ((month == 5) && (day in arrayListOf(1, 9))) {
                isHoliday = true
            }
            if ((month == 2) && (day == 23)) {
                isHoliday = true
            }
            if ((month == 3) && (day == 8)) {
                isHoliday = true
            }
            if ((month == 6) && (day == 12)) {
                isHoliday = true
            }

            var isWorkHoliday = false
            if (dayOfTheWeek == 6) {
                isWorkHoliday = true
            }

            var isKindergartenHoliday = isWorkHoliday
            if (month in arrayListOf(7, 8)) {
                isKindergartenHoliday = true
            }

            var isSchoolHoliday = false
            if (month in arrayListOf(6, 7, 8)) {
                isSchoolHoliday = true
            }
            if ((month == 11) && (day in arrayListOf(5, 6, 7, 8, 9, 10, 11))) {
                isSchoolHoliday = true
            }
            if ((month == 12) && (day in arrayListOf(28, 29, 30, 31))) {
                isSchoolHoliday = true
            }
            if ((month == 1) && (day in arrayListOf(6, 7, 8, 9))) {
                isSchoolHoliday = true
            }
            if ((month == 3) && (day in arrayListOf(22, 23, 24, 25, 26, 27, 28, 29, 30, 31))) {
                isSchoolHoliday = true
            }

            var isUniversityHoliday = false
            if ((month == 12) && (day in arrayListOf(22, 23, 24, 25, 26, 27, 28, 29, 30, 31))) {
                isUniversityHoliday = true
            }
            if (month == 1) {
                if (day !in arrayListOf(11, 15, 19, 23, 27)) {
                    isUniversityHoliday = true
                }
            }
            if (month == 6) {
                if (day !in arrayListOf(11, 15, 19, 23, 27)) {
                    isUniversityHoliday = true
                }
            }
            if (month in arrayListOf(7, 8)) {
                isUniversityHoliday = true
            }
            if ((month == 2) && (day in arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))) {
                isUniversityHoliday = true
            }

            // Для каждого агента находим влияние силы инфекции на вероятность инфицирования на текущем шаге
            households.parallelStream().forEach { household ->
                household.agents.forEach { agent ->
                    agent.findInfectivityInfluence()
                }
            }
            // Выходной во всех коллективах
            // Полные контакты в домохозяйствах
            if (((isUniversityHoliday) && (isSchoolHoliday) && (isKindergartenHoliday) && (isWorkHoliday)) || (isHoliday)) {
                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if (agent.healthStatus == 1) {
                            makeContactsInHouseholdForAgent(true, household, agent)
                        }
                    }
                }
            } else {
                // Контакты в детских садах
                if (!isKindergartenHoliday) {
                    kindergarten.groupsByAge.parallelStream().forEach { groupByAge ->
                        groupByAge.forEach { group ->
                            group.agents.forEach { agent ->
                                if ((agent.healthStatus == 1) &&
                                        (!agent.isStayingHomeWhenInfected)) {
                                    makeContactsInGroupForAgent(group, agent)
                                }
                            }
                        }
                    }
                }
                // Контакты в рабочих коллективах
                if (!isWorkHoliday) {
                    workplace.workingGroups.parallelStream().forEach { group ->
                        group.agents.forEach { agent ->
                            if ((agent.healthStatus == 1) &&
                                    (!agent.isStayingHomeWhenInfected) && (!agent.isOnMotherLeave)) {
                                makeContactsInGroupForAgentAtWork(group, agent)
                            }
                        }
                    }
                }
                // Контакты в школах
                if (!isSchoolHoliday) {
                    school.groupsByAge.forEachIndexed { index, groupByAge ->
                        if ((index == 0) && (isWorkHoliday)) {

                        } else {
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if ((agent.healthStatus == 1) &&
                                            (!agent.isStayingHomeWhenInfected)) {
                                        makeContactsInGroupForAgent(group, agent)
                                    }
                                }
                            }
                        }
                    }
                }
                // Контакты в университетах
                if (!isUniversityHoliday) {
                    university.groupsByAge.parallelStream().forEach { groupByAge ->
                        groupByAge.forEach { group ->
                            group.agents.forEach { agent ->
                                if ((agent.healthStatus == 1) &&
                                        (!agent.isStayingHomeWhenInfected) && (!agent.isOnMotherLeave)) {
                                    makeContactsInGroupForAgent(group, agent)
                                }
                            }
                        }
                    }
                }
                // Контакты в домохозяйствах
                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if (agent.healthStatus == 1) {
                            makeContactsInHouseholdForAgent(false, household, agent,
                                isKindergartenHoliday, isSchoolHoliday, isWorkHoliday, isUniversityHoliday)
                        }
                    }
                }
            }
            // Обновление свойств агентов
            households.forEach { household ->
                household.agents.forEach { agent ->
                    when (agent.healthStatus) {
                        3 -> {
                            // + 1 вторичный случай
                            numOfInfected += 1
                            // Переход сразу в резистентное состояние
                            agent.healthStatus = 2
                        }
                        1 -> {
                            if (agent.daysInfected == agent.infectionPeriod) {
                                // Условие прекращения цикла
                                agent.healthStatus = 2
                                stopRun = true
                            } else {
                                agent.daysInfected += 1
                                if (!agent.isStayingHomeWhenInfected) {
                                    agent.isStayingHomeWhenInfected = agent.findIfShouldStayAtHome()
                                    if (agent.isStayingHomeWhenInfected) {
                                        if (agent.age < 14) {
                                            for (otherAgent in household.agents) {
                                                if (otherAgent.needMotherLeave) {
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
            if (stopRun) {
                break
            }
            // Обновляем дату
            dayOfTheWeek += 1
            if (dayOfTheWeek == 8) {
                dayOfTheWeek = 1
            }
            day += 1
            dayOfTheYear += 1
            if (dayOfTheYear == 366) {
                dayOfTheYear = 0
            }
            if (dayOfTheYear == 59) {
                dayOfTheYear = 60
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
        // Возвращаем число вторичных случаев
        return numOfInfected
    }
}
