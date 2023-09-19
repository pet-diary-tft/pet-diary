package com.petdiary.service;

import com.petdiary.domain.rdspetdiarymembershipdb.dto.MemberDomain;
import com.petdiary.domain.rdspetdiarymembershipdb.service.MemberDomainSvc;
import com.petdiary.dto.req.SwaggerTestReq;
import com.petdiary.dto.res.SwaggerTestRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SwaggerTestSvc {
    private final MemberDomainSvc memberDomainSvc;

    /**
     * 테스트용 서비스니까 그냥 받은 요청값이랑 똑같게 반환하도록 함
     */
    public SwaggerTestRes.Test1Dto test1(SwaggerTestReq.Test1Dto reqDto) {
        SwaggerTestRes.Test1Dto res = SwaggerTestRes.Test1Dto.builder()
                .byteTest(reqDto.getByteTest1())
                .stringTest(reqDto.getStringTest2())
                .longTest(reqDto.getLongTest3())
                .test2DtoList(new ArrayList<>())
                .build();
        for (SwaggerTestReq.Test2Dto test2Dto :reqDto.getTest2DtoList()) {
            res.getTest2DtoList().add(SwaggerTestRes.Test2Dto.builder()
                            .subLongTest(test2Dto.getSubLongTest())
                            .subStringTest(test2Dto.getSubStringTest())
                    .build());
        }
        return res;
    }

    public List<MemberDomain.Dto> getMemberList() {
        return memberDomainSvc.getList();
    }
}
