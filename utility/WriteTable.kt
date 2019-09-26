package utility

import model.Agent
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

//fun writeTableResult(filepath: String, row: Int, stats: ArrayList<Double>) {
//    try {
//        //Instantiate Excel workbook:
//        val xlWb = XSSFWorkbook()
//        //Instantiate Excel worksheet:
//        val xlWs = xlWb.createSheet()
//
//        //Cell index specifies the column within the chosen row (starting at 0):
////        val columnNumber = 0
//        //Write text value to cell located at ROW_NUMBER / COLUMN_NUMBER:
//        val xlRow = xlWs.createRow(row)
////        var xlCol = xlRow.createCell(columnNumber)
//        stats.forEachIndexed { index, item ->
//            val xlCol = xlRow.createCell(index)
//            xlCol.setCellValue(item)
//        }
////        xlCol.setCellValue("Chercher Tech")
//        //Write file:
//        val outputStream = FileOutputStream(filepath)
//        xlWb.write(outputStream)
//        xlWb.close()
//    } catch(err: IOException) {
//        println("Unable to read the file")
//    }
//}

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
