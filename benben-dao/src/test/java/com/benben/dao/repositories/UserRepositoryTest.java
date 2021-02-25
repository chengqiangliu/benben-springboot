package com.benben.dao.repositories;

import com.benben.dao.common.CommonDaoTests;
import com.benben.dao.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
public class UserRepositoryTest extends CommonDaoTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave () {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@gmail.com");
        user.setGroupNo(10001);
        user.setFailLoginCount(0);
        user.setFirstLogin(false);
        user.setLastLogonTime(null);
        user.setLocked(false);
        user.setInsertTime(new Date());
        user.setUpdateTime(new Date());

        this.userRepository.save(user);
    }
}
