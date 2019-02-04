package manager;

import cars.Car;
import cars.CarImplCarFactory;
import exceptions.GetInstanceLimitExceededException;
import licences.DrivingLicence;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The RentalManager class is the core framework from which the rest of the application can be accessed which implements the singleton factory design pattern.
 * The RentalManager class features various maps to achieve different kinds of functionality with the objective of keeping the application as loosely coupled as possible.
 * To illustrate the availableCars list refers to the availableCars in the application. Since Java is a reference type language we can use a list to refer to a set of cars which we have designated as available and another set to refer to those we have rented.
 * This technique allows the class to manage collections of cars (of any type) as posited by the respective Car interface.
 * <p>
 * Similarly there are a number of static maps which refer types of cars (stored as strings) to different kinds of values.
 * <p>
 * To state one of the key conceptual difficulties when creating this class consider the following...
 * There exists an interface "Car" and an abstract class "DefaultCar" and two stemming classes "LargeCar" and "SmallCar" which include static factory instance methods to get instances of large car and small car (where you have static collections of cars in the "LargeCar" class, and another static collection of cars in the "SmallCar" class).
 * In what sense can restrictions to those cars be attributed to the RentalManager class, i.e. a limit on the number of instances of types of car, and still respect the ISA property of interfaces (since strictly speaking a "car" of any kind is not a "collection of cars", but is of a type of car) and without defining the type of car explicitly in its declaration of restrictions since only the AbstractFactory class for cars should have awareness of the kinds of cars that could be said concrete (as its responsibility is instancing them).
 * <p>
 * The solution devised is to generify by making the types arbitrary. This ensures de-coupling between any implementations of the Car by the respective AbstractFactory class and allows those restrictions to be defined as their respective String and Integer values before the RentalManager class is instances.
 * To illustrate, as opposed to allowing the RentalManager to have any sort of control in counting the number of "Small car" instances, the RentalManager makes requests for the number of cars of type "Small".
 * With this solution the RentalManager both aware of the maximum number of instances a particular car can have, and only aware of collections of cars as programmed by the interface of objects of type Car (but not aware of the existence of cars of any particular type).
 * This is implemented by maps of String, integer key-value pairs.
 *
 * @author Callum Dempsey Leach
 */
public final class RentalManager {

    /*
    Restrictions that exist in the system (key-value pairs).
     */
    private static final Map<String, Integer> CAR_INSTANCE_RESTRICTIONS = new HashMap<String, Integer>();
    private static final Map<String, Integer> CAR_AGE_RESTRICTIONS = new HashMap<String, Integer>();
    private static final Map<String, Integer> CAR_LICENCEAGE_RESTRICTIONS = new HashMap<String, Integer>();

    /*
    The available cars is a static method since the number of cars instances should not be done by objects of the RentalManager class (as it has the duty of managing those instances); although they must exist.
    If there exists a restriction imposed on the number of cars that can be created of a certain kind then they will not be populated into the referential list of available cars.
    */
    private static final List<Car> availableCars = new ArrayList<>();

    /*
    Ensuring all calls to getInstance return the same object reference (and no other object reference).
     */
    private static final RentalManager INSTANCE = new RentalManager();
    /*
    String representation of the clas.
     */
    private static final String strRep = "Rental Management System";
    /*
    Create a concrete map of rentedCars which will make use of the list of available cars defined above.
    The map ensures uniqueness in that multiple drivers cannot have multiple cars.
     */
    private final Map<DrivingLicence, Car> rentedCars = new HashMap<>();

    /**
     * Rental Manager is an constructor method as part of the Singleton factory pattern.
     */
    private RentalManager() {
        // The constructor implements all default static age restrictions to conform with the specification.
        addCarLicenceAgeRestriction("SMALL", 1);
        addCarLicenceAgeRestriction("LARGE", 5);
        addCarAgeRestriction("SMALL", 21);
        addCarAgeRestriction("LARGE", 25);
    }


    /**
     * getInstance method to ensure the RentalManager class is instantiated only once.
     *
     * @return Returns the RentalManager.
     */
    public static RentalManager getInstance() {
        return INSTANCE;
    }

