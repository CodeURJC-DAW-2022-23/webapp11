package com.techmarket.app.Repositories;

import com.techmarket.app.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ImageRepository extends JpaRepository<Image, String> {

    // Image findByImageId(String imageId);
    // Not really sure if we need this one

    // List<Image> findByUserId(String productId);
    // Not really sure if we need this one

    List<Image> findByProductId(String productId);
    // We need this one to get all the images of a product

    Image findByImage(String image);

}
