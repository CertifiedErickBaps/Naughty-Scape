package mx.itesm.naughty.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Scenes.Hud;
import mx.itesm.naughty.Screens.PlayScreen;

public class GulUp extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Animation deadAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    public boolean destroyed;

    public GulUp(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 9; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("DeathG2WalkSide"), i * 96, 0, 96, 96));
        walkAnimation = new Animation(0.2f, frames);

        for(int i = 0; i < 7; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("DeathG2DamageSide"), i * 96, 0, 96, 96));
        deadAnimation = new Animation(0.2f, frames);

        stateTime = 0;
        setBounds(getX(), getY(), 64 / MainGame.PPM, 64 / MainGame.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion((TextureRegion) deadAnimation.getKeyFrame(stateTime));
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
        shape.setRadius(15 / MainGame.PPM);
        fdef.filter.categoryBits = MainGame.ENEMY_BIT;
        fdef.filter.maskBits = MainGame.GROUND_BIT
                | MainGame.ARMA_BIT
                | MainGame.COFRE_BIT
                | MainGame.OBJECT_BIT
                | MainGame.ENEMY_BIT
                | MainGame.PLAYER_BIT
                | MainGame.PLAYER_HEAD_BIT
                | MainGame.DOOR_BIT
                | MainGame.BALA_BIT;
        fdef.shape = shape;
        fdef.restitution = 0.9f;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1){
            super.draw(batch);
        }
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
        Hud.addScore(100);
    }

}
