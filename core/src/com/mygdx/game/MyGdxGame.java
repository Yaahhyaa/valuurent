package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.actors.Spieler;
import com.mygdx.game.helper.imageHelper;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Music backgroundMusic; // Hintergrundmusik

    Spieler spieler;

    @Override
    public void create() {

        batch = new SpriteBatch();
        img = new Texture("images/Map.png");
        img = new Texture("images/jett.png");


        // Hintergrundmusik
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Valorant Main Menu .mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
        imageHelper ih = new imageHelper();

        spieler = new Spieler(0, 0, ih.changeImgSize(250, 150, "images/jett.png"));
    }

    @Override
    public void render() {

        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();

        // Zuerst die Karte im Hintergrund zeichnen
        batch.draw(img, 0, 0);

        // Dann den Spieler (Jett) im Vordergrund zeichnen
        batch.draw(spieler.getTexture(), spieler.getX(), spieler.getY());

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        backgroundMusic.dispose();
    }
}
