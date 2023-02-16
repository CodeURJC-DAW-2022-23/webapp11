package com.techmarket.app.Repositories;

import com.techmarket.app.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {

    // Image findByImageId(String imageId);
    // Not really sure if we need this one

    // List<Image> findByUserId(String productId);
    // Not really sure if we need this one

    // List<Image> findByProductId(String productId);
    // Not really sure if we need this one

    Image findByImage(String image);

}
