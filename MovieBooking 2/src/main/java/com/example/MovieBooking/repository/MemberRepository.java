package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m.memberId, a.fullname, a.identityCard, a.email, a.phoneNumber, a.address, a.image FROM Member m JOIN Account a ON m.account.accountId = a.accountId where a.fullname like %:search% OR a.email like %:search% OR a.identityCard like %:search% OR a.phoneNumber like %:search% ")
    Page<Object[]> getAllMembers(@Param("search") String search, Pageable pageable);
}
