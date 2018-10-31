package mx.itesm.naughty.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.naughty.Box2DCreator;
import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Sprites.Player;

public class PlayScreen implements Screen {
    private MainGame game;
    private TextureAtlas atlas;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;

    //Sistema de particulas
    //private ParticleEffect sp;

    public PlayScreen(MainGame game){
        atlas = new TextureAtlas("naughty.pack");

        this.game = game;
        gameCam = new OrthographicCamera(MainGame.ANCHO_JUEGO / MainGame.PPM, MainGame.ALTO_JUEGO / MainGame.PPM);
        gamePort = new StretchViewport(MainGame.ANCHO_JUEGO / MainGame.PPM, MainGame.ALTO_JUEGO / MainGame.PPM, gameCam);
        hud = new Hud(game.batch);

        LoadMap();

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        new Box2DCreator(world, map);
        player = new Player(world, this);


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
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
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

    @Override
    public void render(float delta) {
        //sp.update(delta);
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        renderer.render();
        game.batch.begin();
        player.draw(game.batch);
        //sp.draw(game.batch);
        game.batch.end();



        b2dr.render(world,gameCam.combined);

        // Dibuja el hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
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
        //sp.dispose();
    }
}
