package com.mizzle.simulator.service;

import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import com.mizzle.simulator.advice.assertthat.CustomAssert;
import com.mizzle.simulator.entity.FileNames;
import com.mizzle.simulator.library.file.FileIO;
import com.mizzle.simulator.repository.FileNamesRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileShareService {
    private final FileNamesRepository fileNamesRepository;

    private final FileIO fileIO;

    @Transactional
    public ResponseEntity<byte[]> read(long id, String name) throws IOException  {
        
        Optional<FileNames> file = fileNamesRepository.findByName(name);
        CustomAssert.isOptionalPresent(file);

        String path = file.get().getPath();
        byte[] image = fileIO.getImage(path, name);

        return new ResponseEntity<byte[]>(image, HttpStatus.OK);
    }
}
