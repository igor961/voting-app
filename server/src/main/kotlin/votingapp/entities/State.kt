package votingapp.entities

import votingapp.types.EstimateTypeChecker

class State(private val estimateTypeChecker: EstimateTypeChecker, size: Int = 10) {
    private val estimates: ArrayList<Estimate> = ArrayList(size)

    @Synchronized
    fun pushEstimate(e: Int, u: User): Estimate {
        val estimate = Estimate(estimateTypeChecker.validateEstimate(e), u)
        estimates.add(estimate)
        return estimate
    }

    fun getAvgEstimate() = estimates.map { it.estimate }.average()
}