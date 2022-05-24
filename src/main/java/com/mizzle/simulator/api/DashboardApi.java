package com.mizzle.simulator.api;

import com.mizzle.simulator.advice.assertthat.CustomAssert;
import com.mizzle.simulator.advice.payload.ErrorResponse;
import com.mizzle.simulator.payload.request.LoadPayload;
import com.mizzle.simulator.payload.request.RegisterPayload;
import com.mizzle.simulator.payload.request.SendSocketIdPayload;
import com.mizzle.simulator.service.DashboardService;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DashboardApi {

    private final DashboardService dashboardService;

    @Operation(summary = "정보입력", description = "시뮬레이션을 위한 데이터셋 위치 및 정보를 입력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "저장 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterPayload.class) ) } ),
            @ApiResponse(responseCode = "400", description = "저장 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
            @ApiResponse(responseCode = "404", description = "찾을 수 없습니다.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } )
    })
    @PostMapping(value = "/dashboard")
    public ResponseEntity<?> create(
            final @RequestBody @Validated RegisterPayload registerPayload,
            BindingResult errors
    ) {
        CustomAssert.isValidParameter(errors);
        return dashboardService.create(registerPayload);
    }

    @Operation(summary = "모든 페이지 불러오기", description = "패이지 크기에 따른 페이지 목록을 불러옵니다.")
    @GetMapping(value = "/dashboard")
    public ResponseEntity<?> readAll(final @Validated LoadPayload loadPayload, BindingResult errors) {
        CustomAssert.isValidParameter(errors);
        return dashboardService.readAll(loadPayload);
    }

    @Operation(summary = "모든 페이지 삭제하기", description = "모든 페이지를 삭제합니다.")
    @DeleteMapping(value = "/dashboard")
    public ResponseEntity<?> deleteAll() {
        return dashboardService.deleteAll();
    }

    @Operation(summary = "특정 페이지 불러오기", description = "특정 페이지를 불러옵니다.")
    @GetMapping(value = "/dashboard/{id}")
    public ResponseEntity<?> readOne(
        @Parameter(name = "id", description = "전송할 dashboard의 id값", in = ParameterIn.PATH)
        @PathVariable long id
    ) {
        return dashboardService.readById(id);
    }

    @Operation(summary = "특정 페이지 삭제하기", description = "특정 페이지를 삭제합니다.")
    @DeleteMapping(value = "/dashboard/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable long id) {
        return dashboardService.deleteById(id);
    }

    @Operation(summary = "특정 페이지 수정하기", description = "특정 페이지를 수정합니다.")
    @PutMapping(value = "/dashboard/{id}")
    public ResponseEntity<?> updateOne(final @RequestBody @Validated RegisterPayload registerPayload,
            @PathVariable long id, BindingResult errors) {
        CustomAssert.isValidParameter(errors);
        return dashboardService.updateById(id, registerPayload);
    }

    @Operation(summary = "특정 데시보드 목록 전송하기", description = "특정 데시보드 목록을 전송 합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "전송 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SendSocketIdPayload.class) ) } ),
        @ApiResponse(responseCode = "400", description = "전송 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value = "/dashboard/send/{id}")
    public ResponseEntity<?> sendId(
        @Parameter(name = "id", description = "전송할 dashboard의 id값", in = ParameterIn.PATH)
        @PathVariable long id, 
        final @RequestBody @Validated SendSocketIdPayload sendSocketIdPayload,
        BindingResult errors
    ) throws InterruptedException 
    {
        CustomAssert.isValidParameter(errors);
        return dashboardService.sendId(id, sendSocketIdPayload);
    }

    @Operation(summary = "특정 데시보드 목록의 특정 파일 전송하기", description = "특정 데시보드 목록의 특정 파일을 전송 합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "전송 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SendSocketIdPayload.class) ) } ),
        @ApiResponse(responseCode = "400", description = "전송 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value = "/dashboard/send/{id}/{fileId}")
    public ResponseEntity<?> sendId(
        @Parameter(name = "id", description = "전송할 dashboard의 id값", in = ParameterIn.PATH)
        @PathVariable long id, 
        @Parameter(name = "fileId", description = "전송할 dashboard의 file id값", in = ParameterIn.PATH)
        @PathVariable long fileId, 
        final @RequestBody @Validated SendSocketIdPayload sendSocketIdPayload,
        BindingResult errors
    ) throws InterruptedException 
    {
        CustomAssert.isValidParameter(errors);
        return dashboardService.sendId(id, fileId, sendSocketIdPayload);
    }
    
}
