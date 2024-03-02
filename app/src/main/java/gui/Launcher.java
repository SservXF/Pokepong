package gui;

import javafx.application.Platform;
import pokepong.App;

public class Launcher{

    public static void main(String[] args) {
        Platform.startup(() ->{});
        App.main(args);
    }
    /*Normalement il suffit de faire
     * ./gradlew clean
     * ./gradlew build
     * ./gradlew createExe
     * pour faire apparaître le launcher, et le jar aussi du coup.
     */
}
