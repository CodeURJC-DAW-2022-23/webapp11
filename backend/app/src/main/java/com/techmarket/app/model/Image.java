package com.techmarket.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.sql.Blob;

@Entity
@EnableAutoConfiguration
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;
    private String fileName;  // This is the image url, we will use this to send the image to the frontend

    @Lob
    @JsonIgnore  // We don't want to send the image blob to the frontend because it's too big, we will send the image url instead
    private Blob imageBlob;  // We don't use byte[] because it will end up taking too much space in the server's memory

    public Image(Long imageId, String fileName, Blob imageBlob) {
        this.imageId = imageId;
        this.fileName = fileName;
        this.imageBlob = imageBlob;
    }

    public Image() {

    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String image) {
        this.fileName = image;
    }

    public Blob getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(Blob imageBlob) {
        this.imageBlob = imageBlob;
    }
}
