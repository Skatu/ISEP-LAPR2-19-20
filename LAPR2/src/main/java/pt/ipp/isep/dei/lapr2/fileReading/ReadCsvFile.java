package pt.ipp.isep.dei.lapr2.fileReading;

import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

public class ReadCsvFile implements ReadFileStrategy, Serializable {

    private static final long serialVersionUID = -5663558123023699519L;
    private final String SEPARATOR=";";
    private final int NUMBER_OF_COLUMNS=17;
    private int lineCounter;

    private RegistryFreelancers registryFreelancers;
    private ListTasks listTasks;
    private ListTransactions listTransactions;
    private ArrayList<String> listSuccessfulLines;
    private ArrayList<String> listFailedLines;
    private ArrayList<Transaction> listTransactionsRead;

    private Transaction transaction;
    private String transactionID;
    private Task task;
    private Execution execution;
    private Freelancer freelancer;

    public ReadCsvFile(RegistryFreelancers registryFreelancers, ListTasks listTasks, ListTransactions listTransactions) {
        if(registryFreelancers ==null)
            throw new IllegalArgumentException ("The freelancer registry cannot be null.");
        if(listTasks ==null)
            throw new IllegalArgumentException ("The tasks list cannot be null.");
        if(listTransactions==null){
            throw new IllegalArgumentException ("The transactions list cannot be null.");
        }
        this.registryFreelancers = registryFreelancers;
        this.listTasks = listTasks;
        this.listTransactions = listTransactions;
        this.listSuccessfulLines =new ArrayList<> ();
        this.listFailedLines =new ArrayList<> ();
        this.listTransactionsRead =new ArrayList<> ();
        this.lineCounter=2;
    }

    @Override
    public void readFile(String directory) {
        try{
            File file=new File (directory);
            Scanner sc=new Scanner ( file );
            parseFile ( sc );
        }catch (FileNotFoundException e){
            e.printStackTrace ();
        }
    }

    @Override
    public List<String> getListSuccessfulLines() {
        return this.listSuccessfulLines;
    }

    @Override
    public List<String> getListFailedLines() {
        return this.listFailedLines;
    }

    @Override
    public List<Transaction> getListTransactionsRead() {
        return listTransactionsRead;
    }

    private void parseFile(Scanner sc){
        //Discard the header
        try{
            sc.nextLine();
            //Loop through the lines of the file
            while(sc.hasNextLine ()){
                String[] s=sc.nextLine ().split ( SEPARATOR );
                //If the line doesn't have the correct numbers of columns, move on to the next line.
                if(s.length!= NUMBER_OF_COLUMNS){
                    registerFailedLine ("Line "+lineCounter +"- Line doesn't have the correct number of columns"  );
                    lineCounter++;
                    continue;
                }
                try{
                    transactionID=s[0];
                    //If the transaction already exists, move on to the next line.
                    if(existsTransaction ( transactionID )){
                        registerFailedLine ("ID "+transactionID  +"- Transaction with the same ID already exists in the system"  );
                        lineCounter++;
                        continue;
                    }
                    //Create the transaction
                    if(createTask (s) && createExecution (s) &&createFreelancer (s)){
                        createTransaction ( transactionID );
                        listSuccessfulLines.add ( "ID "+transactionID +"- Register successful" );
                    }

                }catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e){
                    e.printStackTrace ();
                }
                lineCounter++;
            }
        }catch (NoSuchElementException | IllegalStateException e){
            e.getStackTrace ();
        }
    }

    private void registerFailedLine(String message){
        listFailedLines.add ( message );
    }

    private boolean createTransaction(String transactionID){
        transaction= listTransactions.newTransaction(transactionID,task,execution,freelancer);
        if(listTransactions.validateTransaction ( transaction )){
            listTransactions.registerTransaction ( transaction );
            //add a copy of the transaction to the successfully read list
            return listTransactionsRead.add ( new Transaction ( transaction ) );
        }
        return false;
    }

    private boolean existsTransaction(String transactionID){
        return listTransactions.existsTransaction ( transactionID );
    }

    private boolean createTask(String[]s){
        String taskID=s[1];
        task=getTaskByID ( taskID );
        if(task==null){
            task=ListTasks.newTask ( taskID,s );
            if( validateTask ( task ) ) {
                registerTask ( task );
            }
            else{
                registerFailedLine ( "ID "+transactionID +"- Invalid task data" );
                return false;
            }
        }
        return true;
    }

    private Task getTaskByID(String id){
        return listTasks.getTaskByID ( id );
    }

    private boolean validateTask(Task t){
        return listTasks.validateTask ( t );
    }

    private boolean registerTask(Task t){
        return listTasks.registerTask ( t );
    }

    private boolean createExecution(String[] s){
        execution=Transaction.newExecution ( s[6],s[7],s[8] );
        if(execution==null){
            registerFailedLine ( "ID "+transactionID +"- Invalid execution data" );
            return false;
        }
        return true;
    }

    private boolean createFreelancer(String[]s){
        String freelancerID=s[9];
        freelancer=getFreelancerByID (freelancerID);
        if(freelancer==null){
            freelancer=RegistryFreelancers.newFreelancer ( freelancerID,s );
            if(validateFreelancer ( freelancer )){
                registerFreelancer ( freelancer );
            }
            else{
                registerFailedLine ( "ID "+transactionID +"- Invalid freelancer data" );
                return false;
            }
        }
        return true;
    }

    private Freelancer getFreelancerByID(String id){
        return registryFreelancers.getFreelancerByID(id);
    }

    private boolean validateFreelancer(Freelancer f){
        return registryFreelancers.validateFreelancer ( f );
    }

    private boolean registerFreelancer(Freelancer f){
        return registryFreelancers.registerFreelancer ( f );
    }
}
