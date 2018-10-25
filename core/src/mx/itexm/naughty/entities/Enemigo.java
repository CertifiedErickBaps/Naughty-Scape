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
    private TextureRegion textureDispose;
    private TextureRegion textureDerecha;
    private TextureRegion texturePersonaje;
    private Animation animEnemigoDispose;
    private Animation animEnemigoUpDown;
    private Animation animEnemigoLeft;
    private Animation animEnemigoDerecha;
    private float timerAnimacion;
    private float x, y;
    float timerExplota = 0;
    private EstadoEnemigo estado;

    public Enemigo(float x, float y) {
        // Crea una region
        // Divide la región en frames de 90x90
        //Cambia los texture a textureRegion
        //
        /*
        texturePersonaje = new Texture("Personajes/enemigoArriba.png");
        textureDispose = new Texture("Personajes/enemigoExplota.png");
        */


        // Animacion dispose
        textureDispose = new TextureRegion(new Texture("Personajes/DeathGulAnimUpDownDispose.png"));
        TextureRegion[][] texturaEnemigoDispose = textureDispose.split(32,64);
        animEnemigoDispose = new Animation(0.15f,texturaEnemigoDispose[0][1], texturaEnemigoDispose[0][2],texturaEnemigoDispose[0][3],texturaEnemigoDispose[0][4],texturaEnemigoDispose[0][5],texturaEnemigoDispose[0][6],texturaEnemigoDispose[0][7],texturaEnemigoDispose[0][8],texturaEnemigoDispose[0][9],texturaEnemigoDispose[0][10],texturaEnemigoDispose[0][11], texturaEnemigoDispose[0][12], texturaEnemigoDispose[0][13], texturaEnemigoDispose[0][14]);
        animEnemigoDispose.setPlayMode(Animation.PlayMode.LOOP);


        // Animacion caminarUpDown
        texturePersonaje = new TextureRegion(new Texture("Personajes/DeathGulUpDown.png"));
        TextureRegion[][] texturaPersonajeUpDown = texturePersonaje.split(32,64);
        animEnemigoUpDown = new Animation(0.15f,texturaPersonajeUpDown[0][1],texturaPersonajeUpDown[0][2],texturaPersonajeUpDown[0][3], texturaPersonajeUpDown[0][4], texturaPersonajeUpDown[0][5]);
        animEnemigoUpDown.setPlayMode(Animation.PlayMode.LOOP);
        /*
        // Animacion derecha
        textureDerecha = new TextureRegion(new Texture("Personajes/DeathGulAnimDer.png"));
        TextureRegion[][] texturaEnemigoDerecha = textureDispose.split(64,32);
        animEnemigoDerecha = new Animation(0.15f,texturaEnemigoDerecha[0][1], texturaEnemigoDerecha[0][2],texturaEnemigoDerecha[0][3],texturaEnemigoDerecha[0][4]);
        animEnemigoDispose.setPlayMode(Animation.PlayMode.LOOP);






        // Animacion caminarLeftIzq
        TextureRegion textureLeft = new TextureRegion(new Texture("Personajes/DeathGulAnimIzq.png"));
        TextureRegion[][] texturaPersonajeLeft = textureLeft.split(32,64);
        animEnemigoLeft = new Animation(0.15f,texturaPersonajeLeft[0][1],texturaPersonajeLeft[0][2],texturaPersonajeLeft[0][3],texturaPersonajeLeft[0][4]);
        animEnemigoLeft.setPlayMode(Animation.PlayMode.LOOP);
        */

        timerAnimacion = 0;
        estado = EstadoEnemigo.DERECHA;
        this.x = x;
        this.y = x;
    }

    public void render(SpriteBatch batch){
        switch (estado){
            case DERECHA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                TextureRegion regionDerecha = (TextureRegion) animEnemigoUpDown.getKeyFrame(timerAnimacion);
                regionDerecha.flip(regionDerecha.isFlipX(), false);
                batch.draw(regionDerecha, x, y);
                break;
            case IZQUIERDA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                TextureRegion regionIzquierda = (TextureRegion) animEnemigoUpDown.getKeyFrame(timerAnimacion);
                regionIzquierda.flip(!regionIzquierda.isFlipX(), false);
                batch.draw(regionIzquierda, x, y);
                break;
            case DISPOSE:
                timerExplota += Gdx.graphics.getDeltaTime();
                TextureRegion regionDispose = (TextureRegion) animEnemigoDispose.getKeyFrame(timerExplota);
                if (timerExplota>=0.3f) {
                    estado = EstadoEnemigo.MUERTO;
                }
                batch.draw(regionDispose, x, y);
                break;
        }
    }

    public void mover(float dx, float dy){
        x += dx;
        y += dy;
        if (estado==EstadoEnemigo.DERECHA) {
            estado = EstadoEnemigo.IZQUIERDA;
        } else if (estado==EstadoEnemigo.IZQUIERDA){
            estado = EstadoEnemigo.DERECHA;
        }
    }

    public void disposeEnemigo(){
        estado = EstadoEnemigo.DISPOSE;
        timerExplota = 0;
    }

    public TextureRegion getTextura(){
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
