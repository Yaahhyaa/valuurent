package com.mygdx.game.Character;

import com.badlogic.gdx.math.Rectangle;

public class Hitbox {
    private Rectangle bounds;

    public Hitbox(float x, float y, float width, float height) {
        bounds = new Rectangle(x, y, width, height);
    }

    public void setPosition(float x, float y) {
        bounds.setPosition(x, y);
    }

    public void setSize(float width, float height) {
        bounds.setSize(width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
