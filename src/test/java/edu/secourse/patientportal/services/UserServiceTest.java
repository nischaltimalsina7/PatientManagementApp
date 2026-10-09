
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
                "alex.patient",
                "testPassword123",
                "Alex Morgan",
                "alex@example.com",
                "patient");

        assertNotNull(user);
        assertEquals(1, user.getAccountNumber());
        assertEquals("alex.patient", user.getUsername());
        assertEquals("Alex Morgan", user.getName());
        assertEquals("patient", user.getRole());
        assertEquals("alex@example.com", user.getEmail());
        assertEquals(1, userService.getUserCount());
    }

    @Test
    void testCreateUserWithDuplicateUsernameThrowsException() {
        userService.createUser(
                "sam.patient",
                "passOne!",
                "Sam Taylor",
                "sam@example.com",
                "patient");

        assertThrows(IllegalArgumentException.class, () ->
                userService.createUser(
                        "sam.patient",
                        "passTwo!",
                        "Sam Taylor",
                        "sam2@example.com",
                        "patient"));
    }

    @Test
    void testUpdateUserChangesFields() {
        User user = userService.createUser(
                "jordan.patient",
                "mySecret",
                "Jordan Lee",
                "jordan@example.com",
                "patient");

        User updated = userService.updateUser(
                user.getAccountNumber(),
                "Jordan Lee Updated",
                "jordan.updated@example.com",
                "doctor");

        assertEquals("Jordan Lee Updated", updated.getName());
        assertEquals("jordan.updated@example.com", updated.getEmail());
        assertEquals("doctor", updated.getRole());
    }

    @Test
    void testUpdateNonExistingUserThrows() {
        assertThrows(NoSuchElementException.class, () ->
                userService.updateUser(
                        9999,
                        "Missing User",
                        "missing@example.com",
                        "patient"));
    }

    @Test
    void testRemoveUserRemovesFromService() {
        User user = userService.createUser(
                "casey.patient",
                "simplePass",
                "Casey Brown",
                "casey@example.com",
                "patient");

        boolean removed = userService.removeUser(user.getAccountNumber());

        assertTrue(removed);
        assertEquals(0, userService.getUserCount());
        assertNull(userService.getUserById(user.getAccountNumber()));
        assertNull(userService.getUserByUsername("casey.patient"));
    }

    @Test
    void testGetUserByUsernameReturnsCorrectUser() {
        User user = userService.createUser(
                "taylor.doctor",
                "doctorPass1",
                "Taylor Smith",
                "taylor@example.com",
                "doctor");

        User found = userService.getUserByUsername("taylor.doctor");

        assertNotNull(found);
        assertEquals(user.getAccountNumber(), found.getAccountNumber());
        assertEquals("Taylor Smith", found.getName());
        assertEquals("doctor", found.getRole());
    }

    @Test
    void testRemoveNonExistingUserReturnsFalse() {
        boolean removed = userService.removeUser(9999);

        assertFalse(removed);
        assertEquals(0, userService.getUserCount());
    }
}
