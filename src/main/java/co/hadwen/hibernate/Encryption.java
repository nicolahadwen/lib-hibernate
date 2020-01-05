package co.hadwen.hibernate;

import com.google.common.annotations.VisibleForTesting;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class Encryption {
    @VisibleForTesting
    protected static final String ENCRYPTION_SEPARATOR = "$";
    @VisibleForTesting
    protected static final String ENCRYPTION_SEPARATOR_REGEX = "\\$";
    private static final String ENCRYPTED_PASSWORD_FORMAT = "%s%s%s";

    public String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] salt = createSalt();
            md.update(salt);
            md.update(password.getBytes(StandardCharsets.UTF_8));

            byte[] passEnc = md.digest();
            return String.format(ENCRYPTED_PASSWORD_FORMAT,
                    new String(Base64.getEncoder().encode(salt)),
                    ENCRYPTION_SEPARATOR,
                    new String(Base64.getEncoder().encode(passEnc)));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean verifyPassword(String encryptedPassphrase, String password) {
        String[] splited = encryptedPassphrase.split(ENCRYPTION_SEPARATOR_REGEX);
        if(splited.length != 2) {
            throw new RuntimeException(
                    String.format("Password is not encoded as expected. split length: %d", splited.length));
        }
        byte[] salt = Base64.getDecoder().decode(splited[0].getBytes());
        byte[] passEncDb = Base64.getDecoder().decode(splited[1].getBytes());

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(salt);
            md.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] passEnc = md.digest();

            return Arrays.equals(passEncDb, passEnc);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }


    private byte[] createSalt() {
        byte[] salt = new byte[16];
        new Random().nextBytes(salt);
        return salt;
    }
}
