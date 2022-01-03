package pt.ipp.isep.dei.lapr2.pot.controller;

import junit.framework.TestCase;
import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.util.Calendar;

public class SendManualEmailsControllerTest extends TestCase {

    public void testCalculateFreelancerPercentage() {
        Platform platform = ApplicationPOT.getInstance ().getPlatform ();
        //Creating Organizations
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        //Creating transactions
        Task t1 = new Task("idTest",	"Web Development",	70,
                23,	"Software Development");
        Calendar d1=Calendar.getInstance ();
        d1.set (2020, Calendar.JANUARY, 24);
        Execution e1 = new Execution(d1, 0, "High Quality Work");
        Freelancer f1 = new Freelancer("freelancerTest",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr = new Transaction("transactionIDtest", t1, e1, f1);

        Task t2 = new Task("idTest1",	"Network Administration",	20,	40,
                "Network Administration");
        Calendar d2=Calendar.getInstance ();
        d2.set (2020, Calendar.JANUARY, 4);
        Execution e2 = new Execution(d2, 5, "High Quality Work");
        Transaction tr1 = new Transaction("transactionIDtest2", t2, e2, f1);

        Task t3 = new Task("idTest2",	"Network Administration",	20,	40,
                "Network Administration");
        Calendar d3=Calendar.getInstance ();
        d3.set (2019, Calendar.JANUARY, 4);
        Execution e3 = new Execution(d3, 5, "High Quality Work");
        Transaction tr2 = new Transaction("transactionIDtest2", t3, e3, f1);

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);
        o.getListTransactions().getM_lstTransactions().add(tr1);
        o.getListTransactions().getM_lstTransactions().add(tr2);


        //Adding the organizations and freelancers to the platform
        platform.getRegistryOrganizations ().getM_lstOrganizations().add(o);
        platform.getRegistryFreelancers ().getM_lstFreelancers().add(f1);

        double expected=50;

        SendWarningEmailsTask sendWarningEmailsTask=new SendWarningEmailsTask();
        double result = sendWarningEmailsTask.calculateFreelancerPercentage("freelancerTest");

        assertEquals(expected, result);
    }

    public void testCalculateOverallPercentage() {

        //Platform platform = new Platform("name");
        RegistryOrganizations rOrg=new RegistryOrganizations ();
        RegistryFreelancers rFreel=new RegistryFreelancers ();
        //Creating Organizations
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        //Creating transactions
        Task t1 = new Task("idTest",	"Web Development",	70,
                23,	"Software Development");
        Calendar d1=Calendar.getInstance ();
        d1.set (2020, Calendar.JANUARY, 24);
        Execution e1 = new Execution(d1, 0, "High Quality Work");
        Freelancer f1 = new Freelancer("freelancerTest",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr = new Transaction("transactionIDtest", t1, e1, f1);

        Task t2 = new Task("idTest1",	"Network Administration",	20,	40,
                "Network Administration");
        Calendar d2=Calendar.getInstance ();
        d2.set (2020, Calendar.JANUARY, 4);
        Execution e2 = new Execution(d2, 5, "High Quality Work");
        Transaction tr1 = new Transaction("transactionIDtest2", t2, e2, f1);

        Task t3 = new Task("idTest2", "test", 50, 100, "test");
        Calendar d3=Calendar.getInstance();
        d3.set(2020,Calendar.JANUARY,20);
        Execution e3=new Execution(d3,6, "test");
        Freelancer f2 = new Freelancer("freelancerTest1",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");

        Transaction tr3 = new Transaction("transactionIDtest3", t3, e3, f2);

        Task t4 = new Task("idTest3", "test", 50, 100, "test");
        Calendar d4=Calendar.getInstance();
        d4.set(2019,Calendar.JANUARY,20);
        Execution e4=new Execution(d4,6, "test");

        Transaction tr4 = new Transaction("transactionIDtest4", t4, e4, f2);

        Task t5 = new Task("idTest4", "test", 50, 100, "test");
        Calendar d5=Calendar.getInstance();
        d5.set(2020,Calendar.JANUARY,20);
        Execution e5=new Execution(d5,0, "test");

        Transaction tr5 = new Transaction("transactionIDtest5", t5, e5, f2);




        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTasks().getListTasks().add(t2);
        o.getListTasks().getListTasks().add(t3);
        o.getListTasks().getListTasks().add(t4);
        o.getListTasks().getListTasks().add(t5);
        o.getListTransactions().getM_lstTransactions().add(tr);
        o.getListTransactions().getM_lstTransactions().add(tr1);
        o.getListTransactions().getM_lstTransactions().add(tr3);
        o.getListTransactions().getM_lstTransactions().add(tr4);
        o.getListTransactions().getM_lstTransactions().add(tr5);

        //Adding the organizations and freelancers to the platform
        /*
        platform.getRegistryOrganizations().getM_lstOrganizations().add(o);
        platform.getRegistryFreelancers().getM_lstFreelancers().add(f1);
        platform.getRegistryFreelancers().getM_lstFreelancers().add(f2);

         */
        rOrg.getM_lstOrganizations ().add ( o );
        rFreel.getM_lstFreelancers ().add ( f1 );
        rFreel.getM_lstFreelancers ().add ( f2 );

        //SendWarningEmailsTask sendWarningEmailsTask=new SendWarningEmailsTask(platform);
        SendWarningEmailsTask sendWarningEmailsTask=new SendWarningEmailsTask();
        double expected;
        double sum=sendWarningEmailsTask.calculateFreelancerPercentage("freelancerTest");
        sum=sum+sendWarningEmailsTask.calculateFreelancerPercentage("freelancerTest1");
        expected=sum/2;
        double result = sendWarningEmailsTask.calculateOverallPercentage();
        assertEquals(expected, result);
    }
}