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
import mx.itesm.naughty.Pantallas.MainScreen;
import mx.itesm.naughty.Pantallas.MenuScreen;
import mx.itesm.naughty.Scenes.Hud;
import mx.itesm.naughty.Sprites.Enemies.Enemy;
import mx.itesm.naughty.Sprites.Items.Bate;
import mx.itesm.naughty.Sprites.Items.Item;
import mx.itesm.naughty.Sprites.Items.ItemDef;
import mx.itesm.naughty.Sprites.Items.Katana;
import mx.itesm.naughty.Sprites.Items.Pistola;
import mx.itesm.naughty.Sprites.Player;

import static mx.itesm.naughty.MainGame.ALTO_JUEGO;
import static mx.itesm.naughty.MainGame.ANCHO_JUEGO;
import static mx.itesm.naughty.MainGame.PPM;

public class PlayScreen extends MainScreen {
    public enum EstadoJuego {JUGANDO, PAUSADO}
    private EstadoJuego estado;

    public MainGame getGame() {
        return game;
    }

    private MainGame game;
    private TextureAtlas atlas;

    public Hud getHud() {
        return hud;
    }

    private Hud hud;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private float stateTimer;

    // Box2D creator
    private World world;
    private Box2DCreator box2DCreator;
    private Box2DDebugRenderer b2dr;

    // Sprite
    private Player player;
    private Array<Item> items;
    private LinkedBlockingDeque<ItemDef> itemsToSpawn;


    public boolean updateObjets;
    private String isJhony;
    //Sistema de particulas
    //private ParticleEffect sp;



    //Sound effects
    private Music music;
    private int level;

    private boolean pause;

    public PlayScreen(MainGame game, int level, String isJhony){
        this.game = game;
        this.level = level;
        this.isJhony = isJhony;
        this.music = getMusic();
        updateObjets = true;
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }



    public World getWorld(){
        return world;
    }

