package mx.itesm.naughty.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import mx.itesm.naughty.Screens.MainScreen;
import mx.itesm.naughty.Screens.PlayScreen;

public class Player extends Sprite {
    public enum State { UP, STANDINGUD, STANDINGLR, RUNNINGLR, PUSHINGUD, PUSHINGLR, KATANA};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion jhonyStandUD;
    private TextureRegion jhonyStandRL;
    private TextureRegion jhonyStandKatanaUD;
    private TextureRegion jhonyStandKatanaLR;

    private Animation jhonyRunRL;
    private Animation jhonyRunUD;
    private Animation jhonyPushUD;
    private Animation jhonyPushRL;
    private Animation jhonyRunRLKatana;
    private Animation jhonyRunUDKatana;
    private Animation jhonyPushKatanaUD;
    private Animation jhonyPushKatanaRL;
    private Animation jhonyChanging;


    private float stateTimer;
    private boolean runningRight;
    private boolean isRunningRL;
    private boolean isRunningUD;
    private boolean runningUp;
    private boolean pushing;

    private boolean jhonyIsKatana;
    private boolean runJhonyKatanaAnimation;



    public Player(PlayScreen screen){
        this.world = screen.getWorld();
        currentState = State.STANDINGLR;
        previousState = State.STANDINGLR;
        stateTimer = 0;
        runningRight = true;
        runningUp = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

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

        //Animation pushingUD katana
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_ataque_katana_lado"), i * 90, 0, 90, 90));
        }
        jhonyPushKatanaRL = new Animation(0.1f, frames);
        frames.clear();



        //Animation changing
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
        jhonyChanging = new Animation(0.2f, frames);
        frames.clear();


        jhonyStandRL = new TextureRegion(screen.getAtlas().findRegion("jhony_standing_lado"), 0,5,90,90);
        jhonyStandUD = new TextureRegion(screen.getAtlas().findRegion("Jhony_standingUpDown"), 0,5,90,90);
        jhonyStandKatanaUD = new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0,0,90,90);
        jhonyStandKatanaLR = new TextureRegion(screen.getAtlas().findRegion("jhony_caminado_katana_lado"), 0,0,90,90);

        definePlayer();
        setBounds(0,0,90 / MainScreen.PPM,90 / MainScreen.PPM);
        setRegion(jhonyStandRL);

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case KATANA:
                region = (TextureRegion) jhonyChanging.getKeyFrame(stateTimer);
                if(jhonyChanging.isAnimationFinished(stateTimer))
                    runJhonyKatanaAnimation = false;
                break;
            case RUNNINGLR:
                region = jhonyIsKatana ? (TextureRegion) jhonyRunRLKatana.getKeyFrame(stateTimer, true): (TextureRegion) jhonyRunRL.getKeyFrame(stateTimer, true);
                break;
            case UP:
                region = jhonyIsKatana ? (TextureRegion) jhonyRunUDKatana.getKeyFrame(stateTimer, true): (TextureRegion) jhonyRunUD.getKeyFrame(stateTimer, true);
                break;
            case PUSHINGUD:
                region = jhonyIsKatana ? (TextureRegion) jhonyPushKatanaUD.getKeyFrame(stateTimer, true): (TextureRegion) jhonyPushUD.getKeyFrame(stateTimer, true);
                break;
            case PUSHINGLR:
                region = jhonyIsKatana ? (TextureRegion) jhonyPushKatanaRL.getKeyFrame(stateTimer, true): (TextureRegion) jhonyPushRL.getKeyFrame(stateTimer, true);
                break;
            case STANDINGLR:
                region = jhonyIsKatana ? jhonyStandKatanaLR : jhonyStandRL;
                break;
            default:
                region = jhonyIsKatana ? jhonyStandKatanaUD : jhonyStandUD;
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

        stateTimer = currentState == previousState ? stateTimer+dt: 0;

        previousState = currentState;
        return region;

    }

    public void change(){
        runJhonyKatanaAnimation = true;
        jhonyIsKatana = true;
        setBounds(getX(), getY(), getWidth(), getHeight());
        //MainScreen.manager.get("Musica/chest.mp3", Music.class).play();
    }

    private State getState() {
        if(runJhonyKatanaAnimation) return State.KATANA;

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
        bdef.position.set(170 / MainScreen.PPM,170 / MainScreen.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / MainScreen.PPM);
        fdef.filter.categoryBits = MainScreen.PLAYER_BIT;
        fdef.filter.maskBits = MainScreen.GROUND_BIT
                | MainScreen.ARMA_BIT
                | MainScreen.COFRE_BIT
                | MainScreen.ENEMY_BIT
                | MainScreen.OBJECT_BIT
                | MainScreen.ITEM_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void redefineColision(Vector2 vector1, Vector2 vector2){
        FixtureDef colisionador = new FixtureDef();
        EdgeShape up = new EdgeShape();
        up.set(vector1, vector2);
        colisionador.shape = up;
        colisionador.isSensor = true;
        b2body.createFixture(colisionador).setUserData("up");

    }

}
