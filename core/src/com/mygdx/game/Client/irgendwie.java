package com.mygdx.game.Client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class irgendwie {

    public static void main(String[] args) {
        try {
            // WebSocket-Server-URI
            URI serverUri = new URI("ws://localhost:8080");

            // WebSocket-Client erstellen und Verbindung herstellen
            WebSocketClient client = new WebSocketClient(serverUri) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Verbunden mit WebSocket-Server");
                    send("Hallo Server, dies ist ein Test");
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("Nachricht vom Server erhalten: " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("WebSocket-Verbindung geschlossen: " + code + ", " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    System.err.println("Fehler in der WebSocket-Verbindung: " + ex.getMessage());
                }
            };

            // Verbindung herstellen
            client.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}