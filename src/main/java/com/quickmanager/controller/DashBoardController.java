package com.quickmanager.controller;

import com.quickmanager.Main;
import com.quickmanager.debug.Address;
import com.quickmanager.model.TaiKhoan;
import com.quickmanager.service.SessionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

import static com.quickmanager.Main.isDarkTheme;
import static com.quickmanager.controller.ViewManager.show;

public class DashBoardController {
    @FXML
    VBox menu;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private Label accountLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private ToggleButton btnMenuHome;
    @FXML
    private ToggleButton btnMenuSell;
    @FXML
    private ToggleButton btnMenuImport;
    @FXML
    private ToggleButton btnMenuInvoice;
    @FXML
    private ToggleButton btnMenuProduct;
    @FXML
    private ToggleButton btnMenuEmployee;
    @FXML
    private ToggleButton btnMenuStatistics;
    @FXML
    private HBox menuFooter;
    @FXML
    private Region menuRegion;

    private double dragOffsetX;
    private double dragOffsetY;

    @FXML
    public void initialize() {
        TaiKhoan tk = SessionService.getUser();
        if (tk != null) {
            handleRole(tk);
            accountLabel.setText(tk.getTenDangNhap());
            roleLabel.setText(tk.getVaiTro());
        }
        show(contentPane, "home", "home.fxml");
    }

    public void setPage(String path) {
        try {
            Parent children = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/" + path)));
            contentPane.getChildren().setAll(children);
            AnchorPane.setTopAnchor(children, 0.0);
            AnchorPane.setRightAnchor(children, 0.0);
            AnchorPane.setBottomAnchor(children, 0.0);
            AnchorPane.setLeftAnchor(children, 0.0);
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            Address.printAddress();
            e.printStackTrace();
        }
    }

    public void switchHomePage() {
        show(contentPane, "home", "home.fxml");
    }

    public void switchSellPage() {
        show(contentPane, "sell", "sell.fxml");
    }

    public void switchImportPage() {
        show(contentPane, "import", "import.fxml");
    }

    public void switchInvoicePage() {
        show(contentPane, "invoice", "invoice.fxml");
    }

    public void switchProductPage() {
        show(contentPane, "product", "product.fxml");
    }

    public void switchEmployeePage() {
        show(contentPane, "employee", "employee.fxml");
    }

    public void switchStatisticsPage() {
        show(contentPane, "statistics", "statistics.fxml");
    }

    public void handleLogout() {
        Main.switchParent("/view/login.fxml", "Login - QuickManager", false);
        SessionService.removeUser();
    }

    public void handleSwitchTheme() {
        isDarkTheme = !isDarkTheme;
        Main.stage.getScene().getStylesheets().clear();
        Main.stage.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource(isDarkTheme ? "/view/index.css" : "/view/light.css")).toExternalForm());
    }

    public void handleRole(TaiKhoan tk) {
        menu.getChildren().clear();
        if (tk.getVaiTro().equals("ADMIN")) {
            menu.getChildren().add(btnMenuHome);
            menu.getChildren().add(btnMenuSell);
            menu.getChildren().add(btnMenuImport);
            menu.getChildren().add(btnMenuInvoice);
            menu.getChildren().add(btnMenuProduct);
            menu.getChildren().add(btnMenuEmployee);
            menu.getChildren().add(btnMenuStatistics);
        } else {
            menu.getChildren().add(btnMenuHome);
            menu.getChildren().add(btnMenuSell);
            menu.getChildren().add(btnMenuImport);
        }
        menu.getChildren().add(menuRegion);
        menu.getChildren().add(menuFooter);

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
