package mx.itexm.naughty.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Controller extends Touchpad {
    private static Skin touchpadSkin;

    public Controller(float deadzoneRadius) {
        super(deadzoneRadius, Controller.getTouchPadStyle());
        setBounds(16, 16, 128, 128);
    }

    private static TouchpadStyle getTouchPadStyle(){
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("Botones/padBack.png"));
        touchpadSkin.add("touchKnob", new Texture("Botones/padKnob.png"));

        TouchpadStyle touchpadStyle = new TouchpadStyle();
        touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");
        return touchpadStyle;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
