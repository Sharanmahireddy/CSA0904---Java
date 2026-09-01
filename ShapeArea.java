abstract class Shape {
    abstract void area();
}

class Circle extends Shape {
    double r;

    Circle(double r) {
        this.r = r;
    }

    void area() {
        System.out.println("Circle Area = " + (3.14 * r * r));
    }
}

class Rectangle extends Shape {
    double l, b;

    Rectangle(double l, double b) {
        this.l = l;
        this.b = b;
    }

    void area() {
        System.out.println("Rectangle Area = " + (l * b));
    }
}

class Triangle extends Shape {
    double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    void area() {
        System.out.println("Triangle Area = " + (0.5 * base * height));
    }
}

public class ShapeArea {
    public static void main(String[] args) {

        Shape[] s = new Shape[3];

        s[0] = new Circle(5);
        s[1] = new Rectangle(4, 6);
        s[2] = new Triangle(8, 5);

        for (Shape x : s) {
            x.area();
        }
    }
}