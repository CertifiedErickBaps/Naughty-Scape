package mx.itexm.naughty.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaDificultad extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Stage escenaDificultad;
    private Texture fondo;

    public PantallaDificultad(PantallaInicio pantallaInicio) {
        this.pantallaInicio=pantallaInicio;
    }

    private void crearEscena(){
        escenaDificultad=new Stage(vistaPantalla);
        TextureRegionDrawable trdRegresar_up=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/regresar.png")));
        TextureRegionDrawable trdRegresar_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/regresar_s.png")));
        TextureRegionDrawable trdJhony =new TextureRegionDrawable(new TextureRegion(new Texture("Jhony.png")));
        TextureRegionDrawable trdOrlando=new TextureRegionDrawable(new TextureRegion(new Texture("Orlando.png")));
        TextureRegionDrawable trdJiovany=new TextureRegionDrawable(new TextureRegion(new Texture("Jiovany.png")));

        ImageButton btnJhony =new ImageButton(trdJhony);
        ImageButton btnOrlando=new ImageButton(trdOrlando);
        ImageButton btnJiovany=new ImageButton(trdJiovany);
        ImageButton btnReg=new ImageButton(trdRegresar_up,trdRegresar_down);

        btnJhony.setPosition(ANCHO_PANTALLA*0.25f,ALTO_PANTALLA/2);
        btnOrlando.setPosition(ANCHO_PANTALLA*0.45f,ALTO_PANTALLA/2);
        btnJiovany.setPosition(ANCHO_PANTALLA*0.65f,ALTO_PANTALLA/2);
        btnReg.setPosition(50,50);

        btnJhony.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen(new PantallaJuego(pantallaInicio));
            }
        });

        btnOrlando.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen(new PantallaJuego(pantallaInicio));
            }
        });

        btnJiovany.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen(new PantallaJuego(pantallaInicio));
            }
        });

        btnReg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen(new PantallaMenu(pantallaInicio));
            }
        });
        escenaDificultad.addActor(btnJhony);
        escenaDificultad.addActor(btnOrlando);
        escenaDificultad.addActor(btnJiovany);
        escenaDificultad.addActor(btnReg);
    }
    @Override
    public void show() {
        crearEscena();
        fondo=new Texture("dificultad_fondo.jpg");
        Gdx.input.setInputProcessor(escenaDificultad);
    }

    @Override
    public void render(float delta) {
        batchPantalla.setProjectionMatrix(camaraPantalla.combined);
        batchPantalla.begin();
        batchPantalla.draw(fondo,0,0);
        batchPantalla.end();
        escenaDificultad.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
