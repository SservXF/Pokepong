package gui;
import java.util.LinkedList;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.animation.RotateTransition;
import javafx.util.Duration;
import pokepong.App;
public class Balle extends Circle{

    private double rds; //rotation duration seconds, initié à 2 secondes
    private double departX;
    private double departY;

    private RotateTransition rotation_ball;
    private LinkedList<Circle> trail = new LinkedList<Circle>();
    private LinkedList<LinkedList<Circle>> midtrails = new LinkedList<LinkedList<Circle>>();
    private Color defaultTrail = Color.WHITESMOKE;


    public Balle(double rad, double cX, double cY, String remp){
        super();
        this.setRadius(rad);
        this.setCenterX(cX);
        this.setCenterY(cY);
        departX = cX;
        departY = cY;
        Image ball_i = new Image(getClass().getResourceAsStream(remp));
        this.setFill(new ImagePattern(ball_i));
        rds=2;
        rotation_ball = new RotateTransition(Duration.seconds(rds),this);
        rotation_ball.setFromAngle(0);
        rotation_ball.setByAngle(360);
        rotation_ball.setCycleCount(Animation.INDEFINITE);
        rotation_ball.setInterpolator(Interpolator.LINEAR);
        rotation_ball.play();
        createTrail(20);
        createMidTrail();
    }

    public Balle(double rad, double cX, double cY){
        super();
        this.setRadius(rad);
        this.setCenterX(cX);
        this.setCenterY(cY);
        departX = cX;
        departY = cY;
        rds=2;
        rotation_ball = new RotateTransition(Duration.seconds(rds),this);
        rotation_ball.setFromAngle(0);
        rotation_ball.setByAngle(360);
        rotation_ball.setCycleCount(Animation.INDEFINITE);
        rotation_ball.setInterpolator(Interpolator.LINEAR);
        rotation_ball.play();
        createTrail(20);
        createMidTrail();
    }
    
    public double getRds(){return rds;}
    public void setRds(double x){if(x<=0.2){rds=0.2;}else{rds=x;}}
    public void setDuration(double x){rotation_ball.stop();rotation_ball.setDuration(Duration.seconds(x));rotation_ball.play();}

    public void createTrail(int n){
        for (int i = 0; i < n; i++) {
            Circle c = new Circle(this.getCenterX(), this.getCenterY(), this.getRadius());
            c.setFill(defaultTrail);
            trail.addFirst(c);
            App.root.getChildren().add(c);
        }
    }

    private void createMidTrail(){
        for (int i = 0; i < 3; i++) {
            LinkedList<Circle> midTrail1 = new LinkedList<Circle>();
            for (int j = 0; j < trail.size()-1; j++) {
                Circle c1 = new Circle(this.getCenterX(), this.getCenterY(), this.getRadius());
                c1.setFill(defaultTrail);
                midTrail1.addFirst(c1);
                App.root.getChildren().add(c1);               
            }
            midtrails.add(midTrail1);
        }
    }

    public void loopTrail(){
        Circle last = trail.removeLast();
        trail.addFirst(last);
        last.setRadius(this.getRadius());
        last.setCenterX(this.getCenterX());
        last.setCenterY(this.getCenterY());
        last.setVisible(true);
    }

    private static void placeBetween(Circle c, Circle a, Circle b){
        c.setRadius((a.getRadius() + b.getRadius())/2);
        c.setCenterX((a.getCenterX() + b.getCenterX())/2);
        c.setCenterY((a.getCenterY() + b.getCenterY())/2);
    }

    private void updateMidTrail(){
        for (int i = 0; i < trail.size()-1; i++) {
            Circle m = midtrails.get(0).get(i);
            placeBetween(m, trail.get(i),trail.get(i+1));
            m.setVisible(true);
        }
        for (int i = 0; i < trail.size()-1; i++) {
            Circle m1 = midtrails.get(1).get(i);
            placeBetween(m1, trail.get(i),midtrails.get(0).get(i));
            m1.setVisible(true);

            Circle m2 = midtrails.get(2).get(i);
            placeBetween(m2, midtrails.get(0).get(i),trail.get(i+1));
            m2.setVisible(true);
        }
    }

    public void updateTrail(){
        double index = trail.size();
        double size = trail.size();

        loopTrail();
        for (int i = 1; i < size; i++) {
            Circle c = trail.get(i);
            c.setRadius(this.getRadius() * (index/size));
            index--;
        }
        updateMidTrail();
    }

    public void hideTrail(){
        for (Circle c : trail) {
            c.setVisible(false);
            c.setCenterX(departX);
            c.setCenterY(departY);
            c.setFill(defaultTrail);
        }
        for (LinkedList<Circle> list : midtrails) {
            for (Circle c : list) {
                c.setVisible(false);
                c.setCenterX(departX);
                c.setCenterY(departY);
                c.setFill(defaultTrail);
            }
        }
    }

    public void showTrail(){
        for (Circle c : trail) {
            c.setVisible(true);
        }
        for (LinkedList<Circle> list : midtrails) {
            for (Circle c : list) {
                c.setVisible(true);
            }
        }
    }

    public void fillTrail(Color color){
        for (Circle c : trail) {
            c.setFill(color);
        }
        for (LinkedList<Circle> list : midtrails) {
            for (Circle c : list) {
                c.setFill(color);
            }
        }
    }

    public void fillTrailMultiple(Color[] colors){
        int numberColors = colors.length;
        int division = trail.size()/numberColors;
        int i = 0;
        int choice = 0;
        for (Circle circle : trail) {
            if(i > division){
                choice++;
                i = 0;
            }
            circle.setFill(colors[choice]);
            i++;
        }
        for (LinkedList<Circle> list : midtrails) {
            int j = 0;
            int choice2 = 0;
            for (Circle circle : list) {
                if(j > division){
                    choice2++;
                    j = 0;
                }
                circle.setFill(colors[choice2]);
                j++;
            }
        }
    }
}
