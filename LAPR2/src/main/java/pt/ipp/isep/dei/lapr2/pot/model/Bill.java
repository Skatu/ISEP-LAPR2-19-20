/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.model;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author RodrigoRodrigues
 */

public class Bill implements CurrencyConverter{
    /**
     * List of Strings that represent each transaction's information
     */
    private List<String> Receipt;

    /**
     * List of freelancers present in the payment
     */
    private HashSet<Freelancer> lstFree;

    /**
     * Date of the payment
     */
    private Date currentDate;

    /**
     * Name of the Organization
     */
    private String OrgName;

    /**
     * List of transactions present in the payment
     */
    private List<Transaction> lstTran;

    /**
     * Constructor of the class
     * @param str name of the organization
     * @throws IOException
     */
    public Bill(String str) throws IOException{
        this.currentDate = new Date();
        OrgName = str;
    }

    /**
     * Constructor of the class
     * @param y year of the payment
     * @param m month of the payment
     * @param d day of the payment
     * @param h hour of the payment
     * @param str name of the organization
     */
    public Bill(int y, int m, int d, int h, String str){
        this.currentDate = new Date( y, m, d , h, 0);
        OrgName = str;
    }

    /**
     * Creates a list of strings that represents the transactions of the current billing
     * @param lstTran transactions made by the organization since the last payment
     */
    public void create(List<Transaction> lstTran){
        Receipt.clear();
        for (Transaction tran: lstTran){
            Freelancer free = tran.getFreelancer();
            if (lstFree.contains(free)){

            } else {
                lstFree.add(free);
            }
        }

        for(Freelancer free:lstFree){
            for(Transaction tran:lstTran){
                if(tran.getFreelancer() == free || validateTransaction(tran, free)){
                    addTransaction(tran,free);
                }
            }
        }
    }

    /**
     * Adds the bill to the existing PaymentHistory text file
     * @throws FileNotFoundException
     */
    public void createFile() throws FileNotFoundException{
        double sum = 0;
        if(lstTran.size() > 0){
            Formatter sc = new Formatter("TextFiles/PaymentHistory.txt");

            for (Transaction tran: lstTran){
                Freelancer free = tran.getFreelancer();
                if (lstFree.contains(free)){

                } else {
                    lstFree.add(free);
                }
            }
            sc.format("##############################################\n"
                    + "Organization: %s\n"
                    + "Date: %s\n"
                    + "No. of Freelancer(s): %d\n"
                    + "No. of Transaction(s): %d\n"
                    + "----------------------------------------------\n", OrgName, currentDate.toString(), lstFree.size(), lstTran.size());

            for(Freelancer free: lstFree){
                sc.format("Freelancer: %s\n", free.getFreelancerName());
                for( Transaction tran: lstTran){
                    if(tran.getFreelancer().equals(free)){
                        sc.format("Task: %s\n"
                                + "Cost ( € ): %f\n"
                                + "Converted Cost: %f\n", tran.getTask(), tran.computeAmount(), convertEURto(free.getFreelancerCountry(),tran.computeAmount()));
                        sum += tran.computeAmount();
                    }
                }
            }
            sc.format("----------------------------------------------\n" +
                    "Total: %f\n" +
                    "###############################################\n",sum);
            sc.close();
        }
    }

    /**
     * Validates transactions individually locally
     * @param tran
     * @param free
     * @return
     */
    private boolean validateTransaction(Transaction tran, Freelancer free){
        if(tran.getFreelancer() == free){
            return true;
        }
        return false;
    }

