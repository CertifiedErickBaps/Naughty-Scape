package mx.itesm.naughty;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.itesm.naughty.Pantallas.MenuScreen;

public class MainGame extends Game {
	public static final float ANCHO_PANTALLA = 1280;
	public static final float ALTO_PANTALLA = 720;
	public static final float ANCHO_JUEGO = 800;
	public static final float ALTO_JUEGO = 480;
	public static final float PPM = 100f;
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short ARMA_BIT = 4;
	public static final short COFRE_BIT = 8;
	public static final short DESTROY_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ITEM_BIT = 128;
	public static final short PLAYER_HEAD_BIT = 256;
	public static final short BALA_BIT = 512;

	public static SpriteBatch batch;
	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("Musica/chest.mp3", Music.class);
		manager.load("Musica/menuSound.mp3", Music.class);
		manager.load("Musica/nivel1.mp3", Music.class);
		manager.load("Musica/nivel2.mp3", Music.class);
		manager.load("Musica/punch.mp3", Music.class);
		manager.load("Musica/error.mp3", Music.class);
		manager.finishLoading();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}

	@Override
	public void render() {
		super.render();
	}
}
