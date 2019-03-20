package com.benben.dao.repositories;

import com.benben.dao.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    boolean existsByGroupNo(int groupNo);

    Group findByGroupNo(int groupNo);

    long deleteByGroupNo(int groupNo);
}
