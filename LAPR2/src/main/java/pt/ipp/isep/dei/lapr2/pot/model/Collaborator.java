/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.isep.dei.lapr2.pot.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the collaborator of an organization.
 *
 * @author paulomaio
 * @author Rafael Moreira - 1181055
 */
public class Collaborator implements Serializable {
    /**
     * Name of the collaborator.
     */
    private String name;
    /**
     * E-mail address of the collaborator.
     */
    private String email;
    /**
     * Serialization ID for the class.
     */
    private static final long serialVersionUID = -5712509167420107382L;

    /**
     * Constructor.
     * @param strName Name of the collaborator.
     * @param strEmail E-mail address of the collaborator.
     * @throws IllegalArgumentException If any of the parameters are invalid.
     */
    public Collaborator(String strName, String strEmail)
    {
        if (strName == null || strName.isEmpty())
            throw new IllegalArgumentException("Name cannot be null or empty.");
        if(strEmail == null || strEmail.isEmpty() || ! validateEmail ( strEmail ))
            throw new IllegalArgumentException("Invalid email address.");
        
        this.name = strName;
        this.email = strEmail;
    }

    /**
     * Verifies if the e-mail address has a valid format. Resorts to regular expressions.
     * Method inspired by https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
     * @param email E-mail to validate.
     * @return <code>true</code> if the email has a valid format. <code>false</code> otherwise.
     */
    private boolean validateEmail(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Returns the name of the collaborator.
     * @return Name of the collaborator.
     */
    public String getName() { return this.name; }

    /**
     * Returns the e-mail of the collaborator.
     * @return E-mail of the collaborator.
     */
    public String getEmail() { return this.email; }

    /**
     * Returns the hash code of the instance.
     * @return Hash code.
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.email );
        return hash;
    }

    /**
     * Compares two instances. Uses the e-mail as a comparative measure.
     * @param o Object to compare to.
     * @return <code>true</code> if the collaborator is equal to the Object passed as a parameter. <code>false</code>otherwise.
     */
    @Override
    public boolean equals(Object o) {
        // Inspired in https://www.sitepoint.com/implement-javas-equals-method-correctly/
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
        Collaborator obj = (Collaborator) o;
        return (Objects.equals( email, obj.email ));
    }

    /**
     * Returns the textual representation of the instance.
     * @return Information regarding the instance in String format.
     */
    @Override
    public String toString() { return String.format("%s - %s", this.name, this.email ); }
}
