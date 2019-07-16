package com.test.fileupload.util;

import com.test.fileupload.model.Chunk;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Batman create on 2019-07-01 11:01
 */
@Slf4j
public class FileUtil {

    /**
     * 静态生成路径的方法
     * @param uploadFolder 上传的文件夹
     * @param chunk 上传文件块
     * @return 返回路径信息
     */
    public static String generatePath(String uploadFolder, Chunk chunk) {
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append("/").append(chunk.getIdentifier());
        // 判断uploadFolder/identifier 路径是否正确 不存在则进行创建
        if(!Files.isWritable(Paths.get(sb.toString()))) {
            log.info("path not exist, create path : {}", sb.toString());
            try {
                Files.createDirectories(Paths.get(sb.toString()));
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
            }
        }

        return sb.append("/").append(chunk.getFilename()).append("-")
                .append(chunk.getChunkNumber()).toString();
    }

    /**
     * 对上传文件的分片数据进行合并
     * @param targetFile 目标文件
     * @param folder 合并后的文件夹
     * @param filename 合并后的文件名称
     */
    public static void merge(String targetFile, String folder, String filename) {
        try {
            Files.createFile(Paths.get(targetFile));
            Files.list(Paths.get(folder))
                    .filter(path -> !path.getFileName().toString().equals(filename))
                    .sorted((o1, o2) -> {
                        String p1 = o1.getFileName().toString();
                        String p2 = o2.getFileName().toString();
                        int i1 = p1.lastIndexOf("-");
                        int i2 = p2.lastIndexOf("-");
                        return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                    })
                    .forEach(path -> {
                        try {
                            //以追加的形式写入文件
                            Files.write(Paths.get(targetFile), Files.readAllBytes(path), StandardOpenOption.APPEND);
                            //合并后删除该块
                            Files.delete(path);
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                        }
                    });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


}
