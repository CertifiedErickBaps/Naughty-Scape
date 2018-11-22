package mx.itesm.naughty.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Screens.PlayScreen;


public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screen;
    protected MapObject object;

    protected Fixture fixture;
    protected int BLANK_COIN;

    public InteractiveTileObject(PlayScreen screen, MapObject object){
        this.screen = screen;
        this.object = object;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / MainGame.PPM , (bounds.getY() + bounds.getHeight() / 2) / MainGame.PPM);
        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / MainGame.PPM, (bounds.getHeight() / 2) / MainGame.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

        if(screen.getLevel() == 2){
            BLANK_COIN = 93;
        } else if(screen.getLevel() == 1){
            BLANK_COIN = 109;
        }
    }

    public abstract void onHeadHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(3);
        return layer.getCell((int)(body.getPosition().x * MainGame.PPM / 32), (int)(body.getPosition().y * MainGame.PPM / 32));
    }
}
