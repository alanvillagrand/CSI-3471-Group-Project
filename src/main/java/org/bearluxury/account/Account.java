package org.bearluxury.account;

/**
 * Each account object models the account
 * information for a guest,clerk and admin account
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class Account {
    private String firstName;
    private int id;
    private String lastName;
    private String userName;
    private String email;
    private long phoneNumber;
    private String password;
    private Role role;


    /**
     * constructs an account with the following fields
     * @param firstName
     * @param lastName
     * @param userName
     * @param email
     * @param phoneNumber
     * @param password
     * @param role
     */
    public Account(String firstName,
                   String lastName,
                   String userName,
                   String email,
                   long phoneNumber,
                   String password,
                   Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    /**
     * Creates an empty account
     */
    public Account() {

    }

    /**
     * gets the accounts first name
     * @return the first name of the account
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the accounts first name
     * @param firstName the accounts updated first name
     */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the accounts last name
     * @return the accounts last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the account last name
     * @param lastName the updated account last name
     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets the userName
     * @return the accounts userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets the userName
     * @param userName the updated userName
     */

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * gets the account email
     * @return the account email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the account email
     * @param email the email to update
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets the phone number
     * @return the account phone number
     */

    public long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets the phone number
     * @param phoneNumber the phone number to update
     */

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * gets the account password
     * @return the accounts password
     */

    public String getPassword() {
        return password;
    }

    /**
     * sets the password
     * @param password the password to update with
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * sets the account role
     * @param role the role to update with
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * gets the account role
     * @return the account role
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * prints the account information to the
     * console
     * @return the account information
     */
    @Override
    public String toString() {
        return firstName +
                "," + lastName +
                "," + userName +
                "," + email +
                "," + phoneNumber +
                "," + password +
                '\n';
    }

    /**
     * gets the account id
     * @return the accounts id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the accounts id
     * @param id the id to update with
     */
    public void setId(int id) {
        this.id = id;
    }
}

