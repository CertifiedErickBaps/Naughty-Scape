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

    public PantallaAjustes(PantallaInicio pantallaInicio) {
        this.pantallaInicio=pantallaInicio;
    }
    private void crearEscena(){
        escenaAjustes=new Stage(vista);
        TextureRegionDrawable trdRegresar_up=new TextureRegionDrawable(new TextureRegion(new Texture("regresar.png")));
        TextureRegionDrawable trdRegresar_down=new TextureRegionDrawable(new TextureRegion(new Texture("regresar_s.png")));
        ImageButton btnReg=new ImageButton(trdRegresar_up,trdRegresar_down);
        btnReg.setPosition(50,50);
        btnReg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen(new PantallaMenu(pantallaInicio));
            }
        });
        escenaAjustes.addActor(btnReg);

    }
    @Override
    public void show() {
        crearEscena();
        textFondo=new Texture("creditos_fondo.png");
        Gdx.input.setInputProcessor(escenaAjustes);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(textFondo,0,0);
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
