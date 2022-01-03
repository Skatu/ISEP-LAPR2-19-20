package pt.ipp.isep.dei.lapr2.pot.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordGeneratorAlgorithmTest {

    @Test
    public void doesItGenerateA7AlphaNumericPassword(){
        PasswordGeneratorAlgorithm alg=new PasswordGeneratorAlgorithm ();
        int expected=7;

        String password1= alg.generatePassword ();
        int actual1=password1.length ();
        assertEquals ( expected,actual1 );

        //System.out.println (password1);

        String password2= alg.generatePassword ();
        int actual2=password2.length ();
        assertEquals ( expected,actual2 );

        //System.out.println (password2);

        String password3= alg.generatePassword ();
        int actual3=password3.length ();
        assertEquals ( expected,actual3 );

        //System.out.println (password3);
    }

    @Test
    public void isPasswordAlphaNumeric(){
        PasswordGeneratorAlgorithm alg=new PasswordGeneratorAlgorithm ();
        String password1= alg.generatePassword ();
        boolean result1 = password1.matches(".*\\d.*");
        assertTrue ( result1 );

        String password2= alg.generatePassword ();
        boolean result2 = password2.matches(".*\\d.*");
        assertTrue ( result2 );

        String password3= alg.generatePassword ();
        boolean result3 = password3.matches(".*\\d.*");
        assertTrue ( result3 );
    }
}