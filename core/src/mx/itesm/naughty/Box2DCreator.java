package mx.itesm.naughty;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


import mx.itesm.naughty.Screens.PlayScreen;
import mx.itesm.naughty.Sprites.TileObjects.Arma;
import mx.itesm.naughty.Sprites.TileObjects.Cofre;
import mx.itesm.naughty.Sprites.Enemies.DeathGul;
import mx.itesm.naughty.Sprites.TileObjects.Door;

public class Box2DCreator {
    private Array<DeathGul> deathGul;
    private Body body;
    private World world;
    private Fixture fixture;
    public Box2DCreator(PlayScreen screen){
        this.world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape  = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        // Create ground bodies/fixtures
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MainGame.PPM , (rect.getY() + rect.getHeight() / 2) / MainGame.PPM);
            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MainGame.PPM, (rect.getHeight() / 2) / MainGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MainGame.OBJECT_BIT;
            fixture = body.createFixture(fdef);
            //body.destroyFixture(fixture);
            //world.destroyBody(body);
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

        for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            new Door(screen, object);
        }

        // Create deathguls
        deathGul = new Array<DeathGul>();
        for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            deathGul.add(new DeathGul(screen, rect.getX() / MainGame.PPM, rect.getY() / MainGame.PPM));
        }
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public Array<DeathGul> getDeathGul() {
        return deathGul;
    }
}
