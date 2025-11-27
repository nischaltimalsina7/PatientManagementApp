package edu.secourse.patientportal.models;

import java.time.LocalDateTime;

/**
 * Appointment between a patient and a doctor.
 */
public class Appointment {
    private final int appointmentId;
    private final User patient;
    private final User doctor;
    private LocalDateTime startDateTime;
    private String status; // "active" or "canceled"

    public Appointment(int appointmentId,
                       User patient,
                       User doctor,
                       LocalDateTime startDateTime,
                       String status) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.startDateTime = startDateTime;
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public User getPatient() {
        return patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
