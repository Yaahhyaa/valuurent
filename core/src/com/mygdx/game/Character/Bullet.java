package com.mygdx.game.Character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bullet extends Actor {
    private TextureRegion textureRegion;
    private float speed;
    private float directionX;
    private float directionY;

    public Bullet(float x, float y, float speed, float directionX, float directionY, Texture texture) {
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.textureRegion = new TextureRegion(texture);

        setX(x);
        setY(y);
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setOrigin(getWidth() / 2f, getHeight() / 2f);
        setRotation((float) Math.atan2(directionY, directionX) * 180f / MathUtils.PI);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        moveBy(speed * directionX * delta, speed * directionY * delta);

        // Check for collision or bounds here and handle appropriately
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}