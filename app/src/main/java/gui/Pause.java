package gui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pokepong.App;

public class Pause extends Page {

    public static int ecartY = 20;
    public static double ecartX = ((-328)*0.3)/2;

    public static ImageView resume = new ImageView();
    public static ImageView settings = new ImageView();
    public static ImageView exit = new ImageView();

    public static ImageView select = new ImageView();

    ImageView[] choiceButtons = {resume,settings,exit};
    ImageView choice = resume;
    int choiceInt = 0;

    public Pause(Pane root, Scene scene, GameView gameview, double zoom) {
        super(root, scene, gameview, zoom);

        Node[] objects = 
        {
            resume,settings,exit,select
        };
        this.allObjects = objects;

        createPage();

        // On ajoute tous les objets dans le root
        addChildrens();
    }

    @Override
    public void createPage() {

        Image select_i = new Image(getClass().getResourceAsStream("bouton/select.png"));
        select.setImage(select_i);
        select.setFitWidth(select_i.getWidth()*zoomText);
        select.setFitHeight(select_i.getHeight()*zoomText);
        select.setScaleX(-1);

        Image resume_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/pause_resume.png"));
        resume.setImage(resume_i);
        resume.setFitWidth(resume_i.getWidth()*zoomText);
        resume.setFitHeight(resume_i.getHeight()*zoomText);
        resume.setLayoutX((root.getWidth()/2) - resume.getFitWidth()/2 + ecartX);
        resume.setLayoutY(settings.getLayoutY() - settings.getFitHeight() - ecartY);

        Image settings_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/pause_settings.png"));
        settings.setImage(settings_i);
        settings.setFitWidth(settings_i.getWidth()*zoomText);
        settings.setFitHeight(settings_i.getHeight()*zoomText);
        settings.setLayoutX((root.getWidth()/2) - settings.getFitWidth()/2 + ecartX);
        settings.setLayoutY((root.getHeight() - settings.getFitHeight())/2);       // On met le settings au centre de l'écran, et positionne le reste par rapport à lui

        Image exit_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/pause_exit.png"));
        exit.setImage(exit_i);
        exit.setFitWidth(exit_i.getWidth()*zoomText);
        exit.setFitHeight(exit_i.getHeight()*zoomText);
        exit.setLayoutX((root.getWidth()/2) - exit.getFitWidth()/2 + ecartX);
        exit.setLayoutY(settings.getLayoutY()+settings.getFitHeight()+ecartY);

        placeSelector();
        
    }

    public void showPauseButtons(){
        createPage();
        exit.setVisible(true);
        resume.setVisible(true);
        settings.setVisible(true);
        select.setVisible(true);

        exit.toFront();
        resume.toFront();
        settings.toFront();
        select.toFront();

        configKey();    
    }

    public void hidePauseButtons(){
        exit.setVisible(false);
        resume.setVisible(false);
        settings.setVisible(false);
        select.setVisible(false);
        App.configPongKey();
    }

    public void setChoice(int i){
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
        placeSelector();
    }

    public void placeSelector(){
        select.setLayoutX(choice.getLayoutX() + choice.getFitWidth() + 50 - select.getFitWidth());
        select.setLayoutY(choice.getLayoutY() + choice.getFitHeight()/2 - select.getFitHeight()/2);
    }

    private void fire(){
        switch (choiceInt) {
            case 0:
                gameview.resumePong();
                SoundController.click();
                break;
            case 1:
                App.switchSettings();
                SoundController.click();
                break;
            case 2:
                gameview.exitPong();
                SoundController.click();
                break;
            default:
                break;
        }
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
                case ESCAPE:
                    gameview.resumePong();
                    break;
            }
        });
    }
    
}
