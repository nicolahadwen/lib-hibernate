package co.hadwen.hibernate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EncryptionTest {
    private static final String PASSWORD =
            Encryption.ENCRYPTION_SEPARATOR + "test1234!!!!" + Encryption.ENCRYPTION_SEPARATOR;

    @Test
    public void encryptAndDecryptPassword_Matches() {
        Encryption encryption = new Encryption();
        String encrypted = encryption.encryptPassword(PASSWORD);
        System.out.println(encrypted);
        assertTrue(encryption.verifyPassword(PASSWORD, encrypted));
    }

    @Test
    public void encryptAndDecryptPassword_DoesNotMatch() {
        Encryption encryption = new Encryption();
        String encrypted = encryption.encryptPassword(PASSWORD);
        assertFalse(encryption.verifyPassword(PASSWORD + Encryption.ENCRYPTION_SEPARATOR, encrypted));
    }
}
