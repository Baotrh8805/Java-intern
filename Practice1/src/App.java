public class App {
    public static void main(String[] args) throws Exception {
        Shape shape1 = new Shape();
        shape1.setWidth(5);
        shape1.setHeight(10);       
        Shape shape2 = new Shape();
        shape2.setWidth(7); 
        shape2.setHeight(3);
        Shape shape3 = new Shape();
        shape3.setWidth(4); 
        shape3.setHeight(6);
        System.out.println("Các kích thước của hình là: ");
        System.out.println("Hình 1: " + shape1.getWidth() + " x " + shape1.getHeight());
        System.out.println("Hình 2: " + shape2.getWidth() + " x " + shape2.getHeight());
        System.out.println("Hình 3: " + shape3.getWidth() + " x " + shape3.getHeight());

        Rectangle rectangle1 = new Rectangle(5,10);
        Rectangle rectangle2 = new Rectangle(7,3);
        Rectangle rectangle3 = new Rectangle(4,6);
        System.out.println("Diện tích hình chữ nhật 1: " + rectangle1.getArea());
        System.out.println("Chu vi hình chữ nhật 1: " + rectangle1.getPerimeter());
        System.out.println("Diện tích hình chữ nhật 2: " + rectangle2.getArea());
        System.out.println("Chu vi hình chữ nhật 2: " + rectangle2.getPerimeter());
        System.out.println("Diện tích hình chữ nhật 3: " + rectangle3.getArea());
        System.out.println("Chu vi hình chữ nhật 3: " + rectangle3.getPerimeter());

        Circle circle1 = new Circle(5);
        Circle circle2 = new Circle(7);
        Circle circle3 = new Circle(4);
        System.out.println("Diện tích hình tròn 1: " + circle1.getArea());
        System.out.println("Chu vi hình tròn 1: " + circle1.getPerimeter());
        System.out.println("Diện tích hình tròn 2: " + circle2.getArea());
        System.out.println("Chu vi hình tròn 2: " + circle2.getPerimeter());
        System.out.println("Diện tích hình tròn 3: " + circle3.getArea());
        System.out.println("Chu vi hình tròn 3: " + circle3.getPerimeter());    
          
    }
}
