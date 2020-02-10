package Model;

/*
ENSF 607 - Winter 2020 - Lab 02 - Exercise 6
Kush Bhatt & Matthew Vanderwey 2019-11-15
 */

import java.io.Serializable;

/**
 * This class enables Client objects which have the following attributes:
 * Client ID (a unique 4-digit number assigned by the system)s:
 * First Name (Max 20 Characters);
 * Last Name (Max 20 Characters);
 * Address (Max 50 Characters;
 * Postal Code (A1A 1A1);
 * Phone Number (111-111-1111);
 * Client Type (C or R):
 *
 * @author Kush Bhatt & Matthew Vanderwey
 * @version 2.1 (Updated to implement Serializable and added equals() method)
 * @since 2020-02-07
 */
public class Client implements Serializable {

    /**
     * Used for serializing.
     */
    private static final long serialVersionUID = 1;

    /**
     * Used to create unique client ID numbers starting at 1001.
     */
    private static int baseID = 1000;

    private int id;
    private String firstName, lastName, address, postalCode, phoneNum, clientType;

    // Parametric constructor for new Client objects.
    public Client(int id, String firstName, String lastName, String address,
                  String postalCode, String phoneNum, String clientType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.clientType = clientType;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    // Public Methods

    /**
     * Static method for generating unique four-digit client ID numbers.
     *
     * @return Next available client ID number.
     */
    public static int generateClientID() {
        return ++baseID;
    }

    ////////////////////////////////////////////////////////////////////////////////
    // Getters & Setters & toString

    public static void resetBaseID() {
        baseID = 1000;
    }

    public static int getBaseID() {
        return baseID;
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getClientType() {
        return clientType;
    }

    @Override
    public String toString() {
        return this.id + " " + this.firstName + " " +
                this.lastName + " " + this.clientType;
    }

    public boolean equals(Client test) {
        return this.getID() == test.getID()
                && this.getFirstName().equalsIgnoreCase(test.getFirstName())
                && this.getLastName().equalsIgnoreCase(test.getLastName())
                && this.getAddress().equalsIgnoreCase(test.getAddress())
                && this.getPostalCode().equalsIgnoreCase(test.getPostalCode())
                && this.getPhoneNum().equalsIgnoreCase(test.getPhoneNum())
                && this.getClientType().equalsIgnoreCase(test.getClientType());
    }

}
