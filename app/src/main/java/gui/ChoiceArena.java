package gui;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pokepong.App;

public class ChoiceArena extends Page {

    public static ImageView start_game = new ImageView();
    public static ImageView select1 = new ImageView();
    public static ImageView select2 = new ImageView();
    public static ImageView cadre1 = new ImageView();
    public static ImageView cadre2 = new ImageView();
    public static ImageView cadre3 = new ImageView();
    public static ImageView cadre4 = new ImageView();
    public static ImageView exit = new ImageView();
    public static ImageView choose_arena = new ImageView();

    ImageView[] line1 = {cadre1,cadre2};
    ImageView[] line2 = {start_game};
    ImageView[] line3 = {exit};
    ImageView[] line4 = {cadre3,cadre4};
    ImageView[][] choiceButtons = {line1,line2,line3,line4};

    ImageView choice = cadre1;
    int choiceLineInt = 0;
    int choiceInt = 0;

    public ChoiceArena(Pane root, Scene scene, GameView gameview, double zoom) {
        super(root, scene, gameview, zoom);

        Node[] objects = 
        {
            start_game,
            select1,select2,
            cadre1,cadre2,cadre3,cadre4,
            exit,choose_arena
        };
        this.allObjects = objects;

        createPage();

        // On ajoute tous les objets dans le root
        addChildrens();
    }

    @Override
    public void createPage() {

        root.setId("menu");
        scene.getStylesheets().addAll(this.getClass().getResource("css/fond.css").toExternalForm());

        Image select_i = new Image(getClass().getResourceAsStream("bouton/select.png"));
        select1.setImage(select_i);
        select1.setFitWidth(select_i.getWidth()*zoomText);
        select1.setFitHeight(select_i.getHeight()*zoomText);

        select2.setImage(select_i);
        select2.setFitWidth(select_i.getWidth()*zoomText);
        select2.setFitHeight(select_i.getHeight()*zoomText);
        select2.setScaleX(-1);

        double zoomCadre = 0.33;

        Image cadre1_i = new Image(getClass().getResourceAsStream("bouton/pikachu.png"));
        cadre1.setImage(cadre1_i);
        cadre1.setFitWidth(cadre1_i.getWidth()*zoomCadre);
        cadre1.setFitHeight(cadre1_i.getHeight()*zoomCadre);
        cadre1.setLayoutX((root.getWidth()/4) - cadre1.getFitWidth()/2);
        cadre1.setLayoutY((root.getHeight()/4) - cadre1.getFitHeight()/2);

        Image cadre2_i = new Image(getClass().getResourceAsStream("bouton/salameche.png"));
        cadre2.setImage(cadre2_i);
        cadre2.setFitWidth(cadre2_i.getWidth()*zoomCadre);
        cadre2.setFitHeight(cadre2_i.getHeight()*zoomCadre);
        cadre2.setLayoutX(root.getWidth() - cadre2.getFitWidth()/2 - root.getWidth()/4);
        cadre2.setLayoutY(root.getHeight()/4 - cadre2.getFitHeight()/2);

        Image cadre3_i = new Image(getClass().getResourceAsStream("bouton/bulbizarre.png"));
        cadre3.setImage(cadre3_i);
        cadre3.setFitWidth(cadre3_i.getWidth()*zoomCadre);
        cadre3.setFitHeight(cadre3_i.getHeight()*zoomCadre);
        cadre3.setLayoutX(cadre1.getLayoutX());
        cadre3.setLayoutY(root.getHeight() - cadre2.getFitWidth()/2 - root.getHeight()/4);
        
        Image cadre4_i = new Image(getClass().getResourceAsStream("bouton/carapuce.png"));
        cadre4.setImage(cadre4_i);
        cadre4.setFitWidth(cadre4_i.getWidth()*zoomCadre);
        cadre4.setFitHeight(cadre4_i.getHeight()*zoomCadre);
        cadre4.setLayoutX(cadre2.getLayoutX());
        cadre4.setLayoutY(cadre3.getLayoutY());
        
        Image start_game_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/start_game.png"));
        start_game.setImage(start_game_i);
        start_game.setFitWidth(start_game_i.getWidth()*zoomText);
        start_game.setFitHeight(start_game_i.getHeight()*zoomText);
        start_game.setLayoutX(root.getWidth()/2 - start_game.getFitWidth()/2);
        start_game.setLayoutY(root.getHeight()/2 - start_game.getFitHeight()/2);
        
        // Le bouton "exit" devient un bouton "return" sur cette page
        Image exit_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/return.png"));
        exit.setImage(exit_i);
        exit.setFitWidth(exit_i.getWidth()*zoomText);
        exit.setFitHeight(exit_i.getHeight()*zoomText);
        exit.setLayoutY(start_game.getLayoutY() + 100);
        exit.setLayoutX(screen.getWidth()/2 - exit.getFitWidth()/2);

        Image choose_arena_i = new Image(getClass().getResourceAsStream("image/text/"+App.suff+"/choose_arena.png"));
        choose_arena.setImage(choose_arena_i);
        choose_arena.setFitWidth(choose_arena_i.getWidth()*zoomText);
        choose_arena.setFitHeight(choose_arena_i.getHeight()*zoomText);
        choose_arena.setLayoutY(start_game.getLayoutY() - 100 + choose_arena.getFitHeight()/2);
        choose_arena.setLayoutX(screen.getWidth()/2 - choose_arena.getFitWidth()/2);

        setChoiceLine(0);
        placeSelector();
        configKey();
        big(choice);
    }

