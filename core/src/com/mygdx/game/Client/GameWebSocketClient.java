package com.mygdx.game.Client;

import com.mygdx.game.Screens.GameScreen2;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class GameWebSocketClient extends WebSocketClient {

    public GameWebSocketClient(GameScreen2 gameScreen2) throws URISyntaxException {
        super(new URI("ws://172.20.10.9:8080")); // WebSocket-Server-URI
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Verbunden mit WebSocket-Server");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Nachricht empfangen: " + message);
        // Hier können Sie die empfangene Nachricht verarbeiten
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket-Verbindung geschlossen: " + code + ", " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Fehler in der WebSocket-Verbindung: " + ex.getMessage());
    }

    // Hier können Sie eine Methode implementieren, um Nachrichten an den Server zu senden
    public void sendPlayerCoordinates(float x, float y) {
        JSONObject message = new JSONObject();
        JSONObject playerObject = new JSONObject();
        try {
            playerObject.put("x", x);
            playerObject.put("y", y);
            message.put("player", playerObject);
            send(message.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}