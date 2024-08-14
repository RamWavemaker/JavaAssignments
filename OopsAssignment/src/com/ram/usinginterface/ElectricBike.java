package com.ram.usinginterface;

public class ElectricBike extends ElectricVehicle{
    @Override
    public int topspeed() {
        return 150;
    }

    @Override
    public int seatscapacity() {
        return 2;
    }

    @Override
    public int EngineHorsepower() {
        return 700;
    }

    @Override
    public String Engine() {
        return super.Engine();
    }
}
