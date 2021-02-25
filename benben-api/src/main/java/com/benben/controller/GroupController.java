package com.benben.controller;

import com.benben.annotation.BenbenApiEndpoint;
import com.benben.global.ApiRequestMapping;
import com.benben.global.constants.ApiName;
import com.benben.global.constants.BenbenConstants;
import com.benben.logging.LoggerFormat;
import com.benben.logging.Loggers;
import com.benben.request.GroupRequest;
import com.benben.request.UserRequest;
import com.benben.response.BenbenResponse;
import com.benben.response.GetGroupResponse;
import com.benben.response.GroupAddResponse;
import com.benben.global.http.ResponseStatus;
import com.benben.service.GroupService;
import com.benben.service.input.GroupServiceInput;
import com.benben.service.result.GetGroupServiceResult;
import com.benben.service.result.GroupAddServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@CrossOrigin
@RestController
public class GroupController {

    @Autowired
    private GroupService service;

    /**
     * add a new group
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ApiRequestMapping.GROUP_ADD_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.ADD_GROUP)
    public ResponseEntity<GroupAddResponse> addGroup(@Valid @RequestBody GroupRequest request) {

        GroupServiceInput serviceInput = new GroupServiceInput();
        serviceInput.setGroupNo(request.getGroupNo());
        serviceInput.setGroupName(request.getGroupName());
        serviceInput.setAdminGroup(request.isAdminGroup());
        serviceInput.setApiList(request.getApiList());

        GroupAddServiceResult serviceResult = this.service.insertGroup(serviceInput);
        GroupAddResponse response = new GroupAddResponse();
        response.setGroupName(serviceResult.getGroupName());

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, ResponseStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * update a group
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ApiRequestMapping.GROUP_UPDATE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.UPDATE_GROUP)
    public ResponseEntity<BenbenResponse> udpateGroup(GroupRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * delete a group
     *
     * @param groupNo
     * @return
     */
    @RequestMapping(value = ApiRequestMapping.GROUP_DELETE_REQUEST_MAPPING, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.DELETE_GROUP)
    public ResponseEntity<BenbenResponse> deleteGroup(@PathVariable("groupNo") int groupNo) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * get a group
     *
     * @param groupNo
     * @return
     */
    @RequestMapping(value = ApiRequestMapping.GROUP_GET_REQUEST_MAPPING, method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.GET_GROUP)
    public ResponseEntity<GetGroupResponse> getGroup(@PathVariable("groupNo") int groupNo) {

        GetGroupServiceResult serviceResult = this.service.getGroupByNo(groupNo);
        GetGroupResponse response = new GetGroupResponse();
        response.setGroupNo(serviceResult.getGroup().getGroupNo());
        response.setGroupName(serviceResult.getGroup().getGroupName());
        response.setAdminGroup(serviceResult.getGroup().isAdminGroup());
        response.setApiList(Arrays.asList(serviceResult.getGroup().getApiList()
                .split(BenbenConstants.SEPERATOR_SEMICOLON)));
        response.setInsertTime(serviceResult.getGroup().getInsertTime());
        response.setUpdateTime(serviceResult.getGroup().getUpdateTime());
        response.setSecretKey(serviceResult.getGroup().getSecretKey());

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * list all groups
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ApiRequestMapping.GROUP_LIST_REQUEST_MAPPING, method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
    @ResponseBody
    @BenbenApiEndpoint(apiName = ApiName.GROUP_LIST)
    public ResponseEntity<BenbenResponse> listGroups(UserRequest request) {

        BenbenResponse response = new BenbenResponse();

        Loggers.API_LOGGER.info(LoggerFormat.RESPONSE, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
