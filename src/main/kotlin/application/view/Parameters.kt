package application.view

import application.controller.MyController
import javafx.application.Platform
import javafx.concurrent.Task
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.paint.Color
import tornadofx.*

class Parameters : View("Параметры") {

    private val controller: MyController by inject()

    override val root = stackpane {

        val statusLabel = Label()
        statusLabel.minWidth = 250.0
        statusLabel.textFill = Color.BLUE

        val progressBar = progressbar(0.0).apply {
            managedProperty().bind(visibleProperty())
            visibleProperty().bind(controller.showProgressBar)
            styleClass.add("progress")
        }

        val runLabel = label("Создание популяции").apply {
            managedProperty().bind(visibleProperty())
            visibleProperty().bind(controller.showRunLabel)
            textProperty().bindBidirectional(controller.runLabelText)
            styleClass.add("progress-label")
        }

        vbox {
            hbox {
                vbox {
                    hbox {
                        label("Влияние продолжительности контакта")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.durationInfluenceTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 10.0
                    }

                    hbox {
                        label("Влияние восприимчивости (FluA)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.susceptibilityInfluenceFluATextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 45.0
                    }

                    hbox {
                        label("Влияние восприимчивости (FluB)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.susceptibilityInfluenceFluBTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 45.0
                    }

                    hbox {
                        label("Влияние восприимчивости (RV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.susceptibilityInfluenceRVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 54.0
                    }

                    hbox {
                        label("Влияние восприимчивости (RSV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.susceptibilityInfluenceRSVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 46.5
                    }

                    hbox {
                        label("Влияние восприимчивости (AdV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.susceptibilityInfluenceAdVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 46.5
                    }

                    hbox {
                        label("Влияние восприимчивости (PIV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.susceptibilityInfluencePIVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 52.0
                    }

                    hbox {
                        label("Влияние восприимчивости (CoV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.susceptibilityInfluenceCoVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 46.5
                    }
                }.apply {
                    managedProperty().bind(visibleProperty())
                    alignment = Pos.CENTER
                    spacing = 15.0
                }

                vbox {
                    hbox {
                        label("Влияние температуры воздуха (FluA)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.temperatureInfluenceFluATextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 10.0
                    }

                    hbox {
                        label("Влияние температуры воздуха (FluB)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.temperatureInfluenceFluBTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 10.0
                    }

                    hbox {
                        label("Влияние температуры воздуха (RV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.temperatureInfluenceRVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 20.0
                    }

                    hbox {
                        label("Влияние температуры воздуха (RSV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.temperatureInfluenceRSVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 13.0
                    }

                    hbox {
                        label("Влияние температуры воздуха (AdV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.temperatureInfluenceAdVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 11.0
                    }

                    hbox {
                        label("Влияние температуры воздуха (PIV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.temperatureInfluencePIVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 16.5
                    }

                    hbox {
                        label("Влияние температуры воздуха (CoV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.temperatureInfluenceCoVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 10.0
                    }
                }.apply {
                    managedProperty().bind(visibleProperty())
                    alignment = Pos.CENTER
                    spacing = 15.0
                }

                vbox {
                    hbox {
                        label("Продолжительность типоспецифического иммунитета (FluA)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.immunityDurationFluATextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 10.0
                    }

                    hbox {
                        label("Продолжительность типоспецифического иммунитета (FluB)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.immunityDurationFluBTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 10.0
                    }

                    hbox {
                        label("Продолжительность типоспецифического иммунитета (RV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.immunityDurationRVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 20.0
                    }

                    hbox {
                        label("Продолжительность типоспецифического иммунитета (RSV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.immunityDurationRSVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 13.0
                    }

                    hbox {
                        label("Продолжительность типоспецифического иммунитета (AdV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.immunityDurationAdVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 11.0
                    }

                    hbox {
                        label("Продолжительность типоспецифического иммунитета (PIV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.immunityDurationPIVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 16.5
                    }

                    hbox {
                        label("Продолжительность типоспецифического иммунитета (CoV)")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.immunityDurationCoVTextField)
                        }
                    }.apply {
                        alignment = Pos.CENTER_LEFT
                        spacing = 10.0
                    }

                    hbox {
                        label("Количество прогонов")
                        textfield().apply {
                            disableProperty().bind(controller.started)
                            textProperty().bindBidirectional(controller.numberOfRunsTextField)
                        }
                    }.apply {
                        managedProperty().bind(visibleProperty())
                        alignment = Pos.CENTER_LEFT
                        spacing = 260.0
                    }

                }.apply {
                    managedProperty().bind(visibleProperty())
                    alignment = Pos.CENTER
                    spacing = 15.0
                }
            }.apply {
                spacing = 50.0
            }

            button("Старт").apply {
                disableProperty().bind(controller.started)
                styleClass.add("start-btn")
            }.setOnAction {
                controller.started.set(true)
                controller.showProgressBar.set(true)
                controller.showRunLabel.set(true)

                val task = object : Task<Boolean?>() {
                    override fun call(): Boolean? {
                        controller.createPopulation()
                        controller.showProgressBar.set(false)
                        Platform.runLater {
                            controller.runLabelText.set("Прогон: 1/${controller.numberOfRunsTextField.get()}")
                            controller.dateLabelText.set("1 Марта")
                        }
                        controller.runSimulation()
                        return true
                    }
                }
                progressBar.progressProperty().bind(controller.progress)
                Thread(task).start()
            }
            vbox {
                add(runLabel)
                add(progressBar)
            }.apply {
                spacing = 20.0
                alignment = Pos.CENTER
            }
        }.apply {
            spacing = 33.0
            padding = Insets(40.0, 20.0, 40.0, 20.0)
            alignment = Pos.TOP_CENTER
        }
    }
}
