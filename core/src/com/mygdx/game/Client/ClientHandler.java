package com.mygdx.game.Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // Hier empfängt der Client Nachrichten vom Server und kann entsprechend darauf reagieren
        // Zum Beispiel: Nachrichten analysieren und Spielobjekte aktualisieren
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Hier wird aufgerufen, wenn eine Ausnahme auftritt
        cause.printStackTrace();
        ctx.close();
    }
}