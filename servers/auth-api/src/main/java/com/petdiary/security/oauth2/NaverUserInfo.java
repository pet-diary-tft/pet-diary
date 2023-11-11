package com.petdiary.security.oauth2;

import java.util.Collections;
import java.util.Map;

public class NaverUserInfo extends Oauth2UserInfo {
    public NaverUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.getOrDefault("response", Collections.emptyMap());
        return (String) response.get("id");
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.getOrDefault("response", Collections.emptyMap());
        return (String) response.get("email");
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getNickname() {
        Map<String, Object> response = (Map<String, Object>) attributes.getOrDefault("response", Collections.emptyMap());
        return (String) response.get("name");
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getProfileImage() {
        Map<String, Object> response = (Map<String, Object>) attributes.getOrDefault("response", Collections.emptyMap());
        return (String) response.get("profile_image");
    }
}
