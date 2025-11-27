package edu.secourse.patientportal.models;

/**
 * Base user class in the patient portal.
 */
public class User {
    private final int accountNumber;
    private final String username;
    private String passwordHash;
    private String name;
    private String email;
    private String role; // "patient", "doctor", "clerk/admin", etc.

    public User(int accountNumber,
                String username,
                String passwordHash,
                String name,
                String email,
                String role) {
        this.accountNumber = accountNumber;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
