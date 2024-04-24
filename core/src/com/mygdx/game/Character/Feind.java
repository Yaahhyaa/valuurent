package com.mygdx.game.Character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Feind extends SpielObjekt {
    private Rectangle boundary;

    public Feind(int x, int y, Texture image) {
        super(x, y,image );
        boundary = new Rectangle(1760, 60, image.getWidth(), image.getHeight());
    }

    @Override
    public void draw(Batch b, float parentAlpha) {
        getImage().draw(b);
    }

    public Rectangle getBoundary() {
        return boundary;
    }

    public boolean collideRectangle(Rectangle shape) {
        return Intersector.overlaps(boundary, shape);
    }
}
