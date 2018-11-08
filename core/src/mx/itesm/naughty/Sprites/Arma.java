package mx.itesm.naughty.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import mx.itesm.naughty.Screens.MainScreen;


public class Arma extends InteractiveTileObject {

    public Arma(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MainScreen.ARMA_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Arma", "Collision");
        setCategoryFilter(MainScreen.DESTROY_BIT);
        getCell().setTile(null);
    }
}
