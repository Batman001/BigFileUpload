package com.test.fileupload.service;

import com.test.fileupload.dao.ChunkDao;
import com.test.fileupload.model.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Batman create on 2019-07-05 11:25
 */
@Service
public class ChunkService {

    @Autowired
    private ChunkDao chunkDao;

    public void saveChunk(Chunk chunk) {
        chunkDao.saveChunk(chunk);
    }


    public List<Chunk> getChunkNumbers(String identifier) {
        return chunkDao.getChunkNumbers(identifier);
    }

}
