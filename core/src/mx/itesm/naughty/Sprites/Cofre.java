package mx.itesm.naughty.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import mx.itesm.naughty.Screens.Hud;
import mx.itesm.naughty.Screens.MainScreen;
import mx.itesm.naughty.Screens.PlayScreen;


public class Cofre extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 109;
    public Cofre(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        tileSet = map.getTileSets().getTileSet("70377b86-59d2-4993-9a44-c61a4f16fd0c");
        fixture.setUserData(this);
        setCategoryFilter(MainScreen.COFRE_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Cofre", "Collision");
        Hud.addScore(100);
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        if(getCell().getTile().getId() == BLANK_COIN){
            MainScreen.manager.get("Musica/error.mp3", Music.class).play();
        } else {
            MainScreen.manager.get("Musica/chest.mp3", Music.class).play();
        }

    }
}
