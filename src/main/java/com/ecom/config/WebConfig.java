package com.ecom.config;

import java.io.File;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadAbsolutePath = new File("uploads").getAbsolutePath();

        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + uploadAbsolutePath + "/", "classpath:/static/img/");
    }
}