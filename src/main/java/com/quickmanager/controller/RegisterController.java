package com.quickmanager.controller;

import com.quickmanager.Main;
import com.quickmanager.model.NhanVien;
import com.quickmanager.service.AuthService;
import com.quickmanager.service.EmployeeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private ComboBox<NhanVien> cbNhanVien;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        cbNhanVien.setItems(FXCollections.observableArrayList(EmployeeService.getNhanVienDangLam()));
        cbNhanVien.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(NhanVien item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getMaNhanVien() + " - " + item.getTenNhanVien());
            }
        });
        cbNhanVien.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(NhanVien item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getMaNhanVien() + " - " + item.getTenNhanVien());
            }
        });
    }

    @FXML
    private void handleBackToLogin() {
        Main.switchParent("/view/login.fxml", "Login - QuickManager", false);
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();
        String confirm = confirmPasswordField.getText() == null ? "" : confirmPasswordField.getText();
        NhanVien selected = cbNhanVien.getValue();

        statusLabel.setStyle("-fx-text-fill: red;");
        statusLabel.setText("");

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            statusLabel.setText("Vui long nhap day du thong tin.");
            return;
        }
        if (selected == null) {
            statusLabel.setText("Vui long chon nhan vien.");
            return;
        }
        if (!password.equals(confirm)) {
            statusLabel.setText("Mat khau xac nhan khong trung nhau.");
            return;
        }
        if (AuthService.isUsernameExists(username)) {
            statusLabel.setText("Ten dang nhap da ton tai.");
            return;
        }

        boolean ok = AuthService.register(username, password, selected.getMaNhanVien());
        if (!ok) {
            statusLabel.setText("Dang ky that bai. Vui long thu lai.");
            return;
        }

        statusLabel.setStyle("-fx-text-fill: green;");
        statusLabel.setText("Dang ky thanh cong.");
        Main.switchParent("/view/login.fxml", "Login - QuickManager", false);
    }
}

