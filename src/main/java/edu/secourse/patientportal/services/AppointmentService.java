package edu.secourse.patientportal.services;

import edu.secourse.patientportal.models.Appointment;
import edu.secourse.patientportal.models.User;

import java.time.LocalDateTime;
import java.util.*;


public class AppointmentService {

    private final UserService userService;
    private final Map<Integer, Appointment> appointmentsById = new HashMap<>();
    private int nextAppointmentId = 1;

    public AppointmentService(UserService userService) {
        this.userService = userService;
    }

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

    public boolean cancelAppointment(int appointmentId) {
        Appointment appointment = appointmentsById.get(appointmentId);
        if (appointment == null) {
            return false;
        }
        appointment.setStatus("canceled");
        return true;
    }

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

    public Appointment getAppointmentById(int appointmentId) {
        return appointmentsById.get(appointmentId);
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointmentsById.values());
    }

    // for unit tests
    void resetForTests() {
        appointmentsById.clear();
        nextAppointmentId = 1;
    }
}
