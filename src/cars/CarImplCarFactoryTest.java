package cars;

import exceptions.GetInstanceLimitExceededException;
import org.junit.Test;

import static junit.framework.TestCase.fail;

/*
As an abstract class this tests the contract a particular implementation should extend (so tests the interface implementation).
A test of a concrete class would then extend the abstract class
 */
public class CarImplCarFactoryTest {
    @Test
    public void getInstanceValid() throws Exception {
        //Test parameters.
        Car car = CarImplCarFactory.getInstance("small");
        if (!car.getTypeAsString().equals("SMALL")) {
            fail();
        }
    }

    @Test
    public void getInstanceExtremeValid() throws Exception {
        //Test many instances.
        for (int i = 0; i < 1000; i++) {
            Car smallCar = CarImplCarFactory.getInstance("small");
            if (!smallCar.getTypeAsString().equals("SMALL")) {
                fail();
            }
        }
        for (int i = 0; i < 1000; i++) {
            Car largeCar = CarImplCarFactory.getInstance("large");
            if (!largeCar.getTypeAsString().equals("LARGE")) {
                fail();
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceInvalid() throws Exception {
        //Test parameters.
        Car car = CarImplCarFactory.getInstance(null);
        Car car2 = CarImplCarFactory.getInstance("not defined");
    }

    @Test(expected = GetInstanceLimitExceededException.class)
    public void getInstanceExtremeInvalid() throws Exception {
        //Test for too many instances. Custom exception defined.
        for (int i = 0; i < 1000000; i++) {
            Car smallCar = CarImplCarFactory.getInstance("small");
            if (!smallCar.getTypeAsString().equals("SMALL")) {
                fail();
            }
        }
    }

    @Test
    public void getRegistration() throws Exception {
        //Test for registration equality.
        Car smallCar = CarImplCarFactory.getInstance("small");
        Car largeCar = CarImplCarFactory.getInstance("large");
        if (smallCar.getRegistration().equals(largeCar.getRegistration())) {
            fail();
        }
    }

    @Test
    public void driveValid() throws Exception {
        Car smallCar = CarImplCarFactory.getInstance("small");
        smallCar.setRented(true);
        if (smallCar.drive(20) != 1) {
            fail();
        }
    }

    @Test
    public void driveValidExtreme() throws Exception {
        Car smallCar = CarImplCarFactory.getInstance("small");
        smallCar.setRented(true);
        // If driving the small car by 20 kilometres consumes 1 litre, then driving the car by 200000km should consume 100000 litres (as the method is responsible for handling the negative fuel value).
        if (smallCar.drive(200000) != 10000) {
            fail();
        }
    }

    @Test
    public void driveInvalid() throws Exception {
        Car smallCar = CarImplCarFactory.getInstance("small");
        //Do not drive with ridiculous values and ensure you do not drive when rented!
        smallCar.setRented(true);
        if (smallCar.drive(-1) != 0) {
            fail();
        }
        if (smallCar.drive(0) != 0) {
            fail();
        }
        smallCar.setRented(false);
        if (smallCar.drive(200000) == 10000) {
            fail();
        }
    }

    @Test
    public void driveInvalidExtreme() throws Exception {

        Car smallCar = CarImplCarFactory.getInstance("small");
        smallCar.setRented(true);
        //Do not drive with ridiculous values!
        if (smallCar.drive(-100000) != 0) {
            fail();
        }
    }

    @Test
    public void addFuelValid() throws Exception {
        Car smallCar = CarImplCarFactory.getInstance("small");
        smallCar.setRented(true);
        smallCar.drive(20);
        smallCar.addFuel(1);
        //Adding 1 litre to the tank should cover our expense...
        if (!(smallCar.isFuelFull())) {
            fail();
        }
    }

    @Test
    public void addFuelValidExtreme() throws Exception {
        Car smallCar = CarImplCarFactory.getInstance("small");
        smallCar.setRented(true);
        smallCar.drive(2000);
        System.out.println(smallCar.addFuel(10000));
        if (!(smallCar.isFuelFull())) {
            fail();
        }
    }


    @Test
    public void addFuelInvalid() throws Exception {
        Car smallCar = CarImplCarFactory.getInstance("small");
        smallCar.setRented(true);
        smallCar.drive(20);
        //Ensure test fails adding fuel if adding negative values (adds a value of 0).
        if (!(smallCar.addFuel(-1) == 0)) {
            fail();
        }
    }

    @Test
    public void addFuelInvalidExtreme() throws Exception {
        Car smallCar = CarImplCarFactory.getInstance("small");
        smallCar.setRented(true);
        smallCar.drive(2000000);
        //Ensure test fails adding fuel if adding negative values (adds a value of 0).
        if (!(smallCar.addFuel(-10000) == 0)) {
            fail();
        }
    }

    @Test
    public void isFuelFull() throws Exception {
        //All cars are instanced with a full tank.
        Car smallCar = CarImplCarFactory.getInstance("small");
        smallCar.setRented(true);
        if (!smallCar.isFuelFull()) {
            fail();
        }
        //Drive the car and add fuel and see if the tank is still full after...
        smallCar.drive(20);
        smallCar.addFuel(1);
        if (!smallCar.isFuelFull()) {
            fail();
        }

    }

    @Test
    public void getTypeAsString() throws Exception {
        Car smallCar = CarImplCarFactory.getInstance("small");
        Car largeCar = CarImplCarFactory.getInstance("large");
        if (!(smallCar.getTypeAsString().equals("SMALL") && largeCar.getTypeAsString().equals("LARGE"))) {
            fail();
        }

    }

}