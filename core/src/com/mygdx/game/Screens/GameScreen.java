package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Character.GameActor;
import com.mygdx.game.ScreenGame;

public class GameScreen implements Screen {

    private Stage stage;
    private Game game;
    private boolean gameEnd = false;
    private GameActor background;

    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        initStage();
        showButtons(); // Call showButtons() to add the button to the stage
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
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new TitleScreen(game));
            dispose(); // Aufräumen der aktuellen Screen-Ressourcen
            return;
        }


        stage.act();
        stage.draw();
    }

    // Other overridden methods

    private void initStage() {
        background = new GameActor(0, 0, new Texture("images/Lobby.png"));
        stage.addActor(background);
        // Add other initialization logic here
    }

    public void showButtons() {
        Texture buttonTexture = new Texture(Gdx.files.internal("images/Ready.png")); // Laden des Bildes
        //Texture buttonPressedTexture = new Texture(Gdx.files.internal("ReadyPressed.png")); // Laden des gedrückten Bildes

        // Erstellen des Button-Stils
        ImageButtonStyle style = new ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(buttonTexture));
        //style.imageDown = new TextureRegionDrawable(new TextureRegion(buttonPressedTexture));

        // Erstellen des Buttons
        ImageButton button = new ImageButton(style);
        button.setPosition(130, 105);

        // Hinzufügen des Listeners zum Button
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

        // Hinzufügen des Buttons zur Bühne
        stage.addActor(button);
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
