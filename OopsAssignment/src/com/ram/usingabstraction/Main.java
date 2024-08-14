package com.ram.usingabstraction;

public class Main {
    public static void main(String[] args) {
        System.out.println("USING ABSTRACTION");
        System.out.println();
        Vehicle car = new Car();
        String carengine = car.Engine();
        int enginehorsepower = car.EngineHorsepower();
        int seatcapicity = car.seatscapacity();
        int topspeed = car.topspeed();

        System.out.println("Car");
        System.out.println("Engine : "+carengine);
        System.out.println("Horsepower : "+enginehorsepower);
        System.out.println("SeatCapacity : "+seatcapicity);
        System.out.println("TopSpeed : "+topspeed);
        System.out.println();


        Vehicle bike = new Bike();
        String bikeengine = bike.Engine();
        int bikeenginehorsepower = bike.EngineHorsepower();
        int bikeseatcapicity = bike.seatscapacity();
        int biketopspeed = bike.topspeed();

        System.out.println("Bike");
        System.out.println("Engine : "+bikeengine);
        System.out.println("Horsepower : "+bikeenginehorsepower);
        System.out.println("SeatCapacity : "+bikeseatcapicity);
        System.out.println("TopSpeed : "+biketopspeed);
        System.out.println();


        Vehicle electriccar = new ElectricCar();
        String elecarengine = electriccar.Engine();
        int elecarenginehorsepower = electriccar.EngineHorsepower();
        int elecarseatcapicity = electriccar.seatscapacity();
        int elecartopspeed = electriccar.topspeed();

        System.out.println("ElectricCar");
        System.out.println("Engine : "+elecarengine);
        System.out.println("Horsepower : "+elecarenginehorsepower);
        System.out.println("SeatCapacity : "+elecarseatcapicity);
        System.out.println("TopSpeed : "+elecartopspeed);
        System.out.println();


        Vehicle electricbike = new ElectricBike();
        String elebikeengine = electricbike.Engine();
        int elebikeenginehorsepower = electricbike.EngineHorsepower();
        int elebikeseatcapicity = electricbike.seatscapacity();
        int elebiketopspeed = electricbike.topspeed();

        System.out.println("ElectricBike");
        System.out.println("Engine : "+elebikeengine);
        System.out.println("Horsepower : "+elebikeenginehorsepower);
        System.out.println("SeatCapacity : "+elebikeseatcapicity);
        System.out.println("TopSpeed : "+elebiketopspeed);
        System.out.println();
    }
}
