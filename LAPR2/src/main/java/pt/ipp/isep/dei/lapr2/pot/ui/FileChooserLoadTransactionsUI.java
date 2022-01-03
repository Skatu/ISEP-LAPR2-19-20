package pt.ipp.isep.dei.lapr2.pot.ui;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.util.ArrayList;
import java.util.List;

public class FileChooserLoadTransactionsUI {
    private FileChooser fileChooser;

    public FileChooserLoadTransactionsUI() {
        fileChooser=new FileChooser ();
        List<String> extensions=new ArrayList<> ();
        extensions.add ( "*.txt" );
        extensions.add ( "*.csv" );
        associateFilter ( "Transactions Text Files",extensions );
    }

    public static FileChooser createFileChooseTransactionsFile(){
       FileChooserLoadTransactionsUI fcTransactions=new FileChooserLoadTransactionsUI ();
       return fcTransactions.fileChooser;
    }

    private void associateFilter(String description, List<String> extensions){
        ExtensionFilter filter=new ExtensionFilter ( description,extensions );
        fileChooser.getExtensionFilters ().add ( filter );
    }
}
