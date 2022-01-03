package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.Serializable;
import java.util.Objects;
/**
 * Represents the manager of an organization.
 * @author Rafael Moreira - 1181055
 */

public class Manager implements Serializable {

    /**
     * Name of the manager.
     */
    private String name;
    /**
     * E-mail address of the manager.
     */
    private String email;
    /**
     * Serialization ID for the class.
     */
    private static final long serialVersionUID = 4088214224591698749L;

    /**
     * Constructor.
     * @param strName Name of the manager.
     * @param strEmail E-mail address of the manager.
     * @throws IllegalArgumentException If any of the parameters are invalid.
     */

    public Manager(String strName, String strEmail) {
        if (strName == null || strName.isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty.");
        if(strEmail == null || strEmail.isEmpty() || ! validateEmail ( strEmail ))
            throw new IllegalArgumentException("Invalid email address.");

        this.name = strName;
        this.email = strEmail;
    }

    /**
     * Verifies if the e-mail address has a valid format. Resorts to regular expressions.
     * @param email E-mail to validate.
     * Method inspired by https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
     * @return <code>true</code> if the email has a valid format. <code>false</code> otherwise.
     */
    private boolean validateEmail(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Returns the name of the manager.
     * @return Name of the manager.
     */
    public String getName() { return this.name; }

    /**
     * Returns the e-mail of the manager.
     * @return E-mail of the manager.
     */
    public String getEmail() { return this.email; }

    /**
     * Returns the hash code of the instance.
     * @return Hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.email );
        return hash;
    }

    /**
     * Equals method. Uses the e-mail as a comparative measure.
     * @param o Object to compare to.
     * @return <code>true</code> if the manager is equal to the object passed as a parameter. <code>false</code>otherwise.
     */
    @Override
    public boolean equals(Object o) {
        // Inspirado em https://www.sitepoint.com/implement-javas-equals-method-correctly/
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        // field comparison
        Manager obj = (Manager) o;
        return (Objects.equals( email, obj.email ));
    }

    /**
     * Returns the textual representation of the instance.
     * @return Information regarding the instance in String format.
     */
    @Override
    public String toString() { return String.format("%s - %s", this.name, this.email ); }
}
