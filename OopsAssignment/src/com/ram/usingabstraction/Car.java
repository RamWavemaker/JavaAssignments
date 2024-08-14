package com.ram.usingabstraction;

public class Car extends Vehicle{

    @Override
    String Engine() {
        return "DISEIL";
    }

    @Override
    int EngineHorsepower() {
        return 3000;
    }

    @Override
    int seatscapacity() {
        return 4;
    }

    @Override
    int topspeed() {
        return 200;
    }
}
