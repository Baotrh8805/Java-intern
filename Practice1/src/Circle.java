public class Circle extends Shape{

    public Circle(double radius) {
        super(radius, radius);
    }
    public double getArea(){
        return Math.PI * getWidth() * getWidth();
    }

    public double getPerimeter(){
        return 2 * Math.PI * getWidth();
    }
    
}
