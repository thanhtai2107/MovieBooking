package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m.score from Member m where m.account.accountId = ?1")
    Integer getToTalScore(Long id);
}
