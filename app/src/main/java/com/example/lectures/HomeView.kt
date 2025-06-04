package com.example.lectures

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lectures.data.TimetableData
import kotlin.collections.isNotEmpty

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(
    currentDateState: MutableState<String>,
    currentTimetable: TimetableData?,
    onPasteTimetable: () -> Unit
) {
    var selectedOptionState by remember { mutableStateOf(getDefaultSelectedOption()) }

    Scaffold(
        containerColor = Color.Black,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onPasteTimetable,
                containerColor = buttonColor,
                contentColor = Color.Black
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        },
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Color.Black)
                .padding(it)
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Title()

            if (currentTimetable == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No timetable loaded.\nTap + to import a timetable",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
            } else {
                Buttons({ selectedOptionState = it }, selectedOptionState, currentDateState.value)

                val lectureList = currentTimetable.lectures[selectedOptionState] ?: emptyList()

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = selectedOptionState,
                    style = MaterialTheme.typography.titleLarge.copy(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Red,
                                Color(0xFF00FFAB),
                                Color(0xFF0048FF)
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite
                        ),
                    ),
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                if (selectedOptionState == "SATURDAY" || selectedOptionState == "SUNDAY") {
                    SpecialDay(dayName = selectedOptionState)
                }

                Spacer(modifier = Modifier.padding(8.dp))

                if (lectureList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        items(lectureList) { lecture ->
                            LectureCard(
                                period = lecture[0],
                                subject = lecture[1],
                                time = currentTimetable.timeSlots[lecture[2]] ?: lecture[2],
                                subjectColor = currentTimetable.subjects[lecture[1]] ?: "0xFFFFFFFF"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LectureCard(period: String, subject: String, time: String, subjectColor: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = try {
                Color(subjectColor.toLong(16))
            } catch (e: Exception) {
                Color.White
            }
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = period,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
            Text(
                text = subject,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
            Text(
                text = time,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
        }
    }
}

@Composable
fun Title() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = Color(0xFF000000),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Timetable",
            style = MaterialTheme.typography.titleLarge.copy(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Green,
                        Color.Blue
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite
                ),
                letterSpacing = 1.5.sp
            ),
            fontSize = 36.sp,
        )
    }
}

