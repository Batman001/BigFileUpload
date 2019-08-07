package com.test.fileupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Batman
 */
@SpringBootApplication
public class FileuploadApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FileuploadApplication.class, args);
    }

    /**
     * 为了打包springboot项目 继承SpringBootServletInitializer接口
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
