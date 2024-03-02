package gui;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Court;
import model.Sauvegarde;
import pokepong.App;

public class Settings extends Page {


    public static ImageView settings = new ImageView();
    public static ImageView exit = new ImageView();

    // Objets choix arènes
    public static ImageView theme = new ImageView();
    public static ImageView themeG = new ImageView();
    public static ImageView themeD = new ImageView();
    public static ImageView auto = new ImageView();
    public static ImageView electric = new ImageView();
    public static ImageView fire = new ImageView();
    public static ImageView grass = new ImageView();
    public static ImageView water = new ImageView();
    public static ImageView[] themeTab = {auto,electric,fire,grass,water};
    public static int themeInt = Sauvegarde.getTheme();
    public static ImageView themeChoice = themeTab[themeInt];

    // Objets choix raquette-J1
    public static ImageView racketj1 = new ImageView();
    public static ImageView racketj1G = new ImageView();
    public static ImageView racketj1D = new ImageView();
    public static ImageView redj1 = new ImageView();
    public static ImageView bluej1 = new ImageView();
    public static ImageView greenj1 = new ImageView();
    public static ImageView yellowj1 = new ImageView();
    public static ImageView purplej1 = new ImageView();
    public static ImageView cyanj1 = new ImageView();
    public static ImageView[] racketj1Tab = {redj1,bluej1,greenj1,yellowj1,purplej1,cyanj1};
    public static int racketj1Int = Sauvegarde.getCouleur1();
    public static ImageView racketj1Choice = racketj1Tab[racketj1Int];

    // Objets choix raquette-J2
    public static ImageView racketj2 = new ImageView();
    public static ImageView racketj2G = new ImageView();
    public static ImageView racketj2D = new ImageView();
    public static ImageView redj2 = new ImageView();
    public static ImageView bluej2 = new ImageView();
    public static ImageView greenj2 = new ImageView();
    public static ImageView yellowj2 = new ImageView();
    public static ImageView purplej2 = new ImageView();
    public static ImageView cyanj2 = new ImageView();
    public static ImageView[] racketj2Tab = {redj2,bluej2,greenj2,yellowj2,purplej2,cyanj2};
    public static int racketj2Int = Sauvegarde.getCouleur2();
    public static ImageView racketj2Choice = racketj2Tab[racketj2Int];
    
    // Objets choix sons
    public static ImageView sounds = new ImageView();
    public static ImageView sound = new ImageView();
    public static ImageView music = new ImageView();
    public static boolean sound_muted = Sauvegarde.getSon();
    public static boolean music_muted = Sauvegarde.getMusique();

    // Objets choix langage
    public static ImageView language = new ImageView();
    public static ImageView languageG = new ImageView();
    public static ImageView languageD = new ImageView();
    public static ImageView fr = new ImageView();
    public static ImageView en = new ImageView();
    public static ImageView[] languageTab = {en,fr};
    public static ImageView languageChoice = en;
    public static int languageInt = Sauvegarde.getLangueInt();

    // Déplacements touches clavier
    public static ImageView select = new ImageView();
    ImageView[] choiceButtons = {theme,racketj1,racketj2,sounds,language,exit};
    ImageView choice = theme;
    int choiceInt = 0;

    public Settings(Pane root, Scene scene, GameView gameview, double zoom) {
        super(root, scene, gameview, zoom);

        Node[] objects = 
        {
            settings,exit,
            theme,themeG,themeD,auto,electric,fire,grass,water,
            racketj1,racketj1G,racketj1D,redj1,bluej1,greenj1,yellowj1,purplej1,cyanj1,
            racketj2,racketj2G,racketj2D,redj2,bluej2,greenj2,yellowj2,purplej2,cyanj2,
            sounds,sound,music,
            language,languageG,languageD,en,fr,
            select
        };
        this.allObjects = objects;

        createPage();

        // On ajoute tous les objets dans le root
        addChildrens();
    }

