package com.quickmanager.service;

import com.quickmanager.model.TaiKhoan;

public class SeccionService {
    private static TaiKhoan taiKhoan;

    public static TaiKhoan getUser() {
        return taiKhoan;
    }

    public static void setUser(TaiKhoan tk) {
        taiKhoan = tk;
    }

    public static void removeUser() {
        taiKhoan = null;
    }

}
