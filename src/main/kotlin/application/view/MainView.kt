package application.view

import application.controller.MyController
import javafx.scene.control.*
import javafx.scene.layout.Priority
import tornadofx.*
import kotlin.system.exitProcess

class MainView : View("Агентная модель ОРЗ / COVID-19") {

    private val controller: MyController by inject()

    override val root = borderpane {
        minWidth = 1000.0
        minHeight = 780.0
        vgrow = Priority.ALWAYS
        hgrow = Priority.ALWAYS

        val dateLabel = Label()
        dateLabel.textProperty().bind(controller.dateLabelText)
        dateLabel.styleClass.add("date-label")

        top = stackpane {
            add(dateLabel)
        }
        center = tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            tab(Parameters::class)
            tab(Graph::class)
//            tab(Canvas::class)
        }
    }.also { primaryStage.setOnCloseRequest { exitProcess(0) } }
}
