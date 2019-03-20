package com.benben.dao.entities;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_table")
@Proxy(lazy = false)
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "account_id")
	private String accountId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

	@Column(name = "email")
	private String email;

    @Column(name = "group_no")
    private String groupNo;

	@Column(name = "fail_login_count")
	private Integer failLoginCount;

	@Column(name = "is_first_login")
	private boolean isFirstLogin;

	@Column(name = "last_logon_time")
	private Date lastLogonTime;

	@Column(name = "logon_time")
	private Date logonTime;

	@Column(name = "is_locked")
	private boolean isLocked;

	@Column(name = "insert_time")
	private Date insertTime;

	@Column(name = "update_time")
	private Date updateTime;
}
