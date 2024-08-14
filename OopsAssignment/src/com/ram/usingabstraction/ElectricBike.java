package com.ram.usingabstraction;

public class ElectricBike extends ElectricVehicle{
    @Override
    String Engine() {
        return super.Engine();
    }

    @Override
    int topspeed() {
        return 180;
    }

    @Override
    int seatscapacity() {
        return 2;
    }

    @Override
    int EngineHorsepower() {
        return 700;
    }
}
