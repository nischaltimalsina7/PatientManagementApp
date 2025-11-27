package edu.secourse.patientportal.services;

import edu.secourse.patientportal.models.Admin;
import edu.secourse.patientportal.models.Doctor;
import edu.secourse.patientportal.models.Patient;
import edu.secourse.patientportal.models.User;

import java.util.*;

/**
 * Service class that manages lifecycle of users in the system.
 * Responsible for creating, updating, removing, and fetching users.
 */
public class UserService {

    private final Map<Integer, User> usersById = new HashMap<>();
    private final Map<String, User> usersByUsername = new HashMap<>();
    private int nextAccountNumber = 1;

    /**
     * Creates and stores a new user with the given data.
     * The concrete subtype (Patient, Doctor, Admin) depends on the role.
     *
     * @param username      desired login username (must be unique)
     * @param plainPassword plain text password to hash
     * @param name          full name
     * @param email         email address
     * @param role          role string (patient, doctor, admin, etc.)
     * @return the created User instance
     * @throws IllegalArgumentException if username is blank, password is blank,
     *                                  or username already exists
     */
    public User createUser(String username,
                           String plainPassword,
                           String name,
                           String email,
                           String role) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (usersByUsername.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        int accountNumber = nextAccountNumber++;
        String passwordHash = hashPassword(plainPassword);

        User user;
        String normalizedRole = role == null ? "" : role.toLowerCase();

        switch (normalizedRole) {
            case "patient" -> user = new Patient(accountNumber, username, passwordHash, name, email);
            case "doctor" -> user = new Doctor(accountNumber, username, passwordHash, name, email);
            case "admin", "clerk", "clerk/admin" ->
                    user = new Admin(accountNumber, username, passwordHash, name, email);
            default -> user = new User(accountNumber, username, passwordHash, name, email, role);
        }

        usersById.put(accountNumber, user);
        usersByUsername.put(username, user);
        return user;
    }

    /**
     * Simple hash function for passwords.
     * In a real system, this would use a proper password hashing algorithm.
     *
     * @param plainPassword raw password
     * @return hashed representation
     */
    private String hashPassword(String plainPassword) {
        return Integer.toHexString(plainPassword.hashCode());
    }

    /**
     * Updates basic user information (name, email, role).
     * Empty or null values are ignored.
     *
     * @param accountNumber target user account number
     * @param newName       new name (or null/blank to keep)
     * @param newEmail      new email (or null/blank to keep)
     * @param newRole       new role (or null/blank to keep)
     * @return the updated User
     * @throws NoSuchElementException if the user does not exist
     */
    public User updateUser(int accountNumber,
                           String newName,
                           String newEmail,
                           String newRole) {
        User user = usersById.get(accountNumber);
        if (user == null) {
            throw new NoSuchElementException("User not found: " + accountNumber);
        }

        if (newName != null && !newName.isBlank()) {
            user.setName(newName);
        }
        if (newEmail != null && !newEmail.isBlank()) {
            user.setEmail(newEmail);
        }
        if (newRole != null && !newRole.isBlank()) {
            user.setRole(newRole);
        }
        return user;
    }

    /**
     * Removes a user with the given account number from the system.
     *
     * @param accountNumber account number of user to remove
     * @return true if a user was removed; false if no such user existed
     */
    public boolean removeUser(int accountNumber) {
        User removed = usersById.remove(accountNumber);
        if (removed != null) {
            usersByUsername.remove(removed.getUsername());
            return true;
        }
        return false;
    }

    /**
     * Returns a user by account number.
     *
     * @param accountNumber account number
     * @return matching User, or null if not found
     */
    public User getUserById(int accountNumber) {
        return usersById.get(accountNumber);
    }

    /**
     * Returns a user by username.
     *
     * @param username login username
     * @return matching User, or null if not found
     */
    public User getUserByUsername(String username) {
        return usersByUsername.get(username);
    }

    /**
     * @return a list of all users currently stored
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(usersById.values());
    }

    /**
     * @return the number of users currently stored
     */
    public int getUserCount() {
        return usersById.size();
    }

    /**
     * Helper method used only in unit tests to reset internal state.
     */
    void resetForTests() {
        usersById.clear();
        usersByUsername.clear();
        nextAccountNumber = 1;
    }
}
