package com.benben.service;

import com.benben.service.exception.ServiceSystemException;
import com.benben.service.input.GroupServiceInput;
import com.benben.service.result.GetGroupServiceResult;
import com.benben.service.result.GroupAddServiceResult;
import com.benben.service.result.GroupListServiceResult;
import com.benben.service.result.GroupUpdateServiceResult;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
public interface GroupService {

    GroupAddServiceResult insertGroup(GroupServiceInput input) throws ServiceSystemException;

    GroupUpdateServiceResult updateGroup(GroupServiceInput input) throws ServiceSystemException;

    void deleteGroup(int groupNo) throws ServiceSystemException;

    GetGroupServiceResult getGroupByNo(int groupNo) throws ServiceSystemException;

    GroupListServiceResult getGroupList() throws ServiceSystemException;
}
