package mx.itesm.naughty.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import mx.itesm.naughty.Screens.MainScreen;
import mx.itesm.naughty.Screens.PlayScreen;

public class Player extends Sprite {
    public enum State { UP, STANDING, RUNNINGLR};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion jhonyStand;
    private Animation jhonyRunRight;
    private Animation jhonyRunUpDown;
    private float stateTimer;
    private boolean runningRight;
    private boolean runningUp;

    public Player(World world, PlayScreen screen){
        this.world = world;
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

        //Animation walk up and down
        for(int i = 0; i < 5; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), i * 90, 4, 90, 90));
        }
        jhonyRunUpDown = new Animation(0.1f, frames);
        frames.clear();

        jhonyStand = new TextureRegion(screen.getAtlas().findRegion("Jhony_standingUpDown"), 0,5,90,90);

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
            case RUNNINGLR:
                region = (TextureRegion) jhonyRunRight.getKeyFrame(stateTimer, true);
                break;
            case UP:
                region = (TextureRegion) jhonyRunUpDown.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = jhonyStand;
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

    private State getState() {
        if(b2body.getLinearVelocity().y != 0) return State.UP;
        else if(b2body.getLinearVelocity().x != 0) return State.RUNNINGLR;
        else return State.STANDING;

    }

    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(170 / MainScreen.PPM,170 / MainScreen.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / MainScreen.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }

}
