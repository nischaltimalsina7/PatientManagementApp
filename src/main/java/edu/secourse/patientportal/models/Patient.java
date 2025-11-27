package edu.secourse.patientportal.models;

/**
 * Patient user in the system.
 */
public class Patient extends User {

    public Patient(int accountNumber,
                   String username,
                   String passwordHash,
                   String name,
                   String email) {
        super(accountNumber, username, passwordHash, name, email, "patient");
    }
}
