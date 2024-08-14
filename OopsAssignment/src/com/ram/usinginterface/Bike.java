package com.ram.usinginterface;

public class Bike implements Vechicle{
    @Override
    public String Engine() {
        return "PETROL";
    }

    @Override
    public int EngineHorsepower() {
        return 650;
    }

    @Override
    public int seatscapacity() {
        return 2;
    }

    @Override
    public int topspeed() {
        return 190;
    }
}
