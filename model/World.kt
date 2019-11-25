package model

import kotlin.math.*
import utility.readTableFloat
import utility.readTableInt
import utility.writeTableResult
import org.apache.commons.math3.distribution.GammaDistribution
import kotlinx.coroutines.*

class World {

    private val temp = mapOf(0 to -5.8, 1 to -5.9, 2 to -5.9, 3 to -5.9, 4 to -6.0, 5 to -6.0, 6 to -6.1, 7 to -6.1,
            8 to -6.2, 9 to -6.2, 10 to -6.2, 11 to -6.3, 12 to -6.3, 13 to -6.4, 14 to -6.5, 15 to -6.5, 16 to -6.6,
            17 to -6.6, 18 to -6.7, 19 to -6.7, 20 to -6.8, 21 to -6.8, 22 to -6.9, 23 to -6.9, 24 to -7.0, 25 to -7.0,
            26 to -7.0, 27 to -7.1, 28 to -7.1, 29 to -7.1, 30 to -7.1, 31 to -7.2, 32 to -7.2, 33 to -7.2, 34 to -7.2,
            35 to -7.2, 36 to -7.2, 37 to -7.1, 38 to -7.1, 39 to -7.1, 40 to -7.0, 41 to -7.0, 42 to -6.9, 43 to -6.8,
            44 to -6.8, 45 to -6.7, 46 to -6.6, 47 to -6.5, 48 to -6.4, 49 to -6.3, 50 to -6.1, 51 to -6.0, 52 to -5.9,
            53 to -5.7, 54 to -5.6, 55 to -5.4, 56 to -5.2, 57 to -5.0, 58 to -4.8, 59 to -4.7, 60 to -4.7, 61 to -4.5,
            62 to -4.2, 63 to -4.0, 64 to -3.8, 65 to -3.6, 66 to -3.4, 67 to -3.1, 68 to -2.9, 69 to -2.7, 70 to -2.4,
            71 to -2.2, 72 to -1.9, 73 to -1.7, 74 to -1.4, 75 to -1.2, 76 to -0.9, 77 to -0.6, 78 to -0.4, 79 to -0.1,
            80 to 0.2, 81 to 0.4, 82 to 0.7, 83 to 1.0, 84 to 1.2, 85 to 1.5, 86 to 1.8, 87 to 2.0, 88 to 2.3, 89 to 2.5,
            90 to 2.8, 91 to 3.1, 92 to 3.3, 93 to 3.6, 94 to 3.9, 95 to 4.1, 96 to 4.4, 97 to 4.6, 98 to 4.9, 99 to 5.1,
            100 to 5.4, 101 to 5.6, 102 to 5.9, 103 to 6.1, 104 to 6.4, 105 to 6.6, 106 to 6.9, 107 to 7.1, 108 to 7.4,
            109 to 7.6, 110 to 7.8, 111 to 8.1, 112 to 8.3, 113 to 8.5, 114 to 8.8, 115 to 9.0, 116 to 9.2, 117 to 9.4,
            118 to 9.7, 119 to 9.9, 120 to 10.1, 121 to 10.3, 122 to 10.5, 123 to 10.7, 124 to 11.0, 125 to 11.2,
            126 to 11.4, 127 to 11.6, 128 to 11.8, 129 to 12.0, 130 to 12.1, 131 to 12.3, 132 to 12.5, 133 to 12.7,
            134 to 12.9, 135 to 13.1, 136 to 13.2, 137 to 13.4, 138 to 13.6, 139 to 13.7, 140 to 13.9, 141 to 14.0,
            142 to 14.2, 143 to 14.3, 144 to 14.5, 145 to 14.6, 146 to 14.8, 147 to 14.9, 148 to 15.0, 149 to 15.2,
            150 to 15.3, 151 to 15.4, 152 to 15.5, 153 to 15.6, 154 to 15.8, 155 to 15.9, 156 to 16.0, 157 to 16.1,
            158 to 16.2, 159 to 16.3, 160 to 16.4, 161 to 16.5, 162 to 16.6, 163 to 16.7, 164 to 16.8, 165 to 16.9,
            166 to 17.0, 167 to 17.1, 168 to 17.2, 169 to 17.2, 170 to 17.3, 171 to 17.4, 172 to 17.5, 173 to 17.6,
            174 to 17.7, 175 to 17.8, 176 to 17.9, 177 to 17.9, 178 to 18.0, 179 to 18.1, 180 to 18.2, 181 to 18.3,
            182 to 18.4, 183 to 18.4, 184 to 18.5, 185 to 18.6, 186 to 18.7, 187 to 18.7, 188 to 18.8, 189 to 18.9,
            190 to 18.9, 191 to 19.0, 192 to 19.1, 193 to 19.1, 194 to 19.2, 195 to 19.2, 196 to 19.3, 197 to 19.3,
            198 to 19.3, 199 to 19.4, 200 to 19.4, 201 to 19.4, 202 to 19.4, 203 to 19.4, 204 to 19.4, 205 to 19.4,
            206 to 19.4, 207 to 19.4, 208 to 19.3, 209 to 19.3, 210 to 19.3, 211 to 19.2, 212 to 19.1, 213 to 19.1,
            214 to 19.0, 215 to 18.9, 216 to 18.8, 217 to 18.7, 218 to 18.6, 219 to 18.5, 220 to 18.4, 221 to 18.3,
            222 to 18.2, 223 to 18.0, 224 to 17.9, 225 to 17.7, 226 to 17.6, 227 to 17.4, 228 to 17.2, 229 to 17.1,
            230 to 16.9, 231 to 16.7, 232 to 16.5, 233 to 16.3, 234 to 16.1, 235 to 15.9, 236 to 15.7, 237 to 15.5,
            238 to 15.3, 239 to 15.1, 240 to 14.9, 241 to 14.7, 242 to 14.5, 243 to 14.3, 244 to 14.1, 245 to 13.9,
            246 to 13.7, 247 to 13.5, 248 to 13.3, 249 to 13.1, 250 to 12.8, 251 to 12.6, 252 to 12.4, 253 to 12.2,
            254 to 12.1, 255 to 11.9, 256 to 11.7, 257 to 11.5, 258 to 11.3, 259 to 11.1, 260 to 10.9, 261 to 10.7,
            262 to 10.6, 263 to 10.4, 264 to 10.2, 265 to 10.0, 266 to 9.9, 267 to 9.7, 268 to 9.5, 269 to 9.4,
            270 to 9.2, 271 to 9.0, 272 to 8.9, 273 to 8.7, 274 to 8.5, 275 to 8.3, 276 to 8.2, 277 to 8.0,
            278 to 7.8, 279 to 7.7, 280 to 7.5, 281 to 7.3, 282 to 7.1, 283 to 6.9, 284 to 6.8, 285 to 6.6,
            286 to 6.4, 287 to 6.2, 288 to 6.0, 289 to 5.8, 290 to 5.6, 291 to 5.4, 292 to 5.2, 293 to 4.9,
            294 to 4.7, 295 to 4.5, 296 to 4.3, 297 to 4.0, 298 to 3.8, 299 to 3.6, 300 to 3.3, 301 to 3.1,
            302 to 2.9, 303 to 2.6, 304 to 2.4, 305 to 2.1, 306 to 1.9, 307 to 1.6, 308 to 1.4, 309 to 1.1,
            310 to 0.9, 311 to 0.7, 312 to 0.4, 313 to 0.2, 314 to -0.1, 315 to -0.3, 316 to -0.5, 317 to -0.8,
            318 to -1.0, 319 to -1.2, 320 to -1.5, 321 to -1.7, 322 to -1.9, 323 to -2.1, 324 to -2.3, 325 to -2.5,
            326 to -2.7, 327 to -2.9, 328 to -3.0, 329 to -3.2, 330 to -3.4, 331 to -3.5, 332 to -3.7, 333 to -3.8,
            334 to -4.0, 335 to -4.1, 336 to -4.2, 337 to -4.3, 338 to -4.4, 339 to -4.5, 340 to -4.6, 341 to -4.7,
            342 to -4.8, 343 to -4.9, 344 to -5.0, 345 to -5.0, 346 to -5.1, 347 to -5.2, 348 to -5.2, 349 to -5.3,
            350 to -5.3, 351 to -5.4, 352 to -5.4, 353 to -5.4, 354 to -5.5, 355 to -5.5, 356 to -5.5, 357 to -5.6,
            358 to -5.6, 359 to -5.6, 360 to -5.7, 361 to -5.7, 362 to -5.7, 363 to -5.7, 364 to -5.8, 365 to -5.8
    )

