package com.mygdx.game.Screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ScreenGame;

/**
 * Created by julienvillegas on 17/01/2017.
 */
public class GameScreen2 implements Screen {

    private Stage stage;
    private Game game;

    SpriteBatch batch;
    Sprite backGroundSprite;
    Sprite teacherSprite;
    Sprite catchSprite;
    Sprite touchSprite;
    int counter =0;
    Boolean touched = false;
    private BitmapFont font;
    Boolean stilltouched = false;
    Boolean gameEnd = true;

    public GameScreen2(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        gameEnd = false;
        backGroundSprite = new Sprite(new Texture("background_800.jpg"));
        backGroundSprite.setPosition(0,0);
        teacherSprite = new Sprite(new Texture("sma.png"));
        teacherSprite.setPosition(20,20);
        catchSprite = new Sprite(new Texture("wi_rund_20.png"));
        catchSprite.setPosition(200,200);
        touchSprite = new Sprite(new Texture("beruehrt.png"));
        touchSprite.setPosition(200,20);

    }

    @Override
    public void show() {
        Gdx.app.log("MainScreen","show");
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        if (!gameEnd) {
            // zuerst den Input
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) teacherSprite.translateX(-1);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) teacherSprite.translateX(1);
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) teacherSprite.translateY(1);
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) teacherSprite.translateY(-1);
            // Berechnungen = update
            // Kollisionsberechnung
            double dxr = teacherSprite.getWidth() / 2;
            double dyr = catchSprite.getHeight() / 2;
            double dx = (teacherSprite.getX() + dxr) - (catchSprite.getX() + dyr);
            double dy = (teacherSprite.getY() + dxr) - (catchSprite.getY() + dyr);
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < dxr + dyr) {
                //Collision detected
                System.out.println("Hit Circle");
                touched = true;
                if (!stilltouched) {
                    counter++;
                    stilltouched = true;
                }
            } else {
                touched = false;
                stilltouched = false;
            }


            // Render, Darstellung der Objekte am Schirm
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch = (SpriteBatch) stage.getBatch();
            batch.begin();
            backGroundSprite.draw(batch);
            teacherSprite.draw(batch);
            catchSprite.draw(batch);
            if (touched) {
                touchSprite.draw(batch);
                this.showButtons();
                gameEnd = true;
            }
            font.draw(batch, "Hits:" + counter, 0, 475);
            batch.end();
        }

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

    public void showButtons() {
        Label title = new Label("You died!!!", ScreenGame.gameSkin,"big-black");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        TextButton backButton = new TextButton("Back",ScreenGame.gameSkin);
        backButton.setWidth(Gdx.graphics.getWidth()/2);
        backButton.setPosition(Gdx.graphics.getWidth()/2-backButton.getWidth()/2,Gdx.graphics.getHeight()/4-backButton.getHeight()/2);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TitleScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backButton);
    }
}
