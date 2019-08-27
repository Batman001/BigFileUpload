package com.test.fileupload.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @author Batman create on 2019-07-09 00:06
 * 解决跨域问题的配置类
 */
@Configuration
public class Configure implements WebMvcConfigurer {
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

        // 解决后台报错Unexpected EOF read on the socket
        String location = System.getProperty("user.dir") + "/data/tmp";
        File tmpFile = new File(location);
        if(!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        // 单个数据的大小 设置5Mb
        factory.setMaxFileSize("5MB");
        // 总上传数据的大小 设置为10GB
        factory.setMaxRequestSize("10240MB");
        return factory.createMultipartConfig();
    }


}
