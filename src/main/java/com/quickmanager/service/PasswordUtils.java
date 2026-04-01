package com.quickmanager.service;

import org.springframework.security.crypto.bcrypt.BCrypt;

public final class PasswordUtils {
    private PasswordUtils() {}

    public static String hash(String mkNhap) {
        return BCrypt.hashpw(mkNhap, BCrypt.gensalt());
    }

    public static boolean ktraHash(String mkNhap, String mkLuu) {
        if (mkNhap == null || mkLuu == null) return false;
        return BCrypt.checkpw(mkNhap, mkLuu);
    }

}
