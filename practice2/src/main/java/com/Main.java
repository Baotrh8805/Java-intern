package com;

import com.goods.Crockery;
import com.goods.ElectronicGoods;
import com.goods.Food;
import com.goods.Goods;
import com.goods.GoodsManagement;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Chuong trinh quan ly hang hoa sieu thi (chay tren console).
 */
public class Main {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Ep console dung UTF-8 de hien thi dung tieng Viet co dau (tranh phu thuoc code page cua Windows)
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        GoodsManagement management = new GoodsManagement(100);

        // Nap san du lieu mau de kiem tra chuong trinh
        loadSampleData(management);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Chon chuc nang: ");

            switch (choice) {
                case 1:
                    addFood(management);
                    break;
                case 2:
                    addElectronicGoods(management);
                    break;
                case 3:
                    addCrockery(management);
                    break;
                case 4:
                    management.displayAll();
                    break;
                case 5:
                    searchByCode(management);
                    break;
                case 6:
                    management.displayStatistics();
                    break;
                case 7:
                    management.displayConsumptionEvaluation();
                    break;
                case 0:
                    running = false;
                    System.out.println("Da thoat chuong trinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le, vui long chon lai.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== MENU QUAN LY HANG HOA SIEU THI =====");
        System.out.println("1. Them hang thuc pham");
        System.out.println("2. Them hang dien may");
        System.out.println("3. Them hang sanh su");
        System.out.println("4. Hien thi toan bo danh sach");
        System.out.println("5. Tim hang hoa theo ma");
        System.out.println("6. Hien thi thong ke so luong va VAT");
        System.out.println("7. Hien thi danh gia kha nang tieu thu");
        System.out.println("0. Thoat");
    }

    // ========================= CAC CHUC NANG THEM HANG =========================

    private static void addFood(GoodsManagement management) {
        System.out.println("--- Them hang thuc pham ---");
        String code = readNonEmptyString("Ma hang hoa: ");
        String name = readNonEmptyString("Ten hang hoa: ");
        int quantity = readNonNegativeInt("So luong ton kho (>= 0): ");
        double price = readPositiveDouble("Don gia (> 0): ");
        LocalDate manufacturingDate = readDate("Ngay san xuat (dd/MM/yyyy): ");
        LocalDate expiryDate = readDate("Ngay het han (dd/MM/yyyy): ");
        String supplier = readNonEmptyString("Nha cung cap: ");

        try {
            Food food = new Food(code, name, quantity, price, manufacturingDate, expiryDate, supplier);
            if (management.addGoods(food)) {
                System.out.println("Them hang thuc pham thanh cong.");
            } else {
                System.out.println("Them that bai: ma hang '" + code + "' da ton tai hoac danh sach da day.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Du lieu khong hop le: " + e.getMessage());
        }
    }

    private static void addElectronicGoods(GoodsManagement management) {
        System.out.println("--- Them hang dien may ---");
        String code = readNonEmptyString("Ma hang hoa: ");
        String name = readNonEmptyString("Ten hang hoa: ");
        int quantity = readNonNegativeInt("So luong ton kho (>= 0): ");
        double price = readPositiveDouble("Don gia (> 0): ");
        int warrantyMonths = readNonNegativeInt("So thang bao hanh (>= 0): ");
        double power = readNonNegativeDouble("Cong suat - kW (>= 0): ");

        try {
            ElectronicGoods item = new ElectronicGoods(code, name, quantity, price, warrantyMonths, power);
            if (management.addGoods(item)) {
                System.out.println("Them hang dien may thanh cong.");
            } else {
                System.out.println("Them that bai: ma hang '" + code + "' da ton tai hoac danh sach da day.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Du lieu khong hop le: " + e.getMessage());
        }
    }

    private static void addCrockery(GoodsManagement management) {
        System.out.println("--- Them hang sanh su ---");
        String code = readNonEmptyString("Ma hang hoa: ");
        String name = readNonEmptyString("Ten hang hoa: ");
        int quantity = readNonNegativeInt("So luong ton kho (>= 0): ");
        double price = readPositiveDouble("Don gia (> 0): ");
        String manufacturer = readNonEmptyString("Nha san xuat: ");
        LocalDate importDate = readDate("Ngay nhap kho (dd/MM/yyyy): ");

        try {
            Crockery item = new Crockery(code, name, quantity, price, manufacturer, importDate);
            if (management.addGoods(item)) {
                System.out.println("Them hang sanh su thanh cong.");
            } else {
                System.out.println("Them that bai: ma hang '" + code + "' da ton tai hoac danh sach da day.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Du lieu khong hop le: " + e.getMessage());
        }
    }

    private static void searchByCode(GoodsManagement management) {
        String code = readNonEmptyString("Nhap ma hang hoa can tim: ");
        Goods goods = management.findByCode(code);
        if (goods == null) {
            System.out.println("Khong tim thay hang hoa co ma '" + code + "'.");
        } else {
            System.out.println("Tim thay: " + goods);
        }
    }

    // ========================= CAC HAM NHAP DU LIEU AN TOAN =========================

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("Gia tri khong duoc de trong, vui long nhap lai.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap mot so nguyen hop le.");
            }
        }
    }

    private static int readNonNegativeInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value >= 0) {
                return value;
            }
            System.out.println("Gia tri phai >= 0, vui long nhap lai.");
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap mot so hop le.");
            }
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            double value = readDouble(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Gia tri phai lon hon 0, vui long nhap lai.");
        }
    }

    private static double readNonNegativeDouble(String prompt) {
        while (true) {
            double value = readDouble(prompt);
            if (value >= 0) {
                return value;
            }
            System.out.println("Gia tri phai >= 0, vui long nhap lai.");
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input.trim(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Ngay khong dung dinh dang dd/MM/yyyy, vui long nhap lai.");
            }
        }
    }

    // ========================= DU LIEU MAU =========================

    private static void loadSampleData(GoodsManagement management) {
        LocalDate today = LocalDate.now();

        // 1. Thuc pham da het han, con ton kho -> se danh gia "Kho ban"
        Food food1 = new Food("TP001", "Sua tuoi Vinamilk", 20, 15000,
                today.minusMonths(6), today.minusDays(5), "Vinamilk");

        // 2. Thuc pham binh thuong, con han su dung
        Food food2 = new Food("TP002", "Banh quy AFC", 50, 12000,
                today.minusDays(10), today.plusMonths(6), "Kinh Do");

        // 3. Dien may co so luong < 3 -> se danh gia "Ban chay"
        ElectronicGoods elec1 = new ElectronicGoods("DM001", "Tivi Samsung 55 inch", 2, 12000000, 24, 0.15);

        // 4. Dien may binh thuong
        ElectronicGoods elec2 = new ElectronicGoods("DM002", "May giat LG 9kg", 10, 8500000, 12, 0.5);

        // 5. Sanh su so luong > 50 va da luu kho > 10 ngay -> se danh gia "Ban cham"
        Crockery crockery1 = new Crockery("SS001", "Bo bat su Minh Long", 80, 250000,
                "Minh Long", today.minusDays(30));

        // 6. Sanh su binh thuong
        Crockery crockery2 = new Crockery("SS002", "Ly thuy tinh cao cap", 20, 90000,
                "Duc Tin", today.minusDays(2));

        management.addGoods(food1);
        management.addGoods(food2);
        management.addGoods(elec1);
        management.addGoods(elec2);
        management.addGoods(crockery1);
        management.addGoods(crockery2);

        System.out.println("Da nap " + 6 + " hang hoa mau vao danh sach.");
    }
}
