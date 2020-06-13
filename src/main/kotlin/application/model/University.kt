package application.model

// Класс, содержащий все университеты
class University {

    // Количество агентов в группах
    private fun findNumberOfPeople(groupNum: Int): Int {
        return when (groupNum) {
            0 -> when ((0..99).random()) {
                in (0..19) -> 14
                in (20..79) -> 15
                else -> 16
            }
            in (1..2) -> when ((0..99).random()) {
                in (0..19) -> 13
                in (20..79) -> 14
                else -> 15
            }
            3 -> when ((0..99).random()) {
                in (0..19) -> 12
                in (20..79) -> 13
                else -> 14
            }
            4 -> when ((0..99).random()) {
                in (0..19) -> 11
                in (20..79) -> 12
                else -> 13
            }
            else -> when ((0..99).random()) {
                in (0..19) -> 10
                in (20..79) -> 11
                else -> 12
            }
        }
    }

    // Количество агентов в последних добавленных группах
    private var currentGroupSize = arrayListOf(
        findNumberOfPeople(0),
        findNumberOfPeople(1),
        findNumberOfPeople(2),
        findNumberOfPeople(3),
        findNumberOfPeople(4),
        findNumberOfPeople(5)
    )

    // Группы по годам
    val groupsByAge = arrayListOf(
        arrayListOf<Group>(),
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
            18 -> 0
            19 -> if ((0..1).random() == 0) 0 else 1
            20 -> if ((0..1).random() == 0) 1 else 2
            21 -> if ((0..1).random() == 0) 2 else 3
            22 -> if ((0..1).random() == 0) 3 else 4
            23 -> if ((0..1).random() == 0) 4 else 5
            24 -> 5
            else -> error("Wrong age")
        }
        // Добавление группы, если отсутствует
        if (groupsByAge[groupNum].size == 0) {
            groupsByAge[groupNum].add(Group())
        }
        // Группа заполнена
        if (groupsByAge[groupNum][groupsByAge[groupNum].size - 1].agents.size == currentGroupSize[groupNum]) {
            groupsByAge[groupNum].add(Group())
            currentGroupSize[groupNum] = findNumberOfPeople(groupNum)
        }
        // Добавление агента в последнюю добавленную группу
        groupsByAge[groupNum][groupsByAge[groupNum].size - 1].addAgent(agent)
    }
}
