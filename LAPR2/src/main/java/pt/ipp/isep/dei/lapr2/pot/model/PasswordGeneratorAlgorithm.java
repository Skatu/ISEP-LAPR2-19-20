package pt.ipp.isep.dei.lapr2.pot.model;

import java.util.Random;

/**
 * Algorithm for generating a 7-digit long alphanumeric password.
 * It generates a password composed of 2 capital letters, 2 small letters, 1 digit
 * and 2 random alphanumeric characters.
 * Algorithm inspired by the book "Fundamentals of Computer Programming in C#", by Svetlin Nakov,
 * Veselin Kolev & Co., Chapter 11: Creating and Using Objects".
 */

public class PasswordGeneratorAlgorithm {

    /**
     * Capital letters.
     */
    private final String CAPITAL_LETTERS ="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Small letters.
     */
    private final String SMALL_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    /**
     * Digits.
     */
    private final String DIGITS = "0123456789";
    /**
     * All alphanumeric characters.
     */
    private final String ALL_CHARS = CAPITAL_LETTERS + SMALL_LETTERS + DIGITS;

    /**
     * Variable responsible for choosing random characters.
     */
    private static Random rnd = new Random();

    /**
     * Initiates the password generation process.
     * Calls "passwordAlgorithm" method to generate the password.
     * The only method that can communicate with other classes.
     * @return 7-digit long alphanumeric password.
     */
    public String generatePassword(){
        return passwordAlgorithm ().toString ();
    }

    /**
     * Generates the password.
     * The algorithm starts by instantiating a StringBuilder object which will contain the password.
     * It chooses two random capital letters and inserts them at random positions in the StringBuilder object by using
     * the "insertAtRandomPosition" method.
     * The same process is applied for small letters.
     * Then, it picks a random digit and also inserts it at a random position in the password.
     * Finally, two random alphanumeric characters are determined and placed at random positions.
     * At this point, the password has been generated.
     * @return Generated password as a StringBuilder object.
     */
    private StringBuilder passwordAlgorithm(){
        StringBuilder password = new StringBuilder();

        // Generate two random capital letters
        for(int i=1;i<=2;i++){
            int index=Math.abs (  rnd.nextInt (CAPITAL_LETTERS.length ()));
            char c=CAPITAL_LETTERS.charAt ( index );
            insertAtRandomPosition ( password,c );
        }

        // Generate two random small letters
        for (int i = 1; i <= 2; i++) {
            int index=Math.abs (  rnd.nextInt (SMALL_LETTERS.length ()));
            char c=SMALL_LETTERS.charAt ( index );
            insertAtRandomPosition ( password,c );
        }

        // Generate one random digit
        int digitIndex=Math.abs (  rnd.nextInt (DIGITS.length ()));
        char digit=DIGITS.charAt ( digitIndex );
        insertAtRandomPosition ( password,digit );

        // Generate 2 extra characters
        for (int i = 1; i <= 2; i++) {
            int index=Math.abs (  rnd.nextInt (ALL_CHARS.length ()));
            char c=ALL_CHARS.charAt ( index );
            insertAtRandomPosition (password,c);
        }
        return password;
    }

    /**
     * Inserts a character at a random position in the password.
     * @param password Password that is being generated.
     * @param character Character to be randomly placed in the password.
     */
    private void insertAtRandomPosition(StringBuilder password, char character) {
        int randomPosition = Math.abs ( rnd.nextInt(password.length()+1));
        password.insert(randomPosition, character);
    }
}


