package com.ram.usingabstraction;

public class Bike extends Vehicle{

    @Override
    String Engine() {
        return "PETROL";
    }

    @Override
    int EngineHorsepower() {
        return 650;
    }

    @Override
    int seatscapacity() {
        return 2;
    }

    @Override
    int topspeed() {
        return 160;
    }
}
