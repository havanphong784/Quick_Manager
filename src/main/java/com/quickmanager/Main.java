package com.quickmanager;

import com.quickmanager.debug.Address;
import com.quickmanager.ui.Animation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Objects;

import static javafx.stage.StageStyle.TRANSPARENT;

public class Main extends Application {
    public static Stage stage;
    public static LocalDateTime time;
    public static Boolean isDarkTheme = true;

    static void main(String[] args) {
        System.out.println("App bắt đầu chạy.\n");
        Application.launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        switchParent("/view/login.fxml", "Login - QuickManager", false);
        stage.setTitle("Quick Manager");
        stage.setMaxWidth(Double.MAX_VALUE);
        stage.setMaxHeight(Double.MAX_VALUE);
        stage.initStyle(TRANSPARENT);
        stage.show();
    }

    public static void switchParent(String path, String title, Boolean reSize) {
        try {
            Parent p = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(path)));
            Scene scene = new Scene(p);
            String stylesheet = isDarkTheme ? "/view/index.css" : "/view/light.css";
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(stylesheet)).toExternalForm());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.setResizable(reSize);
            Platform.runLater(() -> {
                Animation.phongTo(scene.getRoot());
            });
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            Address.printAddress();
        }
    }
}
