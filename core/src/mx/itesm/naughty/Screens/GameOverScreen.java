package mx.itesm.naughty.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Pantallas.MainScreen;
import mx.itesm.naughty.Pantallas.MenuScreen;


public class GameOverScreen extends MainScreen {
    private final MainGame mainGame;
    private Stage escenaAcerca;
    private Texture textFondo;

    public GameOverScreen(MainGame mainGame) {
        this.mainGame=mainGame;
    }

    private void crearEscena(){
        escenaAcerca=new Stage(gamePort);
        TextureRegionDrawable trdRegresar_up=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/menuBtn.png")));
        TextureRegionDrawable trdRegresar_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/menuBtnPres.png")));

        TextureRegionDrawable trdAgain=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/tryAgainBtn.png")));
        TextureRegionDrawable trdAgain_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/tryAgainBtnPres.png")));

        ImageButton btnPlay=new ImageButton(trdAgain,trdAgain_down);
        btnPlay.setPosition((MainGame.ANCHO_PANTALLA * 0.4f - btnPlay.getWidth() / 2),(MainGame.ALTO_PANTALLA* 0.2f- btnPlay.getHeight() / 2) );
        btnPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new PlayScreen(mainGame, 1));
            }
        });
        ImageButton btnReg=new ImageButton(trdRegresar_up,trdRegresar_down);
        btnReg.setPosition((MainGame.ANCHO_PANTALLA * 0.8f - btnReg.getWidth() / 2),(MainGame.ALTO_PANTALLA* 0.2f- btnReg.getHeight() / 2) );
        btnReg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new MenuScreen(mainGame));
            }
        });
        escenaAcerca.addActor(btnReg);
        escenaAcerca.addActor(btnPlay);

    }
    @Override
    public void show() {
        crearEscena();
        textFondo=new Texture("gameOver.jpg");
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
