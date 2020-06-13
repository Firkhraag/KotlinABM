package application.model

import kotlin.math.*

// Агент (является ли мужчиной, возраст)
class Agent(val isMale: Boolean, var age: Int) {

    // Нужда в больничном по уходу за ребенком дома в случае его болезни
    var needMotherLeave = false
    // Взял ли агент на больничном по уходу за ребенком
    var isOnMotherLeave = false

    // Месяц для младенцев 1-го года жизни
    var month = (1..12).random()

    // Массив идентификаторов коллег по работе, с которыми происходят контакты
    var connectedWorkAgents = arrayListOf<Int>()

    // Состояние агента
    // 0 - восприимчивый, 1 - инфицированный, 2 - резистентный, 3 - готов перейти в инкубационный период
    var healthStatus = when (age) {
        in 0..2 -> if ((0..999).random() < 16) 1 else 0 // 0.016
        in 3..6 -> if ((0..999).random() < 10) 1 else 0 // 0.01
        in 7..14 -> if ((0..999).random() < 7) 1 else 0 // 0.007
        else -> if ((0..999).random() < 3) 1 else 0 // 0.003
    }

    // Тип инфекции
    // FluA - грипп типа A
    // FluB - грипп типа B
    // RV - риновирус
    // RSV - респираторно-синцитиальный вирус
    // AdV - аденовирус
    // PIV - парагрипп
    // CoV - коронавирус (229e, oc43)
    var infectionType = if (healthStatus == 1) {
        when ((0..99).random()) {
            in 0..59 -> "RV" // 60%
            in 60..89 -> "AdV" // 30%
            else -> "PIV"// 10%
        }
    } else "none"

    // Дней в резистентном состоянии
    var daysImmune = if (healthStatus == 2) 1 else 0

    // Типоспецифический иммунитет
    var hasImmunityFluA = false
    var hasImmunityFluB = false
    var hasImmunityRV = false
    var hasImmunityRSV = false
    var hasImmunityAdV = false
    var hasImmunityPIV = false
    var hasImmunityCoV = false

    // Дней после приобретения типоспецифического иммунитета
    var immunityRVDays = 0
    var immunityRSVDays = 0
    var immunityPIVDays = 0
    var immunityAdVDays = 0
    var immunityCoVDays = 0

