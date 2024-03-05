package com.kasumov.SpringBootApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kasumov.SpringBootApp.model.File;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDto {
    private Long id;
    private String filename;
    private String location;

    public File toFile() {
        return new File(id, filename, location);
    }

    public static FileDto fromFile(File file) {
        return Objects.isNull(file) ? null : new FileDto(file.getId(), file.getFileName(), file.getLocation());
    }

    public static List<FileDto> toFileDtos(List<File> files) {
        return files.stream()
                .map(FileDto::fromFile)
                .collect(Collectors.toList());
    }
}