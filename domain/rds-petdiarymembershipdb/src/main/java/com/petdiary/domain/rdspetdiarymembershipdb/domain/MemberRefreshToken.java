package com.petdiary.domain.rdspetdiarymembershipdb.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "member_refresh_token")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class MemberRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tokenId;

    @Column(name = "member_idx")
    private Long memberIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", insertable = false, updatable = false)
    private Member member;

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "issued_date")
    private LocalDateTime issuedDate;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}
