package gui;

import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundController {

    public static Son son = new Son();
    public static Music music = new Music();

    public static class Son {

        static String[] clicks = {"click.mp4","click1.mp3","click2.mp3","click3.mp3","click4.mp3","click5.mp3","click6.mp3","click7.mp3"};
        static String[] sons = {"goal.mp4","pokechoosed.mp3","rebond.mp4","choice_click.mp3","fireball.mp3","waterball.mp3","grassball.mp3","electricball.mp3"};
        static MediaPlayer [] media_clicks = new MediaPlayer[clicks.length];
        static MediaPlayer [] media_sons = new MediaPlayer[sons.length];

        public Son(){
            // On initialise les clicks
            for(int i = 0; i < media_clicks.length; i++){ 
                Media sound = new Media(this.getClass().getResource("media/"+clicks[i]).toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                media_clicks[i] = mediaPlayer;
            }
            // On initialise les sons
            for(int i = 0; i < media_sons.length; i++){ 
                Media sound = new Media(this.getClass().getResource("media/"+sons[i]).toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                media_sons[i] = mediaPlayer;
            }
        }

        public void mute(){
            for (MediaPlayer m : media_sons) {
                m.setMute(true);
            }
            for (MediaPlayer m : media_clicks) {
                m.setMute(true);
            }
        }
        public void unmute(){
            for (MediaPlayer m : media_sons) {
                m.setMute(false);
            }
            for (MediaPlayer m : media_clicks) {
                m.setMute(false);
            }
        }

        // Retourne true dans le cas ou il trouve le fichier, sinon false
        public boolean joue(String s){
            // On cherche si c'est un click
            for(int i = 0;i<clicks.length;i++){
                if(clicks[i].equals(s+".mp3") || clicks[i].equals(s+".mp4")){
                    media_clicks[i].play();
                    media_clicks[i].seek(media_clicks[i].getStartTime());
                    return true;
                }
            }
            // On cherche si c'est un autre son
            for(int i = 0;i<sons.length;i++){
                if(sons[i].equals(s+".mp3") || sons[i].equals(s+".mp4")){
                    media_sons[i].play();
                    media_sons[i].seek(media_sons[i].getStartTime());
                    return true;
                }
            }
            // On renvoie false si rien trouvé
            return false;
        }

        public void click(){
            Random r = new Random();
            int n = r.nextInt(0,media_clicks.length);
            media_clicks[n].play();
            media_clicks[n].seek(media_clicks[n].getStartTime());
        }
    }

    public static class Music {

        static String[] music = {"theme_menu.mp3","theme_electrik.mp3","theme_fire.mp3","theme_grass.mp3","theme_water.mp3"};
        static MediaPlayer [] media_music = new MediaPlayer[music.length];

        public Music(){
            // On initialise les musiques
            for(int i = 0; i < media_music.length; i++){ 
                Media sound = new Media(this.getClass().getResource("media/"+music[i]).toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                media_music[i] = mediaPlayer;
            }
        }

        public void mute(){
            for (MediaPlayer m : media_music) {
                m.setMute(true);
            }
        }
        public void unmute(){
            for (MediaPlayer m : media_music) {
                m.setMute(false);
            }
        }

        public void stop(){
            for (MediaPlayer m : media_music) {
                m.stop();
            }
        }

        // Retourne true dans le cas ou il trouve le fichier, sinon false
        public boolean joue(String s){
            stop();
            for(int i = 0;i<music.length;i++){
                if(music[i].equals(s+".mp3") || music[i].equals(s+".mp4")){
                    media_music[i].play();
                    media_music[i].seek(media_music[i].getStartTime());
                    return true;
                }
            }
            return false;
        }
    }

    /*
     * Pourquoi se servir d'un string plutot qu'un int ou autre ? :
     * Le soucis des int, c'est que si on décide de retirer et d'ajouter des sons et musiques dans les tableaux, les indices pourraient être changés,
     * et il faudrait reparcourir tout notre code à la main et changer chaque int (trop de travail pour rien)
     * Donc la, on parcourt les deux listes, si le string correspond à un nom d'un de nos fichiers sons, on le joue et on s'arrète.
     * 
     * PS : pas besoin de mettre l'extension dans le string s
     */
    public static void joue(String s){
        son.joue(s);
    }

    public static void theme(String s){
        music.joue(s);
    }
    
    public static void muteAll(){
        muteMusic();
        muteSounds();
    }

    public static void unmuteAll(){
        unmuteMusic();
        unmuteSounds();
    }

    public static void muteMusic(){
        music.mute();
    }
    public static void unmuteMusic(){
        music.unmute();
    }

    public static void muteSounds(){
        son.mute();
    }
    public static void unmuteSounds(){
        son.unmute();
    }

    // Cette méthode sert à choisir un click au hasard
    public static void click(){
        son.click();
    }
}