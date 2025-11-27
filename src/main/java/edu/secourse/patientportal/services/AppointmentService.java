package edu.secourse.patientportal.services;

import edu.secourse.patientportal.models.Appointment;
import edu.secourse.patientportal.models.User;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service class that manages lifecycle of appointments.
 * Handles creation, cancellation, modification, and lookup.
 */
public class AppointmentService {

    private final UserService userService;
    private final Map<Integer, Appointment> appointmentsById = new HashMap<>();
    private int nextAppointmentId = 1;

    /**
     * Creates a new AppointmentService tied to a UserService.
     *
     * @param userService user service used to resolve patient and doctor accounts
     */
    public AppointmentService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new appointment between a patient and a doctor.
     *
     * @param patientAccountNumber patient account number
     * @param doctorAccountNumber  doctor account number
     * @param startDateTime        appointment start date and time
     * @return created Appointment
     * @throws IllegalArgumentException if date/time is null
     * @throws NoSuchElementException   if patient or doctor does not exist
     */
    public Appointment createAppointment(int patientAccountNumber,
                                         int doctorAccountNumber,
                                         LocalDateTime startDateTime) {
        if (startDateTime == null) {
            throw new IllegalArgumentException("Start date/time is required");
        }

        User patient = userService.getUserById(patientAccountNumber);
        User doctor = userService.getUserById(doctorAccountNumber);

        if (patient == null) {
            throw new NoSuchElementException("Patient not found: " + patientAccountNumber);
        }
        if (doctor == null) {
            throw new NoSuchElementException("Doctor not found: " + doctorAccountNumber);
        }

        int id = nextAppointmentId++;
        Appointment appointment = new Appointment(id, patient, doctor, startDateTime, "active");
        appointmentsById.put(id, appointment);
        return appointment;
    }

    /**
     * Cancels an existing appointment by ID.
     *
     * @param appointmentId appointment identifier
     * @return true if the appointment was found and canceled; false otherwise
     */
    public boolean cancelAppointment(int appointmentId) {
        Appointment appointment = appointmentsById.get(appointmentId);
        if (appointment == null) {
            return false;
        }
        appointment.setStatus("canceled");
        return true;
    }

    /**
     * Changes the start date/time of an existing appointment.
     *
     * @param appointmentId   appointment identifier
     * @param newStartDateTime new date/time value
     * @return updated Appointment
     * @throws NoSuchElementException   if appointment does not exist
     * @throws IllegalArgumentException if newStartDateTime is null
     */
    public Appointment modifyAppointmentTime(int appointmentId,
                                             LocalDateTime newStartDateTime) {
        Appointment appointment = appointmentsById.get(appointmentId);
        if (appointment == null) {
            throw new NoSuchElementException("Appointment not found: " + appointmentId);
        }
        if (newStartDateTime == null) {
            throw new IllegalArgumentException("New date/time is required");
        }
        appointment.setStartDateTime(newStartDateTime);
        return appointment;
    }

    /**
     * Returns all appointments where the user is either the patient or the doctor.
     *
     * @param accountNumber user account number
     * @return list of matching appointments
     */
    public List<Appointment> getAppointmentsForUser(int accountNumber) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appt : appointmentsById.values()) {
            if (appt.getPatient().getAccountNumber() == accountNumber
                    || appt.getDoctor().getAccountNumber() == accountNumber) {
                result.add(appt);
            }
        }
        return result;
    }

    /**
     * Looks up an appointment by ID.
     *
     * @param appointmentId appointment identifier
     * @return Appointment, or null if not found
     */
    public Appointment getAppointmentById(int appointmentId) {
        return appointmentsById.get(appointmentId);
    }

    /**
     * @return a list of all appointments currently stored
     */
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointmentsById.values());
    }

    /**
     * Helper method used only in unit tests to reset internal state.
     */
    void resetForTests() {
        appointmentsById.clear();
        nextAppointmentId = 1;
    }
}
