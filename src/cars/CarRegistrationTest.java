package cars;

import exceptions.GetInstanceLimitExceededException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarRegistrationTest {
    @Test
    public void getInstanceValid() throws Exception {
        CarRegistration registration = CarRegistration.getInstance();
        System.out.println(registration);
    }

    @Test
    public void getInstanceExtremeValid() throws Exception {
        CarRegistration registration = null;
        for (int i = 0; i < 20000; i++) {
            //Spawns instances to c0002
            registration = CarRegistration.getInstance();
        }
        if (!registration.toString().equals("c0002")) {
            fail();
        }
    }

    @Test(expected = GetInstanceLimitExceededException.class)
    public void getInstanceExtremeInvalid() throws Exception {
        //Since instances are encapsulated and immutable the only way to throw an error is by exceeding the limit "z9999".
        CarRegistration registration = null;
        for (int i = 0; i < 9999999; i++) {
            //Spawns instances to c0002
            registration = CarRegistration.getInstance();
            System.out.println(registration.toString());
        }

    }

    @Test
    public void getCharacter() throws Exception {
        CarRegistration registration = CarRegistration.getInstance();
        System.out.println(registration.getCharacter());
    }
}