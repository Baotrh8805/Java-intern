package com.goods;

/**
 * Quan ly danh sach hang hoa bang MANG (khong dung ArrayList).
 */
public class GoodsManagement {

    private Goods[] goodsList;
    private int count; // so luong hang hoa dang co trong mang

    public GoodsManagement(int capacity) {
        this.goodsList = new Goods[capacity];
        this.count = 0;
    }

    /**
     * Them 1 hang hoa vao mang.
     * Tra ve false neu: goods null, mang da day, hoac ma hang bi trung.
     */
    public boolean addGoods(Goods goods) {
        if (goods == null) {
            return false;
        }
        if (count >= goodsList.length) {
            return false;
        }
        if (findByCode(goods.getProductCode()) != null) {
            return false;
        }
        goodsList[count] = goods;
        count++;
        return true;
    }

    /** Tim hang hoa theo ma, khong phan biet chu hoa/thuong. */
    public Goods findByCode(String productCode) {
        if (productCode == null) {
            return null;
        }
        for (int i = 0; i < count; i++) {
            if (goodsList[i].getProductCode().equalsIgnoreCase(productCode)) {
                return goodsList[i];
            }
        }
        return null;
    }

    /** Hien thi toan bo danh sach hang hoa. */
    public void displayAll() {
        if (count == 0) {
            System.out.println("Danh sach hang hoa dang rong.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + goodsList[i]);
        }
    }

    /** Tong so luong ton kho cua 1 loai hang hoa (Food.class, ElectronicGoods.class, Crockery.class). */
    public int getTotalQuantityByType(Class<?> type) {
        int total = 0;
        for (int i = 0; i < count; i++) {
            if (type.isInstance(goodsList[i])) {
                total += goodsList[i].getQuantity();
            }
        }
        return total;
    }

    /** Tong tien VAT cua 1 loai hang hoa. */
    public double getTotalVATByType(Class<?> type) {
        double total = 0;
        for (int i = 0; i < count; i++) {
            if (type.isInstance(goodsList[i])) {
                total += goodsList[i].calculateVAT();
            }
        }
        return total;
    }

    /** Hien thi thong ke tong so luong va tong VAT cho tung loai hang hoa. */
    public void displayStatistics() {
        System.out.println("--- THONG KE HANG HOA ---");
        System.out.println("Thuc pham     : tong so luong = " + getTotalQuantityByType(Food.class)
                + ", tong VAT = " + getTotalVATByType(Food.class));
        System.out.println("Dien may      : tong so luong = " + getTotalQuantityByType(ElectronicGoods.class)
                + ", tong VAT = " + getTotalVATByType(ElectronicGoods.class));
        System.out.println("Sanh su       : tong so luong = " + getTotalQuantityByType(Crockery.class)
                + ", tong VAT = " + getTotalVATByType(Crockery.class));
    }

    /** Hien thi danh gia kha nang tieu thu cua tung hang hoa trong danh sach. */
    public void displayConsumptionEvaluation() {
        if (count == 0) {
            System.out.println("Danh sach hang hoa dang rong.");
            return;
        }
        for (int i = 0; i < count; i++) {
            Goods g = goodsList[i];
            System.out.println("Ma: " + g.getProductCode()
                    + " - Ten: " + g.getProductName()
                    + " - Danh gia: " + g.evaluateConsumption());
        }
    }
}
