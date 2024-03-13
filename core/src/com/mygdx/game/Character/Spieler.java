package com.mygdx.game.Character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
public class Spieler extends SpielObjekt{
    private Rectangle boundary;
    private float speed = 2f;
    private float acceleration = 0.5f;
    private int direction = 0;
    public Spieler(int x, int y, Texture image) {
        super(x, y, image);
        boundary = new Rectangle();
        this.setBoundary();
}
    public Rectangle getBoundary() {
        return boundary;
    }

    public void setBoundary(){
        this.boundary.set(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override

    public void draw(Batch b, float parentAlpha) {
        this.getImage().draw(b);
    }

    public void update(float delta){

    }


    public void move(int direction) {

        if (direction != this.direction){
            speed = 2;
        }

        speed += acceleration;
        if(direction == 1){
            this.setX(this.getX()+speed);
        }else if(direction == 0){
            this.setX(this.getX()-speed);
        }else if(direction == 2){
            this.setY(this.getY() + speed);
        }else if (direction == 3){
            this.setY(this.getY() - speed);
        }
        //muss Grafikposition neu berechnen !!
        this.setBoundary();
        this.direction = direction;
        this.setBoundary();
        this.direction = direction;
    }

    public void act(float delta){
        super.act(delta);
        this.update(delta);
    }


    public boolean collidRectangle(Rectangle shape){
        if (Intersector.overlaps(this.boundary,shape)){
            return true;
        }else {
            return false;
        }
    }
}


