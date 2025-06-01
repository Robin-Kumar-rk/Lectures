package com.example.lectures

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import kotlin.collections.forEach
import kotlin.let
import kotlin.text.first
import kotlin.toString

private val selectedColor = Color(0xFFEC3939)
private val buttonColor = Color(0xF3FFE893)

@Composable
fun Buttons(onOptionSelected: (String) -> Unit, selectedOption: String, currDay: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .padding(4.dp)
    ) {
        Column {
            // Weekday buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY").forEach { day ->
                    DayButton(
                        day = day,
                        selectedOption = selectedOption,
                        onOptionSelected = onOptionSelected
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Navigation buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Today button
                NavigationButton(
                    text = "Today",
                    isSelected = selectedOption == currDay,
                    onClick = { onOptionSelected(currDay) }
                )

                // Next button with arrow
                Button(
                    onClick = { onOptionSelected(nextDay[selectedOption].toString()) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedOption == nextDay[selectedOption]) selectedColor else buttonColor,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next Day",
                        tint = Color.Black
                    )
                }

                // Next day button
                NavigationButton(
                    text = "Next",
                    isSelected = selectedOption == nextDay[currDay],
                    onClick = { nextDay[currDay]?.let { onOptionSelected(it) } }
                )
            }
        }
    }
}

@Composable
private fun DayButton(
    day: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Button(
        onClick = { onOptionSelected(day) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedOption == day) selectedColor else buttonColor,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text(day.first().toString(), color = Color.Black)
    }
}

@Composable
private fun NavigationButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) selectedColor else buttonColor,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text, color = Color.Black)
    }
}

@Composable
fun SpecialButton(dayName: String) {
    Spacer(modifier = Modifier.padding(16.dp))
    var text by remember { mutableStateOf(dayName) }
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { text = if (text == dayName) "Moj" else dayName },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.button_default),
                contentColor = colorResource(R.color.button_selection)
            )
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF000000)
            )
        }
    }
}
