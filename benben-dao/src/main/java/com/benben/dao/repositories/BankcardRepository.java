package com.benben.dao.repositories;

import com.benben.dao.entities.Bankcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BankcardRepository extends JpaRepository<Bankcard, Integer> {

    List<Bankcard> findAllByAccountIdAndCurrency(UUID accountId, String currency);

    boolean existsByCardNo(String cardNo);

    Bankcard findByCardNo(String cardNo);

    long deleteByCardNo(String cardNo);
}
