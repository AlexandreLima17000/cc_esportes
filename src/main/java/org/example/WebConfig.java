package org.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapeia a pasta física 'uploads' na raiz do projeto para a URL '/uploads/**'
        File uploadDir = new File("uploads");
        String uploadPath = uploadDir.getAbsolutePath();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + uploadPath + "/", "file:" + uploadPath + "/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}