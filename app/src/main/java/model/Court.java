package model;
import java.util.Random;

import gui.*;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.Court.WaterBall.Teleporter;
import pokepong.App;

public class Court {
    // instance parameters
    public final Player playerA;
    public Player playerB;
    private double width, height; // m
    private static final double ballRadius = 10.0; // m
    // instance state
    private static double ballX, ballY; // m
    private static double ballSpeedX; // m
    private static double ballSpeedY;
    public static boolean statue=true;
    private boolean ordi;
    private GameView gameview;
    private static boolean aledroit;
    static long elapsedTime;
    public static boolean isTicking;
    static long startTime = System.currentTimeMillis();
    static long temps = 0 ;
    static boolean change = false;
    static long valeur;
    static long decalage;
    public static boolean powerBall;
    //private static String mode;
    private static String[] tabDeCouleur = new String[6];{
        tabDeCouleur[0]="#FF0000";//Rouge
        tabDeCouleur[1]="#0000FF";//Bleu
        tabDeCouleur[2]="#00FF00";//Vert
        tabDeCouleur[3]="#FFFF00";//Jaune
        tabDeCouleur[4]="#FF00FF";//Violet
        tabDeCouleur[5]="#00FFFF";//Cyan
    }
    private static int indiceATDC = Sauvegarde.getCouleur1();
    private static int indiceBTDC = Sauvegarde.getCouleur2();

    private static int degatsBonus=0;
    private static int nbRebonds=0;



