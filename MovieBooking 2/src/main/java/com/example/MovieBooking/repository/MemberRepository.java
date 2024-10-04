package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @Query("select m.score from Member m where m.account.accountId = ?1")
    Integer getToTalScore(Long id);

    @Query("SELECT m.memberId, a.fullname, a.identityCard, a.email, a.phoneNumber, a.address, a.image FROM Member m JOIN Account a ON m.account.accountId = a.accountId where a.fullname like %:search% OR a.email like %:search% OR a.identityCard like %:search% OR a.phoneNumber like %:search% ")
    Page<Object[]> getAllMembers(@Param("search") String search, Pageable pageable);

}
