package mx.itesm.naughty.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.concurrent.LinkedBlockingDeque;

import mx.itesm.naughty.Box2DCreator;
import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Sprites.Enemies.Enemy;
import mx.itesm.naughty.Sprites.Items.Item;
import mx.itesm.naughty.Sprites.Items.ItemDef;
import mx.itesm.naughty.Sprites.Items.Katana;
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
    private Array<Item> items;
    private LinkedBlockingDeque<ItemDef> itemsToSpawn;
    //Sistema de particulas
    //private ParticleEffect sp;

    //Sound effects
    private Music music;

    public PlayScreen(MainGame game){
        this.game = game;
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
        world.step(1/ 60f, 6, 2);
        handleInput(dt);
        handleSpawningItems();
        player.update(dt);
        for(Enemy enemy: box2DCreator.getDeathGul()){
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 1f/ MainScreen.PPM) {
                enemy.b2body.setActive(true);
            }
        }

        for(Item item: items){
            item.update(dt);
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

        estado = EstadoJuego.JUGANDO;
        atlas = new TextureAtlas("naughtyScape.pack");

        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(ANCHO_JUEGO / PPM, ALTO_JUEGO / PPM, gameCam);
        hud = new Hud(batch);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        LoadMap();
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        box2DCreator = new Box2DCreator(this);
        player = new Player(this);


        world.setContactListener(new WorldContactListener());

        music = MainScreen.manager.get("Musica/nivel1.mp3", Music.class);
        music.setLooping(true);
        //music.play();
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingDeque<ItemDef>();

    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Katana.class){
                items.add(new Katana(this, idef.position.x, idef.position.y));
            }
        }
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

        for(Item item: items){
            item.draw(batch);
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
