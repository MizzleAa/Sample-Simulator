package com.mizzle.simulator.api;

import java.io.IOException;

import com.mizzle.simulator.service.FileShareService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@RestController
public class FileShareApi {
    
    private final FileShareService fileShareService;

    @GetMapping(value = "/share/simulator/{id}/{name}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
    public ResponseEntity<?> image(@PathVariable long id, @PathVariable String name) throws IOException{
        return fileShareService.read(id, name);
    }
}
