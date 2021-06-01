package votingapp

import com.sun.security.auth.UserPrincipal
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.ServerHttpRequest
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal
import java.util.*


@Configuration
@EnableWebSocketMessageBroker
open class WebSocketConfigurer : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/queue/");
        config.setApplicationDestinationPrefixes("/app");
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/api")
                .setAllowedOrigins("*")
                .setHandshakeHandler(object : DefaultHandshakeHandler() {
                    override fun determineUser(request: ServerHttpRequest?,
                                               wsHandler: WebSocketHandler?,
                                               attributes: Map<String?, Any?>?) =
                            UserPrincipal(UUID.randomUUID().toString())
                }).withSockJS()
    }
}