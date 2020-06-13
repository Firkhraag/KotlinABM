package application.controller

import application.model.World
import application.model.setParameters
import javafx.application.Platform
import javafx.beans.property.ReadOnlyDoubleWrapper
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.chart.XYChart
import tornadofx.Controller

// Контроллер для связи View с Model
open class MyController : Controller() {

    // Модель
    private lateinit var world: World
    // Популяция инициализирована
    private var populationIsCreated = false

    // Модель работает
    val started = SimpleBooleanProperty(false)
    // Показывать прогресс по созданию домохозяйств
    val showProgressBar = SimpleBooleanProperty(false)
    val showRunLabel = SimpleBooleanProperty(false)

    val runLabelText = SimpleStringProperty("Создание популяции")

    // Массивы данных для отображения на графиках
    val series1 = XYChart.Series<String, Number>()
    val series2 = XYChart.Series<String, Number>()
    val series3 = XYChart.Series<String, Number>()
    val series4 = XYChart.Series<String, Number>()

    val series1Real = XYChart.Series<String, Number>()
    val series2Real = XYChart.Series<String, Number>()
    val series3Real = XYChart.Series<String, Number>()
    val series4Real = XYChart.Series<String, Number>()

    // Дата
    val dateLabelText = SimpleStringProperty("День Месяц")
    // Прогресс по созданию домохозяйств
    val progress = ReadOnlyDoubleWrapper()

    // Параметры
    val durationInfluenceTextField = SimpleStringProperty("7.05")
    val susceptibilityInfluenceFluATextField = SimpleStringProperty("2.61")
    val susceptibilityInfluenceFluBTextField = SimpleStringProperty("2.61")
    val susceptibilityInfluenceRVTextField = SimpleStringProperty("3.17")
    val susceptibilityInfluenceRSVTextField = SimpleStringProperty("5.11")
    val susceptibilityInfluenceAdVTextField = SimpleStringProperty("4.69")
    val susceptibilityInfluencePIVTextField = SimpleStringProperty("3.89")
    val susceptibilityInfluenceCoVTextField = SimpleStringProperty("3.77")

    val temperatureInfluenceFluATextField = SimpleStringProperty("-0.8")
    val temperatureInfluenceFluBTextField = SimpleStringProperty("-0.8")
    val temperatureInfluenceRVTextField = SimpleStringProperty("-0.05")
    val temperatureInfluenceRSVTextField = SimpleStringProperty("-0.64")
    val temperatureInfluenceAdVTextField = SimpleStringProperty("-0.2")
    val temperatureInfluencePIVTextField = SimpleStringProperty("-0.05")
    val temperatureInfluenceCoVTextField = SimpleStringProperty("-0.8")

    val immunityDurationFluATextField = SimpleStringProperty("365")
    val immunityDurationFluBTextField = SimpleStringProperty("365")
    val immunityDurationRVTextField = SimpleStringProperty("60")
    val immunityDurationRSVTextField = SimpleStringProperty("60")
    val immunityDurationAdVTextField = SimpleStringProperty("365")
    val immunityDurationPIVTextField = SimpleStringProperty("365")
    val immunityDurationCoVTextField = SimpleStringProperty("365")

    val numberOfRunsTextField = SimpleStringProperty("1")

    // Инициализация
    fun createPopulation() {
        setParameters(
            durationInfluenceTextField.get().toDouble(),
            susceptibilityInfluenceFluATextField.get().toDouble(),
            susceptibilityInfluenceFluBTextField.get().toDouble(),
            susceptibilityInfluenceRVTextField.get().toDouble(),
            susceptibilityInfluenceRSVTextField.get().toDouble(),
            susceptibilityInfluenceAdVTextField.get().toDouble(),
            susceptibilityInfluencePIVTextField.get().toDouble(),
            susceptibilityInfluenceCoVTextField.get().toDouble(),

            temperatureInfluenceFluATextField.get().toDouble(),
            temperatureInfluenceFluBTextField.get().toDouble(),
            temperatureInfluenceRVTextField.get().toDouble(),
            temperatureInfluenceRSVTextField.get().toDouble(),
            temperatureInfluenceAdVTextField.get().toDouble(),
            temperatureInfluencePIVTextField.get().toDouble(),
            temperatureInfluenceCoVTextField.get().toDouble(),

            immunityDurationFluATextField.get().toInt(),
            immunityDurationFluBTextField.get().toInt(),
            immunityDurationRVTextField.get().toInt(),
            immunityDurationRSVTextField.get().toInt(),
            immunityDurationAdVTextField.get().toInt(),
            immunityDurationPIVTextField.get().toInt(),
            immunityDurationCoVTextField.get().toInt()
        )
        if (!populationIsCreated) {
            world = World(progress)
            populationIsCreated = true
        } else {
            world.restartWorld()
            Platform.runLater {
                series1.data.clear()
                series2.data.clear()
                series3.data.clear()
                series4.data.clear()
                series1Real.data.clear()
                series2Real.data.clear()
                series3Real.data.clear()
                series4Real.data.clear()
            }
        }
    }

    // Симуляция
    fun runSimulation() {
        for (i in (1..numberOfRunsTextField.get().toInt())) {
            world.runSimulation(i, series1, series2, series3, series4,
                series1Real, series2Real, series3Real, series4Real, dateLabelText
            )
            world.restartWorld()
        }
        started.set(false)
        Platform.runLater {
            showRunLabel.set(false)
        }
    }
}
