package pt.ipp.isep.dei.lapr2.pot.controller;

import pt.ipp.isep.dei.lapr2.authorization.model.UserSession;
import pt.ipp.isep.dei.lapr2.fileReading.ReadCsvFile;
import pt.ipp.isep.dei.lapr2.fileReading.ReadTxtFile;
import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.util.List;

/**
 * Controller class for the Load Transactions use case.
 * @author Rafael Moreira - 1181055
 */
public class LoadTransactionsController {

    /**
     * The Singleton Platform instance.
     */
    private Platform m_oPlatform;
    /**
     * Class registry of the freelancers.
     */
    private RegistryFreelancers m_oRegistryFreelancers;
    /**
     * Class registry of the organizations.
     */
    private RegistryOrganizations m_oRegistryOrganizations;
    /**
     * The respective organization.
     */
    private Organization m_oOrg;
    /**
     * Class list of transactions of the respective organization.
     */
    private ListTransactions m_oListTransactions;
    /**
     * Class list of tasks of the respective organization.
     */
    private ListTasks m_oListTasks;
    /**
     * Class responsible for initiating the reading of the file.
     */
    private LoadTransactions loader;

    /**
     * Constructor. Validates user session and retrieves the Platform singleton.
     * @throws IllegalStateException The user session is not validated.
     */
    public LoadTransactionsController() {
        if(!ApplicationPOT.getInstance().getCurrentSession ().isLoggedInWithFunction ( Constants.FUNCTION_MANAGER_ORGANIZATION))
            throw new IllegalStateException("Non authorized User.");
        this.m_oPlatform =ApplicationPOT.getInstance ().getPlatform ();
    }

    /**
     * Retrieves all the necessary class instances
     * for the registration of the transactions
     * read from the text file.
     */
    public void initiateFileLoading(){
        ApplicationPOT app=ApplicationPOT.getInstance ();
        UserSession session=app.getCurrentSession ();
        String strEmail=session.getUserEmail ();
        this.m_oRegistryOrganizations=m_oPlatform.getRegistryOrganizations ();
        this.m_oOrg=m_oRegistryOrganizations.getOrgByManagerEmail ( strEmail );
        this.m_oListTransactions=m_oOrg.getListTransactions();
    }

    /**
     * Checks if the directory has a valid extension.
     * Calls the static method <code>isValidFile</code>
     * from the class <code>LoadTransactions</code>.
     * @param directory The directory of the file.
     * @return <code>true</code> if the file is valid. <code>false</code> if it is not.
     */
    public boolean isValidFile(String directory){
        return LoadTransactions.isValidFile ( directory );
    }

    /**
     * Initiates the process of reading the file.
     * Strategy Pattern is applied.
     * First, retrieves the instances of <code>RegistryFreelancers</code>
     * and <code>ListTasks</code>.
     * Then, instantiates the <code>LoadTransactions loader</code> instance variable.
     * Subsequently, it retrieves the extension of the file using the instance method
     * <code>getFileExtension</code> of the class <code>LoadTransactions</code>
     * so it can use the correct class to read the specific file.
     * At the end, the file is read with the instance method <code>read</code>,
     * passing the directory as parameter.
     * @param strDirectory The directory of the file.
     * @return <code>true</code> if the reading is successful. <code>false</code> if it is not.
     */
    public boolean newFileReading(String strDirectory){
        this.m_oRegistryFreelancers = m_oPlatform.getRegistryFreelancers ();
        this.m_oListTasks=m_oOrg.getListTasks ();
        loader=new LoadTransactions ();
        String extension=loader.getFileExtension ( strDirectory );
        switch (extension){
            case ".csv":
                loader.setReadFileStrategy ( new ReadCsvFile ( m_oRegistryFreelancers, m_oListTasks, m_oListTransactions ) );
                break;
            case ".txt":
                loader.setReadFileStrategy ( new ReadTxtFile ( m_oRegistryFreelancers, m_oListTasks, m_oListTransactions ) );
                break;
        }
        loader.read ( strDirectory );
        return true;
    }

    /**
     * Calls instance method <code>getLoadFileLog</code> from the class
     * <code>LoadTransactions</code> to retrieve a summary of the file reading,
     * containing the number of lines read, how many and which lines were successfully read
     * and how many and which lines failed to be read.
     * @return String representation of the summary of the file reading.
     */
    public String getLog(){
        return loader.getLoadFileLog ();
    }

    /**
     * Calls instance method <code>getLstSuccessfulLines</code> from the class
     * <code>LoadTransactions</code> to retrieve how many lines were successfully read
     * from the file. This is done by obtaining the size of the list which contains
     * the log of successfully read lines.
     * @return The number of successfully read lines from the text file.
     */
    public int getNumberSuccessfullLines(){
        return loader.getListSuccessfulLines ().size ();
    }

    /**
     * Calls instance method <code>getLstFailedLines</code> from the class
     * <code>LoadTransactions</code> to retrieve how many lines were failed to be read
     * from the file. This is done by obtaining the size of the list which contains
     * the log of failed lines.
     * @return The number of failed read lines from the text file.
     */
    public int getNumberFailedLines(){
        return loader.getListFailedLines ().size ();
    }

    /**
     * Calls instance method <code>getLstTransactionsRea</code> from the class
     * <code>LoadTransactions</code> to retrieve the list of all transactions read
     * from the file. These transactions will then be shown in the UI for the
     * user to confirm all the data of the transactions was successfully read.
     * @return List containing all the transactions read from the text file.
     */
    public List<Transaction> getListTransactionsRead() {
        return loader.getListTransactionsRead ();
    }
}
