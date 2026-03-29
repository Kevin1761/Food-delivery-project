package org.example.springboot_demo.webSocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WebSocketTask {
    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * Send a message to all clients via WebSocket every 5 seconds.
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void sendMessageToClient() {
        //System.out.println("[Scheduled task triggered] Time: " + LocalDateTime.now());
        webSocketServer.sendToAllClient("This is a message from the server: " + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
    }
}
