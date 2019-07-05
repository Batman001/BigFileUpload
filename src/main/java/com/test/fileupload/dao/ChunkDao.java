package com.test.fileupload.dao;

import com.test.fileupload.model.Chunk;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Batman create on 2019-07-05 11:18
 */
@Repository
public interface ChunkDao {

    /**
     * 保存文件块
     * @param chunk 文件块
     */
    void saveChunk(Chunk chunk);

    /**
     * 根据identifier得到目前该文件已经上传的分片number
     * @param identifier 文件标识
     * @return 已经上传过的分片number
     */
    List<Chunk> getChunkNumbers(String identifier);

}
