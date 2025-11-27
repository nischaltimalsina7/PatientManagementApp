package edu.secourse.patientportal.models;

/**
 * Represents a doctor user in the system.
 */
public class Doctor extends User {

    /**
     * Creates a new doctor.
     *
     * @param accountNumber unique numeric identifier
     * @param username      login username
     * @param passwordHash  hashed password
     * @param name          doctor's full name
     * @param email         doctor's email address
     */
    public Doctor(int accountNumber,
                  String username,
                  String passwordHash,
                  String name,
                  String email) {
        super(accountNumber, username, passwordHash, name, email, "doctor");
    }
}
