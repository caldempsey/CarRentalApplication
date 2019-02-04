package cars;

import exceptions.GetInstanceLimitExceededException;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The CarRegistration class represents an object type used to represent the registration of cars.
 * <p>
 * The CarRegistration class utilizes a static factory build pattern in conjunction to a SortedSet implementation with a comparator.
 * This allows for an ordering of a unique data-structure such that all instances of the CarRegistration class are guaranteed to be unique and stored in the respective order (defined by the class and thus instances of the class).
 * The class thus implements the comparable interface and overrides the equals and hashcode methods to achieve these ends.
 */
public final class CarRegistration implements Comparable<CarRegistration> {

    //Define a new TreeSet which implements the SortedSet interface (generified).
    private static final SortedSet<CarRegistration> REGISTRATIONS = new TreeSet<>();
    // The specification defines the literal number partition of a CarRegistration may include leading zeros.
    // Therefore we can conclude in order to represent this in the data structure it must be stored as a String (or another object which supports leading zeros).
    // Please use the getNumberAsValue method to return this as a value (short).)
    private final String number;
    private final char character;
    private final String strRep;

    /**
     * CarRegistration is a constructor for CarRegistration instances.
     *
     * @param character defines the character of the car registration i.e. "a", or "b".
     * @param number    defines the number of the car registration (String) i.e. 0001.
     * @param strRep    defines the string representation of the car registration (character+number).
     */
    private CarRegistration(char character, String number, String strRep) {
        this.strRep = strRep;
        this.character = character;
        this.number = number;
    }

    /**
     * getInstance generates a unique instance of a CarRegistration object.
     * The uniqueness of these objects is defined by their string representations (from a0001-z9999).
     * This method is responsible for correctly utilizing the "generateNextNumber" and "generateNextCharacter".
     * In particular, it does not define how to get the next number or next character, but provides a logic of when and where those methods should be used.
     * <p>
     * To illustrate one implementation (this one) is, if the Car Registration number == 9999 (of string a9999), then perform generateNextCharacter.
     *
     * @return returns a new Car Registration object.
     * @throws GetInstanceLimitExceededException throws a GetInstanceLimitExceededException in the event that the maximum limitation for instances of this type is exceeded i.e. z9999.
     */
    final static CarRegistration getInstance() throws GetInstanceLimitExceededException {
        short number;
        char character;
        /*
        If it is not the case there are CarRegistration's already stored then define a new one with the first-most possible values "a" and "1".
         */
        if (REGISTRATIONS.isEmpty()) {
            assert (REGISTRATIONS.isEmpty());
            number = 1;
            character = 'a';
        } else {
            /*
             If there are CarRegistration's already stored then find the last CarRegistration stored and take its values.
             */
            CarRegistration carRegistration = getLastRegistration();
            number = carRegistration.getNumberAsValue();
            character = carRegistration.getCharacter();
             /*
             Return the next values (provided by the relevant methods).
             */
            number = generateNextNumber(number);
            if (number == 1) {
                //If the number is equal to 1, then we move on to the next character.
                character = generateNextCharacter(character);
            }

            // Assume we have not reached the last possible character i.e. "z" and it is not the case that we have reached the last possible number value i.e. "9999".
            // In production this is be handled by the respective methods (generateNextNumber and generateNextCharacter).
            assert (character <= 'z');
            assert (number < 10000 || number > 0);
        }
        /*
        Create object and append to the collection by key-value pair.
         */
        //Assert our inputs are still valid.
        assert (isCharacterValid(character));
        assert (isNumberValid(number));
        //Ensure that we store the string representation of the registration to the object (as defined in the specification).
        final String leadingZeros = generateLeadingZeros(number);
        final String numberStr = leadingZeros + number;
        final String strRep = String.valueOf(character + numberStr);
        final CarRegistration carRegistration = new CarRegistration(character, numberStr, strRep);
        REGISTRATIONS.add(carRegistration);
        return carRegistration;
    }


    /**
     * generateNextNumber is responsible for taking an input value and guaranteeing the return of the next number that can be returned.
     * This is to de-couple the number generation from the validity of the upper boundary of Car Registration numbers. Validity rules of the valid range of numbers can be found at the isNumberValid method.
     * From that range this method utilizes the logic that if the inbound number is valid, and the number proceeding that is invalid, then the next number must be the wrap around value.
     * To illustrate if a9999 and 9999 is the highest value, then the next value must be 10000 (defined invalid), so must be 1.
     *
     * @param number takes a valid input number.
     * @return returns the next valid number.
     */
    private static short generateNextNumber(short number) {
        /*
        Check to see if the method has been passed a legal value. If it hasn't something serious has went wrong and we can't recover from it since CarRegistration objects are encapsulated.
         */
        if (!isNumberValid(number)) {
            throw new IllegalArgumentException("The passed value " + number + " is not within the defined range of values.");
        }

        /*
        Increment the input value by 1.
         */
        short nextNumber = (short) (number + 1);
        /*
        If the input value is valid and the input value incremented by 1 is invalid, then the input value incremented by 1 must be greater than the defined valid value.
        To illustrate if the input value is valid and to be a valid input can be at most 9999, then it must be the case the incremented value is 10000.
        Therefore we can know that the next number should be the value of 1.
         */
        if (!isNumberValid(nextNumber)) {
            nextNumber = 1;
        }
        //Return the result.
        return nextNumber;
    }

