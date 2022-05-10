package com.mizzle.simulator.payload.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendSocketPayload {
    
    @Schema( type = "string", example = "http://127.0.0.1/api/websocket/receive")
    @NotBlank(message = "전송할 uri는 필수 입력 값입니다.")
    private String url;

    @Schema( type = "string", example = "POST")
    @NotBlank(message = "통신 형식은 필수 입력 값입니다.")
    private String method;

    @Schema( type = "string", example = "\\192.168.0.11\\share\\simulator")
    @NotBlank(message = "이미지 경로는 필수 입력 값입니다.")
    private String path;

    @Schema( type = "string", example = "sample.png")
    @NotBlank(message = "이미지 명칭은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "외부 파라메터는 필수 입력 값입니다.")
    private String raw;

}
