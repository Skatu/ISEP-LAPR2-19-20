package pt.ipp.isep.dei.lapr2.pot.model;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.PatternSyntaxException;

/**
 * The class Parse contains methods which allows
 * parsing strings to other primitive and non-primitive
 * data types like int, double and Calendar.
 * @author Rafael Moreira - 1181055
 */

public final class Parse {
    /**
     * Don't let anyone instantiate this class.
     */
    private Parse() {
        throw new AssertionError ();
    }

    /**
     * Verify if the string can be converted to int.
     * It uses exception handling to check if the parsing is possible.
     * If it is not possible, {@link NumberFormatException} will be thrown and the method will return false.
     * If the parsing doesn't throw the exception, the method will return true.
     * @param num {@link String} to verify if it is parsable.
     * @return <code>true</code> if the string is parsable to int. <code>false</code> otherwise.
     */
    //Inspired in https://stackoverflow.com/questions/8391979/does-java-have-a-int-tryparse-that-doesnt-throw-an-exception-for-bad-data
    public static boolean tryParseInt(String num){
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verify if the string can be converted to double.
     * It uses exception handling to check if the parsing is possible.
     * If it is not possible, {@link NumberFormatException} will be thrown and the method will return false.
     * If the parsing doesn't throw the exception, the method will return true.
     * @param num {@link String} to verify if it is parsable.
     * @return <code>true</code> if the string is parsable to double. <code>false</code> otherwise.
     */
    //Inspired in https://stackoverflow.com/questions/8391979/does-java-have-a-int-tryparse-that-doesnt-throw-an-exception-for-bad-data
    public static boolean tryParseDouble(String num){
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Converts a string into a {@link Calendar} object.
     * String must used either the dash("-"), space(" "), dot(".") or stroke("/") date separator.
     * To verify which date separator is used, method <code>parseData</code>
     * calls the method <code>getDateSeparator</code>.
     * If the string is not in the right format, the method will return null.
     * Otherwise, it will parse the string and create the {@link Calendar} object with the date passed as parameter.
     * @param date {@link String} to be parsed to {@link Calendar}.
     * @return {@link Calendar} object.
     */
    public static Calendar parseDate(String date) {
        Calendar cal=Calendar.getInstance ();
        try {
            String[] s = date.split ( getDateSeparator ( date ) );
            int year, month, day;
            if (tryParseInt ( s[0] ) && tryParseInt ( s[1] ) && tryParseInt ( s[2] )) {
                day = Integer.parseInt ( s[0] );
                month = Integer.parseInt ( s[1] );
                year = Integer.parseInt ( s[2] );
                cal.set ( year, month - 1, day  );
                return cal;
            }
        } catch (PatternSyntaxException | IndexOutOfBoundsException e) {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * Converts Calendar object in YYYYMMDD format to DDMMYYYY format.
     * Method used in toString method of the class Execution to convert the format of the execution end date.
     * @param c Calendar object to parse.
     * @return String representing a date in DD-MM-YYY format.
     */
    public static String parseDateIntoDDMMYYYYFormat(Calendar c){
        SimpleDateFormat format=new SimpleDateFormat ("dd-MM-yyyy");
        return format.format ( c.getTime () );
    }

    /**
     * Verifies which date separator is being used in the string passed as parameter.
     * @param date Date in String format.
     * @return Date separator of the String passed as parameter.
     */

    private static String getDateSeparator(String date){
        if(date.contains ( "-" )) return "-";
        if(date.contains ( " " )) return " ";
        if(date.contains ( "." )) return ".";
        return "/";
    }


}
