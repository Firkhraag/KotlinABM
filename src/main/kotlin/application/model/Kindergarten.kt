package application.model

// Класс, содержащий все детские сады
class Kindergarten {

    // Количество детей в группах
    private fun findNumberOfPeople(groupNum: Int): Int {
        return when (groupNum) {
            0 -> when ((0..99).random()) {
                in (0..19) -> 9
                in (20..79) -> 10
                else -> 11
            }
            in (1..2) -> when ((0..99).random()) {
                in (0..19) -> 14
                in (20..79) -> 15
                else -> 16
            }
            else -> when ((0..99).random()) {
                in (0..19) -> 19
                in (20..79) -> 20
                else -> 21
            }
        }
    }

    // Количество детей в последних добавленных группах
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
            0 -> 0
            1 -> 1
            2 -> if ((0..1).random() == 0) 1 else 2
            3 -> if ((0..1).random() == 0) 2 else 3
            4 -> if ((0..1).random() == 0) 3 else 4
            5 -> if ((0..1).random() == 0) 4 else 5
            6 -> 5
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
