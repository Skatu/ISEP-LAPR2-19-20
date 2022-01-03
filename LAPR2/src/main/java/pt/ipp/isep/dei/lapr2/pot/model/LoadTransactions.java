package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.Serializable;
import java.util.*;

/**
 * Responsible for initiating the file reading.
 * It resorts to the Strategy Pattern to support
 * various types of files.
 * Implements the {@link Serializable} interface.
 */
public class LoadTransactions implements Serializable {

    /**
     * Serialization ID for the class.
     */
    private static final long serialVersionUID = -3844577763605688219L;
    /**
     * A {@link Set} containing all the valid file extensions that can be read by the program.
     * If a new extension is to be added to this set, it is done through the static method of the class.
     */
    private static Set<String> validExtensions;

    /**
     * Interface instance responsible for reading the various types of files
     */
    private transient ReadFileStrategy readFileStrategy;

    static{
        validExtensions=new HashSet<> ();
        validExtensions.add ( ".txt" );
        validExtensions.add ( ".csv" );
    }

    /**
     * Sets which class (that implements the {@link ReadFileStrategy} interface) will be responsible for reading the file.
     * @param readFileStrategy Instance that implements the {@link ReadFileStrategy} interface
     */
    public void setReadFileStrategy(ReadFileStrategy readFileStrategy){
        this.readFileStrategy=readFileStrategy;
    }

    /**
     * Initiates the reading of the file. Calls the interface method <code>readFile</code>.
     * @param strDirectory The directory of the file to be read.
     */
    public void read(String strDirectory){
        readFileStrategy.readFile ( strDirectory );
    }

    /**
     * Obtains a {@link List} of {@link String} containing the
     * successfully read lines from the file.
     * Calls the interface method <code>getListSuccessfulLines</code>.
     * @return {@link List} of {@link String} describing all the successfully read lines.
     */
    public List<String> getListSuccessfulLines() {
        return readFileStrategy.getListSuccessfulLines ();
    }

    /**
     * Obtains a {@link List} of {@link String} containing the
     * failed read lines from the file.
     * Calls the interface method <code>getListFailedLines</code>.
     * @return {@link List} of {@link String} describing all the failed read lines.
     */
    public List<String> getListFailedLines() {
        return readFileStrategy.getListFailedLines ();
    }

    /**
     * Obtains a {@link List} of {@link Transaction} containing the
     * all the transactions read lines from the file. This list is called so all the read
     * transactions are displayed on the {@link javafx.scene.control.TableView}.
     * Calls the interface method <code>getListTransactionsRead</code>.
     * @return {@link List} of {@link String} describing all the failed read lines in the {@link pt.ipp.isep.dei.lapr2.pot.ui.LoadTransactionsUI}.
     */
    public List<Transaction> getListTransactionsRead() {
        return readFileStrategy.getListTransactionsRead ();
    }

    /**
     * Retrieves the extension of the file to be read from the 
     * @param directory The directory of the file to get the extension from.
     * @return The extension for the file
     * @throws IndexOutOfBoundsException if the file doesn't contain an extension.
     */
    public String getFileExtension(String directory){
        int extensionIndex=directory.lastIndexOf ( "." );
        return directory.substring ( extensionIndex );
    }

    /**
     * Checks if the input file has a valid extension. For this, it loops through
     * <code>validExtensions</code> {@link HashSet} to verify if the extension exists.
     * @param directoryExtension The directory of the file to validate.
     * @return <code>true</code> if the file is valid. <code>false</code> otherwise.
     */
    public static boolean isValidFile(String directoryExtension){
        for(String ext: validExtensions){
            if(directoryExtension.endsWith ( ext ))
                return true;
        }
        return false;
    }

    /**
     * Gets the summary of the file reading. The summary
     * contains the total number of lines read , how many
     * and which lines were successful, and how many and which
     * lines were not read.
     * @return {@link String} representation of the summary of the file reading.
     */
    public String getLoadFileLog(){
        StringBuilder s=new StringBuilder ();

        s.append ( "Number of lines read: " );
        s.append ( readFileStrategy.getListSuccessfulLines ().size () + readFileStrategy.getListFailedLines ().size () );
        s.append ( "\n" );
        s.append ( "Successful lines: " );
        s.append ( readFileStrategy.getListSuccessfulLines ().size () );
        s.append ( "\n" );
        List<String> succLines=readFileStrategy.getListSuccessfulLines ();
        for(String line:succLines ){
            s.append ( "\t" );
            s.append ( line );
            s.append ( "\n" );
        }
        s.append ( "Failed lines: " );
        s.append ( readFileStrategy.getListFailedLines ().size () );
        s.append ( "\n" );
        List<String> failLines=readFileStrategy.getListFailedLines ();
        for(String line:failLines ){
            s.append ( "\t" );
            s.append ( line );
            s.append ( "\n" );
        }
        return s.toString ();
    }
}
