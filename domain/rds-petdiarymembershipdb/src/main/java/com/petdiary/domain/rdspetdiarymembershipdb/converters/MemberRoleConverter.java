package com.petdiary.domain.rdspetdiarymembershipdb.converters;

import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberRoleType;
import jakarta.persistence.AttributeConverter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MemberRoleConverter implements AttributeConverter<Set<MemberRoleType>, String> {
    @Override
    public String convertToDatabaseColumn(Set<MemberRoleType> attribute) {
        if (attribute == null || attribute.isEmpty()) return "";
        return attribute.stream().map(Enum::toString).collect(Collectors.joining(","));
    }

    @Override
    public Set<MemberRoleType> convertToEntityAttribute(String dbData) {
        if (dbData == null) return new HashSet<>();

        Set<MemberRoleType> attribute = new HashSet<>();
        String[] memberRoleTypeStrArr = dbData.split(",");
        for (String memberRoleTypeStr: memberRoleTypeStrArr) {
            try {
                MemberRoleType memberRoleType = MemberRoleType.valueOf(memberRoleTypeStr);
                attribute.add(memberRoleType);
            } catch (IllegalArgumentException ignore) {}
        }
        return attribute;
    }
}
