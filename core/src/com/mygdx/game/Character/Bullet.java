package com.mygdx.game.Character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bullet extends Actor {
    private Texture texture;
    private Vector2 position;
    private Rectangle bounds;
    private Vector2 direction;
    private float speed;

    public Bullet(float x, float y, float speed, float directionX, float directionY, Texture texture) {
        this.position = new Vector2(x, y);
        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        this.speed = speed;
        this.direction = new Vector2(directionX, directionY).nor();
        this.texture = texture;
    }

    public void update(float delta) {
        position.x += speed * direction.x * delta;
        position.y += speed * direction.y * delta;
        bounds.setPosition(position);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }
}
