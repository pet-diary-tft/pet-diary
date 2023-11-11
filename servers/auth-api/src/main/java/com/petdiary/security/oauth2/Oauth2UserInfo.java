package com.petdiary.security.oauth2;

import java.util.Collections;
import java.util.Map;

public abstract class Oauth2UserInfo {

    protected Map<String, Object> attributes;

    public Oauth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes == null ? Collections.emptyMap() : attributes;
    }

    public abstract String getId();

    public abstract String getEmail();

    public abstract String getNickname();

    public abstract String getProfileImage();
}
