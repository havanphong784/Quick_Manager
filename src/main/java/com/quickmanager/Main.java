package com.quickmanager;

import com.quickmanager.debug.AppLogger;
import com.quickmanager.ui.Animation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.stage.StageStyle.TRANSPARENT;

public class Main extends Application {
    private static final Logger logger = AppLogger.getLogger(Main.class);

    public static Stage stage;
    public static LocalDateTime time;
    public static Boolean isDarkTheme = true;

    static void main(String[] args) {
        logger.info("App bắt đầu chạy.");
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

    @Override
    public void stop() throws Exception {
        com.quickmanager.config.DBConnection.closePool();
        super.stop();
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
            logger.log(Level.SEVERE, "Lỗi chuyển màn hình: " + path, e);
        }
    }
}
