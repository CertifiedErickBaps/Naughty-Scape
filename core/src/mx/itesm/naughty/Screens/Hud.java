package mx.itesm.naughty.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.naughty.Controller;
import mx.itesm.naughty.MainGame;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    private float timeCount;
    private static Integer score;
    private Label countDownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label naughtyLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new StretchViewport(MainScreen.ANCHO_JUEGO, MainScreen.ALTO_JUEGO,new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        naughtyLabel = new Label("Jhony", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(naughtyLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(countDownLabel).expandX();
        stage.addActor(table);

        // Hace que el pad se pueda usar
        Gdx.input.setInputProcessor(stage);


    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countDownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
