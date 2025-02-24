package com.skbit.techrel.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skbit.techrel.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
