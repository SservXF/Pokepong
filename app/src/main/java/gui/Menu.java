package gui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Court;
import pokepong.App;

public class Menu extends Page {

    public static ImageView start_game = new ImageView();
    public static ImageView select = new ImageView();
    public static ImageView player1 = new ImageView();
    public static ImageView player2 = new ImageView();
    public static ImageView settings = new ImageView();
    public static ImageView exit = new ImageView();     // Ce bouton va servir de bouton pour quitter le jeu et de bouton de retour entre les pages

    ImageView[] choiceButtons = {player1,player2,settings,exit};
    ImageView choice = player1;
    int choiceInt = 0;

    public Menu(Pane root, Scene scene, GameView gameview, double zoom) {
        super(root, scene, gameview, zoom);

        Node[] objects = 
        {
            start_game,player1,player2,settings,exit,
            select,
        };
        this.allObjects = objects;

        createPage();

        // On ajoute tous les objets dans le root
        addChildrens();    
    }

    public void createPage(){

        Court.setAledroit(false);

        root.setId("menu");
        scene.getStylesheets().addAll(this.getClass().getResource("css/fond.css").toExternalForm());

        // Création du bouton "start game"
        Image start_game_i = new Image(getClass().getResourceAsStream("image/text/pokepong.png"));
        start_game.setImage(start_game_i);
        start_game.setFitWidth(start_game_i.getWidth()*zoomText);
        start_game.setFitHeight(start_game_i.getHeight()*zoomText);
        start_game.setLayoutX((root.getWidth()/2) - start_game.getFitWidth()/2);
        start_game.setLayoutY((root.getHeight()/2.5) - start_game.getFitHeight()/2);

        // Création du bouton "player1"
        Image player1_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/1-player.png"));
        player1.setImage(player1_i);
        player1.setFitWidth(player1_i.getWidth()*zoomText);
        player1.setFitHeight(player1_i.getHeight()*zoomText);
        player1.setLayoutX((root.getWidth()/2) - player1.getFitWidth()/2);
        player1.setLayoutY(start_game.getLayoutY() + start_game.getFitHeight() + 50);

        // Création du bouton "player2"
        Image player2_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/2-players.png"));
        player2.setImage(player2_i);
        player2.setFitWidth(player2_i.getWidth()*zoomText);
        player2.setFitHeight(player2_i.getHeight()*zoomText);
        player2.setLayoutX((root.getWidth()/2) - player2.getFitWidth()/2);
        player2.setLayoutY(player1.getLayoutY() + player1.getFitHeight() + 10);

        
        // Création du bouton "settings"
        Image settings_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/settings.png"));
        settings.setImage(settings_i);
        settings.setFitWidth(settings_i.getWidth()*zoomText);
        settings.setFitHeight(settings_i.getHeight()*zoomText);
        settings.setLayoutX((root.getWidth()/2) - settings.getFitWidth()/2);
        settings.setLayoutY(player2.getLayoutY() + player2.getFitHeight() + 20);

        
        // Création du petit curseur selecteur
        Image select_i = new Image(getClass().getResourceAsStream("bouton/select.png"));
        select.setImage(select_i);
        select.setFitWidth(select_i.getWidth()*zoomText);
        select.setFitHeight(select_i.getHeight()*zoomText);
        placeSelector(choice);

        // Création du bouton "exit"
        Image exit_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/exit.png"));
        exit.setImage(exit_i);
        exit.setFitWidth(exit_i.getWidth()*zoomText);
        exit.setFitHeight(exit_i.getHeight()*zoomText);
        exit.setLayoutX((root.getWidth()/2) - exit.getFitWidth()/2);
        exit.setLayoutY(settings.getLayoutY() + settings.getFitHeight() + 10);

        configKey();
    }

    private void setChoice(int i){
        if(i >= 0 && i<choiceButtons.length){
            choiceInt = i;
            
        }
        else if(i<0){
            choiceInt = choiceButtons.length-1;
        }
        else{
            choiceInt = 0;
        }
        choice = choiceButtons[choiceInt];
        placeSelector(choice);
    }

    public void configKey(){
        scene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                default: break;
                case UP:
                    setChoice(choiceInt-1);
                    break;
                case DOWN:
                    setChoice(choiceInt+1);
                    break;
                case ENTER:
                    fire();
                    break;
                case SPACE:
                    fire();
                    break;
            }
        });
    }

    public void placeSelector(ImageView selected){
        select.setLayoutX(selected.getLayoutX() - select.getFitWidth() - 15);
        select.setLayoutY(selected.getLayoutY());
    }

    private void fire(){
        switch (choiceInt) {
            case 0:
                App.court.getPlayerB().setPseudo("Bot");
                SoundController.click();
                App.switchJ1();
                break;
            case 1:
                App.court.getPlayerB().setPseudo("Joueur B");
                SoundController.click();
                App.switchJ2();
                break;
            case 2:
                SoundController.click();
                App.switchSettings();
                break;
            case 3:
                SoundController.click();
                App.primaryStage.close();
                break;
            default:
                break;
        }
    }

    public void lunch() {

        createPage();
        show();
    }
}
