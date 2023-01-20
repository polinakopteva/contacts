package contacts

fun readLine(): String {
    val input = readln()
    if ("exit" == input) {
        throw GameExitException()
    }
    return input
}