package com.quickmanager.controller;

import com.quickmanager.debug.Address;
import com.quickmanager.model.TaiKhoan;
import com.quickmanager.service.SessionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

import java.util.Objects;

public class DashBoardController {
    @FXML
    private AnchorPane contentPane;
    
    @FXML
    private Label accountLabel;
    
    @FXML
    private Label roleLabel;

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
}
