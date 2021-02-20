package com.zigar.zigarcore.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zigar.zigarcore.entity.FileEntity;
import com.zigar.zigarcore.model.Results;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author zigar
 * @since 2020-06-12
 */
public interface FileService extends IService<FileEntity> {

    /**
     * 上传文件
     *
     * @param file
     * @param currentUserId
     * @return
     */
    Results<FileEntity> uploadFile(MultipartFile file, String currentUserId);

}
