package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.repository.MemberRepository;
import com.example.MovieBooking.service.IMemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IMemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member updaMember(Member member) {
        return memberRepository.save(member);
    }
}
