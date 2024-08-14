package com.ram.usinginterface;

public class ElectricVehicle implements Vechicle{
    @Override
    public String Engine() {
        return "ELECTRIC";
    }

    @Override
    public int EngineHorsepower() {
        return 0;
    }

    @Override
    public int seatscapacity() {
        return 0;
    }

    @Override
    public int topspeed() {
        return 0;
    }
}
