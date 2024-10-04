package com.example.MovieBooking.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadImage {
    String uploadImage(MultipartFile file) throws IOException;

}


