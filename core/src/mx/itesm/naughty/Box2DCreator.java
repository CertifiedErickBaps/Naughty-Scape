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

import mx.itesm.naughty.Screens.MainScreen;
import mx.itesm.naughty.Sprites.Arma;
import mx.itesm.naughty.Sprites.Cofre;

public class Box2DCreator {
    public Box2DCreator(World world, TiledMap map){
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
            body.createFixture(fdef);
        }

        // Create armas bodies/fixtures
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Arma(world, map, rect);
        }
        // Create cofre bodies/fixtures
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Cofre(world, map, rect);
        }
    }

}
