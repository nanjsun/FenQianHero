package com.fenqian.gui.javafx;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/2
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WorkShop extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("javafx/sample.fxml"));

        primaryStage.setTitle("Fairy Money");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }
}
