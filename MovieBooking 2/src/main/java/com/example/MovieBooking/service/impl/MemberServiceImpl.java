package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.dto.req.AccountDTO;
import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.repository.MemberRepository;
import com.example.MovieBooking.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private MemberRepository memberRepository;

    public int updateMember(Long memberId, int score) {
        return memberRepository.updateScoreByMemberId(memberId,score);
    }
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
        accountDTO.setScore(member.getScore());
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
}
