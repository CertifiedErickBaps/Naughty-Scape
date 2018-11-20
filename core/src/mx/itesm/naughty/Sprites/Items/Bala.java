package mx.itesm.naughty.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Screens.PlayScreen;
import mx.itesm.naughty.Sprites.Player;

public class Bala extends Sprite {
    PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation fireAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    boolean fireUp;

    private Player player;

    Body b2body;
    public Bala(PlayScreen screen, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        //this.fireUp = fireUp;
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("Bala"), 12,0,32,32));
        fireAnimation = new Animation(0.2f, frames);
        setRegion((TextureRegion) fireAnimation.getKeyFrame(0));
        setBounds(x, y, 6 / MainGame.PPM, 6 / MainGame.PPM);
        defineFireBall();
    }

    public void defineFireBall(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 /MainGame.PPM : getX() - 12 /MainGame.PPM, getY());
        //bdef.position.set(getX(), fireUp ? getY() + 12 / MainGame.PPM : getY() - 12 / MainGame.PPM);

        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / MainGame.PPM);
        fdef.filter.categoryBits = MainGame.BALA_BIT;
        fdef.filter.maskBits = MainGame.GROUND_BIT |
                MainGame.COFRE_BIT |
                MainGame.OBJECT_BIT |
                MainGame.GROUND_BIT |
                MainGame.ENEMY_BIT |
                MainGame.DOOR_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);

        b2body.setLinearVelocity(new Vector2(fireRight ? 2 : -2, 0));
        //b2body.setLinearVelocity(new Vector2(0, fireUp ? 2: -2));


    }

    public void update(float dt){
        stateTime += dt;
        setRegion((TextureRegion) fireAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        if(b2body.getLinearVelocity().y > 2f)
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
        if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();
        //else if((fireUp && b2body.getLinearVelocity().y < 0) || (!fireUp && b2body.getLinearVelocity().y > 0))
        //   setToDestroy();
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

}
