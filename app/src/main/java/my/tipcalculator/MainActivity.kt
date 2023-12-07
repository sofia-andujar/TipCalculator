package my.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import my.tipcalculator.components.InputField
import my.tipcalculator.ui.theme.TipCalculatorTheme
import my.tipcalculator.widgets.RoundIconButton
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import my.tipcalculator.ui.theme.primarySurface
import my.tipcalculator.ui.theme.secondarySurface
import my.tipcalculator.util.calculateTotalPerPerson
import my.tipcalculator.util.calculateTotalTip
import kotlin.ranges.ClosedFloatingPointRange


@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorApp {
                MainContent()
            }
        }
    }
}

@Composable
fun TipCalculatorApp(content: @Composable () -> Unit) {
    TipCalculatorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun MainContent() {

    val totalBillState = remember { mutableStateOf("") }
    val splitByState = remember { mutableStateOf(1) }
    val tipAmountState = remember { mutableStateOf(0.0) }
    val totalPerPersonState = remember { mutableStateOf(0.0) }

    AppContents(
        splitByState = splitByState,
        tipAmountState = tipAmountState,
        totalPerPersonState = totalPerPersonState,
        totalBillState = totalBillState
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppContents(
    modifier: Modifier = Modifier,
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    totalBillState: MutableState<String>
) {

    val sliderPositionState = remember { mutableStateOf(0f) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column {
        TopHeader(totalPerPersonState.value)
        Surface(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth(),
            border = BorderStroke(color = MaterialTheme.colors.primary, width = 2.dp),
            shape = RoundedCornerShape(corner = CornerSize(5.dp)),
            elevation = 5.dp,
            color = primarySurface
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                InputField(
                    modifier = modifier.padding(bottom = 10.dp),
                    valueState = totalBillState,
                    labelId = "Introduce el total",
                    enabled = true,
                    isSingleLine = true,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                    onCancel = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        totalBillState.value = ""
                        tipAmountState.value =
                            calculateTotalTip(
                                totalBillState.value,
                                sliderPositionState.value
                            )
                        totalPerPersonState.value =
                            calculateTotalPerPerson(
                                totalBillState.value,
                                sliderPositionState.value,
                                splitByState.value
                            )
                    },
                    onAction = KeyboardActions {
                        keyboardController?.hide()
                        tipAmountState.value =
                            calculateTotalTip(
                                totalBillState.value,
                                sliderPositionState.value
                            )
                        totalPerPersonState.value =
                            calculateTotalPerPerson(
                                totalBillState.value,
                                sliderPositionState.value,
                                splitByState.value
                            )
                    }
                )
                //            if (validState) {
                Row(
                    modifier = modifier
//                    .padding(10.dp)
                        .height(50.dp)
                        .fillMaxWidth(),
//                    .weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Entre",
                        modifier = modifier.align(alignment = Alignment.CenterVertically),
//                    style = MaterialTheme.typography.body1,
                        fontSize = 18.sp
                    )
                    Row(
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Remove icon",
                            onClick = {
                                if (splitByState.value > 1) splitByState.value--
                                tipAmountState.value =
                                    calculateTotalTip(
                                        totalBillState.value,
                                        sliderPositionState.value
                                    )
                                totalPerPersonState.value =
                                    calculateTotalPerPerson(
                                        totalBillState.value,
                                        sliderPositionState.value,
                                        splitByState.value
                                    )
                            }
                        )
                        Text(
                            text = "${splitByState.value}",
                            textAlign = TextAlign.Center,
                            modifier = modifier
                                .align(Alignment.CenterVertically)
                                .width(24.dp)
                        )
                        RoundIconButton(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add icon",
                            onClick = {
                                if (splitByState.value < 99) splitByState.value++
                                tipAmountState.value =
                                    calculateTotalTip(
                                        totalBillState.value,
                                        sliderPositionState.value
                                    )
                                totalPerPersonState.value =
                                    calculateTotalPerPerson(
                                        totalBillState.value,
                                        sliderPositionState.value,
                                        splitByState.value
                                    )
                            }
                        )
                    }
                }
                Row(
                    modifier = modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = modifier.align(alignment = Alignment.CenterVertically),
                        text = "Propina",
                        fontSize = 18.sp
                    )
                    Text(
                        modifier = modifier.align(alignment = Alignment.CenterVertically),
                        text = "%.2f".format(tipAmountState.value) + " €",
//                    text = "$tipAmountState.value €",
                        fontSize = 18.sp
                    )
                }
                Row(
                    modifier = modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = modifier.align(alignment = Alignment.CenterVertically),
                        text = "Porcentaje",
                        fontSize = 18.sp
                    )
                    Text(
                        modifier = modifier.align(alignment = Alignment.CenterVertically),
                        text = (sliderPositionState.value * 100).toInt().toString() + " %",
                        fontSize = 18.sp
                    )
                }
                Slider(
                    valueRange = 0f..0.5f,
                    modifier = modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    value = sliderPositionState.value,
                    enabled = true,
                    onValueChange = {
                        sliderPositionState.value = it
                        tipAmountState.value =
                            calculateTotalTip(
                                totalBillState.value,
                                sliderPositionState.value
                            )
                        totalPerPersonState.value = calculateTotalPerPerson(
                            totalBillState.value,
                            sliderPositionState.value,
                            splitByState.value
                        )
                    },
                    steps = 8
                )
            }
        }
//            } else {
//                Box() {}
//            }
    }
}

@Composable
fun TopHeader(totalPerPerson: Double = 0.00) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 5.dp)
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(5.dp))),
        color = secondarySurface,
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Por persona:",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.secondary
            )
            Text(
                text = "%.2f".format(totalPerPerson) + " €",
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}
