import model.World
import java.io.FileInputStream
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.IOException

fun main(args: Array<String>) {
//    readTable("D:\\Dev\\Projects\\KotlinProjects\\MasterThesis\\src\\Tables\\flu.xlsx", 43, 51)
    val world = World()
    world.runSimulation()
}