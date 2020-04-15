package application.controller

import application.model.World
import javafx.beans.property.ReadOnlyDoubleWrapper
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.chart.XYChart
import tornadofx.Controller

open class MyController : Controller() {

    val progress = ReadOnlyDoubleWrapper()

    private lateinit var world: World

    val started = SimpleBooleanProperty(false)
    val showProgressBar = SimpleBooleanProperty(false)
    val series1 = XYChart.Series<String, Number>()
    val series2 = XYChart.Series<String, Number>()
    val dateLabelText = SimpleStringProperty("День Месяц")

    val durationTextField = SimpleStringProperty("8.0")
    val susceptibilityFluATextField = SimpleStringProperty("2.55")
    val susceptibilityFluBTextField = SimpleStringProperty("2.63")
    val susceptibilityRVTextField = SimpleStringProperty("3.74")
    val susceptibilityRSVTextField = SimpleStringProperty("5.1")
    val susceptibilityAdVTextField = SimpleStringProperty("5.32")
    val susceptibilityPIVTextField = SimpleStringProperty("4.73")
    val susceptibilityCoVTextField = SimpleStringProperty("4.15")

    val temperatureFluATextField = SimpleStringProperty("-0.82")
    val temperatureFluBTextField = SimpleStringProperty("-0.91")
    val temperatureRVTextField = SimpleStringProperty("-0.01")
    val temperatureRSVTextField = SimpleStringProperty("-0.56")
    val temperatureAdVTextField = SimpleStringProperty("-0.22")
    val temperaturePIVTextField = SimpleStringProperty("-0.06")
    val temperatureCoVTextField = SimpleStringProperty("-0.51")

    val immunityFluATextField = SimpleStringProperty("365")
    val immunityFluBTextField = SimpleStringProperty("365")
    val immunityRVTextField = SimpleStringProperty("60")
    val immunityRSVTextField = SimpleStringProperty("60")
    val immunityAdVTextField = SimpleStringProperty("90")
    val immunityPIVTextField = SimpleStringProperty("90")
    val immunityCoVTextField = SimpleStringProperty("90")

    fun createPopulation() {
        world = World(progress)
    }

