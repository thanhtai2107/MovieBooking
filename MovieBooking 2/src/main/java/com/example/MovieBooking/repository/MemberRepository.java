package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m.score FROM Member m WHERE m.memberId = :memberId")
    int findScoreByMemberId(Long memberId);
 
    @Query("UPDATE Member m SET m.score = :score WHERE m.memberId = :memberId")
    int updateScoreByMemberId(@Param("memberId") Long memberId, @Param("score") Integer score);
}
