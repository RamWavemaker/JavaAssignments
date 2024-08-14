package com.ram.usinginterface;

public class ElectricCar extends ElectricVehicle{
    @Override
    public int topspeed() {
        return 240;
    }

    @Override
    public int seatscapacity() {
        return 4;
    }

    @Override
    public int EngineHorsepower() {
        return 1800;
    }

    @Override
    public String Engine() {
        return super.Engine();
    }
}
