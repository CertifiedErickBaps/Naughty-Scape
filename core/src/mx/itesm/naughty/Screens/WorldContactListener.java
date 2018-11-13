package mx.itesm.naughty.Screens;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import mx.itesm.naughty.Sprites.Enemy;
import mx.itesm.naughty.Sprites.InteractiveTileObject;

class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if(fixA.getUserData() == "up" || fixB.getUserData() == "up"){
            Fixture up = fixA.getUserData() == "up" ? fixA : fixB;
            Fixture object = up == fixA ? fixB : fixA;

            if(object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }
        switch (cDef){
            case MainScreen.ENEMY_BIT | MainScreen.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == MainScreen.ENEMY_BIT){
                    ((Enemy)fixA.getUserData()).hitOnHead();
                }
                else {
                    ((Enemy)fixB.getUserData()).hitOnHead();
                }
                break;
            case MainScreen.ENEMY_BIT | MainScreen.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MainScreen.ENEMY_BIT){
                    ((Enemy)fixA.getUserData()).revereVelocity(true, false);
                }
                else {
                    ((Enemy)fixB.getUserData()).revereVelocity(true, false);
                }
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
