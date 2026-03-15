USE QL_SIEU_THI;
GO
SET NOCOUNT ON;
GO

BEGIN TRY
    BEGIN TRAN;

    -- 1) Clear old data (child -> parent)
    DELETE FROM CT_HOA_DON;
    DELETE FROM HOA_DON;
    DELETE FROM CT_PHIEU_NHAP;
    DELETE FROM PHIEU_NHAP;
    DELETE FROM TAI_KHOAN;
    DELETE FROM SAN_PHAM;
    DELETE FROM DANH_MUC;
    DELETE FROM NHA_CUNG_CAP;
    DELETE FROM NHAN_VIEN;

    -- 2) Reset identity
    DBCC CHECKIDENT ('NHAN_VIEN', RESEED, 0);
    DBCC CHECKIDENT ('DANH_MUC', RESEED, 0);
    DBCC CHECKIDENT ('SAN_PHAM', RESEED, 0);
    DBCC CHECKIDENT ('NHA_CUNG_CAP', RESEED, 0);
    DBCC CHECKIDENT ('PHIEU_NHAP', RESEED, 0);
    DBCC CHECKIDENT ('HOA_DON', RESEED, 0);

    -- =========================
    -- NHAN_VIEN (10)
    -- =========================
    INSERT INTO NHAN_VIEN (TenNhanVien, NgaySinh, GioiTinh, SoDienThoai, Email, DiaChi, ChucVu, Luong, TrangThai) VALUES
    (N'Nguyễn Văn Minh', '1989-04-12', N'Nam', '0912345601', 'minh.nguyen@quickmart.vn', N'Quận 1, TP.HCM', N'Quản lý', 25000000, N'Đang làm'),
    (N'Trần Thị Lan', '1992-09-08', N'Nữ', '0912345602', 'lan.tran@quickmart.vn', N'Quận 3, TP.HCM', N'Kế toán', 18000000, N'Đang làm'),
    (N'Lê Quốc Bảo', '1995-01-20', N'Nam', '0912345603', 'bao.le@quickmart.vn', N'Quận 7, TP.HCM', N'Thủ kho', 14000000, N'Đang làm'),
    (N'Phạm Thu Hà', '1998-07-14', N'Nữ', '0912345604', 'ha.pham@quickmart.vn', N'Gò Vấp, TP.HCM', N'Thu ngân', 10500000, N'Đang làm'),
    (N'Đỗ Hoàng Nam', '1997-12-03', N'Nam', '0912345605', 'nam.do@quickmart.vn', N'Thủ Đức, TP.HCM', N'Thu ngân', 10300000, N'Đang làm'),
    (N'Võ Ngọc Anh', '1999-03-30', N'Nữ', '0912345606', 'anh.vo@quickmart.vn', N'Bình Thạnh, TP.HCM', N'Bán hàng', 9800000, N'Đang làm'),
    (N'Bùi Thanh Tùng', '1994-11-05', N'Nam', '0912345607', 'tung.bui@quickmart.vn', N'Tân Bình, TP.HCM', N'Bán hàng', 9900000, N'Nghỉ phép'),
    (N'Ngô Thùy Dung', '1996-06-25', N'Nữ', '0912345608', 'dung.ngo@quickmart.vn', N'Quận 10, TP.HCM', N'CSKH', 11000000, N'Đang làm'),
    (N'Huỳnh Gia Huy', '1991-02-17', N'Nam', '0912345609', 'huy.huynh@quickmart.vn', N'Quận 12, TP.HCM', N'Giám sát', 17000000, N'Đang làm'),
    (N'Phan Mỹ Linh', '1993-08-19', N'Nữ', '0912345610', 'linh.phan@quickmart.vn', N'Bình Tân, TP.HCM', N'Bán hàng', 9500000, N'Nghỉ việc');

    -- =========================
    -- TAI_KHOAN (10)
    -- =========================
    INSERT INTO TAI_KHOAN (TenDangNhap, MatKhau, MaNhanVien, VaiTro, TrangThai) VALUES
    ('admin',      'Admin@123', 1, N'ADMIN',    N'Hoạt động'),
    ('ketoan.lan', 'Lan@123',   2, N'KETOAN',   N'Hoạt động'),
    ('thukho.bao', 'Bao@123',   3, N'THUKHO',   N'Hoạt động'),
    ('thungan.ha', 'Ha@123',    4, N'THUNGAN',  N'Hoạt động'),
    ('thungan.nam','Nam@123',   5, N'THUNGAN',  N'Hoạt động'),
    ('banhang.anh','Anh@123',   6, N'BANHANG',  N'Hoạt động'),
    ('banhang.tung','Tung@123', 7, N'BANHANG',  N'Tạm khóa'),
    ('cskh.dung',  'Dung@123',  8, N'CSKH',     N'Hoạt động'),
    ('giamsat.huy','Huy@123',   9, N'GIAMSAT',  N'Hoạt động'),
    ('linh.old',   'Linh@123', 10, N'BANHANG',  N'Ngừng sử dụng');

    -- =========================
    -- DANH_MUC (8)
    -- =========================
    INSERT INTO DANH_MUC (TenDanhMuc, MoTa, TrangThai) VALUES
    (N'Đồ uống', N'Nước ngọt, nước suối, trà đóng chai', N'Hoạt động'),
    (N'Thực phẩm khô', N'Mì gói, gạo, gia vị, đồ hộp', N'Hoạt động'),
    (N'Rau củ', N'Rau ăn lá, củ quả tươi', N'Hoạt động'),
    (N'Trái cây', N'Trái cây nội địa và nhập khẩu', N'Hoạt động'),
    (N'Sữa và chế phẩm', N'Sữa tươi, sữa chua, phô mai', N'Hoạt động'),
    (N'Đồ gia dụng', N'Nước rửa chén, giấy vệ sinh, túi rác', N'Hoạt động'),
    (N'Chăm sóc cá nhân', N'Sữa tắm, dầu gội, kem đánh răng', N'Hoạt động'),
    (N'Đông lạnh', N'Xúc xích, chả giò, hải sản đông lạnh', N'Hoạt động');

    -- =========================
    -- NHA_CUNG_CAP (6)
    -- =========================
    INSERT INTO NHA_CUNG_CAP (TenNCC, SoDienThoai, Email, DiaChi, TrangThai) VALUES
    (N'Công ty TNHH FMCG Việt', '02873001111', 'sales@fmcgviet.vn', N'KCN Tân Bình, TP.HCM', N'Hợp tác'),
    (N'Công ty CP Nông Sản Xanh', '02873002222', 'contact@nongsanxanh.vn', N'Long An', N'Hợp tác'),
    (N'FreshFarm Distribution', '02873003333', 'biz@freshfarm.vn', N'Đà Lạt, Lâm Đồng', N'Hợp tác'),
    (N'Mega Supplier JSC', '02873004444', 'cs@megasupplier.vn', N'Biên Hòa, Đồng Nai', N'Hợp tác'),
    (N'An Phát Consumer', '02873005555', 'partner@anphatconsumer.vn', N'Bình Dương', N'Tạm dừng'),
    (N'Vina Cold Chain', '02873006666', 'support@vinacold.vn', N'Củ Chi, TP.HCM', N'Hợp tác');

    -- =========================
    -- SAN_PHAM (24)
    -- =========================
    INSERT INTO SAN_PHAM (TenSanPham, MaDanhMuc, GiaNhap, GiaBan, SoLuongTon, DonViTinh, NgaySanXuat, HanSuDung, TrangThai) VALUES
    (N'Nước suối Lavie 500ml', 1, 8500, 12000, 280, N'Chai', '2025-12-01', '2027-12-01', N'Đang bán'),
    (N'Coca Cola lon 330ml', 1, 12000, 16500, 240, N'Lon', '2025-11-20', '2026-11-20', N'Đang bán'),
    (N'Pepsi lon 330ml', 1, 14000, 19000, 180, N'Lon', '2025-10-15', '2026-10-15', N'Đang bán'),
    (N'Trà xanh C2 455ml', 1, 26000, 36000, 95, N'Chai', '2025-09-01', '2026-09-01', N'Đang bán'),
    (N'Sữa tươi Vinamilk 1L', 5, 28000, 39000, 75, N'Hộp', '2026-01-05', '2026-07-05', N'Đang bán'),
    (N'Sữa chua uống Probi', 5, 23000, 32000, 110, N'Lốc', '2025-12-10', '2026-05-10', N'Đang bán'),
    (N'Sữa chua có đường 4 hộp', 5, 19000, 28000, 140, N'Lốc', '2026-01-12', '2026-04-12', N'Đang bán'),
    (N'Phô mai lát 8 miếng', 5, 15000, 22000, 60, N'Gói', '2025-11-01', '2026-04-30', N'Đang bán'),
    (N'Mì Hảo Hảo tôm chua cay', 2, 15000, 23000, 420, N'Gói', '2025-10-01', '2026-10-01', N'Đang bán'),
    (N'Gạo ST25 túi 5kg', 2, 13000, 20000, 160, N'Kg', '2025-12-15', '2027-12-15', N'Đang bán'),
    (N'Dầu ăn Tường An 1L', 2, 18000, 27000, 130, N'Chai', '2025-08-01', '2027-08-01', N'Đang bán'),
    (N'Nước mắm 500ml', 2, 22000, 32000, 90, N'Chai', '2025-07-10', '2027-07-10', N'Đang bán'),
    (N'Táo Gala nhập khẩu', 4, 30000, 45000, 85, N'Kg', '2026-02-01', '2026-04-15', N'Đang bán'),
    (N'Chuối già Nam Mỹ', 4, 5000, 9000, 70, N'Kg', '2026-03-01', '2026-03-25', N'Đang bán'),
    (N'Cà rốt Đà Lạt', 3, 7000, 12000, 65, N'Kg', '2026-03-01', '2026-03-20', N'Đang bán'),
    (N'Khoai tây', 3, 9000, 15000, 95, N'Kg', '2026-02-20', '2026-03-30', N'Đang bán'),
    (N'Rau cải xanh', 3, 22000, 34000, 40, N'Kg', '2026-03-10', '2026-03-17', N'Đang bán'),
    (N'Cà chua', 3, 35000, 52000, 25, N'Kg', '2026-03-12', '2026-03-18', N'Đang bán'),
    (N'Dầu gội Sunsilk 650g', 7, 48000, 69000, 70, N'Chai', '2025-06-01', '2028-06-01', N'Đang bán'),
    (N'Sữa tắm Lifebuoy 850g', 7, 27000, 39000, 55, N'Chai', '2025-05-20', '2027-05-20', N'Đang bán'),
    (N'Kem đánh răng P/S 180g', 7, 45000, 69000, 100, N'Tuýp', '2025-04-15', '2028-04-15', N'Đang bán'),
    (N'Nước rửa chén Sunlight 3.6kg', 6, 32000, 49000, 45, N'Can', '2025-09-30', '2028-09-30', N'Đang bán'),
    (N'Giấy vệ sinh Bless You 10 cuộn', 6, 55000, 79000, 60, N'Lốc', '2025-10-20', '2028-10-20', N'Đang bán'),
    (N'Xúc xích tiệt trùng 500g', 8, 68000, 99000, 35, N'Gói', '2025-12-01', '2026-05-01', N'Đang bán');

    -- Ví dụ trường hợp biên sản phẩm
    UPDATE SAN_PHAM SET TrangThai = N'Ngừng kinh doanh' WHERE MaSanPham = 8;   -- phô mai
    UPDATE SAN_PHAM SET SoLuongTon = 0, TrangThai = N'Hết hàng' WHERE MaSanPham = 18; -- cà chua
    UPDATE SAN_PHAM SET HanSuDung = '2026-02-28', TrangThai = N'Hết hạn' WHERE MaSanPham = 17; -- rau cải xanh

    -- =========================
    -- PHIEU_NHAP (8)
    -- =========================
    INSERT INTO PHIEU_NHAP (MaNhanVien, MaNCC, NgayNhap, TongTien, TrangThai) VALUES
    (3, 1, '2026-01-10 08:45:00', 3210000, N'Đã nhập'),
    (3, 1, '2026-01-15 09:30:00', 4680000, N'Đã nhập'),
    (3, 3, '2026-02-01 07:55:00', 6340000, N'Đã nhập'),
    (9, 3, '2026-02-18 10:20:00', 7220000, N'Đã nhập'),
    (9, 4, '2026-02-25 14:00:00', 10680000, N'Đã nhập'),
    (3, 2, '2026-03-01 08:10:00', 6310000, N'Đã nhập'),
    (3, 2, '2026-03-05 08:35:00', 3640000, N'Đã nhập'),
    (9, 6, '2026-03-08 15:20:00', 5780000, N'Đã nhập');

    -- CT_PHIEU_NHAP
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

    -- =========================
    -- HOA_DON (15)
    -- =========================
    INSERT INTO HOA_DON (MaNhanVien, NgayLap, TongTien, TienKhachDua, TienThoi, TrangThai) VALUES
    (4, '2026-03-10 08:15:00', 108000, 200000, 92000, N'Đã thanh toán'),
    (4, '2026-03-10 09:05:00', 103000, 200000, 97000, N'Đã thanh toán'),
    (5, '2026-03-10 10:20:00', 93000, 100000, 7000, N'Đã thanh toán'),
    (5, '2026-03-10 11:40:00', 86000, 100000, 14000, N'Đã thanh toán'),
    (4, '2026-03-10 14:30:00', 197000, 200000, 3000, N'Đã thanh toán'),
    (5, '2026-03-10 16:10:00', 159000, 200000, 41000, N'Đã thanh toán'),
    (4, '2026-03-11 08:45:00', 173000, 200000, 27000, N'Đã thanh toán'),
    (5, '2026-03-11 09:55:00', 111000, 120000, 9000, N'Đã thanh toán'),
    (4, '2026-03-11 12:15:00', 109000, 110000, 1000, N'Đã thanh toán'),
    (5, '2026-03-11 15:50:00', 298500, 300000, 1500, N'Đã thanh toán'),
    (4, '2026-03-12 08:10:00', 173000, 200000, 27000, N'Đã thanh toán'),
    (5, '2026-03-12 10:25:00', 170000, 200000, 30000, N'Đã thanh toán'),
    (4, '2026-03-12 11:30:00', 85000, NULL, NULL, N'Đã hủy'),
    (5, '2026-03-12 17:20:00', 178000, NULL, NULL, N'Chờ thanh toán'),
    (4, '2026-03-13 18:05:00', 493000, 500000, 7000, N'Đã thanh toán');

    -- CT_HOA_DON
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