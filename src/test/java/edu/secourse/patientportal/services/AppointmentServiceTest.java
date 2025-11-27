package edu.secourse.patientportal.services;

import edu.secourse.patientportal.models.Appointment;
import edu.secourse.patientportal.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


class AppointmentServiceTest {

    private UserService userService;
    private AppointmentService appointmentService;
    private User patient;
    private User doctor;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        userService.resetForTests();

        appointmentService = new AppointmentService(userService);
        appointmentService.resetForTests();

        patient = userService.createUser(
                "nischal.t",
                "patientPass",
                "Nischal Timalsina",
                "nischal.timalsina@gmail.com",
                "patient");

        doctor = userService.createUser(
                "dr.aayush",
                "doctorPass",
                "Dr. Aayush Karki",
                "aayush.karki@bpkihs.edu.np",
                "doctor");
    }

    @Test
    void testCreateAppointmentAssignsIdAndStores() {
        LocalDateTime time = LocalDateTime.of(2025, 12, 1, 10, 0);

        Appointment appt = appointmentService.createAppointment(
                patient.getAccountNumber(),
                doctor.getAccountNumber(),
                time);

        assertNotNull(appt);
        assertEquals(1, appt.getAppointmentId());
        assertEquals(patient.getAccountNumber(), appt.getPatient().getAccountNumber());
        assertEquals(doctor.getAccountNumber(), appt.getDoctor().getAccountNumber());
        assertEquals("active", appt.getStatus());
        assertEquals(time, appt.getStartDateTime());
    }

    @Test
    void testCreateAppointmentWithMissingPatientThrows() {
        LocalDateTime time = LocalDateTime.of(2025, 12, 1, 10, 0);

        assertThrows(NoSuchElementException.class, () ->
                appointmentService.createAppointment(
                        9999,
                        doctor.getAccountNumber(),
                        time));
    }

    @Test
    void testCreateAppointmentWithMissingDoctorThrows() {
        LocalDateTime time = LocalDateTime.of(2025, 12, 1, 11, 30);

        assertThrows(NoSuchElementException.class, () ->
                appointmentService.createAppointment(
                        patient.getAccountNumber(),
                        8888,
                        time));
    }

    @Test
    void testCancelAppointmentChangesStatus() {
        LocalDateTime time = LocalDateTime.of(2025, 12, 3, 9, 0);

        Appointment appt = appointmentService.createAppointment(
                patient.getAccountNumber(),
                doctor.getAccountNumber(),
                time);

        boolean canceled = appointmentService.cancelAppointment(appt.getAppointmentId());

        assertTrue(canceled);
        Appointment stored = appointmentService.getAppointmentById(appt.getAppointmentId());
        assertNotNull(stored);
        assertEquals("canceled", stored.getStatus());
    }

    @Test
    void testModifyAppointmentTime() {
        LocalDateTime original = LocalDateTime.of(2025, 12, 5, 14, 0);
        Appointment appt = appointmentService.createAppointment(
                patient.getAccountNumber(),
                doctor.getAccountNumber(),
                original);

        LocalDateTime newTime = LocalDateTime.of(2025, 12, 6, 16, 15);

        Appointment updated = appointmentService.modifyAppointmentTime(
                appt.getAppointmentId(),
                newTime);

        assertEquals(newTime, updated.getStartDateTime());
    }

    @Test
    void testGetAppointmentsForUserReturnsCorrectList() {
        LocalDateTime t1 = LocalDateTime.of(2025, 12, 10, 10, 0);
        LocalDateTime t2 = LocalDateTime.of(2025, 12, 11, 15, 0);

        appointmentService.createAppointment(
                patient.getAccountNumber(),
                doctor.getAccountNumber(),
                t1);

        appointmentService.createAppointment(
                patient.getAccountNumber(),
                doctor.getAccountNumber(),
                t2);

        List<Appointment> patientAppts =
                appointmentService.getAppointmentsForUser(patient.getAccountNumber());
        List<Appointment> doctorAppts =
                appointmentService.getAppointmentsForUser(doctor.getAccountNumber());

        assertEquals(2, patientAppts.size());
        assertEquals(2, doctorAppts.size());
    }
}