    fun runSimulation() {
        // Data
        val results0 = arrayListOf(0.23610585838068632, 0.25355567515258814, 0.26545148817503833, 0.2818010863612463,
            0.3215856696604859, 0.3539574971427314, 0.4405909170631226, 0.5602137737928882, 0.7242059649063424,
            0.734836523905189, 0.787265191781839, 0.795590487158219, 0.8399941918235238, 0.8577236606849298,
            0.8835784350745373, 0.7898439360883801, 0.7961301685245058, 0.8248429636427632, 0.8800230405677681,
            0.884346494854126, 0.8088644769184086, 0.8375944380090069, 0.8197543413469003, 0.6544981832255639,
            0.7706613037723964, 0.7344318829800942, 0.7779106910184048, 0.8438926257589563, 0.8246722945827205,
            0.8079849634338866, 0.7493528060882022, 0.7126774273065305, 0.607091417240455, 0.6666219762574301,
            0.6719935155296791, 0.7073727027825057, 0.6789760913021142, 0.6750316877333427, 0.7022763212630868,
            0.7132370043532311, 0.5434644109053163, 0.5014796215794557, 0.5422711005143377, 0.5379350862432271,
            0.5373960860514143, 0.4857891537764303, 0.3872108424910912, 0.3439828873770691, 0.31386969991652336,
            0.25277271097334686, 0.23223896264146843, 0.2210777687242387)
        val results3 = arrayListOf(0.1793542469296102, 0.19877739884252665, 0.2014162800707572, 0.22029381970008663,
            0.24795351738389437, 0.3003897740277566, 0.3977423628948179, 0.610464247639273, 0.8276874110311071,
            0.8860143119781273, 0.9992057709325156, 1.0870862976285571, 1.1597458819480302, 1.1953618062282798,
            1.2187304545264521, 1.0123639912758604, 0.944358613918544, 1.0144485235920384, 1.068635268398418,
            1.049793015667435, 0.8727319708354189, 0.8821243303912101, 0.8745437379112027, 0.6336832784759767,
            0.7792451202607983, 0.8795738219743214, 1.0068123491085539, 1.1007229531225655, 1.1269340308222215,
            1.0769868160640086, 0.9861582403702536, 0.9224162683529177, 0.7549655287501477, 0.7994649742889326,
            0.8869072442806989, 0.9128491708840843, 0.8901314527129843, 0.8996703002836323, 0.9074391657617134,
            0.8820264882117508, 0.5821930098164864, 0.47235167183432514, 0.5505041877375123, 0.5834243051063417,
            0.5648564895293917, 0.4567902907038844, 0.3152777859352105, 0.27202893240559145, 0.24563349932560277,
            0.1876497058353123, 0.1725728439385491, 0.16371341365253575)
        val results7 = arrayListOf(0.1739077892460305, 0.19434485691097989, 0.2205627064852913, 0.22469189545539092,
            0.26076490597675855, 0.3533157660270398, 0.6023097389052368, 1.172556439747432, 1.5388533877125983,
            1.518824484840041, 1.5621730419119368, 1.5801480099581044, 1.6528892895659741, 1.701188174059928,
            1.6023358727781192, 0.9508934685995748, 1.064058710235829, 1.3233310455103373, 1.6180509620848917,
            1.7718363253921143, 1.449192606074993, 1.524690286318128, 1.389331383517864, 0.6384364551086766,
            1.029259961379441, 1.6641693849219146, 2.1329442226825375, 2.423193352276763, 2.5002990128174902,
            2.2955211639376, 2.013452912538246, 1.7888747732642416, 1.3656306100889863, 1.550663760670001,
            1.299986191601201, 0.9451569106819531, 1.0217755178356482, 1.2251554495017216, 1.3747529445736526,
            1.305434799705655, 0.8033118913671782, 0.6584386158057853, 0.84773593413391, 0.7565064545983979,
            0.5170588236144487, 0.3100939867503764, 0.20187684608279535, 0.1935879100187239, 0.19943212310714828,
            0.1638107816952672, 0.14295801978976627, 0.152049710856963)
        val results15 = arrayListOf(0.7966841256774086, 0.8068869898790005, 0.8554968885929622, 0.9634166456263183,
            1.071788810925344, 1.2298651440630892, 1.377363582810348, 1.9138318115673334, 2.5252218186962416,
            2.6478512802961682, 2.617024671991088, 2.482270647731042, 2.546450921917047, 2.7921654928211552,
            2.614027226723293, 2.0703412109150614, 2.288213335495086, 2.5539461869331537, 2.8339467275722745,
            2.9109771089044894, 2.3783175393116123, 2.826074366531262, 2.8714299781843993, 1.992967523723446,
            3.1106295224945733, 4.442950087320171, 4.490271552134618, 5.268650144843063, 5.588884269843217,
            5.593916035113105, 4.824763053282847, 4.179071515010256, 2.942314730437087, 3.237099189928985,
            2.986669848265976, 2.8867019773797136, 2.7205481416271446, 2.5307500635553053, 2.4808454304410774,
            2.9793997859278525, 1.2722869392133096, 1.2423252691839775, 1.745432720754166, 1.6766145781120134,
            1.6327823746138892, 1.4046020327007762, 1.0328035321299693, 1.1315357525718077, 1.058861881030632,
            0.9818320560347648, 0.781878834841036, 0.772657081221479)

        var bMap = mapOf("fluA" to susceptibilityFluATextField.get().toDouble(),
            "fluB" to susceptibilityFluBTextField.get().toDouble(),
            "RV" to susceptibilityRVTextField.get().toDouble(),
            "RSV" to susceptibilityRSVTextField.get().toDouble(),
            "AdV" to susceptibilityAdVTextField.get().toDouble(),
            "PIV" to susceptibilityPIVTextField.get().toDouble(),
            "CoV" to susceptibilityCoVTextField.get().toDouble())

        var tMap = mapOf("fluA" to temperatureFluATextField.get().toDouble(),
            "fluB" to temperatureFluBTextField.get().toDouble(),
            "RV" to temperatureRVTextField.get().toDouble(),
            "RSV" to temperatureRSVTextField.get().toDouble(),
            "AdV" to temperatureAdVTextField.get().toDouble(),
            "PIV" to temperaturePIVTextField.get().toDouble(),
            "CoV" to temperatureCoVTextField.get().toDouble())

        var imMap = mapOf("fluA" to immunityFluATextField.get().toInt(),
            "fluB" to immunityFluBTextField.get().toInt(),
            "RV" to immunityRVTextField.get().toInt(),
            "RSV" to immunityRSVTextField.get().toInt(),
            "AdV" to immunityAdVTextField.get().toInt(),
            "PIV" to immunityPIVTextField.get().toInt(),
            "CoV" to immunityCoVTextField.get().toInt())

        for (numOfIter in (1..1)) {
            // Run simulation
            val worldStats = world.runSimulation(durationTextField.get().toDouble(), bMap, tMap, imMap,
                numOfIter, series1, series2, dateLabelText)
            // Sum of RSS for each age group
            var error = 0.0
            for (i in (0..51)) {
                error += (worldStats[i][0] / 9863.0 - results0[i]) * (worldStats[i][0] / 9863.0 - results0[i])
                error += (worldStats[i][1] / 9863.0 - results3[i]) * (worldStats[i][1] / 9863.0 - results3[i])
                error += (worldStats[i][2] / 9863.0 - results7[i]) * (worldStats[i][2] / 9863.0 - results7[i])
                error += (worldStats[i][3] / 9863.0 - results15[i]) * (worldStats[i][3] / 9863.0 - results15[i])
            }
            println("Iteration: $numOfIter")
            println("Error: $error")
//            world.restartWorld()
        }
    }
}