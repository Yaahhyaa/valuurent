package com.mygdx.game.Client;

import com.badlogic.gdx.math.Vector2;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class GameWebSocketClient extends WebSocketClient {
    private String username;
    private boolean newPositionReceived = false;
    private Vector2 player2Position = new Vector2();

    public GameWebSocketClient(String username) throws URISyntaxException {
        super(new URI("ws://172.20.10.9:8080")); // WebSocket-Server-URI
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Verbunden mit WebSocket-Server");
        sendUsername(username);
    }

    public void sendUsername(String username) {
        JSONObject usernameObject = new JSONObject();
        try {
            usernameObject.put("username", username);
            send(usernameObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendPosition(Vector2 position) {
        JSONObject positionObject = new JSONObject();
        try {
            positionObject.put("username", username);
            JSONObject pos = new JSONObject();
            pos.put("x", position.x);
            pos.put("y", position.y);
            positionObject.put("position", pos);
            send(positionObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            if (jsonObject.has("player")) {
                JSONObject players = jsonObject.getJSONObject("player");
                for (String key : players.keySet()) {
                    if (!key.equals(username)) { // Aktuelle Spieler-Position ignorieren
                        JSONObject playerObject = players.getJSONObject(key);
                        float x = (float) playerObject.getDouble("x");
                        float y = (float) playerObject.getDouble("y");
                        player2Position.set(x, y);
                        newPositionReceived = true;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WebSocket-Verbindung geschlossen: " + code + ", " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Fehler in der WebSocket-Verbindung: " + ex.getMessage());
    }

    public boolean hasNewPosition() {
        return newPositionReceived;
    }

    public Vector2 getPlayer2Position() {
        newPositionReceived = false; // Zurücksetzen, nachdem die Position verwendet wurde
        return player2Position;
    }
}