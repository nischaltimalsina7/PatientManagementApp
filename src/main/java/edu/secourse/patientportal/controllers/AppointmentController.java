package edu.secourse.patientportal.controllers;

import edu.secourse.patientportal.models.Appointment;
import edu.secourse.patientportal.services.AppointmentService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based controller for appointment-related operations.
 * Reads input from the user and delegates to AppointmentService.
 */
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Creates a new AppointmentController.
     *
     * @param appointmentService service that performs appointment operations
     */
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Handles the "create appointment" flow using console input.
     *
     * @param scanner scanner connected to System.in
     */
    public void createAppointment(Scanner scanner) {
        System.out.println("\n--- Create Appointment ---");
        System.out.print("Patient account number: ");
        int patientId = readInt(scanner);

        System.out.print("Doctor account number: ");
        int doctorId = readInt(scanner);

        System.out.print("Start date/time (YYYY-MM-DDTHH:MM, e.g., 2025-12-01T14:30): ");
        LocalDateTime dateTime = readDateTime(scanner);

        try {
            Appointment appt = appointmentService.createAppointment(patientId, doctorId, dateTime);
            System.out.println("Appointment created with ID: " + appt.getAppointmentId());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Handles the "cancel appointment" flow using console input.
     *
     * @param scanner scanner connected to System.in
     */
    public void cancelAppointment(Scanner scanner) {
        System.out.println("\n--- Cancel Appointment ---");
        System.out.print("Appointment ID: ");
        int apptId = readInt(scanner);

        boolean success = appointmentService.cancelAppointment(apptId);
        if (success) {
            System.out.println("Appointment canceled.");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    /**
     * Lists all appointments for a given user account number.
     *
     * @param scanner scanner connected to System.in
     */
    public void listAppointmentsForUser(Scanner scanner) {
        System.out.println("\n--- List Appointments For User ---");
        System.out.print("User account number: ");
        int accountNumber = readInt(scanner);

        List<Appointment> list = appointmentService.getAppointmentsForUser(accountNumber);
        if (list.isEmpty()) {
            System.out.println("No appointments found for this user.");
            return;
        }
        for (Appointment appt : list) {
            System.out.printf("ID=%d, patient=%s, doctor=%s, time=%s, status=%s%n",
                    appt.getAppointmentId(),
                    appt.getPatient().getName(),
                    appt.getDoctor().getName(),
                    appt.getStartDateTime(),
                    appt.getStatus());
        }
    }

    /**
     * Handles the "modify appointment time" flow using console input.
     *
     * @param scanner scanner connected to System.in
     */
    public void modifyAppointmentTime(Scanner scanner) {
        System.out.println("\n--- Modify Appointment Time ---");
        System.out.print("Appointment ID: ");
        int apptId = readInt(scanner);

        System.out.print("New date/time (YYYY-MM-DDTHH:MM): ");
        LocalDateTime dateTime = readDateTime(scanner);

        try {
            Appointment appt = appointmentService.modifyAppointmentTime(apptId, dateTime);
            System.out.println("Updated appointment time: " + appt.getStartDateTime());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Reads an integer from the console, re-prompting until a valid value is entered.
     *
     * @param scanner scanner connected to System.in
     * @return parsed integer value
     */
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

    /**
     * Reads a LocalDateTime from the console using ISO-8601 format.
     *
     * @param scanner scanner connected to System.in
     * @return parsed LocalDateTime
     */
    private LocalDateTime readDateTime(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine();
            try {
                return LocalDateTime.parse(line.trim());
            } catch (DateTimeParseException ex) {
                System.out.print("Enter a valid date/time (YYYY-MM-DDTHH:MM): ");
            }
        }
    }
}
