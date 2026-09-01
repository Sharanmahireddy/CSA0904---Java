import java.util.Scanner;

class Employee {
    int id;
    String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    void calculateSalary() {}

    void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
    }
}

class PermanentEmployee extends Employee {
    double basic;

    PermanentEmployee(int id, String name, double basic) {
        super(id, name);
        this.basic = basic;
    }

    void calculateSalary() {
        display();
        System.out.println("Salary: " + (basic + basic * 0.3));
    }
}

class ContractEmployee extends Employee {
    int hours;
    double rate;

    ContractEmployee(int id, String name, int hours, double rate) {
        super(id, name);
        this.hours = hours;
        this.rate = rate;
    }

    void calculateSalary() {
        display();
        System.out.println("Salary: " + (hours * rate));
    }
}

public class PayrollSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1.Permanent 2.Contract");
        int ch = sc.nextInt();

        Employee e;

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        if (ch == 1) {
            System.out.print("Basic Salary: ");
            double basic = sc.nextDouble();
            e = new PermanentEmployee(id, name, basic);
        } else {
            System.out.print("Hours Worked: ");
            int h = sc.nextInt();
            System.out.print("Rate Per Hour: ");
            double r = sc.nextDouble();
            e = new ContractEmployee(id, name, h, r);
        }

        e.calculateSalary();
        sc.close();
    }
}