package cars;

import exceptions.GetInstanceLimitExceededException;

/**
 * CarImplSmallCar is a class which inherits the behaviour of the superclass CarImplCarFactory (which defines default cars).
 * This class acts as a representation of small cars.
 * The package-private constructor cannot be directly instantiated by clients outside this package.
 * Please use CarImplCarFactory.getInstance("SMALL") instead.
 */
final class CarImplSmallCar extends CarImplCarFactory {

    /**
     * CarImplSmallCar is a constructor which posits the values of the small car implementation (all sub-types of the Car factory must at least have this behaviour).
     *
     * @throws GetInstanceLimitExceededException throws a GetInstanceLimitExceeded exception in the event that the instance cannot be created for the reason the instance limitations have been reached.
     */
    CarImplSmallCar() throws GetInstanceLimitExceededException {
        super(49, 20);
    }

    /**
     * getTypeAsString returns the car type i.e. "SMALL" as a string.
     * All sub-classes of the CarImplCarFactory class must implement this behaviour.
     *
     * @return returns the car type as a String.
     */
    @Override
    public final String getTypeAsString() {
        return "SMALL";
    }

}
