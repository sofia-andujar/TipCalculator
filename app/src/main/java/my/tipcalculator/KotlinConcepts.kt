package my.tipcalculator


fun main() {

    lambdaTest()
    trailingLambda()
    val input = Result.ERROR
    objects()

}

fun objects(){
    enums(Repository.getCurrentState())
    Repository.startFetch()
    enums(Repository.getCurrentState())
    Repository.finishedFetch()
    enums(Repository.getCurrentState())
    Repository.error()
    enums(Repository.getCurrentState())
}

fun enums(result: Result) {
    return when(result) {
        Result.SUCCESS -> println("Success")
        Result.FAILURE -> println("Failure")
        Result.ERROR -> println("Error")
        Result.NULL -> println("Null")
        Result.LOADING -> println("Loading")
    }
}

object Repository {
    private var loadState: Result = Result.NULL
    private var dataFetched: String? = ""
    fun startFetch(){
        loadState = Result.LOADING
        dataFetched = "data"
    }
    fun finishedFetch(){
        loadState = Result.SUCCESS
        dataFetched = null
    }
    fun error(){
        loadState = Result.ERROR
    }
    fun getCurrentState(): Result {
        return loadState
    }

}

enum class Result {
    SUCCESS,
    FAILURE,
    ERROR,
    NULL,
    LOADING
}
fun trailingLambda() {
    enhancedMessage("Hello Paulo") {
        sumLambda(12, 12)
    }
    enhancedMessage2("Hello Paulo", sumLambda(12,12))
}

fun enhancedMessage(message:String, funAsParameter: () -> Int) {
    println("$message ${funAsParameter()}")
}
fun enhancedMessage2(message:String, param:Int) {
    println("$message $param")
}

fun lambdaTest() {
    println(sumLambda(1, 2))
    println(itOperator(2))
    itAndVoid("Hello girl")
}
val sumLambda: (Int, Int) -> Int = { a, b -> a + b }
val itOperator: (Int) -> Int = { it*2 }
val itAndVoid: (String) -> Unit = { println(it) }
