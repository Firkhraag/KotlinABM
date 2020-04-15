package application.view

import application.controller.MyController
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.concurrent.Task
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.paint.Color
import tornadofx.*

class Parameters : View("Параметры") {

    private val controller: MyController by inject()
    private var buttonText = SimpleStringProperty("Старт")

    override val root = borderpane {

        val statusLabel = Label()
        statusLabel.minWidth = 250.0
        statusLabel.textFill = Color.BLUE

        val progressBar = ProgressBar(0.0)
        progressBar.managedProperty().bind(visibleProperty())
        progressBar.visibleProperty().bind(controller.showProgressBar)
        progressBar.styleClass.add("progress")

        center = vbox {
            vbox {
                label("Коэффициент продолжительности")
                textfield().apply {
                    disableProperty().bind(controller.started)
                    textProperty().bind(controller.durationTextField)
                }
            }.apply {
                alignment = Pos.CENTER
                spacing = 5.0
            }

            label("Коэффициенты восприимчивости").apply {
                managedProperty().bind(visibleProperty())
            }
            hbox {
                vbox {
                    label("FluA")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.susceptibilityFluATextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("FluB")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.susceptibilityFluBTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("RV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.susceptibilityRVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("RSV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.susceptibilityRSVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("AdV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.susceptibilityAdVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("PIV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.susceptibilityPIVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("CoV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.susceptibilityCoVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
            }.apply {
                managedProperty().bind(visibleProperty())
                alignment = Pos.CENTER
                spacing = 20.0
            }

            label("Коэффициенты температуры воздуха").apply {
                managedProperty().bind(visibleProperty())
            }
            hbox {
                vbox {
                    label("FluA")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.temperatureFluATextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("FluB")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.temperatureFluBTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("RV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.temperatureRVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("RSV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.temperatureRSVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("AdV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.temperatureAdVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("PIV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.temperaturePIVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("CoV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.temperatureCoVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
            }.apply {
                managedProperty().bind(visibleProperty())
                alignment = Pos.CENTER
                spacing = 20.0
            }

            label("Продолжительности типоспецифического иммунитета").apply {
                managedProperty().bind(visibleProperty())
            }
            hbox {
                vbox {
                    label("FluA")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.immunityFluATextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("FluB")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.immunityFluBTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("RV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.immunityRVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("RSV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.immunityRSVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("AdV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.immunityAdVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("PIV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.immunityPIVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
                vbox {
                    label("CoV")
                    textfield().apply {
                        disableProperty().bind(controller.started)
                        textProperty().bind(controller.immunityCoVTextField)
                    }
                }.apply {
                    spacing = 5.0
                }
            }.apply {
                managedProperty().bind(visibleProperty())
                alignment = Pos.CENTER
                spacing = 20.0
            }

            button(buttonText).apply {
                disableProperty().bind(controller.started)
            }.setOnAction {
                controller.started.set(true)
                controller.showProgressBar.set(true)

                val task = object : Task<Boolean?>() {
                    override fun call(): Boolean? {
                        controller.createPopulation()
                        controller.showProgressBar.set(false)
                        Platform.runLater {
                            controller.dateLabelText.set("1 Августа")
                        }
                        controller.runSimulation()
                        return true
                    }
                }
                progressBar.progressProperty().bind(controller.progress)
                Thread(task).start()
            }
            hbox {
                label("Создание популяции:").apply {
                    managedProperty().bind(visibleProperty())
                    visibleProperty().bind(controller.showProgressBar)
                }
                add(progressBar)
            }.apply {
                spacing = 20.0
                alignment = Pos.CENTER
            }
        }.apply {
            spacing = 20.0
            alignment = Pos.CENTER
        }
    }
}