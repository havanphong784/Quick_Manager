USE QL_SIEU_THI;
GO

-- ==============================================================================
-- CẬP NHẬT DỮ LIỆU NGÀY THÁNG ĐỂ GẦN VỚI THỜI GIAN HIỆN TẠI
-- ==============================================================================

-- 1. Cập nhật bảng HOA_DON (Dời ngày lập hóa đơn)
DECLARE @MaxNgayLap DATETIME;
SELECT @MaxNgayLap = MAX(NgayLap) FROM HOA_DON;

IF @MaxNgayLap IS NOT NULL
BEGIN
    DECLARE @DiffDaysHoaDon INT;
    -- Tính khoảng cách ngày từ ngày lớn nhất đến hiện tại
    SET @DiffDaysHoaDon = DATEDIFF(DAY, @MaxNgayLap, GETDATE());

    IF @DiffDaysHoaDon > 0
    BEGIN
        UPDATE HOA_DON
        SET NgayLap = DATEADD(DAY, @DiffDaysHoaDon, NgayLap);
        PRINT N'Đã cập nhật ngày lập HOA_DON.';
    END
END
GO

-- 2. Cập nhật bảng PHIEU_NHAP (Dời ngày nhập)
DECLARE @MaxNgayNhap DATETIME;
SELECT @MaxNgayNhap = MAX(NgayNhap) FROM PHIEU_NHAP;

IF @MaxNgayNhap IS NOT NULL
BEGIN
    DECLARE @DiffDaysPhieuNhap INT;
    -- Tính khoảng cách ngày từ ngày nhập lớn nhất đến hiện tại
    SET @DiffDaysPhieuNhap = DATEDIFF(DAY, @MaxNgayNhap, GETDATE());

    IF @DiffDaysPhieuNhap > 0
    BEGIN
        UPDATE PHIEU_NHAP
        SET NgayNhap = DATEADD(DAY, @DiffDaysPhieuNhap, NgayNhap);
        PRINT N'Đã cập nhật ngày nhận PHIEU_NHAP.';
    END
END
GO

-- 3. Cập nhật ngày sản xuất và hạn sử dụng của SAN_PHAM
-- Sản phẩm được cập nhật để NSX luôn ở trong quá khứ (30-210 ngày trước)
-- và HSD luôn ở trong tương lai (180-540 ngày tính từ hiện tại).
BEGIN
    UPDATE SAN_PHAM
    SET
        NgaySanXuat = CAST(DATEADD(DAY, - (ABS(CHECKSUM(NEWID())) % 180) - 30, GETDATE()) AS DATE),
        HanSuDung = CAST(DATEADD(DAY, (ABS(CHECKSUM(NEWID())) % 360) + 180, GETDATE()) AS DATE)
    WHERE TrangThai <> N'Ngưng kinh doanh';

    PRINT N'Đã cập nhật NgaySanXuat và HanSuDung của SAN_PHAM.';
END
GO

PRINT N'Hoàn tất cập nhật dữ liệu gần với hiện tại!';
GO

