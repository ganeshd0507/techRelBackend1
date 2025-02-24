package com.skbit.techrel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skbit.techrel.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
    User findByMobileNo(long mobileNo);
    User findByAadharNo(long aadharNo);


}
