package com.petdiary.domain.rdspetdiarydb.service;

import com.petdiary.domain.rdspetdiarydb.dto.MemberDomain;
import com.petdiary.domain.rdspetdiarydb.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberDomainSvc {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberDomain.Dto> getList() {
        return memberRepository.findAll().stream()
                .map(member -> MemberDomain.Dto.builder()
                        .idx(member.getIdx())
                        .email(member.getEmail())
                        .name(member.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
