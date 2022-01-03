package pt.ipp.isep.dei.lapr2.pot.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class OrganizationTest {
    /*
        Manager Tests
     */
    @Test
    public void createManagerWithValidNameAndEmail(){
        Manager manager1=Organization.newManager ( "name1","email@email.com" );
        assertNotNull ( manager1 );
        Manager manager2=Organization.newManager ( "name2","email2@email.com" );
        assertNotNull ( manager2 );
    }

    @Test
    public void failCreateManagerWithInvalidName(){
        Manager manager1=Organization.newManager ( null, "email@email.com");
        assertNull ( manager1 );
        Manager manager2=Organization.newManager ( "", "email@email.com");
        assertNull ( manager2 );
    }

    @Test
    public void failCreateManagerWithInvalidEmail(){
        Manager manager1=Organization.newManager ( "name1", "email@email@email.com");
        assertNull ( manager1 );
        Manager manager2=Organization.newManager ( "name2", "");
        assertNull ( manager2 );
        Manager manager3=Organization.newManager ( "name3", null);
        assertNull ( manager3 );
        Manager manager4=Organization.newManager ( "name1", "rrrr");
        assertNull ( manager4 );
        Manager manager5=Organization.newManager ( "name1", "werwer.com");
        assertNull ( manager5 );
    }

    /*
        Collaborator Tests
     */
    @Test
    public void createCollaboratorWithValidNameAndEmail(){
        Collaborator collab1=Organization.newCollaborator ( "col1","colab.org@email.com" );
        assertNotNull ( collab1 );
        Collaborator collab2=Organization.newCollaborator ( "col2","olb.grt.er@email.co.uk" );
        assertNotNull ( collab2 );
    }

    @Test
    public void failCreateCollaboratorWithInvalidName(){
        Collaborator collab1=Organization.newCollaborator ( null, "olb.grt.er@email.co.uk");
        assertNull ( collab1 );
        Collaborator collab2=Organization.newCollaborator ( "", "email@email.com.pt");
        assertNull ( collab2 );
    }

    @Test
    public void failCreateCollaboratorWithInvalidEmail(){
        Collaborator collab1=Organization.newCollaborator ( "name1", "email\34554€&E@email.com");
        assertNull ( collab1 );
        Collaborator collab2=Organization.newCollaborator ( "name2", "");
        assertNull ( collab2 );
        Collaborator collab3=Organization.newCollaborator ( "name3", null);
        assertNull ( collab3 );
        Collaborator collab4=Organization.newCollaborator ( "name1", "missing email");
        assertNull ( collab4 );
        Collaborator collab5=Organization.newCollaborator ( "name1", "??=='?='@email.com?");
        assertNull ( collab5 );
    }

    /*
        Organization Tests
     */
    @Test
    public void successfulyCreateOrganization(){
        Manager manager1=Organization.newManager ( "name1","email@email.com" );
        Collaborator collab1=Organization.newCollaborator ( "col1","colab.org@email.com" );
        Organization org1=new Organization ("org1","nif1",manager1,collab1  );
        assertNotNull ( org1 );
    }

    @Test(expected = IllegalArgumentException.class)
    public void failCreateOrganizationWithInvalidManager(){
        Manager manager1=null;
        Collaborator collab1=Organization.newCollaborator ( "col1","colab.org@email.com" );
        new Organization ("org1","nif1",manager1,collab1  );
    }

    @Test(expected = IllegalArgumentException.class)
    public void failCreateOrganizationWithInvalidCollaborator(){
        Manager manager1=Organization.newManager ( "name1","email@email.com" );
        Collaborator collab1=null;
        new Organization ("org1","nif1",manager1,collab1  );
    }

    @Test(expected = IllegalArgumentException.class)
    public void failCreateOrganizationWithEmptyName(){
        Manager manager1=Organization.newManager ( "name1","email@email.com" );
        Collaborator collab1=Organization.newCollaborator ( "col1","colab.org@email.com" );
        new Organization ("","nif1",manager1,collab1  );
    }

    @Test(expected = IllegalArgumentException.class)
    public void failCreateOrganizationWithNullName(){
        Manager manager1=Organization.newManager ( "name1","email@email.com" );
        Collaborator collab1=Organization.newCollaborator ( "col1","colab.org@email.com" );
        new Organization (null,"nif1",manager1,collab1  );
    }

    @Test(expected = IllegalArgumentException.class)
    public void failCreateOrganizationWithEmptyNIF(){
        Manager manager1=Organization.newManager ( "name1","email@email.com" );
        Collaborator collab1=Organization.newCollaborator ( "col1","colab.org@email.com" );
        new Organization ("org1","",manager1,collab1  );
    }

    @Test(expected = IllegalArgumentException.class)
    public void failCreateOrganizationWithNullNIF(){
        Manager manager1=Organization.newManager ( "name1","email@email.com" );
        Collaborator collab1=Organization.newCollaborator ( "col1","colab.org@email.com" );
        new Organization ("org1",null,manager1,collab1  );
    }

    @Test
    public void registerOrganization(){
        RegistryOrganizations rOrgs=new RegistryOrganizations ();
        Manager manager1=Organization.newManager ( "testMan1","email@email.com" );
        Collaborator collab1=Organization.newCollaborator ( "testCol1","colab.org@email.com" );
        Organization org1=new Organization ("org1","nif1",manager1,collab1  );
        boolean result=rOrgs.registerOrganization (org1 );
        assertTrue ( result );
    }

    @Test
    public void successfulyCreateTwoDifferentOrganizations(){
        RegistryOrganizations rOrgs=new RegistryOrganizations ();
        Manager manager1=Organization.newManager ( "name1","email@email.com" );
        Collaborator collab1=Organization.newCollaborator ( "col1","colab.org@email.com" );
        Organization org1=new Organization ("org1","nif1",manager1,collab1  );
        Manager manager2=Organization.newManager ( "name2","email2@email.com" );
        Collaborator collab2=Organization.newCollaborator ( "col2","colab.org2@email.com" );
        Organization org2=new Organization ("org2","nif2",manager2,collab2  );
        boolean result=rOrgs.registerOrganization ( org1 ) && rOrgs.registerOrganization ( org2 );
        assertTrue ( result );
    }

    @Test
    public void failCreateOrganizationsWithSameNIF(){
        RegistryOrganizations rOrgs=new RegistryOrganizations ();
        String nif="nif";
        Manager manager1=Organization.newManager ( "name1","email@email.com" );
        Collaborator collab1=Organization.newCollaborator ( "col1","colab.org@email.com" );
        Organization org1=new Organization ("org1",nif,manager1,collab1  );
        Manager manager2=Organization.newManager ( "name2","email2@email.com" );
        Collaborator collab2=Organization.newCollaborator ( "col2","colab.org2@email.com" );
        Organization org2=new Organization ("org2",nif,manager2,collab2  );
        boolean result=rOrgs.registerOrganization ( org1 ) && rOrgs.registerOrganization ( org2 );
        assertFalse ( result );
    }

    /*
        Performance statistics tests
     */
    @Test
    public void determinePaymentOrganization(){
        //Creating one Organization
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        //Creating two transactions
        Task t1 = new Task("idTest",	"Web Development",	70,
                23,	"Software Development");
        Freelancer f1 = new Freelancer("freelancerTest",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr = new Transaction("transactionIDtest", t1, f1);

        Task t2 = new Task("idTest1",	"Network Administration",	20,	40,
                "Network Administration");
        Transaction tr1 = new Transaction("transactionIDtest2", t2, f1);

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);

        o.getListTasks().getListTasks().add(t2);
        o.getListTransactions().getM_lstTransactions().add(tr1);

        //Testing the options
        TreeMap<String, List<Double>> expected = new TreeMap<>();
        expected.put("freelancerTest", new ArrayList<>());
        expected.get("freelancerTest").add(1610d);
        expected.get("freelancerTest").add(800d);

        TreeMap<String, List<Double>> organizationData = o.determinePaymentOrganization();
        TreeMap<String, List<Double>> result=new TreeMap<>();
        result.put("freelancerTest",organizationData.get("freelancerTest"));
        assertEquals(expected, result);
    }

    @Test
    public void calculateMeanPayment(){
        //Creating one Organization
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);


        //Creating three transactions
        Task t1 = new Task("idTest",	"Web Development",	70,
                23,	"Software Development");
        Freelancer f1 = new Freelancer("freelancerTest",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr = new Transaction("transactionIDtest", t1, f1);

        Task t2 = new Task("idTest1",	"Network Administration",	20,	40,
                "Network Administration");
        Transaction tr1 = new Transaction("transactionIDtest2", t2, f1);

        Task t3 = new Task("idTest2",	"Network Administration",	8,	89,
                "Network Administration");
        Freelancer f2 =  new Freelancer("freelancerTest1",	"nameTest1",	"Senior",
                "nameTest1@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr2 = new Transaction("102", t3, f2);

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);
        o.getListTasks().getListTasks().add(t2);
        o.getListTransactions().getM_lstTransactions().add(tr1);
        o.getListTasks().getListTasks().add(t3);
        o.getListTransactions().getM_lstTransactions().add(tr2);

        TreeMap<String, List<Double>> mapTotalPayments = new TreeMap<>();
        mapTotalPayments.put("freelancerTest", new ArrayList<>());
        mapTotalPayments.get("freelancerTest").add(1610d);
        mapTotalPayments.get("freelancerTest").add(800d);
        mapTotalPayments.put("freelancerTest1", new ArrayList<>());
        mapTotalPayments.get("freelancerTest1").add(1424d);

        TreeMap<String, Double> expected = new TreeMap<>();
        expected.put("freelancerTest",1205d);
        expected.put("freelancerTest1", 1424d);

        TreeMap<String, Double> platformMean = o.calculateMeanPayment(mapTotalPayments);
        TreeMap<String, Double> result=new TreeMap<>();
        result.put("freelancerTest", platformMean.get("freelancerTest"));
        result.put("freelancerTest1", platformMean.get("freelancerTest1"));

        assertEquals(expected, result);
    }

    @Test
    public void calculateDeviationPayment(){
        //Creating one Organization
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);


        //Creating three transactions
        Task t1 = new Task("idTest",	"Web Development",	70,
                23,	"Software Development");
        Freelancer f1 = new Freelancer("freelancerTest",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr = new Transaction("transactionIDtest", t1, f1);

        Task t2 = new Task("idTest1",	"Network Administration",	20,	40,
                "Network Administration");
        Transaction tr1 = new Transaction("transactionIDtest2", t2, f1);

        Task t3 = new Task("idTest2",	"Network Administration",	8,	89,
                "Network Administration");
        Freelancer f2 =  new Freelancer("freelancerTest1",	"nameTest1",	"Senior",
                "nameTest1@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr2 = new Transaction("102", t3, f2);

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);
        o.getListTasks().getListTasks().add(t2);
        o.getListTransactions().getM_lstTransactions().add(tr1);
        o.getListTasks().getListTasks().add(t3);
        o.getListTransactions().getM_lstTransactions().add(tr2);

        //Determining all payments related to each freelancer
        TreeMap<String, List<Double>> mapTotalPayments = new TreeMap<>();
        mapTotalPayments.put("freelancerTest", new ArrayList<>());
        mapTotalPayments.get("freelancerTest").add(1610d);
        mapTotalPayments.get("freelancerTest").add(800d);
        mapTotalPayments.put("freelancerTest1", new ArrayList<>());
        mapTotalPayments.get("freelancerTest1").add(1424d);

        //Determining payment mean for each freelancer
        TreeMap<String, Double> mapMeanPayments = new TreeMap<>();
        mapMeanPayments.put("freelancerTest",1205d);
        mapMeanPayments.put("freelancerTest1", 1424d);

        //Comparing
        TreeMap<String, Double> expected = new TreeMap<>();
        expected.put("freelancerTest", 405d);
        expected.put("freelancerTest1", 0d);

        TreeMap<String, Double> organizationDeviation = o.calculateDeviationPayment(mapTotalPayments,mapMeanPayments);
        TreeMap<String, Double> result=new TreeMap<>();
        result.put("freelancerTest", organizationDeviation.get("freelancerTest"));
        result.put("freelancerTest1", organizationDeviation.get("freelancerTest1"));

        assertEquals(expected, result);
    }

    @Test
    public void determineDelayOrganization(){
        //Creating one Organization
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        //Creating two transactions
        Task t1 = new Task("idTest",	"Web Development",	70,
                23,	"Software Development");
        Calendar d1=Calendar.getInstance ();
        d1.set (2020, Calendar.JANUARY, 24);
        Execution e1 = new Execution(d1, 5, "High Quality Work");
        Freelancer f1 = new Freelancer("freelancerTest",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr = new Transaction("transactionIDtest", t1, e1, f1);

        Task t2 = new Task("idTest1",	"Network Administration",	20,	40,
                "Network Administration");
        Calendar d2=Calendar.getInstance ();
        d2.set (2020, Calendar.JANUARY, 4);
        Execution e2 = new Execution(d2, -3, "High Quality Work");
        Transaction tr1 = new Transaction("transactionIDtest2", t2, e2, f1);

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);

        o.getListTasks().getListTasks().add(t2);
        o.getListTransactions().getM_lstTransactions().add(tr1);

        //Testing the options
        TreeMap<String, List<Double>> expected = new TreeMap<>();
        expected.put("freelancerTest", new ArrayList<>());
        expected.get("freelancerTest").add(5d);
        expected.get("freelancerTest").add(-3d);

        TreeMap<String, List<Double>> organizationData = o.determineDelayOrganization();
        TreeMap<String, List<Double>> result=new TreeMap<>();
        result.put("freelancerTest",organizationData.get("freelancerTest"));
        assertEquals(expected, result);
    }

    @Test
    public void calculateMeanDelay(){
        //Creating one Organization
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        //Creating three transactions
        Task t1 = new Task("idTest",	"Web Development",	70,
                23,	"Software Development");
        Calendar d1=Calendar.getInstance ();
        d1.set (2020, Calendar.JANUARY, 24);
        Execution e1 = new Execution(d1, 5, "High Quality Work");
        Freelancer f1 = new Freelancer("freelancerTest",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr = new Transaction("transactionIDtest", t1, e1, f1);

        Task t2 = new Task("idTest1",	"Network Administration",	20,	40,
                "Network Administration");
        Calendar d2=Calendar.getInstance ();
        d2.set (2020, Calendar.JANUARY, 4);
        Execution e2 = new Execution(d2, -3, "High Quality Work");
        Transaction tr1 = new Transaction("transactionIDtest2", t2, e2, f1);

        Task t3 = new Task("idTest2",	"Network Administration",	8,	89,
                "Network Administration");
        Calendar d3=Calendar.getInstance ();
        d3.set (2020, Calendar.JANUARY, 18);
        Execution e3 = new Execution(d2, 29, "High Quality Work");
        Freelancer f2 =  new Freelancer("freelancerTest1",	"nameTest1",	"Senior",
                "nameTest1@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr2 = new Transaction("102", t3, e3, f2);

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);
        o.getListTasks().getListTasks().add(t2);
        o.getListTransactions().getM_lstTransactions().add(tr1);
        o.getListTasks().getListTasks().add(t3);
        o.getListTransactions().getM_lstTransactions().add(tr2);

        TreeMap<String, List<Double>> mapTotalDelays = new TreeMap<>();
        mapTotalDelays.put("freelancerTest", new ArrayList<>());
        mapTotalDelays.get("freelancerTest").add(5d);
        mapTotalDelays.get("freelancerTest").add(-3d);
        mapTotalDelays.put("freelancerTest1", new ArrayList<>());
        mapTotalDelays.get("freelancerTest1").add(29d);

        TreeMap<String, Double> expected = new TreeMap<>();
        expected.put("freelancerTest",1d);
        expected.put("freelancerTest1", 29d);

        TreeMap<String, Double> organizationMean = o.calculateMeanDelay(mapTotalDelays);
        TreeMap<String, Double> result=new TreeMap<>();
        result.put("freelancerTest", organizationMean.get("freelancerTest"));
        result.put("freelancerTest1", organizationMean.get("freelancerTest1"));

        assertEquals(expected, result);

    }

    @Test
    public void calculateDeviationDelay(){
        //Creating one Organization
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);


        //Creating three transactions
        Task t1 = new Task("idTest",	"Web Development",	70,
                23,	"Software Development");
        Calendar d1=Calendar.getInstance ();
        d1.set (2020, Calendar.JANUARY, 24);
        Execution e1 = new Execution(d1, 5, "High Quality Work");
        Freelancer f1 = new Freelancer("freelancerTest",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr = new Transaction("transactionIDtest", t1, e1, f1);

        Task t2 = new Task("idTest1",	"Network Administration",	20,	40,
                "Network Administration");
        Calendar d2=Calendar.getInstance ();
        d2.set (2020, Calendar.JANUARY, 4);
        Execution e2 = new Execution(d2, -3, "High Quality Work");
        Transaction tr1 = new Transaction("transactionIDtest2", t2, e2, f1);

        Task t3 = new Task("idTest2",	"Network Administration",	8,	89,
                "Network Administration");
        Calendar d3=Calendar.getInstance ();
        d3.set (2020, Calendar.JANUARY, 18);
        Execution e3 = new Execution(d2, 29, "High Quality Work");
        Freelancer f2 =  new Freelancer("freelancerTest1",	"nameTest1",	"Senior",
                "nameTest1@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr2 = new Transaction("102", t3, e3, f2);

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);
        o.getListTasks().getListTasks().add(t2);
        o.getListTransactions().getM_lstTransactions().add(tr1);
        o.getListTasks().getListTasks().add(t3);
        o.getListTransactions().getM_lstTransactions().add(tr2);

        //Determining all payments related to each freelancer
        TreeMap<String, List<Double>> mapTotalDelays = new TreeMap<>();
        mapTotalDelays.put("freelancerTest", new ArrayList<>());
        mapTotalDelays.get("freelancerTest").add(5d);
        mapTotalDelays.get("freelancerTest").add(-3d);
        mapTotalDelays.put("freelancerTest1", new ArrayList<>());
        mapTotalDelays.get("freelancerTest1").add(29d);

        //Determining payment mean for each freelancer
        TreeMap<String, Double> mapMeanDelays = new TreeMap<>();
        mapMeanDelays.put("freelancerTest",1d);
        mapMeanDelays.put("freelancerTest1", 29d);

        //Comparing
        TreeMap<String, Double> expected = new TreeMap<>();
        expected.put("freelancerTest", 4d);
        expected.put("freelancerTest1", 0d);

        TreeMap<String, Double> organizationDeviation = o.calculateDeviationPayment(mapTotalDelays,mapMeanDelays);
        TreeMap<String, Double> result=new TreeMap<>();
        result.put("freelancerTest", organizationDeviation.get("freelancerTest"));
        result.put("freelancerTest1", organizationDeviation.get("freelancerTest1"));
        assertEquals(expected, result);
    }

    @Test
    public void determineIntervalsMean(){
        //Creating an Organization
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        //Creating two transactions
        Task t1 = new Task("idTest",	"Web Development",	70,
                23,	"Software Development");
        Calendar d1=Calendar.getInstance ();
        d1.set (2020, Calendar.JANUARY, 24);
        Execution e1 = new Execution(d1, 5, "High Quality Work");
        Freelancer f1 = new Freelancer("freelancerTest",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr = new Transaction("transactionIDtest", t1, e1, f1);

        Task t2 = new Task("idTest1",	"Network Administration",	20,	40,
                "Network Administration");
        Calendar d2=Calendar.getInstance ();
        d2.set (2020, Calendar.JANUARY, 4);
        Execution e2 = new Execution(d2, -3, "High Quality Work");
        Transaction tr1 = new Transaction("transactionIDtest2", t2, e2, f1);

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);
        o.getListTasks().getListTasks().add(t2);
        o.getListTransactions().getM_lstTransactions().add(tr1);

        //Adding the payments
        TreeMap<String, List<Double>> mapTotalPayments = new TreeMap<>();
        mapTotalPayments.put("freelancerTest", new ArrayList<>());
        mapTotalPayments.get("freelancerTest").add(1610d);
        mapTotalPayments.get("freelancerTest").add(800d);

        double expected = 1205d;

        double result = o.determineIntervalsMean(mapTotalPayments);
        assertEquals(expected, result, 2);

        TreeMap<String, List<Double>> mapTotalDelays = new TreeMap<>();
        mapTotalDelays.put("freelancerTest", new ArrayList<>());
        mapTotalDelays.get("freelancerTest").add(5d);
        mapTotalDelays.get("freelancerTest").add(-3d);

        double expected1 = 1d;

        double result1 = o.determineIntervalsMean(mapTotalDelays);
        assertEquals(expected1, result1, 2);
    }

    @Test
    public void determineIntervalsDeviation(){
        //Creating an Organization
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        //Creating two transactions
        Task t1 = new Task("idTest",	"Web Development",	70,
                23,	"Software Development");
        Calendar d1=Calendar.getInstance ();
        d1.set (2020, Calendar.JANUARY, 24);
        Execution e1 = new Execution(d1, 5, "High Quality Work");
        Freelancer f1 = new Freelancer("freelancerTest",	"nameTest",	"Junior",
                "nameTest@gmail.com",	"00000000",	"PTXXXXXXXXXXXXXXXXXX",
                "Street X, Y, Z",	"Portugal");
        Transaction tr = new Transaction("transactionIDtest", t1, e1, f1);

        Task t2 = new Task("idTest1",	"Network Administration",	20,	40,
                "Network Administration");
        Calendar d2=Calendar.getInstance ();
        d2.set (2020, Calendar.JANUARY, 4);
        Execution e2 = new Execution(d2, -3, "High Quality Work");
        Transaction tr1 = new Transaction("transactionIDtest2", t2, e2, f1);

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);
        o.getListTasks().getListTasks().add(t2);
        o.getListTransactions().getM_lstTransactions().add(tr1);

        //Adding the payments
        TreeMap<String, List<Double>> mapTotalPayments = new TreeMap<> ();
        mapTotalPayments.put("freelancerTest", new ArrayList<> ());
        mapTotalPayments.get("freelancerTest").add(1610d);
        mapTotalPayments.get("freelancerTest").add(800d);

        double expected = 405d;

        double result = o.determineIntervalsDeviation(mapTotalPayments);
        assertEquals(expected, result, 2);

        TreeMap<String, List<Double>> mapTotalDelays = new TreeMap<>();
        mapTotalDelays.put("freelancerTest", new ArrayList<>());
        mapTotalDelays.get("freelancerTest").add(5d);
        mapTotalDelays.get("freelancerTest").add(-3d);

        double expected1 = 4d;

        double result1 = o.determineIntervalsDeviation(mapTotalDelays);
        assertEquals(expected1, result1, 2);
    }

}