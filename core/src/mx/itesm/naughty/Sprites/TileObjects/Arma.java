package mx.itesm.naughty.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Scenes.Hud;
import mx.itesm.naughty.Screens.PlayScreen;


public class Arma extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;

    public Arma(PlayScreen screen, MapObject object) {
        super(screen, object);

        fixture.setUserData(this);
        setCategoryFilter(MainGame.ARMA_BIT);
    }

    @Override
    public void onHeadHit() {
        //Gdx.app.log("Arma", "Collision");
        setCategoryFilter(MainGame.DESTROY_BIT);
        // Elimina la vista del objeto
        getCell().setTile(null);
        Hud.addScore(100);
    }
}
