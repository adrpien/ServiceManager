package com.example.servicemanager.feature_home_domain.use_cases

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import com.example.core.util.MapperExtensionFunction.mapToObject
import com.example.core.util.Resource
import com.example.servicemanager.feature_inspections_domain.model.Inspection
import com.example.core.util.MapperExtensionFunction.toMap
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_home_domain.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream

import javax.inject.Inject

class ImportInspectionsFromFile @Inject constructor () {
    operator fun invoke(file: File): Flow<Resource<List<Inspection>>> = flow {
        val data = mutableListOf<Inspection>()
        // try {
            FileInputStream(file).use { fileInputStream ->
                val workbook = XSSFWorkbook(fileInputStream)
                // val workbook = WorkbookFactory.create(fileInputStream)
                val sheet = workbook.getSheetAt(0)

                for (row in sheet){
                    val cellIterator = row.cellIterator()
                    val mapOfData: MutableMap<String, String> = mutableMapOf<String, String>()
                    val listOfKeys = Inspection().toMap().keys.toList()
                    for (i in 0..listOfKeys.size){
                        val cellValue = getStringCellValue(cellIterator.next())
                        mapOfData.set(listOfKeys.get(i), cellValue)
                    }
                    val inspection = mapToObject(mapOfData, Inspection::class)
                    data.add(inspection)
                }
                workbook.close()
                fileInputStream.close()
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        data,
                        UiText.StringResource(R.string.inspection_import_from_file_error)
                    )
                )

            }
        // } catch (e: Exception) {
            // TODO ImportInspectionsFromFile error handling (SnackBar or something)
            emit(
                Resource(
                    ResourceState.ERROR,
                    data,
                    UiText.StringResource(R.string.inspection_import_from_file_success)
                )
            )
        //}
    }
}

private fun getStringCellValue(cell: Cell): String {
    return when (cell.cellType) {
        CellType.STRING -> cell.stringCellValue
        CellType.NUMERIC -> cell.numericCellValue.toString()
        else -> ""
    }
}
