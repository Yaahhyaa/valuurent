package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Character.Spieler;
import com.mygdx.game.helper.imageHelper;
import org.w3c.dom.Text;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture mapTexture;
    Texture spielerTexture;
    Texture img;
    Music backgroundMusic; // Hintergrundmusik

    Spieler spieler;

    @Override
    public void create() {
        batch = new SpriteBatch();
        mapTexture = new Texture("images/Map.png");
        img = new Texture("images/jett.png");

        // Hintergrundmusik
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Valorant_Main_Menu.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

        imageHelper ih = new imageHelper();


    }

    @Override
    public void render() {

        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& spieler.getX()<750) spieler.move(1);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)&& spieler.getX()>0) spieler.move(0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)&& spieler.getY()<580) spieler.move(2);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)&& spieler.getY()>0) spieler.move(3);


        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();

        // Zuerst die Karte im Hintergrund zeichnen
        batch.draw(mapTexture, 0, 0);

        // Dann den Spieler (Jett) im Vordergrund zeichnen

        batch.end();


    }

    @Override
    public void dispose() {
        batch.dispose();
        mapTexture.dispose();
        spielerTexture.dispose();
        backgroundMusic.dispose();
    }
}
