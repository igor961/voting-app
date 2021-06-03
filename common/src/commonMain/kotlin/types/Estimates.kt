package votingapp.types

class EstimateTypeChecker(maxEstimate: Int = 5, type: EstimateType = EstimateType.Fib) {
    private val estimates: BooleanArray

    init {
        if (maxEstimate > 100)
            throw IllegalArgumentException("maxEstimate cannot be greater than 100!")
        estimates = BooleanArray(maxEstimate + 1)
        when (type) {
            EstimateType.Std ->
                estimates.fill(true);
            EstimateType.Fib ->
                fibonacci()
                    .take(maxEstimate + 1)
                    .forEach { estimates[it] = true }
        }
    }

    fun validateEstimate(value: Int): Int =
        if (estimates[value])
            value
        else
            throw IllegalArgumentException("It is not a valid estimate!")

}

enum class EstimateType {
    Std,
    Fib
}

private fun fibonacci() = sequence {
    var terms = Pair(0, 1)

    // this sequence is infinite
    while (true) {
        yield(terms.first)
        terms = Pair(terms.second, terms.first + terms.second)
    }
}