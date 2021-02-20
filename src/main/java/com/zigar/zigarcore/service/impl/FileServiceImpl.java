package com.zigar.zigarcore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.zigar.zigarcore.entity.FileEntity;
import com.zigar.zigarcore.mapper.FileMapper;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.myabtisplus.ZServiceImpl;
import com.zigar.zigarcore.properties.FileProperties;
import com.zigar.zigarcore.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author zigar
 * @since 2020-06-12
 */
@Service
public class FileServiceImpl extends ZServiceImpl<FileMapper, FileEntity> implements FileService {

    @Autowired
    private FileProperties fileProperties;

    @Override
    public Results<FileEntity> uploadFile(MultipartFile file, String currentUserId) {

        /**
         *
         * 判断文件是否为空
         * 判断文件是否已经上传过，已上传的则直接返回数据库内容
         * 检查本服务器是否配置了文件存储位置，文件存储以时间为文件夹存储当天文件
         * 插入数据库文件记录
         * 返回文件id给前端
         *
         */

        //前端没有选择文件，srcFile为空
        if (file == null || file.isEmpty()) {
            return Results.error("请选择文件");
        }
        try {
            //插入文件记录

            //先检查文件是否已经上传
            String md5FileName = DigestUtils.md5DigestAsHex(file.getBytes());
            LambdaQueryWrapper<FileEntity> fileEntityLambdaQueryWrapper = Wrappers.<FileEntity>lambdaQuery().eq(FileEntity::getMd5, md5FileName);
            FileEntity localFileEntity = this.getOne(fileEntityLambdaQueryWrapper);
            if (localFileEntity != null) {
                return Results.succeed(localFileEntity);
            }

            FileEntity newFileEntity = new FileEntity();
            newFileEntity.setUploadUserId(currentUserId);
            newFileEntity.setMd5(md5FileName);

            //构建上传目标路径
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String nowDateStr = simpleDateFormat.format(new Date());
            String destFilePath = fileProperties.getLocalFilePath() + "/" + nowDateStr + "/";
            String filePath = destFilePath + newFileEntity.getMd5();

            newFileEntity.setUrl(filePath);

            String originalFilename = file.getOriginalFilename();

            newFileEntity.setName(originalFilename);
            newFileEntity.setSize(file.getSize());

            String suffixName = StringUtils.lowerCase(StringUtils.substring(originalFilename, StringUtils.lastIndexOf(originalFilename, ".") + 1));
            newFileEntity.setSuffix(suffixName);

            this.save(newFileEntity);


            //开始将源文件写入目标地址
            //如果构建目录不能存在，则需要在本地服务器新建多级目录
            File destFile = new File(destFilePath);
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            byte[] bytes = file.getBytes();
            //目录 + md5文件名称
            Path path = Paths.get(filePath);
            Files.write(path, bytes);

            return Results.succeed(newFileEntity);
        } catch (IOException e) {
            e.printStackTrace();
            return Results.error("文件上传失败：" + e.getMessage());
        }

    }
}
