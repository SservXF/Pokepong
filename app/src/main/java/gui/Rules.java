package gui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pokepong.App;

public class Rules extends Page {

    // Objets rules

    public static ImageView howToPlay = new ImageView();

    public static ImageView j1 = new ImageView();

    public static ImageView key_up_j1 = new ImageView();
    public static ImageView key_down_j1 = new ImageView();
    public static ImageView key_power_j1 = new ImageView();
    public static ImageView key_pause_j1 = new ImageView();

    public static ImageView j2 = new ImageView();

    public static ImageView key_up_j2 = new ImageView();
    public static ImageView key_down_j2 = new ImageView();
    public static ImageView key_power_j2 = new ImageView();
    public static ImageView key_pause_j2 = new ImageView();

    public static ImageView goUp = new ImageView();
    public static ImageView goDown = new ImageView();
    public static ImageView power = new ImageView();
    public static ImageView pause = new ImageView();

    public Rules(Pane root, Scene scene, GameView gameview, double zoom) {
        super(root, scene, gameview, zoom);  
        Node[] objects = 
        {
            howToPlay,
            j1,j2,
            key_up_j1,key_down_j1,key_power_j1,key_pause_j1,
            key_up_j2,key_down_j2,key_power_j2,key_pause_j2,
            goUp,goDown,power,
            pause
        };
        this.allObjects = objects;

        createPage();

        // On ajoute tous les objets dans le root
        addChildrens();
    }

