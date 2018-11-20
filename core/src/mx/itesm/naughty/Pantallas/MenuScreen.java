package mx.itesm.naughty.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.itesm.naughty.MainGame;

import static mx.itesm.naughty.MainGame.ALTO_PANTALLA;
import static mx.itesm.naughty.MainGame.ANCHO_PANTALLA;

public class MenuScreen extends MainScreen {
    private final MainGame mainGame;
    private Stage escenaMenu;
    private Sprite logo;
    private Texture fondo;

    public MenuScreen(MainGame mainGame){
        this.mainGame = mainGame;
    }

    @Override
    public void show() {
        crearEscena();
        fondo=new Texture("menu_fondo.jpg");
        logo =new Sprite(new Texture("logo.png"));
        logo.setPosition((ANCHO_PANTALLA/2-logo.getWidth()/2), (0.65f*ALTO_PANTALLA-logo.getHeight()/2));
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void crearEscena() {
        escenaMenu = new Stage(gamePort);

        //Botones normales
        TextureRegionDrawable trdP = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/playBtn.png")));
        TextureRegionDrawable trdAj = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/btnAjustes.png")));
        TextureRegionDrawable trdA = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/acerca.png")));
        //Botones suprimidos
        TextureRegionDrawable trdPs = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/playPres.png")));
        TextureRegionDrawable trdAjs = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/AjustesPres.png")));
        TextureRegionDrawable trdAs = new TextureRegionDrawable(new TextureRegion(new Texture("Botones/acercaPres.png")));

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
                mainGame.setScreen( new PantallaDificultad(mainGame));
            }
        });

        btnAcerca.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen( new PantallaAcerca(mainGame));
            }
        });

        btnAjuste.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen( new PantallaAjustes(mainGame));
            }
        });
        escenaMenu.addActor(btnPlay);
        escenaMenu.addActor(btnAjuste);
        escenaMenu.addActor(btnAcerca);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.34f,0.43f,0.46f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MainGame.batch.setProjectionMatrix(gameCam.combined);
        MainGame.batch.begin();
        MainGame.batch.draw(fondo,0,0);
        MainGame.batch.draw(logo,logo.getX(),logo.getY());
        MainGame.batch.end();
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
        escenaMenu.dispose();
    }
}
