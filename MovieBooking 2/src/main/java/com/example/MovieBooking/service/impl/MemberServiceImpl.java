package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.entity.dto.MemberDTO;
import com.example.MovieBooking.repository.AccountRepository;
import com.example.MovieBooking.repository.MemberRepository;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.service.IMemberService;
import com.example.MovieBooking.service.IUploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private IUploadImage uploadImage;

    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }
    @Override
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Integer getTotalScore(Long id) {
        return memberRepository.getToTalScore(id);
    }

    public int getTotalPage(String search, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return memberRepository.getAllMembers(search,pageable).getTotalPages();
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(null);
    }

    @Override
    public List<MemberDTO> getAllMembers(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Object[]> objects = memberRepository.getAllMembers(search, pageable);
        List<MemberDTO> memberDTOS = new ArrayList<>();
        for (Object[] object: objects.toList()) {
            MemberDTO memberDTO = MemberDTO.builder()
                    .memberId(Long.parseLong(String.valueOf(object[0])))
                    .memberName(String.valueOf(object[1]))
                    .identityCard(String.valueOf(object[2]))
                    .email(String.valueOf(object[3]))
                    .phoneNumber(String.valueOf(object[4]))
                    .address(String.valueOf(object[5]))
                    .image(String.valueOf(object[6]))
                    .build();
            memberDTOS.add(memberDTO);
        }
        return memberDTOS;
    }
}
