package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Character.GameActor;

public class GameScreen implements Screen {

    private Stage stage;
    private Game game;
    private boolean gameEnd = false;
    private GameActor background;
    private TextField textField;
    private TextureAtlas atlas;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> animation;
    private Music backgroundMusic;
    private Batch b;
    private float stateTime = 0;

    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        initStage();
        showButtons(); // Call showButtons() to add the button to the stage

        // Setze den Input Processor, um auf die Escape-Taste zu reagieren
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
                    Gdx.app.exit();
                    return true;
                }
                return false;
            }
        });

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Valorant_Main_Menu.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.0f);
        backgroundMusic.play();
    }

    @Override
    public void show() {
        Gdx.app.log("MainScreen", "show");
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameEnd) {
            // Handle game logic
        }



        stage.act();
        stage.draw();

        b.begin();
        stateTime += delta;
        currentFrame = animation.getKeyFrame(stateTime, true);
        // Setzen der Größe des Jets mit setSize
        float jetWidth = 180; // Neue Breite des Jets

        float jetHeight = 275; // Neue Höhe des Jets
        float jetX = 815; // X-Position des Jets
        float jetY = 150; // Y-Position des Jets
        b.draw(currentFrame, jetX, jetY, jetWidth, jetHeight); // Zeichnen des Jets mit der neuen Größe
        b.end();
    }

    // Other overridden methods

    private void initStage() {
        background = new GameActor(0, 0, new Texture("images/Lobby.png"));
        stage.addActor(background);
        // Add other initialization logic here

        atlas = new TextureAtlas(Gdx.files.internal("animation/jet.atlas"));
        animation = new Animation<>(0.1f, atlas.findRegions("idle"), Animation.PlayMode.LOOP);
        b = new SpriteBatch();

    }

    public void showButtons() {



        ImageButtonStyle submitStyle = new ImageButtonStyle();
// Setzen Sie hier die gewünschten Attribute für submitStyle

        ImageButtonStyle cancelStyle = new ImageButtonStyle();
// Setzen Sie hier die gewünschten Attribute für cancelStyle


        ImageButton submitButton = new ImageButton(submitStyle);
        ImageButton cancelButton = new ImageButton(cancelStyle);

        // Lokale Kopien erstellen
        final ImageButton finalSubmitButton = submitButton;
        final ImageButton finalCancelButton = cancelButton;

        // Start Button
        Texture startTexture = new Texture(Gdx.files.internal("images/Ready.png")); // Laden des Bildes
        ImageButtonStyle startStyle = new ImageButtonStyle();
        startStyle.imageUp = new TextureRegionDrawable(new TextureRegion(startTexture));
        ImageButton startButton = new ImageButton(startStyle);
        startButton.setPosition(130, 105);
        startButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen2(game)); // Wechsle zu GameScreen2, wenn der Button losgelassen wird
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(startButton);

        Texture settinTexture = new Texture(Gdx.files.internal("images/Ready.png")); // Laden des Bildes
        ImageButtonStyle settinStyle = new ImageButtonStyle();
        settinStyle.imageUp = new TextureRegionDrawable(new TextureRegion(settinTexture));
        ImageButton settinButton = new ImageButton(settinStyle);
        settinButton.setPosition(1280, 680);
        settinButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(settinButton);


        // Exit-Button
        Texture exitTexture = new Texture(Gdx.files.internal("images/Ready.png")); // Laden des Bildes
        ImageButtonStyle exitStyle = new ImageButtonStyle();
        exitStyle.imageUp = new TextureRegionDrawable(new TextureRegion(exitTexture));
        ImageButton exitButton = new ImageButton(exitStyle);
        exitButton.setPosition(1700, 20);
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit(); // Anwendung beenden, wenn der Exit-Button losgelassen wird
                return false;
            }
        });
        stage.addActor(exitButton);

        // Jett-Button
        Texture settingTexture = new Texture(Gdx.files.internal("images/Ready.png"));
        ImageButtonStyle ssstyle = new ImageButtonStyle();
        ssstyle.imageUp = new TextureRegionDrawable(new TextureRegion(settingTexture));
        ImageButton jettButton = new ImageButton(ssstyle);
        jettButton.setPosition(1150, 300);
        stage.addActor(jettButton);

        Gdx.app.log("GameScreen", "Adding cancel button listener");
        jettButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                if (textField == null) {
                    Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

                    TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
                    textField = new TextField("", textFieldStyle);
                    textField.setPosition(1120, 400);
                    textField.setSize(200, 50);
                    stage.addActor(textField);

                    // Laden der Texturen für die eigenen Buttons
                    Texture submitTexture = new Texture(Gdx.files.internal("images/CONTINUE.png"));
                    Texture cancelTexture = new Texture(Gdx.files.internal("images/Exitt.png"));

                    // Submit Button
                    ImageButtonStyle submitStyle = new ImageButtonStyle();
                    submitStyle.imageUp = new TextureRegionDrawable(new TextureRegion(submitTexture));
                    ImageButton submitButton = new ImageButton(submitStyle);
                    submitButton.setPosition(1320, 376);
                    submitButton.setSize(100,100);
                    stage.addActor(submitButton);

                    // Cancel Button
                    ImageButtonStyle cancelStyle = new ImageButtonStyle();
                    cancelStyle.imageUp = new TextureRegionDrawable(new TextureRegion(cancelTexture));
                    ImageButton cancelButton = new ImageButton(cancelStyle);
                    cancelButton.setPosition(1420, 376);
                    cancelButton.setSize(100, 100);
                    stage.addActor(cancelButton);

                    submitButton.addListener(new InputListener() {
                        @Override
                        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                            Gdx.app.log("GameScreen", "Submitted Text: " + textField.getText());
                            textField.remove();
                            submitButton.remove();
                            cancelButton.remove();
                        }
                    });

                    cancelButton.addListener(new InputListener() {
                        @Override
                        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                            Gdx.app.log("GameScreen", "Cancelled Text Input");
                            textField.remove();
                            submitButton.remove();
                            cancelButton.remove();

                            Gdx.app.log("GameScreen", "Cancel button touched up");
                            if (textField != null) {
                                Gdx.app.log("GameScreen", "Text field exists");
                                Gdx.app.log("GameScreen", "Text in text field: " + textField.getText());
                            } else {
                                Gdx.app.log("GameScreen", "Text field is null");
                            }
                            if (submitButton != null) {
                                Gdx.app.log("GameScreen", "Submit button exists");
                            } else {
                                Gdx.app.log("GameScreen", "Submit button is null");
                            }
                            if (cancelButton != null) {
                                Gdx.app.log("GameScreen", "Cancel button exists");
                            } else {
                                Gdx.app.log("GameScreen", "Cancel button is null");
                            }
                        }
                    });
                }



            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
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
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
    }
}