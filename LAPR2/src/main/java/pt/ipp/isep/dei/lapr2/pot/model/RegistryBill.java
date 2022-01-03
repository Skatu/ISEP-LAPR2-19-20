/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.model;

import pt.ipp.isep.dei.lapr2.emailService.EmailToTextFile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author RodrigoRodrigues
 */

public class RegistryBill {

    /**
     * List of valid bill instances that have been created
     */
    private List<Bill> lstBill;

    /**
     * EmailToTextFile instance controller
     */
    private EmailToTextFile EmailController;

    /**
     * Method to create text files for the bill and validate it and add it to the list of valid bill instances
     *
     * @param bill
     * @throws FileNotFoundException
     */
    public void addBill(Bill bill) throws FileNotFoundException {
        if(validateBill(bill)){
            bill.createFile();
            for(Freelancer free: bill.getFreelancerList()){
                List<Transaction> lstTranFreelancer = new ArrayList<>();
                for(Transaction tran: bill.getLstTran()){
                    if(tran.getFreelancer() == free){
                        lstTranFreelancer.add(tran);
                    }
                }
                try {
                    bill.createFreelancerTransaction(free,lstTranFreelancer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EmailController.sendFreelancerPaymentWarning(free.getFreelancerEmail(),lstTranFreelancer,bill);
            }
            addBillToList(bill);
        }

    }

    /**
     * Validates bill instance globally
     *
     * @param bill
     * @return
     */
    private boolean validateBill(Bill bill) {
        for(Bill bill2: lstBill){
            if(bill2 == bill){
                return false;
            }
        }
        return true;
    }

    /**
     * Adds bill to the valid bills' list
     * @param bill
     */
    private void addBillToList(Bill bill){
        lstBill.add(bill);
    }

    /**
     * Returns the list of valid bills created
     * @return
     */
    public List<Bill> getListBill(){
        return lstBill;
    }
}

