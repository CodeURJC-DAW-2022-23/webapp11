package com.techmarket.app.Repositories;

import com.techmarket.app.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
@EnableJpaRepositories
public interface ImageRepository extends JpaRepository<Image, String> {
    Image findByImageId(Long imageId);  // We will use this to get the image from the database

    long deleteByImageId(Long imageId); // We will use this to delete the image from the database

}

