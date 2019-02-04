package licences;

import org.junit.Test;

import static org.junit.Assert.*;

public class NameTest {
    @Test
    public void getInstanceValid() throws Exception {
        Name.getInstance("Testy", "Testherface");

    }

    @Test
    public void getInstanceExtremeValid() throws Exception {
        Name.getInstance("llllllllol", "cometome");

    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceInvalid() throws Exception {
        Name.getInstance("ffffffffffffirsty", "aaaaa3aasdddddddlasty");

    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceExtremeInvalid() throws Exception {
        Name.getInstance("43242", "213141515");

    }

    @Test
    public void getFirstName() throws Exception {
        Name name = Name.getInstance("Firstname", "Lastname");
        if (!(name.getFirstName().equals("FIRSTNAME"))) {
            fail();
        }
    }

    @Test
    public void getLastName() throws Exception {
        Name name = Name.getInstance("Firstname", "Lastname");
        if (!(name.getLastName().equals("LASTNAME"))) {
            fail();
        }
    }

    @Test
    public void getFirstInitial() throws Exception {
        Name name = Name.getInstance("Fresh", "Freddy");
        if (!(String.valueOf(name.getFirstInitial()).equals("F"))) {
            fail();
        }
    }

    @Test
    public void getLastInitial() throws Exception {
        Name name = Name.getInstance("Fresh", "Freddy");
        if (!(String.valueOf(name.getLastInitial()).equals("F"))) {
            fail();
        }
    }

}