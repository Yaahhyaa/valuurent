package com.mygdx.game.Screens;

//Auswahl screeen

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

    private GameActor background;

    public TitleScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        Texture texture = new Texture(Gdx.files.internal("images/Logo.png"));
        Image image = new Image(texture);
        image.setPosition(Gdx.graphics.getWidth() / 2 - image.getWidth() / 2, Gdx.graphics.getHeight() * 2 / 3.6f);
        stage.addActor(image);

        Texture buttonTexture = new Texture(Gdx.files.internal("images/CONTINUE.png")); // Laden des Bildes
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        ImageButton button = new ImageButton(style);
        button.setPosition(800, 450);

        button.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen2(game)); // Wechsle zu GameScreen2, wenn der Button losgelassen wird
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(button);

        Texture Bt = new Texture(Gdx.files.internal("images/Settings.png")); // Laden des Bildes
        ImageButton.ImageButtonStyle ss = new ImageButton.ImageButtonStyle();
        ss.imageUp = new TextureRegionDrawable(new TextureRegion(Bt));
        ImageButton bb = new ImageButton(ss);
        bb.setPosition(800, 250);
        bb.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(bb);

        Texture exitTexture = new Texture(Gdx.files.internal("images/Exitt.png")); // Laden des Bildes
        ImageButton.ImageButtonStyle exitStyle = new ImageButton.ImageButtonStyle();
        exitStyle.imageUp = new TextureRegionDrawable(new TextureRegion(exitTexture));
        ImageButton exitButton = new ImageButton(exitStyle);
        exitButton.setPosition(800, 50);
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(exitButton);


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
