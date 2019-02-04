package cars;

/**
 * The Car interface defines the methods of an instance type Car exposed to the outside world.
 * <p>
 * Classes which implement this interface should ensure that they are programming to the interface to ensure the ISA contract with the interface (and not to inherit behaviour).
 */
public interface Car {

    /**
     * getRegistration should get the car's registration.
     *
     * @return CarRegistration object.
     */
    CarRegistration getRegistration();

    /**
     * getFuelCapacity returns the car's fuel capacity.
     *
     * @return returns the cars fuel capacity.
     */
    int getFuelCapacity();

    /**
     * getFuelRemaining should return the cars fuel remaining.
     *
     * @return the fuel remaining of the car.
     */
    int getFuelRemaining();

    /**
     * isFuelFull should return whether the car has a full tank of fuel.
     *
     * @return true if the car has a full tank.
     */
    boolean isFuelFull();

    /**
     * isRented should return whether a car is rented;
     *
     * @return true if the car is rented.
     */
    boolean isRented();

    /**
     * setRented should allow us to set the car as rented.
     *
     * @param rented set whether the car is rented.
     */
    void setRented(boolean rented);

    /**
     * addFuel should allow us to add fuel to the car.
     *
     * @param fuelIn specifies the fuel input.
     * @return the fuel the car required to fill.
     */
    int addFuel(int fuelIn);

    /**
     * drive should allow us to drive the car for a set number of kilometres and return the value of fuel consumed during the journey.
     *
     * @param kilometres specifies the number of kilometres to drive the car.
     * @return value of fuel consumed during a journey.
     */
    int drive(int kilometres);

    /**
     * toString should return the object as a String.
     *
     * @return string representation of object.
     */
    String toString();

    /**
     * getTypeAsString should return the string representation of the cars type.
     *
     * @return the string representation of the type of car.
     */
    String getTypeAsString();

    /**
     * getFuelNeeded should specify how much fuel is required to fill up the car.
     *
     * @return the value of fuel required to fill the car.
     */
    int getFuelNeeded();

}