    public Court(Player playerA, Player playerB, double width, double height, boolean ordi) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.width = width;
        this.height = height;
        this.ordi = ordi;
        reset();
    }
    //getter setter
    public static String getCouleur(int i){
        return tabDeCouleur[i];
    }

    public static int getIndiceATDC(){
        return indiceATDC;
    }

    public static int getIndiceBTDC(){
        return indiceBTDC;
    }

    public static void setIndiceATDC(int i){
        if(i<0){indiceATDC=tabDeCouleur.length-1;}
        else if(i>tabDeCouleur.length-1){indiceATDC=0;}
        else{indiceATDC=i;}
    }

    public static void setIndiceBTDC(int i){
        if(i<0){indiceBTDC=tabDeCouleur.length-1;}
        else if(i>tabDeCouleur.length-1){indiceBTDC=0;}
        else{indiceBTDC=i;}
    }

    public static void setAledroit(boolean b){
        aledroit=b;
    }

    public static boolean getAledroit(){
        return aledroit;
    }

    public void setGameView(GameView gameview){
        this.gameview = gameview;
    }

    public void setPlayer2(Player b){
        playerB = b;
    }

    public void setBot(boolean b){
        ordi = b;
    }

    public boolean getBot(){
        return this.ordi;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getBallX() {
        return ballX;
    }
    public double getBallY() {
        return ballY;
    }
    public void setBallSpeedX(double x){
        ballSpeedX=x;
    }
    public void setBallSpeedY(double y){
        ballSpeedY=y;
    }
    public double getBallSpeedX(){
        return ballSpeedX;
    }
    public double getBallSpeedY(){
        return ballSpeedY;
    }

    public Player getPlayerA(){
        return playerA;
    }

    public Player getPlayerB(){
        return playerB;
    }
    public int getScoreA(){
        return playerA.getScore();
    }
    public int getScoreB(){
        return playerB.getScore();
    }
    public static boolean getStatue(){
        return statue;
    }
    public static void setStatue(boolean s){
        statue=s;
    }
    public double getBallRadius() {
        return ballRadius;
    }
    //methodes
    public void update(double deltaT) {
        double bnextBallY = ballY + deltaT * ballSpeedY;
        if(this.ordi){playerB.setIsBot(true);}
        else{playerB.setIsBot(false);}
        playerA.moveRacket(deltaT, height,bnextBallY);
        playerB.moveRacket(deltaT, height, bnextBallY);
        if (updateBall(deltaT)){
            gameview.updateBall();
            reset();
            gameview.hideTrail();
            // On affiche pas le décompte lorsqu'on arrive à la fin de la partie
            if(playerA.getPV() > 0 && playerB.getPV() > 0){
                decompte();
            }
        }
    }

    public void decompte(){
        gameview.compte_a_rebours();
    }

    public void setPowerShotTrailColor(){
        // Ces couleurs seront ceux de base au cas où
        Color c1 = Color.RED;
        Color c2 = Color.ORANGE;
        Color c3 = Color.YELLOW;
        switch (App.pokepongArenaI) {
            case 0:     // ELECTRIK
                c1 = Color.YELLOW;
                c2 = Color.ORANGE;
                c3 = Color.BLACK;
                break;
            case 1:     // FIRE
                c1 = Color.RED;
                c2 = Color.ORANGERED;
                c3 = Color.YELLOW;
                break;
            case 2:     // GRASS
                c1 = Color.GREEN;
                c2 = Color.LIGHTGREEN;
                c3 = Color.LIME;
                break;
            case 3:     // WATER
                c1 = Color.BLUE;
                c2 = Color.LIGHTBLUE;
                c3 = Color.CYAN;
                break;
            default:
                break;
        }
        Color[] colors = {c1,c2,c3};
        gameview.fillTrailMultiple(colors);
    }

    /**
     * @return true if a player lost
     */
    private boolean updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        // next, see if the ball would meet some obstacle
        if(GrassBall.isActive){     // Cas où il y a le pouvoir de traverser les murs active
            if(nextBallY < 0){
                nextBallY = height;
            }
            else if(nextBallY > height){
                nextBallY = 0;
            }
        }
        else{                       // Cas où les pouvoirs ne sont pas activés
            if (nextBallY < 0 || nextBallY > height) {
                ballSpeedY = -ballSpeedY;
                nextBallY = ballY + deltaT * ballSpeedY;
            }
        }

        if(WaterBall.isActive){
            if(Teleporter.intersects(nextBallX, nextBallY, ballRadius) && Teleporter.canTeleport){
                ImageView t = Teleporter.randomTeleporter();
                nextBallX = t.getX() + t.getFitWidth()/2;
                nextBallY = t.getY() + t.getFitHeight()/2;
                Teleporter.canTeleport = false;
            }
            else if(!Teleporter.intersects(nextBallX, nextBallY, ballRadius)){
                Teleporter.canTeleport = true;
            }
        }

        boolean rebondJ1 = (nextBallX < 0 && nextBallY > playerA.getRacketPosition() && nextBallY < playerA.getRacketPosition() + playerA.getRacketSize());
        boolean rebondJ2 = (nextBallX > width-100 && nextBallY > playerB.getRacketPosition()) && nextBallY < playerB.getRacketPosition()+ playerB.getRacketSize();

        if(playerA.getPower() < playerA.getPowermax()){
            playerA.setPower(playerA.getPower()+1);
        }
        if(playerB.getPower() < playerB.getPowermax()){
            playerB.setPower(playerB.getPower()+1);
        }

        // Pour que le bot active son pouvoir automatiquement
        if(playerB.isFullPower() && playerB.getIsBot()){
            gameview.powerUp(playerB);
        }

        gameview.updatePowerJauge();
        gameview.updateTrail();

        if (rebondJ1 || rebondJ2) {
            Random rand= new Random();
            int r = rand.nextInt(5)+1;
            int r2 = rand.nextInt(2);
            //la balle bougeras de facon aleatoire, pour que parfois elle puisse venir tout droit vers nous
            ballSpeedY= r2==0?ballSpeedY+(100*r):ballSpeedY-(100*r);
            if(ballSpeedY>=1000){ballSpeedY-=(100*r);}
            if(ballSpeedY<=-1000){ballSpeedY+=(100*r);}
            ballSpeedX = -(ballSpeedX*1.05);
            nextBallX = ballX + deltaT * ballSpeedX;
           /* gameview.setRds(gameview.getRds()-0.2); //on accelere la rotation de la balle
            gameview.setDurationBall(gameview.getRds()); //on accelere la rotation de la balle*/
            nbRebonds++;
            //Tout les 3 rebonds, il y aura des dégâts en plus
            if (nbRebonds>=3){
                nbRebonds=0;
                if (degatsBonus>=20) degatsBonus=15;
                else degatsBonus+=5;
                // On accelere la rotation de la balle
                gameview.getBall().setRds(gameview.getBall().getRds()-0.4);
                gameview.getFireball().setRds(gameview.getBall().getRds()-0.4);
                gameview.getBall().setDuration(gameview.getBall().getRds());
                gameview.getFireball().setDuration(gameview.getFireball().getRds());
            }

            // Si la balle rebondi sur le J1 et qu'il est powerUp, on active son powershot
            if(rebondJ1 && playerA.isPowerUp()){
                lunchPowerBall(playerA);    // On lance le powershot du J1
                gameview.powerDown(playerA);    
            }
            // Si la balle rebondi sur le J1 et qu'il n'est pas powerUp et qu'il y a un powershot, on stop ce powershot
            else if(rebondJ1 && !playerA.isPowerUp() && powerBall){
                stopPowerBall(playerB);     // On annule le powershot adverse
            }
            // Si la balle rebondi sur le J2 et qu'il est powerUp, on active son powershot
            else if(rebondJ2 && playerB.isPowerUp()){
                lunchPowerBall(playerB);    // On lance le powershot du J2
                gameview.powerDown(playerB);
            }
            // Si la balle rebondi sur le J2 et qu'il n'est pas powerUp et qu'il y a un powershot, on stop ce powershot
            else if(rebondJ2 && !playerB.isPowerUp() && powerBall){
                stopPowerBall(playerA);     // On annule le powershot adverse
            }
            // Pour mettre la balle en feu lorsqu'il faut
            gameview.updateBall();
            if(rebondJ1){
                Teleporter.reverse(false);
                if(powerBall){
                    gameview.fillPowerBall(playerA);
                    setPowerShotTrailColor();
                    powerRebondSound(playerA);
                }
                else{
                    gameview.fillTrail(Color.web(Court.getCouleur(Court.getIndiceATDC())));
                    SoundController.joue("rebond"); // rebond sans fireball
                }
            }
            else{
                Teleporter.reverse(true);
                if(powerBall){
                    gameview.fillPowerBall(playerB);
                    setPowerShotTrailColor();
                    powerRebondSound(playerB);
                }
                else{
                    gameview.fillTrail(Color.web(Court.getCouleur(Court.getIndiceBTDC())));
                    SoundController.joue("rebond"); // rebond sans fireball
                }
            }
        } else if (nextBallX < 0) {
            //score playerB augmente
            playerB.But();
            if (powerBall) degatsBonus+=5;
            if (degatsBonus>playerA.getPV()) degatsBonus=playerA.getPV()-5; //éviter le bug de la jauge de PV à la fin
            playerA.perd(playerB.getAttaque()+degatsBonus);
            gameview.updatePVbarJ1();
            goalSound();
            if(powerBall){
                stopPowerBall(playerB);
            }
            degatsBonus=0;
            nbRebonds=0;
            gameview.getBall().setRds(2);
            gameview.getFireball().setRds(gameview.getBall().getRds());
            gameview.getBall().setDuration(gameview.getBall().getRds());
            gameview.getFireball().setDuration(gameview.getFireball().getRds());
           /* gameview.setRds(2); //pour remettre la rotation de la balle a sa valeur de base
            gameview.setDurationBall(gameview.getRds()); //pour remettre la rotation de la balle a sa valeur de base
            */
            return true;
        } else if (nextBallX > width-100) {
          //score playerA augmente
            playerA.But();
            if (powerBall) degatsBonus+=5; 
            if (degatsBonus>playerB.getPV()) degatsBonus=playerB.getPV()-5; //éviter le bug de la jauge de PV à la fin
            playerB.perd(playerA.getAttaque()+degatsBonus);
            gameview.updatePVbarJ2();
            goalSound();
            if(powerBall){
                stopPowerBall(playerA);
            }
            degatsBonus=0;
            nbRebonds=0;
            gameview.getBall().setRds(2);
            gameview.getFireball().setRds(gameview.getBall().getRds());
            gameview.getBall().setDuration(gameview.getBall().getRds());
            gameview.getFireball().setDuration(gameview.getFireball().getRds());
            /*gameview.setRds(2); //pour remettre la rotation de la balle a sa valeur de base
            gameview.setDurationBall(gameview.getRds()); //pour remettre la rotation de la balle a sa valeur de base
            */
            return true;
        }
        setBallSpeedX(ballSpeedX);
        setBallSpeedY(ballSpeedY);
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
    }

    private static class FireBall {

        static double multiplicateurVitesse = 1.5;

        public static void lunch(){
            ballSpeedX *= multiplicateurVitesse;
            ballSpeedY *= multiplicateurVitesse;
            powerBall = true;
        }
        public static void stop(){
            ballSpeedX /= multiplicateurVitesse;
            ballSpeedY /= multiplicateurVitesse;
            powerBall = false;
        }
    }

    private static class ElectrikBall {

        static double multiplicateurVitesse = 2;
        static AnimationTimer zigzag = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Random r = new Random();
                int n = r.nextInt(0,2);
                if(n == 0){ballSpeedY = -ballSpeedY;}
            }   
        };

        public static void lunch(){
            ballSpeedY *= multiplicateurVitesse;
            zigzag.start();
            powerBall = true;
        }
        public static void stop(){
            ballSpeedY /= multiplicateurVitesse;
            zigzag.stop();
            powerBall = false;
        }
    }

    private static class GrassBall {

        static double multiplicateurVitesse = 1.2;
        static boolean isActive;

        public static void lunch(){
            ballSpeedY *= multiplicateurVitesse;
            isActive = true;
            powerBall = true;
        }
        public static void stop(){
            ballSpeedY /= multiplicateurVitesse;
            isActive = false;
            powerBall = false;
        }
    }

    public static class WaterBall {

        public static class Teleporter {

            static boolean canTeleport;
            static ImageView[] teleporters = new ImageView[3];
            static double zoom = 0.40;
            static double teleporterHeight;
            static double teleporterWidth;
            
            public Teleporter(){
                Image portal = new Image(getClass().getResourceAsStream("image/waterportal.gif"));
                teleporterHeight = portal.getHeight()*zoom;
                teleporterWidth = portal.getWidth()*zoom;
                for (int i = 0; i < teleporters.length; i++) {
                    teleporters[i] = new ImageView();
                    teleporters[i].setFitHeight(teleporterHeight);
                    teleporters[i].setFitWidth(teleporterWidth);
                    teleporters[i].setImage(portal);
                    App.root.getChildren().add(teleporters[i]);
                    teleporters[i].setVisible(false);
                }
            }

            public static void show(){
                for (ImageView t : teleporters) {
                    t.setVisible(true);
                }
            }

            public static void place(){
                teleporters[0].setX(App.court.getWidth()*1/2 - teleporterWidth/2);
                teleporters[0].setY(App.court.getHeight()*1/4 - teleporterHeight/2);
                teleporters[1].setX(App.court.getWidth()*1/2 - teleporterWidth/2);
                teleporters[1].setY(App.court.getHeight()*2/4 - teleporterHeight/2);
                teleporters[2].setX(App.court.getWidth()*1/2 - teleporterWidth/2);
                teleporters[2].setY(App.court.getHeight()*3/4 - teleporterHeight/2);
                show();
            }

            public static void reverse(boolean reverse){
                for (ImageView t : teleporters) {
                    if(!reverse){t.setScaleX(1);}
                    else{t.setScaleX(-1);}
                }
            }

            public static boolean intersects(double x, double y, double radius){
                for (ImageView t : teleporters) {
                    if(t.intersects(x,y,radius,radius)){
                        return true;
                    }
                }
                return false;
            }

            public static ImageView randomTeleporter(){
                Random r = new Random();
                int n = r.nextInt(0,Teleporter.teleporters.length);
                return teleporters[n];
            }

            public static void hide(){
                for (ImageView t : teleporters) {
                    t.setVisible(false);
                }
            }
        }

        static Teleporter teleporter = new Teleporter();
        public static boolean isActive;
        static double multiplicateurVitesse = 1.2;

        public static void lunch(){
            Teleporter.place();
            ballSpeedY *= multiplicateurVitesse;
            isActive = true;
            powerBall = true;
        }

        public static void showTeleporters(){
            Teleporter.show();
        }

        public static void stop(){
            Teleporter.hide();
            ballSpeedY *= multiplicateurVitesse;
            isActive = false;
            powerBall = false;
        }
    }

    public void lunchPowerBall(Player p){
        switch (p.getPowerBall()) {
            default:
                FireBall.lunch();
                break;
            case ELECTRIK:
                ElectrikBall.lunch();
                break;
            case GRASS:
                GrassBall.lunch();
                break;
            case WATER:
                WaterBall.lunch();
                break;
        }
    }

    public void stopPowerBall(Player p){
        switch (p.getPowerBall()) {
            default:
                FireBall.stop();
                break;
            case ELECTRIK:
                ElectrikBall.stop();
                break;
            case GRASS:
                GrassBall.stop();
                break;
            case WATER:
                WaterBall.stop();
                break;
        }
    }

    public void goalSound(){
        if(powerBall){
            // TODO : Jouer le son d'un but critique
            SoundController.joue("goal");
        }
        else{
            // TODO : Jouer le son d'un but normal
            SoundController.joue("goal");
        }
    }

    public void powerRebondSound(Player p){
        switch (p.getPowerBall()) {
            default:
                SoundController.joue("fireball");
                break;
            case ELECTRIK:
                SoundController.joue("electricball");
                break;
            case GRASS:
                SoundController.joue("grassball");
                break;
            case WATER:
                SoundController.joue("waterball");
                break;
        }
    }

    public void reset() {
        playerA.resetRacket(height);
        playerB.resetRacket(height);
        ballSpeedX = playerA.getPV()>playerB.getPV()?500:-500;//on tire la balle vers le perdant, sinon vers le joueur 1
        ballSpeedY = 0;
        ballX = (width / 2) - 50;
        ballY = height / 2;
    }
    public void changeBothRacketSpeed(double speed){
        this.playerA.setRacketSpeed(speed);
        this.playerB.setRacketSpeed(speed);
    }
}
