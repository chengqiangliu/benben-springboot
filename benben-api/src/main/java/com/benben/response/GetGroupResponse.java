package com.benben.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class GetGroupResponse {

    private int groupNo;

    private String groupName;

    private String secretKey;

    private boolean isAdminGroup;

    private List<String> apiList;

    private Date insertTime;

    private Date updateTime;
}