    private val humidity = arrayListOf(85.0, 81.0, 74.0, 66.0, 66.0, 69.0, 72.0, 75.0, 81.0, 83.0, 86.0, 86.0)

    private val maxTemp = 19.4
    private val minTemp = -7.2

    private val maxMinTemp = 26.6

    val infectivityMapAdult = mapOf(1 to 0.0, 2 to 0.045, 3 to 0.055, 4 to 0.045, 5 to 0.03,
            6 to 0.02, 7 to 0.01, 8 to 0.05)
    val infectivityMapChild = mapOf(1 to 0.0, 2 to 0.095, 3 to 0.095, 4 to 0.075, 5 to 0.05,
            6 to 0.03, 7 to 0.015, 8 to 0.01, 9 to 0.05, 10 to 0.005, 11 to 0.004, 12 to 0.003, 13 to 0.002, 14 to 0.001)

    private var day = 1
    private var month = 8
    private var year = 1

    private var globalDay = 0
    private var tempDay = 213
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

    private fun contactsInHouseholdForAgent(household: Household, agent: Agent, externalRisk: Double, coeffD: Double,
                                            deviationD: Double, coeffA: Double, deviationA: Double, coeffTS: Double) {
        val infectivity = if (agent.age > 17) {
            infectivityMapAdult[agent.daysInfected] ?: 0.001
        } else {
            infectivityMapChild[agent.daysInfected] ?: 0.001
        }

        household.agents.forEach { agent2 ->
            if (agent2.healthStatus == 0) {
//                val internalCognition = if (agent2.age > 17) {
//                    (coeffD * exp(-1.0 / household.agents.size) - deviationD) * (coeffA - deviationA)
//                } else {
//                    (coeffD * exp(-1.0 / household.agents.size) - deviationD) * (coeffA / (1 +exp(-1.0 / agent2.age)) - deviationA)
//                }
//                val internalRisk = exp(-internalCognition * household.numOfInfected / household.agents.size)
                val internalRisk = 1

                var susceptibility = when (agent.age) {
                    in 0..17 -> {
                        1.18153846154
                    }
                    in 18..64 -> {
                        1.11384615385
                    }
                    else -> {
                        1.0
                    }
                }
                val curTemp = temp[tempDay] ?: 1.0 - minTemp
                susceptibility *= coeffTS * curTemp + 1
                val randNum = (0..9999).random() * 0.0001
                if (randNum < infectivity * susceptibility * externalRisk * internalRisk) {
                    agent2.healthStatus = 3
                }
            }
        }
    }

