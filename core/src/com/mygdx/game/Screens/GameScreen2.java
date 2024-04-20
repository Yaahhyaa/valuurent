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
    private Spieler spieler;
    private GameActor background;

    public GameScreen2(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        initStage();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
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
    }

    private void initStage() {
        Texture spielerTexture = new Texture("images/jett.png");
        spieler = new Spieler((int)spieler.getX(), (int)spieler.getY(), spielerTexture); // Setze die Startposition des Spielers auf (1800, 0)
        background = new GameActor(0, 0, new Texture("images/Map.png"));
        stage.addActor(background);
        stage.addActor(spieler);
        Gdx.input.setInputProcessor(stage);
    }
}
