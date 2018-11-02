package mx.itesm.naughty.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class MainScreen implements Screen {
    public static final int ANCHO_JUEGO = 800;
    public static final int ALTO_JUEGO = 480;
    public static final float PPM = 100f;

    public SpriteBatch batch;
    public OrthographicCamera gameCam;
    public Viewport gamePort;

    public MainScreen(){
        gameCam = new OrthographicCamera();

        gameCam.update();

        gamePort = new StretchViewport(ANCHO_JUEGO / PPM, ALTO_JUEGO / PPM, gameCam);
        batch = new SpriteBatch();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        batch.dispose();
    }
}