    /**
     * addCarInstance is a method which allows us to limit the number of instances of any car of an arbitrary type (see class body for conceptual justification)
     *
     * @param typeOfCar      defines the type of car (converted into UPPERCASE). This must match the string representation of the cars type.
     * @param noMaxInstances defines the maximum number of instances the car should have.
     */
    static void addCarInstanceRestriction(String typeOfCar, int noMaxInstances) {
        /*
        Check input parameters.
         */
        if (typeOfCar == null) throw new IllegalArgumentException("Type of car cannot be null.");
        if (noMaxInstances <= 0) {
            throw new IllegalArgumentException("Value specified must be greater than 0");
        }
        CAR_INSTANCE_RESTRICTIONS.put(typeOfCar.toUpperCase(), noMaxInstances);
    }

    /**
     * addCarLicenceAgeRestriction is a method which allows us to limit access to any car of an arbitrary type to only users whose licences are of a specified number of years old.
     *
     * @param typeOfCar defines the type of car (converted into UPPERCASE). This must match the string representation of the cars type.
     * @param noYears   defines the maximum number of years valid drivers of the car should have.
     */
    static void addCarLicenceAgeRestriction(String typeOfCar, int noYears) {
        /*
        Check input parameters.
         */
        if (typeOfCar == null) throw new IllegalArgumentException("Type of car cannot be null.");
        if (noYears <= 0) {
            throw new IllegalArgumentException("Value specified must be greater than 0");
        }
        CAR_LICENCEAGE_RESTRICTIONS.put(typeOfCar.toUpperCase(), noYears);
    }

    /**
     * addCarAgeRestrictions is a method which allows us to limit access to any car of an arbitrary type to only users whose age is of a specified number of years old.
     *
     * @param typeOfCar defines the type of car (converted into UPPERCASE). This must match the string representation of the cars type.
     * @param noYears   defines the maximum number of years valid drivers of the car should have.
     */
    static void addCarAgeRestriction(String typeOfCar, int noYears) {
        /*
        Check input parameters.
         */
        if (typeOfCar == null) throw new IllegalArgumentException("Type of car cannot be null.");
        if (noYears <= 0) {
            throw new IllegalArgumentException("Value specified must be greater than 0");
        }
        CAR_AGE_RESTRICTIONS.put(typeOfCar.toUpperCase(), noYears);
    }

    /**
     * createAvailableCars is the primary mechanism by which having specified a car type and a number of instances, the car type and number of instances requested are checked before being sent to the respective AbstractFactory method to have the request fulfilled (or rejected).
     *
     * @param typeOfCar            defines the type of car (converted into UPPERCASE). This must match the string representation of the cars type.
     * @param noInstancesRequested defines the number of instances of that kind requested.
     * @throws GetInstanceLimitExceededException a GetInstanceLimit exceeded exception must be handled as the event is recoverable (to be handled by the client). This occurs when a getInstance request exceeds a defined maximum number of instances.
     */
    static void createAvailableCars(String typeOfCar, int noInstancesRequested) throws GetInstanceLimitExceededException {
        /*
        Check input parameters.
         */
        if (typeOfCar == null) {
            throw new IllegalArgumentException("Car type cannot be null.");
        }
        if (noInstancesRequested <= 0) {
            throw new IllegalArgumentException("Cannot generate zero or less instances of " + typeOfCar + "car.");
        }
        int noCarsOfType = 0;
        //By convention all car types should be stated in uppercase.
        typeOfCar = typeOfCar.toUpperCase();
        //If the map of restrictions contains a key for the type of car we would like to instantiate.
        if (CAR_INSTANCE_RESTRICTIONS.containsKey(typeOfCar)) {
            // For all cars available in the list of cars instantiated.
            for (Car availableCar : availableCars) {
                // If that car is equal to the same type.
                if (typeOfCar.equals(availableCar.getTypeAsString())) {
                    // Increment the counter.
                    noCarsOfType++;
                }
            }
            // If the number of cars of that type found plus the number of instances we would like to create exceed the defined limitation.
            if (noInstancesRequested + noCarsOfType > (CAR_INSTANCE_RESTRICTIONS.get(typeOfCar))) {
                // Throw an error (the method is not supposed to be used in this way.)
                throw new GetInstanceLimitExceededException("Creating " + noInstancesRequested + " of " + typeOfCar + " cars will exceed the valid number of instances specified by POPULATION_RESTRICTIONS.");
            }
        }
        // If this test passes we can proceed with population...
        for (int i = 0; i < noInstancesRequested; i++) {
            Car car = CarImplCarFactory.getInstance(typeOfCar);
            availableCars.add(car);
        }
    }

