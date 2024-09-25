package com.example.MovieBooking.service.impl;

import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.repository.MemberRepository;
import com.example.MovieBooking.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void saveMember(Member member) {
        memberRepository.save(member);
    }
}
