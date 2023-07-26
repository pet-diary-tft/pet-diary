package com.petdiary.domain.rdspetdiarydb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class PetDomain {
    @Getter @Setter @Builder
    public static class Dto {
        private Long idx;
        private String name;
        private String kind;
    }
}
