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


public class WinScreen extends MainScreen {

    private final MainGame mainGame;
    private Stage escenaWin;
    private Texture textFondo;

    public WinScreen(MainGame mainGame) {
        this.mainGame=mainGame;
    }

    private void crearEscena(){
        escenaWin =new Stage(gamePort);
        TextureRegionDrawable trdSalir=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/salirBtn.png")));
        TextureRegionDrawable trdSalir_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/salirBtnPres.png")));

        TextureRegionDrawable trdRContinuar=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/continuarBtn.png")));
        TextureRegionDrawable trdContinuar_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/continuarBtnPres.png")));


        ImageButton btnSalir=new ImageButton(trdSalir,trdSalir_down);
        btnSalir.setPosition((MainGame.ANCHO_PANTALLA * 0.8f - btnSalir.getWidth() / 2),(MainGame.ALTO_PANTALLA* 0.2f- btnSalir.getHeight() / 2) );
        btnSalir.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new MenuScreen(mainGame));
            }
        });

        ImageButton btnContinuar=new ImageButton(trdRContinuar,trdContinuar_down);
        btnContinuar.setPosition((MainGame.ANCHO_PANTALLA * 0.5f - btnContinuar.getWidth() / 2),(MainGame.ALTO_PANTALLA* 0.2f- btnSalir.getHeight() / 2) );
        btnContinuar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new PlayScreen(mainGame, 2, "Jhony"));
            }
        });

        escenaWin.addActor(btnContinuar);
        escenaWin.addActor(btnSalir);

    }
    @Override
    public void show() {
        crearEscena();
        textFondo=new Texture("youwin.jpg");
        Gdx.input.setInputProcessor(escenaWin);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        MainGame.batch.setProjectionMatrix(gameCam.combined);
        MainGame.batch.begin();
        MainGame.batch.draw(textFondo,0,0);
        MainGame.batch.end();
        escenaWin.draw();
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
        escenaWin.dispose();
    }
}