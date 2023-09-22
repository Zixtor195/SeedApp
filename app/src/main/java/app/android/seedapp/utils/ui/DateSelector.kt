package app.android.seedapp.utils.ui

import android.app.DatePickerDialog
import android.content.Context
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun dateSelector(
    context: Context,
    value: String,
    pattern: String = "yyyy-MM-dd",
    onValueChange: (String) -> Unit = {},
): DatePickerDialog {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val date = if (value.isNotBlank()) LocalDate.parse(value, formatter) else LocalDate.now()

    val dialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onValueChange(LocalDate.of(year, (month + 1), dayOfMonth).toString())
        },
        date.year,
        (date.monthValue - 1),
        date.dayOfMonth,
    )

    dialog.datePicker.maxDate = System.currentTimeMillis() - 1000

    return dialog
}