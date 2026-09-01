import java.util.Scanner;

interface MedicalRecord {
    void addRecord();
    void displayRecord();
}

class Patient implements MedicalRecord {
    int id;
    String name;

    public void addRecord() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient ID: ");
        id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Patient Name: ");
        name = sc.nextLine();
    }

    public void displayRecord() {
        System.out.println("Patient ID: " + id);
        System.out.println("Patient Name: " + name);
    }
}

class Doctor implements MedicalRecord {
    int id;
    String name;

    public void addRecord() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Doctor ID: ");
        id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Doctor Name: ");
        name = sc.nextLine();
    }

    public void displayRecord() {
        System.out.println("Doctor ID: " + id);
        System.out.println("Doctor Name: " + name);
    }
}

public class HospitalManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.print("Enter Choice: ");
        int ch = sc.nextInt();

        MedicalRecord m;

        if (ch == 1)
            m = new Patient();
        else
            m = new Doctor();

        m.addRecord();
        System.out.println("\nRecord Details");
        m.displayRecord();
    }
}