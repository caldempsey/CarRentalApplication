package exceptions;

/**
 * If generating an object will generate an illegal number of instances then a GetInstanceLimitExceededException can be thrown.
 * GetInstanceLimitExceededException is a custom exception which should provide some details of the operation however these aren't necessary).
 */
public final class GetInstanceLimitExceededException extends Exception {

    /**
     * GetInstanceLimitExceededException() takes advantage of constructor overloading to allow the user not to specify the details of the exception.
     */
    public GetInstanceLimitExceededException() {
        super();
    }

    /**
     * GetInstanceLimitExceededException() takes advantage of constructor overloading to allow the user to specify the details of the exception.
     * @param s specifies reason for the exception occurring.
     */
    public GetInstanceLimitExceededException(String s) {
        super(s);
    }


}