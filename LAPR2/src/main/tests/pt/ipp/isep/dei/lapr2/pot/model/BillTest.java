package pt.ipp.isep.dei.lapr2.pot.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class BillTest{

    @Test
    public void createTest(){
        // <editor-fold defaultstate="collapsed">

        List<String> lst1 = new ArrayList<>();
        List<String> lst2 = new ArrayList<>();

        Freelancer f1 = new Freelancer("1","free1","junior","email1","111","111","Rua1","c1");
        Freelancer f2 = new Freelancer("2","free2","junior","email2","222","222","Rua2","c2");
        Freelancer f3 = new Freelancer("3","free3","junior","email3","333","333","Rua3","c3");

        Task task1 = new Task("ref1","blah",2.0,3.0,"blah");
        Task task2 = new Task("ref2","blah",2.0,3.0,"blah");
        Task task3 = new Task("ref3","blah",2.0,3.0,"blah");
        Task task4 = new Task("ref4","blah",2.0,3.0,"blah");
        Task task5 = new Task("ref5","blah",2.0,3.0,"blah");
        Task task6 = new Task("ref6","blah",2.0,3.0,"blah");

        Transaction t1 = new Transaction("1",task1,f1);
        Transaction t2 = new Transaction("2",task2,f1);
        Transaction t3 = new Transaction("3",task3,f1);
        Transaction t4 = new Transaction("4",task4,f2);
        Transaction t5 = new Transaction("5",task5,f2);
        Transaction t6 = new Transaction("6",task6,f3);


        List<Freelancer> lstFree = new ArrayList<>();
        List<Transaction> lstTran = new ArrayList<>();

        lstFree.add(f1); lstFree.add(f2); lstFree.add(f3);
        lstTran.add(t1); lstTran.add(t2); lstTran.add(t3); lstTran.add(t4); lstTran.add(t5); lstTran.add(t6);

        lst2.add(String.format("%s,%s,%s,%f",t1.getTask().getId(),f1.getFreelancerID(),t1.getTransactionID(),3.0));
        lst2.add(String.format("%s,%s,%s,%f",t2.getTask().getId(),f1.getFreelancerID(),t2.getTransactionID(),3.0));
        lst2.add(String.format("%s,%s,%s,%f",t3.getTask().getId(),f1.getFreelancerID(),t3.getTransactionID(),3.0));
        lst2.add(String.format("%s,%s,%s,%f",t4.getTask().getId(),f2.getFreelancerID(),t4.getTransactionID(),3.0));
        lst2.add(String.format("%s,%s,%s,%f",t5.getTask().getId(),f2.getFreelancerID(),t5.getTransactionID(),3.0));
        lst2.add(String.format("%s,%s,%s,%f",t6.getTask().getId(),f3.getFreelancerID(),t6.getTransactionID(),3.0));



        // </editor-fold>
        for (Transaction tran: lstTran){
            Freelancer free = tran.getFreelancer();
            if (lstFree.contains(free)){

            } else {
                lstFree.add(free);
            }
        }

        for(Freelancer free:lstFree){
            for(Transaction tran:lstTran){
                if(tran.getFreelancer() == free) {
                    String line = String.format("%s,%s,%s,%f", tran.getTask().getId(), free.getFreelancerID(), tran.getTransactionID(), 3.0);
                    lst1.add(line);
                }
            }
        }
        assertArrayEquals (lst1.toArray(),lst2.toArray());
    }

    @Test
    public void createTestFail(){
        // <editor-fold defaultstate="collapsed">

        List<String> lst1 = new ArrayList<>();
        List<String> lst2 = new ArrayList<>();

        Freelancer f1 = new Freelancer("1","free1","junior","email1","111","111","Rua1","c1");
        Freelancer f2 = new Freelancer("2","free2","junior","email2","222","222","Rua2","c2");
        Freelancer f3 = new Freelancer("3","free3","junior","email3","333","333","Rua3","c3");

        Task task1 = new Task("ref1","blah",2.0,3.0,"blah");
        Task task2 = new Task("ref2","blah",2.0,3.0,"blah");
        Task task3 = new Task("ref3","blah",2.0,3.0,"blah");
        Task task4 = new Task("ref4","blah",2.0,3.0,"blah");
        Task task5 = new Task("ref5","blah",2.0,3.0,"blah");
        Task task6 = new Task("ref6","blah",2.0,3.0,"blah");

        Transaction t1 = new Transaction("1",task1,f1);
        Transaction t2 = new Transaction("2",task2,f1);
        Transaction t3 = new Transaction("3",task3,f1);
        Transaction t4 = new Transaction("4",task4,f2);
        Transaction t5 = new Transaction("5",task5,f2);
        Transaction t6 = new Transaction("6",task6,f3);


        List<Freelancer> lstFree = new ArrayList<>();
        List<Transaction> lstTran = new ArrayList<>();

        lstFree.add(f1); lstFree.add(f2); lstFree.add(f3);
        lstTran.add(t1); lstTran.add(t2); lstTran.add(t3); lstTran.add(t4); lstTran.add(t5); lstTran.add(t6);

        String line1 = String.format("%s,%s,%s,%f",t1.getTask().getId(),f1.getFreelancerID(),t1.getTransactionID(),3.0);
        String line2 = String.format("%s,%s,%s,%f",t2.getTask().getId(),f1.getFreelancerID(),t2.getTransactionID(),3.0);
        String line3 = String.format("%s,%s,%s,%f",t3.getTask().getId(),f1.getFreelancerID(),t3.getTransactionID(),3.0);
        String line4 = String.format("%s,%s,%s,%f",t4.getTask().getId(),f2.getFreelancerID(),t4.getTransactionID(),3.0);
        String line5 = String.format("%s,%s,%s,%f",t5.getTask().getId(),f2.getFreelancerID(),t5.getTransactionID(),3.0);
        String line6 = String.format("%s,%s,%s,%f",t6.getTask().getId(),f3.getFreelancerID(),t6.getTransactionID(),3.0);

        lst2.add(line1); lst2.add(line2); lst2.add(line3); lst2.add(line4); lst2.add(line5); lst2.add(line6);

        // </editor-fold>
        for (Transaction tran: lstTran){
            Freelancer free = tran.getFreelancer();
            if (lstFree.contains(free)){

            } else {
                lstFree.add(free);
            }
        }

        for(Freelancer free:lstFree){
            for(Transaction tran:lstTran){
                String line = String.format("%s,%s,%s,%f",tran.getTask().getId(),free.getFreelancerID(),tran.getTransactionID(),3.0);
                lst1.add(line);
            }
        }
        assertNotEquals(lst1.toArray(),lst2.toArray());
    }

    @Test
    public void addTransactionTest(){

        Freelancer f1 = new Freelancer("1","free1","junior","email1","111","111","Rua1","c1");
        Task task1 = new Task("ref1","blah",2.0,3.0,"blah");
        Transaction t1 = new Transaction("1",task1,f1);

        String line1 = String.format("ref1,1,1,%f",3.0);
        String line2 = String.format("%s,%s,%s,%f",t1.getTask().getId(),f1.getFreelancerID(),t1.getTransactionID(),3.0);

        assertEquals(line1,line2);
    }

    @Test
    public void addTransactionTestFail(){

        Freelancer f1 = new Freelancer("1","free1","junior","email1","111","111","Rua1","c1");
        Task task1 = new Task("ref1","blah",2.0,3.0,"blah");
        Transaction t1 = new Transaction("1",task1,f1);

        String line1 = String.format("ref2,2,2,%f",3.0);
        String line2 = String.format("%s,%s,%s,%f",t1.getTask().getId(),f1.getFreelancerID(),t1.getTransactionID(),3.0);

        assertNotEquals(line1,line2);
    }


}