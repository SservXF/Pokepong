package gui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pokepong.App;

public class EndGame extends Page {

    public static int ecartY = 20;
    public static double ecartX = ((-328)*0.3)/2;

    public static ImageView playAgain = new ImageView();
    private static ImageView victory = new ImageView();
    private static ImageView defeat= new ImageView();
    private static ImageView victory_j1 = new ImageView();
    private static ImageView victory_j2= new ImageView();
    public static ImageView exit = new ImageView();

    public static ImageView select = new ImageView();

    ImageView[] choiceButtons = {playAgain,exit};
    ImageView choice = playAgain;
    int choiceInt = 0;

    public EndGame(Pane root, Scene scene, GameView gameview, double zoom) {
        super(root, scene, gameview, zoom);

        Node[] objects = 
        {
            victory,defeat,
            victory_j1,victory_j2,
            playAgain,exit,
            select
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

        Image exit_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/pause_exit.png"));
        exit.setImage(exit_i);
        exit.setFitWidth(exit_i.getWidth()*zoomText);
        exit.setFitHeight(exit_i.getHeight()*zoomText);
        exit.setLayoutX((root.getWidth()/2) - exit.getFitWidth()/2 + ecartX);
        exit.setLayoutY(Pause.exit.getLayoutY());

        Image playAgain_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/play_again.png"));
        playAgain.setImage(playAgain_i);
        playAgain.setFitWidth(playAgain_i.getWidth()*zoomText);
        playAgain.setFitHeight(playAgain_i.getHeight()*zoomText);
        playAgain.setLayoutX((root.getWidth()/2) - playAgain.getFitWidth()/2 + ecartX);
        playAgain.setLayoutY(exit.getLayoutY() - playAgain.getFitHeight() - ecartY);

        victory.setImage(new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/victory.png")));
        victory.setFitHeight(victory.getImage().getHeight());
        victory.setFitWidth(victory.getImage().getWidth());
        victory.setLayoutX((root.getWidth()/2) - victory.getFitWidth()/2);
        victory.setLayoutY(playAgain.getLayoutY() - victory.getFitHeight() - ecartY);

        defeat.setImage(new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/defeat.png")));
        defeat.setFitHeight(defeat.getImage().getHeight());
        defeat.setFitWidth(defeat.getImage().getWidth());
        defeat.setLayoutX((root.getWidth()/2) - defeat.getFitWidth()/2);
        defeat.setLayoutY(playAgain.getLayoutY() - defeat.getFitHeight() - ecartY);

        victory_j1.setImage(new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/victory_j1.png")));
        victory_j1.setFitHeight(victory_j1.getImage().getHeight());
        victory_j1.setFitWidth(victory_j1.getImage().getWidth());
        victory_j1.setLayoutX((root.getWidth()/2) - victory_j1.getFitWidth()/2);
        victory_j1.setLayoutY(playAgain.getLayoutY() - victory_j1.getFitHeight() - ecartY);

        victory_j2.setImage(new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/victory_j2.png")));
        victory_j2.setFitHeight(victory_j2.getImage().getHeight());
        victory_j2.setFitWidth(victory_j2.getImage().getWidth());
        victory_j2.setLayoutX((root.getWidth()/2) - victory_j2.getFitWidth()/2);
        victory_j2.setLayoutY(playAgain.getLayoutY() - victory_j2.getFitHeight() - ecartY);

        placeSelector();
        setChoice(0);
        
    }

    public void show(){
        if(App.bot){
            if(App.playerA.getPV() <=0){
                defeat.setVisible(true);
                victory.setVisible(false);
            }
            else{
                victory.setVisible(true);
                defeat.setVisible(false);
            }
        }
        else{
            if(App.playerB.getPV() <= 0){
                victory_j1.setVisible(true);
                victory_j2.setVisible(false);
            }
            else{
                victory_j2.setVisible(true);
                victory_j1.setVisible(false);
            }
        }
        playAgain.setVisible(true);
        Pause.exit.setVisible(true);
        select.setVisible(true);
        configKey();    
    }

    public void hide(){
        victory.setVisible(false);
        defeat.setVisible(false);
        victory_j1.setVisible(false);
        victory_j2.setVisible(false);
        select.setVisible(false);
        playAgain.setVisible(false);
        exit.setVisible(false);
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
                hide();
                GameView.pause.hidePauseButtons();
                App.switchPong();
                SoundController.click();
                break;
            case 1:
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
            }
        });
    }
    
}
