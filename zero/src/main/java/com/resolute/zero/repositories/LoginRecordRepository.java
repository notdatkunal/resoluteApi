package com.resolute.zero.repositories;


import com.resolute.zero.domains.LoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecord,Integer> {
}
