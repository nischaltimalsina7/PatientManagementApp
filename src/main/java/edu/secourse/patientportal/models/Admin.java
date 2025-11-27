package edu.secourse.patientportal.models;

/**
 * Admin / clerk user in the system.
 */
public class Admin extends User {

    public Admin(int accountNumber,
                 String username,
                 String passwordHash,
                 String name,
                 String email) {
        super(accountNumber, username, passwordHash, name, email, "clerk/admin");
    }
}
