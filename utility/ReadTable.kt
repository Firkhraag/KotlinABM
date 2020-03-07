package utility

import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.io.IOException

fun readTableDouble(filepath: String, numOfRows: Int, numOfCols: Int, array: ArrayList<ArrayList<Double>>) {
    try {
        val inputStream = FileInputStream(filepath)
        val xlWb = WorkbookFactory.create(inputStream)

        val table = xlWb.getSheetAt(0)

        for (rowNumber in 0..numOfRows) {
            array.add(arrayListOf())
            for (colNumber in 0..numOfCols)
                array[rowNumber].add(table.getRow(rowNumber).getCell(colNumber).numericCellValue)
        }
    } catch(err: IOException) {
        println("Unable to read the file")
    }
}

fun readTableInt(filepath: String, numOfRows: Int, numOfCols: Int, array: ArrayList<ArrayList<Int>>) {
    try {
        val inputStream = FileInputStream(filepath)
        val xlWb = WorkbookFactory.create(inputStream)

        val table = xlWb.getSheetAt(0)

        for (rowNumber in 1..numOfRows) {
            array.add(arrayListOf())
            for (colNumber in 0..numOfCols)
                array[rowNumber - 1].add(table.getRow(rowNumber).getCell(colNumber).numericCellValue.toInt())
        }
    } catch(err: IOException) {
        println("Unable to read the file")
    }
}
