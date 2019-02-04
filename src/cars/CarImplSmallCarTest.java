package cars;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

public class CarImplSmallCarTest {
    @Test
    public void getTypeAsString() throws Exception {
        Car smallCar = CarImplCarFactory.getInstance("small");
        if (!(smallCar.getTypeAsString().equals("SMALL"))) {
            fail();
        }
    }
}