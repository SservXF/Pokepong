package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pokepong.App;

public class Luncher extends Page {

    public static ImageView pokeball;
    public static ImageView pokepong;

    public Luncher(Pane root, Scene scene, GameView gameView, double zoom) {
        super(root, scene,gameView, zoom);

        pokeball = new ImageView(new Image(getClass().getResourceAsStream("image/pokeball.png")));
        pokepong = new ImageView(new Image(getClass().getResourceAsStream("image/pokepong.png")));
    

        Node[] objects = 
        {
            pokeball,pokepong
        };
        this.allObjects = objects;

        addChildrens();
    }

    @Override
    public void createPage() {

        // Création de la page
        root.setId("luncher");
        scene.getStylesheets().addAll(this.getClass().getResource("css/fond.css").toExternalForm());

        pokeball.setFitWidth(860/3);
        pokeball.setFitHeight(900/3);

        pokeball.setLayoutX(-pokeball.getFitWidth());
        pokeball.setLayoutY(screen.getHeight()/2 - pokeball.getFitHeight()/2);

        TranslateTransition t_pokeball = new TranslateTransition();
        t_pokeball.setNode(pokeball);
        t_pokeball.setDuration(Duration.seconds(1));
        t_pokeball.setByX(screen.getWidth()/2 + pokeball.getFitWidth()/2);


        pokepong.setFitWidth(1920/3);
        pokepong.setFitHeight(1080/3);

        pokepong.setLayoutX(screen.getWidth());
        pokepong.setLayoutY(screen.getHeight()/2 - pokepong.getFitHeight()/2);

        TranslateTransition t_pokepong = new TranslateTransition();
        t_pokepong.setNode(pokepong);
        t_pokepong.setDuration(Duration.seconds(1));
        t_pokepong.setByX(-(screen.getWidth()/2 + pokepong.getFitWidth()/2));

        // Affichage de la scene
        App.primaryStage.setScene(scene);
        App.primaryStage.show();
        t_pokeball.play();
        t_pokepong.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), (ActionEvent event) -> {
            App.switchMenu();
        }));
        timeline.play();
    }
}
