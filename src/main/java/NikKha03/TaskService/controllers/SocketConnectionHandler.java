package NikKha03.TaskService.controllers;

import NikKha03.TaskService.model.WebSocketUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * https://www.geeksforgeeks.org/springboot/spring-boot-web-socket/
 */
@Component
public class SocketConnectionHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(SocketConnectionHandler.class);

    /* Массив WebSocketUser по projectId */
    private final Map<Long, Set<WebSocketUser>> socketUsersByProjectId = new ConcurrentHashMap<>();

    /* WebSocketUser по sessionId */
    private final Map<String, WebSocketUser> socketUsersBySessionId = new ConcurrentHashMap<>();

    /* Метод при подключении клиента */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, String> params = extractParamsFromSession(session);
        WebSocketUser socketUser = new WebSocketUser(Long.parseLong(params.get("projectId")), params.get("keycloakId"), session);
        socketUsersBySessionId.put(session.getId(), socketUser);
        socketUsersByProjectId.computeIfAbsent(Long.parseLong(params.get("projectId")), k -> ConcurrentHashMap.newKeySet()).add(socketUser); // добавляем сеанс

        super.afterConnectionEstablished(session);
        logger.info("WebSocket connected! KeycloakUserId: " + socketUser.getKeycloakId() + ", projectId: " + socketUser.getProjectId());
    }

    /* Метод при отключении клиента */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketUser socketUser = socketUsersBySessionId.get(session.getId());
        socketUsersBySessionId.remove(session.getId());
        socketUsersByProjectId.computeIfPresent(socketUser.getProjectId(), (id, users) -> {
            users.remove(socketUser); // удаляем пользователя
            return users.isEmpty() ? null : users; // если множество пустое → убираем ключ совсем
        });

        super.afterConnectionClosed(session, status);
        logger.info("WebSocket disconnected! KeycloakUserId: " + socketUser.getKeycloakId() + ", projectId: " + socketUser.getProjectId());
    }

    /**
     * Метод отправки сообщения
     * Через сокет клиенты обмениваются данными, полученные данные в сокете никак не обрабатываются.
     * Изменения данных в БД инициирует клиент.
     **/
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        WebSocketUser socketUser = socketUsersBySessionId.get(session.getId());
        Set<WebSocketUser> users = socketUsersByProjectId.get(socketUser.getProjectId());

        users.forEach(user -> {
            if (user.getSession() != session) {
                try {
                    user.getSession().sendMessage(message);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });
    }

    private Map<String, String> extractParamsFromSession(WebSocketSession session) {
        URI uri = session.getUri();
        Map<String, String> paramsMap = new HashMap<>();

        if (uri != null && uri.getQuery() != null) {
            String[] params = uri.getQuery().split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    paramsMap.put(keyValue[0], keyValue[1]);
                }
            }
        }

        return paramsMap;
    }

}
