/**
 * Truck kế thừa Vehicle, bổ sung thuộc tính riêng là trọng tải (tấn).
 */
public class Truck extends Vehicle {

    private double tonnage;

    public Truck(String vehicleNumber, Manufacturer manufacturer, int manufactureYear,
                 String color, VehicleOwner owner, double tonnage) {
        super(vehicleNumber, manufacturer, manufactureYear, color, owner);
        setTonnage(tonnage);
    }

    public double getTonnage() {
        return tonnage;
    }

    public void setTonnage(double tonnage) {
        if (tonnage <= 0) {
            throw new IllegalArgumentException("Trọng tải phải lớn hơn 0");
        }
        this.tonnage = tonnage;
    }

    @Override
    public String getVehicleType() {
        return "Xe tải";
    }

    @Override
    public String toString() {
        return "Truck [" + super.toString()
                + ", tonnage=" + tonnage
                + "]";
    }
}
