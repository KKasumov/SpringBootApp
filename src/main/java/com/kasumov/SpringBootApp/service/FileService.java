package com.kasumov.SpringBootApp.service;



import com.kasumov.SpringBootApp.model.File;

import java.io.InputStream;
import java.util.Optional;

public interface FileService {
    void upload(File file);
    InputStream download (File file);
    Optional<String> listFiles();
    void deleteFile(String fileName);
}
