package com.quickmanager.controller;

import com.quickmanager.model.SanPham;
import com.quickmanager.service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;

public class SellController {
    @FXML private TableView<SanPham> tbvSanPham;
    @FXML private TableColumn<SanPham, Integer> colMaSP;
    @FXML private TableColumn<SanPham, String> colTenSP;
    @FXML private TableColumn<SanPham, String> colDVT;
    @FXML private TableColumn<SanPham, Integer> colTonKho;
    @FXML private TableColumn<SanPham, BigDecimal> colGiaBan;
    @FXML private TextField txtTimSanPham;
    private static final ObservableList<SanPham> dataSanPham = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        initTable();
        tbvSanPham.setItems(dataSanPham);
        loadSanPham();
    }

    public void initTable() {
        colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSanPham"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSanPham"));
        colDVT.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        colTonKho.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
    }

    public void loadSanPham() {
        dataSanPham.setAll(ProductService.getProduct(txtTimSanPham.getText()));
    }

    public void handleSearch() {
        loadSanPham();
    }

}
