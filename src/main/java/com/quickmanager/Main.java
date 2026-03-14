package com.quickmanager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
    public Stage stage;
    public Scene loginScene;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;


        Scene loginScene = new Scene(
                FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")))
        );
        stage.setScene(loginScene);
        stage.setTitle("Quick Manager");
        stage.setMaxWidth(Double.MAX_VALUE);
        stage.setMaxHeight(Double.MAX_VALUE);
        stage.show();
    }
}
