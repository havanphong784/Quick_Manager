package com.quickmanager.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

public class MailConfig {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = MailConfig.class.getClassLoader().getResourceAsStream("mail.properties")) {
            if (input != null) {
                PROPERTIES.load(input);
            }
        } catch (Exception e) {
            System.out.println("Khong the doc cau hinh mail: " + e.getMessage());
        }
    }

    private MailConfig() {
    }

    public static boolean isEnabled() {
        return getBoolean("mail.enabled", "MAIL_ENABLED", false);
    }

    public static String getHost() {
        return get("mail.smtp.host", "MAIL_SMTP_HOST", "smtp.gmail.com");
    }

    public static int getPort() {
        String value = get("mail.smtp.port", "MAIL_SMTP_PORT", "587");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 587;
        }
    }

    public static boolean isAuth() {
        return getBoolean("mail.smtp.auth", "MAIL_SMTP_AUTH", true);
    }

    public static boolean isStartTls() {
        return getBoolean("mail.smtp.starttls.enable", "MAIL_SMTP_STARTTLS", true);
    }

    public static String getAuthCode() {
        return get("mail.auth.code", "MAIL_AUTH_CODE", "");
    }

    public static String getUsername() {
        Credential credential = resolveCredentialFromCode();
        if (credential != null) {
            return credential.username();
        }
        return get("mail.username", "MAIL_USERNAME", "");
    }

    public static String getPassword() {
        Credential credential = resolveCredentialFromCode();
        if (credential != null) {
            return credential.password();
        }
        return get("mail.password", "MAIL_PASSWORD", "");
    }

    public static String getFromAddress() {
        return get("mail.from", "MAIL_FROM", getUsername());
    }

    public static String getFromName() {
        return get("mail.from.name", "MAIL_FROM_NAME", "Quick Manager");
    }

    private static String get(String key, String envKey, String defaultValue) {
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue.trim();
        }

        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value.trim();
    }

    private static boolean getBoolean(String key, String envKey, boolean defaultValue) {
        String value = get(key, envKey, String.valueOf(defaultValue));
        return Boolean.parseBoolean(value);
    }

    private static Credential resolveCredentialFromCode() {
        String authCode = getAuthCode();
        if (authCode == null || authCode.isBlank()) {
            return null;
        }

        try {
            String decoded = new String(Base64.getDecoder().decode(authCode), StandardCharsets.UTF_8);
            int splitIndex = decoded.indexOf(':');
            if (splitIndex <= 0 || splitIndex >= decoded.length() - 1) {
                System.out.println("Mail auth code khong dung dinh dang. Dung base64 cua username:password.");
                return null;
            }

            String username = decoded.substring(0, splitIndex).trim();
            String password = decoded.substring(splitIndex + 1).trim();
            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Mail auth code khong hop le vi thieu username hoac password.");
                return null;
            }
            return new Credential(username, password);
        } catch (IllegalArgumentException e) {
            System.out.println("Mail auth code khong phai base64 hop le: " + e.getMessage());
            return null;
        }
    }

    private record Credential(String username, String password) {
    }
}