    @Override
    public void show(){
        Node[] objects = {exit,settings,
            theme,themeG,themeD,themeChoice,
            racketj1,racketj1G,racketj1D,racketj1Choice,
            racketj2,racketj2G,racketj2D,racketj2Choice,
            sounds,sound,music,
            language,languageG,languageD,languageChoice,
            select
        };
        List<Node> obj = Arrays.asList(objects);
        for(Node o : root.getChildren()){
            if(obj.contains(o)){
                o.setVisible(true);
            }
            else{
                o.setVisible(false);
            }
        }
    }

    @Override
    public void createPage() {

        root.setId("settings");
        scene.getStylesheets().addAll(this.getClass().getResource("css/fond.css").toExternalForm());

        double zoomSon = 0.4;

        // Le bouton "exit" devient un bouton "back" sur cette page
        Image exit_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/return.png"));
        exit.setImage(exit_i);
        exit.setFitWidth(exit_i.getWidth()*zoomText);
        exit.setFitHeight(exit_i.getHeight()*zoomText);
        exit.setLayoutY(screen.getHeight() - exit.getFitHeight() - 50);
        exit.setLayoutX(screen.getWidth()/2 - exit.getFitWidth()/2);

        // Reglage du bouton "settings"
        Image settings_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/settings.png"));
        settings.setImage(settings_i);
        settings.setFitWidth(settings_i.getWidth()*zoomText);
        settings.setFitHeight(settings_i.getHeight()*zoomText);
        settings.setLayoutY(settings.getFitHeight() + 50);
        settings.setLayoutX(screen.getWidth()/2 - settings.getFitWidth()/2);

        ////////////////////////////////////////////////

        // Reglage du bouton "theme"
        createChoiceButton(theme,"music", settings);

        // Reglage du bouton "auto"
        createOptionButton(auto, "auto", theme);
 
        // Reglage du bouton "electric"
        createOptionButton_trad(electric, "electric", theme);

        // Reglage du bouton "fire"
        createOptionButton_trad(fire, "fire", theme);

        // Reglage du bouton "grass"
        createOptionButton_trad(grass, "grass", theme);

        // Reglage du bouton "water"
        createOptionButton_trad(water, "water", theme);

        // Reglage du bouton "themeG"
        createLeftSelector(themeG, themeChoice);

        // Reglage du bouton "themeD"
        createRightSelector(themeD, themeChoice);

        ////////////////////////////////////////////////

        // Reglage du bouton "racketj1"
        createChoiceButton(racketj1,"racketj1", theme);

        // Reglage du bouton "redj1"
        createOptionButton_trad(redj1, "red", racketj1);

        // Reglage du bouton "bluej1"
        createOptionButton_trad(bluej1, "blue", racketj1);

        // Reglage du bouton "greenj1"
        createOptionButton_trad(greenj1, "green", racketj1);

        // Reglage du bouton "yellowj1"
        createOptionButton_trad(yellowj1, "yellow", racketj1);

        // Reglage du bouton "purplej1"
        createOptionButton_trad(purplej1, "purple", racketj1);

        // Reglage du bouton "cyanj1"
        Image cyanj1_i = new Image(getClass().getResourceAsStream("bouton/cyan.png"));
        cyanj1.setImage(cyanj1_i);
        cyanj1.setFitWidth(cyanj1_i.getWidth()*zoomText);
        cyanj1.setFitHeight(cyanj1_i.getHeight()*zoomText);
        cyanj1.setLayoutY(racketj1.getLayoutY());
        cyanj1.setLayoutX(screen.getWidth() - screen.getWidth()/4 - cyanj1.getFitWidth()/2);
        cyanj1.setVisible(false);
        
        // Reglage du bouton "racketj1G"
        createLeftSelector(racketj1G, racketj1Choice);

        // Reglage du bouton "racketj1D"
        createRightSelector(racketj1D, racketj1Choice);

        ////////////////////////////////////////////////

        // Reglage du bouton "racketj2"
        createChoiceButton(racketj2,"racketj2", racketj1);

        // Reglage du bouton "redj2"
        createOptionButton_trad(redj2, "red", racketj2);

        // Reglage du bouton "bluej2"
        createOptionButton_trad(bluej2, "blue", racketj2);

        // Reglage du bouton "greenj2"
        createOptionButton_trad(greenj2, "green", racketj2);

        // Reglage du bouton "yellowj2"
        createOptionButton_trad(yellowj2, "yellow", racketj2);

        // Reglage du bouton "purplej2"
        createOptionButton_trad(purplej2, "purple", racketj2);

        // Reglage du bouton "cyanj2"
        Image cyanj2_i = new Image(getClass().getResourceAsStream("bouton/cyan.png"));
        cyanj2.setImage(cyanj2_i);
        cyanj2.setFitWidth(cyanj2_i.getWidth()*zoomText);
        cyanj2.setFitHeight(cyanj2_i.getHeight()*zoomText);
        cyanj2.setLayoutY(racketj2.getLayoutY());
        cyanj2.setLayoutX(screen.getWidth() - screen.getWidth()/4 - cyanj2.getFitWidth()/2);
        cyanj2.setVisible(false);

        // Reglage du bouton "racketj2G"
        createLeftSelector(racketj2G, racketj2Choice);

        // Reglage du bouton "racketj2D"
        createRightSelector(racketj2D, racketj2Choice);
        ////////////////////////////////////////////////

        // Reglage du bouton "sounds"
        createChoiceButton(sounds,"sounds", racketj2);

        // Reglage du bouton "music"
        String mm = "";
        if(music_muted){mm = "muted";}
        else{mm = "unmuted";}

        Image mute_i = new Image(getClass().getResourceAsStream("bouton/music_"+mm+".png"));
        music.setImage(mute_i);
        music.setFitWidth(mute_i.getWidth()*zoomSon);
        music.setFitHeight(mute_i.getHeight()*zoomSon);
        music.setLayoutY(sounds.getLayoutY()-music.getFitHeight()/2 + sounds.getFitHeight()/2);
        music.setLayoutX(screen.getWidth() - screen.getWidth()/4 - music.getFitWidth()/2 - 50);

        // Reglage du bouton "sound"
        String sm = "";
        if(sound_muted){sm = "muted";}
        else{sm = "unmuted";}

        Image sound_i = new Image(getClass().getResourceAsStream("bouton/sound_"+sm+".png"));
        sound.setImage(sound_i);
        sound.setFitWidth(sound_i.getWidth()*zoomSon);
        sound.setFitHeight(sound_i.getHeight()*zoomSon);
        sound.setLayoutY(sounds.getLayoutY()-sound.getFitHeight()/2 + sounds.getFitHeight()/2);
        sound.setLayoutX(screen.getWidth() - screen.getWidth()/4 - sound.getFitWidth()/2 + 50);

        ////////////////////////////////////////////////

        // Reglage du bouton "language"
        createChoiceButton(language,"language", sounds);

        // Reglage du bouton "en"
        createOptionButton_trad(en, "flag", language);

        // Reglage du bouton "fr"
        createOptionButton_trad(fr, "flag", language);

        // Reglage du bouton "languageG"
        createLeftSelector(languageG, languageChoice);

        // Reglage du bouton "languageD"
        createRightSelector(languageD, languageChoice);

        ////////////////////////////////////////////////

        themeChoice.setVisible(true);
        racketj1Choice.setVisible(true);
        racketj2Choice.setVisible(true);
        languageChoice.setVisible(true);

        if(!App.court.getPlayerA().isPowerUp()){
            Court.setIndiceATDC(racketj1Int);
            GameView.getRacketA().setFill(Color.web(Court.getCouleur(Court.getIndiceATDC())));
        }
        if(!App.court.getPlayerB().isPowerUp()){
            Court.setIndiceBTDC(racketj2Int);
            GameView.getRacketB().setFill(Color.web(Court.getCouleur(Court.getIndiceBTDC())));
        }


        if(App.enJeu){
            themeG.setOpacity(0.5);
            themeD.setOpacity(0.5);
        }
        else{
            themeG.setOpacity(1);
            themeD.setOpacity(1);
        }

        App.gameView.updateGraphiquePause();

        Image select_i = new Image(getClass().getResourceAsStream("bouton/select.png"));
        select.setImage(select_i);
        select.setFitWidth(select_i.getWidth()*zoomText);
        select.setFitHeight(select_i.getHeight()*zoomText);

        placeSelector();
        configKey();
    }

