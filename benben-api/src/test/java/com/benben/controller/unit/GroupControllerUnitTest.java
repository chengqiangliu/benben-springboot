package com.benben.controller.unit;

import com.benben.controller.GroupController;
import com.benben.global.ApiRequestMapping;
import com.benben.global.constants.BenbenConstants;
import com.benben.request.GroupRequest;
import com.benben.response.GroupAddResponse;
import com.benben.global.http.ResponseStatus;
import com.benben.service.GroupService;
import com.benben.service.result.GroupAddServiceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupControllerUnitTest {

    private MockMvc mockMvc;

    @MockBean
    private GroupService service;

    @Autowired
    private GroupController controller;

    private ObjectMapper mapper = new ObjectMapper();

    private GroupRequest request;

    private GroupAddServiceResult serviceResult;

    @Before
    public void setUp() {
        this.request = new GroupRequest();
        this.request.setGroupNo(10001);
        this.request.setGroupName("admin group");
        this.request.setAdminGroup(true);
        this.request.setApiList(Arrays.asList(new String[]{"RegisterUserAPI", "AddBandcardAPI"}));

        this.serviceResult = new GroupAddServiceResult();
        this.serviceResult.setGroupName("admin group");
        this.serviceResult.setServiceStatus(0);

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
        given(this.service.insertGroup(any())).willReturn(this.serviceResult);
    }

    @After
    public void tearDown() {
        this.request = null;
    }

    @Test
    public void testAddUserGroupSuccess() throws Exception {
        MvcResult mvcResult = this.getResultByContent(this.request);
        assertEquals(mvcResult.getResponse().getStatus(), 200);
        GroupAddResponse actualResponse = this.mapper.readValue(mvcResult.getResponse().getContentAsString(),
                GroupAddResponse.class);

        assertEquals(actualResponse.getGroupName(), this.request.getGroupName());
        assertEquals(actualResponse.getStatus(), ResponseStatus.OK);
    }

    /**
     * mock to trigger controller method by content
     *
     * @param request
     * @return
     * @throws Exception
     */
    private MvcResult getResultByContent(GroupRequest request) throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.post(ApiRequestMapping.USER_GROUP_ADD_REQUEST_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(this.mapper.writeValueAsBytes(request)))
                .andDo(MockMvcResultHandlers.print()).andReturn();
        return mvcResult;
    }

    /**
     * mock to trigger controller method by json
     *
     * @param json
     * @return
     * @throws Exception
     */
    private MvcResult callControllerWithJson(String json) throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.post(ApiRequestMapping.USER_GROUP_ADD_REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_VALUE + BenbenConstants.PRODUCE_CHARSET)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
