package com.aleksieienko.water.pipeline.system;

import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class App extends Application
{
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            URL url = new File("src/main/resources/fxml/main_page.fxml").toURI().toURL();
            AnchorPane root = FXMLLoader.load(url);
            Scene scene = new Scene(root, 343, 120);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Water pipeline system");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
