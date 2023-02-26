package com.techmarket.app.Repositories;

import com.techmarket.app.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ImageRepository extends JpaRepository<Image, String> {
    Image findByImageId(String imageId);  // We will use this to get the image from the database

}
