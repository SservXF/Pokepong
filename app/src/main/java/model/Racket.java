package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class Racket extends Rectangle{
    private double speed; // m/s
    private double size;
    private double position;
    private double thickness;
    private double speedStat;
    public Racket(){
        speed=600;
        speedStat = 600;
        size=150.0;
        thickness=10;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeedStat(){
        return this.speedStat;
    }

    public void getSpeedStat(double speed){
        this.speedStat = speed;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
    public void configurateRacket(double gameviewScale,double racketHeight, double racketThickness,String racketFill,double racketSpeed){
        /* on vérifie que les valeurs entées sont valides, c'est surtout pour permettre
           de pas avoir à tout changer à chaque fois qu'on veut modifier un attribut
           mettre un nombre négatif ou une chaine vide pour pas modifier un attribut
        */
        if (racketHeight >= 0){
            this.size=racketHeight;
            this.setHeight(size*gameviewScale);
        }
        if(racketThickness>=0) {
            this.thickness=racketThickness;
            this.setWidth(racketThickness);
        }
        if(racketSpeed>=0){
            this.speed=racketSpeed;
            this.speedStat = racketSpeed;
        }
        if(racketFill.length()>0)this.setFill(Color.web(racketFill));

    }
    public void returnToDefaultRacketSettings(double gameviewScale){
        speed=600;
        speedStat = 600;
        size=150.0;
        thickness=10;
        this.setHeight(size*gameviewScale);
        this.setWidth(thickness);
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness=thickness;
    }
}
