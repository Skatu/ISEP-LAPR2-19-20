package pt.ipp.isep.dei.lapr2.fileReading;

import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReadTxtFile implements ReadFileStrategy, Serializable {

    private static final long serialVersionUID = -5663558123023699519L;
    private final String SEPARATOR="\t";
    private final int NUMBER_OF_COLUMNS=17;
    private int lineCounter;

    private RegistryFreelancers m_oRegistryFreelancers;
    private ListTasks m_oListTasks;
    private ListTransactions m_oListTransactions;
    private ArrayList<String> lstSuccessfulLines;
    private ArrayList<String> lstFailedLines;
    private ArrayList<Transaction> lstTransactionsRead;

    private Transaction transaction;
    private String transactionID;
    private Task task;
    private Execution execution;
    private Freelancer freelancer;

    public ReadTxtFile(RegistryFreelancers m_oRegistryFreelancers, ListTasks m_oListTasks, ListTransactions m_oListTransactions) {
        if(m_oRegistryFreelancers==null)
            throw new IllegalArgumentException ("The freelancer registry cannot be null.");
        if(m_oListTasks==null)
            throw new IllegalArgumentException ("The tasks list cannot be null.");
        this.m_oRegistryFreelancers=m_oRegistryFreelancers;
        this.m_oListTasks=m_oListTasks;
        this.m_oListTransactions=m_oListTransactions;
        this.lstSuccessfulLines =new ArrayList<> ();
        this.lstFailedLines=new ArrayList<> ();
        this.lstTransactionsRead=new ArrayList<> ();
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
        return this.lstSuccessfulLines;
    }

    @Override
    public List<String> getListFailedLines() {
        return this.lstFailedLines;
    }

    @Override
    public List<Transaction> getListTransactionsRead() {
        return lstTransactionsRead;
    }

    private void parseFile(Scanner sc){
        try {
            //Discard the header
            sc.nextLine ();
            //Loop through the lines of the file
            while (sc.hasNextLine ()) {
                String[] s = sc.nextLine ().split ( SEPARATOR );
                //If the line doesn't have the correct numbers of columns, move on to the next line.
                if (s.length != NUMBER_OF_COLUMNS) {
                    registerFailedLine ( "Line " + lineCounter + "- Line doesn't have the correct number of columns" );
                    continue;
                }
                try {
                    transactionID = s[0];
                    //If the transaction already exists, move on to the next line.
                    if (existsTransaction ( transactionID )) {
                        registerFailedLine ( "ID " + transactionID + "- Transaction with the same ID already exists in the system" );
                        lineCounter++;
                        continue;
                    }
                    //Create the transaction
                    if (createTask ( s ) && createExecution ( s ) && createFreelancer ( s )) {
                        createTransaction ( transactionID );
                        lstSuccessfulLines.add ( "ID " + transactionID + "- Register successful" );
                    }

                } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                    e.printStackTrace ();
                }
                lineCounter++;
            }
        }catch (NoSuchElementException | IllegalStateException e){
            e.getStackTrace ();
        }
    }

    private void registerFailedLine(String message){
        lstFailedLines.add ( message );
    }

    private boolean createTransaction(String transactionID){
        transaction= m_oListTransactions.newTransaction(transactionID,task,execution,freelancer);
        if(m_oListTransactions.validateTransaction ( transaction )){
            m_oListTransactions.registerTransaction ( transaction );
            //add a copy of the transaction to the successfully read list
            return lstTransactionsRead.add ( new Transaction ( transaction ) );
        }
        return false;
    }

    private boolean existsTransaction(String transactionID){
        return m_oListTransactions.existsTransaction ( transactionID );
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
                registerFailedLine ( "ID "+transactionID+": Invalid task data" );
                return false;
            }
        }
        return true;
    }

    private Task getTaskByID(String id){
        return m_oListTasks.getTaskByID ( id );
    }

    private boolean validateTask(Task t){
        return m_oListTasks.validateTask ( t );
    }

    private boolean registerTask(Task t){
        return m_oListTasks.registerTask ( t );
    }

    private boolean createExecution(String[] s){
        execution=Transaction.newExecution ( s[6],s[7],s[8] );
        if(execution==null){
            registerFailedLine ( "ID "+transactionID+"- Invalid execution data" );
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
                registerFailedLine ( "ID "+transactionID+"- Invalid freelancer data" );
                return false;
            }
        }
        return true;
    }

    private Freelancer getFreelancerByID(String id){
        return m_oRegistryFreelancers.getFreelancerByID(id);
    }

    private boolean validateFreelancer(Freelancer f){
        return m_oRegistryFreelancers.validateFreelancer ( f );
    }

    private boolean registerFreelancer(Freelancer f){
        return m_oRegistryFreelancers.registerFreelancer ( f );
    }
}
