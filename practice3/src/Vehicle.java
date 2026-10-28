import java.time.Year;

/**
 * Vehicle là lớp cha (abstract class) dùng chung cho mọi loại phương tiện
 * (Car, Motorcycle, Truck). Lớp cha chứa các thuộc tính và hành vi chung,
 * còn phần riêng biệt của từng loại xe sẽ do lớp con tự khai báo (kế thừa).
 *
 * Vehicle là abstract vì bản thân "một phương tiện chung chung" không tồn tại
 * trong thực tế - ta luôn phải tạo ra Car, Motorcycle hoặc Truck cụ thể.
 * Phương thức getVehicleType() được khai báo abstract để bắt buộc mỗi lớp
 * con phải tự định nghĩa loại xe của mình (đây là một dạng đa hình).
 */
public abstract class Vehicle {

    private String vehicleNumber;
    private Manufacturer manufacturer;
    private int manufactureYear;
    private String color;
    private VehicleOwner owner; // quan hệ has-a: một Vehicle "có một" VehicleOwner

    public Vehicle(String vehicleNumber, Manufacturer manufacturer, int manufactureYear,
                   String color, VehicleOwner owner) {
        setVehicleNumber(vehicleNumber);
        setManufacturer(manufacturer);
        setManufactureYear(manufactureYear);
        setColor(color);
        setOwner(owner);
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã số phương tiện không được để trống");
        }
        if (vehicleNumber.length() != 5) {
            throw new IllegalArgumentException("Mã số phương tiện phải có đúng 5 ký tự");
        }
        this.vehicleNumber = vehicleNumber;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("Hãng sản xuất không được để trống");
        }
        this.manufacturer = manufacturer;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        int currentYear = Year.now().getValue();
        if (manufactureYear <= 2000 || manufactureYear > currentYear) {
            throw new IllegalArgumentException(
                    "Năm sản xuất phải lớn hơn 2000 và không vượt quá " + currentYear);
        }
        this.manufactureYear = manufactureYear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("Màu xe không được để trống");
        }
        this.color = color;
    }

    public VehicleOwner getOwner() {
        return owner;
    }

    public void setOwner(VehicleOwner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Chủ phương tiện không được để trống");
        }
        this.owner = owner;
    }

    /**
     * Mỗi lớp con phải cho biết mình là loại phương tiện gì.
     * Đây là phương thức abstract - minh họa cho tính đa hình (polymorphism):
     * cùng một lời gọi getVehicleType() nhưng Car, Motorcycle, Truck sẽ
     * trả về kết quả khác nhau tùy vào đối tượng thực sự tại thời điểm chạy.
     */
    public abstract String getVehicleType();

    /**
     * toString() của lớp cha chỉ in các thông tin chung.
     * Các lớp con sẽ gọi super.toString() rồi nối thêm thông tin riêng.
     */
    @Override
    public String toString() {
        return "vehicleNumber='" + vehicleNumber + "'"
                + ", manufacturer=" + manufacturer
                + ", manufactureYear=" + manufactureYear
                + ", color='" + color + "'"
                + ", owner=[" + owner + "]";
    }
}