    private void LoadMap() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        if(level == 1){
            manager.load("Mapas/Nivel1.tmx",TiledMap.class);
            manager.finishLoading(); // Espera
            map = manager.get("Mapas/Nivel1.tmx");
            music = MainGame.manager.get("Musica/niveluno.mp3", Music.class);
        } else if(level == 2){
            manager.load("Mapas/Nivel2.tmx",TiledMap.class);
            manager.finishLoading(); // Espera
            map = manager.get("Mapas/Nivel2.tmx");
            music = MainGame.manager.get("Musica/niveldos.mp3", Music.class);
        }

        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
    }
    public TiledMap getMap(){
        return map;
    }
    
    public void update(float dt){

        handleSpawningItems();
        world.step(1/ 60f, 6, 2);
        player.update(dt);

        for(Enemy enemy: box2DCreator.getDeathGul()){
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 700f/ PPM && enemy.getY() < player.getY() + 700f/ PPM) {
                enemy.b2body.setActive(true);
            }
        }

        for(Enemy enemy: box2DCreator.getDeathGul2()){
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 700f/ PPM && enemy.getY() < player.getY() + 700f/ PPM) {
                enemy.b2body.setActive(true);
            }
        }

        for(Item item: items){
            item.update(dt);
        }
        if(player.currentState != Player.State.DEAD){

            if (player.b2body.getPosition().x >= (ANCHO_JUEGO / 2 / PPM) && player.b2body.getPosition().x <= ((ANCHO_JUEGO + 70)/ PPM))
                gameCam.position.x = player.b2body.getPosition().x;
            if (player.b2body.getPosition().y >= (ALTO_JUEGO / 2 / PPM)
                    && player.b2body.getPosition().y <= ((2650) / PPM))
                gameCam.position.y = player.b2body.getPosition().y;
        }
        hud.update(dt);
        gameCam.update();
        renderer.setView(gameCam);



    }

    private void handleInput() {
        if(!player.isDead()){
            hud.getBtnUp().addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if((hud.getBtnUp().isPressed()) && (player.b2body.getLinearVelocity().y <= 2.3f)){
                        player.b2body.applyLinearImpulse(new Vector2(0, 2.3f), player.b2body.getWorldCenter(), true);
                        //player.redefineColision(new Vector2(-20/ PPM, 30 / PPM), new Vector2(20/ PPM, 30 / PPM));
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
                        player.b2body.applyLinearImpulse(new Vector2(0, -2.3f), player.b2body.getWorldCenter(), true);
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
                        player.b2body.applyLinearImpulse(new Vector2(-2.3f, 0f), player.b2body.getWorldCenter(), true);
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
                        player.b2body.applyLinearImpulse(new Vector2(2.3f, 0f), player.b2body.getWorldCenter(), true);
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
                    if(player.isPlayerIsWithPistola()){
                        player.fire();
                    } else {
                        player.setPushing(true);
                    }
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    player.setPushing(false);
                }
            });
            hud.getBtnB().addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    player.changeDefault();
                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                }
            });
        }



    }

    @Override
    public void show() {
        LoadMap();
        music.setLooping(true);
        music.play();
        /*
        //Inicializa el sistema de particulas
        sp = new ParticleEffect();
        sp.load(Gdx.files.internal("lluvia.pe"), Gdx.files.internal(""));
        sp.getEmitters().get(0).setPosition(0, MainGame.ALTO_JUEGO);
        sp.start();
        */
        stateTimer = 0;
        estado = EstadoJuego.JUGANDO;
        atlas = new TextureAtlas("juegofinal.pack");

        gameCam = new OrthographicCamera();
        gamePort = new StretchViewport(ANCHO_JUEGO / PPM, ALTO_JUEGO / PPM, gameCam);
        hud = new Hud(this);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);


        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();
        //Ocultar fixtures
        //b2dr.setDrawBodies(false);

        //Color de fixture
        //b2dr.SHAPE_STATIC.set(0, 0, 0, 0);

        box2DCreator = new Box2DCreator(this);
        player = new Player(this);


        world.setContactListener(new WorldContactListener());

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingDeque<ItemDef>();
        Gdx.input.setCatchBackKey(true);
        handleInput();

    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Bate.class){
                items.add(new Bate(this, idef.position.x, idef.position.y));
            }
            else if(idef.type == Katana.class){
                items.add(new Katana(this, idef.position.x, idef.position.y));
            } else if(idef.type == Pistola.class){
                items.add(new Pistola(this, idef.position.x, idef.position.y));
            }
        }
    }

    public boolean gameOver(){
        if(player.currentState == Player.State.DEAD && player.getStateTimer() > 1f){
            music.stop();
            return true;
        }
        return false;
    }

    public boolean gameWin(){
        if(player.currentState == Player.State.WIN && player.getStateTimer() > 1f){
            music.stop();
            return true;
        }
        return false;
    }

    @Override
    public void render(float delta) {
        if(updateObjets){
            update(delta);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //update(delta);

        renderer.render();
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        game.batch.begin();

        player.draw(game.batch);
        //sp.draw(game.batch);
        for(Enemy enemy: box2DCreator.getDeathGul()){
            enemy.draw(game.batch);
        }
        for(Enemy enemy: box2DCreator.getDeathGul2()){
            enemy.draw(game.batch);
        }
        for(Item item: items){
            item.draw(game.batch);
        }
        game.batch.end();
        hud.stage.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        } else if(gameWin()){
            game.setScreen(new WinScreen(game));
            dispose();
        }

        if(player.currentState == Player.State.DEAD){
            hud.stage.getActors().removeValue(hud.getCorazon(), true);
        }




    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
    public Music getMusic() {
        return music;
    }
    public int getLevel() {
        return level;
    }
    public String getJhony() {
        return isJhony;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {
        if(getHud().isFirstPause()){
            updateObjets = false;
            getHud().createButtonsPaused();
            getHud().addActors();
            getHud().setFirstPause(false);
        }
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
