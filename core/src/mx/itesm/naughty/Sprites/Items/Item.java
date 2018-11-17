package mx.itesm.naughty.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Screens.MainScreen;
import mx.itesm.naughty.Screens.PlayScreen;
import mx.itesm.naughty.Sprites.Player;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        setBounds(getX(), getY(), 32 / MainGame.PPM, 32 / MainGame.PPM);
        defineItem();
        toDestroy = false;
        destroyed = false;
    }

    public abstract void defineItem();

    public abstract void use(Player player);

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }
    public void draw(SpriteBatch batch){
        if(!destroyed){
            super.draw(batch);
        }
    }

    public void destroy(){
        toDestroy = true;
    }
}
