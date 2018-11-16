package mx.itesm.naughty.Sprites.Items;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import mx.itesm.naughty.Screens.MainScreen;
import mx.itesm.naughty.Screens.PlayScreen;
import mx.itesm.naughty.Sprites.Player;

public class Katana extends Item {
    public Katana(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("Katana"), 0, 0, 32, 32);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / MainScreen.PPM);
        fdef.filter.categoryBits = MainScreen.ITEM_BIT;
        fdef.filter.maskBits = MainScreen.PLAYER_BIT | MainScreen.OBJECT_BIT | MainScreen.GROUND_BIT | MainScreen.ARMA_BIT | MainScreen.COFRE_BIT;

        fdef.shape = shape;
        fdef.restitution = 0.9f;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Player player) {
        destroy();
        player.change();
    }


    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        //body.setLinearVelocity(velocity);
    }
}
