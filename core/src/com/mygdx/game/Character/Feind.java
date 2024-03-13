package com.mygdx.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Feind extends SpielObjekt {
    private Rectangle boundary;

    private float acceleration;
    private float geschwindigkeit = 2;

    public Feind(int x, int y, Texture image, ArrayList<Feind> f) {
        super(x, y, image);
        boundary = new Rectangle();
        this.setBoundary();
        this.setRandomPosition(f);

        Random r = new Random();
        int min = Gdx.graphics.getHeight()+(int)this.getHeight();
        this.setY(r.nextInt(2300 + 1 - 800)+ 800);
        this.setBoundary();
        int max = (int) (Gdx.graphics.getWidth() - this.getWidth());
        min = 0;
        this.setX(r.nextInt(max +1 - min)+ min);

    }
    public void setBoundary(){
        this.boundary.set(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    @Override
    public void draw(Batch b, float parentaplha) {
        this.getImage().draw(b);
    }

    public void setRandomPosition(ArrayList<Feind> feindListe){
        Random r = new Random();
        Boolean collision = true;
        int ry=0;
        int rx = 0;
        while(collision) {
            int minY = Gdx.graphics.getHeight() + (int) this.getHeight();
            int maxW = (int) (Gdx.graphics.getWidth() - this.getWidth());
            rx = r.nextInt(maxW + 1 - 0) + 0;
            ry = r.nextInt(1500 + 1 - minY) + minY;

            Rectangle rec = new Rectangle(rx, ry, this.getWidth(), this.getHeight());
            collision = false;
            for(Feind f: feindListe){
                if(Intersector.overlaps(rec,f.getBoundary())){
                    collision = true;
                    break;
                }
            }
        }
        this.setBoundary();

        this.setY(ry);
        this.setX(rx);
        this.setBoundary();
    }

    public void update(float delta){
        this.setY((int)(this.getY() - 4));

        if(this.getY() < 0){
            this.setY(Gdx.graphics.getHeight() + this.getHeight());
            Random r = new Random();
            int max = (int) (Gdx.graphics.getWidth() - this.getHeight());
            int min = 0;
            this.setX(r.nextInt(max + 1 - min) + min);
        }
        this.setBoundary();
    }

    public void act(float delta){
        super.act(delta);
        this.update(delta);
    }

    public Rectangle getBoundary() {
        return boundary;
    }

    public boolean collideRectangle(Rectangle shape){
        if (Intersector.overlaps(this.boundary, shape)){
            return true;
        }else {
            return false;
        }
    }
}
