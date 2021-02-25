package com.benben.errorhandler;

import com.benben.global.constants.BenbenConstants;
import com.benben.response.ErrorResponse;
import com.benben.auth.error.ApiError;
import com.benben.auth.exception.ApiSystemException;
import com.benben.auth.exception.ApiValidateException;
import com.benben.auth.exception.ConfigurationException;
import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import com.benben.service.exception.ServiceSystemException;
import com.benben.service.exception.ServiceValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@ControllerAdvice
public class ExceptionHandler {

    /**
     * handle HttpMediaTypeNotSupportedException
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        Loggers.API_LOGGER.error(LoggerFormat.COMMON_EXCEPTION,
                ApiError.HTTP_MEDIA_TYPE_NOT_SUPPORTED.getCode(),
                ApiError.HTTP_MEDIA_TYPE_NOT_SUPPORTED.getMessage(),
                this.getRootCauseString(ex),
                this.getRootStackString(ex));
        ResponseEntity<ErrorResponse> responseEntity = this.getResponseEntity(ApiError.HTTP_MEDIA_TYPE_NOT_SUPPORTED.getCode());
        return responseEntity;
    }

    /**
     * handle HttpRequestMethodNotSupportedException
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        Loggers.API_LOGGER.error(LoggerFormat.COMMON_EXCEPTION,
                ApiError.HTTP_METHOD_ERROR.getCode(),
                ApiError.HTTP_METHOD_ERROR.getMessage(),
                this.getRootCauseString(ex),
                this.getRootStackString(ex));
        ResponseEntity<ErrorResponse> responseEntity = this.getResponseEntity(ApiError.HTTP_METHOD_ERROR.getCode());
        return responseEntity;
    }

    /**
     * handle MethodArgumentNotValidException
     *
     * @param ex MethodArgumentNotValidException
     * @return ResponseEntity
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ErrorItem> errorList = new ArrayList<>();
        List<ObjectError> allErrors = new ArrayList<>();
        Set<String> errorFieldSet = new HashSet<>();

        for(ObjectError error : bindingResult.getAllErrors()){
            allErrors.add(error);
        }

        Collections.sort(allErrors, (o1, o2) -> {
            if(o1.getCode() != null && o2.getCode() != null){
                return o2.getCode().compareTo(o1.getCode());
            }
            return 0;
        });

        ApiError apiError;
        String internalCode = ApiError.SYSTEM_ERROR.getCode();
        for(ObjectError error : allErrors){
            if(error instanceof FieldError){
                FieldError fieldError = (FieldError)error;
                String field = fieldError.getField();
                if(errorFieldSet.contains(field)){
                    continue;
                }
                errorFieldSet.add(field);

                switch(fieldError.getCode()){
                    case "NotNull":
                        apiError = this.getApiError(field.toUpperCase() + "_IS_NULL");
                        break;
                    case "Future":
                        apiError = this.getApiError(field.toUpperCase() + "_Expired");
                        break;
                    case "Size":
                    case "Max":
                    case "Min":
                    case "Pattern":
                    case "Email":
                        apiError = this.getApiError(field.toUpperCase() + "_INVALID_FORMAT");
                        break;
                    default:
                        apiError = ApiError.SYSTEM_ERROR;
                }
                internalCode = apiError.getCode();
            }
        }

        return this.getResponseEntity(internalCode);
    }


    /**
     * handle ApiValidateException
     *
     * @param ex ApiValidateException
     * @return ResponseEntity
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ApiValidateException.class)
    public ResponseEntity<ErrorResponse> handleApiValidateException(ApiValidateException ex) {
        Loggers.API_LOGGER.error(LoggerFormat.COMMON_EXCEPTION,
                ex.getErrorCode(),
                ex.getErrorMessage(),
                this.getRootCauseString(ex),
                this.getRootStackString(ex));
        ResponseEntity<ErrorResponse> responseEntity = this.getResponseEntity(ex.getErrorCode());
        return responseEntity;
    }

    /**
     * handle ApiSystemException
     *
     * @param ex ApiSystemException
     * @return ResponseEntity
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ApiSystemException.class)
    public ResponseEntity<ErrorResponse> handleApiSystemException(ApiSystemException ex) {
        Loggers.API_LOGGER.error(LoggerFormat.COMMON_EXCEPTION,
                ex.getErrorCode(),
                ex.getErrorMessage(),
                this.getRootCauseString(ex),
                this.getRootStackString(ex));
        ResponseEntity<ErrorResponse> responseEntity = this.getResponseEntity(ex.getErrorCode());
        return responseEntity;
    }

    /**
     * handle ServiceValidateException
     *
     * @param ex ServiceValidateException
     * @return ResponseEntity
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceValidateException.class)
    public ResponseEntity<ErrorResponse> handleServiceValidateException(ServiceValidateException ex) {
        Loggers.API_LOGGER.error(LoggerFormat.COMMON_EXCEPTION,
                ex.getErrorCode(),
                ex.getErrorMessage(),
                this.getRootCauseString(ex),
                this.getRootStackString(ex));
        ResponseEntity<ErrorResponse> responseEntity = this.getResponseEntity(ex.getErrorCode());
        return responseEntity;
    }

    /**
     * handle AuthValidationException
     *
     * @param ex FfmsSystemException
     * @return ResponseEntity
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceSystemException.class)
    public ResponseEntity<ErrorResponse> handleServiceSystemException(ServiceSystemException ex) {
        Loggers.API_LOGGER.error(LoggerFormat.COMMON_EXCEPTION,
                ex.getErrorCode(),
                ex.getErrorMessage(),
                this.getRootCauseString(ex),
                this.getRootStackString(ex));
        ResponseEntity<ErrorResponse> responseEntity = this.getResponseEntity(ex.getErrorCode());
        return responseEntity;
    }

    /**
     * handle ConfigurationException
     *
     * @param ex ConfigurationException
     * @return ResponseEntity
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ConfigurationException.class)
    public ResponseEntity<ErrorResponse> handleConfigurationException(ConfigurationException ex) {
        Loggers.API_LOGGER.error(LoggerFormat.COMMON_EXCEPTION,
                ex.getErrorCode(),
                ex.getErrorMessage(),
                this.getRootCauseString(ex),
                this.getRootStackString(ex));
        ResponseEntity<ErrorResponse> responseEntity = this.getResponseEntity(ex.getErrorCode());
        return responseEntity;
    }

    /**
     * handle all other Exception
     *
     * @param ex Exception
     * @return ResponseEntity
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, Exception ex) {
        Loggers.API_LOGGER.error(LoggerFormat.COMMON_EXCEPTION,
                                ApiError.SYSTEM_ERROR.getCode(),
                                ApiError.SYSTEM_ERROR.getMessage(),
                                this.getRootCauseString(ex),
                                this.getRootStackString(ex));
        ResponseEntity<ErrorResponse> responseEntity = this.getResponseEntity(ApiError.SYSTEM_ERROR.getCode());
        return responseEntity;
    }

    /**
     * get ExternalError
     *
     * @param internalCode
     * @return
     */
    private ExternalError getExternalError(String internalCode) throws ConfigurationException {
        ErrorMappingReader errorMappingReader = null;
        try {
            errorMappingReader = new ErrorMappingReader();
        } catch (IOException e) {
            throw new ConfigurationException(ApiError.READING_CONFIGURATION_FILE_ERROR);
        }
        return errorMappingReader.getExternalError(internalCode);
    }

    /**
     * get ResponseEntity by internal error code
     *
     * @param internalCode
     * @return
     */
    private ResponseEntity<ErrorResponse> getResponseEntity(String internalCode) throws ConfigurationException {
        List<ErrorItem> errorList = new ArrayList<>();
        ExternalError externalError = this.getExternalError(internalCode);
        errorList.add(new ErrorItem(externalError));
        ErrorResponse response = new ErrorResponse();
        response.setErrors(errorList);
        return new ResponseEntity<>(response, HttpStatus.valueOf(externalError.getHttpStatus()));
    }

    private String getRootCauseString (Throwable ex) {
        return Loggers.getRootCause(ex).toString().replaceAll("\n", BenbenConstants.BLANK_STRING);
    }

    private String getRootStackString (Throwable ex) {
        return Loggers.getRootCause(ex).getStackTrace().toString().replaceAll("\n", BenbenConstants.BLANK_STRING);
    }

    /**
     * get ApiError
     *
     * @param errorKey errorKey
     * @return ApiError
     */
    private ApiError getApiError(String errorKey){
        try {
            return ApiError.valueOf(errorKey);
        } catch (Exception e) {
            return ApiError.SYSTEM_ERROR;
        }
    }

}
