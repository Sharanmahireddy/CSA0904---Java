import java.util.Scanner;

class Admission {

    void calculateFee() {
        System.out.println("Undergraduate Fee: Rs.50000");
    }

    void calculateFee(int pg) {
        System.out.println("Postgraduate Fee: Rs.70000");
    }

    void calculateFee(double scholarship) {
        double fee = 50000 - scholarship;
        System.out.println("Scholarship Student Fee: Rs." + fee);
    }
}

public class UniversityAdmission {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Admission a = new Admission();

        System.out.println("1. Undergraduate");
        System.out.println("2. Postgraduate");
        System.out.println("3. Scholarship Student");
        System.out.print("Enter Choice: ");
        int ch = sc.nextInt();

        switch (ch) {
            case 1:
                a.calculateFee();
                break;

            case 2:
                a.calculateFee(1);
                break;

            case 3:
                System.out.print("Enter Scholarship Amount: ");
                double amount = sc.nextDouble();
                a.calculateFee(amount);
                break;

            default:
                System.out.println("Invalid Choice");
        }

        sc.close();
    }
}