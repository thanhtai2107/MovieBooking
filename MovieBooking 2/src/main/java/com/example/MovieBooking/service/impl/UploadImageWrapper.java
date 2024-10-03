package com.example.MovieBooking.service.impl;

import com.cloudinary.Cloudinary;
import com.example.MovieBooking.service.IUploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadImageWrapper implements IUploadImage {
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("public_id", UUID.randomUUID().toString());

        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), params);
            return (String) result.get("url");
        } catch (IOException e) {
            throw new IOException("Failed to upload image", e);
        }
    }
}