package com.goods;

import java.time.LocalDate;

/**
 * Hang thuc pham - ke thua Goods.
 * VAT co dinh la 5%.
 */
public class Food extends Goods {

    private static final double VAT_RATE = 0.05;

    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private String supplier;

    public Food(String productCode, String productName, int quantity, double price,
                LocalDate manufacturingDate, LocalDate expiryDate, String supplier) {
        // Goi constructor lop cha de kiem tra cac thuoc tinh chung truoc
        super(productCode, productName, quantity, price);

        if (expiryDate == null || manufacturingDate == null) {
            throw new IllegalArgumentException("Ngay san xuat / het han khong duoc null.");
        }
        if (!expiryDate.isAfter(manufacturingDate)) {
            throw new IllegalArgumentException("Ngay het han phai sau ngay san xuat.");
        }
        if (supplier == null || supplier.trim().isEmpty()) {
            throw new IllegalArgumentException("Nha cung cap khong duoc rong.");
        }

        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.supplier = supplier;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        if (supplier == null || supplier.trim().isEmpty()) {
            throw new IllegalArgumentException("Nha cung cap khong duoc rong.");
        }
        this.supplier = supplier;
    }

    @Override
    public double calculateVAT() {
        return getQuantity() * getPrice() * VAT_RATE;
    }

    @Override
    public String evaluateConsumption() {
        // Con hang ma da het han -> danh gia la kho ban
        if (getQuantity() > 0 && expiryDate.isBefore(LocalDate.now())) {
            return "Khó bán";
        }
        return "Không đánh giá";
    }

    @Override
    public String toString() {
        return "Food{" +
                "ma='" + getProductCode() + '\'' +
                ", ten='" + getProductName() + '\'' +
                ", soLuong=" + getQuantity() +
                ", donGia=" + getPrice() +
                ", ngaySX=" + manufacturingDate +
                ", ngayHetHan=" + expiryDate +
                ", nhaCungCap='" + supplier + '\'' +
                ", VAT=" + calculateVAT() +
                '}';
    }
}
