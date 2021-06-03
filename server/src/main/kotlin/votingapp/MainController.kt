package votingapp

import dto.*
import exceptions.RoomNotExistException
import exceptions.UserNotInRoomException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.*
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.stereotype.Controller
import votingapp.entities.Estimate
import votingapp.entities.Room
import votingapp.entities.User
import java.security.Principal


@Controller
class MainController {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val rooms = HashMap<String, Room>()

    @MessageMapping("/{roomId}/voice")
    @SendTo("/queue/{roomId}/estimate")
    fun estimate(
        @DestinationVariable roomId: String,
        @Payload voice: VoiceDto,
        user: Principal
    ): EstimateDto {
        val room = rooms[roomId] ?: throw RoomNotExistException()

        val estimate: Estimate = room.userById(user.name)?.let {
            room.estimate(voice.voice, it)
        } ?: throw UserNotInRoomException()

        return estimate.let { EstimateDto(it.user.name, it.estimate) }
    }

    @MessageMapping("/room")
    @SendToUser("/queue/room")
    fun createRoom(@Payload pld: CreateRoomDto, user: Principal): String {
        rooms[user.name] = Room(
            User(user.name, pld.humanName),
            pld.maxEstimate,
            pld.estimateType
        )

        return user.name
    }

    @MessageMapping("/{roomId}/join")
    @SendTo("/queue/{roomId}/newUser")
    fun joinRoom(
        @DestinationVariable roomId: String,
        @Payload pld: UserDto,
        user: Principal
    ): UserDto {
        val newUser = User(user.name, pld.name)
        rooms[roomId]?.addUser(newUser) ?: throw RoomNotExistException()
        return pld
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    fun handleException(exception: Throwable) = exception.message

}