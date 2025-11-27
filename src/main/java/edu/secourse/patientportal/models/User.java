package edu.secourse.patientportal.models;

/**
 * Represents a generic user in the patient portal system.
 * A user may be a patient, doctor, or admin.
 */
public class User {
    private final int accountNumber;
    private final String username;
    private String passwordHash;
    private String name;
    private String email;
    private String role; // "patient", "doctor", "clerk/admin", etc.

    /**
     * Creates a new user.
     *
     * @param accountNumber unique numeric identifier for the user
     * @param username      login username
     * @param passwordHash  hashed password value
     * @param name          full name
     * @param email         email address
     * @param role          role type (e.g., patient, doctor, clerk/admin)
     */
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

    /**
     * @return the user's account number
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the hashed password value
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Updates the stored password hash.
     *
     * @param passwordHash new password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * @return the user's full name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the user's full name.
     *
     * @param name new name value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the user's email address.
     *
     * @param email new email value
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the user's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Updates the user's role.
     *
     * @param role new role value
     */
    public void setRole(String role) {
        this.role = role;
    }
}
