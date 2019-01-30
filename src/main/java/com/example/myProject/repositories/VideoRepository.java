package com.example.myProject.repositories;

import com.example.myProject.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Video findByUrl(String url);

}
