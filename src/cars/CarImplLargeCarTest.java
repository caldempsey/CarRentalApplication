package cars;

import org.junit.Test;

import static junit.framework.TestCase.fail;


public class CarImplLargeCarTest {
    @Test
    public void driveValid() throws Exception {
        Car largeCar = CarImplCarFactory.getInstance("large");
        largeCar.setRented(true);
        if (largeCar.drive(10) != 1) {
            fail();
        }
        if (largeCar.drive(20) != 2) {
            fail();
        }
        if (largeCar.drive(30) != 3) {
            fail();
        }
        if (largeCar.drive(40) != 4) {
            fail();
        }
        if (largeCar.drive(50) != 5) {
            fail();
        }
        //1 kilometres extra per 5 kilometres PROCEEDING 51 kilometres, so skip 6.
        if (largeCar.drive(55) != 7) {
            fail();
        }
        if (largeCar.drive(61) != 8) {
            fail();
        }
    }

    @Test
    public void driveValidExtreme() throws Exception {
        Car largeCar = CarImplCarFactory.getInstance("large");
        largeCar.setRented(true);
        if (largeCar.drive(1000) != 164) {
            fail();
        }
    }

    @Test
    public void driveInvalid() throws Exception {
        Car largeCar = CarImplCarFactory.getInstance("large");
        largeCar.setRented(true);
        if (largeCar.drive(-1) != 0) {
            fail();
        }
    }

    @Test
    public void driveInvalidExtreme() throws Exception {
        Car largeCar = CarImplCarFactory.getInstance("large");
        largeCar.setRented(true);
        if (largeCar.drive(-10000) != 0) {
            fail();
        }
    }

    @Test
    public void getTypeAsString() throws Exception {
        Car largeCar = CarImplCarFactory.getInstance("large");
        if (!(largeCar.getTypeAsString().equals("LARGE"))) {
            fail();
        }
    }

}