package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.Character.Spieler;

public class HealthBar extends Actor {
    private Spieler spieler;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private float width;
    private float height;

    public HealthBar(Spieler spieler, float width, float height) {
        this.spieler = spieler;
        this.width = width;
        this.height = height;
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
        this.font.getData().setScale(1.5f);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(10, 20, width, height);

        shapeRenderer.setColor(Color.GREEN);
     //   float healthWidth = width * ((float) spieler.getHealth() / spieler.getMaxHealth());
       // shapeRenderer.rect(10, 20, healthWidth, height);

        shapeRenderer.end();

        batch.begin();

        //font.draw(batch, (int) spieler.getHealth() + " | " + (int) spieler.getMaxHealth(), 20, 40);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }
}
