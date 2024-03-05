package com.kasumov.SpringBootApp.rest;

import com.kasumov.SpringBootApp.dto.FileDto;
import com.kasumov.SpringBootApp.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/files")
public class FileRestController {

    private final FileService fileService;

    public FileRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<Optional<String>> listFiles() {
        Optional<String> fileList = fileService.listFiles();
        if (fileList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fileList);
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<String> delete(@PathVariable String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return ResponseEntity.badRequest().build();
        }
        fileService.deleteFile(fileName);
        return ResponseEntity.ok("File deleted successfully");
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestBody @NonNull FileDto fileDto) {
        fileService.upload(fileDto.toFile());
        return ResponseEntity.ok("File uploaded successfully");
    }

    @PutMapping
    public ResponseEntity<?> download(@RequestBody @NonNull FileDto fileDto) {
        if (StringUtils.isEmpty(fileDto.getFilename())) {
            return ResponseEntity.badRequest().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileDto.getFilename());
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).body(fileService.download(fileDto.toFile()));
    }
}