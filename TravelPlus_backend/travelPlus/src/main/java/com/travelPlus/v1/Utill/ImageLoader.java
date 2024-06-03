package com.travelPlus.v1.Utill;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Component
public class ImageLoader {
    public byte[] loadImageData(String imagePath) throws IOException {
        // Check if the image path is Base64 encoded
        if (imagePath.startsWith("data:image")) {
            // Extract the base64 string from the image path
            String base64Image = imagePath.split(",")[1];
            // Decode the base64 string to byte array
            return Base64.getDecoder().decode(base64Image);
        } else {
            // If the image path is a file path, load the image data from the file
            Path path = Paths.get(imagePath);
            return Files.readAllBytes(path);
        }
    }
}
