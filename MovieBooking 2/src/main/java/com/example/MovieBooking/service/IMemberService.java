package com.example.MovieBooking.service;

import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.entity.dto.MemberDTO;

import java.util.List;

public interface IMemberService {
    void saveMember(Member member);
    List<MemberDTO> getAllMembers(String search, int page, int size);
    int getTotalPage(String search, int page, int size);
    Member findMemberById(Long id);
}
