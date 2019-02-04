package licences;

import exceptions.GetInstanceLimitExceededException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The DrivingLicenceNumber class is an immutable class which stores DrivingLicenceNumber object.
 * The class is responsible for generating DrivingLicencenNumbers to the defined format "[First Initial][Last Initial]-[Birth date (YEAR)]-[Unique Serial Number]".
 * A DrivingLicenceNumber is always unique to serial i.e. if AA-1993-01 exists, the next licence number will be AA-1993-02, or AB-1993-01, and so on.
 * Only the string representation of those numbers is stored. This information is stored in a set to guarantee uniqueness.
 */
final class DrivingLicenceNumber {
    private final static Set<DrivingLicenceNumber> DRIVING_LICENCE_NUMBERS = new HashSet<>();
    private final String strRep;

    /**
     * DrivingLicenceNumber is a constructor for a DrivingLicenceNumber object by given string representation.
     * This should only be accessed by the getInstance method of the class. The static keyword entails that this method cannot be accessed by any instance of the class.
     *
     * @param strRep specifies the string representation of the object.
     */
    private DrivingLicenceNumber(String strRep) {
        this.strRep = strRep;
    }


    /**
     * getInstance returns a new unique DrivingLicenceNumber with the given parameters each time it is called.
     * Getting a licence should always return a unique instance (there should be no possible duplicate licences).
     * For this reason whenever a new licence number is created it cannot be re-produced: it is not possible to get the instance of a previous licence number from a static context.
     *
     * @param birth        specifies the birth-date of the driving licence holder.
     * @param firstInitial specifies the first initial to be used for the driving licence holder.
     * @param lastInitial  specifies the last initial to be used for the driving licence holder.
     * @return returns a unique DrivingLicenceNumber object.
     * @throws GetInstanceLimitExceededException in the event that the maximum number of possible instances have been reached (this should be handled).
     */
    public final static DrivingLicenceNumber getInstance(Date birth, char firstInitial, char lastInitial) throws GetInstanceLimitExceededException {
        /*
        Check parameters.
         */
        assert birth != null;

        /*
        Defensive copying for the mutable class "Date": without this it would be possible to use the reference of the parameter passed to the constructor to make changes (note the 'getTime' method returns a long value).
         */
        birth = new Date(birth.getTime());

        /*
        Type conversion of input parameters into a string for validation.
         */
        String firstInitialString = String.valueOf(firstInitial);
        String lastInitialString = String.valueOf(lastInitial);
        Calendar cal = Calendar.getInstance();
        cal.setTime(birth);
        final short birthYear = (short) cal.get(Calendar.YEAR);
        if (firstInitialString.matches("^[A-Z]$") && lastInitialString.matches("^[A-Z]$")) {
            //Assertions.
            // Keeping in mind calender as a module should be able to determine the year (as implemented by Oracle)
            // For testing purposes we assert the date entered will be between 1900-2999.
            assert firstInitialString.matches("^[A-Z]$");
            assert lastInitialString.matches("^[A-Z]$");
            assert String.valueOf(birthYear).matches("((19)[0-9]{2}|(2)[0-9]{3})");
            //Generate a new unique serial number.
            final int serialNumber = getNextSerialNumber();
            //Create a string representation of the first initial., last initial, birth year, and the new licence number.
            String strRep = String.valueOf(firstInitial) + String.valueOf(lastInitial) + "-" + birthYear + "-" + serialNumber;
            //Construct a new Driving Licence Number object with that unique string representation attributed.
            DrivingLicenceNumber drivingLicenceNumber = new DrivingLicenceNumber(strRep);
            //Add it to the set.
            DRIVING_LICENCE_NUMBERS.add(drivingLicenceNumber);
            return drivingLicenceNumber;
        } else {
            throw new IllegalArgumentException("The parameters entered are invalid.");
        }
    }

    /**
     * getNextSerialNumber attempts to generate the next serialNumber applicable for Driving Licences.
     *
     * @return returns the next serial number as an integer.
     * @throws GetInstanceLimitExceededException in the event that the maximum number of licences of a particular kind have already been generated.
     */
    private static int getNextSerialNumber() throws GetInstanceLimitExceededException {
        final int numberOfLicences = DRIVING_LICENCE_NUMBERS.size();
        if (numberOfLicences == Integer.MAX_VALUE) {
            throw new GetInstanceLimitExceededException("The maximum number of licences that can be generated with these values has been reached");
        }
        return numberOfLicences + 1;
    }

    /**
     * toString generates the string representation of the object.
     *
     * @return returns a direct string representation of the licence number i.e. "AA-1993-01".
     */
    @Override
    public final String toString() {
        return strRep;
    }

    @Override
    //When creating a set of unique numbers equality is important.
    public final boolean equals(Object object) {
        /*
        Test for identity...
        Ensure that the object can be said true in and of itself (is reflexively equal). If so return true (is an instance of itself)
        */
        if (object == this) {
            return true;
        }
        /*
        Test for none-nullity...
        Use the == operator to check if the argument is null.
         */
        if (object == null) {
            return false;
        }
        /*
        Test for type...
        Check the object is of the correct instance.
         */
        if (!(object instanceof DrivingLicenceNumber)) {
            return false;
        }
        /*
        If the object is of the correct instance then we can perform a type conversion (or a cast) to this kind of object.
         */
        DrivingLicenceNumber drivingLicenceNumber = (DrivingLicenceNumber) object;
        /*
        At this stage we can safely investigate the fields of the object.
         */
        return drivingLicenceNumber.strRep.equals(strRep);
    }

    @Override
    public final int hashCode() {
        int prime = 31;
        // Return the prime value in addition to String.hashcode() with a check for nulls (the object should not be null but just in case).
        return prime + (strRep == null ? 0 : strRep.hashCode());
    }
}
