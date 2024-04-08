package com.travelPlus.v1.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {
    @Value("${image.upload.dir}")
    private String uploadDir;

    public String saveImage(String imageName, byte[] imageData) {
        try {
            Path imagePath = Paths.get(uploadDir, imageName);
            // Write imageData to imagePath
            // Return the path to the saved image
            return imagePath.toString();
        } catch (Exception e) {
            // Handle exception
            return null;
        }
    }

}
