package licences;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.YEAR;
import static org.junit.Assert.fail;

public class DrivingLicenceTest {
    @Test
    public void getInstanceValid() throws Exception {
        Date birth = new GregorianCalendar(1993, 3, 10).getTime();
        Date issue = new GregorianCalendar(1993, 3, 10).getTime();
        DrivingLicence licence = DrivingLicence.getInstance("Test", "Timmy", birth, issue, false);
        System.out.println(licence.toString());
    }

    @Test
    public void getInstanceExtremeValid() throws Exception {
        Date birth = new GregorianCalendar(1993, 3, 10).getTime();
        Date issue = new GregorianCalendar(1993, 3, 10).getTime();
        for (int i = 0; i < 10001; i++) {
            DrivingLicence licence = DrivingLicence.getInstance("Test", "Timmy", birth, issue, false);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceInvalid() throws Exception {
        Date birth = new GregorianCalendar(1993, 3, 10).getTime();
        Date issue = new GregorianCalendar(1993, 3, 10).getTime();
        DrivingLicence licence = DrivingLicence.getInstance("Te st", "T immy", birth, issue, false);
        System.out.println(licence.toString());

        birth = new GregorianCalendar(-1993, 3, 10).getTime();
        issue = new GregorianCalendar(-1993, 3, 10).getTime();
        licence = DrivingLicence.getInstance("Test", "Timmy", birth, issue, false);
        System.out.println(licence.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceExtremeInvalid() throws Exception {
        //Should print no information nothing as there is no licence to return.
        Date birth = new GregorianCalendar(19493, 33123, 1310).getTime();
        Date issue = new GregorianCalendar(191393, 33213, 1032).getTime();
        DrivingLicence licence = DrivingLicence.getInstance("Te st", "T immy", birth, issue, false);
        System.out.println(licence.toString());
        birth = new GregorianCalendar(-1312993, 333, 110).getTime();
        issue = new GregorianCalendar(-1993, 223, 1330).getTime();
        licence = DrivingLicence.getInstance("Test", "Timmy", birth, issue, false);
        System.out.println(licence.toString());
    }

    @Test
    public void getBirth() throws Exception {
        //Test defensive copying.
        Date birth = new GregorianCalendar(1993, 3, 10).getTime();
        Date issue = new GregorianCalendar(1993, 3, 10).getTime();
        DrivingLicence licence = DrivingLicence.getInstance("Test", "Timmy", birth, issue, false);
        birth = new GregorianCalendar(1337, 3, 10).getTime();
        //Test for object equality.
        if (birth.equals(licence.getBirth())) {
            fail();
        }

    }

    @Test
    public void getAge() throws Exception {
        // Define current time (today).
        Calendar today = Calendar.getInstance();
        int year = 1993;
        Date birth = new GregorianCalendar(year, 3, 10).getTime();
        Date issue = new GregorianCalendar(year, 3, 10).getTime();
        DrivingLicence licence = DrivingLicence.getInstance("Test", "Timmy", birth, issue, false);
        //Check to see if the supposed date by the method provides the correct licence age (by comparison with current date).
        if (!((today.get(YEAR) - 1993) == licence.getYearsHeld())) {
            fail();
        }
    }


}