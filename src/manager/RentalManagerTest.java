package manager;

import cars.Car;
import exceptions.GetInstanceLimitExceededException;
import licences.DrivingLicence;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;


public class RentalManagerTest {
    @Test
    public void addCarInstanceRestrictionValid() throws Exception {
        //Small Cars

        //Valid
        RentalManager.addCarInstanceRestriction("small", 1);

        //Extreme Valid.

        RentalManager.addCarInstanceRestriction("small", 9999);

        //Large Cars

        //Valid
        RentalManager.addCarInstanceRestriction("large", 1);

        //Extreme Valid.

        RentalManager.addCarInstanceRestriction("large", 9999);

    }


    @Test(expected = IllegalArgumentException.class)
    public void addCarInstanceRestrictionInvalid() throws Exception {
        //Small Cars

        //Invalid
        RentalManager.addCarInstanceRestriction("small", 0);

        //Extreme Invalid.

        RentalManager.addCarInstanceRestriction("small", -9999);

        //Large Cars

        //Invalid
        RentalManager.addCarInstanceRestriction("large", -1);

        //Extreme Invalid.

        RentalManager.addCarInstanceRestriction("large", -9999);
    }

    @Test
    public void createAvailableCarsValid() throws Exception {

        //Small Cars

        //Valid
        RentalManager.createAvailableCars("small", 1);

        //Extreme Valid.

        RentalManager.createAvailableCars("small", 5000);

        //Large Cars

        //Valid
        RentalManager.createAvailableCars("large", 1);

        //Extreme Valid.

        RentalManager.createAvailableCars("large", 5000);

    }


    @Test(expected = IllegalArgumentException.class)
    public void createAvailableCarsInvalid() throws Exception {

        //Small Cars

        //Invalid
        RentalManager.createAvailableCars("small", 0);

        //Extreme Invalid.

        RentalManager.createAvailableCars("small", -9999);

        //Large Cars

        //Invalid
        RentalManager.createAvailableCars("large", -1);

        //Extreme Invalid.

        RentalManager.createAvailableCars("large", -9999);

    }

    @Test
    public void addCarAgeRestrictionValid() throws Exception {
        //Small Cars

        //Valid
        RentalManager.addCarAgeRestriction("small", 1);

        //Extreme Valid.

        RentalManager.addCarAgeRestriction("small", 9999);

        //Large Cars

        //Valid
        RentalManager.addCarAgeRestriction("large", 1);

        //Extreme Valid.

        RentalManager.addCarAgeRestriction("large", 9999);

    }


    @Test
    public void addCarLicenceAgeRestrictionValid() throws Exception {

        //Small Cars

        //Valid
        RentalManager.addCarLicenceAgeRestriction("small", 1);

        //Extreme Valid.

        RentalManager.addCarLicenceAgeRestriction("small", 5000);

        //Large Cars

        //Valid
        RentalManager.addCarLicenceAgeRestriction("large", 1);

        //Extreme Valid.

        RentalManager.addCarLicenceAgeRestriction("large", 5000);

    }


    @Test(expected = IllegalArgumentException.class)
    public void addCarLicenceAgeRestrictionInvalid() throws Exception {

        //Small Cars

        //Invalid
        RentalManager.addCarLicenceAgeRestriction("small", 0);

        //Extreme Invalid.

        RentalManager.addCarLicenceAgeRestriction("small", -9999);

        //Large Cars

        //Invalid
        RentalManager.addCarLicenceAgeRestriction("large", -1);

        //Extreme Invalid.

        RentalManager.addCarLicenceAgeRestriction("large", -9999);

    }


    @Test()
    public void createAvailableCarsWithRestrictionValid() throws Exception {

        //Please note this test will return false if ran in conjunction with any other tests in the stack.
        //The reason for this is static variables adding additional objects (over the restrictions defined)

        //Valid
        RentalManager.addCarInstanceRestriction("small", 1);
        RentalManager.createAvailableCars("small", 1);
        RentalManager.addCarInstanceRestriction("large", 1);
        RentalManager.createAvailableCars("large", 1);

        //Extreme Valid. Change the restriction by overwriting the key, then test for validity with existing static instances in mind.
        RentalManager.addCarInstanceRestriction("small", 9999);
        RentalManager.createAvailableCars("small", 9998);
        RentalManager.addCarInstanceRestriction("large", 9999);
        RentalManager.createAvailableCars("large", 9998);
    }

    @Test(expected = GetInstanceLimitExceededException.class)
    public void createAvailableCarsWithRestrictionInvalid() throws Exception {

        //Invalid
        RentalManager.addCarInstanceRestriction("small", 1);
        RentalManager.createAvailableCars("small", 100);
        RentalManager.addCarInstanceRestriction("large", 1);
        RentalManager.createAvailableCars("large", 100);

        //Extreme invalid.
        RentalManager.addCarInstanceRestriction("small", 1);
        RentalManager.createAvailableCars("small", 999999999);
        RentalManager.addCarInstanceRestriction("large", 1);
        RentalManager.createAvailableCars("large", 999999999);

    }

    @Test
    public void getInstance() throws Exception {
        //Test Singleton Instantiation. Tests for creating one and only one instance of the Rental Manager class.
        RentalManager manager = RentalManager.getInstance();
        RentalManager manager2 = RentalManager.getInstance();
        if (!manager.equals(manager2)) {
            fail();
        }
    }