    // Méthode pour créer la flèche de droite
    private void createRightSelector(ImageView buttonD, ImageView buttonChoice){
        Image b_i = new Image(getClass().getResourceAsStream("bouton/select.png"));
        buttonD.setImage(b_i);
        buttonD.setFitWidth(b_i.getWidth()*zoomText);
        buttonD.setFitHeight(b_i.getHeight()*zoomText);
        buttonD.setLayoutY(buttonChoice.getLayoutY());
        buttonD.setLayoutX(buttonChoice.getLayoutX() + buttonChoice.getFitWidth() + 10);
    }

    // Méthode pour créer la flèche de gauche
    private void createLeftSelector(ImageView buttonG, ImageView buttonChoice){
        Image b_i = new Image(getClass().getResourceAsStream("bouton/select.png"));
        buttonG.setImage(b_i);
        buttonG.setFitWidth(b_i.getWidth()*zoomText);
        buttonG.setFitHeight(b_i.getHeight()*zoomText);
        buttonG.setLayoutY(buttonChoice.getLayoutY());
        buttonG.setLayoutX(buttonChoice.getLayoutX() - buttonG.getFitWidth() - 10);
        buttonG.setScaleX(-1);
    }

    // Méthode pour créer le nom du choix à gauche de la fenetre
    private void createChoiceButton(ImageView buttonChoice, String filename, ImageView upsideButton){
        Image i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/"+filename+".png"));
        buttonChoice.setImage(i);
        buttonChoice.setFitWidth(i.getWidth()*zoomText);
        buttonChoice.setFitHeight(i.getHeight()*zoomText);
        buttonChoice.setLayoutY(upsideButton.getLayoutY() + buttonChoice.getFitHeight()+50);
        buttonChoice.setLayoutX(screen.getWidth()/4 - buttonChoice.getFitWidth()/2);
    }

