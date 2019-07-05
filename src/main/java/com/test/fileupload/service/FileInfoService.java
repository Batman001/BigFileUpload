package com.test.fileupload.service;

import com.test.fileupload.dao.FileInfoDao;
import com.test.fileupload.model.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Batman create on 2019-07-05 11:25
 */
@Service
public class FileInfoService {

    @Autowired
    private FileInfoDao fileInfoDao;

    public void addFileInfo(FileInfo fileInfo) {
        fileInfoDao.addFileInfo(fileInfo);
    }


}
