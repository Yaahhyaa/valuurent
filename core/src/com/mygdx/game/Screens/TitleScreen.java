package com.mygdx.game.Screens;

//Auswahl screeen

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Character.GameActor;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.ScreenGame;

import javax.swing.*;

public class TitleScreen implements Screen {
    private Stage stage;
    private Game game;
    private GameScreen2 gameScreen2;

    public TitleScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        Label title = new Label("Welcome to valuurent", ScreenGame.gameSkin, "big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 2 / 3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton playButton = new TextButton("Arena", ScreenGame.gameSkin);
        playButton.setWidth(Gdx.graphics.getWidth() / 4);
        playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2);
        playButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(gameScreen2);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);

        TextButton optionsButton = new TextButton("Options", ScreenGame.gameSkin);
        optionsButton.setWidth(Gdx.graphics.getWidth() / 4);
        optionsButton.setPosition(Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2, Gdx.graphics.getHeight() / 3 - optionsButton.getHeight() / 2);
        optionsButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionsButton);

        // Weitere Buttons und Elemente hier hinzufügen...

        gameScreen2 = new GameScreen2(game);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Rendere den GameScreen2
        gameScreen2.render(delta);

        // Rendere den TitleScreen über dem GameScreen2
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        gameScreen2.resize(width, height);
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
        stage.dispose();
        gameScreen2.dispose();
    }
}
