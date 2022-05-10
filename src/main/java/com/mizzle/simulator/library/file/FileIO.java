package com.mizzle.simulator.library.file;

import java.io.FileInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.mizzle.simulator.advice.assertthat.CustomAssert;
import com.mizzle.simulator.library.generator.Generator;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@PropertySource(value = { "properties/fileio.properties" })
public class FileIO {

    @Value("${file-io.ends-with-list}")
    private List<String> endsWithList;

    @Value("${file-io.save-file-path}")
    private String saveFilePath;

    @Value("${file-io.file-path}")
    private String filePath;


    public List<FileStruct> makeFileList(long id, String path) {
        List<FileStruct> fileNames = new ArrayList<>();
        
        try {
            File[] files = readFileList(path);
            CustomAssert.isListNull(files);
            makeDirectory(id);
            fileNames = saveFileList(id, files);
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return fileNames;
    }

    public byte[] getImage(String path, String name) throws IOException{
        InputStream imageStream = new FileInputStream(path + "/" + name);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return imageByteArray;
    }

    private File[] readFileList(String path) {
        File directory = new File(path);
        File files[] = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                for (String endsWith : endsWithList) {
                    if (name.toLowerCase().endsWith(endsWith)) {
                        return true;
                    }
                }
                return false;
            }
        });

        return files;
    }

    private List<FileStruct> saveFileList(long id, File files[]) throws IOException {
        List<FileStruct> fileNames = new ArrayList<>();

        for (File file : files) {
            String extension = getExtension(file.getName());
            File origin = new File(file.getAbsolutePath());
            File copy = new File(makeFilePathName(id, makeFileName(extension)));
            Files.copy(origin.toPath(), copy.toPath(), StandardCopyOption.REPLACE_EXISTING);
            fileNames.add(FileStruct.builder().path(makeSendFilePath(id)).name(copy.getName()).build());
        }

        return fileNames;
    }

    private String getExtension(String name) {
        return FilenameUtils.getExtension(name);
    }

    private boolean makeDirectory(long id) throws IOException {
        File urlDirectory = new File(saveFilePath + "/" + id);
        if(urlDirectory.exists()){
            urlDirectory.delete();
        }
        urlDirectory.mkdir();

        return true;
    }

    private String makeSendFilePath(long id) throws UnknownHostException {
        // InetAddress local = InetAddress.getLocalHost();
        // String ip = local.getHostAddress();
        //String webFilePath = String.format("%s", saveFilePath);

        return String.format("%s/%d", saveFilePath, id);
    }

    private String makeFilePathName(long id, String name) {
        return String.format("%s/%d/%s", saveFilePath, id, name);
    }

    private String makeFileName(String extension) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh-mm-ss");
        String time = localDateTime.format(dateTimeFormatter);
        String random = Generator.randomString(10);
        return String.format("%s_%s.%s", time, random, extension);
    }

}
