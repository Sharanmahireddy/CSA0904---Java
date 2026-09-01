import java.util.Scanner;

class Vehicle {
    String name;

    Vehicle(String name) {
        this.name = name;
    }

    void calculateRent(int days) {
    }
}

class Car extends Vehicle {
    Car(String name) {
        super(name);
    }

    void calculateRent(int days) {
        System.out.println(name + " Rent = " + (days * 1000));
    }
}

class Bike extends Vehicle {
    Bike(String name) {
        super(name);
    }

    void calculateRent(int days) {
        System.out.println(name + " Rent = " + (days * 500));
    }
}

class Bus extends Vehicle {
    Bus(String name) {
        super(name);
    }

    void calculateRent(int days) {
        System.out.println(name + " Rent = " + (days * 2000));
    }
}

public class VehicleRental {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Vehicle[] v = new Vehicle[3];

        System.out.print("Enter rental days: ");
        int days = sc.nextInt();

        v[0] = new Car("Car");
        v[1] = new Bike("Bike");
        v[2] = new Bus("Bus");

        System.out.println("\nRental Bills");
        for (Vehicle x : v) {
            x.calculateRent(days);
        }

        sc.close();
    }
}