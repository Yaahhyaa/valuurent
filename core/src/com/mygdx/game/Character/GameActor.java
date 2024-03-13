package com.mygdx.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


public class GameActor extends SpielObjekt {

    private int direction =0;

    private double geschwindigkeit = 2;
    private double beschleunigung = 0.1;


    private Circle circle;

    public GameActor(int x, int y, Texture image) {
        super(x, y, image);
        circle = new Circle();
        circle.x = x;
        circle.y = y;
    }

    @Override
    public void draw(Batch b, float parentAlpha) {
        this.getImage().draw(b);
    }


    public void update(float delta) {
        this.setBoundary();
    }

    private void setBoundary() {
        int half = (int)this.getHeight() /2;
        circle.setPosition(this.getX()+half, this.getY()+half);
    }

    public boolean collideCircle(Rectangle shape) {
        if (Intersector.overlaps(circle, shape))
            return true;
        else
            return false;
    }
    public void move(int direction){
        //direction 0 left, 1 right
        geschwindigkeit += beschleunigung;
        if (direction != this.direction) {
            // reset speed
            geschwindigkeit = 2;
        }
        if (direction == 0 && this.getX() > 0) {
            this.setX((int)(this.getX()-geschwindigkeit));
        }
        if (direction == 1 && this.getX()< (Gdx.graphics.getWidth()-this.getWidth()))
        {
            this.setX((int)(this.getX()+geschwindigkeit));
        }
        this.direction  = direction;

    }

    @Override
    public void act(float dt) {
        super.act(dt);
        this.update(dt);
    }
}
