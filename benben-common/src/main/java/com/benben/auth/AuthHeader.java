package com.benben.auth;

import com.benben.auth.error.BenbenAuthError;
import com.benben.auth.exception.AuthValidationException;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Base64;

@Data
public class AuthHeader {

    private static final String SIGNATURE_PREFIX = "Signature ";

    private static final String SEPARATOR = ";";

    private static final int NUM_AFTER_SPLIT = 3;

    private String signature;

    private int groupNo;

    private String timestamp;

    private ZonedDateTime zonedDateTime;

    public AuthHeader(String authHeader) throws AuthValidationException {
        if (authHeader == null) {
            throw new AuthValidationException(BenbenAuthError.REQUIRED_AUTH_NOT_FOUND);
        }

        String base64 = authHeader.substring(SIGNATURE_PREFIX.length());
        if (base64.length() == 0) {
            throw new AuthValidationException(BenbenAuthError.INVALID_SIGNATURE);
        }
        try {
            // Base 64 Decode
            String header = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);

            // Parse header
            String[] authz = header.split(SEPARATOR);
            if (authz.length == NUM_AFTER_SPLIT) {
                String gpidString = authz[0];
                this.groupNo = Integer.parseInt(gpidString);
                this.signature = authz[1];
                this.timestamp = authz[2];
                zonedDateTime = ZonedDateTime.parse(timestamp);
            } else {
                throw new AuthValidationException(BenbenAuthError.INVALID_NUMBER_OF_AUTH_FIELD);
            }
        } catch (DateTimeParseException | IllegalArgumentException e) {
            throw new AuthValidationException(BenbenAuthError.INVALID_GROUP_NO);
        }
    }
}
