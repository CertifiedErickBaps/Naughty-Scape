package mx.itesm.naughty.Sprites.TileObjects;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Scenes.Hud;
import mx.itesm.naughty.Screens.PlayScreen;
import mx.itesm.naughty.Sprites.Items.Bate;
import mx.itesm.naughty.Sprites.Items.ItemDef;
import mx.itesm.naughty.Sprites.Items.Katana;


public class Cofre extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 109;
    public Cofre(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("70377b86-59d2-4993-9a44-c61a4f16fd0c");
        fixture.setUserData(this);
        setCategoryFilter(MainGame.COFRE_BIT);
    }

    @Override
    public void onHeadHit() {
        //Gdx.app.log("Cofre", "Collision");
        if(getCell().getTile().getId() == BLANK_COIN){
            MainGame.manager.get("Musica/error.mp3", Music.class).play();
        } else {
            if(object.getProperties().containsKey("katana")){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x + 32 / MainGame.PPM, body.getPosition().y), Katana.class));
            }
            else if(object.getProperties().containsKey("bate")){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x + 32 / MainGame.PPM, body.getPosition().y), Bate.class));
            }
            MainGame.manager.get("Musica/chest.mp3", Music.class).play();

        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);

    }
}
