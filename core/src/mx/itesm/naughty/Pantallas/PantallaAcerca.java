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
        Gdx.input.setInputProcessor(escenaAcerca);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        mainGame.batch.setProjectionMatrix(gameCam.combined);
        mainGame.batch.begin();
        mainGame.batch.draw(textFondo,0,0);
        mainGame.batch.end();
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