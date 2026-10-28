/**
 * Motorcycle kế thừa Vehicle, bổ sung thuộc tính riêng là dung tích/công suất.
 */
public class Motorcycle extends Vehicle {

    private double capacity;

    public Motorcycle(String vehicleNumber, Manufacturer manufacturer, int manufactureYear,
                       String color, VehicleOwner owner, double capacity) {
        super(vehicleNumber, manufacturer, manufactureYear, color, owner);
        setCapacity(capacity);
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Dung tích/công suất phải lớn hơn 0");
        }
        this.capacity = capacity;
    }

    @Override
    public String getVehicleType() {
        return "Xe máy";
    }

    @Override
    public String toString() {
        return "Motorcycle [" + super.toString()
                + ", capacity=" + capacity
                + "]";
    }
}
