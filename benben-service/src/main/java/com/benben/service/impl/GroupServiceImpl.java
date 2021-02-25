package com.benben.service.impl;

import com.benben.dao.entities.Group;
import com.benben.dao.entities.User;
import com.benben.dao.repositories.GroupRepository;
import com.benben.dao.repositories.UserRepository;
import com.benben.global.constants.BenbenConstants;
import com.benben.service.GroupService;
import com.benben.service.error.BenbenServiceError;
import com.benben.service.exception.ServiceSystemException;
import com.benben.service.input.GroupServiceInput;
import com.benben.service.result.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    private Clock clock = Clock.systemUTC();

    @Override
    public GroupAddServiceResult insertGroup(GroupServiceInput input) throws ServiceSystemException {
        Date currentTime = new Date(this.clock.millis());

        Group group = new Group();
        group.setGroupNo(input.getGroupNo());
        group.setGroupName(input.getGroupName());
        group.setAdminGroup(input.isAdminGroup());
        group.setInsertTime(currentTime);
        group.setSecretKey(UUID.randomUUID().toString().replaceAll(BenbenConstants.SEPERATOR_HYPHEN, BenbenConstants.BLANK_STRING));
        group.setApiList(StringUtils.join(input.getApiList(), BenbenConstants.SEPERATOR_SEMICOLON));

        boolean existsFlg = this.groupRepository.existsByGroupNo(input.getGroupNo());
        if (existsFlg) {
            throw new ServiceSystemException(BenbenServiceError.GROUP_ALREADY_EXISTS);
        }
        this.groupRepository.save(group);

        GroupAddServiceResult result = new GroupAddServiceResult();
        result.setGroupName(input.getGroupName());
        return result;
    }

    @Override
    public GroupUpdateServiceResult updateGroup(GroupServiceInput input) throws ServiceSystemException {
        Group group = this.groupRepository.findByGroupNo(input.getGroupNo());
        if (group == null) {
            throw new ServiceSystemException(BenbenServiceError.GROUP_IS_NOT_FOUND);
        }
        Date currentTime = new Date(this.clock.millis());
        group.setAdminGroup(input.isAdminGroup());
        group.setApiList(StringUtils.join(input.getApiList(), BenbenConstants.SEPERATOR_SEMICOLON));
        group.setUpdateTime(currentTime);
        this.groupRepository.save(group);

        GroupUpdateServiceResult result = new GroupUpdateServiceResult();
        result.setUpdateTime(currentTime);
        return result;
    }

    @Override
    public void deleteGroup(int groupNo) throws ServiceSystemException {
        List<User> userList = this.userRepository.findAllByGroupNo(groupNo);
        if (!userList.isEmpty()) {
            throw new ServiceSystemException(BenbenServiceError.GROUP_CAN_NOT_BE_DELETED_WITH_USERS);
        }
        long deletedCount = this.groupRepository.deleteByGroupNo(groupNo);
        if (deletedCount == 0) {
            throw new ServiceSystemException(BenbenServiceError.GROUP_IS_NOT_FOUND);
        }
    }

    @Override
    public GetGroupServiceResult getGroupByNo(int groupNo) throws ServiceSystemException {
        Group group = this.groupRepository.findByGroupNo(groupNo);
        if (group == null) {
            throw new ServiceSystemException(BenbenServiceError.GROUP_IS_NOT_FOUND);
        }

        GetGroupServiceResult result = new GetGroupServiceResult();
        result.setGroup(group);
        return result;
    }

    @Override
    public GroupListServiceResult getGroupList() throws ServiceSystemException {
        List<Group> groupList = this.groupRepository.findAll();
        if (groupList.isEmpty()) {
            throw new ServiceSystemException(BenbenServiceError.NO_GROUP_IS_FOUND);
        }

        GroupListServiceResult result = new GroupListServiceResult();
        result.setGroupList(groupList);
        return result;
    }
}
