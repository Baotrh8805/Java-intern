import java.util.ArrayList;
import java.util.Comparator;

/**
 * VehicleManagement quản lý toàn bộ danh sách phương tiện bằng ArrayList.
 * Đây là lớp nghiệp vụ (business logic) trung tâm của chương trình:
 * mọi thao tác thêm/tìm/xóa/thống kê đều đi qua lớp này, Main chỉ gọi
 * các phương thức public và hiển thị kết quả ra màn hình.
 */
public class VehicleManagement {

    private ArrayList<Vehicle> vehicleList = new ArrayList<>();

    // ============ 1. THÊM PHƯƠNG TIỆN ============

    /**
     * Thêm một phương tiện vào danh sách.
     *
     * Quy tắc xử lý chủ xe (owner):
     * - Nếu CCCD của chủ xe đã tồn tại trong hệ thống:
     *      + Nếu họ tên và email khớp với chủ xe cũ -> dùng LẠI chính đối
     *        tượng VehicleOwner cũ (không tạo đối tượng owner mới), để đảm
     *        bảo một CCCD luôn ứng với đúng một đối tượng VehicleOwner.
     *      + Nếu họ tên hoặc email khác -> dữ liệu không nhất quán, từ chối
     *        thêm phương tiện.
     * - Nếu CCCD chưa tồn tại -> đây là chủ xe mới, giữ nguyên owner được
     *   truyền vào.
     *
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }

        // 1. Kiểm tra trùng mã phương tiện (không phân biệt hoa/thường)
        if (isVehicleNumberExists(vehicle.getVehicleNumber())) {
            System.out.println("Lỗi: Mã phương tiện \"" + vehicle.getVehicleNumber()
                    + "\" đã tồn tại.");
            return false;
        }

        // 2. Kiểm tra tính nhất quán của chủ xe theo CCCD
        VehicleOwner incomingOwner = vehicle.getOwner();
        VehicleOwner existingOwner = findOwnerByCitizenId(incomingOwner.getCitizenId());

        if (existingOwner != null) {
            boolean sameFullName = existingOwner.getFullName().equals(incomingOwner.getFullName());
            boolean sameEmail = existingOwner.getEmail().equals(incomingOwner.getEmail());
            if (!sameFullName || !sameEmail) {
                System.out.println("Lỗi: CCCD " + incomingOwner.getCitizenId()
                        + " đã tồn tại nhưng họ tên/email không khớp với dữ liệu cũ."
                        + " Dữ liệu chủ xe không nhất quán.");
                return false;
            }
            // Họ tên và email khớp -> dùng lại đúng một đối tượng VehicleOwner
            vehicle.setOwner(existingOwner);
        }

        vehicleList.add(vehicle);
        return true;
    }

    // ============ 2. TÌM THEO MÃ SỐ PHƯƠNG TIỆN ============

    public Vehicle findByVehicleNumber(String vehicleNumber) {
        if (vehicleNumber == null) {
            return null;
        }
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getVehicleNumber().equalsIgnoreCase(vehicleNumber)) {
                return vehicle;
            }
        }
        return null;
    }

    // ============ 3. TÌM THEO CCCD CHỦ XE ============

    public ArrayList<Vehicle> findVehiclesByCitizenId(String citizenId) {
        ArrayList<Vehicle> result = new ArrayList<>();
        if (citizenId == null) {
            return result;
        }
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getOwner().getCitizenId().equals(citizenId)) {
                result.add(vehicle);
            }
        }
        return result;
    }

    // ============ 4. XÓA THEO HÃNG SẢN XUẤT ============

    /**
     * Xóa toàn bộ phương tiện thuộc một hãng sản xuất.
     * Dùng removeIf() (có từ Java 8) để xóa trong khi duyệt danh sách mà
     * KHÔNG gây ra lỗi ConcurrentModificationException - lỗi này thường xảy
     * ra nếu ta dùng vòng for-each thông thường rồi gọi list.remove() bên
     * trong vòng lặp.
     *
     * @return số lượng phương tiện đã bị xóa
     */
    public int deleteVehiclesByManufacturer(Manufacturer manufacturer) {
        int sizeBefore = vehicleList.size();
        vehicleList.removeIf(vehicle -> vehicle.getManufacturer() == manufacturer);
        int sizeAfter = vehicleList.size();
        return sizeBefore - sizeAfter;
    }

    // ============ 5. HÃNG CÓ NHIỀU PHƯƠNG TIỆN NHẤT ============

    public ArrayList<Manufacturer> findManufacturersWithMostVehicles() {
        ArrayList<Manufacturer> result = new ArrayList<>();
        if (vehicleList.isEmpty()) {
            return result;
        }

        int[] counts = countVehiclesByManufacturer();
        int maxCount = 0;
        for (int count : counts) {
            if (count > maxCount) {
                maxCount = count;
            }
        }

        Manufacturer[] manufacturers = Manufacturer.values();
        for (int i = 0; i < manufacturers.length; i++) {
            if (counts[i] == maxCount && maxCount > 0) {
                result.add(manufacturers[i]);
            }
        }
        return result;
    }

    // ============ 6. SẮP XẾP HÃNG THEO SỐ LƯỢNG PHƯƠNG TIỆN GIẢM DẦN ============

