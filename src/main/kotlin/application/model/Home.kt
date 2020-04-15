package application.model

class Home(val pos: Pair<Double, Double>, val okato: Int,
           val closestMetroIndex: Int, val closestKindergartenIndex: Int,
           val closestSchoolIndex: Int) {
    var numberOfInfected = 0
}
