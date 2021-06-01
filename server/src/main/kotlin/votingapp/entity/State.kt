package votingapp.entity

import votingapp.types.EstimateTypeChecker

class State(private val estimateTypeChecker: EstimateTypeChecker, size: Int = 10) {
    private val estimates: ArrayList<Int> = ArrayList(size)

    @Synchronized
    fun pushEstimate(e: Int) =
            estimates.add(estimateTypeChecker.validateEstimate(e))

    fun getAvgEstimate() = estimates.average()
}