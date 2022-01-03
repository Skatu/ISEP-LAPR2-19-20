package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.Serializable;

public class Freelancer implements Serializable {

    private static final long serialVersionUID = -4405177816528040098L;

    /**
     * ID of the freelancer
     */
    private String freelancerID;

    /**
     * Name of the Freelancer
     */
    private String freelancerName;

    /**
     * level of expertise: junior or senior
     */   
    private EnumFreelancerExpertise freelancerExpertise;

    /**
     * Email of the Freelancer
     */
    private String freelancerEmail;

    /**
     * NIF of the Freelancer
     */
    private String freelancerNIF;

    /**
     * IBAN of the Freelancer
     */
    private String freelancerIBAN;

    /**
     * Address of the Freelancer
     */
    private String freelancerAddress;

    /**
     * Country of the Freelancer
     */
    private String freelancerCountry;

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
     */
    public Freelancer(String freelancerID, String freelancerName, String freelancerExpertise, String freelancerEmail,
                      String freelancerNIF, String freelancerIBAN, String freelancerAddress, String freelancerCountry) {
        if (freelancerID == null || freelancerID.isEmpty() || freelancerName == null || freelancerName.isEmpty()
                || freelancerExpertise == null || freelancerExpertise.isEmpty() || freelancerEmail == null || freelancerEmail.isEmpty()
                || freelancerNIF == null || freelancerNIF.isEmpty() || freelancerIBAN == null || freelancerIBAN.isEmpty()
                || freelancerAddress == null || freelancerAddress.isEmpty() || freelancerCountry == null ||freelancerCountry.isEmpty()){
            throw new IllegalArgumentException ("Freelancer cannot be empty");
        }
        this.freelancerID = freelancerID;
        this.freelancerName = freelancerName;
        this.freelancerExpertise = EnumFreelancerExpertise.returnEnumByDesignation(freelancerExpertise);
        this.freelancerEmail = freelancerEmail;
        this.freelancerNIF = freelancerNIF;
        this.freelancerIBAN = freelancerIBAN;
        this.freelancerAddress = freelancerAddress;
        this.freelancerCountry = freelancerCountry;
    }

    /**
     * Returns the Freelancer's ID
     * @return
     */
    public String getFreelancerID() {
        return freelancerID;
    }

    /**
     * Sets the Freelancer's instance with a new ID
     * @param freelancerID
     */
    public void setFreelancerID(String freelancerID) {
        this.freelancerID = freelancerID;
    }

    /**
     * Returns the Freelancer's Name
     * @return
     */
    public String getFreelancerName() {
        return freelancerName;
    }

    /**
     * Returns the Freelancer's level of expertise
     * @return
     */
    public EnumFreelancerExpertise getFreelancerExpertise() {
        return freelancerExpertise;
    }


    /**
     * Returns the Freelancer's email
     * @return
     */
    public String getFreelancerEmail() {
        return freelancerEmail;
    }

    /**
     * Returns the Freelancer's NIF
     * @return
     */
    public String getFreelancerNIF() {
        return freelancerNIF;
    }

    /**
     * Returns the Freelancer's country
     * @return
     */
    public String getFreelancerCountry() {
        return freelancerCountry;
    }

     /**
     * 
     * @param freelancerEmail freelancer´s email
     * @return returns if freelancer has email or not
     */
    public boolean hasEmail(String freelancerEmail){
        return this.freelancerEmail.equalsIgnoreCase(freelancerEmail);
    }
    /**
     * 
     * @param freelancerIBAN freelancer´s IBAN
     * @return  returns if freelancer has IBAN or not
     */
    public boolean hasIBAN(String freelancerIBAN){
        return this.freelancerIBAN.equalsIgnoreCase(freelancerIBAN);
    }

    /**
     * Returns the total payment amount of the freelancer from the original amount and his cost per hour
     * @param amount
     * @return
     */
    public double calculateTotalPaymentAmount(double amount){
        return amount*freelancerExpertise.getExtraCostPerHour();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        Freelancer that = (Freelancer) o;
        return freelancerID.equals ( that.freelancerID ) &&
                freelancerNIF.equals ( that.freelancerNIF ) &&
                freelancerIBAN.equals ( that.freelancerIBAN );
    }

    /**
     * Returns the description of the Freelancer's instance in a String format
     * @return
     */
    @Override
    public String toString() {
        return String.format("Freelancer: \nID: %s \nName: %s \nExpertise: %s \nEmail: %s \nNIF: %s \nIBAN:%s \nAdress: %s \nCountry: %s  ", freelancerID, freelancerName, freelancerExpertise, freelancerEmail, freelancerNIF, freelancerIBAN, freelancerAddress, freelancerCountry);
    }
}