    private fun contactsInHouseholdForAgentInSaturday(household: Household, agent: Agent, externalRisk: Double, coeffD: Double,
                                                      deviationD: Double, coeffA: Double, deviationA: Double, coeffTS: Double) {
        val infectivity = if (agent.age > 17) {
            infectivityMapAdult[agent.daysInfected] ?: 0.001
        } else {
            infectivityMapChild[agent.daysInfected] ?: 0.001
        }

        household.agents.forEach { agent2 ->
            if (((agent.healthStatus == 0) && (agent.isStayingHomeWhenInfected)) ||
                    ((agent.healthStatus == 0) && (agent.activityStatus == 5)) ||
                    ((agent.healthStatus == 0) && (agent.activityStatus == 1)) ||
                    ((agent.healthStatus == 0) && (agent.activityStatus == 0))) {
//                val internalCognition = if (agent2.age > 17) {
//                    (coeffD * exp(-1.0 / household.agents.size) - deviationD) * (coeffA - deviationA)
//                } else {
//                    (coeffD * exp(-1.0 / household.agents.size) - deviationD) * (coeffA / (1 +exp(-1.0 / agent2.age)) - deviationA)
//                }
//                val internalRisk = exp(-internalCognition * household.numOfInfected / household.agents.size)
                val internalRisk = 1

                var susceptibility = when (agent.age) {
                    in 0..17 -> {
                        1.18153846154
                    }
                    in 18..64 -> {
                        1.11384615385
                    }
                    else -> {
                        1.0
                    }
                }
                val curTemp = temp[tempDay] ?: 1.0 - minTemp
                susceptibility *= coeffTS * curTemp + 1
                val randNum = (0..9999).random() * 0.0001
                if (randNum < infectivity * susceptibility * externalRisk * internalRisk) {
                    agent2.healthStatus = 3
                }
            }
        }
    }

