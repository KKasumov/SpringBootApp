package com.kasumov.SpringBootApp.repository;


import com.kasumov.SpringBootApp.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Long> {
}
