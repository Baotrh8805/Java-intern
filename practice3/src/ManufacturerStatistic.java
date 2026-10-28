/**
 * Lớp hỗ trợ, dùng để lưu kết quả thống kê: một hãng sản xuất đi kèm
 * số lượng phương tiện của hãng đó. Được dùng làm kết quả trả về của
 * VehicleManagement.sortManufacturersByVehicleCountDescending().
 */
public class ManufacturerStatistic {

    private Manufacturer manufacturer;
    private int vehicleCount;

    public ManufacturerStatistic(Manufacturer manufacturer, int vehicleCount) {
        this.manufacturer = manufacturer;
        this.vehicleCount = vehicleCount;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    @Override
    public String toString() {
        return manufacturer + ": " + vehicleCount + " phương tiện";
    }
}
