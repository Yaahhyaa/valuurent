package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;



public class Spieler extends SpielObjekt{


    private Rectangle boundary;

    public Spieler(int x, int y, Texture image) {
        super(x, y, image);
        boundary = new Rectangle();
        this.setBoundary();        
    }

    private void setBoundary() {
    }


}
