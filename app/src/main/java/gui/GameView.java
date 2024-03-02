package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.*;
import model.Court;
import model.Racket;
import model.Sauvegarde;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import pokepong.App;

public class GameView {
    // class parameters
    private final Court court;
    private final Pane gameRoot; // main node of the game
    private final double scale;
    public final double xMargin = 50.0, racketThickness = 10.0; // pixels
    // children of the game main node
    private static Racket racketA, racketB;
    private Balle ball;
    private Balle powerBall;

    // Variables pour bien dimensionner les cadres de PV et PP
    private double pv_size_ratio = 0.25;
    private double pv_placementX;

    private Image jauge_power_i = new Image(getClass().getResourceAsStream("image/pv/jauge_power_purple.png"));

    private ImageView jauge_hp_void_j1 = new ImageView();
    private ImageView jauge_hp_j1 = new ImageView();
    private ImageView jauge_power_void_j1 = new ImageView();
    private ImageView jauge_power_j1 = new ImageView();
    private ImageView cadre_PV_j1 = new ImageView();

    private ImageView jauge_hp_void_j2 = new ImageView();
    private ImageView jauge_hp_j2 = new ImageView();
    private ImageView jauge_power_void_j2 = new ImageView();
    private ImageView jauge_power_j2 = new ImageView();
    private ImageView cadre_PV_j2 = new ImageView();

    private static Text pseudoA;
    private static Text pseudoB;

    public static Pause pause;
    public static EndGame endGame;

    private ImageView trois;
    private ImageView deux;
    private ImageView un;

    private PauseTransition pause_trois;
    private PauseTransition pause_deux;
    private PauseTransition pause_un;

   // private double rds = 2; //rotation duration seconds, initié à 2 secondes

