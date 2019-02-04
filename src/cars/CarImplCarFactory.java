package cars;

import exceptions.GetInstanceLimitExceededException;
import java.util.HashMap;
import java.util.Map;

/**
 * CarImplCarFactory provides a static factory method pattern for instantiating unique instances of its sub-class types and also acts as the default implementation for all sub-class types of the class (for non-static or concrete objects).
 * As such it provides a static map of cars of each type it is able to instance and provides the default behaviour as implemented by the Car interface and defines the minimum attributes a member of the class must have in order to be considered implementable.
 * These are a fuel capacity and consumption rate in addition to the set of methods defined by the interface.
 * <p>
 * Since Java is a reference type language all instances are stored in the static maps provided.
 * To provide easier implementation of more kinds of sub-class types i.e. "BMWCar" the getInstance method for the static factory provides a switch-case which identifies different car types with the respective static maps.
 * Here it is important to note that all requests to the getInstance method, i.e. for a small car, are represented by an uppercase string i.e. "SMALL".
 */
public abstract class CarImplCarFactory implements Car {

    /*
    Define static maps of String (Car String Representation) to Car objects.
    If implementing remember not to put mutable car objects in a Set (hence the key value pair defined below).
     */
    private static final Map<String, Car> SMALL_CARS = new HashMap<>();
    private static final Map<String, Car> LARGE_CARS = new HashMap<>();
    /*
    Field variable for cars.
     */
    private final CarRegistration registration;
    private final int fuelCapacity;
    private final double fuelConsumptionRate;
    private boolean isRented;
    private int fuelRemaining;

    /**
     * CarImplCarFactory is a constructor responsible for building car objects. All cars must have at least the following behaviour (defined by params)
     *
     * @param fuelCapacity    the capacity of the fuel tank in Litres.
     * @param consumptionRate the rate of consumption of the fuel tank in Kilometres/Litre.
     * @throws GetInstanceLimitExceededException a GetInstanceLimitExceededException is thrown if the constructor (for internal reasons) is unable to produce any more cars of the specified type.
     */
    CarImplCarFactory(int fuelCapacity, double consumptionRate) throws GetInstanceLimitExceededException {
        /*
        Check parameters.
         */
        if ((fuelCapacity <= 0)) {
            throw new IllegalArgumentException("Fuel capacity cannot be a value lower than or equal to 0.");
        }
        if ((consumptionRate <= 0)) {
            throw new IllegalArgumentException("Consumption rate cannot be a value lower than or equal to 0.");
        }
        this.fuelCapacity = fuelCapacity;
        this.fuelConsumptionRate = consumptionRate;
        this.setFuelRemaining(fuelCapacity);
        this.isRented = false;
        registration = CarRegistration.getInstance();
    }

    /**
     * getInstance is the primary mechanism responsible for instantiating new objects of any given subclass type.
     * In order to add a new kind of object to the getInstance static factory method, first a respective Map must be defined (see above) and secondly the method must be written to the logic to posit the type.
     * The type is posited by conversion of the inbound string typeOfCar to upper-case, and then checked against the respective case arguments.
     *
     * @param typeOfCar the type of the car to generate an instance of.
     * @return return the instance of the car requested.
     * @throws GetInstanceLimitExceededException a GetInstanceLimitExceededException is thrown if the factory method (for internal reasons) is unable to produce any more cars of the specified type.
     */
    public static Car getInstance(String typeOfCar) throws GetInstanceLimitExceededException {
        /*
        Check parameters (since a public method throw exception if objects are null).
         */
        if (typeOfCar == null) {
            throw new IllegalArgumentException("The car type specified cannot be a null object.");
        }

        typeOfCar = typeOfCar.toUpperCase();
        //Implicit check using switch-case tests typeOfCar.equals(case)
        switch (typeOfCar) {
            case "SMALL": {
                Car car = new CarImplSmallCar();
                SMALL_CARS.put(car.toString(), car);
                return car;
            }
            case "LARGE": {
                Car car = new CarImplLargeCar();
                LARGE_CARS.put(car.toString(), car);
                return car;
            }
            default:
                throw new IllegalArgumentException("The car type specified is an invalid type.");
        }
    }

    /**
     * getRegistration returns the car's registration.
     *
     * @return returns the car registration object associated with the car.
     */
    @Override
    public final CarRegistration getRegistration() {
        return registration;
    }

    /**
     * getFuelCapacity returns the car's fuel capacity.
     *
     * @return returns the cars fuel capacity.
     */

    @Override
    public final int getFuelCapacity() {
        return fuelCapacity;
    }

    /**
     * getFuelRemaining returns the car's fuel remaining.
     *
     * @return returns the cars fuel remaining.
     */
    @Override
    public final int getFuelRemaining() {
        return fuelRemaining;
    }

    /**
     * setFuelRemaining sets the car's fuel remaining (is package private). This (by design) allows for negative values.
     * Setting over the maximum capacity of the tank will default to the value of the maximum capacity of the tank.
     * @param fuelRemaining specify the value to change fuelRemaining to.
     */
    void setFuelRemaining(int fuelRemaining) {
        if (fuelRemaining > fuelCapacity) {
            this.fuelRemaining = fuelCapacity;
        } else {
            this.fuelRemaining = fuelRemaining;
        }
    }

