package edu.secourse.patientportal.models;

/**
 * Represents a patient user in the system.
 */
public class Patient extends User {

    /**
     * Creates a new patient.
     *
     * @param accountNumber unique numeric identifier
     * @param username      login username
     * @param passwordHash  hashed password
     * @param name          patient's full name
     * @param email         patient's email address
     */
    public Patient(int accountNumber,
                   String username,
                   String passwordHash,
                   String name,
                   String email) {
        super(accountNumber, username, passwordHash, name, email, "patient");
    }
}
