package com.goods;

/**
 * Lop cha (abstract) chua cac thuoc tinh va phuong thuc chung
 * cho moi loai hang hoa trong sieu thi.
 */
public abstract class Goods {

    private String productCode;
    private String productName;
    private int quantity;
    private double price;

    public Goods(String productCode, String productName, int quantity, double price) {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma hang hoa khong duoc rong.");
        }
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten hang hoa khong duoc rong.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("So luong ton kho phai >= 0.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Don gia phai lon hon 0.");
        }
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma hang hoa khong duoc rong.");
        }
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten hang hoa khong duoc rong.");
        }
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("So luong ton kho phai >= 0.");
        }
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Don gia phai lon hon 0.");
        }
        this.price = price;
    }

    /** Tong tien hang = so luong ton kho * don gia (chua VAT). */
    public double getTotalPrice() {
        return quantity * price;
    }

    /** Moi loai hang hoa co thue suat VAT rieng nen bat buoc phai override. */
    public abstract double calculateVAT();

    /** Moi loai hang hoa co cach danh gia kha nang tieu thu rieng. */
    public abstract String evaluateConsumption();

    @Override
    public abstract String toString();
}
