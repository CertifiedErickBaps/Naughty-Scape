package mx.itesm.naughty.Sprites.Items;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Screens.PlayScreen;
import mx.itesm.naughty.Sprites.Player;

public class Pistola extends Item {
    public Pistola(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("Pistola"), 0, 0, 32, 32);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / MainGame.PPM);
        fdef.filter.categoryBits = MainGame.ITEM_BIT;
        fdef.filter.maskBits = MainGame.PLAYER_BIT | MainGame.OBJECT_BIT | MainGame.GROUND_BIT | MainGame.ARMA_BIT | MainGame.COFRE_BIT;

        fdef.shape = shape;
        fdef.restitution = 0.9f;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Player player) {
        destroy();
        player.changePistola();
    }


    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        //body.setLinearVelocity(velocity);
    }
}
