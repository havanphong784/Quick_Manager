package com.quickmanager.controller;

import com.quickmanager.Main;
import com.quickmanager.debug.Address;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewManager {
    private static final Map<String, Parent> viewCache = new HashMap<>();

    public static void show(AnchorPane rootPane, String title, String path) {
        Parent view = viewCache.get(title);
        if (view == null) {
            try {
                view = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/view/" + path)));
                viewCache.put(title, view);
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
                Address.printAddress();
                return;
            }
        }
        rootPane.getChildren().setAll(view);
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
    }

    public static void clearCache() {
        viewCache.clear();
    }

}
