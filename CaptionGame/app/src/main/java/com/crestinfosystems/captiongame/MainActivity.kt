package com.crestinfosystems.captiongame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crestinfosystems.captiongame.ui.theme.CaptionGameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaptionGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    var score by remember { mutableIntStateOf(0) }
    var direc by remember { mutableStateOf("North") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(text = "Your Score is ${score}", fontSize = 20.sp, fontWeight = FontWeight.W400)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Current Direction is ${direc}", fontSize = 20.sp, fontWeight = FontWeight.W400)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(onClick = {
                if (Random.nextBoolean()) {
                    score += 1;
                    direc = "North"
                } else {
                    score = 0;
                    direc = "North"
                }
            }) {
                Text(text = "Change to North")
            }
            Button(onClick = {
                if (Random.nextBoolean()) {
                    score += 1;
                    direc = "South"
                } else {
                     score = 0;
                    direc = "South"
                }
            }) {
                Text(text = "Change to South")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                if (Random.nextBoolean()) {
                    score += 1;
                    direc = "East"
                } else {
                    score = 0;
                    direc = "East"
                }
            }) {
                Text(text = "Change to East")
            }
            Button(onClick = {
                if (Random.nextBoolean()) {
                    score += 1;
                    direc = "West"
                } else {
                    score = 0;
                    direc = "West"
                }
            }) {
                Text(text = "Change to West")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CaptionGameTheme {
        Greeting()
    }
}