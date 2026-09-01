class Device {
    String name;

    Device(String name) {
        this.name = name;
    }

    void on() {
        System.out.println(name + " ON");
    }

    void off() {
        System.out.println(name + " OFF");
    }

    void power() {
    }
}

class Light extends Device {
    Light(String name) {
        super(name);
    }

    void power() {
        System.out.println("Power Consumption: 20 W");
    }
}

class Fan extends Device {
    Fan(String name) {
        super(name);
    }

    void power() {
        System.out.println("Power Consumption: 75 W");
    }
}

class AirConditioner extends Device {
    AirConditioner(String name) {
        super(name);
    }

    void power() {
        System.out.println("Power Consumption: 1500 W");
    }
}

public class SmartHome {
    public static void main(String[] args) {

        Device[] d = new Device[3];

        d[0] = new Light("Light");
        d[1] = new Fan("Fan");
        d[2] = new AirConditioner("Air Conditioner");

        for (Device x : d) {
            x.on();
            x.power();
            x.off();
            System.out.println();
        }
    }
}