package com.example.MovieBooking.service.impl;


import com.example.MovieBooking.dto.req.AccountDTO;

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

    public int updateScoreMemberbyMemberId(Long memberId, int score) {
        return memberRepository.updateScoreByMemberId(memberId,score);}


    public Member updateMember(Member member) {
        return memberRepository.save(member);

    }

    /**
     * Save member into database
     *
     * @author Hoang Thanh Tai
     * @param member information of member
     */
    @Override
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public AccountDTO getMember(Long id) {
        Member member =  memberRepository.findById(id).orElse(null);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setMemberId(member.getMemberId());
        accountDTO.setFullname(member.getAccount().getFullname());
        accountDTO.setPhoneNumber(member.getAccount().getPhoneNumber());
        accountDTO.setIdentityCard(member.getAccount().getIdentityCard());
        accountDTO.setScore(Math.toIntExact(member.getScore()));
        return accountDTO;
    }

    @Override
    public Member getMemberByID(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public Integer getScoreByMember(Long id) {
        return memberRepository.findScoreByMemberId(id);
    }

    public Integer getTotalScore(Long id) {
        return memberRepository.getToTalScore(id);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * Get total page use for pagination
     *
     *
     * @param search key word to search member's information
     * @param page current page - used for pagination
     * @param size number of records per page - used for pagination
     * @return total page
     */
    public int getTotalPage(String search, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return memberRepository.getAllMembers(search,pageable).getTotalPages();
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(null);
    }

    /**
     * Show all and search member's information
     *
     * @author Hoang Thanh Tai
     * @param search key word to search member's information
     * @param page current page - used for pagination
     * @param size number of records per page - used for pagination
     * @return list of member's information
     */
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
