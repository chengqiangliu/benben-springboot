package com.benben.service;

import com.benben.service.exception.ServiceSystemException;
import com.benben.service.input.EditUserServiceInput;
import com.benben.service.input.UpdatePasswordServiceInput;
import com.benben.service.input.AddUserServiceInput;
import com.benben.service.result.GetUserServiceResult;
import com.benben.service.result.SimpleServiceResult;

import java.util.List;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
public interface UserService {

    SimpleServiceResult insertUser(AddUserServiceInput input) throws ServiceSystemException;

    GetUserServiceResult getUserInfo(String username) throws ServiceSystemException;

    boolean isFirstLogon(String username) throws ServiceSystemException;

    boolean isLocked(String username) throws ServiceSystemException;

    void deletetUser(String username) throws ServiceSystemException;

    void editUser(EditUserServiceInput input) throws ServiceSystemException;

    boolean updatePassword(UpdatePasswordServiceInput input) throws ServiceSystemException;

    void updateLogonTime(String username);

    void updateFailLoginCount(String username, int failLoginCount);

    void lockUser(String username) throws ServiceSystemException;

    void unlockUser(String username) throws ServiceSystemException;

    boolean logon(String username, String password) throws ServiceSystemException;

    void lockUsers(List<String> userList) throws ServiceSystemException;

    List<String> getAllLockedUsers() throws ServiceSystemException;
}
