package com.demo.zeroplace.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/** ex
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다.",
 *     "validation": {
 *         "name": "값을 입력해주세요" // 어떤 필드가 잘못되었는지 알려주기 위한 필드
 *     }
 * }
 */
@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String > validation = new HashMap<>();
    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String fieldName, String errorMessage){
        this.validation.put(fieldName, errorMessage);
    }

}
