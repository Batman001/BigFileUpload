package com.test.fileupload.dao;

import com.test.fileupload.model.FileInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Batman create on 2019-07-05 11:19
 */
@Repository
public interface FileInfoDao {
    /**
     * 添加fileInfo的信息
     * @param fileInfo 当前文件的fileInfo
     * @return 返回添加的FileInfo信息
     */
    void addFileInfo(FileInfo fileInfo);
}
