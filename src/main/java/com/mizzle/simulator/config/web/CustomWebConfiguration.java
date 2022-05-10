package com.mizzle.simulator.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource(value = { "properties/resource.properties" })
public class CustomWebConfiguration implements WebMvcConfigurer {

    @Value("${resource.upload.path}")
    private String uploadPath;

    @Value("${resource.location.path}")
    private String locationPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        registry.addResourceHandler(uploadPath)
                .addResourceLocations(locationPath);
    }
    
}
