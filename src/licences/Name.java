package licences;

import java.util.HashMap;
import java.util.Map;

/**
 * The Name class is an immutable class which utilizes a static factory method pattern to generate unique instances of Names.
 * Uniqueness of Name objects allows us to re-use any previous valid "Name" object already stored in the map.
 */
final class Name {

    private static final Map<String, Name> NAME_MAP = new HashMap<>();
    private final String firstName, lastName, strRep;

    /**
     * Name is a constructor for a new Name object with a first name, last name, and string representation.
     *
     * @param firstName specifies the first name of the Name.
     * @param lastName  specifies the last name of the Name.
     * @param strRep    specifies the string representation of the Name.
     */
    private Name(String firstName, String lastName, String strRep) {
        this.strRep = strRep;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * the getInstance method is responsible for constructing new instances of "Name" objects and passing them back the the caller.
     *
     * @param firstName specifies the first name as a String.
     * @param lastName  specifies the last name as a String.
     * @return returns a new Name object with the giving parameters.
     * @throws IllegalArgumentException an InvalidArgumentException is thrown only if the method has been passeed invalid parameters.
     */
    public static Name getInstance(String firstName, String lastName) throws IllegalArgumentException {
        /*
        Check parameters.
        */
        assert firstName != null;
        assert lastName != null;
        firstName = firstName.toUpperCase();
        lastName = lastName.toUpperCase();
        String strRep = firstName + " " + lastName;
        // "strRep" is a string concatenation which uses the first name and last name in conjunction.
        // Therefore if strRep is valid it must be the case that the first name (string) and last name (string) is valid.
        if (strRep.matches("^[A-Z]+ [A-Z]+$")) {
            strRep = firstName + " " + lastName;
            Name name = NAME_MAP.get(strRep);
            if (name == null) {
                name = new Name(firstName, lastName, strRep);
                NAME_MAP.put(strRep, name);
            }
            return name;
        } else {
            throw new IllegalArgumentException("The parameters entered are invalid.");
        }
    }


    /**
     * getFirstName returns the first name as a String object.
     *
     * @return returns the first name as a String.
     */
    public final String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    /**
     * getFirstInitial returns the first letter of the first name as a character.
     *
     * @return returns a the first letter of the first name as a character.
     */
    public final char getFirstInitial() {
        final char initial;
        initial = firstName.charAt(0);
        return initial;
    }

    /**
     * getLastInitial returns the first letter of the last name as a character.
     *
     * @return returns a the first letter of the last name as a character.
     */
    public final char getLastInitial() {
        final char initial;
        initial = lastName.charAt(0);
        return initial;
    }

    /**
     * toString generates the string representation of the object.
     *
     * @return returns a string representation of the object as "first-name last-name".
     */
    @Override
    public final String toString() {
        return strRep;
    }
}
