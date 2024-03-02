package gui;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import pokepong.App;

public abstract class Page {

    protected Pane root;
    protected Scene scene;
    protected GameView gameview;
    protected double zoom;
    public Node[] allObjects;
    protected Rectangle2D screen = App.screen; // Attribut pour dimension de l'écran du joueur

    protected static double zoomText = 0.3;
    
    public Page(Pane root, Scene scene, GameView gameview, double zoom){
        this.root = root;
        this.scene = scene;
        this.gameview = gameview;
        this.zoom = zoom;
    }

    public abstract void createPage();

    public void lunch(){
        createPage();
        show();
    }
    
    protected void addChildrens(){
        for (Node node : allObjects) {
            root.getChildren().add(node);
        }
    }

    protected void show(){
        List<Node> obj = Arrays.asList(this.allObjects);
        for(Node o : root.getChildren()){
            if(obj.contains(o)){
                o.setVisible(true);
            }
            else{
                o.setVisible(false);
            }
        }
    }

}
