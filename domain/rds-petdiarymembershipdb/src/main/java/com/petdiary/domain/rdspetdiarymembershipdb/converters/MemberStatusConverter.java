package com.petdiary.domain.rdspetdiarymembershipdb.converters;

import com.petdiary.domain.rdspetdiarymembershipdb.enums.MemberStatusType;
import jakarta.persistence.AttributeConverter;

public class MemberStatusConverter implements AttributeConverter<MemberStatusType, Byte> {
    @Override
    public Byte convertToDatabaseColumn(MemberStatusType attribute) {
        if (attribute == null) return null;
        return attribute.getCode();
    }

    @Override
    public MemberStatusType convertToEntityAttribute(Byte dbData) {
        return MemberStatusType.getByCode(dbData);
    }
}