    @Test
    public void availableCarsValid() throws Exception {
        RentalManager manager = RentalManager.getInstance();
        //Valid
        RentalManager.addCarInstanceRestriction("small", 1);
        RentalManager.createAvailableCars("small", 1);
        manager.availableCars("small");
        System.out.println("availableCarsValid: " + manager.availableCars("small"));

        //Extreme Valid.
        RentalManager.addCarInstanceRestriction("large", 100);
        RentalManager.createAvailableCars("large", 99);
        manager.availableCars("large");
        System.out.println("availableCarsValid: " + manager.availableCars("large"));
    }


    @Test(expected = GetInstanceLimitExceededException.class)
    public void availableCarsInvalid() throws Exception {
        RentalManager manager = RentalManager.getInstance();
        //Invalid
        RentalManager.addCarInstanceRestriction("small", 1);
        RentalManager.createAvailableCars("small", 10);
        manager.availableCars("small");
        System.out.println("availableCarsInvalid: " + manager.availableCars("small"));

        //Extreme Invalid.
        RentalManager.addCarInstanceRestriction("small", -1);
        RentalManager.createAvailableCars("small", 100);
        manager.availableCars("small");
        System.out.println("availableCarsInvalid: " + manager.availableCars("small"));
    }

    @Test
    public void issueCar() throws Exception {
        //Instantiate
        RentalManager manager = RentalManager.getInstance();
        RentalManager.addCarInstanceRestriction("small", 100);
        RentalManager.createAvailableCars("small", 10);

        //Create a new Valid Licence
        Date birth = new GregorianCalendar(1975, 3, 10).getTime();
        Date issue = new GregorianCalendar(2000, 3, 10).getTime();
        DrivingLicence validLicence = DrivingLicence.getInstance("John", "Wick", birth, issue, true);
        manager.issueCar(validLicence, "small");

        //Validate if a car is successfully issued.
        Car car = manager.getCar(validLicence);
        if (!car.getTypeAsString().equals("SMALL")) {
            fail();
        }

        //Test Invalid Licence (Same Licence Twice)
        if ((manager.issueCar(validLicence, "small"))) {
            fail();
        }

        //Test New Licence, ensure the same car is not issued twice.

        //Create a new Valid Licence
        DrivingLicence validLicence2 = DrivingLicence.getInstance("Steel", "Wick", birth, issue, true);
        //Test Issue To ValidLicence2
        if (!(manager.issueCar(validLicence2, "small"))) {
            fail();
        }

        //Test cars for equality.
        if (manager.getCar(validLicence).equals(manager.getCar(validLicence2))) {
            fail();
        }

    }

    @Test
    public void getCar() throws Exception {
        //Instantiate
        RentalManager manager = RentalManager.getInstance();
        RentalManager.addCarInstanceRestriction("small", 100);
        RentalManager.createAvailableCars("small", 10);

        //Create a new Valid Licence
        Date birth = new GregorianCalendar(1975, 3, 10).getTime();
        Date issue = new GregorianCalendar(2000, 3, 10).getTime();
        DrivingLicence validLicence = DrivingLicence.getInstance("John", "Wick", birth, issue, true);
        System.out.println(manager.issueCar(validLicence, "small"));
        // Test getCar for instance.
        manager.getCar(validLicence);
    }

    @Test
    public void getRentedCars() throws Exception {
        //Instantiate
        RentalManager manager = RentalManager.getInstance();
        RentalManager.addCarInstanceRestriction("small", 100);
        RentalManager.createAvailableCars("small", 10);

        //Create a new Valid Licence
        Date birth = new GregorianCalendar(1993, 3, 10).getTime();
        Date issue = new GregorianCalendar(1993, 3, 10).getTime();
        DrivingLicence validLicence = DrivingLicence.getInstance("John", "Wick", birth, issue, true);
        manager.issueCar(validLicence, "small");
        manager.getCar(validLicence);
        // Test getRentedCars with getCar for equality: if these are equal then we can know the car returned by getRentedCars are the same objects as rentedCars.
        if (!manager.getCar(validLicence).equals(manager.getRentedCars().get(0))) {
            fail();
        }
    }

    @Test
    public void terminateRental() throws Exception {

        //Instantiate
        RentalManager manager = RentalManager.getInstance();
        RentalManager.addCarInstanceRestriction("small", 100);
        RentalManager.createAvailableCars("small", 10);

        //Create a new Valid Licence
        Date birth = new GregorianCalendar(1975, 3, 10).getTime();
        Date issue = new GregorianCalendar(2000, 3, 10).getTime();
        DrivingLicence validLicence = DrivingLicence.getInstance("John", "Wick", birth, issue, true);
        System.out.println(manager.issueCar(validLicence, "small"));
        // Test getCar for instance.
        manager.getCar(validLicence);
        //Issue two cars.
        manager.issueCar(validLicence, "small");
        //Terminate rental 1.
        int fuelToFill = manager.terminateRental(validLicence);
        //We didn't drive the car so if the value returned is not 0 then fail.
        if (fuelToFill != 0) {
            fail();
        }
    }

}