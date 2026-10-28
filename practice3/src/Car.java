/**
 * Car kế thừa (extends) Vehicle, bổ sung 2 thuộc tính riêng: số chỗ ngồi
 * và kiểu động cơ. Từ khóa "extends" thể hiện tính kế thừa (inheritance):
 * Car có đầy đủ các thuộc tính/phương thức của Vehicle, đồng thời có thêm
 * đặc điểm riêng của mình.
 */
public class Car extends Vehicle {

    private int numberOfSeats;
    private String engineType;

    public Car(String vehicleNumber, Manufacturer manufacturer, int manufactureYear,
               String color, VehicleOwner owner, int numberOfSeats, String engineType) {
        // super(...) gọi constructor của lớp cha để khởi tạo phần dữ liệu chung
        super(vehicleNumber, manufacturer, manufactureYear, color, owner);
        setNumberOfSeats(numberOfSeats);
        setEngineType(engineType);
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException("Số chỗ ngồi phải lớn hơn 0");
        }
        this.numberOfSeats = numberOfSeats;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        if (engineType == null || engineType.trim().isEmpty()) {
            throw new IllegalArgumentException("Kiểu động cơ không được để trống");
        }
        this.engineType = engineType;
    }

    // Ghi đè (override) phương thức abstract của lớp cha - đây là đa hình
    @Override
    public String getVehicleType() {
        return "Ô tô";
    }

    // Ghi đè toString(): gọi super.toString() rồi nối thêm thông tin riêng của Car
    @Override
    public String toString() {
        return "Car [" + super.toString()
                + ", numberOfSeats=" + numberOfSeats
                + ", engineType='" + engineType + "'"
                + "]";
    }
}
