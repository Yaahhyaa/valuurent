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
import com.mygdx.game.Character.HealthBar;
import com.mygdx.game.Client.GameWebSocketClient;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class GameScreen2 implements Screen {
    private Game game;
    private Stage stage;
    private Spieler localPlayer;
    private Spieler remotePlayer;
    private GameActor background;
    private boolean initialized;
    private Hitbox hitbox;
    private Hitbox hitbox2;
    private Hitbox hitbox3;
    private Hitbox hitbox4;
    private float previousX;
    private float previousY;
    private static final float BULLET_SPEED = 1000;
    private ArrayList<Bullet> bullets;
    private int shotCounter;
    private float shotCooldownTimer;
    private static final int SHOT_LIMIT = 20;
    private static final float COOLDOWN_DURATION = 5f;
    private HealthBar localHealthBar;
    private HealthBar remoteHealthBar;
    private GameWebSocketClient webSocketClient;

    public GameScreen2(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        initialized = false;
        hitbox = new Hitbox(1530, 390, 32, 15);
        hitbox2 = new Hitbox(1320, 240, 32, 1);
        hitbox3 = new Hitbox(375, 390, 2, 15);
        hitbox4 = new Hitbox(565, 250, 0.2f, 0.6f);
        bullets = new ArrayList<>();
        shotCounter = 0;
        shotCooldownTimer = 0;

        try {
            webSocketClient = new GameWebSocketClient(this);
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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

        handleInput(delta);

        if (localPlayer.getBoundary().overlaps(hitbox.getBounds()) ||
                localPlayer.getBoundary().overlaps(hitbox2.getBounds())) {
            localPlayer.setPosition(previousX, previousY);
        } else {
            previousX = localPlayer.getX();
            previousY = localPlayer.getY();
        }

        shotCooldownTimer -= delta; // Timer für Cooldown aktualisieren

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new TitleScreen(game));
            dispose();
        }

        sendPlayerCoordinates(localPlayer);
    }

    private void handleInput(float delta) {
        localPlayer.move(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.D) && localPlayer.getX() < 1780) localPlayer.move(1);
        if (Gdx.input.isKeyPressed(Input.Keys.A) && localPlayer.getX() > 980) localPlayer.move(0);
        if (Gdx.input.isKeyPressed(Input.Keys.W) && localPlayer.getY() < 315) localPlayer.move(2);
        if (Gdx.input.isKeyPressed(Input.Keys.S) && localPlayer.getY() > 0) localPlayer.move(3);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (shotCooldownTimer <= 0) {
                float mouseX = Gdx.input.getX();
                float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (localPlayer != null) {
                    spawnBullet(localPlayer, mouseX, mouseY);
                    shotCounter++;
                    if (shotCounter >= SHOT_LIMIT) {
                        shotCooldownTimer = COOLDOWN_DURATION;
                        shotCounter = 0; // Zurücksetzen des Schusszählers nach dem Cooldown
                    }
                }
            }
        }
    }

    public void spawnBullet(Spieler spieler, float mouseX, float mouseY) {
        Texture bulletTexture = new Texture("images/bullet.png");
        float weaponX = spieler.getX() + 0;
        float weaponY = spieler.getY() + 60;
        Vector2 direction = calculateBulletDirection(weaponX, weaponY, mouseX, mouseY);
        Bullet bullet = new Bullet(weaponX, weaponY, BULLET_SPEED, direction.x, direction.y, bulletTexture);
        stage.addActor(bullet);
        bullets.add(bullet); // Fügen Sie die Kugel der Liste hinzu
    }

    private Vector2 calculateBulletDirection(float weaponX, float weaponY, float mouseX, float mouseY) {
        Vector2 weaponPosition = new Vector2(weaponX, weaponY);
        Vector2 mousePosition = new Vector2(mouseX, mouseY);
        Vector2 direction = new Vector2();
        direction.set(mousePosition).sub(weaponPosition).nor();
        return direction;
    }

    private void initStage() {
        Texture playerTexture = new Texture("images/jett.png");
        localPlayer = new Spieler(1800, 0, playerTexture);
        Texture enemyTexture = new Texture("animation/phnx.png");
        remotePlayer = new Spieler(1800, 0, enemyTexture);

        localHealthBar = new HealthBar(localPlayer, 200, 20);
        remoteHealthBar = new HealthBar(remotePlayer, 200, 20);

        background = new GameActor(0, 0, new Texture("images/Map.png"));
        stage.addActor(background);
        stage.addActor(localPlayer);
        stage.addActor(remotePlayer);
        stage.addActor(localHealthBar);
        stage.addActor(remoteHealthBar);
        Gdx.input.setInputProcessor(stage);
    }

    private void sendPlayerCoordinates(Spieler spieler) {
        if (webSocketClient != null) {
            Vector2 coordinates = spieler.getCoordinates();
            JSONObject message = new JSONObject();
            JSONObject playerObject = new JSONObject();
            playerObject.put("x", coordinates.x);
            playerObject.put("y", coordinates.y);
            message.put("player", playerObject);
            webSocketClient.send(message.toString());
        }
    }

    public void updateRemotePlayerCoordinates(float x, float y) {
        if (remotePlayer != null) {
            remotePlayer.setPosition(x, y);
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
        stage.dispose();
    }
}
