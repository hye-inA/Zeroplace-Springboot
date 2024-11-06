package com.demo.zeroplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberCreateRequest {

    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "휴대폰 번호를 입력해주세요")
    private String tel;


}
