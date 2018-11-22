package mx.itesm.naughty.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import mx.itesm.naughty.MainGame;
import mx.itesm.naughty.Screens.PlayScreen;
import mx.itesm.naughty.Sprites.Items.Bala;

import static mx.itesm.naughty.MainGame.PPM;

public class Player extends Sprite {
    public enum State { UP, STANDINGUD, STANDINGLR, RUNNINGLR, PUSHINGUD, PUSHINGLR, KATANA, BATE, PISTOLA, DEAD, WIN}

    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;

    private TextureRegion playerStandUD;
    private TextureRegion playerStandRL;
    private TextureRegion playerStandKatanaUD;
    private TextureRegion playerStandKatanaLR;
    private TextureRegion playerStandBateUD;
    private TextureRegion playerStandBateLR;
    private TextureRegion playerStandPistolaLR;
    private TextureRegion playerStandPistolaUD;

    private Fixture fixture;
    private Animation playerRunRL;
    private Animation playerRunUD;
    private Animation playerRunRLKatana;
    private Animation playerRunUDKatana;
    private Animation playerRunRLBate;
    private Animation playerRunUDBate;
    private Animation playerRunUDPistola;
    private Animation playerRunRLPistola;

    private Animation playerPushUD;
    private Animation playerPushRL;
    private Animation playerPushKatanaUD;
    private Animation playerPushKatanaRL;
    private Animation playerPushBateUD;
    private Animation playerPushBateRL;
    private Animation playerPushPistolaRL;
    private Animation playerPushPistolaUD;

    private Animation playerChangingKatana;
    private Animation playerChangingBate;
    private Animation playerChangingPistola;

    private Animation playerDead;


    private float stateTimer;
    private boolean runningRight;
    private boolean runningUp;
    private boolean isRunningRL;

    public boolean isRunningRL() {
        return isRunningRL;
    }

    public boolean isRunningUD() {
        return isRunningUD;
    }

    private boolean isRunningUD;

    private boolean pushing;

    private boolean playerIsWithKatana;
    private boolean playerIsWithBate;


    private boolean playerIsWithPistola;
    private boolean runPlayerKatanaAnimation;
    private boolean runPlayerBateAnimation;
    private boolean runPlayerPistolaAnimation;

    private boolean playerIsDead;
    private boolean playerIsWin;

    private Array<Bala> balas;
    private PlayScreen screen;


    public Player(PlayScreen screen){
        this.world = screen.getWorld();
        this.screen = screen;
        currentState = State.STANDINGLR;
        previousState = State.STANDINGLR;
        stateTimer = 0;
        runningRight = true;
        runningUp = true;

        createAnimations();
    }

