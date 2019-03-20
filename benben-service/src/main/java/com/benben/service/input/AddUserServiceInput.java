package com.benben.service.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
@Setter
@Getter
@NoArgsConstructor
public class AddUserServiceInput {

    private String username;

    private String password;

    private String groupNo;

    private String email;
}