    private void setChoiceLine(int i){
        if(i >= 0 && i<choiceButtons.length){
            choiceLineInt = i;
        }
        else if(i<0){
            choiceLineInt = choiceButtons.length-1;
        }
        else{
            choiceLineInt = 0;
        }
        choiceInt = 0;
        choice = choiceButtons[choiceLineInt][choiceInt];
        placeSelector();
    }

    private void setChoice(int i){
        if(i >= 0 && i<choiceButtons[choiceLineInt].length){
            choiceInt = i;
        }
        else if(i<0){
            choiceInt = choiceButtons[choiceLineInt].length-1;
        }
        else{
            choiceInt = 0;
        }
        choice = choiceButtons[choiceLineInt][choiceInt];
        placeSelector();
    }

    private void goUp(){
        setChoiceLine(choiceLineInt - 1);
    }
    private void goDown(){
        setChoiceLine(choiceLineInt + 1);
    }
    private void goRight(){
        setChoice(choiceInt+1);
    }
    private void goLeft(){
        setChoice(choiceInt-1);
    }

    public void placeSelector(){
        select1.setLayoutX(choice.getLayoutX() - 50);
        select1.setLayoutY(choice.getLayoutY() + choice.getFitHeight()/2 - select1.getFitHeight()/2);

        select2.setLayoutX(choice.getLayoutX() + choice.getFitWidth() + 50 - select2.getFitWidth());
        select2.setLayoutY(select1.getLayoutY());
    }

    public void configKey(){
        scene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                default: break;
                case UP:
                    goUp();
                    break;
                case DOWN:
                    goDown();
                    break;
                case RIGHT:
                    goRight();
                    break;
                case LEFT:
                    goLeft();
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

    public void fire(){
        switch (choiceLineInt) {
            case 0:
                if(choiceInt == 0){
                    SoundController.joue("pokechoosed");
                    App.setBackPokepongArenaI();
                    big(cadre1);
                }
                if(choiceInt == 1){
                    SoundController.joue("pokechoosed");
                    App.pokepongArenaI=1;
                    big(cadre2);
                }
                break;
            case 1:
                SoundController.click();
                if(App.bot){
                    App.switchPseudoA();
                }
                else{
                    App.switchPseudoB();
                }
                break;
            case 2:
                SoundController.click();
                App.switchMenu();
                break;
            case 3:
                if(choiceInt == 0){
                    SoundController.joue("pokechoosed");
                    App.pokepongArenaI=2;
                    big(cadre3);
                }
                if(choiceInt == 1){
                    SoundController.joue("pokechoosed");
                    App.pokepongArenaI=3;
                    big(cadre4);
                }
                break;
            default:
                break;
        }
    }

    private void big(ImageView i){
        cadre1.setEffect(null);
        cadre2.setEffect(null);
        cadre3.setEffect(null);
        cadre4.setEffect(null);

        Glow glow = new Glow();
        glow.setLevel(9);
        DropShadow dropshadow = new DropShadow();
        dropshadow.setOffsetY(10);

        Blend blend = new Blend();
        blend.setMode(BlendMode.ADD);
        blend.setBottomInput(dropshadow);
        blend.setBottomInput(glow);

        i.setEffect(blend);
    }

}
