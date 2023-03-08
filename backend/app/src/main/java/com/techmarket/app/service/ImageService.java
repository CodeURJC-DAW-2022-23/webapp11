package com.techmarket.app.service;


import com.techmarket.app.Repositories.ImageRepository;
import com.techmarket.app.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void saveImage(Image image) {
        imageRepository.save(image);
    }


}
