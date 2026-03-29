package org.example.springboot_demo.webSocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * WebSocket service.
 */
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    // Store session objects
    private static Map<String, Session> sessionMap = new HashMap();

    /**
     * Method called when a new connection is successfully established.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("Client: " + sid + " connected");
        sessionMap.put(sid, session);
    }

    /**
     * Method called after receiving a message from the client.
     *
     * @param message The message sent from the client
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("Received message from client " + sid + ": " + message);
    }

    /**
     * Method called when the connection is closed.
     *
     * @param sid session id
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("Connection closed: " + sid);
        sessionMap.remove(sid);
    }

    /**
     * Broadcast a message to all connected clients.
     *
     * @param message message to broadcast
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                // Server sends message to client
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