    // L'animation devient aussi un attribut, de cette façon, on peut lancer et arreter l'animation juste avec les méthodes "animate()" et "stop()"
    private AnimationTimer animation;

    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
     */
    public GameView(Court court, Pane root, double scale) {
        this.court = court;
        this.gameRoot = root;
        this.scale = scale;
        racketA=court.getPlayerA().getRacket();
        racketB=court.getPlayerB().getRacket();
        pv_placementX = court.getWidth()/25;

        root.setMinWidth(court.getWidth() * this.scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * this.scale);

        ball = new Balle(court.getBallRadius(),court.getBallX() * this.scale + xMargin,court.getBallY() * this.scale,"image/pokeball.png");
        powerBall = new Balle(court.getBallRadius(),court.getBallX() * this.scale + xMargin,court.getBallY() * this.scale,"image/fire_ball.png");
        racketA.setHeight(racketA.getSize() * this.scale);
        racketA.setWidth(racketThickness);
        Court.setIndiceATDC(Sauvegarde.getCouleur1());
        racketA.setX(xMargin - racketThickness);
        racketA.setY(court.getPlayerA().getRacketSize() * this.scale - xMargin);
        racketA.setStroke(Color.BLACK);
        racketA.setStrokeWidth(2);

        racketB.setHeight(racketB.getSize()* this.scale);
        racketB.setWidth(racketThickness);
        Court.setIndiceBTDC(Sauvegarde.getCouleur2());
        racketB.setX(court.getWidth() * this.scale - xMargin);
        racketB.setY(court.getPlayerB().getRacketSize() * this.scale);
        racketB.setStroke(Color.BLACK);
        racketB.setStrokeWidth(2);

        initialisePVBar();

        pseudoA = new Text(court.getPlayerA().getPseudo());
        pseudoB = new Text(court.getPlayerB().getPseudo());
        //pseudoB.setText("joueur 2");
        Font pixel = Font.loadFont(getClass().getResourceAsStream("font/PixelFont-YS.ttf"), 50);
        pseudoA.setFont(pixel);
        pseudoB.setFont(pixel);

        // On initialise les images du décompte
        trois = new ImageView(new Image(getClass().getResourceAsStream("decompte/3_noir.png")));
        deux = new ImageView(new Image(getClass().getResourceAsStream("decompte/2_noir.png")));
        un = new ImageView(new Image(getClass().getResourceAsStream("decompte/1_noir.png")));

        // On créer la transition du décompte ici, comme ça pas besoin de la recréer à chaque fois qu'on appelle le décompte
        pause_trois = new PauseTransition(Duration.seconds(0));
        pause_trois.setOnFinished(event -> {
        TranslateTransition transition_trois = new TranslateTransition();
        transition_trois.setNode(trois);
        transition_trois.setDuration(Duration.seconds(1));
        transition_trois.setByX(court.getWidth()/2 + trois.getFitWidth()/2);
        transition_trois.play();});

        pause_deux = new PauseTransition(Duration.seconds(1));
        pause_deux.setOnFinished(event -> {
        TranslateTransition transition_deux = new TranslateTransition();
        transition_deux.setNode(deux);
        transition_deux.setDuration(Duration.seconds(1));
        transition_deux.setByX(court.getWidth()/2 + deux.getFitWidth()/2);
        trois.setVisible(false);
        transition_deux.play();});

        pause_un = new PauseTransition(Duration.seconds(2));
        pause_un.setOnFinished(event -> {
        TranslateTransition transition_un = new TranslateTransition();
        transition_un.setNode(un);
        transition_un.setDuration(Duration.seconds(1));
        transition_un.setByX(court.getWidth()/2 + un.getFitWidth()/2);
        deux.setVisible(false);
        transition_un.play();});

        gameRoot.getChildren().addAll(racketA,racketB, pseudoA, pseudoB, ball, powerBall,
        trois,deux,un
        );

        // On rajoute aussi les objets de la jauge PV
        gameRoot.getChildren().addAll(
        jauge_hp_void_j1,jauge_hp_j1,jauge_power_void_j1,jauge_power_j1,cadre_PV_j1,
        jauge_hp_void_j2,jauge_hp_j2,jauge_power_void_j2,jauge_power_j2,cadre_PV_j2);

        pause = new Pause(root, root.getScene(), this, 1);
        endGame = new EndGame(root, root.getScene(), this, 1);

        // On créer l'animation, pour pouvoir s'en servir avec les méthodes "animate()" et "stop()"
        animation =  new AnimationTimer() {
            
            long last = 0;
            //On prédéfinit sX et sY, qui serviront pour garder en mémoire la vitesse et direction de la balle
            double sX;
            double sY;
            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                if(Court.getStatue()){
                    court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                    last = now;
                    if (!(court.getBallSpeedX() != 0)){
                        court.setBallSpeedX(sX);
                        court.setBallSpeedY(sY);
                        court.getPlayerA().setRacketSpeed(racketA.getSpeedStat());
                        court.getPlayerB().setRacketSpeed(racketB.getSpeedStat());
                    }
                    ball.setCenterX(court.getBallX() * scale + xMargin);
                    ball.setCenterY(court.getBallY() * scale);
                    powerBall.setCenterX(court.getBallX() * scale + xMargin);
                    powerBall.setCenterY(court.getBallY() * scale);
                    racketA.setY(racketA.getPosition() * scale);
                    racketB.setY(racketB.getPosition() * scale);
                    }else{
                        //On stock la vitesse de la balle
                        if(court.getBallSpeedX()!=0){
                            sX = court.getBallSpeedX();
                            sY = court.getBallSpeedY();
                            //Pour simuler la pause on met la vitesse de la balle à 0
                            court.setBallSpeedX(0);
                            court.setBallSpeedY(0);
                            court.changeBothRacketSpeed(0);
                        }
                    }
                    if(court.getPlayerA().getPV() <= 0 || (court.getPlayerB().getPV() <= 0)){
                        stop();
                        Court.setAledroit(false);
                        App.enJeu = false;
                        endGame.lunch();
                    }
            }
        };
    }

    // Méthode pour bien mettre à jour l'image de la balle
    public void updateBall(){
        if(Court.powerBall){
            powerBall.setVisible(true);
            ball.setVisible(false);
        }
        else{
            powerBall.setVisible(false);
            ball.setVisible(true);
        }
        ball.setCenterX(court.getBallX() * this.scale + xMargin);
        ball.setCenterY(court.getBallY() * this.scale);
        powerBall.setCenterX(court.getBallX() * this.scale + xMargin);
        powerBall.setCenterY(court.getBallY() * this.scale);
    }

    public void updateTrail(){
        ball.updateTrail();
    }

    public void hideTrail(){
        ball.hideTrail();
    } 

    public void fillTrail(Color c){
        ball.fillTrail(c);
    }

    public void fillTrailMultiple(Color[] colors){
        ball.fillTrailMultiple(colors);
    }

    public Balle getBall(){return ball;}
    public Balle getFireball(){return powerBall;}

    public static void setPseudoA(String text){pseudoA.setText(text.toLowerCase());}
    public static void setPseudoB(String text){pseudoB.setText(text.toLowerCase());}
    public static Racket getRacketA(){return racketA;}
    public static Racket getRacketB(){return racketB;}

    public void racketAura(Player p){
        if(p == court.playerA){
            racketA.setStroke(null);
            switch (p.getPowerBall()) {
                default:
                    racketA.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/fire_aura.png"))));
                    break;
                case GRASS:
                    racketA.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/grass_aura.png"))));
                    break;
                case ELECTRIK:
                    racketA.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/electrik_aura.png"))));
                    break;
                case WATER:
                    racketA.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/water_aura.png"))));
                    break;
            }
        }
        else{
            racketB.setStroke(null);
            switch (p.getPowerBall()) {
                default:
                    racketB.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/fire_aura.png"))));
                    break;
                case GRASS:
                    racketB.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/grass_aura.png"))));
                    break;
                case ELECTRIK:
                    racketB.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/electrik_aura.png"))));
                    break;
                case WATER:
                    racketB.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/water_aura.png"))));
                    break;
            }
        }
    }

    public void fillPowerBall(Player p){
        switch (p.getPowerBall()) {
            default:
                powerBall.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/fire_ball.png"))));
                break;
            case ELECTRIK:
                powerBall.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/electric_ball.png"))));
                break;
            case GRASS:
                powerBall.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/grass_ball.png"))));
                break;
            case WATER:
                powerBall.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("image/water_ball.png"))));
                break;
        }
    }

    public void powerUp(Player p){
        if(p == court.playerA){
            if(court.getPlayerA().isFullPower()){
                racketA.setWidth(racketThickness*2);
                racketA.setX(xMargin - racketThickness*2);
                court.getPlayerA().setPowerUp(true);
                racketAura(court.playerA);
            }
        }
        else{
            if(court.getPlayerB().isFullPower()){
                racketB.setWidth(racketThickness*2);
                court.getPlayerB().setPowerUp(true);
                racketAura(court.playerB);
            }
        }
    }

    public void powerDown(Player p){
        p.setPowerUp(false);
        p.setPower(0);
        App.gameView.hideAura(p);
    }

    public void hideAura(Player p){
        if(p == court.playerA){
            racketA.setWidth(racketThickness);
            racketA.setX(xMargin - racketThickness);
            racketA.setStroke(Color.BLACK);
            racketA.setStrokeWidth(2);
            racketA.setFill(Color.web(Court.getCouleur(Court.getIndiceATDC())));
        }
        else{
            racketB.setWidth(racketThickness);
            racketB.setStroke(Color.BLACK);
            racketB.setStrokeWidth(2);
            racketB.setFill(Color.web(Court.getCouleur(Court.getIndiceBTDC())));
        }
    }

    public void updateGraphiquePause(){
        pause.createPage();
        endGame.createPage();
    }

    public void transition(){
        pause_trois.play();
        pause_deux.play();
        pause_un.play();
    }
    public void compte_a_rebours(){
        Court.setStatue(false);
        Court.setAledroit(false);
        PauseTransition pause = new PauseTransition(Duration.seconds(3.5));
        pause.setOnFinished(event -> {
            trois.setTranslateX(0);
            deux.setTranslateX(0);
            un.setTranslateX(0);
            Court.setStatue(true);
            Court.setAledroit(true);
            trois.setVisible(false);
            deux.setVisible(false);
            un.setVisible(false);
        });
        pause.play();

        double x = 1920;
        double y = 1080;

        trois.setFitWidth(x/3);
        trois.setFitHeight(y/3);
        trois.setLayoutX(-trois.getFitWidth());
        trois.setLayoutY(court.getHeight()/2 - trois.getFitHeight()/2);

        deux.setFitWidth(x/3);
        deux.setFitHeight(y/3);
        deux.setLayoutX(-deux.getFitWidth());
        deux.setLayoutY(court.getHeight()/2 - deux.getFitHeight()/2);

        un.setFitWidth(x/3);
        un.setFitHeight(y/3);
        un.setLayoutX(-un.getFitWidth());
        un.setLayoutY(court.getHeight()/2 - un.getFitHeight()/2);

        trois.setVisible(true);
        deux.setVisible(true);
        un.setVisible(true);

        transition();
    }

    // ATTENTION //
    /*
    Pour les méthodes hidePong() et showPong(), à chaque création d'un objet nouveau, veuillez à le mettre dans ces deux méthodes
    Exemple : Je créer un attribut bouton : "Button bonjour = new Button()",
    il faut mettre :
    dans hidePong() : bonjour.setVisible(false)
    dans showPong() : bonjour.setVisible(true)
    */

    // Méthode pour montrer le pong (à utiliser lorsqu'on lance une partie)
    public void showPong(int pokepongArena){
        racketA.setVisible(true);
        racketB.setVisible(true);
        showPV();
        pseudoA.setVisible(true);
        pseudoB.setVisible(true);
        if(Court.powerBall){
            powerBall.setVisible(true);
            ball.setVisible(false);
            if(Court.WaterBall.isActive){
                Court.WaterBall.showTeleporters();
            }
        }
        else{
            powerBall.setVisible(false);
            ball.setVisible(true);
        }
        if(court.getPlayerA().isPowerUp()){powerUp(court.playerA);}
        if(court.getPlayerB().isPowerUp()){powerUp(court.playerB);}
        ball.showTrail();
        // On met le fond
        switch(pokepongArena){
            default:
                gameRoot.setId("pokepongArena1");//terrain jaune
                if(court.getPlayerB().getIsBot()){
                    racketB.returnToDefaultRacketSettings(this.scale);
                }
                else{
                    racketB.returnToDefaultRacketSettings(this.scale);
                }
                break;
            case 1:
                gameRoot.setId("pokepongArena2");//terrain rouge
                if(court.getPlayerB().getIsBot()){
                    racketB.configurateRacket(this.scale,200.0,-1,Court.getCouleur(Court.getIndiceBTDC()),500);
                }
                else{
                    racketB.returnToDefaultRacketSettings(this.scale);
                }
                break;
            case 2:
                gameRoot.setId("pokepongArena3");//terrain vert
                if(court.getPlayerB().getIsBot()){
                    racketB.configurateRacket(this.scale,400.0,-1,Court.getCouleur(Court.getIndiceBTDC()),400);
                }
                else{
                    racketB.returnToDefaultRacketSettings(this.scale);
                }
                break;
            case 3:
                gameRoot.setId("pokepongArena4");//terrain bleu
                if(court.getPlayerB().getIsBot()){
                    racketB.configurateRacket(this.scale,100.0,-1,Court.getCouleur(Court.getIndiceBTDC()),700);                    
                }
                else{
                    racketB.returnToDefaultRacketSettings(this.scale);
                }
                break;
        }
        gameRoot.getStylesheets().addAll(this.getClass().getResource("css/fond.css").toExternalForm());
    }

    // Méthode pour cacher le pong (à utiliser lorsqu'on revient au menu)
    public void hidePong(){
        racketA.setVisible(false);
        racketB.setVisible(false);
        hidePV();
        pseudoA.setVisible(false);
        pseudoB.setVisible(false);
        ball.setVisible(false);
        powerBall.setVisible(false);
        trois.setVisible(false);
        deux.setVisible(false);
        un.setVisible(false);
        pause.hidePauseButtons();
        endGame.hide();
    }

    public void resumePong(){
        App.enPause = false;
        pause.hidePauseButtons();
        Court.setStatue(true);
        App.configPongKey();
    }

    public void exitPong(){
        hidePong();     // On cache les objets du pong
        powerDown(court.playerA);
        powerDown(court.playerB);
        court.stopPowerBall(court.playerA);     // On annule bien les powershots en cours
        court.stopPowerBall(court.playerB);
        ball.setRds(2);
        powerBall.setRds(2);
        ball.setDuration(ball.getRds());      // On remet la rotation de la balle à sa valeur de base
        powerBall.setDuration(powerBall.getRds());      // On remet la rotation de la balle à sa valeur de base
        stop();         // On stop l'animation
        SoundController.theme("theme_menu");
        App.switchMenu();   // Et on switch vers le menu
        Court.powerBall = false;
        App.enJeu = false;
    }

    public void showPV(){
        jauge_hp_j1.setVisible(true);
        jauge_hp_void_j1.setVisible(true);
        jauge_power_j1.setVisible(true);
        jauge_power_void_j1.setVisible(true);
        cadre_PV_j1.setVisible(true);

        jauge_hp_j2.setVisible(true);
        jauge_hp_void_j2.setVisible(true);
        jauge_power_j2.setVisible(true);
        jauge_power_void_j2.setVisible(true);
        cadre_PV_j2.setVisible(true);
    }

    public void hidePV(){
        jauge_hp_j1.setVisible(false);
        jauge_hp_void_j1.setVisible(false);
        jauge_power_j1.setVisible(false);
        jauge_power_void_j1.setVisible(false);
        cadre_PV_j1.setVisible(false);

        jauge_hp_j2.setVisible(false);
        jauge_hp_void_j2.setVisible(false);
        jauge_power_j2.setVisible(false);
        jauge_power_void_j2.setVisible(false);
        cadre_PV_j2.setVisible(false);
    }

    public void initialisePVBar(){
        
        Image cadre_PV_i = new Image(getClass().getResourceAsStream("image/pv/cadre_pv.png"));
        Image jauge_power_void_i = new Image(getClass().getResourceAsStream("image/pv/jauge_power_void.png"));
        Image jauge_hp_void_i = new Image(getClass().getResourceAsStream("image/pv/jauge_hp_void.png"));


        cadre_PV_j1.setImage(cadre_PV_i);
        cadre_PV_j1.setFitWidth(cadre_PV_i.getWidth()*pv_size_ratio);
        cadre_PV_j1.setFitHeight(cadre_PV_i.getHeight()*pv_size_ratio);
        cadre_PV_j1.setLayoutY(court.getHeight()/10);
        cadre_PV_j1.setLayoutX(pv_placementX);

        jauge_hp_void_j1.setImage(jauge_hp_void_i);
        jauge_hp_void_j1.setFitWidth(jauge_hp_void_i.getWidth()*pv_size_ratio);
        jauge_hp_void_j1.setFitHeight(jauge_hp_void_i.getHeight()*pv_size_ratio);
        jauge_hp_void_j1.setLayoutY(cadre_PV_j1.getLayoutY()+(128*pv_size_ratio));
        jauge_hp_void_j1.setLayoutX(cadre_PV_j1.getLayoutX()+(456*pv_size_ratio));

        jauge_power_void_j1.setImage(jauge_power_void_i);
        jauge_power_void_j1.setFitWidth(jauge_power_void_i.getWidth()*pv_size_ratio);
        jauge_power_void_j1.setFitHeight(jauge_power_void_i.getHeight()*pv_size_ratio);
        jauge_power_void_j1.setLayoutY(cadre_PV_j1.getLayoutY()+(208*pv_size_ratio));
        jauge_power_void_j1.setLayoutX(cadre_PV_j1.getLayoutX()+(456*pv_size_ratio));

        /////

        cadre_PV_j2.setImage(cadre_PV_i);
        cadre_PV_j2.setFitWidth(cadre_PV_i.getWidth()*pv_size_ratio);
        cadre_PV_j2.setFitHeight(cadre_PV_i.getHeight()*pv_size_ratio);
        cadre_PV_j2.setLayoutY(court.getHeight()/10);
        cadre_PV_j2.setLayoutX(court.getWidth()-cadre_PV_j2.getFitWidth()-pv_placementX);
        cadre_PV_j2.setScaleX(-1);

        jauge_hp_void_j2.setImage(jauge_hp_void_i);
        jauge_hp_void_j2.setFitWidth(jauge_hp_void_i.getWidth()*pv_size_ratio);
        jauge_hp_void_j2.setFitHeight(jauge_hp_void_i.getHeight()*pv_size_ratio);
        jauge_hp_void_j2.setLayoutY(cadre_PV_j2.getLayoutY()+(128*pv_size_ratio));
        jauge_hp_void_j2.setLayoutX(court.getWidth()-jauge_hp_void_j2.getFitWidth()-pv_placementX - (456*pv_size_ratio));
        jauge_hp_void_j2.setScaleX(-1);

        jauge_power_void_j2.setImage(jauge_power_void_i);
        jauge_power_void_j2.setFitWidth(jauge_power_void_i.getWidth()*pv_size_ratio);
        jauge_power_void_j2.setFitHeight(jauge_power_void_i.getHeight()*pv_size_ratio);
        jauge_power_void_j2.setLayoutY(cadre_PV_j2.getLayoutY()+(208*pv_size_ratio));
        jauge_power_void_j2.setLayoutX(court.getWidth()-jauge_power_void_j2.getFitWidth()-pv_placementX - (456*pv_size_ratio));
        jauge_power_void_j2.setScaleX(-1);

    }

    public void updatePVbarJ1(Player player){
        Image jauge_hp_i;

        double HPRatio = (double)player.getPV() / (double)player.getPVmax();
        if(HPRatio <= 0.3){jauge_hp_i = new Image(getClass().getResourceAsStream("image/pv/jauge_hp_red.png"));}
        else if(HPRatio <= 0.6){jauge_hp_i = new Image(getClass().getResourceAsStream("image/pv/jauge_hp_yellow.png"));}
        else{jauge_hp_i = new Image(getClass().getResourceAsStream("image/pv/jauge_hp_green.png"));}
        if(HPRatio == 0){jauge_hp_j1.setVisible(false);}
        jauge_hp_j1.setImage(jauge_hp_i);
        jauge_hp_j1.setFitWidth(jauge_hp_i.getWidth()*pv_size_ratio*HPRatio);
        jauge_hp_j1.setFitHeight(jauge_hp_i.getHeight()*pv_size_ratio);
        jauge_hp_j1.setLayoutY(cadre_PV_j1.getLayoutY()+(128*pv_size_ratio));
        jauge_hp_j1.setLayoutX(cadre_PV_j1.getLayoutX()+(456*pv_size_ratio));


        double powerRatio = (double)player.getPower() / (double)player.getPowermax();
        if(powerRatio <= 0.0){jauge_power_j1.setVisible(false);}
        else{jauge_power_j1.setVisible(true);}
        jauge_power_j1.setImage(jauge_power_i);
        jauge_power_j1.setFitWidth(jauge_power_i.getWidth()*pv_size_ratio * powerRatio);
        jauge_power_j1.setFitHeight(jauge_power_i.getHeight()*pv_size_ratio);
        jauge_power_j1.setLayoutY(cadre_PV_j1.getLayoutY()+(208*pv_size_ratio));
        jauge_power_j1.setLayoutX(cadre_PV_j1.getLayoutX()+(456*pv_size_ratio));

        pseudoA.setX(jauge_hp_void_j1.getLayoutX());
        pseudoA.setY(jauge_hp_void_j1.getLayoutY()-(jauge_hp_void_j1.getFitHeight()*2));
    }

    public void updatePVbarJ1(){
        updatePVbarJ1(court.getPlayerA());
    }

    public void updatePVbarJ2(Player player, double pv_placementX){

        Image jauge_hp_i;

        double HPRatio = (double)player.getPV() / (double)player.getPVmax();
        if(HPRatio <= 0.3){jauge_hp_i = new Image(getClass().getResourceAsStream("image/pv/jauge_hp_red.png"));}
        else if(HPRatio <= 0.6){jauge_hp_i = new Image(getClass().getResourceAsStream("image/pv/jauge_hp_yellow.png"));}
        else{jauge_hp_i = new Image(getClass().getResourceAsStream("image/pv/jauge_hp_green.png"));}
        if(HPRatio == 0){jauge_hp_j2.setVisible(false);}
        jauge_hp_j2.setImage(jauge_hp_i);
        jauge_hp_j2.setFitWidth(jauge_hp_i.getWidth()*pv_size_ratio*HPRatio);
        jauge_hp_j2.setFitHeight(jauge_hp_i.getHeight()*pv_size_ratio);
        jauge_hp_j2.setLayoutY(cadre_PV_j2.getLayoutY()+(128*pv_size_ratio));
        jauge_hp_j2.setLayoutX(court.getWidth()-jauge_hp_j2.getFitWidth()-pv_placementX - (456*pv_size_ratio));
        jauge_hp_j2.setScaleX(-1);

        double powerRatio = (double)player.getPower() / (double)player.getPowermax();
        if(powerRatio <= 0.0){jauge_power_j2.setVisible(false);}
        else{jauge_power_j2.setVisible(true);}
        jauge_power_j2.setImage(jauge_power_i);
        jauge_power_j2.setFitWidth(jauge_power_i.getWidth()*pv_size_ratio*powerRatio);
        jauge_power_j2.setFitHeight(jauge_power_i.getHeight()*pv_size_ratio);
        jauge_power_j2.setLayoutY(cadre_PV_j2.getLayoutY()+(208*pv_size_ratio));
        jauge_power_j2.setLayoutX(court.getWidth()-jauge_power_j2.getFitWidth()-pv_placementX - (456*pv_size_ratio));
        jauge_power_j2.setScaleX(-1);

        pseudoB.setX(jauge_hp_void_j2.getLayoutX()+jauge_hp_void_j2.getFitWidth()-pseudoB.getLayoutBounds().getWidth());
        pseudoB.setY(jauge_hp_void_j2.getLayoutY()-(jauge_hp_void_j2.getFitHeight()*2));
    }

    public void updatePowerJauge(){
        double powerRatio_j1 = (double)court.getPlayerA().getPower() / (double)court.getPlayerA().getPowermax();
        if(powerRatio_j1 <= 0.0){jauge_power_j1.setVisible(false);}
        else if(powerRatio_j1 <= 1.0){
            jauge_power_j1.setVisible(true);
            jauge_power_j1.setFitWidth(jauge_power_i.getWidth()*pv_size_ratio * powerRatio_j1);
            jauge_power_j1.setFitHeight(jauge_power_i.getHeight()*pv_size_ratio);
        }
        else{
            jauge_power_j1.setVisible(true);
        }

        double powerRatio_j2 = (double)court.getPlayerB().getPower() / (double)court.getPlayerB().getPowermax();
        if(powerRatio_j2 <= 0.0){jauge_power_j2.setVisible(false);}
        else if(powerRatio_j2 <= 1.0){
            jauge_power_j2.setVisible(true);
            jauge_power_j2.setFitWidth(jauge_power_i.getWidth()*pv_size_ratio * powerRatio_j2);
            jauge_power_j2.setFitHeight(jauge_power_i.getHeight()*pv_size_ratio);
            jauge_power_j2.setLayoutX(court.getWidth()-jauge_power_j2.getFitWidth()- pv_placementX - (456*pv_size_ratio));
        }
        else{
            jauge_power_j2.setVisible(true);
        }
    }

    public void updatePVbarJ2(){
        updatePVbarJ2(court.getPlayerB(),court.getWidth()/25);
    }

    // Méthode pour l'animation du pong
    public void animate(int pokepongArena) {
        
        // On affiche les objets du pong
        showPong(pokepongArena);

        updateGraphiquePause();
        updatePVbarJ1();
        updatePVbarJ2();
        updatePowerJauge();
        updateBall();

        // Et on lance l'animation
        animation.start();
        // On reset le court pour débuter le pong avec l'engagement (et surtout initialiser la vitesse de la balle qui a été freeze dans le stop())
        court.reset();
        // On lance le décompte
        court.decompte();
    }

    // Méthode pour arreter l'animation (utilisé pour le bouton exit qui arrête l'animation du pong)
    public void stop() {
        // Lorsque l'un des joueurs perd, l'animation est stop(), mais à cause de ça, certains attributs (PV par exemple) ne sont pas changés visuellement à temps lorsqu'on relance une partie, donc on anime au cas où.
        animation.stop();
        // On ne fige pas le jeu histoire de pouvoir faire les changements voulus (placer la balle par exemple)
        Court.setStatue(false);
        // On remplace la balle pour la prochaine partie
        court.reset();
        // On soigne les joueurs au maximum pour la prochaine partie
        court.getPlayerA().soignerMax();
        court.getPlayerB().soignerMax();
    }
}
