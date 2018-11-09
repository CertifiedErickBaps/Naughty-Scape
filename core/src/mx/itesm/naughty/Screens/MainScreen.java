package mx.itesm.naughty.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class MainScreen implements Screen {
    public static final float ANCHO_PANTALLA = 1280;
    public static final float ALTO_PANTALLA = 720;
    public static final float ANCHO_JUEGO = 800;
    public static final float ALTO_JUEGO = 480;
    public static final float PPM = 100f;
    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short ARMA_BIT = 4;
    public static final short COFRE_BIT = 8;
    public static final short DESTROY_BIT = 16;

    public static SpriteBatch batch;
    public static OrthographicCamera gameCam;
    public static Viewport gamePort;

    public static AssetManager manager;

    public MainScreen(){
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(ANCHO_PANTALLA, ALTO_PANTALLA, gameCam);
        gameCam.position.set(gamePort.getWorldWidth() / 2,gamePort.getWorldHeight() / 2, 0);
        gameCam.update();


        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("Musica/chest.mp3", Music.class);
        manager.load("Musica/menuSound.mp3", Music.class);
        manager.load("Musica/nivel1.mp3", Music.class);
        manager.load("Musica/nivel2.mp3", Music.class);
        manager.load("Musica/punch.mp3", Music.class);
        manager.load("Musica/error.mp3", Music.class);
        manager.finishLoading();
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
        batch.dispose();
        manager.dispose();
    }
}
