package com.example.servicemanager.feature_home_domain.use_cases

import com.example.core.util.MapperExtensionFunction.mapToObject
import com.example.core.util.Resource
import com.example.servicemanager.feature_inspections_domain.model.Inspection
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
        emit(
            Resource(
                ResourceState.LOADING,
                null,
                UiText.StringResource(R.string.importing_initiatialization))
        )
        val listOfKeys = Inspection.listOfProperties
        val mapOfData: MutableMap<String, String> = mutableMapOf<String, String>()
        val data = mutableListOf<Inspection>()
         try {
            inputStream.use { fileInputStream ->
                val workbook = XSSFWorkbook(fileInputStream)
                val sheet = workbook.getSheetAt(0)
                for (i in 1 .. sheet.physicalNumberOfRows - 1) {
                    val row = sheet.getRow(i)
                    val cellIterator = row.cellIterator()
                    if (cellIterator.hasNext()) {
                        for (j in 0..listOfKeys.size-1){
                            val cellValue = getStringCellValue(cellIterator.next())
                            mapOfData.set(listOfKeys[j], cellValue)
                        }
                        val inspection = mapToObject(mapOfData, Inspection::class).copy(inspectionId = "0")
                        data.add(inspection)
                        emit(
                            Resource(
                                ResourceState.LOADING,
                                data,
                                UiText.StringResource(R.string.found_records)
                            )
                        )
                    }
                }
                workbook.close()
                fileInputStream.close()
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        data,
                        UiText.StringResource(R.string.found_records))
                    )
            }
         } catch (e: Exception) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    data,
                    UiText.StringResource(R.string.wrong_file_format)
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
