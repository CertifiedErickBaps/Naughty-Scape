package mx.itesm.naughty.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;

import mx.itesm.naughty.Screens.MainScreen;
import mx.itesm.naughty.Screens.PlayScreen;

public class DeathGul extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;

    public DeathGul(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("DeathGulAnimDer"), i * 64, 0, 64, 32));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 90 / MainScreen.PPM, 90 / MainScreen.PPM);
    }

    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x -getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 / MainScreen.PPM,170 / MainScreen.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / MainScreen.PPM);
        fdef.filter.categoryBits = MainScreen.ENEMY_BIT;
        fdef.filter.maskBits = MainScreen.GROUND_BIT
                | MainScreen.ARMA_BIT
                | MainScreen.COFRE_BIT
                | MainScreen.OBJECT_BIT
                | MainScreen.ENEMY_BIT
                | MainScreen.PLAYER_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
