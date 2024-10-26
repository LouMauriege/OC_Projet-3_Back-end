package com.chatop.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map the URL path `/uploads/**` to the local `uploads/` folder
        registry.addResourceHandler("/rentals-pictures/**")
                .addResourceLocations("file:src/main/resources/static/rentals-pictures/");
    }
}
