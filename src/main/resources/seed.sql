GO
SET NOCOUNT ON;
GO

BEGIN TRY
    BEGIN TRAN;

    DELETE FROM CT_HOA_DON;
    DELETE FROM HOA_DON;
    DELETE FROM KHACH_HANG;
    DELETE FROM CT_PHIEU_NHAP;
    DELETE FROM PHIEU_NHAP;
    DELETE FROM TAI_KHOAN;
    DELETE FROM SAN_PHAM;
    DELETE FROM DANH_MUC;
    DELETE FROM NHA_CUNG_CAP;
    DELETE FROM NHAN_VIEN;

    SET IDENTITY_INSERT NHAN_VIEN ON;
    INSERT INTO NHAN_VIEN (MaNhanVien, TenNhanVien, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, Luong, TrangThai) VALUES
    (1, N'Nguyễn Văn Minh', '1989-04-12', N'Nam', '0912345601', 'minh.nguyen@quickmart.vn', N'Quận 1, TP.HCM',  25000000, N'Đang làm'),
    (2, N'Trần Thị Lan', '1992-09-08', N'Nữ', '0912345602', 'lan.tran@quickmart.vn', N'Quận 3, TP.HCM',  18000000, N'Đang làm'),
    (3, N'Lê Quốc Bảo', '1995-01-20', N'Nam', '0912345603', 'bao.le@quickmart.vn', N'Quận 7, TP.HCM',  14000000, N'Đang làm'),
    (4, N'Phạm Thu Hà', '1998-07-14', N'Nữ', '0912345604', 'ha.pham@quickmart.vn', N'Gò Vấp, TP.HCM',  10500000, N'Đang làm'),
    (5, N'Đỗ Hoàng Nam', '1997-12-03', N'Nam', '0912345605', 'nam.do@quickmart.vn', N'Thủ Đức, TP.HCM',  10300000, N'Đang làm'),
    (6, N'Võ Ngọc Anh', '1999-03-30', N'Nữ', '0912345606', 'anh.vo@quickmart.vn', N'Bình Thạnh, TP.HCM',  9800000, N'Đang làm'),
    (7, N'Bùi Thanh Tùng', '1994-11-05', N'Nam', '0912345607', 'tung.bui@quickmart.vn', N'Tân Bình, TP.HCM',  9900000, N'Nghỉ phép'),
    (8, N'Ngô Thùy Dung', '1996-06-25', N'Nữ', '0912345608', 'dung.ngo@quickmart.vn', N'Quận 10, TP.HCM', 11000000, N'Đang làm'),
    (9, N'Huỳnh Gia Huy', '1991-02-17', N'Nam', '0912345609', 'huy.huynh@quickmart.vn', N'Quận 12, TP.HCM',  9500000, N'Nghỉ việc'),
    (10, N'Linh Old', '1990-10-10', N'Nữ', '0912345610', 'linh.old@quickmart.vn', N'Quận 2, TP.HCM',  8000000, N'Ngừng sử dụng');
    SET IDENTITY_INSERT NHAN_VIEN OFF;

    -- Mật khẩu đã được hash bằng BCrypt (gensalt). Nếu cần thay đổi, chạy utility GenerateHashes.java để tạo hash mới.
    INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai) VALUES
    ('admin',      '$2a$10$afKR7Iaf1NGCK9aRO6MNGeskMCz6pP7754taROT9EGFEY3zkAIMEC', 1, N'ADMIN',    N'Hoạt động'),
    ('ketoan.lan', '$2a$10$7B49LxBTz6o8Yv8PvVoLuONcwF/tIYPrfBDPakY0jJEJnHNSAKjFe', 2, N'KETOAN',   N'Hoạt động'),
    ('thukho.bao', '$2a$10$44YXpuOqyb0iaQMCOeYsMeOZJGeoF2zAWlMsL/irZCNwuSgMX3e4S', 3, N'THUKHO',   N'Hoạt động'),
    ('thungan.ha', '$2a$10$oEsC8O7bJEwyXgIHZRZFAOw.YzmtqK2LyH99JY856v7XglCv8nWGW', 4, N'THUNGAN',  N'Hoạt động'),
    ('thungan.nam','$2a$10$HDeAvysgu1jO.hWx3mrYPe4K4Qq5uojcMfG29NlHENVshSgdAr.cS', 5, N'THUNGAN',  N'Hoạt động'),
    ('banhang.anh','$2a$10$Gf1tq4bn3twAk/.ZkWFiT.7PscYP9QfxVtW37JEHvX3xCz/Q2uYw.', 6, N'BANHANG',  N'Hoạt động'),
    ('banhang.tung','$2a$10$sfQG6Y1ZB/uYtqf1uy6Oaul2HvQojH7rAqh9zeYTAodiuQ1dt0iHq', 7, N'BANHANG',  N'Tạm khóa'),
    ('cskh.dung',  '$2a$10$my9Vime9n6NQ6bh3ejCjX.hZVWvzWm5z5HgepLMCsEpl0hVAlf3Oa', 8, N'CSKH',     N'Hoạt động'),
    ('giamsat.huy','$2a$10$XKTA/F612W3PN7Y3sPTmzunVnLXmfFw7imbGJMywqTj5MSeQM8pgy', 9, N'GIAMSAT',  N'Hoạt động'),
    ('linh.old',   '$2a$10$NIAQF.EYmGdlMAC71QGKm.6ERKsmNJAs8KjHTXzBw/b3Xkh7bhqzG', 10, N'BANHANG',  N'Ngừng sử dụng');

    SET IDENTITY_INSERT DANH_MUC ON;
    INSERT INTO DANH_MUC (MaDanhMuc, TenDanhMuc, MoTa, TrangThai) VALUES
    (1, N'Đồ uống', N'Nước ngọt, nước suối, trà đóng chai', N'Hoạt động'),
    (2, N'Thực phẩm khô', N'Mì gói, gạo, gia vị, đồ hộp', N'Hoạt động'),
    (3, N'Rau củ', N'Rau ăn lá, củ quả tươi', N'Hoạt động'),
    (4, N'Trái cây', N'Trái cây nội địa và nhập khẩu', N'Hoạt động'),
    (5, N'Sữa và chế phẩm', N'Sữa tươi, sữa chua, phô mai', N'Hoạt động'),
    (6, N'Đồ gia dụng', N'Nước rửa chén, giấy vệ sinh, túi rác', N'Hoạt động'),
    (7, N'Chăm sóc cá nhân', N'Sữa tắm, dầu gội, kem đánh răng', N'Hoạt động'),
    (8, N'Đông lạnh', N'Xúc xích, chả giò, hải sản đông lạnh', N'Hoạt động');
    SET IDENTITY_INSERT DANH_MUC OFF;

    SET IDENTITY_INSERT NHA_CUNG_CAP ON;
    INSERT INTO NHA_CUNG_CAP (MaNCC, TenNCC, SoDienThoai, Email, DiaChi, TrangThai) VALUES
    (1, N'Công ty TNHH FMCG Việt', '02873001111', 'sales@fmcgviet.vn', N'KCN Tân Bình, TP.HCM', N'Hợp tác'),
    (2, N'Công ty CP Nông Sản Xanh', '02873002222', 'contact@nongsanxanh.vn', N'Long An', N'Hợp tác'),
    (3, N'FreshFarm Distribution', '02873003333', 'biz@freshfarm.vn', N'Đà Lạt, Lâm Đồng', N'Hợp tác'),
    (4, N'Mega Supplier JSC', '02873004444', 'cs@megasupplier.vn', N'Biên Hòa, Đồng Nai', N'Hợp tác'),
    (5, N'An Phát Consumer', '02873005555', 'partner@anphatconsumer.vn', N'Bình Dương', N'Tạm dừng'),
    (6, N'Vina Cold Chain', '02873006666', 'support@vinacold.vn', N'Củ Chi, TP.HCM', N'Hợp tác');
    SET IDENTITY_INSERT NHA_CUNG_CAP OFF;

    SET IDENTITY_INSERT KHACH_HANG ON;
    INSERT INTO KHACH_HANG (MaKhachHang, TenKhachHang, SoDienThoai, Email, DiaChi, DiemTichLuy, TrangThai) VALUES
    (1, N'Nguyễn Thành Đạt', '0901112201', 'dat.nguyen@gmail.com', N'Quận 1, TP.HCM', 120, N'Hoạt động'),
    (2, N'Trần Bảo Ngọc', '0901112202', 'ngoc.tran@gmail.com', N'Quận 3, TP.HCM', 85, N'Hoạt động'),
    (3, N'Lê Minh Khang', '0901112203', 'khang.le@gmail.com', N'Quận 7, TP.HCM', 40, N'Hoạt động'),
    (4, N'Phạm Hoài Thư', '0901112204', 'thu.pham@gmail.com', N'Thủ Đức, TP.HCM', 200, N'VIP'),
    (5, N'Võ Quốc An', '0901112205', 'an.vo@gmail.com', N'Bình Thạnh, TP.HCM', 15, N'Hoạt động'),
    (6, N'Bùi Gia Hân', '0901112206', 'han.bui@gmail.com', N'Gò Vấp, TP.HCM', 60, N'Hoạt động'),
    (7, N'Ngô Quỳnh Mai', '0901112207', 'mai.ngo@gmail.com', N'Quận 10, TP.HCM', 30, N'Hoạt động'),
    (8, N'Đặng Hải Nam', '0901112208', 'nam.dang@gmail.com', N'Tân Bình, TP.HCM', 0, N'Mới'),
    (9, N'Huỳnh Yến Nhi', '0901112209', 'nhi.huynh@gmail.com', N'Bình Tân, TP.HCM', 150, N'VIP'),
    (10, N'Phan Đức Long', '0901112210', 'long.phan@gmail.com', N'Nhà Bè, TP.HCM', 10, N'Hoạt động');
    SET IDENTITY_INSERT KHACH_HANG OFF;

    SET IDENTITY_INSERT SAN_PHAM ON;
    INSERT INTO SAN_PHAM (MaSanPham, TenSanPham, MaDanhMuc, GiaNhap, GiaBan, SoLuongTon, DonViTinh, NgaySanXuat, HanSuDung, TrangThai) VALUES
    (1, N'Nước suối Lavie 500ml', 1, 8500, 12000, 280, N'Chai', '2025-12-01', '2027-12-01', N'Đang bán'),
    (2, N'Coca Cola lon 330ml', 1, 12000, 16500, 240, N'Lon', '2025-11-20', '2026-11-20', N'Đang bán'),
    (3, N'Pepsi lon 330ml', 1, 14000, 19000, 180, N'Lon', '2025-10-15', '2026-10-15', N'Đang bán'),
    (4, N'Trà xanh C2 455ml', 1, 26000, 36000, 95, N'Chai', '2025-09-01', '2026-09-01', N'Đang bán'),
    (5, N'Sữa tươi Vinamilk 1L', 5, 28000, 39000, 75, N'Hộp', '2026-01-05', '2026-07-05', N'Đang bán'),
    (6, N'Sữa chua uống Probi', 5, 23000, 32000, 110, N'Lốc', '2025-12-10', '2026-05-10', N'Đang bán'),
    (7, N'Sữa chua có đường 4 hộp', 5, 19000, 28000, 140, N'Lốc', '2026-01-12', '2026-04-12', N'Đang bán'),
    (8, N'Phô mai lát 8 miếng', 5, 15000, 22000, 60, N'Gói', '2025-11-01', '2026-04-30', N'Đang bán'),
    (9, N'Mì Hảo Hảo tôm chua cay', 2, 15000, 23000, 420, N'Gói', '2025-10-01', '2026-10-01', N'Đang bán'),
    (10, N'Gạo ST25 túi 5kg', 2, 13000, 20000, 160, N'Kg', '2025-12-15', '2027-12-15', N'Đang bán'),
    (11, N'Dầu ăn Tường An 1L', 2, 18000, 27000, 130, N'Chai', '2025-08-01', '2027-08-01', N'Đang bán'),
    (12, N'Nước mắm 500ml', 2, 22000, 32000, 90, N'Chai', '2025-07-10', '2027-07-10', N'Đang bán'),
    (13, N'Táo Gala nhập khẩu', 4, 30000, 45000, 85, N'Kg', '2026-02-01', '2026-04-15', N'Đang bán'),
    (14, N'Chuối già Nam Mỹ', 4, 5000, 9000, 70, N'Kg', '2026-03-01', '2026-03-25', N'Đang bán'),
    (15, N'Cà rốt Đà Lạt', 3, 7000, 12000, 65, N'Kg', '2026-03-01', '2026-03-20', N'Đang bán'),
    (16, N'Khoai tây', 3, 9000, 15000, 95, N'Kg', '2026-02-20', '2026-03-30', N'Đang bán'),
    (17, N'Rau cải xanh', 3, 22000, 34000, 40, N'Kg', '2026-03-10', '2026-03-17', N'Đang bán'),
    (18, N'Cà chua', 3, 35000, 52000, 25, N'Kg', '2026-03-12', '2026-03-18', N'Đang bán'),
    (19, N'Dầu gội Sunsilk 650g', 7, 48000, 69000, 70, N'Chai', '2025-06-01', '2028-06-01', N'Đang bán'),
    (20, N'Sữa tắm Lifebuoy 850g', 7, 27000, 39000, 55, N'Chai', '2025-05-20', '2027-05-20', N'Đang bán'),
    (21, N'Kem đánh răng P/S 180g', 7, 45000, 69000, 100, N'Tuýp', '2025-04-15', '2028-04-15', N'Đang bán'),
    (22, N'Nước rửa chén Sunlight 3.6kg', 6, 32000, 49000, 45, N'Can', '2025-09-30', '2028-09-30', N'Đang bán'),
    (23, N'Giấy vệ sinh Bless You 10 cuộn', 6, 55000, 79000, 60, N'Lốc', '2025-10-20', '2028-10-20', N'Đang bán'),
    (24, N'Xúc xích tiệt trùng 500g', 8, 68000, 99000, 35, N'Gói', '2025-12-01', '2026-05-01', N'Đang bán');
    SET IDENTITY_INSERT SAN_PHAM OFF;

    UPDATE SAN_PHAM SET TrangThai = N'Ngừng kinh doanh' WHERE MaSanPham = 8;   -- phô mai
    UPDATE SAN_PHAM SET SoLuongTon = 0, TrangThai = N'Hết hàng' WHERE MaSanPham = 18; -- cà chua
    UPDATE SAN_PHAM SET HanSuDung = '2026-02-28', TrangThai = N'Hết hạn' WHERE MaSanPham = 17; -- rau cải xanh

    SET IDENTITY_INSERT PHIEU_NHAP ON;
    INSERT INTO PHIEU_NHAP (MaPhieuNhap, MaNhanVien, MaNCC, NgayNhap, TongTien, TrangThai) VALUES
    (1, 3, 1, '2026-01-10 08:45:00', 3210000, N'Đã nhập'),
    (2, 3, 1, '2026-01-15 09:30:00', 4680000, N'Đã nhập'),
    (3, 3, 3, '2026-02-01 07:55:00', 6340000, N'Đã nhập'),
    (4, 9, 3, '2026-02-18 10:20:00', 7220000, N'Đã nhập'),
    (5, 9, 4, '2026-02-25 14:00:00', 10680000, N'Đã nhập'),
    (6, 3, 2, '2026-03-01 08:10:00', 6310000, N'Đã nhập'),
    (7, 3, 2, '2026-03-05 08:35:00', 3640000, N'Đã nhập'),
    (8, 9, 6, '2026-03-08 15:20:00', 5780000, N'Đã nhập');
    SET IDENTITY_INSERT PHIEU_NHAP OFF;

    INSERT INTO CT_PHIEU_NHAP (MaPhieuNhap, MaSanPham, SoLuong, GiaNhap, ThanhTien) VALUES
    (1, 1, 100, 8500, 850000),
    (1, 2, 80, 12000, 960000),
    (1, 5, 50, 28000, 1400000),

    (2, 9, 100, 15000, 1500000),
    (2,10, 120, 13000, 1560000),
    (2,11, 90, 18000, 1620000),

    (3,13, 150, 30000, 4500000),
    (3,14, 200, 5000, 1000000),
    (3,15, 120, 7000, 840000),

    (4,17, 80, 22000, 1760000),
    (4,18, 60, 35000, 2100000),
    (4,19, 70, 48000, 3360000),

    (5,21, 100, 45000, 4500000),
    (5,22, 90, 32000, 2880000),
    (5,23, 60, 55000, 3300000),

    (6, 6, 120, 23000, 2760000),
    (6, 7, 100, 19000, 1900000),
    (6, 8, 110, 15000, 1650000),

    (7, 3, 70, 14000, 980000),
    (7, 4, 60, 26000, 1560000),
    (7,12, 50, 22000, 1100000),

    (8,16, 100, 9000, 900000),
    (8,20, 80, 27000, 2160000),
    (8,24, 40, 68000, 2720000);

    SET IDENTITY_INSERT HOA_DON ON;
    INSERT INTO HOA_DON (MaHoaDon, MaNhanVien, MaKhachHang, NgayLap, TongTien, TienKhachDua, TienThoi, TrangThai) VALUES
    (1, 4, 1, '2026-03-10 08:15:00', 108000, 200000, 92000, N'Đã thanh toán'),
    (2, 4, 2, '2026-03-10 09:05:00', 103000, 200000, 97000, N'Đã thanh toán'),
    (3, 5, NULL, '2026-03-10 10:20:00', 93000, 100000, 7000, N'Đã thanh toán'),
    (4, 5, 3, '2026-03-10 11:40:00', 86000, 100000, 14000, N'Đã thanh toán'),
    (5, 4, 4, '2026-03-10 14:30:00', 197000, 200000, 3000, N'Đã thanh toán'),
    (6, 5, 5, '2026-03-10 16:10:00', 159000, 200000, 41000, N'Đã thanh toán'),
    (7, 4, NULL, '2026-03-11 08:45:00', 173000, 200000, 27000, N'Đã thanh toán'),
    (8, 5, 6, '2026-03-11 09:55:00', 111000, 120000, 9000, N'Đã thanh toán'),
    (9, 4, 7, '2026-03-11 12:15:00', 109000, 110000, 1000, N'Đã thanh toán'),
    (10, 5, 8, '2026-03-11 15:50:00', 298500, 300000, 1500, N'Đã thanh toán'),
    (11, 4, NULL, '2026-03-12 08:10:00', 173000, 200000, 27000, N'Đã thanh toán'),
    (12, 5, 9, '2026-03-12 10:25:00', 170000, 200000, 30000, N'Đã thanh toán'),
    (13, 4, NULL, '2026-03-12 11:30:00', 85000, NULL, NULL, N'Đã hủy'),
    (14, 5, 10, '2026-03-12 17:20:00', 178000, NULL, NULL, N'Chờ thanh toán'),
    (15, 4, 2, '2026-03-13 18:05:00', 493000, 500000, 7000, N'Đã thanh toán');
    SET IDENTITY_INSERT HOA_DON OFF;

    INSERT INTO CT_HOA_DON (MaHoaDon, MaSanPham, SoLuong, DonGia, ThanhTien) VALUES
    (1, 1, 4, 12000, 48000), (1, 2, 2, 16500, 33000), (1,14, 3, 9000, 27000),
    (2, 5, 1, 39000, 39000), (2, 6, 2, 32000, 64000),
    (3, 9, 2, 23000, 46000), (3,10, 1, 20000, 20000), (3,11, 1, 27000, 27000),
    (4,17, 1, 34000, 34000), (4,18, 1, 52000, 52000),
    (5,21, 1, 69000, 69000), (5,22, 1, 49000, 49000), (5,23, 1, 79000, 79000),
    (6,13, 2, 45000, 90000), (6,15, 2, 12000, 24000), (6,16, 3, 15000, 45000),
    (7, 3, 2, 19000, 38000), (7, 4, 1, 36000, 36000), (7,24, 1, 99000, 99000),
    (8, 7, 1, 28000, 28000), (8, 8, 2, 22000, 44000), (8,20, 1, 39000, 39000),
    (9,12, 2, 32000, 64000), (9,14, 5, 9000, 45000),
    (10,1,10,12000,120000), (10,2,5,16500,82500), (10,6,3,32000,96000),
    (11,18,2,52000,104000), (11,19,1,69000,69000),
    (12,5,2,39000,78000), (12,9,1,23000,23000), (12,21,1,69000,69000),
    (13,10,2,20000,40000), (13,16,3,15000,45000),
    (14,23,1,79000,79000), (14,24,1,99000,99000),
    (15,1,6,12000,72000), (15,3,4,19000,76000), (15,5,3,39000,117000), (15,13,2,45000,90000), (15,21,2,69000,138000);

    COMMIT TRAN;
    PRINT N'Seed dữ liệu thành công.';
END TRY
BEGIN CATCH
    IF @@TRANCOUNT > 0 ROLLBACK TRAN;
    PRINT N'Seed thất bại: ' + ERROR_MESSAGE();
END CATCH;
GO