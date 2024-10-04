package com.example.MovieBooking.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hoang Thanh Tai
 */
@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME = "dxlwkyxxb";
    private final String API_KEY = "279717253714246";
    private final String API_SECRET = "TBUx2jOWIS2oKTO2f1QCfqPeSkU";

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);
        return new Cloudinary(config);
    }
}
