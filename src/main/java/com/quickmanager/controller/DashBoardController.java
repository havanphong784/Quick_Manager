package com.quickmanager.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class DashBoardController {
    @FXML
    private AnchorPane contentPane;
    public void setPage(String path) {
        try {
            Parent children = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/"+path)));
            contentPane.getChildren().setAll(children);
        }catch (Exception e){
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
