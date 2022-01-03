package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegistryFreelancers implements Serializable {

    private static final long serialVersionUID = 5243335817527246362L;
    /**
     * freelancer´s list
     */
    private List<Freelancer> m_lstFreelancers;

    public RegistryFreelancers() {
        if(binFileExists ()) deserializeFreelancers ();
        else m_lstFreelancers = new ArrayList<>();
    }

    /**
     *
     * @param freelancerID freelancer´s id
     * @param freelancerName freelancer´s name
     * @param freelancerExpertise freelancer´s level of expertise(junior or senior)
     * @param freelancerEmail freelancer´s email
     * @param freelancerNIF freelancer´s NIF
     * @param freelancerIBAN freelancer´s IBAN
     * @param freelancerAddress freelancer´s address
     * @param freelancerCountry freelancer´s country
     * @return returns new freelancer
     */
    public Freelancer newFreelancer(String freelancerID, String freelancerName, String freelancerExpertise, String freelancerEmail, String freelancerNIF, String freelancerIBAN, String freelancerAddress, String freelancerCountry) {
        return new Freelancer(freelancerID, freelancerName, freelancerExpertise, freelancerEmail, freelancerNIF,
                freelancerIBAN, freelancerAddress, freelancerCountry);
    }

    /**
     *
     * @param id freelancer´s id
     * @return return freelancer by id
     */
    public Freelancer getFreelancerByID(String id) {
        for (Freelancer freelancer : m_lstFreelancers) {
            if (freelancer.getFreelancerID().equalsIgnoreCase(id)) {
                return freelancer;
            }
        }
        return null;
    }

    /**
     * /**
     * Method that verifies if the freelancer NIF received by parameter is assigned to
     * any existent transaction.
     *
     * @param freelancerNIF freelancer NIF
     * @return true if the freelancer exists, otherwise, returns false
     */
    public boolean existsFreelancerByNIF(String freelancerNIF) {
        for (Freelancer freelancer : m_lstFreelancers) {
            if (freelancer.getFreelancerNIF().equals(freelancerNIF)) {
                return true;
            }
        }
        return false;
    }

    public List<Freelancer> getM_lstFreelancers() {
        return m_lstFreelancers;
    }

    /**
     *
     * @param freelancer freelancer
     * @return adds new freelancer to the list of freelancers
     */
    public boolean addFreelancer(Freelancer freelancer) {
        return m_lstFreelancers.add(freelancer);
    }

    /**
     *
     * @param freelancer checking if freelancer exists or not
     * @return returns if freelancer exists
     */
    public boolean validateFreelancer(Freelancer freelancer) {
        //return freelancer.equals(m_lstFreelancers);
        for(Freelancer f: m_lstFreelancers){
            if(freelancer.equals ( f )){
                return false;
            }
        }
        return true;
    }

    //UC2
    /**
     *
     * @param freelancer new freelancer
     * @return registration of a freelancer
     */
    public boolean registerFreelancer(Freelancer freelancer) {
        if (validateFreelancer(freelancer) == true) {

            return addFreelancer(freelancer);
        }
        return false;

    }

    /**
     *
     * @param freelancerEmail freelancer´s email
     * @return returns freelancer by email
     */
    public Freelancer getFreelancerByEmail(String freelancerEmail) {
        for (Freelancer freelancer : this.m_lstFreelancers) {
            if (freelancer.hasEmail(freelancerEmail)) {
                return freelancer;
            }
        }

        return null;
    }

    /**
     *
     * @param FreelancerIBAN freelancer´s IBAN
     * @return returns freelancer by IBAN
     */
    public Freelancer getFreelancerByIBAN(String FreelancerIBAN) {
        for (Freelancer freelancer : this.m_lstFreelancers) {
            if (freelancer.hasIBAN(FreelancerIBAN)) {
                return freelancer;
            }
        }
        return null;
    }

    /**
     * 
     * @return return list of freelancers ID
     */
    public List<String> getFreelancersID(){
        List<String> lstFreelancersID =  new ArrayList<>();
        for (Freelancer f : m_lstFreelancers){
            lstFreelancersID.add(f.getFreelancerID());
        }
        return lstFreelancersID;
    }

    public void serializeFreelancers(){
        try {
            ObjectOutputStream out=new ObjectOutputStream ( new FileOutputStream ( Constants.BINARY_FREELANCERS ) );
            out.writeObject ( m_lstFreelancers );
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public static Freelancer newFreelancer(String freelancerID,String[] s){
        String freelancerName=s[10];
        String freelancerExpertise=s[11];
        String freelancerEmail=s[12];
        String freelancerNIF=s[13];
        String freelancerBankAccount=s[14];
        String freelancerAddress=s[15];
        String freelancerCountry=s[16];
        return new Freelancer ( freelancerID, freelancerName, freelancerExpertise,
                freelancerEmail, freelancerNIF, freelancerBankAccount, freelancerAddress, freelancerCountry);
    }

    private boolean binFileExists(){
        File f=new File ( Constants.BINARY_FREELANCERS );
        return f.exists () && !f.isDirectory ();
    }

    private void deserializeFreelancers(){
        try {
            ObjectInputStream in=new ObjectInputStream ( new FileInputStream ( Constants.BINARY_FREELANCERS ) );
            m_lstFreelancers=(List<Freelancer>)in.readObject ();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace ();
        }
    }
    /**
     * 
     * @param freelancerID freelancer's ID
     * @return returns generated ID
     */
    public String generateID(String freelancerID){
        String [] parts = freelancerID.split(" ");
        String firstName = parts[0];
        String lastName = parts[1];

        try {
            int nID = 1;
            String id = ("" + firstName.charAt(0) + lastName.charAt(0) + nID).toUpperCase();
            while (getFreelancerByID(id) != null) {
                nID++;
                id = ("" + firstName.charAt(0) + lastName.charAt(0) + nID).toUpperCase();
            }
            return id.toUpperCase();
        } catch (Exception e) {
            return null;
        }

    }
}
