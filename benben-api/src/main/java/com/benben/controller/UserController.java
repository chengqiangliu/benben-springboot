package com.benben.controller;

import com.benben.annotation.BenbenApiEndpoint;
import com.benben.global.ApiRequestMapping;
import com.benben.global.constants.BenbenConstants;
import com.benben.request.UserRequest;
import com.benben.response.BenbenResponse;
import com.benben.global.constants.ApiName;
import com.benben.global.utils.DateUtils;
import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import com.benben.service.UserService;
import com.benben.service.input.AddUserServiceInput;
import com.benben.service.result.SimpleServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = ApiRequestMapping.USER_REGISTER_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> registerUser(@Valid @RequestBody UserRequest request) {
        AddUserServiceInput serviceInput = new AddUserServiceInput();
        serviceInput.setUsername(request.getUsername());
        serviceInput.setPassword(request.getPassword());
        serviceInput.setGroupNo(request.getGroupNo());
        serviceInput.setEmail(request.getEmail());

        SimpleServiceResult serviceResult = this.service.insertUser(serviceInput);
        Loggers.API_LOGGER.info("invoke UserService to insert benben");

        BenbenResponse response = new BenbenResponse();
        response.setRegisterTime(DateUtils.getDateInISOFormat(serviceResult.getTime()));

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.USER_DELETE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.DELETE_USER)
    public ResponseEntity<BenbenResponse> deleteUser(UserRequest request) {
        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.USER_EDIT_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.EDIT_USER)
    public ResponseEntity<BenbenResponse> editUser(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.USER_LIST_REQUEST_MAPPING, method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.GET_USER)
    public ResponseEntity<BenbenResponse> getUser(String username) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = ApiRequestMapping.UPDATE_PASSWORD_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.REGISTER_USER)
    public ResponseEntity<BenbenResponse> updatePassword(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
