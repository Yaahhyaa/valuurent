package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Character.GameActor;
import com.mygdx.game.Character.Spieler;

public class GameScreen2 implements Screen {
    SpriteBatch batch;
    Texture mapTexture;
    Texture spielerTexture;
    Texture img;
    Music backgroundMusic; // Hintergrundmusik
    private Game game;
    Spieler spieler;
    private Stage stage;
    GameActor gameActor;
    GameActor background;




    public GameScreen2(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        initStage();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && spieler.getX()<1600) spieler.move(1);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)&& spieler.getX()>0) spieler.move(0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)&& spieler.getY()<780) spieler.move(2);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)&& spieler.getY()>0) spieler.move(3);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        mapTexture.dispose();
        spielerTexture.dispose();
        backgroundMusic.dispose();
    }

    private void initStage(){
        //mapTexture = new Texture("images/Map.png");
        //img = new Texture("images/jett.png");

        // Hintergrundmusik
        //backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Valorant_Main_Menu.mp3"));
        //backgroundMusic.setLooping(true);
        //backgroundMusic.setVolume(0.5f);
        //backgroundMusic.play();

        batch = new SpriteBatch();


        spieler = new Spieler(0, 0, new Texture("images/jett.png"));

        //gameActor = new GameActor(0, 0, new Texture("images/jett.png")); // Hier den gameActor initialisieren
        background = new GameActor(0,0,new Texture("images/Map.png"));
        //feind = new Feind(500,300,"images/wi_rund_20.png",Gdx.graphics.getHeight());

        stage.addActor(background);
        //stage.addActor(gameActor);
        stage.addActor(spieler);
        //stage.addActor(feind); uiu
        //jkiji

    }
}
