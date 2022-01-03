package pt.ipp.isep.dei.lapr2.emailService;

import pt.ipp.isep.dei.lapr2.pot.model.*;

import java.io.*;
import java.util.List;

public class EmailToTextFile implements ExternalEmailService {
    private final String FILE_DIRECTORY=Constants.TEXT_FILE_BASEPATH+"email.txt";

    /**
     * Sends an email with the email and password
     * @param email The e-mail to send the credentials to.
     * @param password The password to be sent.
     * @return returns true or false
     */
    @Override
    public boolean sendPassword(String email, String password) {
        try {
            FileWriter formatter=new FileWriter ( FILE_DIRECTORY, true );
            formatter.append ("\n------Account "+email);
            formatter.append ( "\n\tPassword: " +password);
            formatter.close ();
            return true;
        } catch (IOException e) {
            e.printStackTrace ();
            return false;
        }
    }

    /**
     * Sends an email warning for bad performance
     * @param email email of the freelancer
     * @return returns true or false
     */
    @Override
    public boolean sendWarningEmail(String email) {
        try {
            //Formatter formatter=new Formatter (new File ( email+".txt" ) );
            FileWriter formatter=new FileWriter ( FILE_DIRECTORY, true );
            formatter.append("\n\n------Delay Warning Email");
            formatter.append ( "\ne-mail: " +email);
            formatter.append("\nWatch out!");
            formatter.append("\nYour income is weak!");
            formatter.append("\nYour statistics show a delay higher than 3 hours and your percentage of delays is higher than the overall percentage of delays.");
            formatter.close ();
            /*
            formatter.format ( "\ne-mail: $s" +email);
            formatter.format("Watch out!");
            formatter.format("\nYour income is weak!");
            formatter.format("Your statistics show a delay higher than 3 hours and your percentage of delays is higher than the overall percentage of delays.");
            formatter.close ();

             */
            return true;
        } catch (IOException e) {
            e.printStackTrace ();
            return false;
        }
    }

    public boolean sendFreelancerPaymentWarning(String email, List<Transaction> lstTran, Bill bill) {
        try {
            double sum = 0;
            double convertedSum = 0;
            //Formatter formatter=new Formatter (new File ( email+".txt" ) );
            FileWriter formatter=new FileWriter ( Constants.TEXT_FILE_BASEPATH+"organizations.txt", true );
            /*
            formatter.format ( "\ne-mail: $s" +email);
            formatter.format("\nYour payment from $s has been made!", bill.getOrgName());
            formatter.format("\nNo. of transactions are $d", lstTran.size());
            formatter.format("------------------------------------------------\n");

             */
            formatter.append ( "\n------Payment Bill Email" );
            formatter.append ( "\ne-mail: " +email);
            formatter.append("\nYour payment from" +bill.getOrgName() +" has been made!");
            formatter.append("\nNo. of transactions are "+ lstTran.size());
            formatter.append("------------------------------------------------\n");
            for(Transaction tran: lstTran){
                /*
                formatter.write ("Transaction: $s\n"
                        + "Cost ( € ): $f\n"
                        + "Converted Cost: $f\n", tran.getTransactionID(), tran.computeAmount(), bill.convertEURto(tran.getFreelancer().getFreelancerCountry(),tran.computeAmount()););
                */
                sum += tran.computeAmount();
                formatter.write ("Transaction: "+tran.getTransactionID()+"\n"
                        + "Cost ( € ): "+tran.computeAmount()+"\n"
                        + "Converted Cost: "+bill.convertEURto(tran.getFreelancer().getFreelancerCountry(),tran.computeAmount())+"\n");
                convertedSum += bill.convertEURto(tran.getFreelancer().getFreelancerCountry(),tran.computeAmount());
            }
            formatter.append("------------------------------------------------\n");
            formatter.append ("The total sum of the transaction was "+sum+" (euros)\n");
            formatter.append("The convert sum was "+convertedSum);
            formatter.close ();
            return true;
        } catch (IOException e) {
            e.printStackTrace ();
            return false;
        }
    }
}
