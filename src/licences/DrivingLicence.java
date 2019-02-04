package licences;

import exceptions.GetInstanceLimitExceededException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The DrivingLicence class represents an object type used to represent users driving licences.
 * <p>
 * These are built from various fields i.e. birth-date, issue-date, unique driving licence number object, unique name object .etc.
 * The class either utilizes immutable final field variables or provides defensive copying as necessary.
 * To illustrate, for birth-date access to Date objects are encapsulated by get methods which implement defensive copying approaches.
 * <p>
 * The DrivingLicence class utilizes a static factory method build pattern. Instances of the DrivingLicence class are guaranteed uniqueness in that they are provided them in a HashSet.
 * As per the conventions of a set this ensures the DrivingLicence is defined as an immutable object.
 * The DrivingLicence class overrides the .equals and .hashcode methods for direct comparison.
 */
public final class DrivingLicence {
    private static final Set<DrivingLicence> LICENCES = new HashSet<DrivingLicence>();
    private final Date birth;
    private final Date issue;
    private final DrivingLicenceNumber licenceNumber;
    private final Name name;
    private final boolean isFull;
    private final String strRep;

    /**
     * DrivingLicence is a constructor for a new DrivingLicence object.
     *
     * @param birth         the date of birth of the driving licence holder (as a Date object).
     * @param issue         the date of issue of the driving licence holder (as a Date object).
     * @param licenceNumber the licence number of the driving licence holder.
     * @param name          the name of the driving licence holder (as a Name object).
     * @param isFull        specifies whether the licence is a full licence
     * @param strRep        the string representation of the object.
     */
    private DrivingLicence(Date birth, Date issue, DrivingLicenceNumber licenceNumber, Name name, boolean isFull, String strRep) {
        this.birth = birth;
        this.licenceNumber = licenceNumber;
        this.name = name;
        this.isFull = isFull;
        this.strRep = strRep;
        this.issue = issue;
    }

    /**
     * getInstance returns a new instance of a DrivingLicence object given the respective parameters.
     *
     * @param firstName the first name of the licence holder.
     * @param lastName  the last name of the licence holder.
     * @param birth     the birth date of the licence holder.
     * @param issue     the date the licence is to be issued (this is specified in the event we need to update the collection of licences with old data). If the date of issue is expected to be "now" then this should be an appropriate Date object.
     * @param isFull    a boolean representing whether or not the licence is full.
     * @return returns a unique DrivingLicence object given the respective parameters.
     * @throws GetInstanceLimitExceededException a GetInstanceLimitExceededException should be handled by the client in the event that a requested instance cannot be generated.
     */
    public final static DrivingLicence getInstance(String firstName, String lastName, Date birth, Date issue, boolean isFull) throws GetInstanceLimitExceededException {
        /*
        Check parameters (since a public method throw exception if objects are null).
         */
        if (firstName == null || lastName == null || birth == null) {
            throw new IllegalArgumentException("Parameters cannot be a null object");
        } else {
        /*
        Defensive copying of mutable class.
         */
            birth = new Date(birth.getTime());
            issue = new Date(issue.getTime());
            /*
            Generate instance.
             */
            Name name = Name.getInstance(firstName, lastName);
            final char firstInitial = name.getFirstInitial();
            final char lastInitial = name.getLastInitial();
            DrivingLicenceNumber licenceNumber = DrivingLicenceNumber.getInstance(birth, firstInitial, lastInitial);
            final String strRep = licenceNumber.toString() + "[" + firstName + ", " + lastName + ", is full licence = " + isFull + "]";
            DrivingLicence drivingLicence = new DrivingLicence(birth, issue, licenceNumber, name, isFull, strRep);
            LICENCES.add(drivingLicence);
            return drivingLicence;
        }
    }

