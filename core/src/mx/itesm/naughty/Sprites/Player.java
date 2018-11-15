package mx.itesm.naughty.Sprites;

import com.badlogic.gdx.audio.Music;
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
    public enum State { UP, STANDING, RUNNINGLR, PUSHING, KATANA};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion jhonyStand;
    private Animation jhonyRunRight;
    private Animation jhonyRunUpDown;
    private Animation jhonyPushUpDown;
    private TextureRegion jhonyStandKatana;
    private Animation jhonyRunRightKatana;
    private Animation jhonyRunUpDownKatana;
    private Animation jhonyPushKatana;
    private Animation jhonyChanging;
    private float stateTimer;
    private boolean runningRight;
    private boolean runningUp;
    private boolean pushing;

    private boolean jhonyIsKatana;
    private boolean runJhonyKatanaAnimation;



    public Player(PlayScreen screen){
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        runningUp = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //Animation walk right and down
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkRight"), i * 89, 0, 90, 90));
        }
        jhonyRunRight = new Animation(0.1f, frames);
        frames.clear();

        // Animation katana right and down
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_caminado_katana_lado"), i * 90, 0, 90, 90));
        }
        jhonyRunRightKatana = new Animation(0.1f, frames);
        frames.clear();


        //Animation walk up and down
        for(int i = 1; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), i * 90, 4, 90, 90));
        }
        jhonyRunUpDown = new Animation(0.1f, frames);
        frames.clear();

        //Animation walk up and down katana
        for(int i = 1; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), i * 89, 0, 90, 90));
        }
        jhonyRunUpDownKatana = new Animation(0.1f, frames);
        frames.clear();

        //Animation pushing
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_golpesUpDown"), i * 90, 4, 90, 90));
        }
        jhonyPushUpDown = new Animation(0.1f, frames);
        //jhonyPushUpDown.setPlayMode(Animation.PlayMode.LOOP);
        frames.clear();

        //Animation pushing katana
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_katanaAttackUpDown"), i * 90, 0, 90, 90));
        }
        jhonyPushKatana = new Animation(0.1f, frames);
        frames.clear();

        //Animation changing
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0, 0, 90, 90));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
        jhonyChanging = new Animation(0.2f, frames);
        frames.clear();


        jhonyStand = new TextureRegion(screen.getAtlas().findRegion("Jhony_standingUpDown"), 0,5,90,90);
        jhonyStandKatana = new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0,0,90,90);

        definePlayer();
        setBounds(0,0,90 / MainScreen.PPM,90 / MainScreen.PPM);
        setRegion(jhonyStand);

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
                region = jhonyIsKatana ? (TextureRegion) jhonyRunRightKatana.getKeyFrame(stateTimer, true): (TextureRegion) jhonyRunRight.getKeyFrame(stateTimer, true);
                break;
            case UP:
                region = jhonyIsKatana ? (TextureRegion) jhonyRunUpDownKatana.getKeyFrame(stateTimer, true): (TextureRegion) jhonyRunUpDown.getKeyFrame(stateTimer, true);
                break;
            case PUSHING:
                region = jhonyIsKatana ? (TextureRegion) jhonyPushKatana.getKeyFrame(stateTimer, true): (TextureRegion) jhonyPushUpDown.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = jhonyIsKatana ? jhonyStandKatana: jhonyStand;
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

        else if(b2body.getLinearVelocity().y != 0) return State.UP;
        else if(b2body.getLinearVelocity().x != 0) return State.RUNNINGLR;
        else if(pushing) return State.PUSHING;
        else return State.STANDING;

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
