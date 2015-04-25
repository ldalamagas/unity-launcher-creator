package com.ulc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {

    public static final String APP_NAME = "Unity Launcher Creator";

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final double SCENE_WIDTH = 600;
    private static final double SCENE_HEIGHT = 400;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Starting {}", APP_NAME);
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
