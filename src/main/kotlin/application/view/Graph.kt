package application.view

import application.controller.MyController
import javafx.scene.chart.*
import tornadofx.*

class Graph : View("Графики") {

    private val controller: MyController by inject()

    override val root = stackpane {
        val xAxis1 = CategoryAxis()
        val yAxis1 = NumberAxis()
        xAxis1.label = "День"
        xAxis1.animated = false
        yAxis1.label = "Человек"
        yAxis1.animated = false
        val chart1 = AreaChart(xAxis1, yAxis1)
        chart1.styleClass.add("graph1")
        chart1.title = "Число активных больных"
        chart1.animated = false
        chart1.isLegendVisible = false
        chart1.data.add(controller.series1)

        val xAxis2 = CategoryAxis()
        val yAxis2 = NumberAxis()
        xAxis2.label = "День"
        xAxis2.animated = false
        yAxis2.label = "Человек"
        yAxis2.animated = false
        val chart2 = AreaChart(xAxis2, yAxis2)
        chart2.styleClass.add("graph2")
        chart2.title = "Число новых случаев"
        chart2.animated = false
        chart2.isLegendVisible = false

        chart2.data.add(controller.series2)

        vbox {
            add(chart1)
            add(chart2)
//            label("Число больных: 3000")
        }.apply {
            this.spacing = 10.0
        }

    }
}