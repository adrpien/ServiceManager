package com.example.servicemanager.feature_home_domain.use_cases

import com.example.core.util.MapperExtensionFunction.mapToObject
import com.example.core.util.Resource
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.core.util.MapperExtensionFunction.toMap
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_home_domain.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.InputStream

import javax.inject.Inject

class ImportInspectionsFromFile @Inject constructor () {
    operator fun invoke(inputStream: InputStream): Flow<Resource<List<Inspection>>> = flow {
        val data = mutableListOf<Inspection>()
         try {
            inputStream.use { fileInputStream ->
                val workbook = XSSFWorkbook(fileInputStream)
                val sheet = workbook.getSheetAt(0)
                for(i in 1 until sheet.physicalNumberOfRows){
                    val row = sheet.getRow(i)
                    val cellIterator = row.cellIterator()
                    val mapOfData: MutableMap<String, String> = mutableMapOf<String, String>()
                    val listOfKeys = Inspection().toMap().keys.toList()
                    for (i in 0..listOfKeys.size-1){
                        val cellValue = getStringCellValue(cellIterator.next())
                        mapOfData.set(listOfKeys.get(i), cellValue)
                    }
                    val inspection = mapToObject(mapOfData, Inspection::class)
                    data.add(inspection)
                }
                workbook.close()
                fileInputStream.close()
                val listSize: Int = data.size
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        data,
                        UiText.StringResource(R.string.found_records, listOf(listSize.toString()))
                    )

                )

            }
         } catch (e: Exception) {
            // TODO ImportInspectionsFromFile error handling (SnackBar or something)
            emit(
                Resource(
                    ResourceState.ERROR,
                    data,
                    UiText.StringResource(R.string.unknown_error)
                )
            )
        }
    }
}

private fun getStringCellValue(cell: Cell): String {
    return when (cell.cellType) {
        CellType.STRING -> cell.stringCellValue
        CellType.NUMERIC -> cell.numericCellValue.toString()
        else -> ""
    }
}
