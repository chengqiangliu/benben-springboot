package com.benben.service.impl;

import com.benben.dao.entities.User;
import com.benben.dao.repositories.UserRepository;
import com.benben.global.constants.EventType;
import com.benben.global.events.CreateUserEvent;
import com.benben.logging.Loggers;
import com.benben.rabbitmq.EventPublisher;
import com.benben.service.UserService;
import com.benben.service.error.BenbenServiceError;
import com.benben.service.exception.ServiceSystemException;
import com.benben.service.input.EditUserServiceInput;
import com.benben.service.input.UpdatePasswordServiceInput;
import com.benben.service.input.AddUserServiceInput;
import com.benben.service.result.GetUserServiceResult;
import com.benben.service.result.SimpleServiceResult;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventPublisher eventPublisher;

    private Clock clock = Clock.systemUTC();

    @Override
    public SimpleServiceResult insertUser(AddUserServiceInput input) throws ServiceSystemException {
        User userInDb = this.userRepository.findByUsername(input.getUsername());
        if (userInDb != null) {
            throw new ServiceSystemException(BenbenServiceError.USERNAME_IS_DUPLICATED);
        }

        Date currentTime = new Date(this.clock.millis());
        User user = new User();
        user.setAccountId(UUID.randomUUID().toString());
        user.setUsername(input.getUsername());
        user.setPassword(input.getPassword());
        user.setEmail(input.getEmail());
        user.setGroupNo(input.getGroupNo());
        user.setFailLoginCount(0);
        user.setFirstLogin(true);
        user.setLastLogonTime(null);
        user.setLocked(false);
        user.setInsertTime(currentTime);
        user.setUpdateTime(currentTime);
        this.userRepository.save(user);

        SimpleServiceResult result = new SimpleServiceResult();
        result.setTime(currentTime);

        CreateUserEvent event = new CreateUserEvent();
        event.setType(EventType.CREATE_USER);
        event.setUsername(input.getUsername());
        event.setEmail(input.getEmail());
        this.eventPublisher.publish(event, "user.created");

        Loggers.SERVICE_LOGGER.info("invoke UserDao to insert user");
        return result;
    }

    @Override
    public GetUserServiceResult getUserInfo(String username) throws ServiceSystemException {
        User user = this.getUserByUsername(username);

        GetUserServiceResult serviceResult = new GetUserServiceResult();
        serviceResult.setAccountId(UUID.fromString(user.getAccountId()));
        serviceResult.setUsername(user.getUsername());
        serviceResult.setEmail(user.getEmail());
        serviceResult.setGroupNo(user.getGroupNo());
        serviceResult.setLastLogonTime(user.getLastLogonTime());
        serviceResult.setLogonTime(user.getLogonTime());
        serviceResult.setFailLoginCount(user.getFailLoginCount());
        serviceResult.setLocked(user.isLocked());
        serviceResult.setInsertTime(user.getInsertTime());
        serviceResult.setUpdateTime(user.getUpdateTime());
        return serviceResult;
    }

    @Override
    public boolean isFirstLogon(String username) throws ServiceSystemException {
        return this.userRepository.isFirstLogin(username);
    }

    @Override
    public boolean isLocked(String username) throws ServiceSystemException {
        return this.userRepository.getLockStatusByUsername(username);
    }

    @Override
    public void deletetUser(String username) throws ServiceSystemException {
        int deletedCount = this.userRepository.deleteByUsername(username);
        if (deletedCount == 0) {
            throw new ServiceSystemException(BenbenServiceError.USER_IS_NOT_FOUND);
        }
    }

    @Override
    public void editUser(EditUserServiceInput input) throws ServiceSystemException {
        User user = this.getUserByUsername(input.getUsername());

        user.setEmail(input.getEmail());
        user.setUpdateTime(new Date(this.clock.millis()));
        this.userRepository.save(user);
    }

    @Override
    public boolean updatePassword(UpdatePasswordServiceInput input) throws ServiceSystemException {
        User user = this.getUserByUsername(input.getUsername());
        if (input.getOldPassword().equals(user.getPassword())) {
            throw new ServiceSystemException(BenbenServiceError.OLD_PASSWORD_IS_NOT_CORRECT);
        }

        Date currentTime = new Date(this.clock.millis());
        int updateCount = this.userRepository.updatePassword(input.getNewPassword(), currentTime, input.getUsername(),
                user.getUpdateTime());
        if (updateCount == 1) {
            throw new ServiceSystemException(BenbenServiceError.THE_USER_WAS_UPDATED_BY_OTHER);
        }
        return true;
    }

    @Override
    public void updateLogonTime(String username) {
        User user = this.getUserByUsername(username);

        Date currentTime = new Date(this.clock.millis());
        user.setLastLogonTime(user.getLogonTime());
        user.setLogonTime(currentTime);
        user.setUpdateTime(currentTime);
        this.userRepository.save(user);
    }

    @Override
    public void updateFailLoginCount(String username, int failLoginCount) {
        User user = this.getUserByUsername(username);
        Date currentTime = new Date(this.clock.millis());
        user.setFailLoginCount(failLoginCount);
        user.setUpdateTime(currentTime);
        this.userRepository.save(user);
    }

    @Override
    public void lockUser(String username) throws ServiceSystemException {
        User user = this.getUserByUsername(username);

        Date currentTime = new Date(this.clock.millis());
        this.userRepository.updateLockStatus(true, currentTime, username, user.getUpdateTime());
    }

    @Override
    public void unlockUser(String username) throws ServiceSystemException {
        User user = this.getUserByUsername(username);

        Date currentTime = new Date(this.clock.millis());
        this.userRepository.updateLockStatus(false, currentTime, username, user.getUpdateTime());
    }

    @Override
    public boolean logon(String username, String password) throws ServiceSystemException {
        return this.userRepository.existsByUsernameAndPassword(username, password);
    }

    @Override
    public void lockUsers(List<String> userList) throws ServiceSystemException {
        Date currentTime = new Date(this.clock.millis());
        userList.stream().forEach(username -> {
            User user = this.getUserByUsername(username);
            user.setLocked(true);
            user.setUpdateTime(currentTime);
            this.userRepository.save(user);
        });
    }

    @Override
    public List<String> getAllLockedUsers() throws ServiceSystemException {
        return this.userRepository.findAllLockedUsername();
    }

    private User getUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new ServiceSystemException(BenbenServiceError.USER_IS_NOT_FOUND);
        }
        return user;
    }
}
