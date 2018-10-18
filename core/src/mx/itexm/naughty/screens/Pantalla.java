package mx.itexm.naughty.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Pantalla implements Screen {
    public static final float ANCHO_PANTALLA = 1280;
    public static final float ALTO_PANTALLA = 720;
    public static final float ANCHO_JUEGO = 640;
    public static final float ALTO_JUEGO = 360;


    protected OrthographicCamera camaraPantalla;
    protected OrthographicCamera camaraJuego;
    protected Viewport vistaPantalla;
    protected Viewport vistaJuego;
    protected SpriteBatch batchPantalla;
    protected SpriteBatch batchJuego;

    public Pantalla() {
        camaraPantalla = new OrthographicCamera(ANCHO_PANTALLA, ALTO_PANTALLA);
        camaraJuego = new OrthographicCamera(ANCHO_PANTALLA, ALTO_PANTALLA);

        camaraPantalla.position.set(ANCHO_PANTALLA/2, ALTO_PANTALLA/2, 0);
        camaraJuego.position.set(ANCHO_JUEGO/2, ALTO_JUEGO/2, 0);
        camaraPantalla.update();
        camaraJuego.update();

        vistaPantalla = new StretchViewport(ANCHO_PANTALLA, ALTO_PANTALLA, camaraPantalla);
        vistaJuego = new StretchViewport(ANCHO_JUEGO, ALTO_JUEGO, camaraJuego);

        batchPantalla = new SpriteBatch();
        batchJuego = new SpriteBatch();
    }

    public void borrarPantalla(float r, float g, float b) {
        Gdx.gl.glClearColor(r, g, b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        batchPantalla.dispose();
        batchJuego.dispose();
    }

    @Override
    public void resize(int width, int height) {
        vistaPantalla.update(width, height);
        vistaJuego.update(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }
}