    /**
     * Thống kê số lượng phương tiện của từng hãng rồi sắp xếp giảm dần theo
     * số lượng; nếu bằng nhau thì sắp xếp theo tên hãng tăng dần.
     * Phương thức này KHÔNG làm thay đổi danh sách phương tiện gốc, vì nó
     * chỉ đọc dữ liệu (đếm) rồi tạo ra một danh sách thống kê mới.
     */
    public ArrayList<ManufacturerStatistic> sortManufacturersByVehicleCountDescending() {
        int[] counts = countVehiclesByManufacturer();
        ArrayList<ManufacturerStatistic> statistics = new ArrayList<>();

        Manufacturer[] manufacturers = Manufacturer.values();
        for (int i = 0; i < manufacturers.length; i++) {
            statistics.add(new ManufacturerStatistic(manufacturers[i], counts[i]));
        }

        // Comparator: so sánh trước theo số lượng giảm dần, nếu bằng nhau thì
        // so sánh theo tên hãng (dạng chuỗi) tăng dần.
        statistics.sort(new Comparator<ManufacturerStatistic>() {
            @Override
            public int compare(ManufacturerStatistic a, ManufacturerStatistic b) {
                if (a.getVehicleCount() != b.getVehicleCount()) {
                    // giảm dần -> lấy b trừ a
                    return b.getVehicleCount() - a.getVehicleCount();
                }
                return a.getManufacturer().name().compareTo(b.getManufacturer().name());
            }
        });

        return statistics;
    }

    /**
     * CHỨC NĂNG BỔ SUNG (không thay thế mục 6 phía trên):
     * Sắp xếp danh sách PHƯƠNG TIỆN theo vehicleNumber tăng dần, chỉ để
     * minh họa cách dùng Comparator với danh sách đối tượng Vehicle.
     * Trả về một danh sách MỚI, không làm thay đổi thứ tự của vehicleList gốc.
     */
    public ArrayList<Vehicle> sortVehiclesByVehicleNumberAscending() {
        ArrayList<Vehicle> sortedList = new ArrayList<>(vehicleList);
        sortedList.sort(Comparator.comparing(
                vehicle -> vehicle.getVehicleNumber().toUpperCase()));
        return sortedList;
    }

    // Đếm số phương tiện theo từng hãng, trả về mảng int song song với
    // Manufacturer.values() (counts[i] tương ứng manufacturers[i]).
    private int[] countVehiclesByManufacturer() {
        Manufacturer[] manufacturers = Manufacturer.values();
        int[] counts = new int[manufacturers.length];
        for (Vehicle vehicle : vehicleList) {
            for (int i = 0; i < manufacturers.length; i++) {
                if (vehicle.getManufacturer() == manufacturers[i]) {
                    counts[i]++;
                    break;
                }
            }
        }
        return counts;
    }

    // ============ 7. THỐNG KÊ SỐ LƯỢNG TỪNG LOẠI PHƯƠNG TIỆN ============

    /**
     * In ra số lượng Car, Motorcycle, Truck và tổng số phương tiện.
     * Dùng instanceof để kiểm tra kiểu thực sự của từng đối tượng Vehicle
     * trong ArrayList<Vehicle> (đây cũng là một cách nhìn thấy tính đa hình:
     * vehicleList chứa nhiều loại đối tượng con khác nhau).
     */
    public void displayVehicleTypeStatistics() {
        int carCount = 0;
        int motorcycleCount = 0;
        int truckCount = 0;

        for (Vehicle vehicle : vehicleList) {
            if (vehicle instanceof Car) {
                carCount++;
            } else if (vehicle instanceof Motorcycle) {
                motorcycleCount++;
            } else if (vehicle instanceof Truck) {
                truckCount++;
            }
        }

        System.out.println("--- Thống kê số lượng theo loại phương tiện ---");
        System.out.println("Số ô tô     : " + carCount);
        System.out.println("Số xe máy   : " + motorcycleCount);
        System.out.println("Số xe tải   : " + truckCount);
        System.out.println("Tổng số     : " + vehicleList.size());
    }

    // ============ CÁC PHƯƠNG THỨC BỔ SUNG (MỤC 5) ============

    public void displayAllVehicles() {
        if (vehicleList.isEmpty()) {
            System.out.println("Danh sách phương tiện đang trống.");
            return;
        }
        for (Vehicle vehicle : vehicleList) {
            // Lời gọi vehicle.toString() (ngầm định qua println) sẽ tự động
            // chạy đúng phiên bản toString() của Car/Motorcycle/Truck tùy
            // theo kiểu thực sự của đối tượng - đây chính là đa hình.
            System.out.println(vehicle);
        }
    }

    public boolean isVehicleNumberExists(String vehicleNumber) {
        return findByVehicleNumber(vehicleNumber) != null;
    }

    public VehicleOwner findOwnerByCitizenId(String citizenId) {
        if (citizenId == null) {
            return null;
        }
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getOwner().getCitizenId().equals(citizenId)) {
                return vehicle.getOwner();
            }
        }
        return null;
    }

    public int getVehicleCount() {
        return vehicleList.size();
    }
}
