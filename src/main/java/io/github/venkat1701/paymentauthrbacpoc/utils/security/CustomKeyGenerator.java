package io.github.venkat1701.paymentauthrbacpoc.utils.security;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * This is the first place to start your application. You start off by generating a
 * signing key for your JWT token. This process happens only once and henceforth, after fetching the key,
 * you can use it to sign and verify JWT tokens.
 *
 * The key generated might look like this: kyv+3jJXmc3ADmFUIvZUGeh7XvN0veYHPhBZ6qUI+d9eS6HkA6AGL40Ny/zX7TBN9rfiGHbnYlc1vh35qjQOzQ==
 *
 * @author Venkat
 * @version 1.0
 */
public class CustomKeyGenerator {

    private static Logger logger = Logger.getLogger(CustomKeyGenerator.class.getName());

    public static void main(String[] args) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
        SecretKey key = keyGenerator.generateKey();
        String base64key = Base64.getEncoder().encodeToString(key.getEncoded());
        logger.log(new LogRecord(Level.INFO, "Generated signing key: " + base64key));
    }
}
