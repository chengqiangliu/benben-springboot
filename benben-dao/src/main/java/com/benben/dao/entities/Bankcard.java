package com.benben.dao.entities;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="bankcard_table")
@Proxy(lazy = false)
@Data
public class Bankcard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "card_no")
	private String cardNo;

	@Column(name = "card_type")
	private String cardType;

	@Column(name = "bank_type")
	private String bankType;

	@Column(name = "account_id")
	private UUID accountId;

	@Column(name = "currency")
	private String currency;

	@Column(name = "credit_sum")
	private BigDecimal creditSum;

	@Column(name = "balance")
	private BigDecimal balance;

	@Column(name = "issue_date")
	private Date issueDate;

	@Column(name = "insert_time")
	private Date insertTime;

	@Column(name = "update_time")
	private Date updateTime;
}
