package com.mygdx.game.Client;

public class GlobalState {
    private static String username;

    public static void setUsername(String name) {
        username = name;
    }

    public static String getUsername() {
        return username;
    }
}