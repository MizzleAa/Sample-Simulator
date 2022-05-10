package com.mizzle.simulator.api;

import com.mizzle.simulator.advice.assertthat.CustomAssert;
import com.mizzle.simulator.advice.payload.ErrorResponse;
import com.mizzle.simulator.library.socket.WebSocket;
import com.mizzle.simulator.payload.request.ReceiveSocketPayload;
import com.mizzle.simulator.payload.request.SendSocketPayload;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/websocket")
public class WebSocketApi {

    private final WebSocket webSocket;
    
    @Operation(summary = "정보 전송", description = "시뮬레이션을 위한 데이터셋 위치 및 정보를 입력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전송 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SendSocketPayload.class) ) } ),
            @ApiResponse(responseCode = "400", description = "전송 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
            @ApiResponse(responseCode = "404", description = "URL을 찾을 수 없습니다.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } )
    })
    @PostMapping(value = "/send")
    public ResponseEntity<?> send(
            final @RequestBody @Validated SendSocketPayload sendSocketPayload,
            BindingResult errors
    ) {

        CustomAssert.isValidParameter(errors);
        webSocket.send(sendSocketPayload.getUrl(), sendSocketPayload.getMethod(), sendSocketPayload.getRaw());
        return ResponseEntity.ok(true);
    }

    @Operation(summary = "받은 정보", description = "시뮬레이션을 위한 데이터셋 위치 및 정보를 전달 받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전송 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReceiveSocketPayload.class) ) } ),
            @ApiResponse(responseCode = "400", description = "전송 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
            @ApiResponse(responseCode = "404", description = "URL을 찾을 수 없습니다.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } )
    })
    @PostMapping(value ="/receive")
    public ResponseEntity<?> receive(
            final @RequestBody @Validated ReceiveSocketPayload receiveSocketPayload,
            BindingResult errors
    ) {
        CustomAssert.isValidParameter(errors);
        log.info("receiveSocketPayload={}",receiveSocketPayload);
        return ResponseEntity.ok(true);
    }
}
