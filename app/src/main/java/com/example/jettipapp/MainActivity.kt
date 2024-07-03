package com.example.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettipapp.Component.input
import com.example.jettipapp.ui.theme.JetTipAppTheme
import com.example.jettipapp.widegts.roundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            myApp {
                mainContent()
            }
        }
    }
}

@Composable
fun myApp(content: @Composable ()-> Unit)
{
    JetTipAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            content()

        }
    }

}
//@Preview

@Preview
@Composable
fun mainContent(){
//    Surface(modifier = Modifier
//        .padding(2.dp)
//        .fillMaxWidth(),
//        shape = RoundedCornerShape(CornerSize(8.dp)),
//        border = BorderStroke(width = 1.dp, color = Color.LightGray)
//    ) {
//
//
//    }
    Column(modifier = Modifier.padding(all = 12.dp)) {
        billForm()
        {}

    }


}
@Composable
fun topHeader(totalPerPerPerson:Double)
{
    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp)))
        ,
        color = Color(0xFFB490CF)
    ){
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total ="%.2f".format(totalPerPerPerson)
            Text(text = "Total Per Person",
                style = MaterialTheme.typography.titleMedium)
            Text(text = "$$total",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold)
        }
    }

}


@Composable
fun billForm(   modifier:Modifier=Modifier,
                onVaChange:(String)->Unit={}
)
{
    val totalBillState= remember {
        mutableStateOf("")
    }
    val valid= remember (totalBillState.value){
        totalBillState.value.trim().isNotEmpty()
    }
    val sliderPostVal = remember {
        mutableStateOf(0f)
    }
    val tipPercentage=(sliderPostVal.value*100).toInt()
    val range=IntRange(start = 1, endInclusive = 100)
    val tipAmountState= remember {
        mutableStateOf(0.0)
    }
    var splitByState= remember {
        mutableStateOf(1)
    }
    val totalPerPersonState= remember {
        mutableStateOf(0.0)
    }

    val keyBoardController= LocalSoftwareKeyboardController.current
    topHeader(totalPerPersonState.value)
    Surface (
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(1.dp, color = Color.LightGray)
    ){
        Column {
            input(valstate = totalBillState, labelId ="Enter Bill" , enable = true , isSingleLine = true,
                onAction = KeyboardActions{
                    if (!valid)return@KeyboardActions
                    onVaChange(totalBillState.value.trim())
                   keyBoardController?.hide()
                }
            )
            Row (
                modifier=Modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start
            ){
                Text(text = "Split",
                    modifier=Modifier.align(
                        alignment = Alignment.CenterVertically
                    ))
                Spacer(modifier = modifier.width(120.dp))
                Row (modifier=Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.End){
                    roundIconButton(modifier = Modifier,
                        imgVector =Icons.Default.Remove,
                        onClick = {
                            splitByState.value= if (splitByState.value>1) splitByState.value-1
                            else 1
                            totalPerPersonState.value= calcTotalPerPerson(totalBill =totalBillState.value.toDouble(),
                                splitBy = splitByState.value,
                                tipPercentage = tipPercentage)
                        })
                    Text(text = "${splitByState.value}",
                        modifier= Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 9.dp, end = 9.dp),
                        fontWeight = FontWeight.SemiBold)
                    roundIconButton(modifier = Modifier,
                        imgVector =Icons.Default.Add ,
                        onClick = { if (splitByState.value<range.endInclusive) {
                            splitByState.value = splitByState.value+1
                        }
                            totalPerPersonState.value= calcTotalPerPerson(totalBill =totalBillState.value.toDouble(),
                                splitBy = splitByState.value,
                                tipPercentage = tipPercentage)
                        }
                    )
                }
            }
            Row (
                modifier=Modifier.padding(horizontal = 3.dp, vertical = 12.dp)
            ){
                Text(text = "Tip",
                    modifier=Modifier.align(alignment = Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(200.dp))
                Text(text = "$ ${tipAmountState.value}",
                    modifier=Modifier.align(alignment = Alignment.CenterVertically),
                    fontWeight = FontWeight.SemiBold)
            }
            Column(modifier=Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$tipPercentage%")
                Spacer(modifier = Modifier.height(14.dp))
                //slider
                Slider(value = sliderPostVal.value,
                    onValueChange ={newVal->
                        sliderPostVal.value=newVal
                        tipAmountState.value=
                            calculateTip (totalBill = totalBillState.value.toDouble() ,
                                tipPercentage = tipPercentage)
                        totalPerPersonState.value= calcTotalPerPerson(totalBill =totalBillState.value.toDouble(),
                            splitBy = splitByState.value,
                            tipPercentage = tipPercentage)
                    },
                    modifier=Modifier.padding(start = 16.dp, end = 16.dp)
                    //steps = 5
)
            }
            //testing
//            Row (
//                modifier=Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically){
//                    Text(text = "33%")
//            }
        }

    }


}

fun calculateTip(totalBill: Double, tipPercentage: Int): Double {
    return if (totalBill>1 &&
        totalBill.toString().isNotEmpty())
        (totalBill*tipPercentage)/100
    else 0.0
}

fun calcTotalPerPerson(
    totalBill:Double,
    splitBy:Int,
    tipPercentage: Int
): Double {
    val bill=totalBill+ calculateTip(totalBill,tipPercentage)
    return bill/splitBy
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTipAppTheme {
        myApp {
            Text(text = "Hello again")
        }
    }
}