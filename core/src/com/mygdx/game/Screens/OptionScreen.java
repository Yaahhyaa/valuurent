package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.ScreenGame;
import com.mygdx.game.Screens.GameScreen2;
import com.mygdx.game.Screens.TitleScreen;

public class OptionScreen implements Screen {

    private Stage stage;
    private Game game;
    private CheckBox musicCheckBox;
    private Slider musicVolumeSlider;
    private Music backgroundMusic;

    public OptionScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        Label title = new Label("Options Screen", ScreenGame.gameSkin, "big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight() * 2 / 3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        musicVolumeSlider = new Slider(0.0f, 100.0f, 1.0f, false, ScreenGame.gameSkin); // Slider für Prozentwerte von 0 bis 100
        musicVolumeSlider.setWidth(200);
        musicVolumeSlider.setValue(100.0f); // Beginne mit voller Lautstärke
        musicVolumeSlider.setPosition(Gdx.graphics.getWidth() / 2 - musicVolumeSlider.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        musicVolumeSlider.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Preferences preferences = Gdx.app.getPreferences("MyPreferences");
                preferences.putFloat("musicVolume", musicVolumeSlider.getValue()); // Speichere den Wert in Prozent
                preferences.flush();

                if (backgroundMusic != null) {
                    float volume = musicVolumeSlider.getValue() / 100.0f; // Konvertiere Prozent in Dezimalwert
                    backgroundMusic.setVolume(volume);
                }
            }
        });
        stage.addActor(musicVolumeSlider);

        TextButton backButton = new TextButton("Go Back", ScreenGame.gameSkin);
        backButton.setWidth(Gdx.graphics.getWidth() / 2);
        backButton.setPosition(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2, Gdx.graphics.getHeight() / 3 - backButton.getHeight() / 2);
        backButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Preferences preferences = Gdx.app.getPreferences("MyPreferences");
                preferences.putFloat("musicVolume", musicVolumeSlider.getValue()); // Speichere den Wert in Prozent
                preferences.flush();

                if (backgroundMusic != null) {
                    float volume = musicVolumeSlider.getValue() / 100.0f; // Konvertiere Prozent in Dezimalwert
                    backgroundMusic.setVolume(volume);
                }

                if (musicVolumeSlider.getValue() == 0.0f) {
                    if (backgroundMusic != null) {
                        backgroundMusic.setVolume(0.0f); // Setze die Lautstärke auf 0, wenn der Regler ganz links ist
                    }
                }
                return true;
            }
        });
        stage.addActor(backButton);

        Texture dummyTexture = new Texture(Gdx.files.internal("images/Settings.png"));
        ImageButtonStyle dummyStyle = new ImageButtonStyle();
        dummyStyle.imageUp = new TextureRegionDrawable(new TextureRegion(dummyTexture));
        ImageButton dummyButton = new ImageButton(dummyStyle);
        dummyButton.setSize(100, 100);
        dummyButton.setPosition(1200, 800);
        dummyButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen2(game));
            }
        });
        stage.addActor(dummyButton);

        // Lade und starte die Hintergrundmusik
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Valorant_Main_Menu.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.0f);
        backgroundMusic.play();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
    }
}
