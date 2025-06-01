package com.example.lectures

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.lectures.data.Graph
import com.example.lectures.models.Lecture
import kotlin.collections.isNotEmpty

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(
    currentDateState: MutableState<String>
) {
    var selectedOptionState by remember { mutableStateOf(getDefaultSelectedOption()) }

    Scaffold(
        containerColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Color.Black)
                .padding(it)
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Title()

            Buttons({ selectedOptionState = it }, selectedOptionState, currentDateState.value)

            val lectureList = Graph.lectures[selectedOptionState] ?: emptyList()

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
                SpecialButton(dayName = selectedOptionState)
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
                        LectureCard(lecture = lecture)
                    }
                }
            }
        }
    }
}

@Composable
fun LectureCard(lecture: Lecture) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Graph.getColorForSubject(lecture.lectureName)
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
                text = lecture.period,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
            Text(
                text = lecture.lectureName,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
            Text(
                text = lecture.time,
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