    public void createAnimations(){

        Array<TextureRegion> frames = new Array<TextureRegion>();
        if(screen.getJhony() == "Jhony"){
            //Animation dead
            for(int i = 0; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_golpeado"), i * 89, 0, 90, 90));
            }
            playerDead = new Animation(0.2f, frames);
            frames.clear();

            //Animation walk right and down
            for(int i = 0; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkRight"), i * 89, 0, 90, 90));
            }
            playerRunRL = new Animation(0.1f, frames);
            frames.clear();

            // Animation katana right and down
            for(int i = 0; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_caminado_katana_lado"), i * 90, 0, 90, 90));
            }
            playerRunRLKatana = new Animation(0.1f, frames);
            frames.clear();

            // Animation bate right and down
            for(int i = 0; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_caminado_bate_lado"), i * 90, 0, 90, 90));
            }
            playerRunRLBate = new Animation(0.1f, frames);
            frames.clear();

            // Animation pistola right and down
            for(int i = 0; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_caminando_pistola_lado"), i * 90, 0, 90, 90));
            }
            playerRunRLPistola = new Animation(0.1f, frames);
            frames.clear();

            //Animation walk up and down
            for(int i = 1; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), i * 90, 4, 90, 90));
            }
            playerRunUD = new Animation(0.1f, frames);
            frames.clear();

            //Animation walk up and down katana
            for(int i = 1; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), i * 89, 0, 90, 90));
            }
            playerRunUDKatana = new Animation(0.1f, frames);
            frames.clear();

            //Animation walk up and down bate
            for(int i = 1; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_bate"), i * 89, 0, 90, 90));
            }
            playerRunUDBate = new Animation(0.1f, frames);
            frames.clear();

            //Animation walk up and down pistola
            for(int i = 1; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_caminando_pistola"), i * 89, 0, 90, 90));
            }
            playerRunUDPistola = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingUD
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_golpesUpDown"), i * 90, 4, 90, 90));
            }
            playerPushUD = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingLR
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_golpes_lado"), i * 90, 4, 90, 90));
            }
            playerPushRL = new Animation(0.1f, frames);
            frames.clear();


            //Animation pushingUD katana
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_katanaAttackUpDown"), i * 90, 0, 90, 90));
            }
            playerPushKatanaUD = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingUD bate
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_bateAttackUpDown"), i * 90, 0, 90, 90));
            }
            playerPushBateUD = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingUD bate
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_ataque_pistola"), i * 90, 0, 90, 90));
            }
            playerPushPistolaUD = new Animation(0.1f, frames);
            frames.clear();


            //Animation pushingLR katana
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_ataque_katana_lado"), i * 90, 0, 90, 90));
            }
            playerPushKatanaRL = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingLR bate
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_ataque_bate_lado"), i * 90, 0, 90, 90));
            }
            playerPushBateRL = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingLR bate
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_ataque_pistola_lado"), i * 90, 0, 90, 90));
            }
            playerPushPistolaRL = new Animation(0.1f, frames);
            frames.clear();

            //Animation changing katana
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
            playerChangingKatana = new Animation(0.2f, frames);
            frames.clear();

            //Animation changing bate
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_bate"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_bate"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
            playerChangingBate = new Animation(0.2f, frames);
            frames.clear();

            //Animation changing bate
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_caminando_pistola"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jhony_caminando_pistola"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Jhony_walkUpDown"), 0, 0, 90, 90));
            playerChangingPistola = new Animation(0.2f, frames);
            frames.clear();

            playerStandRL = new TextureRegion(screen.getAtlas().findRegion("jhony_standing_lado"), 0,5,90,90);
            playerStandUD = new TextureRegion(screen.getAtlas().findRegion("Jhony_standingUpDown"), 0,5,90,90);

            playerStandKatanaUD = new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_katana"), 0,0,90,90);
            playerStandKatanaLR = new TextureRegion(screen.getAtlas().findRegion("jhony_caminado_katana_lado"), 0,0,90,90);

            playerStandBateLR = new TextureRegion(screen.getAtlas().findRegion("jhony_caminado_bate_lado"), 0,0,90,90);
            playerStandBateUD = new TextureRegion(screen.getAtlas().findRegion("Jhony_walk_bate"), 0,0,90,90);

            playerStandPistolaUD = new TextureRegion(screen.getAtlas().findRegion("jhony_caminando_pistola"), 0,0,90,90);
            playerStandPistolaLR = new TextureRegion(screen.getAtlas().findRegion("jhony_caminando_pistola_lado"), 0,0,90,90);
        } else if(screen.getJhony() == "Orlando") {
            //Animation dead
            for(int i = 0; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_dano"), i * 89, 0, 90, 90));
            }
            playerDead = new Animation(0.2f, frames);
            frames.clear();

            //Animation walk right and down
            for(int i = 0; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_lado"), i * 89, 0, 90, 90));
            }
            playerRunRL = new Animation(0.1f, frames);
            frames.clear();

            // Animation katana right and down
            for(int i = 0; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_katana_lado"), i * 90, 0, 90, 90));
            }
            playerRunRLKatana = new Animation(0.1f, frames);
            frames.clear();

            // Animation bate right and down
            for(int i = 0; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_bate_lado"), i * 90, 0, 90, 90));
            }
            playerRunRLBate = new Animation(0.1f, frames);
            frames.clear();

            // Animation pistola right and down
            for(int i = 0; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_pistola_lado"), i * 90, 0, 90, 90));
            }
            playerRunRLPistola = new Animation(0.1f, frames);
            frames.clear();

            //Animation walk up and down
            for(int i = 1; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_frente"), i * 90, 0, 90, 90));
            }
            playerRunUD = new Animation(0.1f, frames);
            frames.clear();

            //Animation walk up and down katana
            for(int i = 1; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_katana"), i * 89, 0, 90, 90));
            }
            playerRunUDKatana = new Animation(0.1f, frames);
            frames.clear();

            //Animation walk up and down bate
            for(int i = 1; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_bate"), i * 90, 0, 90, 90));
            }
            playerRunUDBate = new Animation(0.1f, frames);
            frames.clear();

            //Animation walk up and down pistola
            for(int i = 1; i < 5; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_pistola"), i * 89, 0, 90, 90));
            }
            playerRunUDPistola = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingUD
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_golpes"), i * 90, 4, 90, 90));
            }
            playerPushUD = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingLR
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_golpes_lado"), i * 90, 4, 90, 90));
            }
            playerPushRL = new Animation(0.1f, frames);
            frames.clear();


            //Animation pushingUD katana
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_ataque_katana"), i * 90, 0, 90, 90));
            }
            playerPushKatanaUD = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingUD bate
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_ataque_bate"), i * 90, 0, 90, 90));
            }
            playerPushBateUD = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingUD bate
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_ataque_pistola"), i * 90, 0, 90, 90));
            }
            playerPushPistolaUD = new Animation(0.1f, frames);
            frames.clear();


            //Animation pushingLR katana
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_ataque_katana_lado"), i * 90, 0, 90, 90));
            }
            playerPushKatanaRL = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingLR bate
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_ataque_bate_lado"), i * 90, 0, 90, 90));
            }
            playerPushBateRL = new Animation(0.1f, frames);
            frames.clear();

            //Animation pushingLR bate
            for(int i = 0; i < 3; i++){
                frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_ataque_pistola_lado"), i * 90, 0, 90, 90));
            }
            playerPushPistolaRL = new Animation(0.1f, frames);
            frames.clear();

            //Animation changing katana
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_katana"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_frente"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_katana"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_frente"), 0, 0, 90, 90));
            playerChangingKatana = new Animation(0.2f, frames);
            frames.clear();

            //Animation changing bate
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_bate"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_frente"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_bate"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_frente"), 0, 0, 90, 90));
            playerChangingBate = new Animation(0.2f, frames);
            frames.clear();

            //Animation changing bate
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_pistola"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_frente"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_pistola"), 0, 0, 90, 90));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_frente"), 0, 0, 90, 90));
            playerChangingPistola = new Animation(0.2f, frames);
            frames.clear();

            playerStandRL = new TextureRegion(screen.getAtlas().findRegion("orlando_standing_lado"), 0,5,90,90);
            playerStandUD = new TextureRegion(screen.getAtlas().findRegion("orlando_standing"), 0,5,90,90);

            playerStandKatanaUD = new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_katana"), 0,0,90,90);
            playerStandKatanaLR = new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_katana_lado"), 0,0,90,90);

            playerStandBateLR = new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_bate_lado"), 0,0,90,90);
            playerStandBateUD = new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_bate"), 0,0,90,90);

            playerStandPistolaUD = new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_pistola"), 0,0,90,90);
            playerStandPistolaLR = new TextureRegion(screen.getAtlas().findRegion("orlando_caminando_pistola_lado"), 0,0,90,90);
        }



        definePlayer();
        setBounds(0,0,90 / PPM,90 / PPM);
        setRegion(playerStandRL);

        balas = new Array<Bala>();
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        // Actualiza las balas y las elimina
        for(Bala bala: balas){
            bala.update(dt);
            if(bala.isDestroyed()){
                balas.removeValue(bala, true);
            }
        }

        if(currentState == State.PUSHINGUD){
            if(runningUp){
                if(playerPushUD.getKeyFrameIndex(stateTimer) == 0){
                    redefineColision(new Vector2(-15/ PPM, 30 / PPM), new Vector2(15/ PPM, 30 / PPM));
                }else if(playerPushUD.getKeyFrameIndex(stateTimer) == 1){

                    b2body.destroyFixture(b2body.getFixtureList().get(1));
                    redefineColision(new Vector2(-15/ PPM, 50 / PPM), new Vector2(15/ PPM, 50 / PPM));
                }else if(playerPushUD.getKeyFrameIndex(stateTimer) == 2){
                    for(int i = 1; i < b2body.getFixtureList().size; i++)
                        b2body.destroyFixture(b2body.getFixtureList().get(i));
                }
            } else if(!runningUp){
                if(playerPushUD.getKeyFrameIndex(stateTimer) == 0){
                    redefineColision(new Vector2(-15/ PPM, -30 / PPM), new Vector2(15/ PPM, -30 / PPM));
                }else if(playerPushUD.getKeyFrameIndex(stateTimer) == 1){

                    b2body.destroyFixture(b2body.getFixtureList().get(1));
                    redefineColision(new Vector2(-15/ PPM, -50 / PPM), new Vector2(15/ PPM, -50 / PPM));
                }else if(playerPushUD.getKeyFrameIndex(stateTimer) == 2){
                    for(int i = 1; i < b2body.getFixtureList().size; i++)
                        b2body.destroyFixture(b2body.getFixtureList().get(i));
                }
            }
        } else if(currentState == State.PUSHINGLR){
            if(runningRight){
                if(playerPushRL.getKeyFrameIndex(stateTimer) == 0){
                    redefineColision(new Vector2(30/ PPM, 15 / PPM), new Vector2(30/ PPM, -15 / PPM));
                }else if(playerPushRL.getKeyFrameIndex(stateTimer) == 1){
                    b2body.destroyFixture(b2body.getFixtureList().get(1));
                    redefineColision(new Vector2(50/ PPM, 15 / PPM), new Vector2(50/ PPM, -15 / PPM));
                } if(playerPushRL.getKeyFrameIndex(stateTimer) == 2){
                    for(int i = 1; i < b2body.getFixtureList().size; i++)
                        b2body.destroyFixture(b2body.getFixtureList().get(i));
                }
            } else if(!runningRight){
                if(playerPushRL.getKeyFrameIndex(stateTimer) == 0){
                    redefineColision(new Vector2(-30/ PPM, 15 / PPM), new Vector2(-30/ PPM, -15 / PPM));
                }else if(playerPushRL.getKeyFrameIndex(stateTimer) == 1){

                    b2body.destroyFixture(b2body.getFixtureList().get(1));
                    redefineColision(new Vector2(-50/ PPM, 15 / PPM), new Vector2(-50/ PPM, -15 / PPM));
                }if(playerPushRL.getKeyFrameIndex(stateTimer) == 2){
                    for(int i = 1; i < b2body.getFixtureList().size; i++)
                        b2body.destroyFixture(b2body.getFixtureList().get(i));
                }
            }
        }

    }

    public boolean isDead(){
        return playerIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    private TextureRegion getFrame(float dt) {
        TextureRegion region;
        switch (currentState){
            case DEAD:

                region = (TextureRegion) playerDead.getKeyFrame(stateTimer);
                break;
            case WIN:
                region = playerStandUD;
                break;
            case KATANA:
                region = (TextureRegion) playerChangingKatana.getKeyFrame(stateTimer);
                if(playerChangingKatana.isAnimationFinished(stateTimer))
                    runPlayerKatanaAnimation = false;
                break;
            case BATE:
                region = (TextureRegion) playerChangingBate.getKeyFrame(stateTimer);
                if(playerChangingBate.isAnimationFinished(stateTimer))
                    runPlayerBateAnimation = false;
                break;
            case PISTOLA:
                region = (TextureRegion) playerChangingPistola.getKeyFrame(stateTimer);
                if(playerChangingPistola.isAnimationFinished(stateTimer))
                    runPlayerPistolaAnimation = false;
                break;
            case RUNNINGLR:
                if(playerIsWithKatana){
                    region = (TextureRegion) playerRunRLKatana.getKeyFrame(stateTimer, true);
                } else if(playerIsWithBate){
                    region = (TextureRegion) playerRunRLBate.getKeyFrame(stateTimer, true);
                } else if(playerIsWithPistola){
                    region = (TextureRegion) playerRunRLPistola.getKeyFrame(stateTimer, true);
                } else {
                    region = (TextureRegion) playerRunRL.getKeyFrame(stateTimer, true);
                }
                break;
            case UP:
                if(playerIsWithKatana){
                    region = (TextureRegion) playerRunUDKatana.getKeyFrame(stateTimer, true);
                } else if(playerIsWithBate){
                    region = (TextureRegion) playerRunUDBate.getKeyFrame(stateTimer, true);
                } else if(playerIsWithPistola){
                    region = (TextureRegion) playerRunUDPistola.getKeyFrame(stateTimer, true);
                } else {
                    region = (TextureRegion) playerRunUD.getKeyFrame(stateTimer, true);
                }
                break;
            case PUSHINGUD:
                if(playerIsWithKatana){
                    region = (TextureRegion) playerPushKatanaUD.getKeyFrame(stateTimer, true);
                } else if(playerIsWithBate){
                    region = (TextureRegion) playerPushBateUD.getKeyFrame(stateTimer, true);
                }else if(playerIsWithPistola){
                    region = (TextureRegion) playerPushPistolaUD.getKeyFrame(stateTimer, true);
                }else {
                    region = (TextureRegion) playerPushUD.getKeyFrame(stateTimer, true);
                }
                break;
            case PUSHINGLR:
                if(playerIsWithKatana){
                    region = (TextureRegion) playerPushKatanaRL.getKeyFrame(stateTimer, true);
                } else if(playerIsWithBate){
                    region = (TextureRegion) playerPushBateRL.getKeyFrame(stateTimer, true);
                }else if(playerIsWithPistola){
                    region = (TextureRegion) playerPushPistolaRL.getKeyFrame(stateTimer, true);
                }else {
                    region = (TextureRegion) playerPushRL.getKeyFrame(stateTimer, true);
                }
                break;
            case STANDINGLR:
                if(playerIsWithKatana){
                    region = playerStandKatanaLR;
                } else if(playerIsWithBate){
                    region = playerStandBateLR;
                }else if(playerIsWithPistola){
                    region = playerStandPistolaLR;
                }else {
                    region = playerStandRL;
                }
                break;
            default:
                if(playerIsWithKatana){
                    region = playerStandKatanaUD;
                } else if(playerIsWithBate){
                    region = playerStandBateUD;
                }else if(playerIsWithPistola){
                    region = playerStandPistolaUD;
                }else {
                    region = playerStandUD;
                }
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        else if((b2body.getLinearVelocity().y > 0 || runningUp) && region.isFlipY()){
            region.flip(false, true);
            runningUp = true;
        }
        else if((b2body.getLinearVelocity().y < 0 || !runningUp) && !region.isFlipY()){
            region.flip(false, true);
            runningUp = false;
        }
        currentState = getState();
        stateTimer = currentState == previousState ? stateTimer+dt: 0;
        previousState = currentState;
        return region;

    }

    public void changeDefault(){
        playerIsWithBate = false;
        playerIsWithPistola = false;
        playerIsWithKatana = false;
    }

    public void changeKatana(){
        runPlayerKatanaAnimation = true;
        playerIsWithBate = false;
        playerIsWithPistola = false;
        playerIsWithKatana = true;

        setBounds(getX(), getY(), getWidth(), getHeight());
        //MainScreen.manager.get("Musica/chest.mp3", Music.class).play();
    }

    public void changeBate(){
        runPlayerBateAnimation = true;
        playerIsWithKatana = false;
        playerIsWithPistola = false;
        playerIsWithBate = true;

        setBounds(getX(), getY(), getWidth(), getHeight());
        //MainScreen.manager.get("Musica/chest.mp3", Music.class).play();
    }

    public void changePistola(){
        runPlayerPistolaAnimation = true;
        playerIsWithKatana = false;
        playerIsWithBate = false;
        playerIsWithPistola = true;
        setBounds(getX(), getY(), getWidth(), getHeight());
        //MainScreen.manager.get("Musica/chest.mp3", Music.class).play();
    }

    private State getState() {
        if(playerIsDead) return State.DEAD;
        else if(playerIsWin) return State.WIN;
        else if(runPlayerKatanaAnimation) return State.KATANA;
        else if(runPlayerPistolaAnimation) return State.PISTOLA;
        else if(runPlayerBateAnimation) return State.BATE;
        else if(b2body.getLinearVelocity().y != 0) {
            isRunningUD = true;
            isRunningRL = false;
            return State.UP;
        }
        else if(b2body.getLinearVelocity().x != 0) {
            isRunningRL = true;
            isRunningUD = false;
            return State.RUNNINGLR;
        }
        else if(pushing && isRunningUD) return State.PUSHINGUD;
        else if(pushing && isRunningRL) return State.PUSHINGLR;
        else if(isRunningRL && !isRunningUD) return State.STANDINGLR;
        else return State.STANDINGUD;
    }

    public void setPushing(boolean pushing) {
        this.pushing = pushing;
    }

    private void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(170 / PPM,170 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / PPM);
        fdef.filter.categoryBits = MainGame.PLAYER_BIT;
        fdef.filter.maskBits = MainGame.GROUND_BIT
                | MainGame.ARMA_BIT
                | MainGame.COFRE_BIT
                | MainGame.ENEMY_BIT
                | MainGame.OBJECT_BIT
                | MainGame.ITEM_BIT
                | MainGame.DOOR_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void redefineColision(Vector2 vector1, Vector2 vector2){
        FixtureDef colisionador = new FixtureDef();
        EdgeShape up = new EdgeShape();
        up.set(vector1, vector2);
        colisionador.filter.categoryBits = MainGame.PLAYER_HEAD_BIT;
        colisionador.shape = up;
        colisionador.isSensor = true;
        fixture = b2body.createFixture(colisionador);
        fixture.setUserData(this);
    }

    public void hit(){
        playerIsDead = true;
        Filter filter = new Filter();
        filter.maskBits = MainGame.NOTHING_BIT;
        for(Fixture fixture: b2body.getFixtureList())
            fixture.setFilterData(filter);
        //b2body.setLinearVelocity(0, 0);
    }

    public void win(){
        playerIsWin = true;
        Filter filter = new Filter();
        filter.maskBits = MainGame.NOTHING_BIT;
        for(Fixture fixture: b2body.getFixtureList())
            fixture.setFilterData(filter);
    }

    public void fire(){
        balas.add(new Bala(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true: false));
    }

    public boolean isPlayerIsWithPistola() {
        return playerIsWithPistola;
    }

    public void draw(Batch batch){
        super.draw(batch);
        for(Bala bala: balas){
            bala.draw(batch);
        }
    }

}