    /**
     * availableCars returns the number of cars available of a particular type. This is achieved by calling the size header of the getAvailableCarsOfType method (encapsulated).
     *
     * @param typeOfCar defines the type of car (converted into UPPERCASE). This must match the string representation of the cars type.
     * @return returns the number of available cars of a particular type.
     */
    int availableCars(String typeOfCar) {
        /*
        Check parameters.
        */
        assert typeOfCar != null;
        return getAvailableCarsOfType(typeOfCar).size();
    }

    /**
     * getRentedCars is a method to get a list of rented cars.
     *
     * @return returns a list of rented cars.
     */
    List<Car> getRentedCars() {
        return new ArrayList<Car>(rentedCars.values());
    }

    /**
     * getCarAgeRestriction returns the age restriction imposed an any particular type of car.
     *
     * @param typeOfCar defines the type of car (converted into UPPERCASE). This must match the string representation of the cars type.
     * @return returns the implementation details of the age restriction imposed on any specified type of car.
     */
    private int getCarAgeRestriction(String typeOfCar) {
        /*
        Check parameters.
        */
        assert typeOfCar != null;
        typeOfCar = typeOfCar.toUpperCase();
        Integer carAgeRestriction = CAR_AGE_RESTRICTIONS.get(typeOfCar);
        if (carAgeRestriction == null) {
            carAgeRestriction = 0;
        }
        return carAgeRestriction;
    }

    /**
     * getCarLicenceAgeRestriction returns the licence age restriction imposed an any particular type of car.
     *
     * @param typeOfCar defines the type of car (converted into UPPERCASE). This must match the string representation of the cars type.
     * @return returns the licence age restriction imposed on any specified type of car.
     */
    private int getCarLicenceAgeRestriction(String typeOfCar) {
        /*
        Check parameters.
        */
        assert typeOfCar != null;
        typeOfCar = typeOfCar.toUpperCase();
        Integer carLicenceAgeRestriction = CAR_LICENCEAGE_RESTRICTIONS.get(typeOfCar);
        if (carLicenceAgeRestriction == null) {
            carLicenceAgeRestriction = 0;
        }
        return carLicenceAgeRestriction;
    }

    /**
     * getAvailableCarsOfType extracts a list of the available cars of a particular type.
     *
     * @param typeOfCar defines the type of car (converted into UPPERCASE). This must match the string representation of the cars type.
     * @return returns returns a list of the available cars of a particular type.
     */
    private List<Car> getAvailableCarsOfType(String typeOfCar) {
        /*
        Check parameters.
         */
        assert typeOfCar != null;
        typeOfCar = typeOfCar.toUpperCase();
        List<Car> cars = new ArrayList<Car>();
        for (Car car : availableCars) {
            if (typeOfCar.equals(car.getTypeAsString())) {
                cars.add(car);
            }
        }
        return cars;
    }

    /**
     * getCar takes a DrivingLicence object and identifies any associated car object.
     *
     * @param drivingLicence specifies the DrivingLicence object.
     * @return returns the car associated with that licence.
     */
    Car getCar(DrivingLicence drivingLicence) {
        /*
        Check parameters.
        */
        assert drivingLicence != null;
        Car car = rentedCars.get(drivingLicence);
        if (car == null) {
            throw new IllegalArgumentException("There are no cars associated with the driving licence specified");
        }
        return car;
    }

