package com.quickmanager.debug;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashMK {
    public static void main(String[] args) {
        String[] passwords = {
                "Admin@123",
                "Lan@123",
                "Bao@123",
                "Ha@123",
                "Nam@123",
                "Anh@123",
                "Tung@123",
                "Dung@123",
                "Huy@123",
                "Linh@123"
        };
        for (String pwd : passwords) {
            String hash = BCrypt.hashpw(pwd, BCrypt.gensalt());
            System.out.println(hash);
        }
    }
}
