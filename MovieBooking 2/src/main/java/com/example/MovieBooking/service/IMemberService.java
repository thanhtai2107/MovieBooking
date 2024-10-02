package com.example.MovieBooking.service;

import com.example.MovieBooking.dto.req.AccountDTO;
import com.example.MovieBooking.entity.Member;

public interface IMemberService {
    void saveMember(Member member);
    AccountDTO getMember(Long id);
    Member getMemberByID(Long id);
    Integer getScoreByMember(Long id);
    int updateMember(Long memberId, int score);
}