    public void createPage(){

        root.setId("rules");
        scene.getStylesheets().addAll(this.getClass().getResource("css/fond.css").toExternalForm());

        double ecartY = 100 * zoomText;

        Image howToPlay_i = new Image(getClass().getResourceAsStream("image/text/"+App.suff+"/how_to_play.png"));
        Image goDown_i = new Image(getClass().getResourceAsStream("image/text/"+App.suff+"/go_down.png"));
        Image goUp_i = new Image(getClass().getResourceAsStream("image/text/"+App.suff+"/go_up.png"));
        Image activePower_i = new Image(getClass().getResourceAsStream("image/text/"+App.suff+"/power.png"));
        Image pause_i = new Image(getClass().getResourceAsStream("image/text/pause.png"));

        howToPlay.setImage(howToPlay_i);
        howToPlay.setFitWidth(howToPlay_i.getWidth()*zoomText);
        howToPlay.setFitHeight(howToPlay_i.getHeight()*zoomText);
        howToPlay.setLayoutX(screen.getWidth()/2 - howToPlay.getFitWidth()/2);
        howToPlay.setLayoutY(screen.getHeight()/5 - howToPlay.getFitHeight()/2);

        goDown.setImage(goDown_i);
        goDown.setFitWidth(goDown_i.getWidth()*zoomText);
        goDown.setFitHeight(goDown_i.getHeight()*zoomText);
        goDown.setLayoutX(screen.getWidth()/2 - goDown.getFitWidth()/2);
        goDown.setLayoutY(screen.getHeight()/2 - goDown.getFitHeight()/2);

        goUp.setImage(goUp_i);
        goUp.setFitWidth(goUp_i.getWidth()*zoomText);
        goUp.setFitHeight(goUp_i.getHeight()*zoomText);
        goUp.setLayoutX(screen.getWidth()/2 - goUp.getFitWidth()/2);
        goUp.setLayoutY(goDown.getLayoutY() - goDown.getFitHeight() - ecartY);

        power.setImage(activePower_i);
        power.setFitWidth(activePower_i.getWidth()*zoomText);
        power.setFitHeight(activePower_i.getHeight()*zoomText);
        power.setLayoutX(screen.getWidth()/2 - power.getFitWidth()/2);
        power.setLayoutY(goDown.getLayoutY() + goDown.getFitHeight() + ecartY);

        pause.setImage(pause_i);
        pause.setFitWidth(pause_i.getWidth()*zoomText);
        pause.setFitHeight(pause_i.getHeight()*zoomText);
        pause.setLayoutX(screen.getWidth()/2 - pause.getFitWidth()/2);
        pause.setLayoutY(power.getLayoutY() + power.getFitHeight() + ecartY);


        ////////////////////////////

        Image j1_i = new Image(getClass().getResourceAsStream("image/text/"+App.suff+"/j1.png"));
        Image key_z_i = new Image(getClass().getResourceAsStream("image/key/z.png"));
        Image key_s_i = new Image(getClass().getResourceAsStream("image/key/s.png"));
        Image key_space_i = new Image(getClass().getResourceAsStream("image/key/space.png"));
        Image key_pause_i = new Image(getClass().getResourceAsStream("image/key/esc.png"));

        double zoomKey = (goDown_i.getHeight()/key_s_i.getHeight()) * zoomText;

        // Images touches J1

        key_down_j1.setImage(key_s_i);
        key_down_j1.setFitWidth(key_s_i.getWidth()*zoomKey);
        key_down_j1.setFitHeight(key_s_i.getHeight()*zoomKey);
        key_down_j1.setLayoutY(goDown.getLayoutY());
        key_down_j1.setLayoutX(screen.getWidth()/4 - key_down_j1.getFitWidth()/2);

        key_up_j1.setImage(key_z_i);
        key_up_j1.setFitWidth(key_z_i.getWidth()*zoomKey);
        key_up_j1.setFitHeight(key_z_i.getHeight()*zoomKey);
        key_up_j1.setLayoutY(goUp.getLayoutY());
        key_up_j1.setLayoutX(screen.getWidth()/4 - key_up_j1.getFitWidth()/2);

        key_power_j1.setImage(key_space_i);
        key_power_j1.setFitWidth(key_space_i.getWidth()*zoomKey);
        key_power_j1.setFitHeight(key_space_i.getHeight()*zoomKey);
        key_power_j1.setLayoutY(power.getLayoutY());
        key_power_j1.setLayoutX(screen.getWidth()/4 - key_power_j1.getFitWidth()/2);

        key_pause_j1.setImage(key_pause_i);
        key_pause_j1.setFitWidth(key_pause_i.getWidth()*zoomKey);
        key_pause_j1.setFitHeight(key_pause_i.getHeight()*zoomKey);
        key_pause_j1.setLayoutY(pause.getLayoutY());
        key_pause_j1.setLayoutX(screen.getWidth()/4 - key_pause_j1.getFitWidth()/2);

        j1.setImage(j1_i);
        j1.setFitWidth(j1_i.getWidth()*zoomText);
        j1.setFitHeight(j1_i.getHeight()*zoomText);
        j1.setLayoutY(goUp.getLayoutY()-goUp.getFitHeight() - ecartY);
        j1.setLayoutX(screen.getWidth()/4 - j1.getFitWidth()/2);



        // Images touches J2

        Image j2_i = new Image(getClass().getResourceAsStream("image/text/"+App.suff+"/j2.png"));
        Image key_up_i = new Image(getClass().getResourceAsStream("image/key/up.png"));
        Image key_down_i = new Image(getClass().getResourceAsStream("image/key/down.png"));
        Image key_p_i = new Image(getClass().getResourceAsStream("image/key/p.png"));

        key_down_j2.setImage(key_down_i);
        key_down_j2.setFitWidth(key_down_i.getWidth()*zoomKey);
        key_down_j2.setFitHeight(key_down_i.getHeight()*zoomKey);
        key_down_j2.setLayoutY(goDown.getLayoutY());
        key_down_j2.setLayoutX(screen.getWidth()*3/4 - key_down_j2.getFitWidth()/2);

        key_up_j2.setImage(key_up_i);
        key_up_j2.setFitWidth(key_up_i.getWidth()*zoomKey);
        key_up_j2.setFitHeight(key_up_i.getHeight()*zoomKey);
        key_up_j2.setLayoutY(goUp.getLayoutY());
        key_up_j2.setLayoutX(screen.getWidth()*3/4 - key_up_j2.getFitWidth()/2);

        key_power_j2.setImage(key_p_i);
        key_power_j2.setFitWidth(key_p_i.getWidth()*zoomKey);
        key_power_j2.setFitHeight(key_p_i.getHeight()*zoomKey);
        key_power_j2.setLayoutY(power.getLayoutY());
        key_power_j2.setLayoutX(screen.getWidth()*3/4 - key_power_j2.getFitWidth()/2);

        key_pause_j2.setImage(key_pause_i);
        key_pause_j2.setFitWidth(key_pause_i.getWidth()*zoomKey);
        key_pause_j2.setFitHeight(key_pause_i.getHeight()*zoomKey);
        key_pause_j2.setLayoutY(pause.getLayoutY());
        key_pause_j2.setLayoutX(screen.getWidth()*3/4 - key_pause_j2.getFitWidth()/2);

        j2.setImage(j2_i);
        j2.setFitWidth(j2_i.getWidth()*zoomText);
        j2.setFitHeight(j2_i.getHeight()*zoomText);
        j2.setLayoutY(goUp.getLayoutY()-goUp.getFitHeight() - ecartY);
        j2.setLayoutX(screen.getWidth()*3/4 - j2.getFitWidth()/2);
    }

    @Override
    public void lunch(){
        createPage();
        show();
        App.loading.lunch();
    }
}