    // Продолжительность периода болезни
    fun findInfectionPeriod(): Int {
        return if (age < 16) {
            // Ребенок
            when(infectionType) {
                "fluA" -> {
                    // Erlang distribution (mean = 8.8, SD = 1.936)
                    val minValue = 4.0
                    val maxValue = 14.0
                    val mean = 8.8
                    val variance = 3.748
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "fluB" -> {
                    // Erlang distribution (mean = 7.8, SD = 1.716)
                    val minValue = 4.0
                    val maxValue = 14.0
                    val mean = 7.8
                    val variance = 2.94
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "RV" -> {
                    // Erlang distribution (mean = 11.4, SD = 2.5)
                    val minValue = 4.0
                    val maxValue = 14.0
                    val mean = 11.4
                    val variance = 6.25
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "RSV" -> {
                    // Erlang distribution (mean = 9.3, SD = 2.0)
                    val minValue = 4.0
                    val maxValue = 14.0
                    val mean = 9.3
                    val variance = 4.0
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "AdV" -> {
                    // Erlang distribution (mean = 9.0, SD = 1.98)
                    val minValue = 4.0
                    val maxValue = 14.0
                    val mean = 9.0
                    val variance = 3.92
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "PIV" -> {
                    // Erlang distribution (mean = 8.0, SD = 1.76)
                    val minValue = 4.0
                    val maxValue = 14.0
                    val mean = 8.0
                    val variance = 3.1
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "CoV" -> {
                    // Erlang distribution (mean = 8.0, SD = 1.76)
                    val minValue = 4.0
                    val maxValue = 14.0
                    val mean = 8.0
                    val variance = 3.1
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                else -> 0
            }
        } else {
            // Взрослый
            when(infectionType) {
                "fluA" -> {
                    // Erlang distribution (mean = 4.8, SD = 1.056)
                    val minValue = 3.0
                    val maxValue = 12.0
                    val mean = 4.8
                    val variance = 1.12
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "fluB" -> {
                    // Erlang distribution (mean = 3.7, SD = 0.814)
                    val minValue = 3.0
                    val maxValue = 12.0
                    val mean = 3.7
                    val variance = 0.66
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "RV" -> {
                    // Erlang distribution (mean = 10.1, SD = 2.22)
                    val minValue = 3.0
                    val maxValue = 12.0
                    val mean = 10.1
                    val variance = 4.93
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "RSV" -> {
                    // Erlang distribution (mean = 7.4, SD = 1.63)
                    val minValue = 3.0
                    val maxValue = 12.0
                    val mean = 7.4
                    val variance = 2.66
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "AdV" -> {
                    // Erlang distribution (mean = 8.0, SD = 1.76)
                    val minValue = 3.0
                    val maxValue = 12.0
                    val mean = 8.0
                    val variance = 3.1
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "PIV" -> {
                    // Erlang distribution (mean = 7.0, SD = 1.54)
                    val minValue = 3.0
                    val maxValue = 12.0
                    val mean = 7.0
                    val variance = 2.37
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                "CoV" -> {
                    // Erlang distribution (mean = 7.0, SD = 1.54)
                    val minValue = 3.0
                    val maxValue = 12.0
                    val mean = 7.0
                    val variance = 2.37
                    var scale = variance / mean
                    val shape = round(mean / scale)
                    scale = mean / shape
                    val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                    min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
                }
                else -> 0
            }
        }
    }
    var infectionPeriod = findInfectionPeriod()

    // Продолжительность инкубационного периода
    fun findIncubationPeriod(): Int {
        return when (infectionType) {
            "fluA" -> {
                // Erlang distribution (mean = 1.4, SD = 0.3)
                val minValue = 1.0
                val maxValue = 7.0
                val mean = 1.4
                val variance = 0.09
                var scale = variance / mean
                val shape = round(mean / scale)
                scale = mean / shape
                val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
            }
            "fluB" -> {
                // Erlang distribution (mean = 1.0, SD = 0.22)
                val minValue = 1.0
                val maxValue = 7.0
                val mean = 1.0
                val variance = 0.0484
                var scale = variance / mean
                val shape = round(mean / scale)
                scale = mean / shape
                val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
            }
            "RV" -> {
                // Erlang distribution (mean = 1.9, SD = 0.418)
                val minValue = 1.0
                val maxValue = 7.0
                val mean = 1.9
                val variance = 0.175
                var scale = variance / mean
                val shape = round(mean / scale)
                scale = mean / shape
                val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
            }
            "RSV" -> {
                // Erlang distribution (mean = 4.4, SD = 0.968)
                val minValue = 1.0
                val maxValue = 7.0
                val mean = 4.4
                val variance = 0.937
                var scale = variance / mean
                val shape = round(mean / scale)
                scale = mean / shape
                val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
            }
            "AdV" -> {
                // Erlang distribution (mean = 5.6, SD = 1.23)
                val minValue = 1.0
                val maxValue = 7.0
                val mean = 5.6
                val variance = 1.51
                var scale = variance / mean
                val shape = round(mean / scale)
                scale = mean / shape
                val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
            }
            "PIV" -> {
                // Erlang distribution (mean = 2.6, SD = 0.572)
                val minValue = 1.0
                val maxValue = 7.0
                val mean = 2.6
                val variance = 0.327
                var scale = variance / mean
                val shape = round(mean / scale)
                scale = mean / shape
                val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
            }
            "CoV" -> {
                // Erlang distribution (mean = 3.2, SD = 0.704)
                val minValue = 1.0
                val maxValue = 7.0
                val mean = 3.2
                val variance = 0.496
                var scale = variance / mean
                val shape = round(mean / scale)
                scale = mean / shape
                val erlangDistribution = org.apache.commons.math3.distribution.GammaDistribution(shape, scale)
                min(maxValue, max(minValue, erlangDistribution.sample())).roundToInt()
            }
            else -> 0
        }
    }
    var incubationPeriod = findIncubationPeriod()

    // Протекание болезни в бессимптомной форме
    fun findIfWillBeAsymptomatic(): Boolean {
        // Вероятность бессимптомной формы
        val probab = when (infectionType) {
            // Грипп - 16%
            "fluA" -> 16
            "fluB" -> 16
            // Остальные инфекции - 30%
            else -> 30
        }
        return (0..99).random() >= 100 - probab
    }
    var isAsymptomatic = findIfWillBeAsymptomatic()

    // Самоизоляция и выявление
    fun findIfShouldStayAtHome(): Boolean {
        // Если бессимптомный
        if (isAsymptomatic) {
            return false
        }
        // Дней от начала симптомов
        return when (daysInfected) {
            1 -> when (age) {
                in 0..7 -> ((0..999).random() < 304) // 30%
                in 8..17 -> ((0..999).random() < 203) // 20%
                else -> ((0..9).random() == 0) // 10%
            }
            2 -> when (age) {
                in 0..7 -> ((0..999).random() < 575) // 58%
                in 8..17 -> ((0..999).random() < 498) // 50%
                else -> ((0..999).random() < 333) // 33%
            }
            3 -> when (age) {
                in 0..7 -> ((0..999).random() < 324) // 32%
                in 8..17 -> ((0..999).random() < 375) // 38%
                else -> ((0..999).random() < 167) // 17%
            }
            else -> false
        }
    }
    var isStayingHomeWhenInfected = findIfShouldStayAtHome()

    // Средняя вирусная нагрузка
    fun findMeanViralLoad(): Double {
        return when(infectionType) {
            "fluA" -> {
                when(age) {
                    in 0..2 -> 4.6
                    in 3..15 -> 3.45
                    else -> 2.3
                }
            }
            "fluB" -> {
                when(age) {
                    in 0..2 -> 4.7
                    in 3..15 -> 3.53
                    else -> 2.35
                }
            }
            "RV" -> {
                when(age) {
                    in 0..2 -> 3.5
                    in 3..15 -> 2.63
                    else -> 1.75
                }
            }
            "RSV" -> {
                when(age) {
                    in 0..2 -> 6.0
                    in 3..15 -> 4.5
                    else -> 2.25
                }
            }
            "AdV" -> {
                when(age) {
                    in 0..2 -> 4.1
                    in 3..15 -> 3.08
                    else -> 2.05
                }
            }
            "PIV" -> {
                when(age) {
                    in 0..2 -> 4.7
                    in 3..15 -> 3.53
                    else -> 2.35
                }
            }
            "CoV" -> {
                when(age) {
                    in 0..2 -> 4.93
                    in 3..15 -> 3.7
                    else -> 2.47
                }
            }
            else -> 0.0
        }
    }
    var meanViralLoad = findMeanViralLoad()

    // Обновить вирусную нагрузку на текущем шаге
    private fun findViralLoad(): Double {
        return min(12.0, if (daysInfected < 1) {
            if (incubationPeriod == 1) {
                meanViralLoad / 2
            } else {
                val k = meanViralLoad / (incubationPeriod - 1)
                val b = k * (incubationPeriod - 1)
                k * daysInfected + b
            }
        } else {
            val k = 2 * meanViralLoad / (1 - infectionPeriod)
            val b = -k * infectionPeriod
            if (isAsymptomatic) {
                1/2 * (k * daysInfected + b)
            } else {
                k * daysInfected + b
            }
        })
    }

    // День с момента инфицирования
    var daysInfected = if (healthStatus == 1) ((1 - incubationPeriod)..infectionPeriod).random() else 0

    // Обновление динамических свойств агента
    fun updateHealthParameters() {
        isAsymptomatic = findIfWillBeAsymptomatic()
        incubationPeriod = findIncubationPeriod()
        infectionPeriod = findInfectionPeriod()
        daysInfected = 1 - incubationPeriod
        isStayingHomeWhenInfected = findIfShouldStayAtHome()
        meanViralLoad = findMeanViralLoad()
    }

    // Уровень IgG
    private fun getIgGLevel(): Double {
        val rand = java.util.Random()
        return when (age) {
            0 -> when (month) {
                1 -> min(1480.0, max(399.0,913.85 + rand.nextGaussian() * 262.19))
                in 2..4 -> min(981.0, max(217.0,429.5 + rand.nextGaussian() * 145.59))
                in 5..7 -> min(1110.0, max(270.0,482.43 + rand.nextGaussian() * 236.8))
                else -> min(977.0, max(242.0,536.79 + rand.nextGaussian() * 186.62))
            }
            1 -> min(1260.0, max(389.0,726.79 + rand.nextGaussian() * 238.61))
            2 -> min(1970.0, max(486.0,786.41 + rand.nextGaussian() * 249.14))
            in 3..5 -> min(1120.0, max(457.0,823.19 + rand.nextGaussian() * 164.19))
            in 6..8 -> min(1580.0, max(483.0,982.86 + rand.nextGaussian() * 255.53))
            in 9..11 -> min(2290.0, max(642.0,1016.12 + rand.nextGaussian() * 322.27))
            in 12..16 -> min(1610.0, max(636.0,1123.56 + rand.nextGaussian() * 203.83))
            in 17..18 -> min(2430.0, max(688.0,1277.20 + rand.nextGaussian() * 361.89))
            in 19..29 -> min(2757.3, max(780.6,1449.2 + rand.nextGaussian() * 410.63))
            in 30..59 -> min(3005.46, max(850.85,1579.63 + rand.nextGaussian() * 447.59))
            else -> min(2704.91, max(765.77,1421.67 + rand.nextGaussian() * 369.57))
        }
    }

    // Уровень IgA
    private fun getIgALevel(): Double {
        val rand = java.util.Random()
        return when (age) {
            0 -> when (month) {
                1 -> min(8.75, max(6.67,6.77 + rand.nextGaussian() * 0.45))
                in 2..4 -> min(24.6, max(6.67,9.58 + rand.nextGaussian() * 5.16))
                in 5..7 -> min(53.0, max(6.67,17.23 + rand.nextGaussian() * 9.77))
                else -> min(114.0, max(6.68,23.63 + rand.nextGaussian() * 12.37))
            }
            1 -> min(103.0, max(13.1,34.09 + rand.nextGaussian() * 17.1))
            2 -> min(135.0, max(6.67,48.87 + rand.nextGaussian() * 24.52))
            in 3..5 -> min(192.0, max(35.7,62.75 + rand.nextGaussian() * 34.05))
            in 6..8 -> min(276.0, max(44.8,97.38 + rand.nextGaussian() * 49.66))
            in 9..11 -> min(262.0, max(32.6,102.27 + rand.nextGaussian() * 47.05))
            in 12..16 -> min(305.0, max(36.4,112.16 + rand.nextGaussian() * 47.51))
            in 17..18 -> min(385.0, max(46.3,179.21 + rand.nextGaussian() * 89.92))
            in 19..29 -> min(436.86, max(52.54,203.35 + rand.nextGaussian() * 102.0))
            in 30..59 -> min(476.18, max(57.26,221.65 + rand.nextGaussian() * 111.18))
            else -> min(428.56, max(51.5,199.49 + rand.nextGaussian() * 100.01))
        }
    }

    // Уровень IgM
    private fun getIgMLevel(): Double {
        val rand = java.util.Random()
        return when (age) {
            0 -> when (month) {
                1 -> min(50.9, max(5.1,16.89 + rand.nextGaussian() * 8.87))
                in 2..4 -> min(68.5, max(15.2,34.21 + rand.nextGaussian() * 13.55))
                in 5..7 -> min(130.0, max(26.9,69.05 + rand.nextGaussian() * 29.73))
                else -> min(162.0, max(24.2,73.42 + rand.nextGaussian() * 35.76))
            }
            1 -> min(195.0, max(38.6,115.25 + rand.nextGaussian() * 41.63))
            2 -> min(236.0, max(42.7,104.66 + rand.nextGaussian() * 40.55))
            in 3..5 -> min(198.0, max(58.7,115.60 + rand.nextGaussian() * 39.24))
            in 6..8 -> min(242.0, max(50.3,108.05 + rand.nextGaussian() * 41.27))
            in 9..11 -> min(213.0, max(37.4,104.95 + rand.nextGaussian() * 43.68))
            in 12..16 -> min(197.0, max(42.4,119.16 + rand.nextGaussian() * 39.31))
            in 17..18 -> min(323.0, max(60.7,130.60 + rand.nextGaussian() * 64.32))
            in 19..29 -> min(366.51, max(68.88,148.19 + rand.nextGaussian() * 72.98))
            in 30..59 -> min(399.5, max(75.08,161.53 + rand.nextGaussian() * 79.55))
            else -> min(359.55, max(67.57,145.38 + rand.nextGaussian() * 71.6))
        }
    }

    // Нормализованный суммарный уровень иммуноглобулина
    private fun getIgLevel(): Double {
        // IgG
        val igGMax = 3005.46
        val igGMin = 217.0
        // IgA
        val igAMax = 476.18
        val igAMin = 6.67
        // IgM
        val igMMax = 399.5
        val igMMin = 5.1
        return (getIgGLevel() + getIgALevel() + getIgMLevel() - igGMin - igAMin - igMMin) /
                (igGMax + igAMax + igMMax - igGMin - igAMin - igMMin)
    }

    // Влияние силы инфекции на вероятность инфицирования
    var infectivityInfluence = 0.0
    fun findInfectivityInfluence() {
        infectivityInfluence = findViralLoad() / 12.0
    }

    // Влияние восприимчивости на вероятность инфицирования
    val susceptibilityInfluenceFluA = 2 / (1 + exp((susceptibilityParameterMap["fluA"] ?: error("Required")) * getIgLevel()))
    val susceptibilityInfluenceFluB = 2 / (1 + exp((susceptibilityParameterMap["fluB"] ?: error("Required")) * getIgLevel()))
    val susceptibilityInfluenceRV = 2 / (1 + exp((susceptibilityParameterMap["RV"] ?: error("Required")) * getIgLevel()))
    val susceptibilityInfluenceRSV = 2 / (1 + exp((susceptibilityParameterMap["RSV"] ?: error("Required")) * getIgLevel()))
    val susceptibilityInfluenceAdV = 2 / (1 + exp((susceptibilityParameterMap["AdV"] ?: error("Required")) * getIgLevel()))
    val susceptibilityInfluencePIV = 2 / (1 + exp((susceptibilityParameterMap["PIV"] ?: error("Required")) * getIgLevel()))
    val susceptibilityInfluenceCoV = 2 / (1 + exp((susceptibilityParameterMap["CoV"] ?: error("Required")) * getIgLevel()))

    // Социальный статус
    var activityStatus: Int = when (age) {
        // Ясли
        in 0..2 -> if ((0..99).random() < 23) 1 else 0
        // Детсад
        in 3..5 -> if ((0..99).random() < 83) 1 else 0
        6 -> 1
        // Школа
        in 7..17 -> 2
        // 50% - школа, 50% - универ
        18 -> if ((0..1).random() == 0) 2 else 3
        // 50% - универ, 50% - работа
        in 19..22 -> if ((0..1).random() == 0) 3 else 4
        // Магистратура / работа
        in 23..24 -> if ((0..99).random() < 82) 4 else {
            if ((0..9).random() < 6) 3 else 0
        }
        // Работа
        in 25..64 -> {
            if (isMale) when (age) {
                in 25..29 -> if ((0..99).random() < 82) 4 else 0
                in 30..39 -> if ((0..99).random() < 95) 4 else 0
                in 40..49 -> if ((0..99).random() < 94) 4 else 0
                in 50..59 -> if ((0..99).random() < 88) 4 else 0
                else -> if ((0..99).random() < 51) 4 else 0
            } else when (age) {
                in 23..29 -> if ((0..99).random() < 74) 4 else 0
                in 30..39 -> if ((0..99).random() < 85) 4 else 0
                in 40..49 -> if ((0..99).random() < 89) 4 else 0
                in 50..59 -> if ((0..99).random() < 70) 4 else 0
                else -> if ((0..99).random() < 29) 5 else 0
            }
        }
        else -> 0
    }
}
