
![mahua](mahua-logo.jpg)
---
title: SpringBoot+Mybatis 后端分离实现大文件分块上传
date: 2019-07-05 17:04:36
categories: "SpringBoot"  MyBatis
tags: 
    - SpringBoot
    - Vue.js
    - 文件上传
---
## SpringBoot + Mybatis + MySql 搭建后端大文件上传项目
### 悄悄说 目前只上传后端 前端工作 未上传
> 参考博客地址:  [https://luoliangdsga.github.io](https://luoliangdsga.github.io)  
# 上传有哪些功能？
* `文件及文件夹`上传功能
    * 支持文件的上传功能
    * 支持文件夹的上传功能
* 文件断点续传功能
    * 文件的`断点续传`功能
    * 使用`mysql存储`已上传的分片信息

## 开始
> 需要准备好基础软件环境
- Java 
- Node(前台需要)
- MySQL
   
## 后端
> 新建一个SpringBoot项目，我这里使用的是SpringBoot2，引入mvc，jpa，mysql相关的依赖。
```java
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.1</version>
        </dependency>

        <!-- 数据库连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.5</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        
    </dependencies>
``` 

> 在yml中配置mvc以及数据库连接等属性
```
server:
  port: 8081

spring:
  multipart:
    max-file-size: 20MB
    max-request-size: 20MB
  datasource:
    url: jdbc:mysql://localhost:3306/fileupload?characterEncoding=utf-8&useSSL=false
    username: root
    password: *******
    # 使用druid数据源
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    filters: stat
    maxActive: 20
    initialSize: 1
    maxWait: 60000
    minIdle: 1
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: select 'x'
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    maxOpenPreparedStatements: 20

mybatis:
  mapper-locations: classpath:mapping/*Mapper.xml
  type-aliases-package: com.test.fileupload.model

#showSql
logging:
  level:
    com.caini.fileupload.*: debug

#pagehelper分页插件
pagehelper:
  helperDialect: mysql
  reasonable: true
  supportMethodsArguments: true
  params: count=countSql


prop:
  upload-folder: ./files

```



   

