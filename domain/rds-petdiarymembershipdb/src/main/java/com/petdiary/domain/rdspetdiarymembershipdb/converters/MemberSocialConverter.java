package com.petdiary.domain.rdspetdiarymembershipdb.converters;

import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberSocialType;
import jakarta.persistence.AttributeConverter;

public class MemberSocialConverter implements AttributeConverter<MemberSocialType, String> {
    @Override
    public String convertToDatabaseColumn(MemberSocialType attribute) {
        if (attribute == null) return null;
        return attribute.getRegistrationId();
    }

    @Override
    public MemberSocialType convertToEntityAttribute(String dbData) {
        return MemberSocialType.getByRegistrationId(dbData);
    }
}
