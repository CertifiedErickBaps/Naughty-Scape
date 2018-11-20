package mx.itesm.naughty.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.naughty.MainGame;

import static mx.itesm.naughty.MainGame.ALTO_PANTALLA;
import static mx.itesm.naughty.MainGame.ANCHO_PANTALLA;

class PantallaAcerca extends MainScreen {
    private final MainGame mainGame;
    private Stage escenaAcerca;
    private Texture textFondo;
    private Texto info;

    public PantallaAcerca(MainGame mainGame) {
        this.mainGame=mainGame;
    }

    private void crearEscena(){
        escenaAcerca=new Stage(gamePort);
        TextureRegionDrawable trdRegresar_up=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/Back.png")));
        TextureRegionDrawable trdRegresar_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BackPres.png")));
        ImageButton btnReg=new ImageButton(trdRegresar_up,trdRegresar_down);
        btnReg.setPosition(50,50);
        btnReg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new MenuScreen(mainGame));
            }
        });
        escenaAcerca.addActor(btnReg);

    }
    @Override
    public void show() {
        crearEscena();
        textFondo=new Texture("acerca_fondo.jpg");
        info=new Texto();
        Gdx.input.setInputProcessor(escenaAcerca);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        MainGame.batch.setProjectionMatrix(gameCam.combined);
        MainGame.batch.begin();
        MainGame.batch.draw(textFondo,0,0);
        info.mostrarMensaje(MainGame.batch, "--CREADO POR--"+"\n"+"Eric Gomez (ISC)"+"\n"+"Erick Bautista (ISC)"+"\n"+"Jessica I. Alvarez (LAD)"+"\n"+"Sebastian Gomez (LAD)" ,ANCHO_PANTALLA*0.7f, ALTO_PANTALLA*0.90f);
        MainGame.batch.end();
        escenaAcerca.draw();
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
        escenaAcerca.dispose();
    }
}
