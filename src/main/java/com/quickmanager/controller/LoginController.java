package com.quickmanager.controller;

import com.quickmanager.Main;
import com.quickmanager.debug.Address;
import com.quickmanager.model.TaiKhoan;
import com.quickmanager.service.AuthService;
import com.quickmanager.service.SessionService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        statusLabel.setStyle("-fx-text-fill: red;");
        statusLabel.setText("");
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Vui lòng nhập đầy đủ thông tin.");
            System.out.println("Nhap thiếu thông tin.");
            Address.printAddress();
            return;
        }

        TaiKhoan tk = AuthService.login(username, password);
        if (tk == null) {
            statusLabel.setText("Sai mật khẩu hoặc tên đăng nhập.");
            System.out.println("Sai mật khẩu hoặc tên đăng nhập.");
            Address.printAddress();
            return;
        }
        System.out.println("Đăng nhập thành công.\n");
        SessionService.setUser(tk);
        statusLabel.setStyle("-fx-text-fill: green;");
        statusLabel.setText("Đăng nhập thành công. Xin chào " + tk.getTenDangNhap());
        Main.switchParent("/view/dashboard.fxml","Dashboard",true);
    }
}
