public class Kata
{
    public static int TripleDouble(long num1, long num2){
        for (int i=0;i<9;i++){
            if (hasRepeatingInt(num1, i, 3) && (hasRepeatingInt(num2, i, 2))){
                return 1;
            }
        }
        return 0;
    }

    // Simple helper function to identify repeating values using regex.
    public static boolean hasRepeatingInt(long number, int value, int repetition){
        if (String.valueOf(number).matches("(" + value + ")" + "{"+repetition+"}")){
            return true;
        }
        return false;
    }
}