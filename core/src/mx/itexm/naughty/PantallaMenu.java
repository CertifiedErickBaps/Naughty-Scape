package mx.itexm.naughty;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

class PantallaMenu extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Stage escenaMenu;

    public PantallaMenu(PantallaInicio pantallaInicio){
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        crearEscena();
    }

    private void crearEscena() {
        escenaMenu = new Stage(vista);
        //Boton normal
        //Boton suprimido

        //Acciones Boton
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        escenaMenu.draw();
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
}
