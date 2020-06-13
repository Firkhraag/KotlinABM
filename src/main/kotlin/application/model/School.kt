package application.model

// Класс, содержащий все школы
class School {

    // Количество детей в группах
    private fun findNumberOfPeople(): Int {
        return when ((0..99).random()) {
            in (0..19) -> 24
            in (20..79) -> 25
            else -> 26
        }
    }

    // Количество детей в последних добавленных группах
    private var currentGroupSize = arrayListOf(
        findNumberOfPeople(),
        findNumberOfPeople(),
        findNumberOfPeople(),
        findNumberOfPeople(),
        findNumberOfPeople(),
        findNumberOfPeople(),
        findNumberOfPeople(),
        findNumberOfPeople(),
        findNumberOfPeople(),
        findNumberOfPeople(),
        findNumberOfPeople()
    )

    // Группы по годам
    val groupsByAge = arrayListOf(
        arrayListOf<Group>(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf(),
        arrayListOf()
    )

    // Добавить агента
    fun addAgent(agent: Agent) {
        // Выбор группы по возрасту
        val groupNum = when (agent.age) {
            7 -> 0
            8 -> if ((0..1).random() == 0) 0 else 1
            9 -> if ((0..1).random() == 0) 1 else 2
            10 -> if ((0..1).random() == 0) 2 else 3
            11 -> if ((0..1).random() == 0) 3 else 4
            12 -> if ((0..1).random() == 0) 4 else 5
            13 -> if ((0..1).random() == 0) 5 else 6
            14 -> if ((0..1).random() == 0) 6 else 7
            15 -> if ((0..1).random() == 0) 7 else 8
            16 -> if ((0..1).random() == 0) 8 else 9
            17 -> if ((0..1).random() == 0) 9 else 10
            18 -> 10
            else -> error("Wrong age")
        }
        // Добавление группы, если отсутствует
        if (groupsByAge[groupNum].size == 0) {
            groupsByAge[groupNum].add(Group())
        }
        // Группа заполнена
        if (groupsByAge[groupNum][groupsByAge[groupNum].size - 1].agents.size == currentGroupSize[groupNum]) {
            groupsByAge[groupNum].add(Group())
            currentGroupSize[groupNum] = findNumberOfPeople()
        }
        // Добавление агента в последнюю добавленную группу
        groupsByAge[groupNum][groupsByAge[groupNum].size - 1].addAgent(agent)
    }
}
