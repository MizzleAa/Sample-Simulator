package com.mizzle.simulator.library.file;

import lombok.Builder;
import lombok.Data;

@Data
public class FileStruct {
    private String path;
    private String name;

    public FileStruct(){}

    @Builder
    public FileStruct(String path, String name){
        this.path = path;
        this.name = name;
    }
}
