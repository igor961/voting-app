package votingapp.entities

import votingapp.types.EstimateType
import votingapp.types.EstimateTypeChecker

class Room(val admin: User, val maxEstimate: Int, val estimateType: EstimateType) {
    private val users: HashMap<String, User> = HashMap()
    val state: State

    val id: String

    init {
        id = admin.id
        users[id] = admin
        state = State(EstimateTypeChecker(maxEstimate, estimateType))
    }

    @Synchronized
    fun addUser(user: User) = users.set(user.id, user)

    fun userById(userId: String) = users[userId]

    @Synchronized
    fun estimate(e: Int, u: User) = state.pushEstimate(e, userById(u.id)!!)

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