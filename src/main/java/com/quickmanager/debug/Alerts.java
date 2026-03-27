package com.quickmanager.debug;

import javafx.scene.control.Alert;

public class Alerts {

    public static void thongBao(String title,String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(title);
        a.setContentText(content);
        a.showAndWait();
    }
}
