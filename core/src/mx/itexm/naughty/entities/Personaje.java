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

public class Personaje extends Objeto{
    private Animation animacion;
    private Animation animacionAtaque;
    private Animation animacionLeft;
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
        // Divide la región en frames de 90x90

        // Animacion caminarUpDown
        TextureRegion[][] texturaPersonaje = region.split(90,90);
        animacion = new Animation(0.15f,texturaPersonaje[0][0],texturaPersonaje[0][1],texturaPersonaje[0][2], texturaPersonaje[0][3]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);

        // Animacion caminarUpDown
        TextureRegion textureLeft = new TextureRegion(new Texture("Personajes/Jhony_walkLeft.png"));
        TextureRegion[][] texturaPersonajeLeft = textureLeft.split(90,90);
        animacionLeft = new Animation(0.15f,texturaPersonajeLeft[0][0],texturaPersonajeLeft[0][1],texturaPersonajeLeft[0][2], texturaPersonajeLeft[0][3],texturaPersonajeLeft[0][4]);
        animacionLeft.setPlayMode(Animation.PlayMode.LOOP);

        // Animacion golpes
        TextureRegion textureAtaque = new TextureRegion(new Texture("Personajes/Jhony_golpesUpDown.png"));
        TextureRegion[][] texturaPersonajeAtaque = textureAtaque.split(90,90);
        animacionAtaque = new Animation(0.15f,texturaPersonajeAtaque[0][0],texturaPersonajeAtaque[0][1],texturaPersonajeAtaque[0][2], texturaPersonajeAtaque[0][3]);
        animacionAtaque.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;

        texture = new Texture("Personajes/Jhony_standingUpDown.png");
        // Quieto
        sprite = new Sprite(texture);
        sprite.setPosition(150,150);
        x = sprite.getX();
        y = sprite.getY();
    }

    public void render(SpriteBatch batch){
        if (estadoMover==EstadoMovimento.QUIETO) {
            batch.draw(sprite.getTexture(), x, y);
            //batch.draw(marioQuieto.getTexture(),x,y);
            //sprite.draw(batch);

        } else {
            if (estadoMover == EstadoMovimento.IZQUIERDA) {
                timerAnimacion += Gdx.graphics.getDeltaTime();
                TextureRegion regionLeft = (TextureRegion) animacionLeft.getKeyFrame(timerAnimacion);
                regionLeft.flip(regionLeft.isFlipX(), false);
                batch.draw(regionLeft, x, y);
            } else if (estadoMover == EstadoMovimento.DERECHA) {
                timerAnimacion += Gdx.graphics.getDeltaTime();
                TextureRegion regionRight = (TextureRegion) animacionLeft.getKeyFrame(timerAnimacion);
                regionRight.flip(!regionRight.isFlipX(), false);
                batch.draw(regionRight, x, y);
            } else if (estadoMover == EstadoMovimento.ARRIBA) {
                timerAnimacion += Gdx.graphics.getDeltaTime();
                TextureRegion region = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
                region.flip(false, region.isFlipY());
                batch.draw(region, x, y);
            } else if (estadoMover == EstadoMovimento.ABAJO) {
                timerAnimacion += Gdx.graphics.getDeltaTime();
                TextureRegion region = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
                region.flip(false, !region.isFlipY());
                batch.draw(region, x, y);
            } else if (estadoMover == EstadoMovimento.ATAQUE_GOLPE_UP) {
                timerAnimacion += Gdx.graphics.getDeltaTime();
                TextureRegion regionAtaque = (TextureRegion) animacionAtaque.getKeyFrame(timerAnimacion);
                regionAtaque.flip(false, regionAtaque.isFlipY());
                batch.draw(regionAtaque, x, y);
            }
        }
    }

    public void actualizar(TiledMap mapa) {
        // Verificar si se puede mover (no hay obstáculos, por ahora tubos verdes)
        switch (estadoMover) {
            case DERECHA:
                if (puedeMover(mapa, 2.5f,0)) {
                    mover(vx*SPEED, vy*SPEED);
                }break;
            case IZQUIERDA:
                if (puedeMover(mapa,0.5f,0)) {
                    mover(vx*SPEED, vy*SPEED);
                }break;
            case ARRIBA:
                if (puedeMover(mapa,0,1)) {
                    mover(vx*SPEED, vy*SPEED);
                }break;
            case ABAJO:
                if (puedeMover(mapa,0,-1)) {
                    mover(vx*SPEED, vy*SPEED);
                }break;
        }
    }

    // Deteccion de colisiones
    private boolean puedeMover(TiledMap mapa, float dirX, float dirY) {
        // Verifica si lo que esta delante de él es un obstaculo
        int cx = (int)(getX()+(dirX*32))/32;
        int cy = (int)(getY()+(dirY*32))/32;
        // Capas
        MapLayers mapLayers = mapa.getLayers();
        TiledMapTileLayer capaPared = (TiledMapTileLayer) mapLayers.get("Pared");
        TiledMapTileLayer.Cell celdaPared = capaPared.getCell(cx,cy);

        // Obtener la celda en x,y
        if (celdaPared!=null){
            Object tipoPared = celdaPared.getTile().getProperties().get("Tipo");
            if (!"Pared".equals(tipoPared)) return true;
            else return false;
        }
        return true;
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
        IZQUIERDA,
        ATAQUE_GOLPE_UP
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public void setX(float x) {
        this.x = x;
        sprite.setPosition(x,y);
    }

    public void setY(float y) {
        this.y = y;
        sprite.setPosition(x,y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
