package com.ram.usingabstraction;

public class ElectricVehicle extends Vehicle{
    @Override
    String Engine() {
        return "ELECTRIC";
    }

    @Override
    int EngineHorsepower() {
        return 0;
    }

    @Override
    int seatscapacity() {
        return 0;
    }

    @Override
    int topspeed() {
        return 0;
    }
}
