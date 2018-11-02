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

class PantallaDificultad extends MainScreen {
    private final MainGame mainGame;
    private Stage escenaDificultad;
    private Texture fondo;

    public PantallaDificultad(MainGame mainGame) {
        this.mainGame=mainGame;
    }

    private void crearEscena(){
        escenaDificultad=new Stage(gamePort);
        TextureRegionDrawable trdRegresar_up=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/Back.png")));
        TextureRegionDrawable trdRegresar_down=new TextureRegionDrawable(new TextureRegion(new Texture("Botones/BackPres.png")));
        TextureRegionDrawable trdJhony =new TextureRegionDrawable(new TextureRegion(new Texture("Jhony_fondo.png")));
        TextureRegionDrawable trdOrlando=new TextureRegionDrawable(new TextureRegion(new Texture("Orlando_fondo.png")));
        TextureRegionDrawable trdJiovany=new TextureRegionDrawable(new TextureRegion(new Texture("Jiovany_fondo.png")));

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
                mainGame.setScreen(new PlayScreen(mainGame));
            }
        });

        btnOrlando.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new PlayScreen(mainGame));
            }
        });

        btnJiovany.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new PlayScreen(mainGame));
            }
        });

        btnReg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mainGame.setScreen(new PlayScreen(mainGame));
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
        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        batch.draw(fondo,0,0);
        batch.end();
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
        escenaDificultad.dispose();
    }
}
