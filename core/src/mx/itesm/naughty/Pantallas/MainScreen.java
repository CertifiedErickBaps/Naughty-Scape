package mx.itesm.naughty.Pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.naughty.Screens.PlayScreen;

import static mx.itesm.naughty.MainGame.ALTO_PANTALLA;
import static mx.itesm.naughty.MainGame.ANCHO_PANTALLA;


public class MainScreen implements Screen {
    public static OrthographicCamera gameCam;
    public static Viewport gamePort;

    public MainScreen(){
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(ANCHO_PANTALLA, ALTO_PANTALLA, gameCam);
        gameCam.position.set(gamePort.getWorldWidth() / 2,gamePort.getWorldHeight() / 2, 0);
        gameCam.update();
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
        dispose();
    }

    @Override
    public void dispose() {
    }
}