    private fun contactsInGroupForAgent(group: Group, agent: Agent, externalRisk: Double, coeffD: Double,
                                        deviationD: Double, coeffA: Double, deviationA: Double, coeffTS: Double) {
        val infectivity = if (agent.age > 17) {
            infectivityMapAdult[agent.daysInfected] ?: 0.001
        } else {
            infectivityMapChild[agent.daysInfected] ?: 0.001
        }

        group.agents.forEach { agent2 ->
            if (agent2.healthStatus == 0) {

//                val internalCognition = if (agent2.age > 17) {
//                    (coeffD * exp(-1.0 / group.numOfAgents) - deviationD) * (coeffA - deviationA)
//                } else {
//                    (coeffD * exp(-1.0 / group.numOfAgents) - deviationD) * (coeffA / (1 +exp(-1.0 / agent2.age)) - deviationA)
//                }
//                val internalRisk = exp(-internalCognition * group.numOfInfected / group.numOfAgents)
                val internalRisk = 1

                val randNum = (0..9999).random() * 0.0001
                var susceptibility = when (agent.age) {
                    in 0..17 -> {
                        1.18153846154
                    }
                    in 18..64 -> {
                        1.11384615385
                    }
                    else -> {
                        1.0
                    }
                }
                val curTemp = temp[tempDay] ?: 1.0 - minTemp
                susceptibility *= coeffTS * curTemp + 1
                if (randNum < infectivity * susceptibility * externalRisk * internalRisk) {
                    agent2.healthStatus = 3
                }
            }
        }
    }

