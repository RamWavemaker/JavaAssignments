package Models;

public class Addressimp implements Address{
    private String location;
    private int pin;

    public Addressimp(String location, int pin) {
        this.location = location;
        this.pin = pin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}

