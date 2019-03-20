package com.benben.service.result;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
public class GetUserServiceResult extends CommonServiceResult {

    private UUID accountId;

    private String username;

    private String email;

    private String groupNo;

    private int failLoginCount;

    private boolean isFirstLogin;

    private Date lastLogonTime;

    private Date logonTime;

    private boolean isLocked;

    private Date insertTime;

    private Date updateTime;
}
