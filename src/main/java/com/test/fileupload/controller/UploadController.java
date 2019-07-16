package com.test.fileupload.controller;

import com.test.fileupload.model.Chunk;
import com.test.fileupload.model.FileInfo;
import com.test.fileupload.service.ChunkService;
import com.test.fileupload.service.FileInfoService;
import com.test.fileupload.util.FileUtil;
import com.test.fileupload.util.OperateTipsUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Batman create on 2019-07-01 10:14
 */
@RestController
@RequestMapping("/upload")
@Slf4j
@MapperScan("com.test.fileupload.dao")
public class UploadController {
    @Value("${prop.upload-folder}")
    private String uploadFolder;

    @Autowired
    private ChunkService chunkService;

    @Autowired
    private FileInfoService fileInfoService;

    private String msg;
    private String detail;
    boolean needMerge;


    /**
     * 处理前台的chunk的post请求, 将分片数据写入本地 同时将分片信息吸入数据库
     * @param chunk 前台传递的chunk分片信息
     * @return 后台执行结果
     */
    @PostMapping("/chunk")
    public Object uploadChunk(Chunk chunk) {
        MultipartFile file = chunk.getFile();
        log.debug("file originName: {}, chunkNumber: {}", file.getOriginalFilename(), chunk.getChunkNumber());

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FileUtil.generatePath(uploadFolder, chunk));
            //文件写入指定路径
            Files.write(path, bytes);
            log.debug("文件 {} 写入成功, uuid:{}", chunk.getFilename(), chunk.getIdentifier());
            chunkService.saveChunk(chunk);
            // 记录已经上传的文件块
            msg = "分块上传成功";
            detail = "文件 " + chunk.getFilename() +
                    "第" + chunk.getChunkNumber().toString() + "块文件上传成功";
            needMerge = chunk.getChunkNumber().equals(chunk.getTotalChunks());
            return OperateTipsUtils.operateTips(OperateTipsUtils.STATUS_OK, msg, needMerge, detail);
        } catch (IOException e) {
            e.printStackTrace();
            msg = "文件导入后端异常...";
            return OperateTipsUtils.operateTips(OperateTipsUtils.STATUS_ERROR, msg, false, "");
        }
    }

    /**
     * get请求 用于判断chunk分片数据是否上传过
     * @param chunk 数据分片
     * @return 返回给前台
     */
    @GetMapping("/chunk")
    public Object checkChunk(Chunk chunk) {

        List<Chunk> uploadedChunk = chunkService.getChunkNumbers(chunk.getIdentifier());

        if (uploadedChunk.size() == 0) {
            return OperateTipsUtils.operateTips(OperateTipsUtils.STATUS_NO_CHUNK,"未上传过该文件任何分片数据", false, "");
        }
        Map<String, Object> res = new HashMap<>(3);
        boolean mergeFlag = chunk.getTotalChunks() == uploadedChunk.size();
        List<Integer> uploaded = new ArrayList<>();
        for(Chunk item:uploadedChunk) {
            uploaded.add(item.getChunkNumber());
        }
        res.put("uploaded", uploaded);
        res.put("needMerge", mergeFlag);

        return res;
    }

    /**
     * 处理前台的合并文件的post的请求
     * @param fileInfo 需要合并的文件具体信息
     * @return
     */
    @PostMapping("/mergeFile")
    public Object mergeFile(@RequestBody FileInfo fileInfo) {
        String filename = fileInfo.getFilename();
        String file = uploadFolder + "/" + fileInfo.getIdentifier() + "/" + filename;
        String folder = uploadFolder + "/" + fileInfo.getIdentifier();
        FileUtil.merge(file, folder, filename);
        fileInfo.setLocation(file);
        fileInfoService.addFileInfo(fileInfo);
        msg = "合并成功";
        return OperateTipsUtils.operateTips(OperateTipsUtils.STATUS_OK, msg, false,"");
    }
}
