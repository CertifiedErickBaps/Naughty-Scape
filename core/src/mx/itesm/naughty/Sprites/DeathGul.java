package mx.itesm.naughty.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import mx.itesm.naughty.Screens.MainScreen;
import mx.itesm.naughty.Screens.PlayScreen;

public class DeathGul extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    public boolean destroyed;

    public DeathGul(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("DeathGulAnimDer"), i * 64, 0, 64, 32));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 64 / MainScreen.PPM, 32 / MainScreen.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("DeathGulAnimDer"), 6 * 64, 0, 64, 32));
            stateTime = 0;
        }
        else if(!destroyed){
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x -getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        }

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / MainScreen.PPM);
        fdef.filter.categoryBits = MainScreen.ENEMY_BIT;
        fdef.filter.maskBits = MainScreen.GROUND_BIT
                | MainScreen.ARMA_BIT
                | MainScreen.COFRE_BIT
                | MainScreen.OBJECT_BIT
                | MainScreen.ENEMY_BIT
                | MainScreen.PLAYER_BIT;
        fdef.shape = shape;
        fdef.restitution = 0.9f;
        b2body.createFixture(fdef).setUserData(this);

        /*


        // Create head
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-15,18).scl(1 / MainScreen.PPM);
        vertice[1] = new Vector2(15,18).scl(1 / MainScreen.PPM);
        vertice[2] = new Vector2(-13,13).scl(1 / MainScreen.PPM);
        vertice[3] = new Vector2(13,13).scl(1 / MainScreen.PPM);
        head.set(vertice);

        fdef.shape = head;

        fdef.filter.categoryBits = MainScreen.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
         */

    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1){
            super.draw(batch);
        }
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }

}
