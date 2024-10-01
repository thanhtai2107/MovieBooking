package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Member;

public interface IMemberService {
    void saveMember(Member member);
    Integer getTotalScore(Long id);
    List<Member> getAllMembers();
}
