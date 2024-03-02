package gui;
import model.RacketController;
import model.Racket;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

//import javafx.scene.text.Text;
public class Player implements RacketController{
    private String pseudo = "";
    private int score;
    private int attaque;

    // Représentation de la vie courante et vie maximale
    private int pv;
    private int pv_max;

    // Représentation du montant de power et power maximal
    private int power;
    // PS : + power_max sera élevé, + elle la jauge de power mettra du temps à se recharger
    private int power_max;
    // Boolean qui servira à savoir si le powerUp est activé sur le joueur
    private boolean powerUp;

    public State state = State.IDLE;
    private Racket racket;
    private boolean isBot;
    public PowerBall powerBall = PowerBall.ELECTRIK;
    public double innerWidth = 200, outerWidth = 240;
    //private Pouvoir pouvoir 
    // du coup, peut être créer une classe qui permetterais de faire differents pouvoirs

    public Player(){
        this.isBot=false;
        score = 0;
        attaque = 5; // par défault, on mettra 1 points d'attaque
        pv_max = 50;     // par défault, on mettra 5 PV
        pv = pv_max;
        power_max = 10;
        power = 0;
        powerUp = false;
        racket=new Racket();
    }
    public Player(boolean isBot){
      this();
      this.isBot=isBot;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public PowerBall getPowerBall(){
        return powerBall;
    }

    public void setPseudo(String s){
        pseudo = s;
    }

    public String getPseudo(){
        return pseudo;
    }
    
    public void addAttaque(){
        attaque =+ 1; //pourra être utilisé lors de la création des pouvoirs, pour l'instant rajoute 1 PA
    }

    public void But(){
        score++;
    }

    public void perd(int att_adv){ // att_adv correspondra à l'attaque de l'adversaire
        pv = Math.max(pv - att_adv,0);
        // Lorsque le joueur perd un point, sa barre de power augmente de moitié
        power += power_max/2;
        if(power > power_max){power = power_max;}
    }

    public int getAttaque(){
        return attaque;
    }

    public int getScore(){
        return score;
    }

    public int getPV(){
        return pv;
    }

    public void setPV(int pv){
        this.pv = pv;
    }

    public int getPVmax(){
        return pv_max;
    }

    public void setPVmax(int pv_max){
        this.pv_max = pv_max;
    }

    public int getPower(){
        return power;
    }

    public void setPower(int power){
        this.power = power;
    }

    public int getPowermax(){
        return power_max;
    }

    public void setPowermax(int power_max){
        this.power_max = power_max;
    }

    public void soignerMax(){  
        pv = pv_max;
    }

    public void fullPower(){
        power = power_max;
    }

    public boolean isFullPower(){
        return power >= power_max;
    }

    public void setPowerUp(boolean b){
        powerUp = b;
    }

    public boolean isPowerUp(){
        return powerUp;
    }

    public static void clignotte(Text a){
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.05), evt -> {
            a.setVisible(true);
            a.setFill(Color.RED);
            }),
            new KeyFrame(Duration.seconds(0.2), evt -> {
                a.setVisible(true);
                a.setFill(Color.WHITESMOKE);
            })
        );
        timeline.setCycleCount(5);
        timeline.play();
        timeline.setOnFinished(evt -> a.setFill(Color.BLACK));
    }

    public void moveRacket(double deltaT,double height,double bnextBallY ){
        if(isBot){
            int rPlayerO=0;
            //monter
            if(bnextBallY<(racket.getPosition()+((2* racket.getSize())/15))){rPlayerO=1;}
            //descendre
            else if(bnextBallY>(racket.getPosition()+((13* racket.getSize()/15)))) rPlayerO = 2;
            //dans intervalle donc raquette pas bouger
            else{rPlayerO=0;}
            switch (rPlayerO) {
                case 1:
                    racket.setPosition(racket.getPosition()-racket.getSpeed()* deltaT);
                    if (racket.getPosition()< 0.0) racket.setPosition(0.0);
                    break;
                case 2:
                    racket.setPosition(racket.getPosition()+racket.getSpeed()* deltaT);
                    if (racket.getPosition() + racket.getSize() > height) racket.setPosition(height - racket.getSize());
                    break;
            }
        }else {
            switch (getState()) {
                case GOING_UP:
                    racket.setPosition(racket.getPosition() - racket.getSpeed() * deltaT);
                    if (racket.getPosition() < 0.0) racket.setPosition(0.0);
                    break;
                case IDLE:
                    break;
                case GOING_DOWN:
                    racket.setPosition(racket.getPosition() + racket.getSpeed() * deltaT);
                    if (racket.getPosition() + racket.getSize() > height) racket.setPosition(height - racket.getSize());
                    break;
            }
        }
    }
    public void resetRacket(double height){
        racket.setPosition(height/2-(racket.getSize()/2));
    }
    public double getRacketSize(){
        return this.racket.getSize();
    }
    public double getRacketPosition(){
        return this.racket.getPosition();
    }
    public double getRacketSpeed(){
        return this.racket.getSpeed();
    }
    public void setRacketSpeed(double speed){
        this.racket.setSpeed(speed);
    }
    public void setIsBot(boolean bot){
        this.isBot=bot;
    }
    public boolean getIsBot(){
       return  this.isBot;
    }

    public Racket getRacket() {
        return racket;
    }
}

