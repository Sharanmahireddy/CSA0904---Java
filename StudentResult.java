import java.util.Scanner;

class Student {
    int roll;
    String name;
    int m1, m2, m3;

    Student(int roll, String name, int m1, int m2, int m3) {
        this.roll = roll;
        this.name = name;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
    }

    int total() {
        return m1 + m2 + m3;
    }

    double average() {
        return total() / 3.0;
    }

    String grade() {
        double avg = average();

        if (avg >= 90)
            return "A";
        else if (avg >= 75)
            return "B";
        else if (avg >= 50)
            return "C";
        else
            return "Fail";
    }

    void display() {
        System.out.println("Roll No : " + roll);
        System.out.println("Name    : " + name);
        System.out.println("Total   : " + total());
        System.out.println("Average : " + average());
        System.out.println("Grade   : " + grade());
        System.out.println();
    }
}

public class StudentResult {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Number of Students: ");
        int n = sc.nextInt();

        Student[] s = new Student[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Roll No: ");
            int roll = sc.nextInt();
            sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Marks in 3 Subjects: ");
            int m1 = sc.nextInt();
            int m2 = sc.nextInt();
            int m3 = sc.nextInt();

            s[i] = new Student(roll, name, m1, m2, m3);
        }

        int max = s[0].total();
        int topper = 0;

        for (int i = 0; i < n; i++) {
            s[i].display();

            if (s[i].total() > max) {
                max = s[i].total();
                topper = i;
            }
        }

        System.out.println("Class Topper: " + s[topper].name);
        System.out.println("Topper Total: " + s[topper].total());

        sc.close();
    }
}