package com.example.project1.repository;

import com.example.project1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByEmail(String email);

    @Query("select u from User u where u.firstName =:q and u.age in (:ids)")
    User findSomething(@Param("q") String a, @Param("ids") List<Integer> b);

    @Query(value = "select * from users where password=:q", nativeQuery = true)
    User findByNativeQuery(@Param("q") String a);

    boolean existsByEmail(String email);

    User findByActivationCode(String code);

}