    /**
     * isFuelFull returns whether or not the fuel is full.
     *
     * @return return true if the fuel is full.
     */
    @Override
    public final boolean isFuelFull() {
        return (getFuelRemaining() == fuelCapacity);
    }

    /**
     * isRented returns whether or not the car is rented.
     *
     * @return return true if the car is rented.
     */
    @Override
    public boolean isRented() {
        return isRented;
    }

    /**
     * setRented sets whether the car is rented to a boolean.
     */
    @Override
    public void setRented(boolean rented) {
        isRented = rented;
    }

    /**
     * getConsumptionRate provides access to the consumption rate.
     *
     * @return returns the fuel consumption rate.
     */
    double getConsumptionRate() {
        return fuelConsumptionRate;
    }

    /**
     * getFuelNeeded calculates how much fuel would be needed to fill up the tank.
     *
     * @return returns how much fuel (Litres) would be required to fill up the tank.
     */
    @Override
    public final int getFuelNeeded() {
        int fuelNeeded = 0;
        //If the fuel remaining is smaller than 0, then we don't want to perform a -- operation (and provide an incorrect value)
        if (fuelRemaining < 0) {
            //We can overcome this problem by first turning the value into a positive value "fuelNeeded".
            fuelNeeded = fuelNeeded + Math.abs(fuelRemaining);
            //And adding that value to the maximum capacity of the tank. This will report the value as maximum value of the tank + value owed.
            return (fuelNeeded + fuelCapacity);
        }
        //If that's not the case a regular calculation will suffice.
        fuelNeeded = fuelCapacity - fuelRemaining;
        return fuelNeeded;
    }

    /**
     * addFuel adds fuel to the car.
     *
     * @return returns how much fuel was added to the car.
     */
    @Override
    public final int addFuel(int fuelIn) {
        /*
        Define how much fuel *will be* added.
        */
        int addFuel = 0;
        /*
        Add fuel to the tank only if the value of fuel to be added is greater than 0 (else simply don't add fuel).
        */
        if (fuelIn > 0) {
            /*
            If the fuel remaining plus the fuel in is greater than the capacity of the tank.
            */
            if (fuelRemaining + fuelIn > fuelCapacity) {
            /*
            The fuel added will be equal to the capacity of the tank take the fuel remaining (since the tank is then full).
             */
                if (fuelRemaining > 0) {
                    //If the fuel remaining is a negative number than the fuel added must account for this.
                    // This is calculated as the result of the fuel remaining - fuel capacity (avoiding double negatives) converted to positive.
                    // To illustrate, if we have a car which has -1 fuel and we need to reach the capacity of the tank 10 fuel, then we add -10-1=[-11, as a positive, so 11] fuel
                    addFuel = Math.abs(fuelRemaining - fuelCapacity);
                } else {
                    addFuel = fuelCapacity - fuelRemaining;
                }
            } else {
                //Otherwise the fuel added will be equal to the value of fuel inbound to the tank.
                addFuel = fuelIn;
            }
            /*
            Add that fuel to the tank.
             */
            setFuelRemaining(fuelRemaining + addFuel);
        }
        //Assert that the value of fuel added is either 0 or greater than 0 and is not over the value of the maximum tank capacity.
        assert addFuel >= 0;
        //Return the result.
        return addFuel;
    }

    /**
     * drive is a method which allows the client to drive the car for a specified number of kilometres.
     *
     * @param kilometres specifies the kilometres to drive.
     * @return returns the value of fuel consumed by the car during the journey. This may be a negative value.
     */
    @Override
    public int drive(int kilometres) {
        /*
        Define the value of fuel consumed as 0 (we have not driven yet).
         */
        int fuelConsumed = 0;
        /*
        Check parameters, if the value of kilometres is negative then do nothing.
         */
        if (kilometres <= 0) {
            return fuelConsumed;
        }
        /*
        Drive / consume fuel, only if the car is rented.
         */
        if (isRented) {
            /*
            The value of fuel to be consumed is equal to the value of kilometres driven over the rate of consumption.
             */
            fuelConsumed = (int) Math.ceil((double) kilometres / fuelConsumptionRate);
            /*
            The fuel remaining may be a negative value as specified (so the calculation is simplistic).
             */
            int setFuelRemaining = getFuelRemaining() - fuelConsumed;
            setFuelRemaining(setFuelRemaining);
        }
        //Return the result.
        return fuelConsumed;
    }

    /**
     * getTypeAsString returns the car type as a string.
     * This method must be implemented in any subclass.
     *
     * @return returns the car type as a string.
     */
    @Override
    public abstract String getTypeAsString();

    /**
     * toString returns the car as a string.
     *
     * @return returns the following formatted string "CarType[carRegistration]".
     */
    @Override
    public String toString() {
        return getTypeAsString() + "[" + getRegistration().toString() + "]";
    }

}
