package com.benben.service;

import com.benben.dao.entities.User;
import com.benben.dao.repositories.UserRepository;
import com.benben.rabbitmq.EventPublisher;
import com.benben.service.impl.UserServiceImpl;
import com.benben.service.input.AddUserServiceInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventPublisher eventPublisher;

    private UserService service;

    private AddUserServiceInput input;

    @Before
    public void setUp() throws Exception {
        this.service = new UserServiceImpl();
        ReflectionTestUtils.setField(this.service, "userRepository", this.userRepository);
        ReflectionTestUtils.setField(this.service, "eventPublisher",this.eventPublisher);

        when(this.userRepository.save(any(User.class))).thenReturn(null);
        doNothing().when(this.eventPublisher).publish(any(), any(String.class));

        input = new AddUserServiceInput();
        input.setUsername("testusername");
        input.setPassword("testpassword");
        input.setEmail("tester@gmail.com");
        input.setGroupNo("10001");
    }

    @After
    public void tearDown() throws Exception {
        input = null;
    }

    @Test
    public void testInsertUserSucceed() throws Exception {
        this.service.insertUser(this.input);
    }
}
