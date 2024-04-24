package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Character.Bullet;
import com.mygdx.game.Character.GameActor;
import com.mygdx.game.Character.Hitbox;
import com.mygdx.game.Character.Spieler;

public class GameScreen2 implements Screen {
    private Game game;
    private Stage stage;
    private static Spieler spieler;
    private GameActor background;
    private boolean initialized;
    private Hitbox hitbox;
    private float previousX;
    private float previousY;
    private static final float BULLET_SPEED = 1000;

    public GameScreen2(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        initialized = false;
        hitbox = new Hitbox(1520, 370, 32, 15);
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
        if (!initialized) return;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spieler.move(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && spieler.getX() < 1780) spieler.move(1);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && spieler.getX() > 1125) spieler.move(0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && spieler.getY() < 315) spieler.move(2);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && spieler.getY() > 0) spieler.move(3);

        if (spieler.getBoundary().overlaps(hitbox.getBounds())) {
            spieler.setPosition(previousX, previousY);
        } else {
            previousX = spieler.getX();
            previousY = spieler.getY();
        }

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new TitleScreen(game));
            dispose();
        }
    }

    public void spawnBullet(Spieler spieler, float mouseX, float mouseY) {
        Texture bulletTexture = new Texture("images/bullet.png");
        float weaponX = spieler.getX() + 0;
        float weaponY = spieler.getY() + 60;
        Vector2 direction = calculateBulletDirection(weaponX, weaponY, mouseX, mouseY);
        Bullet bullet = new Bullet(weaponX, weaponY, BULLET_SPEED, direction.x, direction.y, bulletTexture);
        stage.addActor(bullet);
    }

    private Vector2 calculateBulletDirection(float weaponX, float weaponY, float mouseX, float mouseY) {
        Vector2 weaponPosition = new Vector2(weaponX, weaponY);
        Vector2 mousePosition = new Vector2(mouseX, mouseY);
        Vector2 direction = new Vector2();
        direction.set(mousePosition).sub(weaponPosition).nor();
        return direction;
    }

    private void initStage() {
        Texture spielerTexture = new Texture("images/jett.png");
        if (spieler == null) {
            spieler = new Spieler(1800, 0, spielerTexture);
        }
        background = new GameActor(0, 0, new Texture("images/Map.png"));
        stage.addActor(background);
        stage.addActor(spieler);
        Gdx.input.setInputProcessor(stage);
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
