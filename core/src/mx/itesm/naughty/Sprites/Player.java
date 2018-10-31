package mx.itesm.naughty.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Screens.PlayScreen;

public class Player extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion jhonyStand;

    public Player(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("Jhony_walkRight"));
        this.world = world;
        definePlayer();
        jhonyStand = new TextureRegion(getTexture(), 0,0,90,90);
        setBounds(0,0,90 / MainGame.PPM,90 / MainGame.PPM);
        setRegion(jhonyStand);

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2 , b2body.getPosition().y - getHeight() / 2);

    }
    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(170 / MainGame.PPM,170 / MainGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / MainGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }

}
