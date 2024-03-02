package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pokepong.App;

public class Loading extends Page {

    public static ImageView loading_back = new ImageView();
    public static ImageView loading_bar = new ImageView();
    public static ImageView loading_cadre = new ImageView();
    public static ImageView pressAnyButton = new ImageView();

    // + on l'augmente, + la vitesse de chargement augmente
    public static int speed = 5;

    public Loading(Pane root, Scene scene, GameView gameview, double zoom) {
        super(root, scene,gameview, zoom);

        Node[] objects = 
        {
            loading_back,loading_bar,loading_cadre,
            pressAnyButton
        };
        this.allObjects = objects;

        // On ajoute tous les objets dans le root
        addChildrens();
    }

    @Override
    public void createPage() {

        scene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                default: break;
            }
        });
        scene.setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
                default: break;
            }
        });

        double zoomLoading = 0.4;

        Image loading_cadre_i = new Image(getClass().getResourceAsStream("image/loading/loading.png"));
        Image loading_bar_i = new Image(getClass().getResourceAsStream("image/loading/loading_bar.png"));
        Image loading_back_i = new Image(getClass().getResourceAsStream("image/loading/loading_back.png"));

        loading_cadre.setImage(loading_cadre_i);
        loading_cadre.setFitWidth(loading_cadre_i.getWidth()*zoomLoading);
        loading_cadre.setFitHeight(loading_cadre_i.getHeight()*zoomLoading);
        loading_cadre.setLayoutX(screen.getWidth()/2 - loading_cadre.getFitWidth()/2);
        loading_cadre.setLayoutY(screen.getHeight()*6/7 - loading_cadre.getFitHeight()/2);

        loading_back.setImage(loading_back_i);
        loading_back.setFitWidth(loading_back_i.getWidth()*zoomLoading);
        loading_back.setFitHeight(loading_back_i.getHeight()*zoomLoading);
        loading_back.setLayoutX(loading_cadre.getLayoutX() + 40*zoomLoading);
        loading_back.setLayoutY(screen.getHeight()*6/7 - loading_back.getFitHeight()/2);

        // Création de la jauge qui va remplir la barre

        loading_bar.setImage(loading_bar_i);
        loading_bar.setFitWidth(1*zoomLoading);
        loading_bar.setFitHeight(loading_bar_i.getHeight()*zoomLoading);
        loading_bar.setLayoutX(loading_cadre.getLayoutX() + 40*zoomLoading);
        loading_bar.setLayoutY(screen.getHeight()*6/7 - loading_bar.getFitHeight()/2);

        AnimationTimer loading = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(loading_bar.getFitWidth() >= loading_bar_i.getWidth()*zoomLoading){
                    this.stop();
                    pressAnyButton();
                }
                else{
                    loading_bar.setFitWidth(loading_bar.getFitWidth()+speed);
                }
            }
        };
        loading.start();

        Image pressAnyButton_i = new Image(getClass().getResourceAsStream("image/text/"+App.suff+"/pressAnyButton.png"));

        pressAnyButton.setImage(pressAnyButton_i);
        pressAnyButton.setFitWidth(pressAnyButton_i.getWidth()*zoomText);
        pressAnyButton.setFitHeight(pressAnyButton_i.getHeight()*zoomText);
        pressAnyButton.setLayoutX(screen.getWidth()/2 - pressAnyButton.getFitWidth()/2);
        pressAnyButton.setLayoutY(screen.getHeight()*6/7 - pressAnyButton.getFitHeight()/2);
        pressAnyButton.setVisible(false);

    }

    public void pressAnyButton(){

        hide();
        pressAnyButton.setVisible(true);

        scene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                default: 
                    SoundController.click();
                    App.switchPong();
                    break;
            }
        });
    }

    public void hide(){
        for (Node node : allObjects) {
            node.setVisible(false);
        }
    }

    @Override
    public void show(){
        for (Node node : allObjects) {
            node.setVisible(true);
        }
        // On ne veut pas montrer cet objet au début
        pressAnyButton.setVisible(false);
    }
    
}
