package com.benben.auth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DigitalSigner {

    private static final String DIGEST_ALGORITHM = "HmacSHA256";

    public static String generateDigest(String data, String salt) {
        byte[] hmacData = null;

        try {
            SecretKeySpec secretKey = new SecretKeySpec(salt.getBytes(StandardCharsets.UTF_8), DIGEST_ALGORITHM);
            Mac mac = Mac.getInstance(DIGEST_ALGORITHM);
            mac.init(secretKey);
            hmacData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(hmacData));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return null;
        }
    }
}
