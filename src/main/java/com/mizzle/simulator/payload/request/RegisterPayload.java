package com.mizzle.simulator.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPayload {
    
    @Schema( type = "string", example = "title")
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    @Size(min = 1, max = 128)
    private String title;
    
    @Schema( type = "string", example = "content")
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    @Size(min = 1, max = 256)
    private String content;

    @Schema( type = "string", example = "//192.168.0.207/aidm/media/fcecd46d-41c5-4313-97d0-5dfde422c49f/image")
    @NotBlank(message = "경로는 필수 입력 값입니다.")
    @Size(min = 1, max = 512)
    private String path;

    @Schema( type = "string", example = "{'sample':'sample'}")
    @NotBlank(message = "정보내용은 필수 입력값 입니다.")
    private String raw;

}
