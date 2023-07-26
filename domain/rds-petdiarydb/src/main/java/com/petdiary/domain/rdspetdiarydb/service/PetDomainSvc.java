package com.petdiary.domain.rdspetdiarydb.service;

import com.petdiary.domain.rdspetdiarydb.dto.PetDomain;
import com.petdiary.domain.rdspetdiarydb.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetDomainSvc {
    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    public List<PetDomain.Dto> getList() {
        return petRepository.findAll().stream()
                .map(pet -> PetDomain.Dto.builder()
                        .idx(pet.getIdx())
                        .name(pet.getName())
                        .kind(pet.getKind())
                        .build())
                .collect(Collectors.toList());
    }
}
