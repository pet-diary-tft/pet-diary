package com.petdiary.security.oauth2;

import com.petdiary.domain.rdspetdiarymembershipdb.domain.Member;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberSocialType;
import com.petdiary.domain.rdspetdiarymembershipdb.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiOauth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public Oauth2UserPrincipal loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String nameAttributeKey = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        MemberSocialType memberSocialType = MemberSocialType.getByRegistrationId(userRequest.getClientRegistration().getRegistrationId());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Oauth2Attributes oauth2Attributes = new Oauth2Attributes(memberSocialType, nameAttributeKey, oAuth2User.getAttributes());

        Member member = memberRepository.findMemberBySocialIdAndSocialType(oauth2Attributes.getOauth2UserInfo().getId(), memberSocialType).orElse(null);
        return member == null ? oauth2Attributes.getPrincipal(memberRepository.save(oauth2Attributes.register())) : oauth2Attributes.getPrincipal(member);
    }
}
