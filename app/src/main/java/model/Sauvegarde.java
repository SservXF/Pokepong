package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import gui.Settings;
import pokepong.App;

public class Sauvegarde {

    static Properties sauvegarde = new Properties();
    

    public static void sauvegardeData(){
        sauvegarde.setProperty("Racket1", Court.getIndiceATDC()+"");
        sauvegarde.setProperty("Racket2", Court.getIndiceBTDC()+"");
        sauvegarde.setProperty("langage", App.suff);
        sauvegarde.setProperty("langageInt", Settings.languageInt+"");
        sauvegarde.setProperty("sons", Settings.sound_muted+"");
        sauvegarde.setProperty("musiques", Settings.music_muted+"");
        sauvegarde.setProperty("theme", Settings.themeInt+"");
        try {
            sauvegarde.storeToXML(new FileOutputStream("settings.xml"), "");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Load Settings
        try {
            sauvegarde.loadFromXML(new FileInputStream("settings.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static int getCouleur1(){
        if(!fileFound())return 0;
        try {
            /*Lit le ficher contenu dans Pokémong -> gui  */
           sauvegarde.loadFromXML(new FileInputStream("settings.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Integer.parseInt(sauvegarde.getProperty("Racket1"));
    }

    public static int getCouleur2(){
        if(!fileFound())return 1;
        try {
           sauvegarde.loadFromXML(new FileInputStream("settings.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Integer.parseInt(sauvegarde.getProperty("Racket2"));
    }

    public static String getLangue(){
        if(!fileFound())return "EN";
        try {
           sauvegarde.loadFromXML(new FileInputStream("settings.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sauvegarde.getProperty("langage");
    }

    public static int getLangueInt(){
        if(!fileFound())return 0;
        try {
           sauvegarde.loadFromXML(new FileInputStream("settings.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Integer.parseInt(sauvegarde.getProperty("langageInt"));
    }

    public static boolean getSon(){
        if(!fileFound())return true;
        try {
           sauvegarde.loadFromXML(new FileInputStream("settings.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Boolean.parseBoolean(sauvegarde.getProperty("sons"));
    }

    public static boolean getMusique(){
        if(!fileFound())return true;
        try {
           sauvegarde.loadFromXML(new FileInputStream("settings.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Boolean.parseBoolean(sauvegarde.getProperty("musiques"));
    }

    public static int getTheme(){
        if(!fileFound())return 0;
        try {
           sauvegarde.loadFromXML(new FileInputStream("settings.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Integer.parseInt(sauvegarde.getProperty("theme"));
    }

    public static boolean fileFound(){
        File f = new File("settings.xml");
        return f.exists();
    }



}
