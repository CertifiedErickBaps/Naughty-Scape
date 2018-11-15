package mx.itesm.naughty;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


import mx.itesm.naughty.Screens.MainScreen;
import mx.itesm.naughty.Screens.PlayScreen;
import mx.itesm.naughty.Sprites.Arma;
import mx.itesm.naughty.Sprites.Cofre;
import mx.itesm.naughty.Sprites.Enemies.DeathGul;

public class Box2DCreator {
    private Array<DeathGul> deathGul;
    public Box2DCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape  = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Create ground bodies/fixtures
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MainScreen.PPM , (rect.getY() + rect.getHeight() / 2) / MainScreen.PPM);
            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MainScreen.PPM, (rect.getHeight() / 2) / MainScreen.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MainScreen.OBJECT_BIT;
            body.createFixture(fdef);
        }

        // Create armas bodies/fixtures
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            //Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Arma(screen, object);
        }
        // Create cofre bodies/fixtures
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            //Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Cofre(screen, object);
        }

        // Create deathguls
        deathGul = new Array<DeathGul>();
        for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            deathGul.add(new DeathGul(screen, rect.getX() / MainScreen.PPM, rect.getY() / MainScreen.PPM));
        }
    }

    public Array<DeathGul> getDeathGul() {
        return deathGul;
    }
}