    /**
     * Creates a file recording a certain payment from an organization to a freelancer
     * @param free
     * @param lsttran
     * @throws FileNotFoundException
     */
    public void createFreelancerTransaction(Freelancer free, List<Transaction> lsttran) throws IOException {
        FileWriter sc = new FileWriter(new File("TextFiles/OrganizationToFreelancerPayments/" + OrgName + "_" + free.getFreelancerName() + "_" + currentDate.getDay() + "_" + currentDate.getMonth() + "_" + currentDate.getYear() +  ".txt"));
        double sum = 0;
        double convertedSum = 0;
        sc.write("##############################################" + "\n");
        sc.write("Organization: " + OrgName + "\n");
        sc.write("Freelancer:   " + free.getFreelancerName() + "\n");
        sc.write("Date: " + currentDate.toString() + "\n");
        sc.write("No. of Transactions: " + lsttran.size() + "\n" );
        sc.write("----------------------------------------------"+ "\n");

        for(Transaction tran: lsttran){
            sc.write("Transaction: " + tran.getTransactionID() + "\n");
            sc.write("Cost ( € ): " + tran.computeAmount() + "\n");
            sc.write("Converted Cost: " + convertEURto(free.getFreelancerCountry(),tran.computeAmount()) + "\n" );
            sum += tran.computeAmount();
            convertedSum += convertEURto(free.getFreelancerCountry(),tran.computeAmount());
        }

        sc.write("----------------------------------------------" + "\n");
        sc.write("Total ( € ): " + sum + "\n");
        sc.write("Converted Total: " + convertedSum + "\n");
        sc.write("##############################################" + "\n");
        sc.close();
    }

    /**
     * Adds a String with transaction and freelancer info to the Receipt list of Strings
     * @param tran
     * @param free
     */
    private void addTransaction(Transaction tran, Freelancer free){
        String line = String.format("%s,%s,%s,%f",tran.getTask().getId(),free.getFreelancerID(),tran.getTransactionID(),tran.computeAmount());
        Receipt.add(line);
    }

    /**
     * returns the List of Strings forming the Receipt
     * @return
     */
    public List<String> getReceipt(){
        return Receipt;
    }

    /**
     * Returns the list of transactions present in the bill
     * @return
     */
    public List<Transaction> getLstTran(){ return lstTran;}

    /**
     * Returns the list of freelancers present in the bill
     * @return
     */
    public Set<Freelancer> getFreelancerList() {
        return  lstFree;
    }

    /**
     * Returns the name of the Organization
     * @return
     */
    public String getOrgName(){return OrgName;}

    /**
     * Returns a value in a implemented currency
     * @param country
     * @param amount
     * @return
     */
    public double convertEURto(String country, double amount){
        switch(country.toLowerCase()){
            case "usa":{return amount*EURtoUSD;}
            case "uk":{return amount*EURtoGBP;}
            case"brazil":{return amount*EURtoBRL;}
            case"japan":{return amount*EURtoJPY;}
            case"china":{return amount*EURtoCNY;}
            case"canada":{return amount*EURtoCAD;}
            case"switzerland":{return amount*EURtoCHF;}
            case"australia":{return amount*EURtoAUD;}
            case"india":{return amount*EURtoINR;}
            case"hong kong":{return amount*EURtoHKD;}
            case"sweden":{return amount*EURtoSEK;}
            case"turkey":{return amount*EURtoTRY;}
            case"norway":{return amount*EURtoNOK;}
            case"mexico":{return amount*EURtoMXN;}
            case"russia":{return amount*EURtoRUB;}
            case"austria":{return amount;}
            case"belgium":{return amount;}
            case"cyprus":{return amount;}
            case"estonia":{return amount;}
            case"finland":{return amount;}
            case"france":{return amount;}
            case"germany":{return amount;}
            case"greece":{return amount;}
            case"ireland":{return amount;}
            case"italy":{return amount;}
            case"latvia":{return amount;}
            case"lithuania":{return amount;}
            case"luxembourg":{return amount;}
            case"malta":{return amount;}
            case"netherlands":{return amount;}
            case"portugal":{return amount;}
            case"slovakia":{return amount;}
            case"slovenia":{return amount;}
            case"spain":{return amount;}
            default:
                return amount;
        }
    }
}
