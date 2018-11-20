package mx.itesm.naughty.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Pantallas.MainScreen;
import mx.itesm.naughty.Pantallas.MenuScreen;

import static mx.itesm.naughty.MainGame.ALTO_PANTALLA;
import static mx.itesm.naughty.MainGame.ANCHO_PANTALLA;

public class WinScreen extends MainScreen {

    private final MainGame mainGame;
    private Stage escenaAcerca;
    private Texture textFondo;

    public WinScreen(MainGame mainGame) {
        this.mainGame=mainGame;
    }

    private void crearEscena(){
        escenaAcerca=new Stage(gamePort);
        TextureRegionDrawable trdRegresar_up=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/menuBtn.png")));
        TextureRegionDrawable trdRegresar_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/menuBtnPres.png")));
        TextureRegionDrawable trdRContinuar=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/continuar.png")));
        TextureRegionDrawable trdContinuar_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/continuarPres.png")));

        ImageButton btnReg=new ImageButton(trdRegresar_up,trdRegresar_down);
        btnReg.setPosition((MainGame.ANCHO_PANTALLA / 2 - btnReg.getWidth() / 2),(MainGame.ALTO_PANTALLA* 0.2f- btnReg.getHeight() / 2) );
        btnReg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new MenuScreen(mainGame));
            }
        });

        ImageButton btnPlay=new ImageButton(trdRContinuar,trdContinuar_down);
        btnPlay.setPosition((MainGame.ANCHO_PANTALLA / 2 - btnPlay.getWidth() / 2),(MainGame.ALTO_PANTALLA* 0.5f- btnPlay.getHeight() / 2) );
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new PlayScreen(mainGame));
            }
        });
        escenaAcerca.addActor(btnReg);
        escenaAcerca.addActor(btnPlay);

    }
    @Override
    public void show() {
        crearEscena();
        textFondo=new Texture("youWin.jpg");
        Gdx.input.setInputProcessor(escenaAcerca);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        MainGame.batch.setProjectionMatrix(gameCam.combined);
        MainGame.batch.begin();
        MainGame.batch.draw(textFondo,0,0);
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