package com.petdiary.domain.rdspetdiarymembershipdb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MemberDomain {
    @Getter @Setter @Builder
    public static class Dto {
        private Long idx;
        private String email;
        private String name;
    }
}
