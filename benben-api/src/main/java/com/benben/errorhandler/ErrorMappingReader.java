package com.benben.errorhandler;

import com.benben.auth.error.ApiError;
import com.benben.auth.exception.ConfigurationException;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ErrorMappingReader {

    /** ERROR_MAPPING_YAML */
    private static final String ERROR_MAPPING_YAML = "error-mapping.yml";

    /** EXTERNAL_ERROR_YAML */
    private static final String EXTERNAL_ERROR_YAML = "external-error.yml";

    /** EXTERNAL_ERROR_CODE_KEY */
    private static final String EXTERNAL_ERROR_CODE_KEY = "externalErrorCode";

    /** MESSAGE_KEY */
    private static final String MESSAGE_KEY = "message";

    /** REPLACEMENT_KEY */
    private static final String REPLACEMENT_KEY = "replacement";

    /** HTTP_STATUS_KEY */
    private static final String HTTP_STATUS_KEY = "httpStatus";

    /** REPLACEMENT_PLACE_HOLDER */
    private static final String REPLACEMENT_PLACE_HOLDER = "{replacement}";

    /** DEFAULT_KEY */
    private static final String DEFAULT_KEY = "Default";

    private Map<String, ExternalError> internalErrorToExternalErrorMap ;

    public ErrorMappingReader() throws ConfigurationException, IOException {
        this.internalErrorToExternalErrorMap = new HashMap<>();
        Yaml yaml = new Yaml();

        // load external-error.yml
        Map<String, Map<String, Object>> externalErrorMap = (Map<String, Map<String, Object>>)yaml
                .load(new ClassPathResource(EXTERNAL_ERROR_YAML).getInputStream());

        // load error-mapping.yml
        Map<String, Map<String, String>> errorMappingMap = (Map<String, Map<String, String>>)yaml
                .load(new ClassPathResource(ERROR_MAPPING_YAML).getInputStream());

        for (Map.Entry<String,  Map<String, String>> entry : errorMappingMap.entrySet()) {
            String externalErrorCode = entry.getValue().get(EXTERNAL_ERROR_CODE_KEY);
            if (externalErrorCode == null) {
                throw new ConfigurationException(ApiError.EXTERNAL_CODE_IS_REQUIRED);
            }

            Map<String, Object> externalError = externalErrorMap.get(externalErrorCode);
            if (externalError == null) {
                throw new ConfigurationException(ApiError.EXTERNAL_CODE_DOES_NOT_EXIST);
            }

            String externalErrorMessage = (String)externalError.get(MESSAGE_KEY);
            if (externalErrorMessage == null) {
                throw new ConfigurationException(ApiError.MESSAGE_IS_REQUIRED);
            }

            Integer httpStatus = (Integer)externalError.get(HTTP_STATUS_KEY);
            if (httpStatus == null) {
                throw new ConfigurationException(ApiError.HTTP_STATUS_IS_REQUIRED);
            }

            if (externalErrorMessage.contains(REPLACEMENT_PLACE_HOLDER)) {
                String replacement = entry.getValue().get(REPLACEMENT_KEY);
                if (replacement == null) {
                    throw new ConfigurationException(ApiError.REPLACEMENT_DOES_NOT_EXIST);
                }
                externalErrorMessage = externalErrorMessage.replace(REPLACEMENT_PLACE_HOLDER, replacement);
            }

            internalErrorToExternalErrorMap.put(entry.getKey(), new ExternalError(externalErrorCode, externalErrorMessage, httpStatus));
        }

        if (this.internalErrorToExternalErrorMap.get(DEFAULT_KEY) == null){
            throw new ConfigurationException(ApiError.DEFAULT_ERROR_MAPPING_DOES_NOT_EXIST);
        }
    }

    /**
     * get external error by internal error code
     *
     * @param intenalErrorCode intenalErrorCode
     * @return ExternalError
     */
    public ExternalError getExternalError(String intenalErrorCode) {
        ExternalError externalError = this.internalErrorToExternalErrorMap.get(intenalErrorCode);
        if (externalError == null) {
            externalError = this.internalErrorToExternalErrorMap.get(DEFAULT_KEY);
        }
        return externalError;
    }
}
