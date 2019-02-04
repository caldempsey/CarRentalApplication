package licences;

import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

public class DrivingLicenceNumberTest {
    @Test
    public void getInstanceValid() throws Exception {
        // Instance Date Object.
        Date birth = new GregorianCalendar(1993, 3, 10).getTime();
        DrivingLicenceNumber licenceNumber = DrivingLicenceNumber.getInstance(birth, 'L', 'O');
        System.out.println(licenceNumber);
    }

    @Test
    public void getInstanceExtremeValid() throws Exception {
        // Instance Date Object.
        Date birth = new GregorianCalendar(1993, 3, 10).getTime();
        for (int i = 0; i < 10000; i++) {
            DrivingLicenceNumber licenceNumber = DrivingLicenceNumber.getInstance(birth, 'L', 'O');
            System.out.println(licenceNumber);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceInvalid() throws Exception {
        // Instance Date Object.
        Date birth = new GregorianCalendar(1991, 12, 10).getTime();
        for (int i = 0; i < 10000; i++) {
            DrivingLicenceNumber licenceNumber = DrivingLicenceNumber.getInstance(birth, 'f', 'f');
            System.out.println(licenceNumber);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceExtremeInvalid() throws Exception {
        // Instance Date Object.
        Date birth = new GregorianCalendar(1991, 12, 10).getTime();
        for (int i = 0; i < 10000; i++) {
            DrivingLicenceNumber licenceNumber = DrivingLicenceNumber.getInstance(birth, '1', '2');
            System.out.println(licenceNumber);
        }
    }

}