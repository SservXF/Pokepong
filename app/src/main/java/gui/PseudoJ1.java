package gui;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pokepong.App;

public class PseudoJ1 extends Page{
    TextField PseudoJ1 = new TextField("Pseudo");
    Text texte1 = new Text("Choisissez votre pseudo");
    ImageView retour = new ImageView();
    ImageView fleche = new ImageView();
    ImageView start_game = new ImageView();
    ImageView leave = new ImageView();
    int choiceInt = 0;
    ImageView[]choix = {start_game,leave};

    public PseudoJ1(Pane root, Scene scene, GameView gameview, double zoom) {
        super(root, scene, gameview, zoom);

        Node[] objects = 
        {
            PseudoJ1,texte1,retour,fleche,start_game,leave
        };
        this.allObjects = objects;
        start_game.setVisible(false);
        createPage();

        // On ajoute tous les objets dans le root
        addChildrens();
    }

    @Override
    public void createPage() {
        root.setId("menu");
        scene.getStylesheets().addAll(this.getClass().getResource("css/fond.css").toExternalForm());
        Font pixel = Font.loadFont(getClass().getResourceAsStream("font/PixelFont-YS.ttf"), 50);
        PseudoJ1.setVisible(true);
        PseudoJ1.fontProperty().set(pixel);
        PseudoJ1.setLayoutX((screen.getWidth()-PseudoJ1.getWidth())/2);
        PseudoJ1.setLayoutY(root.getHeight()/2);
        PseudoJ1.setFocusTraversable(false);
        PseudoJ1.requestFocus();
        PseudoJ1.setOnAction((event) -> {
            App.court.playerA.setPseudo(PseudoJ1.getText());
            choiceInt = 2;
            start_game.requestFocus();
            placeSelector();
        });
        texte1.setVisible(true);
        texte1.fontProperty().set(pixel);
        texte1.setLayoutX(PseudoJ1.getLayoutX());
        texte1.setLayoutY((root.getHeight()-PseudoJ1.getHeight())/2-50);

        Image retour_i = new Image(getClass().getResourceAsStream("image/key/esc.png"));
        retour.setImage(retour_i);
        retour.setVisible(true);
        retour.setLayoutX(0);
        retour.setLayoutX(0);

        Image fleche_i = new Image(getClass().getResourceAsStream("bouton/select.png"));
        fleche.setImage(fleche_i);
        fleche.setFitWidth(fleche_i.getWidth()*zoomText);
        fleche.setFitHeight(fleche_i.getHeight()*zoomText);

        Image start_game_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/start_game.png"));
        start_game.setImage(start_game_i);
        start_game.setFitWidth(start_game_i.getWidth()*zoomText);
        start_game.setFitHeight(start_game_i.getHeight()*zoomText);
        start_game.setLayoutX((root.getWidth()/2) - start_game.getFitWidth()/2);
        start_game.setLayoutY((root.getHeight()) - start_game.getFitHeight()*4);

        Image leave_i = new Image(getClass().getResourceAsStream("bouton/boutons_"+App.suff+"/return.png"));
        leave.setImage(leave_i);
        leave.setFitWidth(leave_i.getWidth()*zoomText);
        leave.setFitHeight(leave_i.getHeight()*zoomText);
        leave.setLayoutX((root.getWidth()/2) - leave.getFitWidth()/2);
        leave.setLayoutY(start_game.getLayoutY()+start_game.getFitHeight()+50);;

        mouvement();
        choiceInt = 0;
        placeSelector();
    }

    private void mouvement(){
        scene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                default: break;
                case ESCAPE:
                    App.switchMenu();
                    break;
                case ENTER:
                if(choiceInt == 2){
                    App.switchRules();
                }
                else{
                    App.switchMenu();
                }
                    break;
                case UP:
                    goUp();
                    break;
                case DOWN:
                    goDown();
                    break;
            }
        });
    }

    public void placeSelector(){
        if(choiceInt == 0){
            fleche.setLayoutX(PseudoJ1.getLayoutX()-50);
            fleche.setLayoutY(PseudoJ1.getLayoutY() +  25 );
            PseudoJ1.requestFocus();
        }
        if(choiceInt == 2){
            fleche.setLayoutX(start_game.getLayoutX()-50);
            fleche.setLayoutY(start_game.getLayoutY());
        }
        if(choiceInt == 3){
            fleche.setLayoutX(leave.getLayoutX()-50);
            fleche.setLayoutY(leave.getLayoutY());
            leave.requestFocus();
        }
    }
    private void goDown(){
        if(choiceInt == 3){
            choiceInt = 2;
        }else{
            choiceInt = 3;
        }
        placeSelector();
    }
    private void goUp(){
        if(choiceInt == 3){
            choiceInt = 2;
        }else{
            choiceInt = 3;
        }
        placeSelector();
    }

    
    @Override
    public void lunch() {
        createPage();
        show();
    }

    

    
}
