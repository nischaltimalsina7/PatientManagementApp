package edu.secourse.patientportal.models;

/**
 * Represents an admin or clerk user in the system.
 */
public class Admin extends User {

    /**
     * Creates a new admin/clerk user.
     *
     * @param accountNumber unique numeric identifier
     * @param username      login username
     * @param passwordHash  hashed password
     * @param name          admin's full name
     * @param email         admin's email address
     */
    public Admin(int accountNumber,
                 String username,
                 String passwordHash,
                 String name,
                 String email) {
        super(accountNumber, username, passwordHash, name, email, "clerk/admin");
    }
}
