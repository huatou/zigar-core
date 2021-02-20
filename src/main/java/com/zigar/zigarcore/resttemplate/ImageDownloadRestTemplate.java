package com.zigar.zigarcore.resttemplate;


import cn.hutool.core.date.DateUtil;
import com.zigar.zigarcore.entity.FileEntity;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.properties.FileProperties;
import com.zigar.zigarcore.service.FileService;
import com.zigar.zigarcore.utils.date.DateUtils;
import com.zigar.zigarcore.utils.jwt.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;

/**
 * @author Zigar
 * @createTime 2020-07-14 11:12:34
 * @description 图片下载的restTemplate
 */

@Component
public class ImageDownloadRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FileProperties fileProperties;

    @Autowired
    private FileService fileService;

    public Results<FileEntity> getImageByUrl(String url, String uploadUserId) {
        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
            byte[] result = response.getBody();
            InputStream inputStream = new ByteArrayInputStream(result);

            //文件名使用UUID
            String fileName = IdUtils.nextStrId();

            //构建文件夹路径
            String codeImageDirPath = fileProperties.getLocalFilePath() + "/imageCode/" + DateUtil.today() + "/";
            File file = new File(codeImageDirPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            //构建文件路径
            String codeImageFilePath = codeImageDirPath + fileName;
            OutputStream outputStream = new FileOutputStream(codeImageFilePath);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();

            FileEntity newFileEntity = new FileEntity();
            newFileEntity.setUploadUserId(uploadUserId);
            String md5FileName = DigestUtils.md5DigestAsHex(buf);
            newFileEntity.setMd5(md5FileName);

            newFileEntity.setUrl(codeImageFilePath);
            newFileEntity.setName(fileName);
            newFileEntity.setSize(file.length());
            String suffixName = StringUtils.lowerCase(StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, ".") + 1));
            newFileEntity.setSuffix(suffixName);

            fileService.save(newFileEntity);

            return Results.succeed(newFileEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Results.error("保存文件失败" + e.getMessage());
        }
    }


}
