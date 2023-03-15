package com.techmarket.app.service;


import com.techmarket.app.Repositories.ImageRepository;
import com.techmarket.app.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    public void deleteImageById(Long imageId) {
        imageRepository.deleteByImageId(imageId);
    }

    public void deleteAllImages(List<Image> images) {
        imageRepository.deleteAll(images);
    }

    public Image getImageById(Long imageId) {
        return imageRepository.findByImageId(imageId);
    }

    public void deleteImage(Long imageId) {
        imageRepository.deleteByImageId(imageId);
    }
}
