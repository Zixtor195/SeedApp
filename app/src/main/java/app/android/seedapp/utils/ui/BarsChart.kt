package app.android.seedapp.utils.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.android.seedapp.ui.theme.AppPrimary
import app.android.seedapp.ui.theme.Black

@Composable
fun BarsChart(
    data: ArrayList<String>,
    dataKey: ArrayList<Float>,
    dataValue: ArrayList<Int>,
    maxValue: Int
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .height(200.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = maxValue.toString())
                    Spacer(modifier = Modifier.fillMaxHeight())
                }

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = (maxValue / 2).toString())
                    Spacer(modifier = Modifier.fillMaxHeight(0.5f))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(Black)
            )

            dataKey.forEachIndexed { index, key ->
                Box(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .width(20.dp)
                        .fillMaxHeight(key)
                        .background(AppPrimary)
                        .clickable {
                            Toast
                                .makeText(context, "${data[index]} : ${dataValue[index]}", Toast.LENGTH_SHORT)
                                .show()
                        }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Black)
        )

        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(start = 72.dp, top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            data.forEach {

                Text(
                    modifier = Modifier
                        .rotate(-90f)
                        .vertical()
                        .height(20.dp),
                    text = it,
                )


            }
        }

    }
}