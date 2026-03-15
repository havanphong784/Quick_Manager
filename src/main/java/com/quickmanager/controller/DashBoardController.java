package com.quickmanager.controller;

import com.quickmanager.Main;
import com.quickmanager.debug.Address;
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
            AnchorPane.setTopAnchor(children, 0.0);
            AnchorPane.setRightAnchor(children, 0.0);
            AnchorPane.setBottomAnchor(children, 0.0);
            AnchorPane.setLeftAnchor(children, 0.0);
        }catch (Exception e){
            System.out.println("Lỗi: " + e.getMessage());
            Address.printAddress();
        }
    }
    public void switchSellPage() {
        setPage("sell.fxml");
    }
}
