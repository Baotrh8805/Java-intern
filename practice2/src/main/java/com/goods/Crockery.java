package com.goods;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Hang sanh su - ke thua Goods.
 * VAT co dinh la 10%.
 */
public class Crockery extends Goods {

    private static final double VAT_RATE = 0.10;

    private String manufacturer;   // nha san xuat
    private LocalDate importDate;  // ngay nhap kho

    public Crockery(String productCode, String productName, int quantity, double price,
                     String manufacturer, LocalDate importDate) {
        super(productCode, productName, quantity, price);

        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            throw new IllegalArgumentException("Nha san xuat khong duoc rong.");
        }
        if (importDate == null || importDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngay nhap kho khong duoc sau ngay hien tai.");
        }

        this.manufacturer = manufacturer;
        this.importDate = importDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            throw new IllegalArgumentException("Nha san xuat khong duoc rong.");
        }
        this.manufacturer = manufacturer;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        if (importDate == null || importDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngay nhap kho khong duoc sau ngay hien tai.");
        }
        this.importDate = importDate;
    }

    @Override
    public double calculateVAT() {
        return getQuantity() * getPrice() * VAT_RATE;
    }

    @Override
    public String evaluateConsumption() {
        long storageDays = ChronoUnit.DAYS.between(importDate, LocalDate.now());
        // Ton kho nhieu va da luu kho lau -> ban cham
        if (getQuantity() > 50 && storageDays > 10) {
            return "Bán chậm";
        }
        return "Không đánh giá";
    }

    @Override
    public String toString() {
        return "Crockery{" +
                "ma='" + getProductCode() + '\'' +
                ", ten='" + getProductName() + '\'' +
                ", soLuong=" + getQuantity() +
                ", donGia=" + getPrice() +
                ", nhaSanXuat='" + manufacturer + '\'' +
                ", ngayNhapKho=" + importDate +
                ", VAT=" + calculateVAT() +
                '}';
    }
}
