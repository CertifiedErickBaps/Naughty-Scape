package mx.itexm.naughty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;

class PantallaJuego extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Texture textFondo;

    public PantallaJuego(PantallaInicio pantallaInicio) {
        this.pantallaInicio=pantallaInicio;
    }

    @Override
    public void show() {
        textFondo=new Texture("juego_fondo.jpg");
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(textFondo,0,0);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    class ProcesadorEntrada implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            pantallaInicio.setScreen(new PantallaMenu(pantallaInicio));
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
