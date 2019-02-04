package cars;

import exceptions.GetInstanceLimitExceededException;

/**
 * CarImplLarge is a class which inherits the behaviour of the superclass CarImplCarFactory (which defines default cars).
 * This class acts as a representation of large cars.
 * The package-private constructor cannot be directly instantiated by clients outside this package.
 * Please use CarImplCarFactory.getInstance("LARGE") instead.
 */
final class CarImplLargeCar extends CarImplCarFactory {

    /**
     * CarImplLargeCar is a constructor which posits the values of the large car implementation (all sub-types of the Car factory must at least have this behaviour).
     *
     * @throws GetInstanceLimitExceededException throws a GetInstanceLimitExceeded exception in the event that the instance cannot be created for the reason the instance limitations have been reached.
     */
    CarImplLargeCar() throws GetInstanceLimitExceededException {
        super(60, 10);
    }

    /**
     * drive is an overrode method as large cars are featured with special behaviour for the drive method: consumption rate changes from 10 to 15 after 50 kilometres.
     * The method allows the client to drive the car for a specified number of kilometres.
     *
     * @param kilometres specifies the kilometres to drive.
     * @return returns the value of fuel consumed by the car during the journey. This may be a negative value.
     */
    @Override
    public final int drive(int kilometres) {
         /*
        Define the value of fuel consumed as 0 (we have not driven yet).
         */
        int fuelConsumed = 0;
        /*
        Define a the increased consumption rate (for use after a set number of kilometres)
         */
        /*
        Check parameters, if the value of kilometres is negative then do nothing.
         */
        if (kilometres <= 0) {
            return fuelConsumed;
        }
        /*
        Drive / consume fuel, only if the car is rented.
         */
        if (isRented()) {
            /*
            ## SPECIAL CASE ##
            Increase fuel consumption "only if" the car has been driven for 50 kilometres (so beginning 51,52.etc).

            Since the value of fuel to be consumed is equal to the value of kilometres driven over the rate of consumption.
            If rate increases after 50 kilometres have been driven.
            Then if over 50 kilometres are driven then the value of fuel consumed is equal to...
            (Extra kilometres driven * the new rate of consumption)+(50 kilometres driven * standard rate of consumption).
             */
            if (kilometres > 50) {
                int extraKilometres = kilometres - 50;
                //Fuel consumed is *always* rounded up to the nearest whole integer.
                int fuelConsumedPost50Kilometres = (int) Math.ceil((double) extraKilometres / 15);
                int fuelConsumedPre50Kilometres = (int) Math.ceil((double) kilometres / getConsumptionRate());
                //Fuel consumed is *always* rounded up to the nearest whole integer.
                fuelConsumed = fuelConsumedPre50Kilometres + fuelConsumedPost50Kilometres;
            } else {
                return super.drive(kilometres);
            }
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
     * getTypeAsString returns the car type i.e. "LARGE" as a string.
     * All sub-classes of the CarImplCarFactory class must implement this behaviour.
     *
     * @return returns the car type as a String.
     */
    @Override
    public String getTypeAsString() {
        return "LARGE";
    }
}