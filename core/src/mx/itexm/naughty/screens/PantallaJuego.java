package mx.itexm.naughty.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itexm.naughty.entities.Controller;
import mx.itexm.naughty.entities.Personaje;

class PantallaJuego extends Pantalla
{
    private static final float ANCHO_MAPA = 1280;
    private static final float ALTO_MAPA = 704;
    private final PantallaInicio juego;
    private Controller controller;
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderer;
    private Personaje personaje;
    private Personaje jhony;

    // HUD, otra cámara con la imagen fija
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;

    // HUD con una escena para los botones y componentes
    private Stage escenaHUD;    // Tendrá un Pad virtual para mover al personaje y el botón de Pausa
    private Skin skin;
    private Music music;


    public PantallaJuego(PantallaInicio juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarMapa();
        crearHUD();
        cargarMusica();
        jhony = new Personaje(new Texture("Personajes/Jhony_caminando.png"));
    }

    private void cargarMusica() {
        AssetManager manager = new AssetManager();
        manager.load("Musica/Nivel1.mp3", Music.class);
        manager.finishLoading();
        music = manager.get("Musica/Nivel1.mp3");
        music.setLooping(true);
        music.play();
    }

    private void cargarMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("Mapas/Test.tmx",TiledMap.class);

        manager.finishLoading(); // Espera
        mapa = manager.get("Mapas/Test.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa);
    }

    private void crearHUD() {

        // Crea la cámara y la vista
        camaraHUD = new OrthographicCamera(ANCHO_JUEGO, ALTO_JUEGO);
        camaraHUD.position.set(ANCHO_JUEGO, ALTO_JUEGO, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO_JUEGO, ALTO_JUEGO, camaraHUD);
        controller = new Controller(0);

        // Comportamiento del pad
        controller.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                //Guarda las velocidades al momento de tomar el evento
                jhony.setVx(pad.getKnobPercentX());
                jhony.setVy(pad.getKnobPercentY());
                if (pad.getKnobPercentX() > 0.10) { // Más de 20% de desplazamiento DERECHA
                    jhony.setEstadoMover(Personaje.EstadoMovimento.DERECHA);
                } else if ( pad.getKnobPercentX() < -0.10 ) {   // Más de 20% IZQUIERDA
                    jhony.setEstadoMover(Personaje.EstadoMovimento.IZQUIERDA);
                } else if ( pad.getKnobPercentY() < -0.10) {
                    jhony.setEstadoMover(Personaje.EstadoMovimento.ABAJO);
                } else if( pad.getKnobPercentY() > 0.10) {
                    jhony.setEstadoMover(Personaje.EstadoMovimento.ARRIBA);
                } else {
                    jhony.setEstadoMover(Personaje.EstadoMovimento.QUIETO);
                }
            }
        });
        controller.setColor(1,1,1,0.7f);   // Transparente

        // Crea la escena y agrega el pad
        escenaHUD = new Stage(vistaHUD);    // Escalar con esta vista
        escenaHUD.addActor(controller);
        Gdx.input.setInputProcessor(escenaHUD);
    }

    @Override
    public void render(float delta) {
        // Actualiza todos los objetos
        jhony.actualizar(mapa);
        actualizarCamara();
        // Cámara fondo

        borrarPantalla(0.35f,0.55f,1);
        batchJuego.setProjectionMatrix(camaraJuego.combined);

        renderer.setView(camaraJuego);
        renderer.render();

        batchJuego.begin();
        jhony.render(batchJuego);
        batchJuego.end();

        // Cámara HUD
        batchJuego.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();
    }

    private void actualizarCamara() {

        // Depende de la posición del personaje. Siempre sigue al personaje
        float posX = jhony.getX();
        float posY = jhony.getY();
        // Primera mitad de la pantalla

        if (posX < ANCHO_JUEGO/2) {
            camaraJuego.position.x = ANCHO_JUEGO/2;
        }
        else if (posX > ANCHO_MAPA - ANCHO_JUEGO/2) {   // Última mitad de la pantalla
            camaraJuego.position.x = ANCHO_MAPA -ANCHO_JUEGO/2;
        }
        else {// En 'medio' del mapa
            camaraJuego.position.x = posX;
        }
        if (posY > ALTO_MAPA - ALTO_JUEGO/2){
            camaraJuego.position.y = ALTO_MAPA - ALTO_JUEGO/2;
        }
        else if (posY < ALTO_JUEGO/2){
            camaraJuego.position.y = ALTO_JUEGO/2;
        }
        else{
            camaraJuego.position.y= posY;
        }
        camaraJuego.update();
    }

    @Override
    public void resize(int width, int height) {
        vistaJuego.update(width, height);
        vistaHUD.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        mapa.dispose();
        escenaHUD.dispose();
        skin.dispose();
        music.dispose();
    }
}