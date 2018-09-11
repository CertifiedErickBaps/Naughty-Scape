package mx.itexm.naughty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    // Numero de muertes totales en juego
    private int puntosJugador = 0;
    private Texto texto; // mensajes en el juego

    @Override
    public void show() {
        crearEscena();
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void crearEscena() {
        escenaMenu = new Stage(vista);

        //Botones normales
        Texture textBtnCPlay = new Texture("Cjugar.png");
        Texture textBtnAjuste = new Texture("ajustes.png");
        Texture textBtnAcerca = new Texture("acerca.png");
        Texture logo = new Texture("logo.png");

        TextureRegionDrawable trdCP = new TextureRegionDrawable(new TextureRegion(textBtnCPlay));
        TextureRegionDrawable trdAj = new TextureRegionDrawable(new TextureRegion(textBtnAjuste));
        TextureRegionDrawable trdA = new TextureRegionDrawable(new TextureRegion(textBtnAcerca));
        TextureRegionDrawable trdLogo = new TextureRegionDrawable(new TextureRegion(logo));

        //Botones suprimidos
        Texture textBtnCPlayS = new Texture("Cjugar_s.png");
        Texture textBtnAjusteS = new Texture("ajustes_s.png");
        Texture textBtnAcercaS = new Texture("acerca_s.png");
        TextureRegionDrawable trdCPs = new TextureRegionDrawable(new TextureRegion(textBtnCPlayS));
        TextureRegionDrawable trdAjs = new TextureRegionDrawable(new TextureRegion(textBtnAjusteS));
        TextureRegionDrawable trdAs = new TextureRegionDrawable(new TextureRegion(textBtnAcercaS));

        ImageButton btnCPlay = new ImageButton(trdCP, trdCPs);
        ImageButton btnAjuste = new ImageButton(trdAj, trdAjs);
        ImageButton btnAcerca = new ImageButton(trdA, trdAs);
        ImageButton btnLogo = new ImageButton(trdLogo);



        //Posicion
        btnCPlay.setPosition(ANCHO/2-btnCPlay.getWidth()/2, 0.80f*ALTO-btnCPlay.getHeight()/2);
        btnAjuste.setPosition(0.10f*ANCHO-btnAjuste.getWidth()/2, 0.10f*ALTO-btnAjuste.getHeight()/2);
        btnAcerca.setPosition(0.86f*ANCHO-btnAcerca.getWidth()/2, 0.10f*ALTO-btnAcerca.getHeight()/2);
        btnLogo.setPosition(ANCHO/2-btnLogo.getWidth()/2, 0.65f*ALTO-btnLogo.getHeight()/2);



        //Acciones Boton
        btnCPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen( new PantallaJuego(pantallaInicio));
            }
        });

        btnAcerca.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen( new PantallaAcerca(pantallaInicio));
            }
        });

        btnAjuste.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen( new PantallaAjustes(pantallaInicio));
            }
        });
        //escenaMenu.addActor(btnCPlay);
        escenaMenu.addActor(btnAjuste);
        escenaMenu.addActor(btnAcerca);
        escenaMenu.addActor(btnLogo);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0.34f,0.43f,0.46f);
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
