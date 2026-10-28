import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main chứa menu console và vòng lặp chính của chương trình.
 * Toàn bộ nghiệp vụ (thêm/tìm/xóa/thống kê) nằm trong VehicleManagement;
 * Main chỉ có nhiệm vụ đọc lựa chọn của người dùng, gọi đúng phương thức
 * tương ứng và hiển thị kết quả.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VehicleManagement management = new VehicleManagement();

        // Nạp dữ liệu mẫu một lần duy nhất khi khởi động chương trình
        initializeSampleData(management);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1:
                    addCarFlow(scanner, management);
                    break;
                case 2:
                    addMotorcycleFlow(scanner, management);
                    break;
                case 3:
                    addTruckFlow(scanner, management);
                    break;
                case 4:
                    management.displayAllVehicles();
                    break;
                case 5:
                    findByVehicleNumberFlow(scanner, management);
                    break;
                case 6:
                    findByCitizenIdFlow(scanner, management);
                    break;
                case 7:
                    deleteByManufacturerFlow(scanner, management);
                    break;
                case 8:
                    showMostVehiclesManufacturerFlow(management);
                    break;
                case 9:
                    showSortedManufacturersFlow(management);
                    break;
                case 10:
                    management.displayVehicleTypeStatistics();
                    break;
                case 11:
                    showVehiclesSortedByNumberFlow(management);
                    break;
                case 0:
                    running = false;
                    System.out.println("Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("================ QUẢN LÝ PHƯƠNG TIỆN ================");
        System.out.println("1. Thêm ô tô");
        System.out.println("2. Thêm xe máy");
        System.out.println("3. Thêm xe tải");
        System.out.println("4. Hiển thị tất cả phương tiện");
        System.out.println("5. Tìm phương tiện theo mã số phương tiện");
        System.out.println("6. Tìm phương tiện theo CCCD của chủ xe");
        System.out.println("7. Xóa tất cả phương tiện theo hãng sản xuất");
        System.out.println("8. Hiển thị hãng có nhiều phương tiện nhất");
        System.out.println("9. Sắp xếp hãng theo số lượng phương tiện giảm dần");
        System.out.println("10. Thống kê số lượng từng loại phương tiện");
        System.out.println("11. (Bổ sung - minh họa Comparator) Sắp xếp phương tiện theo mã số tăng dần");
        System.out.println("0. Thoát");
        System.out.println("=======================================================");
        System.out.print("Nhập lựa chọn của bạn: ");
    }

    /**
     * Đọc lựa chọn menu bằng nextLine() rồi tự parse sang int.
     * Nếu người dùng gõ sai kiểu (chữ, ký tự đặc biệt ...), chương trình
     * không bị dừng (crash) mà chỉ báo lỗi và cho chọn lại.
     */
    private static int readMenuChoice(Scanner scanner) {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; // giá trị không khớp case nào -> rơi vào default
        }
    }

    // ============ CÁC LUỒNG THÊM PHƯƠNG TIỆN ============

    private static void addCarFlow(Scanner scanner, VehicleManagement management) {
        System.out.println("--- Thêm ô tô ---");
        String vehicleNumber = InputHelper.readVehicleNumber(scanner, management);
        Manufacturer manufacturer = InputHelper.readManufacturer(scanner);
        int manufactureYear = InputHelper.readManufactureYear(scanner);
        String color = InputHelper.readNonEmptyString(scanner, "Nhập màu xe: ");

        VehicleOwner owner = InputHelper.readOwner(scanner, management);
        if (owner == null) {
            System.out.println("Hủy thêm ô tô do dữ liệu chủ xe không hợp lệ.");
            return;
        }

        int numberOfSeats = InputHelper.readPositiveInt(scanner, "Nhập số chỗ ngồi: ");
        String engineType = InputHelper.readNonEmptyString(scanner, "Nhập kiểu động cơ: ");

        try {
            Car car = new Car(vehicleNumber, manufacturer, manufactureYear, color, owner,
                    numberOfSeats, engineType);
            reportAddResult(management.addVehicle(car), "ô tô");
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi dữ liệu: " + e.getMessage());
        }
    }

    private static void addMotorcycleFlow(Scanner scanner, VehicleManagement management) {
        System.out.println("--- Thêm xe máy ---");
        String vehicleNumber = InputHelper.readVehicleNumber(scanner, management);
        Manufacturer manufacturer = InputHelper.readManufacturer(scanner);
        int manufactureYear = InputHelper.readManufactureYear(scanner);
        String color = InputHelper.readNonEmptyString(scanner, "Nhập màu xe: ");

        VehicleOwner owner = InputHelper.readOwner(scanner, management);
        if (owner == null) {
            System.out.println("Hủy thêm xe máy do dữ liệu chủ xe không hợp lệ.");
            return;
        }

        double capacity = InputHelper.readPositiveDouble(scanner, "Nhập dung tích/công suất: ");

        try {
            Motorcycle motorcycle = new Motorcycle(vehicleNumber, manufacturer, manufactureYear,
                    color, owner, capacity);
            reportAddResult(management.addVehicle(motorcycle), "xe máy");
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi dữ liệu: " + e.getMessage());
        }
    }

    private static void addTruckFlow(Scanner scanner, VehicleManagement management) {
        System.out.println("--- Thêm xe tải ---");
        String vehicleNumber = InputHelper.readVehicleNumber(scanner, management);
        Manufacturer manufacturer = InputHelper.readManufacturer(scanner);
        int manufactureYear = InputHelper.readManufactureYear(scanner);
        String color = InputHelper.readNonEmptyString(scanner, "Nhập màu xe: ");

        VehicleOwner owner = InputHelper.readOwner(scanner, management);
        if (owner == null) {
            System.out.println("Hủy thêm xe tải do dữ liệu chủ xe không hợp lệ.");
            return;
        }

        double tonnage = InputHelper.readPositiveDouble(scanner, "Nhập trọng tải (tấn): ");

        try {
            Truck truck = new Truck(vehicleNumber, manufacturer, manufactureYear, color, owner,
                    tonnage);
            reportAddResult(management.addVehicle(truck), "xe tải");
        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi dữ liệu: " + e.getMessage());
        }
    }

    private static void reportAddResult(boolean success, String vehicleTypeName) {
        if (success) {
            System.out.println("Thêm " + vehicleTypeName + " thành công.");
        } else {
            System.out.println("Thêm " + vehicleTypeName + " thất bại.");
        }
    }

    // ============ CÁC LUỒNG TÌM KIẾM / XÓA / THỐNG KÊ ============

    private static void findByVehicleNumberFlow(Scanner scanner, VehicleManagement management) {
        System.out.print("Nhập mã số phương tiện cần tìm: ");
        String vehicleNumber = scanner.nextLine().trim();
        Vehicle found = management.findByVehicleNumber(vehicleNumber);
        if (found != null) {
            System.out.println("Tìm thấy phương tiện:");
            System.out.println(found);
        } else {
            System.out.println("Không tìm thấy phương tiện có mã số \"" + vehicleNumber + "\".");
        }
    }

    private static void findByCitizenIdFlow(Scanner scanner, VehicleManagement management) {
        String citizenId = InputHelper.readCitizenId(scanner);
        ArrayList<Vehicle> vehicles = management.findVehiclesByCitizenId(citizenId);
        if (vehicles.isEmpty()) {
            System.out.println("Không có phương tiện nào thuộc CCCD " + citizenId + ".");
            return;
        }
        System.out.println("Danh sách phương tiện của CCCD " + citizenId + ":");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    private static void deleteByManufacturerFlow(Scanner scanner, VehicleManagement management) {
        Manufacturer manufacturer = InputHelper.readManufacturer(scanner);
        int deletedCount = management.deleteVehiclesByManufacturer(manufacturer);
        if (deletedCount > 0) {
            System.out.println("Đã xóa " + deletedCount + " phương tiện của hãng " + manufacturer + ".");
        } else {
            System.out.println("Không có phương tiện nào của hãng " + manufacturer + " để xóa.");
        }
    }

    private static void showMostVehiclesManufacturerFlow(VehicleManagement management) {
        ArrayList<Manufacturer> topManufacturers = management.findManufacturersWithMostVehicles();
        if (topManufacturers.isEmpty()) {
            System.out.println("Danh sách phương tiện đang trống, chưa thể thống kê.");
            return;
        }

        ArrayList<ManufacturerStatistic> statistics = management.sortManufacturersByVehicleCountDescending();
        int maxCount = statistics.get(0).getVehicleCount();

        System.out.println("Hãng có nhiều phương tiện nhất (" + maxCount + " phương tiện):");
        for (Manufacturer manufacturer : topManufacturers) {
            System.out.println("- " + manufacturer);
        }
    }

    private static void showSortedManufacturersFlow(VehicleManagement management) {
        ArrayList<ManufacturerStatistic> statistics = management.sortManufacturersByVehicleCountDescending();
        System.out.println("Thống kê số lượng phương tiện theo hãng (giảm dần):");
        for (ManufacturerStatistic statistic : statistics) {
            System.out.println(statistic);
        }
    }

    private static void showVehiclesSortedByNumberFlow(VehicleManagement management) {
        ArrayList<Vehicle> sorted = management.sortVehiclesByVehicleNumberAscending();
        if (sorted.isEmpty()) {
            System.out.println("Danh sách phương tiện đang trống.");
            return;
        }
        System.out.println("Danh sách phương tiện sắp xếp theo mã số tăng dần (chức năng bổ sung):");
        for (Vehicle vehicle : sorted) {
            System.out.println(vehicle);
        }
    }

    // ============ DỮ LIỆU MẪU ============

    /**
     * Tạo sẵn dữ liệu mẫu để kiểm thử chương trình ngay khi khởi động.
     * Có 3 ô tô, 3 xe máy, 2 xe tải; mỗi hãng (Honda/Yamaha/Toyota/Suzuki)
     * đều có đúng 2 phương tiện (để kiểm thử trường hợp đồng hạng khi tìm
     * hãng có nhiều phương tiện nhất). Có 3 chủ xe sở hữu từ 2 phương tiện
     * trở lên để minh họa việc dùng lại một đối tượng VehicleOwner.
     */
    private static void initializeSampleData(VehicleManagement management) {
        VehicleOwner ownerA = new VehicleOwner("123456789012", "Nguyễn Văn A", "nguyenvana@gmail.com");
        VehicleOwner ownerB = new VehicleOwner("223456789012", "Trần Thị B", "tranthib@gmail.com");
        VehicleOwner ownerC = new VehicleOwner("323456789012", "Lê Văn C", "levanc@gmail.com");
        VehicleOwner ownerD = new VehicleOwner("423456789012", "Phạm Thị D", "phamthid@gmail.com");
        VehicleOwner ownerE = new VehicleOwner("523456789012", "Hoàng Văn E", "hoangvane@gmail.com");

        // Ô tô (3 chiếc) - Honda: A1111, A3333 | Toyota: A2222
        management.addVehicle(new Car("A1111", Manufacturer.HONDA, 2020, "Đỏ", ownerA, 4, "Xăng"));
        management.addVehicle(new Car("A2222", Manufacturer.TOYOTA, 2019, "Trắng", ownerB, 5, "Dầu"));
        // ownerA sở hữu thêm chiếc xe thứ hai -> minh họa "một chủ nhiều xe"
        management.addVehicle(new Car("A3333", Manufacturer.HONDA, 2021, "Đen", ownerA, 7, "Xăng"));

        // Xe máy (3 chiếc) - Yamaha: M1111, M3333 | Suzuki: M2222
        management.addVehicle(new Motorcycle("M1111", Manufacturer.YAMAHA, 2018, "Xanh", ownerC, 110));
        management.addVehicle(new Motorcycle("M2222", Manufacturer.SUZUKI, 2022, "Đỏ", ownerD, 125));
        // ownerB sở hữu thêm xe máy -> chủ B có cả ô tô lẫn xe máy
        management.addVehicle(new Motorcycle("M3333", Manufacturer.YAMAHA, 2020, "Trắng", ownerB, 150));

        // Xe tải (2 chiếc) - Toyota: T1111 | Suzuki: T2222
        management.addVehicle(new Truck("T1111", Manufacturer.TOYOTA, 2017, "Xanh", ownerE, 5.5));
        // ownerC sở hữu thêm xe tải -> chủ C có cả xe máy lẫn xe tải
        management.addVehicle(new Truck("T2222", Manufacturer.SUZUKI, 2021, "Đen", ownerC, 3.2));
    }
}
