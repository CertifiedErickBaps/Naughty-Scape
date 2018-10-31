package mx.itesm.naughty;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.itesm.naughty.Screens.PlayScreen;

public class MainGame extends Game {
	public static final int ANCHO_JUEGO = 800;
	public static final int ALTO_JUEGO = 480;
	public static final float PPM = 100f;

	public SpriteBatch batch;
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}
