package com.ram.usingabstraction;

public class ElectricCar extends ElectricVehicle{
    @Override
    int topspeed() {
        return 250;
    }


    @Override
    int seatscapacity() {
        return 4;
    }

    @Override
    int EngineHorsepower() {
        return 2000;
    }

    @Override
    String Engine() {
        return super.Engine();
    }
}
