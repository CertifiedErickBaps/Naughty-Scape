package mx.itexm.naughty.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itexm.naughty.entities.Controller;
import mx.itexm.naughty.entities.EstadoJuego;
import mx.itexm.naughty.entities.Personaje;

class PantallaJuego extends Pantalla
{

    // Botón atrás
    private Texture texturaBtnAtras;

    // Botón pausa
    private Texture texturaBtnPausa;

    // PAUSA
    private EscenaPausa escenaPausa;      // Muestra la pausa como pop-up

    // Estados. ahora el juego puede estar JUGANDO, PAUSADO, etc
    private EstadoJuego estado;

    private float ANCHO_MAPA = 1280;
    private float ALTO_MAPA = 2880;
    private final PantallaInicio juego;
    private Controller controller;
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderer;
    private Personaje jhony;

    // HUD, otra cámara con la imagen fija
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;

    // HUD con una escena para los botones y componentes
    private Stage escenaHUD;    // Tendrá un Pad virtual para mover al personaje y el botón de Pausa
    private Skin skin;
    private Music music;
    private static Texture corazon;

    public PantallaJuego(PantallaInicio juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarMapa();
        jhony = new Personaje(new Texture("Personajes/Jhony_walkUpDown.png"));
        crearHUD();
        cargarMusica();
        estado = EstadoJuego.JUGANDO;

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
        manager.load("Mapas/MapaNivel1.tmx",TiledMap.class);
        manager.finishLoading(); // Espera
        mapa = manager.get("Mapas/MapaNivel1.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa);
    }

    private void crearHUD() {

        // Crea la cámara y la vista
        camaraHUD = new OrthographicCamera(ANCHO_JUEGO, ALTO_JUEGO);
        camaraHUD.position.set(ANCHO_JUEGO, ALTO_JUEGO, 0);
        camaraHUD.update();

        //Corazones jugador
        corazon = new Texture("Personajes/corazon.png");
        Image corazonImagen = new Image(corazon);
        corazonImagen.setPosition(0, ALTO_JUEGO - corazonImagen.getHeight());

        //Boton x
        TextureRegionDrawable trdX = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/A.png")));
        TextureRegionDrawable trdXPs = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/APres.png")));
        final ImageButton btnX = new ImageButton(trdX, trdXPs);
        btnX.setPosition(ANCHO_JUEGO - btnX.getWidth(), 0);


        // Botón atrás y botón pausa
        TextureRegionDrawable trdPausa = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/Pause.png")));
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/PausaPres.png")));
        ImageButton btnPausa = new ImageButton(trdPausa, trdBack);
        btnPausa.setPosition(ANCHO_JUEGO - btnPausa.getWidth(), ALTO_JUEGO - btnPausa.getHeight());

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
                }
                else if ( pad.getKnobPercentX() < -0.10 ) {   // Más de 20% IZQUIERDA
                    jhony.setEstadoMover(Personaje.EstadoMovimento.IZQUIERDA);
                }
                else if ( pad.getKnobPercentY() < -0.10) {
                    jhony.setEstadoMover(Personaje.EstadoMovimento.ABAJO);
                }
                else if( pad.getKnobPercentY() > 0.10) {
                    jhony.setEstadoMover(Personaje.EstadoMovimento.ARRIBA);
                }
                else {
                    jhony.setEstadoMover(Personaje.EstadoMovimento.QUIETO);
                }
            }
        });
        controller.setColor(1,1,1,0.7f);   // Transparente

        // Accion boton golpe
        btnX.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(btnX.isChecked()){
                    jhony.setEstadoMover(Personaje.EstadoMovimento.ATAQUE_GOLPE_UP);
                    btnX.setChecked(true);
                } else {
                    jhony.setEstadoMover(Personaje.EstadoMovimento.QUIETO);
                    btnX.setChecked(false);
                }
            }
        });

        //Acciones boton pausa
        btnPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(escenaPausa == null){
                    escenaPausa = new EscenaPausa(vistaHUD, batchJuego);
                }
                estado = EstadoJuego.PAUSADO;
                Gdx.input.setInputProcessor(escenaPausa);
            }
        });

        // Crea la escena y agrega el pad
        escenaHUD = new Stage(vistaHUD);    // Escalar con esta vista
        escenaHUD.addActor(controller);
        escenaHUD.addActor(corazonImagen);
        escenaHUD.addActor(btnX);
        escenaHUD.addActor(btnPausa);
        //corazonImagen.remove();
        //botonXImagen.remove();

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

        // Checar condicional

        if (estado==EstadoJuego.PAUSADO) {
            escenaPausa.draw(); // Solo si está pausado muestra la imagen
        }
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

    private class EscenaPausa extends Stage
    {
        // La escena que se muestra cuando está pausado
        public EscenaPausa(final Viewport vistaHUD, SpriteBatch batchJuego) {
            super(vistaHUD,batchJuego);
            // Crear rectángulo transparente
            Pixmap pixmap = new Pixmap((int)(ANCHO_JUEGO*0.7f), (int)(ALTO_JUEGO*0.8f), Pixmap.Format.RGBA8888 );
            pixmap.setColor( 1f, 1f, 1f, 0.65f );
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f*ANCHO_JUEGO, 0.1f*ALTO_JUEGO);
            this.addActor(imgRectangulo);

            // Sonido
            Texture texturaBtnSonido = new Texture("Botones/Sonido.png");
            Texture texturaBtnSonidoPres = new Texture("Botones/SonidoPres.png");
            TextureRegionDrawable trdSonido = new TextureRegionDrawable(new TextureRegion(texturaBtnSonido));
            TextureRegionDrawable trdSonidoPres = new TextureRegionDrawable(new TextureRegion(texturaBtnSonidoPres));
            final ImageButton btnSonido = new ImageButton(trdSonido, trdSonidoPres);
            btnSonido.setPosition(ANCHO_JUEGO/2-btnSonido.getWidth()/2, 0.80f*ALTO_JUEGO - btnSonido.getHeight() /2);
            btnSonido.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(btnSonido.isChecked()) {
                        btnSonido.setChecked(true);
                        music.pause();
                    } else {
                        btnSonido.setChecked(false);
                        music.play();
                    }
                }
            });
            this.addActor(btnSonido);

            // Salir
            Texture texturaBtnSalir = new Texture("Botones/Salir.png");
            Texture texturaBtnSalirPres = new Texture("Botones/SalirPres.png");
            TextureRegionDrawable trdSalir = new TextureRegionDrawable(new TextureRegion(texturaBtnSalir));
            TextureRegionDrawable trdSalirPres = new TextureRegionDrawable(new TextureRegion(texturaBtnSalirPres));
            final ImageButton btnSalir = new ImageButton(trdSalir, trdSalirPres);
            btnSalir.setPosition(ANCHO_JUEGO/2-btnSalir.getWidth()/2, 0.35f* ALTO_JUEGO - btnSalir.getHeight() /2);
            btnSalir.addListener(new ClickListener(){
                // falta accion de salir
            });
            this.addActor(btnSalir);

            // Continuar
            Texture texturaBtnContinuar = new Texture("Botones/Continuar.png");
            Texture texturaBtnContinuarPres = new Texture("Botones/ContinuarPres.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(new TextureRegion(texturaBtnContinuar));
            TextureRegionDrawable trdContinuarPres = new TextureRegionDrawable(new TextureRegion(texturaBtnContinuarPres));
            ImageButton btnContinuar = new ImageButton(trdContinuar, trdContinuarPres);
            btnContinuar.setPosition(ANCHO_JUEGO/2-btnContinuar.getWidth()/2, 0.55f* ALTO_JUEGO - btnContinuar.getHeight() /4);

            btnContinuar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    estado = EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(escenaHUD); // No debería crear uno nuevo
                }
            });
            this.addActor(btnContinuar);
        }
    }

}