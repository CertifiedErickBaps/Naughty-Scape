package mx.itesm.naughty.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import mx.itesm.naughty.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    World world;
    PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1, 0);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    public abstract void hitOnHead();

    public void revereVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        else if(y)
            velocity.y = -velocity.y;
    }
    protected boolean toFlip(){
        if(this.screen.getPlayer().b2body.getPosition().x>this.b2body.getPosition().x){
            return true;
        }
        return false;
    }
}
