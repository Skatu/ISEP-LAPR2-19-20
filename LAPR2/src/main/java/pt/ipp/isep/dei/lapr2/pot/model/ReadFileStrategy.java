package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.Serializable;
import java.util.List;

/**
 * Interface used for reading various types of files.
 * The Strategy Pattern is applied.
 */
public interface ReadFileStrategy extends Serializable {
    /**
     * Reads the text file chosen by the user.
     * @param directory {@link String} of the directory of the file chosen.
     */
    void readFile(String directory);

    /**
    * Obtains a {@link List} of {@link String} containing the
    * successfully read lines from the file.
    * @return {@link List} of {@link String} describing all the successfully read lines.
    */
    List<String> getListSuccessfulLines();
    /**
     * Obtains a {@link List} of {@link String} containing the
     * failed read lines from the file.
     * @return {@link List} of {@link String} describing all the failed read lines.
     */
    List<String> getListFailedLines();
    /**
     * Obtains a {@link List} of {@link Transaction} containing the
     * all the transactions read lines from the file. This list is called so all the read
     * transactions are displayed on the {@link javafx.scene.control.TableView} in the {@link pt.ipp.isep.dei.lapr2.pot.ui.LoadTransactionsUI}.
     * @return {@link List} of {@link String} describing all the failed read lines.
     */
    List<Transaction> getListTransactionsRead();
}

