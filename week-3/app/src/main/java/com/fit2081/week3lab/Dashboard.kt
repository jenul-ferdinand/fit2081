package com.fit2081.week3lab

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.fit2081.week3lab.ui.theme.Week3LabTheme
import java.util.Calendar

class Dashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week3LabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DashboardScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable()
fun DatePickerFun(mDate: MutableState<String>): DatePickerDialog {
    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    return DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/$mMonth/$mYear"
        }, mYear, mMonth, mDay
    )
}

@Composable()
fun TimePickerFun(mTime: MutableState<String>): TimePickerDialog {
    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()

    val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalendar.get(Calendar.MINUTE)

    mCalendar.time = Calendar.getInstance().time

    return TimePickerDialog (
        mContext,
        { _: TimePicker, mHour: Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )
}

@Preview(showBackground = true)
@Composable()
fun DashboardScreen(modifier: Modifier = Modifier) {
    val mContext = LocalContext.current

    val mDate = remember { mutableStateOf("") }
    val mTime = remember { mutableStateOf("") }

    val mTimeFieldValue = remember { mutableStateOf("") }
    val mCheckBoxValue = remember { mutableStateOf(false) }

    // Call the function to return TimePickerDialog
    var mTimePickerDialog = TimePickerFun(mTime)
    var mDatePickerDialog = DatePickerFun(mDate)

    var sliderValue by remember { mutableStateOf(10f) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Button for opening the DatePicker
        Button(
            onClick = {
                mDatePickerDialog.show()
            }
        ) {
            Text("Open DatePicker")
        }
        Text(text = "Selected Date: ${mDate.value}")

        // Button for opening the TimePicker
        Button(
            onClick = {
                mTimePickerDialog.show()
            }
        ) {
            Text("Open TimePicker")
        }
        Text(text = "Selected Time: ${mTime.value}")
    }
}


