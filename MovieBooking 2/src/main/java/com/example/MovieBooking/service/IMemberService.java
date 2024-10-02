package com.example.MovieBooking.service;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.entity.dto.MemberDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IMemberService {
    void saveMember(Member member);

    Integer getTotalScore(Long id);
    List<Member> getAllMembers();
    List<MemberDTO> getAllMembers(String search, int page, int size);
    int getTotalPage(String search, int page, int size);
    Member findMemberById(Long id);
}
