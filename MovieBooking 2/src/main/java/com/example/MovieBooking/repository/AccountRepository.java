package com.example.MovieBooking.repository;

import com.example.MovieBooking.dto.req.AccountDTO;
import com.example.MovieBooking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

   
    @Query("SELECT m.memberId, a.identityCard, a.fullname, a.phoneNumber, m.score FROM Member m JOIN Account a ON m.account.accountId = a.accountId WHERE m.memberId = :memberId")
    Object[] findByMemberId(Long memberId);
    @Query("SELECT m.memberId, a.identityCard, a.fullname, a.phoneNumber, m.score FROM Member m JOIN Account a ON m.account.accountId = a.accountId WHERE a.identityCard = :IdentityCard")
    Object[] findByIdentityCard(String IdentityCard);
    

    @Query("SELECT a FROM Member m JOIN Account a ON m.account.accountId = a.accountId WHERE m.memberId = :memberId")
    Account findAccountByMemberId(Long memberId);

}
