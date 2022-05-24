package com.mizzle.simulator.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.mizzle.simulator.advice.assertthat.CustomAssert;
import com.mizzle.simulator.annotator.SocketStatus;
import com.mizzle.simulator.entity.Dashboard;
import com.mizzle.simulator.entity.FileNames;
import com.mizzle.simulator.library.file.FileIO;
import com.mizzle.simulator.library.file.FileStruct;
import com.mizzle.simulator.library.socket.WebSocket;
import com.mizzle.simulator.payload.mapping.FileNamesMapping;
import com.mizzle.simulator.payload.request.LoadPayload;
import com.mizzle.simulator.payload.request.RegisterPayload;
import com.mizzle.simulator.payload.request.SendSocketIdPayload;
import com.mizzle.simulator.payload.response.CommonPayload;
import com.mizzle.simulator.repository.DashboardRepository;
import com.mizzle.simulator.repository.FileNamesRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DashboardService {

    private final Logger log = LoggerFactory.getLogger("LogAspect");

    private final DashboardRepository dashboardRepository;
    private final FileNamesRepository fileNamesRepository;

    private final FileIO fileIO;
    private final WebSocket webSocket;

    @Transactional
    public ResponseEntity<?> create(RegisterPayload registerPayload) {

        Dashboard dashboard = Dashboard.builder()
                .title(registerPayload.getTitle())
                .content(registerPayload.getContent())
                .path(registerPayload.getPath())
                .raw(registerPayload.getRaw())
                .build();

        dashboardRepository.save(dashboard);

        List<FileStruct> fileNameList = fileIO.makeFileList(dashboard.getId(), registerPayload.getPath());
        List<FileNames> fileNames = new ArrayList<>();

        for (FileStruct fileName : fileNameList) {
            fileNames.add(
                    FileNames.builder().path(fileName.getPath()).name(fileName.getName()).dashboard(dashboard).build());
        }

        fileNamesRepository.saveAll(fileNames);

        CommonPayload commonPayload = CommonPayload.builder().check(true).information(dashboard).build();
        return ResponseEntity.ok(commonPayload);
    }

    public ResponseEntity<?> readAll(LoadPayload loadPayload) {
        Page<Dashboard> dashboards = dashboardRepository.findAll(
                PageRequest.of(loadPayload.getPage(), loadPayload.getSize(), Sort.by(Sort.Order.desc("createdDate"))));
        
        CommonPayload commonPayload = CommonPayload.builder().check(true).information(dashboards.getContent()).build();
        return ResponseEntity.ok(commonPayload);
    }

    @Transactional
    public ResponseEntity<?> deleteAll() {
        dashboardRepository.deleteAllInBatch();
        CommonPayload commonPayload = CommonPayload.builder().check(true).information(true).build();
        return ResponseEntity.ok(commonPayload);
    }

    public ResponseEntity<?> readById(long id) {
        Optional<Dashboard> dashboard = dashboardRepository.findById(id);
        CustomAssert.isOptionalPresent(dashboard);
        
        List<FileNamesMapping> fileNames = fileNamesRepository.findByDashboard(dashboard.get());
        
        Map<String, Object> json = new HashMap<>();
        
        json.put("dashboard", dashboard);
        json.put("files", fileNames);
        json.put("urlPath", webSocket.urlPath(dashboard.get().getId()));

        CommonPayload commonPayload = CommonPayload.builder().check(true).information(json).build();
        return ResponseEntity.ok(commonPayload);
    }

    @Transactional
    public ResponseEntity<?> updateById(long id, RegisterPayload registerPayload) {
        Optional<Dashboard> dashboard = dashboardRepository.findById(id);
        CustomAssert.isOptionalPresent(dashboard);
        CommonPayload commonPayload = CommonPayload.builder().check(true).information(dashboard.get()).build();
        return ResponseEntity.ok(commonPayload);
    }

    @Transactional
    public ResponseEntity<?> deleteById(long id) {
        Optional<Dashboard> dashboard = dashboardRepository.findById(id);

        CustomAssert.isOptionalPresent(dashboard);
        
        fileNamesRepository.deleteAllByDashboard(dashboard.get());
        dashboardRepository.deleteById(id);
        
        CommonPayload commonPayload = CommonPayload.builder().check(true).information(true).build();
        return ResponseEntity.ok(commonPayload);
    }

    public ResponseEntity<?> sendId(long id, SendSocketIdPayload sendSocketIdPayload) throws InterruptedException {
        Optional<Dashboard> dashboard = dashboardRepository.findById(id);

        CustomAssert.isOptionalPresent(dashboard);

        List<FileNamesMapping> fileNames = fileNamesRepository.findByDashboard(dashboard.get());
        List<Integer> status = new ArrayList<>();

        for (FileNamesMapping fileName : fileNames) {
            String json = webSocket.makeJson(fileName.getPath(), fileName.getName(), dashboard.get().getRaw());
            int resultStatus = webSocket.send(sendSocketIdPayload.getUrl(), sendSocketIdPayload.getMethod(), json);
            status.add(resultStatus);
            Thread.sleep(sendSocketIdPayload.getDelay());
            log.info("Socket Status\t[{}]\tFile=\"{}/{}\"\tStatus={}", LocalDateTime.now(), fileName.getPath(), fileName.getName(), resultStatus);
        }

        CommonPayload commonPayload = CommonPayload.builder().check(true).information(status).build();

        return ResponseEntity.ok(commonPayload);
    }

    
    public ResponseEntity<?> sendId(long id, long fileId, SendSocketIdPayload sendSocketIdPayload) throws InterruptedException {
        Optional<Dashboard> dashboard = dashboardRepository.findById(id);

        CustomAssert.isOptionalPresent(dashboard);

        Optional<FileNamesMapping> fileName = fileNamesRepository.findByIdAndDashboard(fileId, dashboard.get());
        CustomAssert.isOptionalPresent(fileName);

        List<Integer> status = new ArrayList<>();

        String json = webSocket.makeJson(fileName.get().getPath(), fileName.get().getName(), dashboard.get().getRaw());
        int resultStatus = webSocket.send(sendSocketIdPayload.getUrl(), sendSocketIdPayload.getMethod(), json);
        status.add(resultStatus);
        //Thread.sleep(sendSocketIdPayload.getDelay());
        log.info("Socket Status\t[{}]\tFile=\"{}/{}\"\tStatus={}", LocalDateTime.now(), fileName.get().getPath(), fileName.get().getName(), resultStatus);

        CommonPayload commonPayload = CommonPayload.builder().check(true).information(status).build();

        return ResponseEntity.ok(commonPayload);
    }
}
