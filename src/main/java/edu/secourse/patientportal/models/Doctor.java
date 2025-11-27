package edu.secourse.patientportal.models;

/**
 * Doctor user in the system.
 */
public class Doctor extends User {

    public Doctor(int accountNumber,
                  String username,
                  String passwordHash,
                  String name,
                  String email) {
        super(accountNumber, username, passwordHash, name, email, "doctor");
    }
}
