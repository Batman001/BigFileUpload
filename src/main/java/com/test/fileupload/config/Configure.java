package com.test.fileupload.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * @author Batman create on 2019-07-09 00:06
 * 解决跨域问题的配置类
 */
@Configuration
public class Configure extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个数据的大小 设置2Mb
        factory.setMaxFileSize("2048KB");
        // 总上传数据的大小 设置为1024Mb
        factory.setMaxRequestSize("1048576KB");
        return factory.createMultipartConfig();
    }
}
