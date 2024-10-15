package tuteez.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TelegramUsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TelegramUsername(null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "john wick$";
        assertThrows(IllegalArgumentException.class, () -> new TelegramUsername(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null username
        assertThrows(NullPointerException.class, () -> TelegramUsername.isValidTelegramHandle(null));

        // invalid usernames
        assertFalse(TelegramUsername.isValidTelegramHandle("")); // empty string
        assertFalse(TelegramUsername.isValidTelegramHandle(" ")); // spaces only
        assertFalse(TelegramUsername.isValidTelegramHandle("john wick")); // contains space
        assertFalse(TelegramUsername.isValidTelegramHandle("john_wick$")); // contains special character
        assertFalse(TelegramUsername.isValidTelegramHandle("johnwick#!@()")); // contains special characters
        assertFalse(TelegramUsername.isValidTelegramHandle("john")); // less than 5 characters
        assertFalse(TelegramUsername.isValidTelegramHandle("johnwick".repeat(5))); // more than 32 characters
        assertFalse(TelegramUsername.isValidTelegramHandle("_johnwick")); // does not start with letter
        assertFalse(TelegramUsername.isValidTelegramHandle("3johnwick")); // does not start with letter
        assertFalse(TelegramUsername.isValidTelegramHandle("$$$$$$")); // contains special characters only

        // valid usernames
        assertTrue(TelegramUsername.isValidTelegramHandle("johnwick"));
        assertTrue(TelegramUsername.isValidTelegramHandle("john_wick"));
        assertTrue(TelegramUsername.isValidTelegramHandle("johnwick123"));
        assertTrue(TelegramUsername.isValidTelegramHandle("johnwick_123"));
        assertTrue(TelegramUsername.isValidTelegramHandle("a".repeat(5))); // exactly 5 characters
        assertTrue(TelegramUsername.isValidTelegramHandle("a".repeat(32))); // exactly 32 characters
        assertTrue(TelegramUsername.isValidTelegramHandle("CaptainAmerica")); // capitalisation allowed
    }

    @Test
    public void equals() {
        TelegramUsername user1 = new TelegramUsername("johnwick");
        TelegramUsername user2 = new TelegramUsername("johnwick");
        TelegramUsername user3 = new TelegramUsername("john_wick");
        TelegramUsername user4 = new TelegramUsername("JohnWick");

        // same object -> returns true
        assertTrue(user1.equals(user1));

        // same values -> returns true
        assertTrue(user1.equals(user2));

        // different capitalisation -> returns true
        assertTrue(user1.equals(user4));

        // null -> returns false
        assertFalse(user1.equals(null));

        // different values -> returns false
        assertFalse(user1.equals(user3));

        // different types (other is String) -> returns false
        assertFalse(user1.equals("johnwick"));
    }

    @Test
    public void toString_validUsername_returnsUsername() {
        String username = "johnwick";
        TelegramUsername user = new TelegramUsername(username);
        assertTrue(user.toString().equals(username));
    }

    @Test
    public void hashCode_sameUsername_sameHashCode() {
        String username = "johnwick";
        TelegramUsername user1 = new TelegramUsername(username);
        TelegramUsername user2 = new TelegramUsername(username);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void hashCode_differentUsername_differentHashCode() {
        TelegramUsername user1 = new TelegramUsername("johnwick");
        TelegramUsername user2 = new TelegramUsername("john_wick");
        assertFalse(user1.hashCode() == user2.hashCode());
    }
}
