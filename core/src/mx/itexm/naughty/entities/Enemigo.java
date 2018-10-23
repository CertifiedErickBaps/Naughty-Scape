package mx.itexm.naughty.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

public class Enemigo extends Objeto{
    private Texture textureDispose;
    private Texture texturePersonaje;
    private Animation animEnemigo;
    private Animation animEnemigoRight;
    private Animation animEnemigoLeft;
    private float timerAnimacion;
    private float x, y;
    float timerExplota = 0;
    private EstadoEnemigo estado;

    public Enemigo(float x, float y) {
        // Crea una region
        // Divide la región en frames de 90x90
        //Cambia los texture a textureRegion
        //
        texturePersonaje = new Texture("Personajes/enemigoArriba.png");
        textureDispose = new Texture("Personajes/enemigoExplota.png");


        /*
        // Animacion dispose
        textureDispose = new TextureRegion(new Texture("Personajes/DeathGulAnimUpDownDispose.png"));
        TextureRegion[][] texturaEnemigoDispose = textureDispose.split(32,64);
        animEnemigoRight = new Animation(0.15f,texturaEnemigoDispose[0][0],texturaEnemigoDispose[0][1],texturaEnemigoDispose[0][2], texturaEnemigoDispose[0][3]);
        animEnemigoRight.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;

        // Animacion caminarUpDown
        texturePersonaje = new TextureRegion(new Texture("Personajes/DeathGulUpDown.png"));
        TextureRegion[][] texturaPersonaje = texturePersonaje.split(32,64);
        animEnemigo = new Animation(0.15f,texturaPersonaje[0][3],texturaPersonaje[0][2],texturaPersonaje[0][1]);
        animEnemigo.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;


        // Animacion caminarLeftIzq
        TextureRegion textureLeft = new TextureRegion(new Texture("Personajes/DeathGulAnimIzq.png"));
        TextureRegion[][] texturaPersonajeLeft = textureLeft.split(32,64);
        animEnemigoLeft = new Animation(0.15f,texturaPersonajeLeft[0][1],texturaPersonajeLeft[0][2],texturaPersonajeLeft[0][3], texturaPersonajeLeft[0][4],texturaPersonajeLeft[0][5]);
        animEnemigoLeft.setPlayMode(Animation.PlayMode.LOOP);

        */
        estado = EstadoEnemigo.ARRIBA;
        this.x = x;
        this.y = x;
    }

    public void render(SpriteBatch batch){
        switch (estado){
            case ARRIBA:
                batch.draw(texturePersonaje, x, y);
                break;
            case ABAJO:
                batch.draw(texturePersonaje, x, y);
                break;
            case DISPOSE:
                timerExplota += Gdx.graphics.getDeltaTime();
                if (timerExplota>=0.3f) {
                    estado = EstadoEnemigo.MUERTO;
                }
                batch.draw(textureDispose, x, y);
                break;
        }
    }

    public void mover(float dx, float dy){
        x += dx;
        y += dy;
        if (estado==EstadoEnemigo.ARRIBA) {
            estado = EstadoEnemigo.ABAJO;
        } else if (estado==EstadoEnemigo.ABAJO){
            estado = EstadoEnemigo.ARRIBA;
        }
    }

    public void disposeEnemigo(){
        estado = EstadoEnemigo.DISPOSE;
        timerExplota = 0;
    }

    public Texture getTextura(){
        return texturePersonaje;
    }

    public EstadoEnemigo getEstado(){
        return estado;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public enum EstadoEnemigo {
        QUIETO,
        ABAJO,
        ARRIBA,
        DERECHA,
        IZQUIERDA,
        DISPOSE,
        MUERTO
    }
}
