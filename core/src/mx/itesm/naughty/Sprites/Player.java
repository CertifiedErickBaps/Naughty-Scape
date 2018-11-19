package mx.itesm.naughty.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Screens.PlayScreen;
import mx.itesm.naughty.Sprites.Items.Bala;

public class Player extends Sprite {
    public enum State { UP, STANDINGUD, STANDINGLR, RUNNINGLR, PUSHINGUD, PUSHINGLR, KATANA, BATE, DEAD, WIN}

    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion jhonyStandUD;
    private TextureRegion jhonyStandRL;
    private TextureRegion jhonyStandKatanaUD;
    private TextureRegion jhonyStandKatanaLR;
    private TextureRegion jhonyStandBateUD;
    private TextureRegion jhonyStandBateLR;

    private Animation jhonyRunRL;
    private Animation jhonyRunUD;
    private Animation jhonyRunRLKatana;
    private Animation jhonyRunUDKatana;
    private Animation jhonyRunRLBate;
    private Animation jhonyRunUDBate;

    private Animation jhonyPushUD;
    private Animation jhonyPushRL;
    private Animation jhonyPushKatanaUD;
    private Animation jhonyPushKatanaRL;
    private Animation jhonyPushBateUD;
    private Animation jhonyPushBateRL;

    private Animation jhonyChangingKatana;
    private Animation jhonyChangingBate;

    private Animation jhonyDead;


    private float stateTimer;
    private boolean runningRight;
    private boolean runningUp;
    private boolean isRunningRL;
    private boolean isRunningUD;

    private boolean pushing;

    private boolean jhonyIsWithKatana;
    private boolean jhonyIsWithBate;
    private boolean runJhonyKatanaAnimation;
    private boolean runJhonyBateAnimation;

    private boolean playerIsDead;
    private boolean playerIsWin;

    private Array<Bala> balas;
    private PlayScreen screen;

    public Player(PlayScreen screen){
        this.world = screen.getWorld();
        this.screen = screen;
        currentState = State.STANDINGLR;
        previousState = State.STANDINGLR;
        stateTimer = 0;
        runningRight = true;
        runningUp = true;
        createAnimations();
    }

    public void createAnimations(){
        Array<TextureRegion> frames = new Array<TextureRegion>();

        //Animation dead
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_golpeado"), i * 89, 0, 90, 90));
        }
        jhonyDead = new Animation(0.2f, frames);
        frames.clear();