    // Méthode pour pouvoir regler facilement les boutons à droite qui nécessite une traduction
    private void createOptionButton_trad(ImageView option, String filename, ImageView choiceButton){
        Image i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/"+filename+".png"));
        option.setImage(i);
        option.setFitWidth(i.getWidth()*zoomText);
        option.setFitHeight(i.getHeight()*zoomText);
        option.setLayoutY(choiceButton.getLayoutY());
        option.setLayoutX(screen.getWidth() - screen.getWidth()/4 - option.getFitWidth()/2);
        option.setVisible(false);
    }

    private void createOptionButton(ImageView option, String filename, ImageView choiceButton){
        Image i = new Image(getClass().getResourceAsStream("image/text/"+filename+".png"));
        option.setImage(i);
        option.setFitWidth(i.getWidth()*zoomText);
        option.setFitHeight(i.getHeight()*zoomText);
        option.setLayoutY(choiceButton.getLayoutY());
        option.setLayoutX(screen.getWidth() - screen.getWidth()/4 - option.getFitWidth()/2);
        option.setVisible(false);
    }

    public void placeSelector(){
        select.setLayoutX(choice.getLayoutX() - 50);
        select.setLayoutY(choice.getLayoutY() + choice.getFitHeight()/2 - select.getFitHeight()/2);
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

    private void goUp(){
        setChoice(choiceInt - 1);
    }
    private void goDown(){
        setChoice(choiceInt + 1);
    }

    private void goRight(){
        switch (choiceInt) {
            case 0:             // Arène
                themeLeft(false);
                break;
            case 1:             // Raquette J1
                racketj1Left(false);
                break;
            case 2:             // Raquette J2
                racketj2Left(false);
                break;
            case 3:             // Sons
                sound();
                break;
            case 4:             // Langage
                languageLeft(false);
                break;
            default:
                break;
        }
    }
    private void goLeft(){
        switch (choiceInt) {
            case 0:             // Arène
                themeLeft(true);
                break;
            case 1:             // Raquette J1
                racketj1Left(true);
                break;
            case 2:             // Raquette J2
                racketj2Left(true);
                break;
            case 3:             // Sons
                music();
                break;
            case 4:             // Langage
                languageLeft(true);
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
    
    private void themeLeft(boolean left){
        if(!App.enJeu){
            if(left){
                if(themeInt != 0){
                    themeInt--;
                }
                else{
                    themeInt = themeTab.length-1;
                }
            }
            else{
                if(themeInt != themeTab.length - 1){
                    themeInt++;
                }
                else{
                    themeInt = 0;
                }
            }
            themeChoice = themeTab[themeInt];
            SoundController.joue("choice_click");
            createPage();
        }
    }

    private void racketj1Left(boolean left){
        if(left){
            if(racketj1Int != 0){
                racketj1Int--;
            }
            else{
                racketj1Int = racketj1Tab.length-1;
            };
        }
        else{
            if(racketj1Int != racketj1Tab.length - 1){
                racketj1Int++;
            }
            else{
                racketj1Int = 0;
            }
        }
        racketj1Choice = racketj1Tab[racketj1Int];
        SoundController.joue("choice_click");
        createPage();
    }

    private void racketj2Left(boolean left){
        if(left){
            if(racketj2Int != 0){
                racketj2Int--;
            }
            else{
                racketj2Int = racketj2Tab.length-1;
            };
        }
        else{
            if(racketj2Int != racketj2Tab.length - 1){
                racketj2Int++;
            }
            else{
                racketj2Int = 0;
            }
        }
        racketj2Choice = racketj2Tab[racketj2Int];
        SoundController.joue("choice_click");
        createPage();
    }

    private void languageLeft(boolean left){
        if(left){
            if(languageInt != 0){
                languageInt--;
            }
            else{
                languageInt = languageTab.length-1;
            }
        }
        else{
            if(languageInt != languageTab.length - 1){
                languageInt++;
            }
            else{
                languageInt = 0;
            }
        }

        languageChoice = languageTab[languageInt];

        if(languageInt == 0){
            App.suff = "EN";
        }
        else{
            App.suff = "FR";
        }
        SoundController.joue("choice_click");
        createPage();
    }

    private void music(){
        if(music_muted){
            music.setImage(new Image(getClass().getResourceAsStream("bouton/music_unmuted.png")));
            music_muted = false;
            SoundController.unmuteMusic();
        }
        else{
            music.setImage(new Image(getClass().getResourceAsStream("bouton/music_muted.png")));
            music_muted = true;
            SoundController.muteMusic();
        }
    }

    private void sound(){
        if(sound_muted){
            sound.setImage(new Image(getClass().getResourceAsStream("bouton/sound_unmuted.png")));
            sound_muted = false;
            SoundController.unmuteSounds();
        }
        else{
            sound.setImage(new Image(getClass().getResourceAsStream("bouton/sound_muted.png")));
            sound_muted = true;
            SoundController.muteSounds();
        }
    }


    private void fire(){
        if(choiceInt == choiceButtons.length-1){
            SoundController.click();
            Sauvegarde.sauvegardeData();        
            if(!App.enJeu){App.switchMenu();}
            else{App.switchPong();}
        }
    }
}
