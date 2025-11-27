package edu.secourse.patientportal.models;

import java.time.LocalDateTime;

/**
 * Represents an appointment between a patient and a doctor.
 */
public class Appointment {
    private final int appointmentId;
    private final User patient;
    private final User doctor;
    private LocalDateTime startDateTime;
    private String status; // "active" or "canceled"

    /**
     * Creates a new appointment.
     *
     * @param appointmentId  unique identifier for the appointment
     * @param patient        patient user
     * @param doctor         doctor user
     * @param startDateTime  appointment start date and time
     * @param status         initial status (e.g., active)
     */
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

    /**
     * @return the appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @return the patient for this appointment
     */
    public User getPatient() {
        return patient;
    }

    /**
     * @return the doctor for this appointment
     */
    public User getDoctor() {
        return doctor;
    }

    /**
     * @return the appointment start date and time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Updates the appointment start date and time.
     *
     * @param startDateTime new date/time
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * @return the status of the appointment
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the appointment status.
     *
     * @param status new status value
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
