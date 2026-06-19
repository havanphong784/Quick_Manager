package com.quickmanager.controller;

import com.quickmanager.debug.AppLogger;
import java.util.logging.Logger;
import java.util.logging.Level;

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
    private static final Logger logger = AppLogger.getLogger(LoginController.class);

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
    private void handleRegister() {
        Main.switchParent("/view/register.fxml", "Dang ky - QuickManager", false);
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        statusLabel.setStyle("-fx-text-fill: red;");
        statusLabel.setText("");
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Vui lòng nhập đầy đủ thông tin.");
            logger.info("Nhap thiếu thông tin.");
            Address.printAddress();
            return;
        }

        TaiKhoan tk = AuthService.login(username, password);
        if (tk == null) {
            statusLabel.setText("Sai mật khẩu.");
            logger.info("Sai mật khẩu");
            Address.printAddress();
            return;
        }
        logger.info("Đăng nhập thành công.\n");
        SessionService.setUser(tk);
        statusLabel.setStyle("-fx-text-fill: green;");
        statusLabel.setText("Đăng nhập thành công. Xin chào " + tk.getTenDangNhap());
        Main.switchParent("/view/dashboard.fxml","Dashboard",true);
    }
}
