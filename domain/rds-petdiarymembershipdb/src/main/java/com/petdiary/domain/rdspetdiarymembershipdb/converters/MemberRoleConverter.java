package com.petdiary.domain.rdspetdiarymembershipdb.converters;

import com.petdiary.core.utils.StringUtil;
import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberRoleType;
import jakarta.persistence.AttributeConverter;

import java.util.Set;

public class MemberRoleConverter implements AttributeConverter<Set<MemberRoleType>, String> {
    @Override
    public String convertToDatabaseColumn(Set<MemberRoleType> attribute) {
        return StringUtil.enumSetToString(attribute);
    }

    @Override
    public Set<MemberRoleType> convertToEntityAttribute(String dbData) {
        return StringUtil.stringToEnumSet(dbData, MemberRoleType.class);
    }
}
