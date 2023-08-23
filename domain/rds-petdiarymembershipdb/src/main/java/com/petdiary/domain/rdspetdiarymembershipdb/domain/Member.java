package com.petdiary.domain.rdspetdiarymembershipdb.domain;

import com.petdiary.domain.rdspetdiarymembershipdb.converters.MemberRoleConverter;
import com.petdiary.domain.rdspetdiarymembershipdb.converters.MemberStatusConverter;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberRoleType;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "member")
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "roles", nullable = false, length = 2047)
    @Convert(converter = MemberRoleConverter.class)
    private Set<MemberRoleType> roles;

    @Column(name = "access_token_expires_at")
    private LocalDateTime accessTokenExpiresAt;

    @Column(name = "status_code", nullable = false)
    @Convert(converter = MemberStatusConverter.class)
    private MemberStatusType statusCode;
}
