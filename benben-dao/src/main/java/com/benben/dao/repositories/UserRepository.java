package com.benben.dao.repositories;

import com.benben.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    List<User> findAllByGroupNo(int groupNo);

    @Query("select u.accountId from User u where u.username = ?1")
    String findAccountIdByUsername(String username);

    @Query("select u.username from User u where u.isLocked = true")
    List<String> findAllLockedUsername();

    @Query("select u.isFirstLogin from User u where u.username = ?1")
    boolean isFirstLogin(String username);

    @Query("select u.isLocked from User u where u.username = ?1")
    boolean getLockStatusByUsername(String username);

    @Modifying
    @Query("update User u set u.password = ?1, u.updateTime = ?2 where u.username = ?3 and u.updateTime = ?4")
    int updatePassword(String newPassword, Date updateTime, String username, Date oldUpdateTime);

    @Modifying
    @Query("update User u set u.isLocked = ?1, u.updateTime = ?2 where u.username = ?3 and u.updateTime = ?4")
    int updateLockStatus(boolean lockStatus, Date updateTime, String username, Date oldUpdateTime);

    @Modifying
    @Query("delete User u where u.username = ?1")
    int deleteByUsername(String username);
}
