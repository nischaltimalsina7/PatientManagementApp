package edu.secourse.patientportal.services;

import edu.secourse.patientportal.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        userService.resetForTests();
    }

    @Test
    void testCreateUserAssignsAccountNumberAndStoresUser() {
        User user = userService.createUser(
                "nischal.t",
                "password123",
                "Nischal Timalsina",
                "nischal.timalsina@gmail.com",
                "patient");

        assertNotNull(user);
        assertEquals(1, user.getAccountNumber());
        assertEquals("nischal.t", user.getUsername());
        assertEquals("Nischal Timalsina", user.getName());
        assertEquals("patient", user.getRole());
        assertEquals("nischal.timalsina@gmail.com", user.getEmail());
        assertEquals(1, userService.getUserCount());
    }

    @Test
    void testCreateUserWithDuplicateUsernameThrowsException() {
        userService.createUser(
                "apekshya.k",
                "passOne!",
                "Apekshya Koirala",
                "apekshya.koirala@gmail.com",
                "patient");

        assertThrows(IllegalArgumentException.class, () ->
                userService.createUser(
                        "apekshya.k",
                        "passTwo!",
                        "Apekshya K.",
                        "apekshya.k@example.com",
                        "patient"));
    }

    @Test
    void testUpdateUserChangesFields() {
        User user = userService.createUser(
                "santosh.g",
                "mySecret",
                "Santosh Gautam",
                "santosh.gautam@outlook.com",
                "patient");

        User updated = userService.updateUser(
                user.getAccountNumber(),
                "Santosh Gautam (Updated)",
                "santosh.gautam.updated@gmail.com",
                "doctor");

        assertEquals("Santosh Gautam (Updated)", updated.getName());
        assertEquals("santosh.gautam.updated@gmail.com", updated.getEmail());
        assertEquals("doctor", updated.getRole());
    }

    @Test
    void testUpdateNonExistingUserThrows() {
        assertThrows(NoSuchElementException.class, () ->
                userService.updateUser(9999,
                        "Random Name",
                        "random.user@gmail.com",
                        "patient"));
    }

    @Test
    void testRemoveUserRemovesFromService() {
        User user = userService.createUser(
                "sita.sh",
                "simplePass",
                "Sita Sharma",
                "sita.sharma@yahoo.com",
                "patient");

        boolean removed = userService.removeUser(user.getAccountNumber());

        assertTrue(removed);
        assertEquals(0, userService.getUserCount());
        assertNull(userService.getUserById(user.getAccountNumber()));
        assertNull(userService.getUserByUsername("sita.sh"));
    }

    @Test
    void testGetUserByUsernameReturnsCorrectUser() {
        User user = userService.createUser(
                "ram.b",
                "ramPass1",
                "Ram Basnet",
                "ram.basnet@gmail.com",
                "doctor");

        User found = userService.getUserByUsername("ram.b");

        assertNotNull(found);
        assertEquals(user.getAccountNumber(), found.getAccountNumber());
        assertEquals("Ram Basnet", found.getName());
        assertEquals("doctor", found.getRole());
    }
}
