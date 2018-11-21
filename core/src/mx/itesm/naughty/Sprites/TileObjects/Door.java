package mx.itesm.naughty.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;


import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Screens.PlayScreen;

public class Door extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int DOOR1 = 3;
    private final int DOOR2 = 4;

    public Door(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("8e4932b4-b26f-4e67-a8db-65e1030f4f36");
        fixture.setUserData(this);
        setCategoryFilter(MainGame.DOOR_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Puerta", "Collision");

        /*
        if((getCell().getTile().getId() == DOOR1) || (getCell().getTile().getId() == DOOR2)){
            MainGame.manager.get("Musica/error.mp3", Music.class).play();
        } else {
            MainGame.manager.get("Musica/chest.mp3", Music.class).play();
        }
        getCell().setTile(tileSet.getTile(DOOR1 | DOOR2));
         */

    }
}
