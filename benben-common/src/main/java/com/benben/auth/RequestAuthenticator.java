package com.benben.auth;

import com.benben.auth.error.BenbenAuthError;
import com.benben.auth.exception.AuthValidationException;
import com.benben.logging.Loggers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class RequestAuthenticator {

    /** Injected threshold for validating auth header */
    @Value("#{new Long('${auth.threshold:10}')}")
    private Long threshold;


    public void authenticateRequest(AuthHeader authHeader, String url, String body, String secret)
            throws AuthValidationException {
        String timestamp = authHeader.getTimestamp();
        String payload = url + timestamp + body;

        ZonedDateTime zdt = authHeader.getZonedDateTime();
        ZonedDateTime minLimit = ZonedDateTime.now().minusMinutes(this.threshold);
        ZonedDateTime maxLimit = ZonedDateTime.now().plusMinutes(this.threshold);

        if (!(zdt.isAfter(minLimit) && zdt.isBefore(maxLimit))) {
            throw new AuthValidationException(BenbenAuthError.TIMESTAMP_EXPIRED);
        }
        Loggers.AUTH.info("Parameter for generating Signature: url -> {} ,ts -> {} ,body -> {}, secret -> {}|",
                url, timestamp, body, secret);
        String generatedSignature = DigitalSigner.generateDigest(payload, secret);

        Loggers.AUTH.info("Payload: {}", payload);
        Loggers.AUTH.info("Signature from header: {}, Generated Signature: {}", authHeader.getSignature(),
                generatedSignature);
        if (!authHeader.getSignature().equals(generatedSignature)) {
            throw new AuthValidationException(BenbenAuthError.SIGNATURE_NOT_MATCH);
        }
    }
}
