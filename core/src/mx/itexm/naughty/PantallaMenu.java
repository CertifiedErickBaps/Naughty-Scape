package mx.itexm.naughty;

import com.badlogic.gdx.Gdx;
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
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void crearEscena() {
        escenaMenu = new Stage(vista);
        //Botones normales
        Texture textBtnPlay = new Texture("jugar.png");
        Texture textBtnCredit = new Texture("creditos.png");
        Texture textBtnDificult = new Texture("dificultad.png");
        Texture textBtnOptions = new Texture("opciones.png");

        TextureRegionDrawable trdP = new TextureRegionDrawable(new TextureRegion(textBtnPlay));
        TextureRegionDrawable trdC = new TextureRegionDrawable(new TextureRegion(textBtnCredit));
        TextureRegionDrawable trdD = new TextureRegionDrawable(new TextureRegion(textBtnDificult));
        TextureRegionDrawable trdO = new TextureRegionDrawable(new TextureRegion(textBtnOptions));


        //Botones suprimidos
        Texture textBtnPlayS = new Texture("jugar_s.png");
        Texture textBtnCreditS = new Texture("creditos_s.png");
        Texture textBtnDificultS = new Texture("dificultad_s.png");
        Texture textBtnOptionS = new Texture("opciones_s.png");
        TextureRegionDrawable trdPs = new TextureRegionDrawable(new TextureRegion(textBtnPlayS));
        TextureRegionDrawable trdCs = new TextureRegionDrawable(new TextureRegion(textBtnCreditS));
        TextureRegionDrawable trdDs = new TextureRegionDrawable(new TextureRegion(textBtnDificultS));
        TextureRegionDrawable trdOs = new TextureRegionDrawable(new TextureRegion(textBtnOptionS));

        ImageButton btnPlay = new ImageButton(trdP, trdPs);
        ImageButton btnCredit = new ImageButton(trdC, trdCs);
        ImageButton btnDificult = new ImageButton(trdD, trdDs);
        ImageButton btnOptions = new ImageButton(trdO, trdOs);



        //Posicion
        btnPlay.setPosition(ANCHO/2-btnPlay.getWidth()/2, 0.8f*ALTO-btnPlay.getHeight()/2);
        btnCredit.setPosition(ANCHO/2-btnCredit.getWidth()/2, 0.60f*ALTO-btnCredit.getHeight()/2);
        btnDificult.setPosition(ANCHO/2-btnDificult.getWidth()/2, 0.40f*ALTO-btnDificult.getHeight()/2);
        btnOptions.setPosition(ANCHO/2-btnOptions.getWidth()/2, 0.20f*ALTO-btnOptions.getHeight()/2);


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
        escenaMenu.addActor(btnDificult);
        escenaMenu.addActor(btnOptions);
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
