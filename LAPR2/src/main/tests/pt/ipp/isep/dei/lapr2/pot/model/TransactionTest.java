package pt.ipp.isep.dei.lapr2.pot.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void testEquals() {
        Task t1 = new Task("1",	"Web Development",	48,
                20,	"Software Development");
        Freelancer f1 = new Freelancer("JW1",	"John Wayne",	"Junior",
                "jw@gmail.com",	"921654987",	"US120010001234567891",
                "Street 92, 90, New York",	"USA");
        Transaction tr = new Transaction("101", t1, f1);

        Task t2 = new Task("2",	"Network Administration",	20,	40,
                "Network Administration");
        Freelancer f2 = new Freelancer("MA", "Marion Antoine",	"Senior",
                "Marion190@hotmail.com",	"223654587",
                "LU120010001234567891",	"Boulevard Royal, 100",
                "Luxembourg");
        Transaction tr1 = new Transaction("102", t2, f2);
        boolean expected = false;
        boolean result = tr.equals(tr1);
        assertEquals(expected, result);
    }

    @Test
    public void computeAmount() {
        Task t1 = new Task("1",	"Web Development",	48,
                20,	"Software Development");
        Freelancer f1 = new Freelancer("JW1",	"John Wayne",	"Junior",
                "jw@gmail.com",	"921654987",	"US120010001234567891",
                "Street 92, 90, New York",	"USA");
        Transaction p = new Transaction("101", t1, f1);
        double result1 = p.computeAmount();
        double expected1 = 960;
        assertEquals("Test for Junior Freelancer", expected1, result1, 2);

        Task t2 = new Task("2",	"Network Administration",	20,	40,
                "Network Administration");
        Freelancer f2 = new Freelancer("MA", "Marion Antoine",	"Senior",
                "Marion190@hotmail.com",	"223654587",
                "LU120010001234567891",	"Boulevard Royal, 100",
                "Luxembourg");
        Transaction p2 = new Transaction("102", t2, f2);
        double result2 = p2.computeAmount();
        double expected2 = 1600;
        assertEquals("Test for Senior Freelancer", expected2, result2, 2);
    }
}