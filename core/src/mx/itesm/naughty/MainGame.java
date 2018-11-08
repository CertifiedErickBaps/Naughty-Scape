package mx.itesm.naughty;

import com.badlogic.gdx.Game;

import mx.itesm.naughty.Screens.MenuScreen;
import mx.itesm.naughty.Screens.PlayScreen;

public class MainGame extends Game {
	@Override
	public void create () {
		setScreen(new MenuScreen(this));

	}

}
