package com.mygdx.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Spieler extends SpielObjekt {
    private Rectangle boundary;
    private float speed = 9f;
    private float acceleration = 0f;
    private int direction = 0;

    private Animation<TextureRegion> animation;
    private Animation<TextureRegion> animationlaufen;
    private Animation<TextureRegion> animationrechts;
    private Animation<TextureRegion> animationIdle;
    private Animation<TextureRegion> animationschiessen;
    private Animation<TextureRegion> animationSterben;

    private TextureRegion currentFrame;
    private TextureAtlas atlas;
    private boolean isAnimation = true;
    private float stateTime = 0;
    private boolean flip = false;

    public Spieler(int x, int y, Texture image) {
        super(x, y, image);
        boundary = new Rectangle();
        this.setBoundary();

        atlas = new TextureAtlas(Gdx.files.internal("animation/jet.atlas"));
        Array<TextureAtlas.AtlasRegion> frames = new Array<>();

        animationIdle = new Animation<>(0.1f, atlas.findRegions("idle"), Animation.PlayMode.LOOP);
        animationlaufen = new Animation<>(0.1f, atlas.findRegions("laufen"), Animation.PlayMode.LOOP);
        animationrechts = new Animation<>(0.1f, atlas.findRegions("laufen"), Animation.PlayMode.LOOP);
        animationschiessen = new Animation<>(0.1f, atlas.findRegions("shoot"), Animation.PlayMode.LOOP);

        for ( TextureRegion cFrame : animationrechts.getKeyFrames()) {
            cFrame.flip(true, false);
        }
        animation = animationIdle;

    }

    public Rectangle getBoundary() {
        return boundary;
    }

    public void setBoundary() {
        this.boundary.set(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void draw(Batch b, float parentAlpha) {
        if (isAnimation) {
            currentFrame = animation.getKeyFrame(stateTime, true);
            b.draw(currentFrame, this.getX(), this.getY());

        }
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void move(int direction) {
        int olddirection = this.direction;
        if (direction != olddirection) {
            speed = 3f;
        }
        speed += acceleration;

        if ((direction != olddirection) && (direction == 0 || direction == 1)){
            animation = animationlaufen;
        }

        Array<TextureAtlas.AtlasRegion> frames = new Array<>();
        atlas = new TextureAtlas(Gdx.files.internal("animation/jet.atlas"));


        if (direction == 1) {
            this.setX(this.getX() + speed);
            animation = animationrechts;

        } else if (direction == 0) {
            this.setX(this.getX() - speed);
            animation = animationlaufen;
        }else if (direction == 2) {
            this.setY(this.getY() + speed);
            animation=animationlaufen;
        } else if (direction == 3) {
            this.setY(this.getY() - speed);
             animation=animationlaufen;
        } else {
            animation = animationIdle;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.F)){
            animation = animationschiessen;
        }
        // Grafikposition neu berechnen
        this.direction = direction;
        this.setBoundary();
        this.getImage().setX(this.getX());
        this.getImage().setY(this.getY());
    }

    public void act(float delta) {
        super.act(delta);
        this.update(delta);
    }

    public boolean collidRectangle(Rectangle shape) {
        return Intersector.overlaps(this.boundary, shape);
    }

    //nice
}
