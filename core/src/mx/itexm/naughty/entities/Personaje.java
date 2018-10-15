package mx.itexm.naughty.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Personaje extends Objeto{
    private Animation animacion;
    private float timerAnimacion;
    private float x, y;

    // Estado de reposo
    EstadoMovimento estadoMover = EstadoMovimento.QUIETO;
    private static final float SPEED = 240; // Velocidad [pixeles/segundo]

    public Personaje(Texture texture) {
        // Crea una region
        TextureRegion region = new TextureRegion(texture);

        // Divide la región en frames de 32x64
        TextureRegion[][] texturaPersonaje = region.split(36,72);
        animacion = new Animation(0.15f,texturaPersonaje[0][3],texturaPersonaje[0][2],texturaPersonaje[0][1]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;

        // Quieto

        sprite = new Sprite(texturaPersonaje[0][0]);
        sprite.setPosition(120,64);
        x = sprite.getX();
        y = sprite.getY();
    }

    public void render(SpriteBatch batch){
        if (estadoMover==EstadoMovimento.QUIETO) {
            //batch.draw(marioQuieto.getTexture(),x,y);
            sprite.draw(batch);
        } else {
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
            if (estadoMover == EstadoMovimento.IZQUIERDA) {
                region.flip(region.isFlipX(), false);
            } else if (estadoMover == EstadoMovimento.DERECHA) {
                region.flip(region.isFlipX(), false);
            } else if (estadoMover == EstadoMovimento.ARRIBA) {
                region.flip(false, region.isFlipY());
            } else if (estadoMover == EstadoMovimento.ABAJO) {
                region.flip(false, region.isFlipY());
            }
            batch.draw(region, x, y);
        }
    }



    public void actualizar(TiledMap mapa) {
        // Verificar si se puede mover (no hay obstáculos, por ahora tubos verdes)
        switch (estadoMover) {
            case DERECHA:
                if (puedeMover(SPEED*Gdx.graphics.getDeltaTime(), 0 ,mapa)) {
                    mover(SPEED * Gdx.graphics.getDeltaTime(), 0);
                }
                break;
            case IZQUIERDA:
                if (puedeMover(-SPEED*Gdx.graphics.getDeltaTime(), 0, mapa)) {
                    mover(-SPEED * Gdx.graphics.getDeltaTime(), 0);
                }
                break;
            case ARRIBA:
                if (puedeMover(0, SPEED*Gdx.graphics.getDeltaTime(), mapa)) {
                    mover(0, SPEED * Gdx.graphics.getDeltaTime());
                }
                break;
            case ABAJO:
                if (puedeMover(0, -SPEED*Gdx.graphics.getDeltaTime(), mapa)) {
                    mover(0, -SPEED * Gdx.graphics.getDeltaTime());
                }
                break;
        }
    }

    private boolean puedeMover(float dx, float dy,  TiledMap mapa) {
        int cx = (int)(getX()+32)/32;
        int cy = (int)(getY())/32;
        // Obtener la celda en x,y
        TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(0);
        TiledMapTileLayer.Cell celda = capa.getCell(cx,cy);
        Object tipo = celda.getTile().getProperties().get("Tipo");
        if (!"Pared".equals(tipo)) {
            return true;
        }
        return false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void mover(float dx, float dy) {
        x += dx;
        y += dy;
        sprite.setPosition(x,y);
    }

    public void setEstadoMover(EstadoMovimento estadoMover) {
        this.estadoMover = estadoMover;
    }



    public enum EstadoMovimento {
        QUIETO,
        ABAJO,
        ARRIBA,
        DERECHA,
        IZQUIERDA
    }
}


