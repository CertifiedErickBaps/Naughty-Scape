package mx.itesm.naughty.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.naughty.Box2DCreator;
import mx.itesm.naughty.Controller;
import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Sprites.Player;

public class PlayScreen extends MainScreen {
    public enum EstadoJuego {JUGANDO, PAUSADO}
    private EstadoJuego estado;
    private MainGame game;
    private TextureAtlas atlas;
    private Hud hud;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private EscenaPausa escenaPausa;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;

    private Controller controller;
    //Sistema de particulas
    //private ParticleEffect sp;

    public PlayScreen(MainGame game){
        estado = EstadoJuego.JUGANDO;
        atlas = new TextureAtlas("naughty.pack");

        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(ANCHO_JUEGO / PPM, ALTO_JUEGO / PPM, gameCam);
        hud = new Hud(batch);

        LoadMap();

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        new Box2DCreator(world, map);
        player = new Player(world, this);

        // Pad para mover jugador
        crearAcciones();
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    private void LoadMap() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("Mapas/Nivel1.tmx",TiledMap.class);
        manager.finishLoading(); // Espera
        map = manager.get("Mapas/Nivel1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
    }
    
    public void update(float dt){
        handleInput(dt);
        world.step(1/ 60f, 6, 2);

        player.update(dt);
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;
        gameCam.update();
        renderer.setView(gameCam);
    }

    private void handleInput(float dt) {

        // Comportamiento del pad
        controller.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                //Guarda las velocidades al momento de tomar el evento
                if ((pad.getKnobPercentX() > 0.10) && (player.b2body.getLinearVelocity().x <= 2.3f)) { // Más de 20% de desplazamiento DERECHA
                    player.b2body.applyLinearImpulse(new Vector2(0.3f, 0), player.b2body.getWorldCenter(), true);
                }
                else if ( (pad.getKnobPercentX() < -0.10) && (player.b2body.getLinearVelocity().x >= -2.3f)) {   // Más de 20% IZQUIERDA
                    player.b2body.applyLinearImpulse(new Vector2(-0.3f, 0), player.b2body.getWorldCenter(), true);
                }
                else if ( (pad.getKnobPercentY() < -0.10) && (player.b2body.getLinearVelocity().y >= -2.3f)) {
                    player.b2body.applyLinearImpulse(new Vector2(0, -0.3f), player.b2body.getWorldCenter(), true);
                }
                else if( (pad.getKnobPercentY() > 0.10) && (player.b2body.getLinearVelocity().y <= 2.3f)) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 0.3f), player.b2body.getWorldCenter(), true);
                }
                else {
                    player.b2body.setLinearVelocity(0,0);
                }
            }
        });



        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0, 0.3f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            player.b2body.applyLinearImpulse(new Vector2(0, -0.3f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            player.b2body.applyLinearImpulse(new Vector2(0.3f, 0f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            player.b2body.applyLinearImpulse(new Vector2(-0.3f, 0f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
            player.b2body.setLinearVelocity(0,0);
        }

    }

    @Override
    public void show() {
        /*
        //Inicializa el sistema de particulas
        sp = new ParticleEffect();
        sp.load(Gdx.files.internal("lluvia.pe"), Gdx.files.internal(""));
        sp.getEmitters().get(0).setPosition(0, MainGame.ALTO_JUEGO);
        sp.start();
        */


    }

    private void crearAcciones() {
        //Boton A
        TextureRegionDrawable trdA = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnA.png")));
        TextureRegionDrawable trdAPs = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/APres.png")));
        final ImageButton btnA = new ImageButton(trdA, trdAPs);
        btnA.setPosition(0.80f*ANCHO_JUEGO - btnA.getWidth(), 10);

        // Botón pausa
        TextureRegionDrawable trdPausa = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/Pause.png")));
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/PausaPres.png")));
        ImageButton btnPausa = new ImageButton(trdPausa, trdBack);
        btnPausa.setPosition(ANCHO_JUEGO - btnPausa.getWidth(), ALTO_JUEGO - btnPausa.getHeight());

        // Boton B
        TextureRegionDrawable trdB = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnB.png")));
        TextureRegionDrawable trdBPs = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BPres.png")));
        final ImageButton btnB = new ImageButton(trdB, trdBPs);
        btnB.setPosition(0.92f*ANCHO_JUEGO - btnB.getWidth(), 30);

        controller = new Controller(0);
        controller.setColor(1,1,1,0.7f);



        // Accion botonA golpe
        btnA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(btnA.isChecked()){

                } else {

                }
            }
        });

        // Accion botonB agarre/cambio de sprite
        btnB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(btnB.isChecked()){

                } else {

                }
            }
        });

        //Acciones boton pausa
        btnPausa.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(escenaPausa == null){
                    escenaPausa = new EscenaPausa(gamePort, batch);
                }
                estado = EstadoJuego.PAUSADO;
                Gdx.input.setInputProcessor(escenaPausa);
            }
        });

        // Crea la escena y agrega el pad
        hud.stage = new Stage(gamePort);    // Escalar con esta vista
        hud = new Hud(batch);
        hud.stage.addActor(controller);
        hud.stage.addActor(btnA);
        hud.stage.addActor(btnB);
        hud.stage.addActor(btnPausa);
    }

    @Override
    public void render(float delta) {
        //sp.update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        renderer.render();
        b2dr.render(world,gameCam.combined);

        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        player.draw(batch);
        //sp.draw(game.batch);
        batch.end();

        if(estado == EstadoJuego.PAUSADO){
            escenaPausa.draw();
        }

        // Dibuja el hud
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        batch.dispose();
        //sp.dispose();
    }


    private class EscenaPausa extends Stage
    {
        // La escena que se muestra cuando está pausado
        public EscenaPausa(final Viewport gamePort, SpriteBatch batch) {
            super(gamePort,batch);
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

                    } else {

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
                MainGame mainGame;
                public void setPantallaInicio(MainGame mainGame) {
                    this.mainGame = mainGame;
                    mainGame.setScreen(new MenuScreen(mainGame));
                }
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
                    Gdx.input.setInputProcessor(hud.stage); // No debería crear uno nuevo
                }
            });
            this.addActor(btnContinuar);
        }
    }
}