    /**
     * isNumberValid is responsible for providing the validity criteria of numbers between too values (see generateNextNumber for usage).
     *
     * @param number input number to check range.
     * @return returns true if number is within a defined range.
     */
    private static boolean isNumberValid(short number) {
        /*
         Define maximum accepted range of numbers so other methods can handle if we exceed that range (check to see if between that range).
         This method de-couples the validation procedure from other methods which would like to perform validation checking.
         */
        final short minimumNumberValue = 1;
        final short maximumNumberValue = 9999;
        //Return false "!" only if the number is not within the defined range.
        return !((number > maximumNumberValue) || number < minimumNumberValue);
    }

    /**
     * generateNextCharacter is responsible for taking an input value and guaranteeing the return of the next character that can be returned.
     * This makes use of the ASCII table for conversion purposes i.e. if a then b = a+=a.
     *
     * @param character specifies the input character to be iterated.
     * @return returns the next character in the sequence of characters for CarRegistration objects.
     * @throws GetInstanceLimitExceededException in the event that the last possible character is reached, then we can know that the limit of instances for CarRegistrations has been reached i.e. we are on character "{".
     */
    private static char generateNextCharacter(char character) throws GetInstanceLimitExceededException {
        /*
        Check to see if the method has been passed a legal value.
        We do this since it could be the case that an input character is '{' (so next character is }). If this happens something seriously has went wrong (shouldn't be possible given error checking below).
         */
        if (!isCharacterValid(character)) {
            throw new IllegalArgumentException("The passed character " + character + " is not within the defined range of characters.");
        }

        //Increment the character to the next character on the ASCII table.
        char nextCharacter = ++character;

        /*
        Check to see if the resultant value is a legal value and handle as appropriate.
        We do this since it could be the case that an input character is 'a' (so next character is b), or the input character is 'z' (so next character on the ASCII table is '{').
        If the character is not valid we can know the instance limit has been exceeded.
         */
        if (!isCharacterValid(nextCharacter)) {
            throw new GetInstanceLimitExceededException("The returned character " + nextCharacter + " is not within the defined accepted range of characters.");
        }
        return nextCharacter;
    }

    /**
     * isCharacterValid is responsible for providing the validity criteria of characters between too values (see generateNextCharacter for usage).
     *
     * @param character input character to check range.
     * @return returns true if character is within a defined range.
     */
    private static boolean isCharacterValid(char character) {
        /*
         Define maximum accepted range of characters so other methods can handle if we exceed that range (check to see if between that range).
         This method de-couples the validation procedure from other methods which would like to perform validation checking.
         */
        for (char c = 'a'; c <= 'z'; c++) {
            if (character == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * generateLeadingZeros is responsible for taking a value and generating the number of leading zeros which would be required to complete a string representation of the object.
     * This is calculable by finding the number of significant figures, defining a minimum number of significant figures, and subtracting them.
     * The class then uses a StringBuolder instance to generate a new string representing those zeros.
     * The value of the minimum significant figures is herein defined.
     *
     * @param value the input value to i
     * @return returns a String object of leading zeros required.
     */
    private static String generateLeadingZeros(short value) {
        //Define minimum significant figures.
        final byte minimumSignificantFigures = 4;
        // Convert the value into a string.
        String valueString = String.valueOf(value);
        // Count the number of characters to obtain number of significant figures.
        final int significantFigures = valueString.length();
        /*
        Take the number of significant figures away from the designated padding size
        To illustrate, if the string represents the value of 99 and we need to make sure there are leading zeros to make up 4 significant figures.
        Then the value of significant figures, 2, subtract the number of leading zeros required (minimum significant figures), is 4-2.
        */
        int noLeadingZeros = minimumSignificantFigures - significantFigures;
        /*
        Instantiate an StringBuilder and create a string object.
        */
        StringBuilder zerosBuilder = new StringBuilder();
        for (int i = 0; i < noLeadingZeros; i++) {
            zerosBuilder.append("0");
        }
        return zerosBuilder.toString();
    }

    private static CarRegistration getLastRegistration() {
        //The SortedSet interface allows us to implement the "last" method.
        return REGISTRATIONS.last();
    }

    /**
     * getCharacter returns the character attributed to the CarRegistration object.
     *
     * @return returns the character attributed to the car registration object.
     */
    public final char getCharacter() {
        return character;
    }

    /**
     * getNumberAsValue provides access to the number represented by the DrivingLicenceNumber number.
     *
     * @return returns the representation of the field variable number as a short.
     */
    public short getNumberAsValue() {
        return Short.valueOf(number);
    }

    /**
     * toString is responsible for proividing the string representation of the object.
     *
     * @return returns the string representation of the object (the registration number i.e. "a9999").
     */
    public final String toString() {
        return strRep;
    }

    @Override
    public final boolean equals(Object object) {
        /*
        Test for identity...
        Ensure that the object can be said true in and of itself (is reflexively equal). If so return true (is an instance of itself)
        */
        if (this == object) return true;
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
        if (!(object instanceof CarRegistration)) {
            return false;
        }
        /*
        If the object is of the correct instance then we can perform a type conversion (or a cast) to this kind of object.
         */
        CarRegistration carRegistration = (CarRegistration) object;
        /*
        At this stage we can safely investigate the fields of the object.
         */
        return (character == carRegistration.character) && number.equals(carRegistration.number) && strRep.equals(carRegistration.strRep);
    }

    @Override
    public final int hashCode() {
        int result = number.hashCode();
        result = 31 * result + (int) character;
        result = 31 * result + strRep.hashCode();
        return result;
    }


    @Override
    public final int compareTo(CarRegistration carRegistration) {
        return strRep.compareTo(carRegistration.toString());
    }
}