    /**
     * isIssuedCar identifies if a car has been issued to the queried DrivingLicence object.
     *
     * @param drivingLicence specifies the DrivingLicence object to lookup.
     * @return returns whether or not the driving licence has been issued a car.
     */
    private boolean isIssuedCar(DrivingLicence drivingLicence) {
        /*
        Check parameters.
        */
        assert drivingLicence != null;
        return rentedCars.containsKey(drivingLicence);
    }

    /**
     * issueCar issues a care only if it is the case...
     * (a) The person renting the car has a full drivers licence.
     * (b) The person renting the car has not already rented a car.
     * (c) All persons renting a car are bound by static restrictions set i.e. a defined restriction that they must be at least 21 years old or a defined restriction that they must be at least 25 years old (default).
     *
     * @param drivingLicence specifies the DrivingLicence object to issue a car to.
     * @param typeOfCar      specifies the type of car to issue.
     * @return returns true if successful, returns false otherwise.
     * @throws GetInstanceLimitExceededException in the event the instance limit has been reached for any particular class should be handled appropriately.
     */
    boolean issueCar(DrivingLicence drivingLicence, String typeOfCar) throws GetInstanceLimitExceededException {

        /*
        Check parameters.
         */
        assert drivingLicence != null;
        assert typeOfCar != null;
        typeOfCar = typeOfCar.toUpperCase();
        //Check if the driver is eligible to rent the car type specified.
        if ((drivingLicence.getYearsHeld() < getCarLicenceAgeRestriction(typeOfCar)) ||
                (drivingLicence.getAge() < (getCarAgeRestriction(typeOfCar)) && getCarAgeRestriction(typeOfCar) != 0) ||
                (!drivingLicence.isFull()) ||
                isIssuedCar(drivingLicence)) {
            return false;
        }

        // Define a null instance of a car.
        Car car = null;
        int index = 0;
        int carPosition = 0;
        //Identify whether or not there exists an available car of that type and if that car is rented and if that car is available.
        for (Car availableCar : availableCars) {
            if (availableCar.getTypeAsString().equals(typeOfCar) && !(availableCar.isRented()) && availableCar.isFuelFull()) {
                //If the tests passed then assign the car as the available car. Attain the reference to that object and its position in the array.
                car = availableCar;
                carPosition = index;
                break;
            }
            index++;
        }
        //If we found a car this condition will fail.
        if (car != null) {
            rentedCars.put(drivingLicence, car);
            car.setRented(true);
            availableCars.remove(carPosition);
            return true;
        }
        //If we didn't find a car, return false.
        return false;
    }

    /**
     * terminatesRental
     *
     * @param drivingLicence defines the DrivingLicence object to terminate the rental of.
     * @return returns the value of fuel required to fill up the tank post-rental.
     * @throws InvalidObjectException safety check the event that the object is in an inconsistent state of both rented and not rented (as internally determined).
     */
    int terminateRental(DrivingLicence drivingLicence) throws InvalidObjectException {
        assert drivingLicence != null;
        //Define a placeholder Car as car null.
        Car car = null;
        //Iterate through map of rented driving licences and cars.
        for (Map.Entry<DrivingLicence, Car> entry : rentedCars.entrySet()) {
            // If the key is found.
            if (entry.getKey().equals(drivingLicence)) {
                // Ensure the car is rented.
                if (!(entry.getValue().isRented())) {
                    //If it isn't, the object is in an inconsistent state and this should be handled.
                    throw new InvalidObjectException("Car " + entry.getValue().toString() + " in an inconsistent state of rented and not rented.");
                }
                // If the validation test passes then we need to terminate the rental agreement, we do this at the end of the method.
                car = entry.getValue();
            }
        }
        //If car is not null then an associate DrivingLicence has been found.
        if (car != null) {
            rentedCars.remove(drivingLicence);
            car.setRented(false);
            return car.getFuelNeeded();
        }
        return 0;
    }

    /**
     * toString provides the string representation of instances of the class. Since we generate a singleton this is overridden to "Rental Management System".
     *
     * @return returns the string representation of the object (static).
     */
    @Override
    public String toString() {
        return strRep;
    }
}
