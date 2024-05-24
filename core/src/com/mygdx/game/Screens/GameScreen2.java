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
import com.mygdx.game.actors.HealthBar;

import java.util.ArrayList;

public class GameScreen2 implements Screen {
    private Game game;
    private Stage stage;
    private static Spieler spieler;
    private GameActor background;
    private boolean initialized;
    private Hitbox hitbox;
    private Hitbox hitbox1;
    private Hitbox hitbox2;
    private Hitbox hitbox3;
    private Hitbox hitbox4;
    private float previousX;
    private float previousY;
    private static final float BULLET_SPEED = 1000;

    private ArrayList<Bullet> bullets;
    private int shotCounter; // Zähler für abgefeuerte Schüsse
    private float shotCooldownTimer; // Timer für Cooldown zwischen den Schüssen
    private static final int SHOT_LIMIT = 7;
    private static final float COOLDOWN_DURATION = 5f; // Cooldown-Dauer in Sekunden
    private HealthBar healthBar;

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
        if (Gdx.input.isKeyPressed(Input.Keys.D) && spieler.getX() < 1780) spieler.move(1);
        if (Gdx.input.isKeyPressed(Input.Keys.A) && spieler.getX() > 980) spieler.move(0);
        if (Gdx.input.isKeyPressed(Input.Keys.W) && spieler.getY() < 315) spieler.move(2);
        if (Gdx.input.isKeyPressed(Input.Keys.S) && spieler.getY() > 0) spieler.move(3);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (shotCooldownTimer <= 0) {
                float mouseX = Gdx.input.getX();
                float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
                if (spieler != null) {
                    spawnBullet(spieler, mouseX, mouseY);
                    shotCounter++;
                    if (shotCounter >= SHOT_LIMIT) {
                        shotCooldownTimer = COOLDOWN_DURATION;
                        shotCounter = 0; // Zurücksetzen des Schusszählers nach dem Cooldown
                    }
                }
            }
        }

        // Reduziere den Cooldown-Timer
        if (shotCooldownTimer > 0) {
            shotCooldownTimer -= delta;
        }

        // Überprüfe Kollisionen zwischen Kugeln und Hitboxen
        for (Bullet bullet : new ArrayList<>(bullets)) {
            bullet.update(delta);
            stage.addActor(bullet);
            for (Hitbox currentHitbox : new Hitbox[]{hitbox, hitbox2, hitbox3, hitbox4}) {
                if (bullet.getBoundary().overlaps(currentHitbox.getBounds())) {
                    bullet.remove();
                    bullets.remove(bullet);
                    break; // Beende die Schleife, wenn eine Kollision erkannt wurde
                }
            }
        }

        if (spieler.getBoundary().overlaps(hitbox.getBounds()) ||
                spieler.getBoundary().overlaps(hitbox2.getBounds()) ||
                spieler.getBoundary().overlaps(hitbox3.getBounds()) ||
                spieler.getBoundary().overlaps(hitbox4.getBounds())) {
            spieler.setPosition(previousX, previousY);
            spieler.decreaseHealth(15); // Wenn der Spieler getroffen wird, werden 15 HP abgezogen
        } else {
            previousX = spieler.getX();
            previousY = spieler.getY();
        }

        healthBar.setPosition(10, Gdx.graphics.getHeight() - 30); // Position des Balkens
        healthBar.act(delta);

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
        bullets.add(bullet);
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
            spieler = new Spieler(1800, 0, spielerTexture, this);
        }
        background = new GameActor(0, 0, new Texture("images/Map.png"));
        stage.addActor(background);
        stage.addActor(spieler);
        Gdx.input.setInputProcessor(stage);

        healthBar = new HealthBar(spieler, 200, 20); // Breite und Höhe des Balkens
        stage.addActor(healthBar); // Hinzufügen des healthBar Actors zur Bühne
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