    /**
     * getLicence gets a licence by its string representation.
     *
     * @param licenceNumber specifies the string representation of the licence.
     * @return returns the DrivingLicence object corresponding to that licence.
     * @throws IllegalArgumentException throws an IllegalArgumentException if that licence does not exist be found.
     */
    public final static DrivingLicence getLicence(String licenceNumber) throws IllegalArgumentException {
        DrivingLicence licence = null;
        for (DrivingLicence licenceEntry : LICENCES) {
            if (licenceEntry.getLicenceNumber().toString().equals(licenceNumber)) {
                licence = licenceEntry;
            }
        }
        if (licence == null) {
            throw new IllegalArgumentException("The licence does not exist.");
        }
        return licence;
    }


    /**
     * getBirth allows access to the stored Date object of the date of birth of the licence holder.
     * Defensive copying techniques are in place to prevent mutability.
     *
     * @return returns the respective Date object corresponding to the licence holders birth date.
     */
    public final Date getBirth() {
        /*
        Defensive copying of mutable class.
         */
        return new Date(birth.getTime());
    }

    /**
     * getLicenceNumber allows access to the DrivingLicenceNumber object  number of the object.
     *
     * @return returns the DrivingLicenceNumber object corresponding to the driving licence holder.
     */
    public DrivingLicenceNumber getLicenceNumber() {
        return licenceNumber;
    }

    /**
     * getName allows access to the stored Name object of the name of the licence holder.
     *
     * @return returns the respective Name object corresponding to the licence holders name.
     */
    public final Name getName() {
        return name;
    }


    /**
     * getAge calculates the age of the driving licence holder and returns this value as an integer.
     *
     * @return returns the age of the driving licence holder.
     */
    public final int getAge() {
        Calendar birthDate = Calendar.getInstance();
        //Use the birth date from the stored Date object to set the time of the calender object (which retrieves the year).
        birthDate.setTime(getBirth());
        Calendar today = Calendar.getInstance();
        //Return as respective int.
        return today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
    }

    /**
     * getAge calculates the age of the driving licence and returns this value as an integer.
     *
     * @return returns the age of the driving licence.
     */
    public final int getYearsHeld() {
        Calendar issueDate = Calendar.getInstance();
        //Use the issue date from the stored Date object to set the time of the calender object (which retrieves the year).
        issueDate.setTime(issue);
        Calendar today = Calendar.getInstance();
        //Return as respective int.
        return today.get(Calendar.YEAR) - issueDate.get(Calendar.YEAR);
    }

    /**
     * isFull returns true only if the driving licence is a full licence.
     *
     * @return returns true only if the licence is a full licence.
     */
    public final boolean isFull() {
        return isFull;
    }

    /**
     * Overrides the toString method such that the string representation of an object is given by...
     * Licence Number + "[" + firstName + ", " + lastName + ", is full licence = " + isFull + "]
     *
     * @return returns the string representation of the object.
     */
    @Override
    public final String toString() {
        return strRep;
    }

    @Override
    public boolean equals(Object object) {
        /*
        Test for identity...
        Ensure that the object can be said true in and of itself (is reflexively equal). If so return true (is an instance of itself)
        */
        if (this == object) return true;
        /*
        Test for none-nullity...
        Use the == operator to check if the argument is null.
         */
        if (object == null) return false;
        /*
        Test for type...
        Check the object is of the correct instance.
         */
        if (!(object instanceof DrivingLicence)) {
            return false;
        }
        /*
        If the object is of the correct instance then we can perform a type conversion (or a cast) to this kind of object.
         */
        DrivingLicence licence = (DrivingLicence) object;
        return isFull == licence.isFull && getBirth().equals(licence.getBirth()) && licenceNumber.equals(licence.licenceNumber) && name.equals(licence.name) && strRep.equals(licence.strRep);
    }

    @Override
    public int hashCode() {
        int result = getBirth().hashCode();
        result = 31 * result + licenceNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (isFull ? 1 : 0);
        result = 31 * result + strRep.hashCode();
        return result;
    }
}
