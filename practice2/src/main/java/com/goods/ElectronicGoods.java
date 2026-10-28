package com.goods;

/**
 * Hang dien may - ke thua Goods.
 * VAT co dinh la 10%.
 */
public class ElectronicGoods extends Goods {

    private static final double VAT_RATE = 0.10;

    private int warrantyMonths; // so thang bao hanh
    private double power;       // cong suat, tinh bang kW

    public ElectronicGoods(String productCode, String productName, int quantity, double price,
                            int warrantyMonths, double power) {
        super(productCode, productName, quantity, price);

        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("So thang bao hanh phai >= 0.");
        }
        if (power < 0) {
            throw new IllegalArgumentException("Cong suat phai >= 0.");
        }

        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("So thang bao hanh phai >= 0.");
        }
        this.warrantyMonths = warrantyMonths;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        if (power < 0) {
            throw new IllegalArgumentException("Cong suat phai >= 0.");
        }
        this.power = power;
    }

    @Override
    public double calculateVAT() {
        return getQuantity() * getPrice() * VAT_RATE;
    }

    @Override
    public String evaluateConsumption() {
        // Ton kho qua it -> hang dang ban chay
        if (getQuantity() < 3) {
            return "Bán chạy";
        }
        return "Không đánh giá";
    }

    @Override
    public String toString() {
        return "ElectronicGoods{" +
                "ma='" + getProductCode() + '\'' +
                ", ten='" + getProductName() + '\'' +
                ", soLuong=" + getQuantity() +
                ", donGia=" + getPrice() +
                ", baoHanh=" + warrantyMonths + " thang" +
                ", congSuat=" + power + " kW" +
                ", VAT=" + calculateVAT() +
                '}';
    }
}
