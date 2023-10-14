package misha.repository;

import misha.domain.MyFile;
import org.apache.http.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FileRepository extends JpaRepository<MyFile, Integer> {
}
