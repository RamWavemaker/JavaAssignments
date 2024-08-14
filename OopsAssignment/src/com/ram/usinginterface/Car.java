package com.ram.usinginterface;

public class Car implements Vechicle{
    @Override
    public String Engine() {
        return "DIESEL";
    }

    @Override
    public int EngineHorsepower() {
        return 2000;
    }

    @Override
    public int seatscapacity() {
        return 4;
    }

    @Override
    public int topspeed() {
        return 230;
    }
}
