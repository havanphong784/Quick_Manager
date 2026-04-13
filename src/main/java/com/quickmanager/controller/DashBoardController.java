package com.quickmanager.controller;

import com.quickmanager.Main;
import com.quickmanager.debug.Address;
import com.quickmanager.model.TaiKhoan;
import com.quickmanager.service.SessionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;

public class DashBoardController {
    @FXML
    private AnchorPane contentPane;
    
    @FXML
    private Label accountLabel;
    
    @FXML
    private Label roleLabel;

    private double dragOffsetX;
    private double dragOffsetY;

    @FXML
    public void initialize() {
        TaiKhoan tk = SessionService.getUser();
        accountLabel.setText(tk.getTenDangNhap());
        roleLabel.setText(tk.getVaiTro());
        setPage("home.fxml");
    }

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
    public void switchHomePage() { setPage("home.fxml"); }
    public void switchSellPage() {setPage("sell.fxml");}
    public void switchImportPage() { setPage("import.fxml"); }
    public void switchInvoicePage() { setPage("invoice.fxml"); }
    public void switchProductPage() { setPage("product.fxml"); }
    public void switchEmployeePage() { setPage("employee.fxml"); }
    public void switchStatisticsPage() { setPage("statistics.fxml"); }

    public void handleLogout() {
        Main.switchParent("/view/login.fxml","Login - QuickManager",false);
        SessionService.removeUser();
    }

    @FXML
    public void handleMinimize() {
        Stage stage = Main.stage;
        if (stage != null) {
            stage.setIconified(true);
        }
    }

    @FXML
    public void handleMaximize() {
        Stage stage = Main.stage;
        if (stage != null) {
            stage.setMaximized(!stage.isMaximized());
        }
    }

    @FXML
    public void handleClose() {
        Stage stage = Main.stage;
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    public void handleHeaderPressed(MouseEvent event) {
        Stage stage = Main.stage;
        if (stage != null) {
            dragOffsetX = event.getSceneX();
            dragOffsetY = event.getSceneY();
        }
    }

    @FXML
    public void handleHeaderDragged(MouseEvent event) {
        Stage stage = Main.stage;
        if (stage != null && !stage.isMaximized()) {
            stage.setX(event.getScreenX() - dragOffsetX);
            stage.setY(event.getScreenY() - dragOffsetY);
        }
    }

}
