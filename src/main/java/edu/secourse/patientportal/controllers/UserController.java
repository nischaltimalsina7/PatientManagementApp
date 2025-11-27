package edu.secourse.patientportal.controllers;

import edu.secourse.patientportal.models.User;
import edu.secourse.patientportal.services.UserService;

import java.util.List;
import java.util.Scanner;


public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void createUser(Scanner scanner) {
        System.out.println("\n--- Create User ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password (plain text for now): ");
        String password = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Role (patient/doctor/admin): ");
        String role = scanner.nextLine();

        try {
            User user = userService.createUser(username, password, name, email, role);
            System.out.println("User created with account number: " + user.getAccountNumber());
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void updateUser(Scanner scanner) {
        System.out.println("\n--- Update User ---");
        System.out.print("Account number: ");
        int accountNumber = readInt(scanner);

        System.out.print("New name (leave blank to keep): ");
        String newName = scanner.nextLine();

        System.out.print("New email (leave blank to keep): ");
        String newEmail = scanner.nextLine();

        System.out.print("New role (leave blank to keep): ");
        String newRole = scanner.nextLine();

        try {
            User user = userService.updateUser(
                    accountNumber,
                    emptyToNull(newName),
                    emptyToNull(newEmail),
                    emptyToNull(newRole));
            System.out.println("Updated user: " + user.getName() + " (" + user.getRole() + ")");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void removeUser(Scanner scanner) {
        System.out.println("\n--- Remove User ---");
        System.out.print("Account number: ");
        int accountNumber = readInt(scanner);

        boolean removed = userService.removeUser(accountNumber);
        if (removed) {
            System.out.println("User removed.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void listUsers() {
        System.out.println("\n--- All Users ---");
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        for (User user : users) {
            System.out.printf("ID=%d, username=%s, name=%s, role=%s, email=%s%n",
                    user.getAccountNumber(),
                    user.getUsername(),
                    user.getName(),
                    user.getRole(),
                    user.getEmail());
        }
    }

    private int readInt(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException ex) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }

    private String emptyToNull(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }
}
