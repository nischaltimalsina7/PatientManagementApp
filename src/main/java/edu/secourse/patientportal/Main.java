package edu.secourse.patientportal;

import edu.secourse.patientportal.controllers.AppointmentController;
import edu.secourse.patientportal.controllers.UserController;
import edu.secourse.patientportal.services.AppointmentService;
import edu.secourse.patientportal.services.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        AppointmentService appointmentService = new AppointmentService(userService);

        UserController userController = new UserController(userService);
        AppointmentController appointmentController = new AppointmentController(appointmentService);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Patient Management App ---");
            System.out.println("1. Create User");
            System.out.println("2. Update User");
            System.out.println("3. Remove User");
            System.out.println("4. List Appointments for User");
            System.out.println("5. Create Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. List All Users");
            System.out.println("8. Modify Appointment Time");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choiceLine = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceLine.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> userController.createUser(scanner);
                case 2 -> userController.updateUser(scanner);
                case 3 -> userController.removeUser(scanner);
                case 4 -> appointmentController.listAppointmentsForUser(scanner);
                case 5 -> appointmentController.createAppointment(scanner);
                case 6 -> appointmentController.cancelAppointment(scanner);
                case 7 -> userController.listUsers();
                case 8 -> appointmentController.modifyAppointmentTime(scanner);
                case 0 -> {
                    running = false;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Unknown option.");
            }
        }

        scanner.close();
    }
}
