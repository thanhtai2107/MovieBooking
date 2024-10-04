package com.example.MovieBooking.service.impl;

import com.cloudinary.Cloudinary;
import com.example.MovieBooking.config.CloudinaryConfig;
import com.example.MovieBooking.service.IUploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author Hoang Thanh Tai
 */
@Service
@Primary
public class UploadImageImpl implements IUploadImage {

    @Autowired
    private Cloudinary cloudinary;

    /**
     *
     * @author Hoang Thanh Tai
     * @param file file image received in multipart request
     * @return url of file after upload to cloudinary
     * @throws IOException if file does not exist
     */
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        return cloudinary.uploader()
                .upload(file.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
}


