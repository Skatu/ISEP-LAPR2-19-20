package pt.ipp.isep.dei.lapr2.pot.model;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class RegistryOrganizationsTest {

    @Test
    public void determinePaymentPlatform() {

        //Creating two Organizations
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        Manager m1 = new Manager("manager1", "manager1@email.com");
        Collaborator c1 = new Collaborator("collaborator1", "collaborator1@email.com");
        Organization o1 = new Organization("name1", "11111111", m1, c1);

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

        o1.getListTasks().getListTasks().add(t2);
        o1.getListTransactions().getM_lstTransactions().add(tr1);

        //Testing the options
        TreeMap<String, List<Double>> expected = new TreeMap<>();
        expected.put("freelancerTest", new ArrayList<>());
        expected.get("freelancerTest").add(1610d);
        expected.get("freelancerTest").add(800d);

        RegistryOrganizations registryOrganizations = new RegistryOrganizations();
        registryOrganizations.getM_lstOrganizations().add(o);
        registryOrganizations.getM_lstOrganizations().add(o1);
        TreeMap<String, List<Double>> platformData = registryOrganizations.determinePaymentPlatform();
        TreeMap<String, List<Double>> result=new TreeMap<>();
        result.put("freelancerTest",platformData.get("freelancerTest"));
        assertEquals(expected, result);
    }

    @Test
    public void calculateMeanPayment() {

        //Creating two Organizations
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        Manager m1 = new Manager("manager1", "manager1@email.com");
        Collaborator c1 = new Collaborator("collaborator1", "collaborator1@email.com");
        Organization o1 = new Organization("name1", "11111111", m1, c1);

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
        o1.getListTasks().getListTasks().add(t2);
        o1.getListTransactions().getM_lstTransactions().add(tr1);
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

        RegistryOrganizations registryOrganizations = new RegistryOrganizations();
        TreeMap<String, Double> platformMean = registryOrganizations.calculateMean(mapTotalPayments);
        TreeMap<String, Double> result=new TreeMap<>();
        result.put("freelancerTest", platformMean.get("freelancerTest"));
        result.put("freelancerTest1", platformMean.get("freelancerTest1"));

        assertEquals(expected, result);

    }

    @Test
    public void calculateDeviationPayment() {

        //Creating two Organizations
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        Manager m1 = new Manager("manager1", "manager1@email.com");
        Collaborator c1 = new Collaborator("collaborator1", "collaborator1@email.com");
        Organization o1 = new Organization("name1", "11111111", m1, c1);

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
        o1.getListTasks().getListTasks().add(t2);
        o1.getListTransactions().getM_lstTransactions().add(tr1);
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

        RegistryOrganizations registryOrganizations = new RegistryOrganizations();
        TreeMap<String, Double> platformDeviation = registryOrganizations.calculateDeviation(mapTotalPayments,mapMeanPayments);
        TreeMap<String, Double> result=new TreeMap<>();
        result.put("freelancerTest", platformDeviation.get("freelancerTest"));
        result.put("freelancerTest1", platformDeviation.get("freelancerTest1"));

        assertEquals(expected, result);

    }

    @Test
    public void determineDelayPlatform(){

        //Creating two Organizations
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        Manager m1 = new Manager("manager1", "manager1@email.com");
        Collaborator c1 = new Collaborator("collaborator1", "collaborator1@email.com");
        Organization o1 = new Organization("name1", "11111111", m1, c1);

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

        o1.getListTasks().getListTasks().add(t2);
        o1.getListTransactions().getM_lstTransactions().add(tr1);

        //Testing the options
        TreeMap<String, List<Double>> expected = new TreeMap<>();
        expected.put("freelancerTest", new ArrayList<>());
        expected.get("freelancerTest").add(5d);
        expected.get("freelancerTest").add(-3d);

        RegistryOrganizations registryOrganizations = new RegistryOrganizations();
        registryOrganizations.getM_lstOrganizations().add(o);
        registryOrganizations.getM_lstOrganizations().add(o1);
        TreeMap<String, List<Double>> platformData = registryOrganizations.determineDelayPlatform();
        TreeMap<String, List<Double>> result=new TreeMap<>();
        result.put("freelancerTest",platformData.get("freelancerTest"));
        assertEquals(expected, result);
    }


    @Test
    public void calculateMeanDelay() {

        //Creating two Organizations
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        Manager m1 = new Manager("manager1", "manager1@email.com");
        Collaborator c1 = new Collaborator("collaborator1", "collaborator1@email.com");
        Organization o1 = new Organization("name1", "11111111", m1, c1);

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
        o1.getListTasks().getListTasks().add(t2);
        o1.getListTransactions().getM_lstTransactions().add(tr1);
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

        RegistryOrganizations registryOrganizations = new RegistryOrganizations();
        TreeMap<String, Double> platformMean = registryOrganizations.calculateMean(mapTotalDelays);
        TreeMap<String, Double> result=new TreeMap<>();
        result.put("freelancerTest", platformMean.get("freelancerTest"));
        result.put("freelancerTest1", platformMean.get("freelancerTest1"));

        assertEquals(expected, result);

    }

    @Test
    public void calculateDeviationDelay() {

        //Creating two Organizations
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        Manager m1 = new Manager("manager1", "manager1@email.com");
        Collaborator c1 = new Collaborator("collaborator1", "collaborator1@email.com");
        Organization o1 = new Organization("name1", "11111111", m1, c1);

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
        o1.getListTasks().getListTasks().add(t2);
        o1.getListTransactions().getM_lstTransactions().add(tr1);
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

        RegistryOrganizations registryOrganizations = new RegistryOrganizations();
        TreeMap<String, Double> platformDeviation = registryOrganizations.calculateDeviation(mapTotalDelays,mapMeanDelays);
        TreeMap<String, Double> result=new TreeMap<>();
        result.put("freelancerTest", platformDeviation.get("freelancerTest"));
        result.put("freelancerTest1", platformDeviation.get("freelancerTest1"));
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

        //Adding the organization to the registry
        RegistryOrganizations registryOrganizations = new RegistryOrganizations();
        registryOrganizations.getM_lstOrganizations().add(o);

        double result = registryOrganizations.determineIntervalsMean(mapTotalPayments);
        assertEquals(expected, result, 2);

        TreeMap<String, List<Double>> mapTotalDelays = new TreeMap<>();
        mapTotalDelays.put("freelancerTest", new ArrayList<>());
        mapTotalDelays.get("freelancerTest").add(5d);
        mapTotalDelays.get("freelancerTest").add(-3d);

        double expected1 = 1d;

        double result1 = registryOrganizations.determineIntervalsMean(mapTotalDelays);
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
        TreeMap<String, List<Double>> mapTotalPayments = new TreeMap<>();
        mapTotalPayments.put("freelancerTest", new ArrayList<>());
        mapTotalPayments.get("freelancerTest").add(1610d);
        mapTotalPayments.get("freelancerTest").add(800d);

        double expected = 405d;

        //Adding the organization to the registry
        RegistryOrganizations registryOrganizations = new RegistryOrganizations();
        registryOrganizations.getM_lstOrganizations().add(o);

        double result = registryOrganizations.determineIntervalsDeviation(mapTotalPayments);
        assertEquals(expected, result, 2);

        TreeMap<String, List<Double>> mapTotalDelays = new TreeMap<>();
        mapTotalDelays.put("freelancerTest", new ArrayList<>());
        mapTotalDelays.get("freelancerTest").add(5d);
        mapTotalDelays.get("freelancerTest").add(-3d);

        double expected1 = 4d;

        double result1 = registryOrganizations.determineIntervalsDeviation(mapTotalDelays);
        assertEquals(expected1, result1, 2);
    }

    @Test
    public void calculateNormalDistribution(){
        //Creating an Organization
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        //Creating two transactions
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

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);
        o.getListTasks().getListTasks().add(t2);
        o.getListTransactions().getM_lstTransactions().add(tr1);

        //Adding the organization to the registry
        RegistryOrganizations registryOrganizations = new RegistryOrganizations();
        registryOrganizations.getM_lstOrganizations().add(o);

        double result = registryOrganizations.calculateNormalDistribution();
        double expected = 0.812969;

        assertEquals(expected, result, 2);
    }

    @Test
    public void determineDelayPlatformThatYear() {
        //Creating two Organizations
        Manager m = new Manager("manager", "manager@email.com");
        Collaborator c = new Collaborator("collaborator", "collaborator@email.com");
        Organization o = new Organization("name", "235756423", m, c);

        Manager m1 = new Manager("manager1", "manager1@email.com");
        Collaborator c1 = new Collaborator("collaborator1", "collaborator1@email.com");
        Organization o1 = new Organization("name1", "11111111", m1, c1);

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

        Task t3 = new Task("idTest2",	"Network Administration",	20,	40,
                "Network Administration");
        Calendar d3=Calendar.getInstance ();
        d3.set (2019, Calendar.JANUARY, 4);
        Execution e3 = new Execution(d3, 5, "High Quality Work");
        Transaction tr2 = new Transaction("transactionIDtest3", t3, e3, f1);

        //Adding the transactions to the ListTransactions
        o.getListTasks().getListTasks().add(t1);
        o.getListTransactions().getM_lstTransactions().add(tr);

        o1.getListTasks().getListTasks().add(t2);
        o1.getListTransactions().getM_lstTransactions().add(tr1);

        o1.getListTasks().getListTasks().add(t3);
        o1.getListTransactions().getM_lstTransactions().add(tr2);

        //Testing the options
        TreeMap<String, List<Double>> expected = new TreeMap<>();
        expected.put("freelancerTest", null);

        RegistryOrganizations registryOrganizations = new RegistryOrganizations();
        registryOrganizations.getM_lstOrganizations().add(o);
        registryOrganizations.getM_lstOrganizations().add(o1);
        TreeMap<String, List<Double>> platformData = registryOrganizations.determineDelayPlatformThatYear();
        TreeMap<String, List<Double>> result=new TreeMap<>();
        result.put("freelancerTest",platformData.get("freelancerTest"));
        assertEquals (expected, result);
    }
}