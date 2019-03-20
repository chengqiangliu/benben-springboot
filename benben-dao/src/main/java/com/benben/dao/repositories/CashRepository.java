package com.benben.dao.repositories;

import com.benben.dao.entities.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CashRepository extends JpaRepository<Cash, Integer> {

    Cash findByAccountIdAndCurrency(String accountId, String currency);

    boolean existsByAccountIdAndCurrency(String accountId, String currency);

    @Transactional
    @Modifying
    @Query("delete from Cash c where c.accountId = ?1 and c.currency = ?2")
    int deleteByAccountIdAndCurrency(String accountId, String currency);
}
