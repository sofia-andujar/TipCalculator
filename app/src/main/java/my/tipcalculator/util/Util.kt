package my.tipcalculator.util

fun calculateTotalTip(totalBillStateValue: String, sliderPosition: Float): Double {
    val tipPercentage = (sliderPosition * 100).toInt()
    val validState = totalBillStateValue.isNotEmpty()
    return if(validState) {
        totalBillStateValue.toDouble() * tipPercentage /100
    } else {
        0.0
    }
}

fun calculateTotalPerPerson(totalBillStateValue: String, sliderPosition: Float, splitBy: Int): Double {
    val totalTip = calculateTotalTip(totalBillStateValue, sliderPosition)
    val validState = totalBillStateValue.isNotEmpty()
    return if(validState) {
        (totalBillStateValue.toDouble() + totalTip) / splitBy
    } else {
        0.0
    }
}