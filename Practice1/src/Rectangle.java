public class Rectangle extends Shape{

    public Rectangle(double width, double height) {
        super(width, height);
    }

    public double getArea(){
        return getWidth() * getHeight();
    }

    public double getPerimeter(){
        return (getWidth() + getHeight()) * 2;
    }

}
