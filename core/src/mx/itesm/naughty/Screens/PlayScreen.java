package mx.itesm.naughty.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
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
import mx.itesm.naughty.Sprites.DeathGul;
import mx.itesm.naughty.Sprites.Enemy;
import mx.itesm.naughty.Sprites.Player;

public class PlayScreen extends MainScreen {
    public enum EstadoJuego {JUGANDO, PAUSADO}
    private EstadoJuego estado;
    private MainGame game;
    private TextureAtlas atlas;
    private Hud hud;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2D creator
    private World world;
    private Box2DCreator box2DCreator;
    private Box2DDebugRenderer b2dr;

    // Sprite
    private Player player;
    //Sistema de particulas
    //private ParticleEffect sp;

    //Sound effects
    private Music music;

    public PlayScreen(MainGame game){
        this.game = game;
        estado = EstadoJuego.JUGANDO;
        atlas = new TextureAtlas("naughtyScape.pack");

        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(ANCHO_JUEGO / PPM, ALTO_JUEGO / PPM, gameCam);
        hud = new Hud(batch);
        /*
        hud.stage = new Stage(gamePort);    // Escalar con esta vista
        hud = new Hud(batch);
         */
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        LoadMap();
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        box2DCreator = new Box2DCreator(this);
        player = new Player(this);


        world.setContactListener(new WorldContactListener());

        music = MainScreen.manager.get("Musica/nivel1.mp3", Music.class);
        music.setLooping(true);
        music.play();


    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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
        for(Enemy enemy: box2DCreator.getDeathGul()){
            enemy.update(dt);
        }

        hud.update(dt);
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;
        gameCam.update();
        renderer.setView(gameCam);
    }

    private void handleInput(float dt) {
        hud.getBtnUp().addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if((hud.getBtnUp().isPressed()) && (player.b2body.getLinearVelocity().y <= 2.3f)){
                    player.b2body.applyLinearImpulse(new Vector2(0, 0.3f), player.b2body.getWorldCenter(), true);
                    player.redefineColision(new Vector2(-20/ MainScreen.PPM, 30 / MainScreen.PPM), new Vector2(20/ MainScreen.PPM, 30 / MainScreen.PPM));
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.b2body.setLinearVelocity(0,0);

            }
        });

        hud.getBtnDown().addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if((hud.getBtnDown().isPressed()) && (player.b2body.getLinearVelocity().y >= -2.3f)){
                    player.b2body.applyLinearImpulse(new Vector2(0, -0.3f), player.b2body.getWorldCenter(), true);
                    player.redefineColision(new Vector2(-20/ MainScreen.PPM, -30 / MainScreen.PPM), new Vector2(20/ MainScreen.PPM, -30 / MainScreen.PPM));
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.b2body.setLinearVelocity(0,0);
            }

        });
        hud.getBtnLeft().addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if((hud.getBtnLeft().isPressed()) && (player.b2body.getLinearVelocity().x >= -2.3f)){
                    player.b2body.applyLinearImpulse(new Vector2(-0.3f, 0f), player.b2body.getWorldCenter(), true);
                    player.redefineColision(new Vector2(-30/ MainScreen.PPM, 30 / MainScreen.PPM), new Vector2(-30/ MainScreen.PPM, -30 / MainScreen.PPM));
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.b2body.setLinearVelocity(0,0);
            }

        });
        hud.getBtnRight().addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if((hud.getBtnRight().isPressed()) && (player.b2body.getLinearVelocity().x <= 2.3f)){
                    player.b2body.applyLinearImpulse(new Vector2(0.3f, 0f), player.b2body.getWorldCenter(), true);
                    player.redefineColision(new Vector2(30/ MainScreen.PPM, 30 / MainScreen.PPM), new Vector2(30/ MainScreen.PPM, -30 / MainScreen.PPM));
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.b2body.setLinearVelocity(0,0);
            }
        });
        hud.getBtnA().addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.setPushing(true);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                player.setPushing(false);
            }
        });




        /*
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
        */

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
        for(Enemy enemy: box2DCreator.getDeathGul()){
            enemy.draw(batch);
        }
        batch.end();


        if(estado == EstadoJuego.PAUSADO){
            //
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
        hud.stage.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        batch.dispose();
        game.dispose();

        //sp.dispose();
    }
}
