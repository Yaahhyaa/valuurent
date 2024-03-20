package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ScreenGame;

public class OptionScreen implements Screen {

    private Stage stage;
    private Game game;
    private CheckBox musicCheckBox;

    public OptionScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        Label title = new Label("Options Screen", ScreenGame.gameSkin, "big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 2 / 3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        // Lese den aktuellen Musikstatus aus den Einstellungen
        Preferences preferences = Gdx.app.getPreferences("MyPreferences");
        boolean musicEnabled = preferences.getBoolean("musicEnabled", true);

        // Erstelle einen Musik-Schalter (Checkbox)
        musicCheckBox = new CheckBox("Enable Music", ScreenGame.gameSkin);
        musicCheckBox.setChecked(musicEnabled);
        musicCheckBox.setPosition(Gdx.graphics.getWidth() / 2 - musicCheckBox.getWidth() / 2, Gdx.graphics.getHeight() / 2 - musicCheckBox.getHeight() / 2);
        musicCheckBox.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Speichere den neuen Musikstatus in den Einstellungen
                Preferences preferences = Gdx.app.getPreferences("MyPreferences");
                preferences.putBoolean("musicEnabled", musicCheckBox.isChecked());
                preferences.flush();
            }
        });
        stage.addActor(musicCheckBox);

        TextButton backButton = new TextButton("Go Back", ScreenGame.gameSkin);
        backButton.setWidth(Gdx.graphics.getWidth() / 2);
        backButton.setPosition(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2, Gdx.graphics.getHeight() / 4 - backButton.getHeight() / 2);
        backButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TitleScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
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
        stage.dispose();
    }
}
