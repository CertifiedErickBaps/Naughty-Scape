package mx.itesm.naughty.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.naughty.MainGame;

class PantallaAjustes extends MainScreen {
    private final MainGame mainGame;
    private Stage escenaAjustes;
    private Texture textFondo;
    private Texto Sound_FX;
    private Texto Music;

    public PantallaAjustes(MainGame mainGame) {
        this.mainGame=mainGame;
    }
    private void crearEscena(){
        escenaAjustes = new Stage(gamePort);

        TextureRegionDrawable trdRegresar_up=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/Back.png")));
        TextureRegionDrawable trdRegresar_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BackPres.png")));
        TextureRegionDrawable trdCasiilla_up=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/check.png")));
        TextureRegionDrawable trdCasilla_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/check_s.png")));


        ImageButton btnReg = new ImageButton(trdRegresar_up,trdRegresar_down);

        final ImageButton btnCheck1 = new ImageButton(trdCasiilla_up,trdCasilla_down, trdCasilla_down);
        final ImageButton btnCheck2 = new ImageButton(trdCasiilla_up,trdCasilla_down, trdCasilla_down);
        btnReg.setPosition(50,50);
        btnCheck1.setPosition(ANCHO_PANTALLA*0.60f,ALTO_PANTALLA*0.60f);
        btnCheck1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(btnCheck1.isChecked()){
                    btnCheck1.setChecked(true);
                } else{
                    btnCheck1.setChecked(false);
                }
            }
        });

        btnCheck2.setPosition(ANCHO_PANTALLA*0.60f,ALTO_PANTALLA*0.30f);
        btnCheck2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(btnCheck2.isChecked()){
                    btnCheck2.setChecked(true);
                } else{
                    btnCheck2.setChecked(false);
                }
            }
        });


        btnReg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new MenuScreen(mainGame));
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
        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        batch.draw(textFondo,0,0);
        Sound_FX.mostrarMensaje(batch,"Sound FX",ANCHO_PANTALLA*0.40f,ALTO_PANTALLA*0.70f);
        Music.mostrarMensaje(batch,"Music",ANCHO_PANTALLA*0.40f,ALTO_PANTALLA*0.40f);
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
        escenaAjustes.dispose();
    }
}