        //Animation walk right and down
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkRight"), i * 89, 0, 90, 90));
        }
        jhonyRunRL = new Animation(0.1f, frames);
        frames.clear();

        // Animation katana right and down
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_caminado_katana_lado"), i * 90, 0, 90, 90));
        }
        jhonyRunRLKatana = new Animation(0.1f, frames);
        frames.clear();

        // Animation bate right and down
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_caminado_bate_lado"), i * 90, 0, 90, 90));
        }
        jhonyRunRLBate = new Animation(0.1f, frames);
        frames.clear();


        //Animation walk up and down
        for(int i = 1; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), i * 90, 4, 90, 90));
        }
        jhonyRunUD = new Animation(0.1f, frames);
        frames.clear();

        //Animation walk up and down katana
        for(int i = 1; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), i * 89, 0, 90, 90));
        }
        jhonyRunUDKatana = new Animation(0.1f, frames);
        frames.clear();

        //Animation walk up and down bate
        for(int i = 1; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_bate"), i * 89, 0, 90, 90));
        }
        jhonyRunUDBate = new Animation(0.1f, frames);
        frames.clear();

        //Animation pushingUD
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_golpesUpDown"), i * 90, 4, 90, 90));
        }
        jhonyPushUD = new Animation(0.1f, frames);
        frames.clear();

        //Animation pushingLR
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_golpes_lado"), i * 90, 4, 90, 90));
        }
        jhonyPushRL = new Animation(0.1f, frames);
        frames.clear();


        //Animation pushingUD katana
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_katanaAttackUpDown"), i * 90, 0, 90, 90));
        }
        jhonyPushKatanaUD = new Animation(0.1f, frames);
        frames.clear();

        //Animation pushingUD bate
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_bateAttackUpDown"), i * 90, 0, 90, 90));
        }
        jhonyPushBateUD = new Animation(0.1f, frames);
        frames.clear();


        //Animation pushingLR katana
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_ataque_katana_lado"), i * 90, 0, 90, 90));
        }
        jhonyPushKatanaRL = new Animation(0.1f, frames);
        frames.clear();

        //Animation pushingLR bate
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_ataque_bate_lado"), i * 90, 0, 90, 90));
        }
        jhonyPushBateRL = new Animation(0.1f, frames);
        frames.clear();

        //Animation changing katana
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
        jhonyChangingKatana = new Animation(0.2f, frames);
        frames.clear();

        //Animation changing bate
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_bate"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_bate"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
        jhonyChangingBate = new Animation(0.2f, frames);
        frames.clear();

        jhonyStandRL = new TextureRegion(screen.getAtlas().findRegion("jhony_standing_lado"), 0,5,90,90);
        jhonyStandUD = new TextureRegion(screen.getAtlas().findRegion("Jhony_standingUpDown"), 0,5,90,90);

        jhonyStandKatanaUD = new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0,0,90,90);
        jhonyStandKatanaLR = new TextureRegion(screen.getAtlas().findRegion("jhony_caminado_katana_lado"), 0,0,90,90);

        jhonyStandBateLR = new TextureRegion(screen.getAtlas().findRegion("jhony_caminado_bate_lado"), 0,0,90,90);
        jhonyStandBateUD = new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_bate"), 0,0,90,90);

        definePlayer();
        setBounds(0,0,90 / MainGame.PPM,90 / MainGame.PPM);
        setRegion(jhonyStandRL);

        balas = new Array<Bala>();
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));

        // Actualiza las balas y las elimina
        for(Bala bala: balas){
            bala.update(dt);
            if(bala.isDestroyed()){
                balas.removeValue(bala, true);
            }
        }
    }

    public boolean isDead(){
        return playerIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    private TextureRegion getFrame(float dt) {
        TextureRegion region;
        switch (currentState){
            case DEAD:
                region = (TextureRegion) jhonyDead.getKeyFrame(stateTimer);
                break;
            case WIN:
                region = jhonyStandUD;
                break;
            case KATANA:
                region = (TextureRegion) jhonyChangingKatana.getKeyFrame(stateTimer);
                if(jhonyChangingKatana.isAnimationFinished(stateTimer))
                    runJhonyKatanaAnimation = false;
                break;
            case BATE:
                region = (TextureRegion) jhonyChangingBate.getKeyFrame(stateTimer);
                if(jhonyChangingBate.isAnimationFinished(stateTimer))
                    runJhonyBateAnimation = false;
                break;
            case RUNNINGLR:
                if(jhonyIsWithKatana){
                    region = (TextureRegion) jhonyRunRLKatana.getKeyFrame(stateTimer, true);
                } else if(jhonyIsWithBate){
                    region = (TextureRegion) jhonyRunRLBate.getKeyFrame(stateTimer, true);
                }else {
                    region = (TextureRegion) jhonyRunRL.getKeyFrame(stateTimer, true);
                }
                break;
            case UP:
                if(jhonyIsWithKatana){
                    region = (TextureRegion) jhonyRunUDKatana.getKeyFrame(stateTimer, true);
                } else if(jhonyIsWithBate){
                    region = (TextureRegion) jhonyRunUDBate.getKeyFrame(stateTimer, true);
                }else {
                    region = (TextureRegion) jhonyRunUD.getKeyFrame(stateTimer, true);
                }
                break;
            case PUSHINGUD:
                if(jhonyIsWithKatana){
                    region = (TextureRegion) jhonyPushKatanaUD.getKeyFrame(stateTimer, true);
                } else if(jhonyIsWithBate){
                    region = (TextureRegion) jhonyPushBateUD.getKeyFrame(stateTimer, true);
                }else {
                    region = (TextureRegion) jhonyPushUD.getKeyFrame(stateTimer, true);
                }
                break;
            case PUSHINGLR:
                if(jhonyIsWithKatana){
                    region = (TextureRegion) jhonyPushKatanaRL.getKeyFrame(stateTimer, true);
                } else if(jhonyIsWithBate){
                    region = (TextureRegion) jhonyPushBateRL.getKeyFrame(stateTimer, true);
                }else {
                    region = (TextureRegion) jhonyPushRL.getKeyFrame(stateTimer, true);
                }
                break;
            case STANDINGLR:
                if(jhonyIsWithKatana){
                    region = jhonyStandKatanaLR;
                } else if(jhonyIsWithBate){
                    region = jhonyStandBateLR;
                }else {
                    region = jhonyStandRL;
                }
                break;
            default:
                if(jhonyIsWithKatana){
                    region = jhonyStandKatanaUD;
                } else if(jhonyIsWithBate){
                    region = jhonyStandBateUD;
                }else {
                    region = jhonyStandUD;
                }
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        else if((b2body.getLinearVelocity().y > 0 || runningUp) && region.isFlipY()){
            region.flip(false, true);
            runningUp = true;
        }
        else if((b2body.getLinearVelocity().y < 0 || !runningUp) && !region.isFlipY()){
            region.flip(false, true);
            runningUp = false;
        }
        currentState = getState();
        stateTimer = currentState == previousState ? stateTimer+dt: 0;
        previousState = currentState;
        return region;

    }

    public void changeKatana(){
        runJhonyKatanaAnimation = true;
        jhonyIsWithKatana = true;
        jhonyIsWithBate = false;
        setBounds(getX(), getY(), getWidth(), getHeight());
        //MainScreen.manager.get("Musica/chest.mp3", Music.class).play();
    }

    public void changeBate(){
        runJhonyBateAnimation = true;
        jhonyIsWithKatana = false;
        jhonyIsWithBate = true;
        setBounds(getX(), getY(), getWidth(), getHeight());
        //MainScreen.manager.get("Musica/chest.mp3", Music.class).play();
    }

    private State getState() {
        if(playerIsDead) return State.DEAD;
        else if(playerIsWin) return State.WIN;
        else if(runJhonyKatanaAnimation) return State.KATANA;
        else if(runJhonyBateAnimation) return State.BATE;
        else if(b2body.getLinearVelocity().y != 0) {
            isRunningUD = true;
            isRunningRL = false;
            return State.UP;
        }
        else if(b2body.getLinearVelocity().x != 0) {
            isRunningRL = true;
            isRunningUD = false;
            return State.RUNNINGLR;
        }
        else if(pushing && isRunningUD) return State.PUSHINGUD;
        else if(pushing && isRunningRL) return State.PUSHINGLR;
        else if(isRunningRL && !isRunningUD) return State.STANDINGLR;
        else return State.STANDINGUD;
    }

    public void setPushing(boolean pushing) {
        this.pushing = pushing;
    }

    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(170 / MainGame.PPM,170 / MainGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / MainGame.PPM);
        fdef.filter.categoryBits = MainGame.PLAYER_BIT;
        fdef.filter.maskBits = MainGame.GROUND_BIT
                | MainGame.ARMA_BIT
                | MainGame.COFRE_BIT
                | MainGame.ENEMY_BIT
                | MainGame.OBJECT_BIT
                | MainGame.ITEM_BIT
                | MainGame.DOOR_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void redefineColision(Vector2 vector1, Vector2 vector2){
        FixtureDef colisionador = new FixtureDef();
        EdgeShape up = new EdgeShape();
        up.set(vector1, vector2);
        colisionador.filter.categoryBits = MainGame.PLAYER_HEAD_BIT;
        colisionador.shape = up;
        colisionador.isSensor = true;
        b2body.createFixture(colisionador).setUserData("up");
    }

    public void hit(){
        playerIsDead = true;
        Filter filter = new Filter();
        filter.maskBits = MainGame.NOTHING_BIT;
        for(Fixture fixture: b2body.getFixtureList())
            fixture.setFilterData(filter);
        //b2body.setLinearVelocity(0, 0);
    }

    public void win(){
        playerIsWin = true;
        Filter filter = new Filter();
        filter.maskBits = MainGame.NOTHING_BIT;
        for(Fixture fixture: b2body.getFixtureList())
            fixture.setFilterData(filter);
    }

    public void fire(){
        balas.add(new Bala(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true: false));
    }

    public void draw(Batch batch){
        super.draw(batch);
        for(Bala bala: balas){
            bala.draw(batch);
        }
    }

}
