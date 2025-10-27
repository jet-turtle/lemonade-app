package kz.rusmen.lemonade2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.rusmen.lemonade2.ui.theme.Lemonade2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lemonade2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LemonadeApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

class Lemon(
    val step: Int,
    val img: Int,
    val description: Int
)

val lemon1 = Lemon(1, R.drawable.lemon_tree, R.string.lemon_tree_content_description)
val lemon2 = Lemon(2, R.drawable.lemon_squeeze, R.string.lemon_content_description)
val lemon3 = Lemon(3, R.drawable.lemon_drink, R.string.glass_of_lemonade_content_description)
val lemon4 = Lemon(4, R.drawable.lemon_restart, R.string.empty_glass_content_description)

@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.background))
    ) {
        Header("Lemonade")
        Content()
    }
}

@Composable
fun Header(
    name: String
) {
    Text(
        text = name,
        color = Color.DarkGray,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.yellow))
            .statusBarsPadding()
            .padding(top = 8.dp, bottom = 16.dp)
    )
}

@Composable
fun Content() {
    var currentStep by remember { mutableIntStateOf(1) }
    val currentLemon = when (currentStep) {
        1 -> lemon1
        2 -> lemon2
        3 -> lemon3
        4 -> lemon4
        else -> lemon1
    }
    var squeeze = 1

    fun getNextStep() {
        when {
            currentLemon.step == 1 -> currentStep = 2
            currentLemon.step == 2 -> {
                val num2to4 = if (squeeze == 1) (2..4).random() else (squeeze..4).random()
                if (num2to4 > squeeze) {
                    currentStep = 2
                    squeeze++
                } else {
                    currentStep = 3
                    squeeze = 1
                }
            }
            currentLemon.step == 3 -> currentStep = 4
            currentLemon.step == 4 -> currentStep = 1
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { getNextStep() },
                modifier = Modifier
                    .size(200.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.button_background)
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(R.color.button_border)
                )
            ) {
                Image(
                    painter = painterResource(currentLemon.img),
                    contentDescription = stringResource(currentLemon.description)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(currentLemon.description)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Lemonade App"
)
@Composable
fun LemonadeAppPreview() {
    Lemonade2Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LemonadeApp(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}