    fun runSimulation(scale: Double, coeffR: Double, deviationR: Double, coeffT: Double, deviationT: Double, coeffD: Double,
                      deviationD: Double, coeffA: Double, deviationA: Double, coeffTS: Double) {

        worldStats[3] = worldStats[1] / 7
        while(true) {

            writeTableResult("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\output\\results.xlsx",
                    globalDay, worldStats)


//            val curTemp = temp[tempDay] ?: 1.0 - minTemp
////            val externalCognition = (coeffT / (1 + exp(-maxMinTemp / curTemp)) - deviationT) *
////                    (coeffR / (1 +exp(-worldStats[3] / worldStats[0].toDouble())) - deviationR)
////            val externalRisk = scale * exp(-
            val externalRisk = scale
            worldStats[3] = 0

            households.parallelStream().forEach { household ->
                household.agents.forEach { agent ->
                    if (agent.healthStatus == 1) {
                        contactsInHouseholdForAgent(household, agent, externalRisk, coeffD, deviationD, coeffA, deviationA, coeffTS)
                    }
                }
            }
            if ((dayOfTheWeek != 7) && (((month != 1) || ((month == 1) && (day !in arrayListOf(1, 2, 3, 4, 5, 6, 7, 8)))) &&
                            ((month != 5) || ((month == 5) && (day !in arrayListOf(1, 9)))) &&
                            ((month != 2) || ((month == 2) && (day != 23))) &&
                            ((month != 3) || ((month == 3) && (day != 8))) &&
                            ((month != 11) || ((month == 11) && (day != 4))))) {
                if (dayOfTheWeek != 6) {
                    if (month !in arrayListOf(6, 7, 8)) {
                        kindergartens.parallelStream().forEach { kindergarten ->
                            kindergarten.groupsByAge.forEach { (_, groupByAge) ->
                                groupByAge.forEach { group ->
                                    group.agents.forEach { agent ->
                                        if ((agent.healthStatus == 1) &&
                                                (!agent.isStayingHomeWhenInfected)) {
                                            contactsInGroupForAgent(group, agent, externalRisk, coeffD, deviationD, coeffA, deviationA, coeffTS)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    homes.parallelStream().forEach { home ->
                        home.workingGroups.forEach { group ->
                            group.agents.forEach { agent ->
                                if ((agent.healthStatus == 1) &&
                                        (!agent.isStayingHomeWhenInfected)) {
                                    contactsInGroupForAgent(group, agent, externalRisk, coeffD, deviationD, coeffA, deviationA, coeffTS)
                                }
                            }
                        }
                    }
                }
                if (month !in arrayListOf(6, 7, 8)) {
                    if (((month != 3) || ((month == 3) && (day !in arrayListOf(21, 22, 23, 24, 25, 26, 27, 28, 29)))) &&
                            ((month != 10) || ((month == 10) && (day !in arrayListOf(28, 29, 30, 31)))) &&
                            ((month != 11) || ((month == 11) && (day !in arrayListOf(1, 2, 3))))) {
                        schools.parallelStream().forEach { school ->
                            school.groupsByAge.forEach { (_, groupByAge) ->
                                groupByAge.forEach { group ->
                                    group.agents.forEach { agent ->
                                        if ((agent.healthStatus == 1) &&
                                                (!agent.isStayingHomeWhenInfected)) {
                                            contactsInGroupForAgent(group, agent, externalRisk, coeffD, deviationD, coeffA, deviationA, coeffTS)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    universities.parallelStream().forEach { university ->
                        university.groupsByAge.forEach { (_, groupByAge) ->
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if ((agent.healthStatus == 1) &&
                                            (!agent.isStayingHomeWhenInfected)) {
                                        contactsInGroupForAgent(group, agent, externalRisk, coeffD, deviationD, coeffA, deviationA, coeffTS)
                                    }
                                }
                            }
                        }
                    }
                    colleges.parallelStream().forEach { college ->
                        college.groupsByAge.forEach { (_, groupByAge) ->
                            groupByAge.forEach { group ->
                                group.agents.forEach { agent ->
                                    if ((agent.healthStatus == 1) &&
                                            (!agent.isStayingHomeWhenInfected)) {
                                        contactsInGroupForAgent(group, agent, externalRisk, coeffD, deviationD, coeffA, deviationA, coeffTS)
                                    }
                                }
                            }
                        }
                    }
                } else if ((month in arrayListOf(6, 7, 8)) && (dayOfTheWeek == 6)) {
                    households.parallelStream().forEach { household ->
                        household.agents.forEach { agent ->
                            if (agent.healthStatus == 1) {
                                contactsInHouseholdForAgent(household, agent, externalRisk, coeffD, deviationD, coeffA, deviationA, coeffTS)
                            }
                        }
                    }
                } else if (dayOfTheWeek == 6) {
                    households.parallelStream().forEach { household ->
                        household.agents.forEach { agent ->
                            if (((agent.healthStatus == 1) && (agent.isStayingHomeWhenInfected)) ||
                                    ((agent.healthStatus == 1) && (agent.activityStatus == 5)) ||
                                    ((agent.healthStatus == 1) && (agent.activityStatus == 1)) ||
                                    ((agent.healthStatus == 1) && (agent.activityStatus == 0))) {
                                contactsInHouseholdForAgentInSaturday(household, agent, externalRisk, coeffD, deviationD, coeffA, deviationA, coeffTS)
                            }
                        }
                    }
                }
            } else {
                households.parallelStream().forEach { household ->
                    household.agents.forEach { agent ->
                        if (agent.healthStatus == 1) {
                            contactsInHouseholdForAgent(household, agent, externalRisk, coeffD, deviationD, coeffA, deviationA, coeffTS)
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
                            agent.daysInfected = 1
//                            agent.shouldBeInfected = agent.willBeInfected()
                            agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
                            household.numOfInfected += 1
                            if (agent.groupId != -1) {
                                if (agent.isStayingHomeWhenInfected) {
                                    groupRepo[agent.groupId].numOfAgents -= 1
                                } else {
                                    groupRepo[agent.groupId].numOfInfected += 1
                                }
                            }
                        }
                        1 -> {
                            //println("Id: ${agent.id}; Infected: ${agent.daysInfected}; ShouldBeInfected: ${agent.shouldBeInfected}")
                            if (agent.daysInfected == agent.shouldBeInfected) {
                                agent.healthStatus = 2
                                worldStats[1] -= 1
                                worldStats[2] += 1
//                                agent.daysInfected = 0
                                if (agent.groupId != -1) {
                                    if (!agent.isStayingHomeWhenInfected) {
                                        groupRepo[agent.groupId].numOfInfected -= 1
                                    } else {
                                        groupRepo[agent.groupId].numOfAgents += 1
                                    }
                                }
                                agent.isStayingHomeWhenInfected = false
                                household.numOfInfected -= 1
                            } else {
                                agent.daysInfected += 1
                                if (!agent.isStayingHomeWhenInfected) {
                                    agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
                                    if ((agent.isStayingHomeWhenInfected) && (agent.groupId != -1)) {
                                        groupRepo[agent.groupId].numOfInfected -= 1
                                        groupRepo[agent.groupId].numOfAgents -= 1
                                    }
                                }
                            }
                        }
                        0 -> {
                            if ((0..99999).random() == 0) {
                                agent.healthStatus = 1
                                worldStats[0] -= 1
                                worldStats[1] += 1
                                worldStats[3] += 1
                                agent.daysInfected = 1
//                                agent.shouldBeInfected = agent.willBeInfected()
                                agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()
                                household.numOfInfected += 1
                                if (agent.groupId != -1) {
                                    if (agent.isStayingHomeWhenInfected) {
                                        groupRepo[agent.groupId].numOfAgents -= 1
                                    } else {
                                        groupRepo[agent.groupId].numOfInfected += 1
                                    }
                                }
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

    fun resetState() {
        day = 1
        month = 8

        globalDay = 0
        dayOfTheWeek = 1

        tempDay = 213

        worldStats = arrayListOf(0, 0, 0, 0)

        households.forEach { household ->
            household.agents.forEach { agent ->
                agent.healthStatus = if ((0..10000).random() < 15) 1 else {
                    if (agent.age < 18) {
                        if ((1..100).random() < 33) 2 else 0
                    } else 0
                }
                agent.daysInfected = if (agent.healthStatus == 1) if (agent.age > 17 ) (1..8).random() else (1..15).random() else 0
                agent.shouldBeInfected = if (agent.age > 17 ) (max(5, agent.daysInfected)..8).random() else (max(7, agent.daysInfected)..15).random()
                agent.isStayingHomeWhenInfected = agent.shouldStayAtHome()

                when (agent.healthStatus) {
                    0 -> worldStats[0] += 1
                    1 -> worldStats[1] += 1
                    2 -> worldStats[2] += 1
                }
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