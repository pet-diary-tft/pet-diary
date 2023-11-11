package com.petdiary.security.oauth2;

import java.util.Collections;
import java.util.Map;

public class KakaoUserInfo extends Oauth2UserInfo {
    public KakaoUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getEmail() {
        Map<String, Object> account = (Map<String, Object>) attributes.getOrDefault("kakao_account", Collections.emptyMap());
        return (String) account.get("email");
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getNickname() {
        Map<String, Object> account = (Map<String, Object>) attributes.getOrDefault("kakao_account", Collections.emptyMap());
        Map<String, Object> profile = (Map<String, Object>) account.getOrDefault("profile", Collections.emptyMap());
        return (String) profile.get("nickname");
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getProfileImage() {
        Map<String, Object> account = (Map<String, Object>) attributes.getOrDefault("kakao_account", Collections.emptyMap());
        Map<String, Object> profile = (Map<String, Object>) account.getOrDefault("profile", Collections.emptyMap());
        return (String) profile.get("thumbnail_image_url");
    }
}
