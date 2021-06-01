package votingapp

import dto.CreateRoomDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.stereotype.Controller
import org.springframework.messaging.handler.annotation.SendTo
import votingapp.entity.Room
import votingapp.entity.User
import java.security.Principal
import kotlin.collections.HashMap

@Controller
open class MainController {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val rooms = HashMap<String, Room>()

    @MessageMapping("/{roomId}/voice")
    @SendTo("/queue/{roomId}/voice")
    fun estimate(
        @DestinationVariable roomId: String,
        @Payload voice: Int,
        user: Principal
    ) {
        val room = rooms[roomId]

    }

    @MessageMapping("/room")
    @SendToUser("/queue/room")
    fun createRoom(@Payload pld: CreateRoomDto, user: Principal): String? {
        rooms[user.name] = Room(
            User(user.name, pld.humanName),
            pld.maxEstimate,
            pld.estimateType
        )

        return user.name
    }

}