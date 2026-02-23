package NikKha03.TaskService.config;

import NikKha03.TaskService.controllers.SocketConnectionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

// Класс для обработки WS соединений
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SocketConnectionHandler websocket;

    // Внедряем через конструктор
    public WebSocketConfig(SocketConnectionHandler websocket) {
        this.websocket = websocket;
    }

    // Переопределение метода, который регистрирует обработчики сокетов в реестре
    @Override
    public void registerWebSocketHandlers(
            WebSocketHandlerRegistry webSocketHandlerRegistry) {

        webSocketHandlerRegistry
                .addHandler(websocket, "/socket-by-project-id")
                .setAllowedOrigins("*");
    }
}