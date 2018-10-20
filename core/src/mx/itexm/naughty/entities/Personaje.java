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
    private float x;
    private float y;

    // Valores para capturas las velocidades del touchpad
    private float vx;
    private float vy;

    // Estado de reposo
    EstadoMovimento estadoMover = EstadoMovimento.QUIETO;
    private static final float SPEED = 3; // Velocidad [pixeles/segundo]

    public Personaje(Texture texture) {
        // Crea una region
        TextureRegion region = new TextureRegion(texture);

        // Divide la región en frames de 32x64
        TextureRegion[][] texturaPersonaje = region.split(90,90);

        animacion = new Animation(0.15f,texturaPersonaje[0][0],texturaPersonaje[0][1],texturaPersonaje[0][3], texturaPersonaje[0][4]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;

        texture = new Texture("Personajes/Jhony_standing.png");
        // Quieto
        sprite = new Sprite(texture);
        sprite.setPosition(120,64);
        x = sprite.getX();
        y = sprite.getY();
    }

    public void render(SpriteBatch batch){
        if (estadoMover==EstadoMovimento.QUIETO) {
            batch.draw(sprite.getTexture(), x, y);
            //batch.draw(marioQuieto.getTexture(),x,y);
            //sprite.draw(batch);

        } else {
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
            if (estadoMover == EstadoMovimento.IZQUIERDA) {
                region.flip(!region.isFlipX(), false);
            } else if (estadoMover == EstadoMovimento.DERECHA) {
                region.flip(region.isFlipX(), false);
            } else if (estadoMover == EstadoMovimento.ARRIBA) {
                region.flip(false, region.isFlipY());
            } else if (estadoMover == EstadoMovimento.ABAJO) {
                region.flip(false, !region.isFlipY());
            }
            batch.draw(region, x, y);
        }
    }

    public void actualizar(TiledMap mapa) {
        // Verificar si se puede mover (no hay obstáculos, por ahora tubos verdes)
        switch (estadoMover) {
            case DERECHA:
                if (puedeMover(mapa, 1,1)) {
                    mover(vx*SPEED, vy*SPEED);
                }break;
            case IZQUIERDA:
                if (puedeMover(mapa,0,1)) {
                    mover(vx*SPEED, vy*SPEED);
                }break;
            case ARRIBA:
                if (puedeMover(mapa,1,1)) {
                    mover(vx*SPEED, vy*SPEED);
                }break;
            case ABAJO:
                if (puedeMover(mapa,1,0)) {
                    mover(vx*SPEED, vy*SPEED);
                }break;
        }
    }

    private boolean puedeMover(TiledMap mapa, float dirX, float dirY) {
        // Verifica si lo que esta delante de el es un obstaculo
        int cx = (int)(x+dirX*90)/32;
        int cy = (int)(y+dirY*45)/32;
        // Obtener la celda en x,y
        TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(0);
        TiledMapTileLayer.Cell celda = capa.getCell(cx,cy);
        Object tipo = celda.getTile().getProperties().get("Tipo");
        if (!"Pared".equals(tipo)) {
            return true;
        }
        return false;
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

    public void setVx(float vx) {
        this.vx = vx;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
