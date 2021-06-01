package dto

import votingapp.types.EstimateType

data class CreateRoomDto(
    val humanName: String,
    val maxEstimate: Int,
    val estimateType: EstimateType
)