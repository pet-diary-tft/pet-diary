package com.petdiary.domain.rdspetdiarymembershipdb.enums;

public enum MemberStatusType {
    VERIFIED((byte)1, "정상"),
    BLOCKED((byte)2, "정지"),
    DELETED((byte)9, "삭제");

    private final byte code;
    private final String desc;

    MemberStatusType(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static MemberStatusType getByCode(Byte code) {
        if (code == null) return null;
        for (MemberStatusType memberStatusType: MemberStatusType.values()) {
            if (memberStatusType.code == code) return memberStatusType;
        }
        return null;
    }
}
