package edu.secourse.patientportal.services;

import edu.secourse.patientportal.models.Admin;
import edu.secourse.patientportal.models.Doctor;
import edu.secourse.patientportal.models.Patient;
import edu.secourse.patientportal.models.User;

import java.util.*;

/**
 * Business logic for managing users.
 */
public class UserService {

    private final Map<Integer, User> usersById = new HashMap<>();
    private final Map<String, User> usersByUsername = new HashMap<>();
    private int nextAccountNumber = 1;

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
            case "patient":
                user = new Patient(accountNumber, username, passwordHash, name, email);
                break;
            case "doctor":
                user = new Doctor(accountNumber, username, passwordHash, name, email);
                break;
            case "admin":
            case "clerk":
            case "clerk/admin":
                user = new Admin(accountNumber, username, passwordHash, name, email);
                break;
            default:
                user = new User(accountNumber, username, passwordHash, name, email, role);
        }

        usersById.put(accountNumber, user);
        usersByUsername.put(username, user);
        return user;
    }

    private String hashPassword(String plainPassword) {
        return Integer.toHexString(plainPassword.hashCode());
    }

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

    public boolean removeUser(int accountNumber) {
        User removed = usersById.remove(accountNumber);
        if (removed != null) {
            usersByUsername.remove(removed.getUsername());
            return true;
        }
        return false;
    }

    public User getUserById(int accountNumber) {
        return usersById.get(accountNumber);
    }

    public User getUserByUsername(String username) {
        return usersByUsername.get(username);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersById.values());
    }

    public int getUserCount() {
        return usersById.size();
    }

    // for unit tests
    void resetForTests() {
        usersById.clear();
        usersByUsername.clear();
        nextAccountNumber = 1;
    }
}
