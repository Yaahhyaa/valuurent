package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Character.GameActor;
import com.mygdx.game.Character.Spieler;

public class GameScreen2 implements Screen {
    private Game game;
    private Stage stage;
    private static Spieler spieler; // Statische Variable für den Spieler
    private GameActor background;
    private boolean initialized;

    public GameScreen2(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        initialized = false;
    }

    @Override
    public void show() {
        if (!initialized) {
            initStage();
            initialized = true;
        }
    }

    @Override
    public void render(float delta) {
        if (!initialized) return; // Abbrechen, wenn der Bildschirm nicht initialisiert wurde

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spieler.move(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && spieler.getX() < 1780) spieler.move(1);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && spieler.getX() > 0) spieler.move(0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && spieler.getY() < 315) spieler.move(2);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && spieler.getY() > 0) spieler.move(3);

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new TitleScreen(game));
            dispose();

            return;
        }
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

    }

    // Andere Methoden wie zuvor

    private void initStage() {
        Texture spielerTexture = new Texture("images/jett.png");
        if (spieler == null) {
            spieler = new Spieler(1800, 0, spielerTexture); // Erstelle den Spieler, wenn er noch nicht existiert
        }
        background = new GameActor(0, 0, new Texture("images/Map.png"));
        stage.addActor(background);
        stage.addActor(spieler);
        Gdx.input.setInputProcessor(stage);
    }
}
