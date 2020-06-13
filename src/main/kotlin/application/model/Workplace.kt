package application.model

import application.utility.generateBarabasiAlbertNetworkForWork

// Класс, содержащий все рабочие коллективы
class Workplace {

    // Минимальный размер фирмы
    private val minFirmSize = 6
    // Максимальный размер фирмы
    private val maxFirmSize = 2500 - minFirmSize

    // Индексы не полностью заполненных фирм
    private val indexesOfWorkplacesToFill = arrayListOf<Int>()

    // Создание графа Барабаши-Альберт для последних добавленных фирм
    fun generateLastBarabasiAlbertNetworks() {
        for (i in indexesOfWorkplacesToFill) {
            if (workingGroups[i].agents.size == 0) {
                // Агентов нет
                continue
            }
            if (workingGroups[i].agents.size >= minFirmSize) {
                generateBarabasiAlbertNetworkForWork(workingGroups[i], minFirmSize)
            } else {
                // Число агентов меньше, чем минимальный размер фирмы
                generateBarabasiAlbertNetworkForWork(workingGroups[i], workingGroups[i].agents.size)
            }
        }
    }

    // Распределение по закону Ципфа
    private val zipfDistribution = org.apache.commons.math3.distribution.ZipfDistribution(maxFirmSize, 1.059)
    // Размеры фирм
    private val groupSizes = arrayListOf<Int>()
    // Массив фирм
    val workingGroups = arrayListOf<Group>()

    // В сколько фирм добавляем агентов
    private val batchSize = 100

    // Добавить агента
    fun addAgent(agent: Agent) {
        // Начальный набор фирм
        if (workingGroups.size == 0) {
            // Добавление новых фирм
            for (i in (0 until batchSize)) {
                workingGroups.add(Group())
                indexesOfWorkplacesToFill.add(workingGroups.size - 1)
                groupSizes.add(zipfDistribution.sample() + (minFirmSize - 1))
            }
        }
        // Выбираем случайную фирму
        val randomWorkplaceIndex = indexesOfWorkplacesToFill.random()
        // Добавляем агента
        workingGroups[randomWorkplaceIndex].addAgent(agent)
        // Если заполнили
        if (workingGroups[randomWorkplaceIndex].agents.size == groupSizes[randomWorkplaceIndex]) {
            generateBarabasiAlbertNetworkForWork(workingGroups[randomWorkplaceIndex], minFirmSize)
            // Убираем из массива фирм, нуждающихся в добавлении агентов
            indexesOfWorkplacesToFill.remove(randomWorkplaceIndex)
            // Добавляем новую фирму
            workingGroups.add(Group())
            indexesOfWorkplacesToFill.add(workingGroups.size - 1)
            groupSizes.add(zipfDistribution.sample() + (minFirmSize - 1))
        }
    }
}
