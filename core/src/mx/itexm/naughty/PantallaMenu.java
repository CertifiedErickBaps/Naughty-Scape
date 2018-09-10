package mx.itexm.naughty;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
        //Botones normales
        Texture textBtnPlay = new Texture("jugar.png");
        Texture textBtnCredit = new Texture("creditos.png");
        TextureRegionDrawable trdP = new TextureRegionDrawable(new TextureRegion(textBtnPlay));
        TextureRegionDrawable trdC = new TextureRegionDrawable(new TextureRegion(textBtnCredit));
        //Botones suprimidos
        Texture textBtnPlayS = new Texture("jugar_s.png");
        Texture textBtnCreditS = new Texture("creditos_s.png");
        TextureRegionDrawable trdPs = new TextureRegionDrawable(new TextureRegion(textBtnPlayS));
        TextureRegionDrawable trdCs = new TextureRegionDrawable(new TextureRegion(textBtnCreditS));

        ImageButton btnPlay = new ImageButton(trdP, trdPs);
        ImageButton btnCredit = new ImageButton(trdC, trdCs);

        //Posicion
        btnPlay.setPosition(ANCHO/7-btnPlay.getWidth()/2, 0.8f*ALTO);
        btnCredit.setPosition(ANCHO/7-btnPlay.getWidth()/2, 0.60f*ALTO);


        //Acciones Boton
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen( new PantallaJuego(pantallaInicio));
            }
        });

        escenaMenu.addActor(btnPlay);
        escenaMenu.addActor(btnCredit);
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
