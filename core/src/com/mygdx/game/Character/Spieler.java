package com.mygdx.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Spieler extends SpielObjekt{
    private Rectangle boundary;
    private float speed = 2f;
    private float acceleration = 0.5f;
    private int direction = 0;

    private Animation<TextureRegion> animation;
    private TextureRegion currentFrame;
    private TextureAtlas atlas;
    private boolean isAnimation = true;
    private float stateTime =0;
    public Spieler(int x, int y, Texture image) {
        super(x, y, image);
        boundary = new Rectangle();
        this.setBoundary();
        atlas = new TextureAtlas(Gdx.files.internal("animation/jet.atlas"));
        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions("a");
        animation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
}
    public Rectangle getBoundary() {
        return boundary;
    }

    public void setBoundary(){
        this.boundary.set(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override

    public void draw(Batch b, float parentAlpha) {

        //this.getImage().draw(b);
        if (isAnimation) {
            currentFrame = animation.getKeyFrame(stateTime, true);
            b.draw(currentFrame, this.getX(), this.getY());
        }
    }

    public void update(float delta){
        stateTime += delta;
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
        this.direction = direction;
        this.setBoundary();
        this.getImage().setX(this.getX());
        this.getImage().setY(this.getY());
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


