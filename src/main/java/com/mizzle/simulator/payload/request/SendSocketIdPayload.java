package com.mizzle.simulator.payload.request;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class SendSocketIdPayload {

    @Schema( type = "string", example = "http://127.0.0.1/api/websocket/receive")
    private String url;

    @Schema( type = "string", example = "POST")
    private String method;

    @Schema( type = "integer", example = "1000")
    private Integer delay;
}
