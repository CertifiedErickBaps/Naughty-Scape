package mx.itesm.naughty.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Button {
    private ImageButton imageButton;
    private float x;
    private float y;
    private String btn, btnPress;


    public Button(String btn, String btnPress, float x, float y){
        this.x = x;
        this.y = y;
        this.btn = btn;
        this.btnPress = btnPress;

        createBoton();
    }

    private void createBoton(){
        Texture textBtnHelp = new Texture(btn);
        TextureRegionDrawable trdHelp = new TextureRegionDrawable(new TextureRegion(textBtnHelp));

        Texture textBtnOprimidoHelp = new Texture(btnPress);

        TextureRegionDrawable trdOpHelp = new TextureRegionDrawable(new TextureRegion(textBtnOprimidoHelp));
        imageButton = new ImageButton(trdHelp, trdOpHelp);
        imageButton.setPosition(this.x, this.y);
    }

    public ImageButton getImageButton() {
        return imageButton;
    }

    public float getWidth(){
        return imageButton.getWidth();
    }

    public float getHeight(){
        return imageButton.getHeight();
    }
}
