package mx.itexm.naughty.screens;

import com.badlogic.gdx.Gdx;
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
    private Sprite logo;
    private Texture fondo;

    public PantallaMenu(PantallaInicio pantallaInicio){
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        crearEscena();
        fondo=new Texture("menu_fondo.jpg");
        logo =new Sprite(new Texture("logo.png"));
        logo.setPosition(ANCHO_PANTALLA/2-logo.getWidth()/2, 0.65f*ALTO_PANTALLA-logo.getHeight()/2);
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void crearEscena() {
        escenaMenu = new Stage(vistaPantalla);

        //Botones normales
        TextureRegionDrawable trdP = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/button_jugar.png")));
        TextureRegionDrawable trdAj = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/button_ajustes.png")));
        TextureRegionDrawable trdA = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/button_acerca.png")));
        //Botones suprimidos
        TextureRegionDrawable trdPs = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/button_jugar_s.png")));
        TextureRegionDrawable trdAjs = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/button_ajustes_s.png")));
        TextureRegionDrawable trdAs = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/button_acerca_s.png")));

        ImageButton btnPlay = new ImageButton(trdP, trdPs);
        ImageButton btnAjuste = new ImageButton(trdAj, trdAjs);
        ImageButton btnAcerca = new ImageButton(trdA, trdAs);
        //Posicion
        btnPlay.setPosition(ANCHO_PANTALLA/2-btnPlay.getWidth()/2, 0.10f*ALTO_PANTALLA-btnPlay.getHeight()/2);
        btnAjuste.setPosition(0.10f*ANCHO_PANTALLA-btnAjuste.getWidth()/2, 0.10f*ALTO_PANTALLA-btnAjuste.getHeight()/2);
        btnAcerca.setPosition(0.86f*ANCHO_PANTALLA-btnAcerca.getWidth()/2, 0.10f*ALTO_PANTALLA-btnAcerca.getHeight()/2);



        //Acciones Boton
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen( new PantallaDificultad(pantallaInicio));
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
        escenaMenu.addActor(btnPlay);
        escenaMenu.addActor(btnAjuste);
        escenaMenu.addActor(btnAcerca);

    }

    @Override
    public void render(float delta) {
        borrarPantalla(0.34f,0.43f,0.46f);
        batchPantalla.setProjectionMatrix(camaraPantalla.combined);
        batchPantalla.begin();
        batchPantalla.draw(fondo,0,0);
        batchPantalla.draw(logo,logo.getX(),logo.getY());
        batchPantalla.end();
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
