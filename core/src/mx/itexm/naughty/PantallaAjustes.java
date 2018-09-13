package mx.itexm.naughty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaAjustes extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Stage escenaAjustes;
    private Texture textFondo;
    private Texto Sound_FX;
    private Texto Music;

    public PantallaAjustes(PantallaInicio pantallaInicio) {
        this.pantallaInicio=pantallaInicio;
    }
    private void crearEscena(){
        escenaAjustes=new Stage(vista);

        TextureRegionDrawable trdRegresar_up=new TextureRegionDrawable(new TextureRegion(new Texture("regresar.png")));
        TextureRegionDrawable trdRegresar_down=new TextureRegionDrawable(new TextureRegion(new Texture("regresar_s.png")));
        TextureRegionDrawable trdCasiilla_up=new TextureRegionDrawable(new TextureRegion(new Texture("check.png")));
        TextureRegionDrawable trdCasilla_down=new TextureRegionDrawable(new TextureRegion(new Texture("check_s.png")));

        ImageButton btnReg=new ImageButton(trdRegresar_up,trdRegresar_down);
        ImageButton btnCheck1=new ImageButton(trdCasiilla_up,trdCasilla_down);
        ImageButton btnCheck2=new ImageButton(trdCasiilla_up,trdCasilla_down);
        btnReg.setPosition(50,50);
        btnCheck1.setPosition(ANCHO*0.60f,ALTO*0.60f);
        btnCheck2.setPosition(ANCHO*0.60f,ALTO*0.30f);
        btnReg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen(new PantallaMenu(pantallaInicio));
            }
        });
        escenaAjustes.addActor(btnReg);
        escenaAjustes.addActor(btnCheck1);
        escenaAjustes.addActor(btnCheck2);

    }
    @Override
    public void show() {
        crearEscena();
        textFondo=new Texture("menu_fondo.jpg");
        Sound_FX=new Texto();
        Music=new Texto();
        Gdx.input.setInputProcessor(escenaAjustes);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(textFondo,0,0);
        Sound_FX.mostrarMensaje(batch,"Sound FX",ANCHO*0.40f,ALTO*0.70f);
        Music.mostrarMensaje(batch,"Music",ANCHO*0.40f,ALTO*0.40f);
        batch.end();
        escenaAjustes.draw();
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
