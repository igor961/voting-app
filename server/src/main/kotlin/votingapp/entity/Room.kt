package votingapp.entity

import votingapp.types.EstimateType
import votingapp.types.EstimateTypeChecker

class Room(val admin: User, val maxEstimate: Int, val estimateType: EstimateType) {
    private val users: ArrayList<User> = ArrayList()
    val state: State

    val id: String

    init {
        id = admin.id
        users.add(admin)
        state = State(EstimateTypeChecker(maxEstimate, estimateType))
    }

    @Synchronized
    fun addUser(user: User) = users.add(user)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Room

        if (admin.id != other.admin.id) return false

        return true
    }

    override fun hashCode(): Int {
        return admin.id.hashCode()
    }


}