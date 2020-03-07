package utility

import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

fun writeTableResult(filepath: String, row: Int, stats: ArrayList<Int>) {
    try {
        val inputStream = FileInputStream(filepath)
        val xlWb = WorkbookFactory.create(inputStream)

        val xlWs = xlWb.getSheetAt(0)
        val xlRow = xlWs.createRow(row)

        stats.forEachIndexed { index, item ->
            val xlCol = xlRow.createCell(index)
            xlCol.setCellValue(item.toDouble())
        }
        val outputStream = FileOutputStream(filepath)
        xlWb.write(outputStream)
        xlWb.close()
    } catch(err: IOException) {
        println("Unable to read the file")
    }
}

fun writeTableR0(filepath: String, row: Int, stats: ArrayList<Double>) {
    try {
        val inputStream = FileInputStream(filepath)
        val xlWb = WorkbookFactory.create(inputStream)

        val xlWs = xlWb.getSheetAt(0)
        val xlRow = xlWs.createRow(row)

        stats.forEachIndexed { index, item ->
            val xlCol = xlRow.createCell(index)
            xlCol.setCellValue(item)
        }
        val outputStream = FileOutputStream(filepath)
        xlWb.write(outputStream)
        xlWb.close()
    } catch(err: IOException) {
        println("Unable to read the file")
    }
}
